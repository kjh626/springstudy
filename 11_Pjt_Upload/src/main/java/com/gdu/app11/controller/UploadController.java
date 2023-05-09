package com.gdu.app11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.app11.service.UploadService;

@RequestMapping("/upload")
@Controller
public class UploadController {
	
	// field
	@Autowired
	private UploadService uploadService;

	@GetMapping("/list.do")
	public String list() {
		return "upload/list";
	}
	
	@GetMapping("/write.do")
	public String write() {
		return "upload/write";
	}
	
	@PostMapping("/add.do")
	public void add(MultipartHttpServletRequest multipartRequest) { // MultipartHttpServletRequest: 파일업로드할 때 쓰임(jsp에서 <input type="file">인 놈을 처리할 수 있는 request)
		uploadService.addUpload(multipartRequest);
	}
	
}
