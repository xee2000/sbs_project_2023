package com.ljh.exam.jsp_bootstrap.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
	
	private int id;
	private String title;
	private String body;
	private Date regDate;
	private Date updateDate; 
	private int memberId;
	private String extra__writerName;
}
