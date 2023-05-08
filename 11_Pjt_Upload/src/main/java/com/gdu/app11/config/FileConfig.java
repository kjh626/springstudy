package com.gdu.app11.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class FileConfig {
	
	// 우리가 바꿀 것은 파일 사이즈밖에 없다. 무턱대고 크게 잡지 말고 서버의 크기에 맞춰서 사이즈 잡아라.
	@Bean
	public CommonsMultipartResolver commonsMultipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
		commonsMultipartResolver.setMaxUploadSize(1024 * 1024 * 100);        // 전체 첨부 파일의 크기 100MB <- 우리는 다중첨부니까
		commonsMultipartResolver.setMaxUploadSizePerFile(1024 * 1024 * 10);  // 첨부 파일 하나의 크기 10MB
		// 서버 메모리가 너무 조금임(안 좋음, 값싼 서버), 이럴 때 서버 메모리를 정해주면 된다. 이런 경우 아니면 굳이 메모리 정해줄 필요 없음.
		return commonsMultipartResolver;
	}
	
}
