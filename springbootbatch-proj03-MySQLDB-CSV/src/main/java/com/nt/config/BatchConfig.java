package com.nt.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import com.nt.listener.JobMonitoringListener;

import com.nt.model.ExamResult;

import com.nt.processor.ExamResultItemProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobfactory;

	@Autowired
	private StepBuilderFactory stepFactory;

	@Autowired
	private DataSource ds;

	@Autowired
	private JobMonitoringListener listener;

	@Autowired
	private ExamResultItemProcessor processor;
	
	@Bean(name="ffIReader")
	public JdbcCursorItemReader<ExamResult> createReader() {
	return new JdbcCursorItemReaderBuilder<ExamResult>()
	.name("JDBC-Reader")
	.dataSource(ds)
	.sql("SELECT ID,DOB,PERCENTAGE,SEMESTER FROM EXAM_RESULT")
	.beanRowMapper(ExamResult.class) //Internally use BeanPropertyRwoMapper to convert the records
	.build(); //of R5 to given model class object but the db table col names and
	} //model class properties name should match

	@Bean (name="ffIWriter")
	public FlatFileItemWriter<ExamResult> createWriter(){
	return new FlatFileItemWriterBuilder<ExamResult>()
	.name("writer")
	//.resource(new ClassPathResource("TopBrains.csv"))
	.resource(new FileSystemResource("C:\\Users\\lokesh.d\\Documents\\TopBrains.csv"))
	.lineSeparator("\r\n")
	.delimited().delimiter(",")
	.names("id","dob","percentage","semester")
	.build();
	}

	@Bean(name = "step1")
	public Step createStep1() {
		return stepFactory.get("step1").<ExamResult, ExamResult>chunk(50).reader(createReader()).processor(processor)
				.writer(createWriter()).build();
	}

	@Bean(name = "job1")
	public Job createJob1() {
		return jobfactory.get("job1").listener(listener).incrementer(new RunIdIncrementer()).start(createStep1())
				.build();
	}
}
