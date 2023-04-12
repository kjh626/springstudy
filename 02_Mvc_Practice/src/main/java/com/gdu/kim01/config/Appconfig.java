package com.gdu.kim01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gdu.kim01.domain.Bbs;

@Configuration
public class Appconfig {
	
	@Bean
	public Bbs bbs2() {
		Bbs bbs = new Bbs();
		bbs.setBbsNo(2);
		bbs.setTitle("헬프미");
		bbs.setCreatedAt("2023-05-02");
		return bbs;
	}
}
