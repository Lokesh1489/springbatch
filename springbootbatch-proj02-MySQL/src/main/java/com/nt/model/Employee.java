package com.nt.model;

import lombok.Data;

@Data
public class Employee {

	private Integer empno;
	private String ename;
	private String eadd;
	private Float salary;
	private String gender;
	private Float grosssalary;
	private Float netSalary;
}
