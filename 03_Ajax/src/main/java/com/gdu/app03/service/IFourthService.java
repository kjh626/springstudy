package com.gdu.app03.service;

import org.springframework.http.ResponseEntity;

public interface IFourthService {
	// 이미지를 반환한다는 것은 바이트배열을 반환한다는 것이다. 이미지를 읽은 바이트 전체
	public ResponseEntity<byte[]> display(String path, String filename);
}
