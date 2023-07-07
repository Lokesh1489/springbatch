package com.nt.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.nt.listener.JobMonitoringListener;
import com.nt.model.Employee;
import com.nt.processor.EmployeeInfoItemProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobfactory;

	@Autowired
	private StepBuilderFactory stepFactory;

	@Autowired
	private JobMonitoringListener listener;

	@Autowired
	private EmployeeInfoItemProcessor processor;

	@Autowired
	private DataSource ds;

//	@Bean(name = "ffiReader")
//	public FlatFileItemReader<Employee> createFFIReader() {
//		// create Reader object
//		FlatFileItemReader<Employee> reader = new FlatFileItemReader<Employee>();
//
//		// set csv file as resource
//		reader.setResource(new ClassPathResource("Employee_info.csv"));

//	1	// create LineMapper object (To get each line from csv file)
//		DefaultLineMapper<Employee> lineMapper = new DefaultLineMapper<Employee>();
//
//		// create LineTokenizer (to get tokens from lines)
//		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
//		tokenizer.setDelimiter(",");
//		tokenizer.setNames("empno", "ename", "eadd", "salary", "gender");
//
//		// create FieldSetMapper (To set Tokens to model class object properties)
//		BeanWrapperFieldSetMapper<Employee> fieldSetMapper = new BeanWrapperFieldSetMapper<Employee>();
//		fieldSetMapper.setTargetType(Employee.class);
//
//		// set Tokenizer, fieldSetMapper objects to line mapper
//		lineMapper.setFieldSetMapper(fieldSetMapper);
//		lineMapper.setLineTokenizer(tokenizer);
//
//		// set LineMapper to reader object
//		reader.setLineMapper(lineMapper);

//  2 //set LineMapper to Reader Object
//		reader.setLineMapper(new DefaultLineMapper<Employee>() {
//			{
//				setLineTokenizer(new DelimitedLineTokenizer() {
//					{
//						setDelimiter(",");
//						setNames("empno", "ename", "eadd", "salary", "gender");
//					}
//				});
//
//				setFieldSetMapper(new BeanWrapperFieldSetMapper<Employee>() {
//					{
//						setTargetType(Employee.class);
//					}
//				});
//			}
//		});
//
//		return reader;
//	}

	@Bean(name = "ffiReader")
public FlatFileItemReader<Employee> createFFIReader() {
		return new FlatFileItemReaderBuilder<Employee>()
				.name("File-reader")
				.resource(new ClassPathResource("Employee_info.csv"))
				.delimited()
				.names("empno","ename","eadd","salary","gender")
				.targetType(Employee.class)
				.build();
	}

// 1	@Bean(name = "jbiw")
//	public JdbcBatchItemWriter<Employee> createJBIWriter() {
//		// create JDBCBatchItemWriter object
//		JdbcBatchItemWriter<Employee> writer = new JdbcBatchItemWriter<Employee>();
//
//		// set DataSource
//		writer.setDataSource(ds);
//
//		// set Insert SQL Query with named parameters
//		writer.setSql("INSERT INTO EMPLOYEEINFO VALUES(:empno,:ename,:eadd,:salary,:gender,:grosssalary,:netSalary)");
//
//		// create BeanPropertyItemSQLParameterSource Provider Object
//		BeanPropertyItemSqlParameterSourceProvider<Employee> sourceProvider = new BeanPropertyItemSqlParameterSourceProvider<Employee>();
//
//		// set source provider object to write
//		writer.setItemSqlParameterSourceProvider(sourceProvider);
//		return writer;
//	}

	@Bean(name = "jbiw")
	public JdbcBatchItemWriter<Employee> createJBIWriter() {
		return new JdbcBatchItemWriterBuilder<Employee>()
				.dataSource(ds)
				.sql("INSERT INTO EMPLOYEEINFO VALUES(:empno,:ename,:eadd,:salary,:gender,:grosssalary,:netSalary)")
				.beanMapped()
				.build();
	}

	@Bean(name = "step1")
	public Step createStep1() {
		return stepFactory.get("step1").<Employee, Employee>chunk(5).reader(createFFIReader()).processor(processor)
				.writer(createJBIWriter()).build();
	}

	@Bean(name = "job1")
	public Job createJob1() {
		return jobfactory.get("job1").listener(listener).incrementer(new RunIdIncrementer()).start(createStep1())
				.build();
	}

}
