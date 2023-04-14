package com.gdu.app03.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.gdu.app03.domain.Contact;

public interface IThirdService {
	// request 안 쓰고 객체로 받고, 주목할 점은 Map으로 매개변수 설정했다.
	// jsp에서 json데이터를 서버측으로 전달하면 빈 이나 맵으로 받는다.
	public ResponseEntity<Contact> execute1(Contact contact);
	public ResponseEntity<Map<String, String>> execute2(Map<String, String> map);
}
