package com.javachip.floodguard;

import com.javachip.floodguard.api.FloodAlertAPI;
import com.javachip.floodguard.service.FloodAlertService;
import com.javachip.floodguard.service.ImageAnalysisSevice;
import com.javachip.floodguard.service.PinService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@RequiredArgsConstructor
@EnableScheduling
@EnableJpaRepositories
@EnableAspectJAutoProxy
public class FloodguardApplication {
	@Autowired
	private final FloodAlertAPI floodAlert;
	private final FloodAlertService floodAlertService;
	@Autowired
	private final PinService service;
	private final ImageAnalysisSevice imageAnalysisSevice;
	public static void main(String[] args) {
		SpringApplication.run(FloodguardApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
						.addMapping("/**")
						.allowedOrigins("http://35.216.52.133:3000", "http://localhost:3000", "http://localhost:8080", "http://localhost:8081", "https://floodguard.live")
						.allowedHeaders("*")
						.allowedMethods("*");
			}
		};
	}
}
