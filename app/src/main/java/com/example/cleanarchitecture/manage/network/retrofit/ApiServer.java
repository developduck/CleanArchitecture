package com.example.cleanarchitecture.manage.network.retrofit;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Hwang on 2018-04-16.
 *
 * Description : API 서버 어노테이션
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiServer {
}
