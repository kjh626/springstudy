package com.gdu.app13.domain.workship;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetiredMemberDTO {
	private int retiredMemberNo;
	private String memberName;
	private String tel;
	private Date joinedAt;
	private Date modifiedAt;
}