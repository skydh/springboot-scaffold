/*
 * Copyright (C) 2018 Pingan, Inc. All Rights Reserved.
 */

package com.dh.common.annotation;

import java.lang.annotation.*;

/**
 * 标示请求无需通过session登陆校验
 */
@Documented
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NoLogin {
}
