package com.sensedia.classloader;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.sensedia.interceptor.externaljar.annotation.ApiSuiteInterceptor;
import com.sensedia.interceptor.externaljar.annotation.InterceptorMethod;
import com.sensedia.interceptor.externaljar.dto.ApiCallData;
import com.sensedia.interceptor.externaljar.exception.ApiException;
import com.sensedia.interceptor.externaljar.rest.Http;

@ApiSuiteInterceptor
public class ExampleClass {

	public String methodNoArgs() {
		return "Executing Method Without Args";
	}

	public String methodArgs(String arg) {
		return "Executing Method With Args: " + arg;
	}

	public static String methodStatic() {
		return "Executing Static Method Without Args";
	}

	public static String methodWithRestrictions() {
		return ExampleClass.class.getClassLoader().getParent().toString();
	}

	@InterceptorMethod
	public void verifyHeader(ApiCallData apiCallData) {

		String headerValue = (String) apiCallData.contextVariables.get("header");

		if (apiCallData.request.getHeader("x-id") == null) {
			apiCallData.decision.setAccept(false);
			throw new ApiException(400, "Abortando por falta do header x-id");
		}

		if (!apiCallData.request.getHeader("x-id").equals(headerValue)) {
			apiCallData.decision.setAccept(false);
			throw new ApiException(400, "Valor do header x-id diferente do esperado");
		}
	}

	@InterceptorMethod
	public void changeDestination(ApiCallData apiCallData) throws URISyntaxException {

		if (apiCallData.app != null && apiCallData.app.code.equals("ObKYeKn6atWH")) {

			String destination = (String) apiCallData.contextVariables.get("destination");

			apiCallData.setDestinationUri(new URI(destination));
		}
	}

	@InterceptorMethod
	public void compositeService(ApiCallData apiCallData) {

		if (Http.get("http://sensedia.com").getStatus() == 200) {
			Http.get("http://sensedia.com/blog");
		}

		if (Http.get("http://sensedia.com").getStatus() == 200) {
			Map<String, Object> headers = new HashMap<String, Object>();
			headers.put("Content-Type", "application/json");
			String body = "{ \"hello\": \"world\" }";

			Http.post("http://www.mocky.io/v2/551018c499386d1a0b53b04b", headers, body);
		}
	}
}