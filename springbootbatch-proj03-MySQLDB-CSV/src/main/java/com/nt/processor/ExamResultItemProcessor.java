package com.nt.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.nt.model.ExamResult;

@Component
public class ExamResultItemProcessor implements ItemProcessor<ExamResult, ExamResult> {

	@Override
	public ExamResult process(ExamResult item) throws Exception {
		System.out.println("ExamResultItemProcessor.process()");
		if (item.getPercentage() >= 75)
			return item;
		else
			return null;
	}

}
