package com.ljh.exam.jsp_bootstrap.vo;

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
}
