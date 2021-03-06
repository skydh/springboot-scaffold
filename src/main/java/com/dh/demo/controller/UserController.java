package com.dh.demo.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.dh.common.annotation.NoLogin;
import com.dh.common.annotation.Valid;
import com.dh.common.pagination.PageHelper;
import com.dh.common.response.ResponseVO;
import com.dh.demo.entity.User;
import com.dh.demo.service.UserService;
import com.dh.demo.vo.UserOrderVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "UserController", description = "综合案例demo")
@Controller
@RequestMapping(value = "/hello")
@RestControllerAdvice
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService service;

	@ApiOperation(value = "save")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public User getById(@RequestParam("csvFile") MultipartFile file) throws IOException {
		User user = new User();
		InputStream inputStream = null;
		FileOutputStream out = null;
		System.out.println(file.getSize());
		try {
			inputStream = file.getInputStream();
			out = new FileOutputStream(new File("test1.txt"));
			byte[] bytes = new byte[1024 * 1024 * 5];

			int length;
			while ((length = inputStream.read(bytes)) > 0) {
				out.write(bytes, 0, length);
				out.flush();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return user;
	}

	@ApiOperation(value = "jpa初试")
	@RequestMapping(value = "/jpa/{id}", method = RequestMethod.GET)
	@ResponseBody
	public User getById(@PathVariable(name = "id") int id) {
		logger.info("login userid is :[{}]", id);
		return service.getById(id);
	}

	@ApiOperation(value = "参数校验")
	@RequestMapping(value = "/validTry", method = RequestMethod.POST)
	@ResponseBody
	@Valid(type = true)
	public ResponseVO<UserOrderVO> getUserOrder(@Validated @RequestBody UserOrderVO form, BindingResult bindingResult) {

		ResponseVO<UserOrderVO> result = new ResponseVO<UserOrderVO>();
		System.out.println(form);
		return result;
	}

	@ApiOperation(value = "批量插入")
	@RequestMapping(value = "/batchInsert", method = RequestMethod.GET)
	@ResponseBody
	public String batchInsert() {
		System.out.println("进来了");
		List<User> list = new ArrayList<User>();
		for (int i = 0; i < 1000; i++) {
			User ms = new User();
			ms.setId(i + 1000);
			ms.setAddress("园艺街" + i);
			ms.setName("武科大" + i);

			list.add(ms);
		}
		service.batchInsert(list);
		return "hello";
	}

	@Resource
	@Qualifier("mysqlJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("pgJdbcTemplate")
	protected JdbcTemplate jdbcTemplate2;

	@Resource
	private RestTemplate restTemplate;

	@ApiOperation(value = "业务异常")
	@RequestMapping(value = "/exception", method = RequestMethod.GET)
	@ResponseBody
	public String exceptionTry(HttpServletRequest request) {
		System.out.println(request.getRequestURL());
		System.out.println(request.getRequestURI());
		redisTemplate.opsForValue().set("111", "asdasd");
		// String quoteString =
		// restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random",
		// String.class);
		System.out.println(redisTemplate.opsForValue().get("111"));

		return "hello";
	}

	@ApiOperation(value = "分页")
	@RequestMapping(value = "/pageable", method = RequestMethod.GET)
	@ResponseBody
	public ResponseVO<Page<User>> pageableTry(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "15") Integer size) {
		ResponseVO<Page<User>> result = new ResponseVO<Page<User>>();
		Sort sort = new Sort(Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page, size, sort);
		result.setResponseData(service.findByName("100", pageable));
		return result;
	}

	@ApiOperation(value = "事务处理")
	@RequestMapping(value = "/transactionalTry", method = RequestMethod.GET)
	@ResponseBody
	@NoLogin
	public String transactionalTry() {

		return "hello";
	}

	@Resource
	private PageHelper pageHelper;

	@Autowired
	private StringRedisTemplate redisTemplate;

	/**
	 * 这里只是为了测试，业务逻辑一律写在service里面
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	@ApiOperation(value = "多表分页")
	@RequestMapping(value = "/pageablemany", method = RequestMethod.GET)
	@ResponseBody
	public ResponseVO<Page<UserOrderVO>> pageableManyTry(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "15") Integer size) {
		redisTemplate.opsForValue().set("as", "sad");
		ResponseVO<Page<UserOrderVO>> result = new ResponseVO<Page<UserOrderVO>>();
		Sort sort = new Sort(Direction.DESC, "a.id", "a.name");
		Pageable pageable = PageRequest.of(page, size, sort);
		String sql = "select a.id as userId from user a inner join orders b on a.id=b.user_id where a.id=? and b.name=?";
		result.setResponseData(pageHelper.getListDataByUserId(sql, pageable, UserOrderVO.class, 1, "asas"));
		return result;
	}

	@Autowired
	private JavaMailSender javaMailSender;

	@RequestMapping("/sendEmail")
	@ResponseBody
	public void sendEmail() throws Exception {
		MimeMessage message = javaMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("757486169@qq.com");
		helper.setTo("757486169@qq.com");
		helper.setSubject("董航");
		helper.setText("董航");
		// SXSSFWorkbook book=ExcelUtils.title();
		// FileSystemResource file = new FileSystemResource(bos);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		// book.write(os);
		// book.close();

		InputStreamSource iss = new ByteArrayResource(os.toByteArray());
		os.close();

		helper.addAttachment("helloword", iss);
		try {
			javaMailSender.send(message);

			System.out.println("发送好了");
		} catch (MailException ex) {
			System.err.println(ex.getMessage());

		}
	}

}
