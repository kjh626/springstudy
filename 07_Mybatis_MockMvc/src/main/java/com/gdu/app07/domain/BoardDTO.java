package com.gdu.app07.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
	private int boardNo;
	private String title;
	private String content;
	private String writer;
	private String createdAt;
	private String modifiedAt;
}
