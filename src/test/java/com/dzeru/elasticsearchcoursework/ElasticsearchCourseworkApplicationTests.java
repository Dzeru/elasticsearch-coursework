package com.dzeru.elasticsearchcoursework;

import com.dzeru.elasticsearchcoursework.services.DocumentExtractor;
import com.dzeru.elasticsearchcoursework.services.HabrDocumentExtractorImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ElasticsearchCourseworkApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private HabrDocumentExtractorImpl documentExtractor;

	@Test
	public void test() throws Exception {
		String[] args = new String[1];
		args[0] = "247373";
		documentExtractor.extractDocument(args);
	}

}
