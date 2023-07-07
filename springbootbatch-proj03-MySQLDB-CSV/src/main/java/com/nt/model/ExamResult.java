package com.nt.model;

import java.util.Date;

import lombok.Data;

@Data
public class ExamResult {
	private Integer Id;
	private Date dob;
	private double percentage;
	private Integer semester;
}
