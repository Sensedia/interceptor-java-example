package com.sensedia.classloader.test6;

import com.sensedia.interceptor.externaljar.annotation.ApiSuiteInterceptor;
import com.sensedia.interceptor.externaljar.annotation.InterceptorMethod;
import com.sensedia.interceptor.externaljar.dto.ApiCallData;

@ApiSuiteInterceptor
public class InterceptorAnnotatedClassTest1 {

	@InterceptorMethod
	public void interceptorMethod1(ApiCallData apiCallData) {
		
	}

	@InterceptorMethod
	public void interceptorMethod2(ApiCallData apiCallData) {
		
	}

	public void ordinaryMethod1() {
		
	}

	public void ordinaryMethod2() {
		
	}
}