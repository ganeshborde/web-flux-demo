package com.example.web_flux_demo.webclient;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

	@Bean("taskWebClient")
	public WebClient taskWebClient(WebClient.Builder builder) {

		return builder.baseUrl("http://localhost:8085/api/tasks").build();
	}

	@Bean("empWebClient")
	public WebClient empWebClient(WebClient.Builder builder) {
		return builder.baseUrl("http://localhost:8085/api/tasks").build();
	}
}
