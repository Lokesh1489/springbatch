package com.nt.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.nt.model.Employee;
@Component
public class EmployeeInfoItemProcessor implements ItemProcessor<Employee, Employee>{

	@Override
	public Employee process(Employee emp) throws Exception {
	if (emp.getSalary()>=100) {
		emp.setGrosssalary((float)Math.round(emp.getSalary()+emp.getSalary()*0.4f));
		emp.setNetSalary((float) Math.round(emp.getGrosssalary()-emp.getGrosssalary()*0.2f));
		return emp;
	}
		return null;
	}

}
