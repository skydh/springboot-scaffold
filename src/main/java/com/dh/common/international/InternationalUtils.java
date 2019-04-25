package com.dh.common.international;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

/**
 * 国际化
 * 
 * @author Lenovo
 *
 */
@Component
public class InternationalUtils {
	@Autowired
	private MessageSource messageSource;

	public String convertMessage(String message) {
		try {
			return this.messageSource.getMessage(message, null, null);
		} catch (NoSuchMessageException e) {
			e.printStackTrace();
			return message;
		}

	}

}
