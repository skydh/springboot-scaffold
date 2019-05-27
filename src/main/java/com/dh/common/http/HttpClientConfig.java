package com.dh.common.http;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.dh.common.http.properties.HttpClientProperties;

/**
 * 连接池
 * 
 * @author Lenovo
 *
 */
@Configuration
@EnableScheduling
public class HttpClientConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientConfig.class);

	@Resource
	private HttpClientProperties properties;

	/**
	 * 连接池
	 * 
	 * @return
	 */
	@Bean
	public PoolingHttpClientConnectionManager poolingConnectionManager() {
		SSLContextBuilder builder = new SSLContextBuilder();
		try {
			builder.loadTrustMaterial(null, new TrustStrategy() {
				public boolean isTrusted(X509Certificate[] arg0, String arg1) {
					return true;
				}
			});
		} catch (NoSuchAlgorithmException | KeyStoreException e) {
			LOGGER.error("Pooling Connection Manager Initialisation failure because of " + e.getMessage(), e);
		}

		SSLConnectionSocketFactory sslsf = null;
		try {
			sslsf = new SSLConnectionSocketFactory(builder.build());
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			LOGGER.error("Pooling Connection Manager Initialisation failure because of " + e.getMessage(), e);
		}

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("https", sslsf).register("http", new PlainConnectionSocketFactory()).build();

		PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry);
		poolingConnectionManager.setMaxTotal(properties.getMaxTotalConnections()); // 最大连接数
		poolingConnectionManager.setDefaultMaxPerRoute(properties.getDefaultMaxPerRoute()); // 同路由并发数
		return poolingConnectionManager;
	}

	/**
	 * 连接保持时间
	 * 
	 * @return
	 */
	@Bean
	public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
		return new ConnectionKeepAliveStrategy() {
			@Override
			public long getKeepAliveDuration(HttpResponse response, HttpContext httpContext) {
				HeaderElementIterator it = new BasicHeaderElementIterator(
						response.headerIterator(HTTP.CONN_KEEP_ALIVE));
				while (it.hasNext()) {
					HeaderElement he = it.nextElement();
					String param = he.getName();
					String value = he.getValue();
					if (value != null && param.equalsIgnoreCase("timeout")) {
						return Long.parseLong(value) * 1000;
					}
				}
				return properties.getDefaultKeepAliveTimeMillis();
			}
		};
	}

	/**
	 * httpclient
	 * 
	 * @return
	 */
	@Bean
	public CloseableHttpClient httpClient() {
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(properties.getRequestTimeout())
				.setConnectTimeout(properties.getConnectTimeout()).setSocketTimeout(properties.getSocketTimeout())
				.build();

		return HttpClients.custom().setDefaultRequestConfig(requestConfig)
				.setConnectionManager(poolingConnectionManager()).setKeepAliveStrategy(connectionKeepAliveStrategy())
				.setRetryHandler(new DefaultHttpRequestRetryHandler(3, true)) // 重试次数
				.build();
	}

	/**
	 * 定期清理过期连接
	 * 
	 * @param connectionManager
	 * @return
	 */
	@Bean
	public Runnable idleConnectionMonitor(final PoolingHttpClientConnectionManager connectionManager) {
		return new Runnable() {
			@Override
			@Scheduled(fixedDelay = 10000)
			public void run() {
				try {
					if (connectionManager != null) {
						LOGGER.trace("run IdleConnectionMonitor - Closing expired and idle connections...");
						connectionManager.closeExpiredConnections();
						connectionManager.closeIdleConnections(properties.getCloseIdleConnectionWaitTimeSecs(),
								TimeUnit.SECONDS);
					} else {
						LOGGER.trace("run IdleConnectionMonitor - Http Client Connection manager is not initialised");
					}
				} catch (Exception e) {
					LOGGER.error("run IdleConnectionMonitor - Exception occurred. msg={}, e={}", e.getMessage(), e);
				}
			}
		};
	}

}