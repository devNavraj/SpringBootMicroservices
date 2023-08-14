package com.order.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.order.service.external.decoder.CustomErrorDecoder;

import feign.codec.ErrorDecoder;

@Configuration
public class FeignConfig {

	@Bean
	ErrorDecoder errorDecoder() {
		return new CustomErrorDecoder();
	}
}
