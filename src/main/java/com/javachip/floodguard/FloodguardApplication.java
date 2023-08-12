package com.javachip.floodguard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javachip.floodguard.api.CCTVApi;
import com.javachip.floodguard.api.FloodAlertAPI;
import com.javachip.floodguard.dto.MessageDTO;
import com.javachip.floodguard.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
@RequiredArgsConstructor
@EnableScheduling
public class FloodguardApplication {
	@Autowired
	private final CCTVApi cctv;
	@Autowired
	private final FloodAlertAPI floodAlert;
	@Autowired
	private final SmsService service;
	public static void main(String[] args) {
		SpringApplication.run(FloodguardApplication.class, args);
	}
	@Scheduled(fixedRate = 5000)
	public void test() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, URISyntaxException, JsonProcessingException {
//		var result = cctv.getCCTV("126.800000","34.900000","127.890000","35.100000");
//		for(var i : result){
//			System.out.println(i.name);
//		}
		//floodAlert.getFloodAlert();
		service.sendSms(MessageDTO.builder().to("01037258283").content("히히").build());
	}

}
