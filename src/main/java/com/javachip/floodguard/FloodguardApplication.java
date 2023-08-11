package com.javachip.floodguard;

import com.javachip.floodguard.api.CCTVApi;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@RequiredArgsConstructor
@EnableScheduling
public class FloodguardApplication {
	@Autowired
	private final CCTVApi cctv;
	public static void main(String[] args) {
		SpringApplication.run(FloodguardApplication.class, args);
	}
	@Scheduled(fixedRate = 1000)
	public void test() {
		var result = cctv.getCCTV("126.800000","34.900000","127.890000","35.100000");
		for(var i : result){
			System.out.println(i.name);
		}
	}

}
