package com.gdu.app11.service;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface UploadService {
	public void getUploadList(Model model);
	public int addUpload(MultipartHttpServletRequest multipartRequest);
	public void getUploadByNo(int uploadNo, Model model);
	// ajax처리하듯 반환해주는 녀석이 ResponseEntity
	public ResponseEntity<byte[]> display(int attachNo);
}
