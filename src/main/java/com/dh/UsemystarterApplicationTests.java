package com.dh;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dh.automation.AutomationService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsemystarterApplicationTests {

	@Autowired
	AutomationService helloService;

	@Test
	public void contextLoads() {
		helloService.print();
	}
}