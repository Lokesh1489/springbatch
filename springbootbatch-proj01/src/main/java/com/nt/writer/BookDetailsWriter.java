package com.nt.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class BookDetailsWriter implements ItemWriter<String> {

	public BookDetailsWriter() {
		System.out.println("BookDetailsWriter::0-param constructor");
	}

	@Override
	public void write(List<? extends String> items) throws Exception {
		System.out.println("BookDetailsWriter.write()");
		items.forEach(System.out::println);

	}

}
