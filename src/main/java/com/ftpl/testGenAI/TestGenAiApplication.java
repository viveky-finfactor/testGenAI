package com.ftpl.testGenAI;

import com.ftpl.testGenAI.config.MongoConfigProperties;
import com.ftpl.testGenAI.service.EmbeddingService;
import com.ftpl.testGenAI.service.GitService;
import com.ftpl.testGenAI.services.VectorDBService;
import com.ftpl.testGenAI.vectordb.GitHubToVectorDB;
import org.springframework.boot.ApplicationContextFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class TestGenAiApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext contextFactory = SpringApplication.run(TestGenAiApplication.class, args);
		final GitHubToVectorDB processor = contextFactory.getBean(GitHubToVectorDB.class);
		processor.process();
	}


}
