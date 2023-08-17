package com.javachip.floodguard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javachip.floodguard.api.CCTVApi;
import com.javachip.floodguard.api.FloodAlertAPI;
import com.javachip.floodguard.dto.MessageDTO;
import com.javachip.floodguard.service.FloodAlertService;
import com.javachip.floodguard.service.ImageAnalysisSevice;
import com.javachip.floodguard.service.PinService;
import com.javachip.floodguard.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.StringJoiner;

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
	@Scheduled(fixedRate = 1000 * 60 * 5)
	public void sendFloodAlert() throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
		var temp = floodAlert.getFloodAlert();
		for(var i : temp){
			if(i.getKind().contains("발령") && i.getWhere().contains("서울시")){
				floodAlertService.alert(i.getWhere(),i.getKind());
			}
		}
	}
	@Scheduled(fixedRate = 1000*60*2)
	public void getCCTV(){
		service.createCCTVPin();
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
						.addMapping("/**")
						.allowedOrigins("http://35.216.52.133:3000", "http://localhost:3000", "http://localhost:8080", "http://localhost:8081", "https://allergysafe.life")
						.allowedHeaders("*")
						.allowedMethods("*");
			}
		};
	}
}
