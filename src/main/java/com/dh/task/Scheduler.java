package com.dh.task;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dh.common.annotation.RedisJobLock;

/**
 * 这里定时器发送邮件
 * 
 * @author Lenovo
 *
 */
@Component
public class Scheduler {

	@Autowired
	private MailProperties mailProperties;

	@Autowired
	private JavaMailSender javaMailSender;

	@RedisJobLock(expire = 60)
	@Scheduled(cron = "0 00 23 * * ?")
	public void SendEmail() throws MessagingException {

		MimeMessage message = null;
		MimeMessageHelper helper = null;

		message = getMessage();
		helper = doHelper(message);
		helper.setText("hello");
		javaMailSender.send(message);

	}

	public MimeMessage getMessage() throws MessagingException {
		return javaMailSender.createMimeMessage();
	}

	public MimeMessageHelper doHelper(MimeMessage message) throws MessagingException {

		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setFrom(mailProperties.getUsername());
		helper.setTo(mailProperties.getReceive());
		helper.setSubject("测试数据");
		helper.setText("测试数据");
		return helper;

	}

}
