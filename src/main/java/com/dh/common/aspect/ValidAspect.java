package com.dh.common.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dh.common.annotation.Valid;
import com.dh.common.exception.VaildException;

/**
 * 参数检验
 * @author Lenovo
 *
 */
@Aspect
@Component
public class ValidAspect {

	@Autowired
	Validator globalValidator;

	@Pointcut("@annotation(com.dh.common.annotation.Valid)")
	public void Valids() {
	}

	@Before("Valids()")
	public void logAnnotation(final JoinPoint joinPoint) {
		boolean isAll = false;
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		Annotation[] annotations = method.getAnnotations();
		for (Annotation annotation : annotations) {
			if (Valid.class.isInstance(annotation)) {
				Valid valid = (Valid) annotation;
				isAll = valid.type();
			}
		}
		Object[] args = joinPoint.getArgs();
		Set<ConstraintViolation<Object>> set = globalValidator.validate(args[0]);
		StringBuilder sb = new StringBuilder();
		int length = set.size();
		int cursor = 1;
		for (ConstraintViolation<Object> constraintViolation : set) {

			sb.append(constraintViolation.getMessage());
			if (!isAll) {
				break;
			}

			if (cursor < length)
				sb.append(" ,  ");
			cursor++;
		}
		if (sb.length() > 0) {

			throw new VaildException(sb.toString());
		}

	}


}
