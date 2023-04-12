package com.gdu.kim01.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bbs {
	private int bbsNo;
	private String title;
	private String createdAt;
}