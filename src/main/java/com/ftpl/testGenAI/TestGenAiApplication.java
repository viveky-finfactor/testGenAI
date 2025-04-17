package com.ftpl.testGenAI;

import com.ftpl.testGenAI.config.MongoConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "com.ftpl.testGenAI")
@EnableMongoRepositories("com.ftpl.testGenAI")
@EnableConfigurationProperties(MongoConfigProperties.class)
public class TestGenAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestGenAiApplication.class, args);
	}

}
