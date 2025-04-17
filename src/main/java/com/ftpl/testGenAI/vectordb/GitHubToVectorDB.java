package com.ftpl.testGenAI.vectordb;

import com.ftpl.testGenAI.service.EmbeddingService;
import com.ftpl.testGenAI.service.GitService;
import com.ftpl.testGenAI.services.VectorDBService;
import dev.langchain4j.data.document.Metadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Vector;

/**
 * Main class for the GitHub to Vector DB pipeline using MongoDB Atlas.
 */

@Service
@RequiredArgsConstructor
public class GitHubToVectorDB {

    private  GitService gitService;

    private  EmbeddingService embeddingService;

    private  VectorDBService vectorDBService;

    public GitHubToVectorDB(GitService gitService, EmbeddingService embeddingService, VectorDBService vectorDBService) {
        this.gitService = gitService;
        this.vectorDBService = vectorDBService;
        this.embeddingService = embeddingService;

    }

    public void process() {
        try {
            System.out.println("Starting GitHub to MongoDB pipeline...");

            // Step 1: Clone GitHub repository
            String gitRepoUrl = "https://github.com/Finfactor-Technologies/market-data-provider.git";
            String localRepoPath = "/Users/viveky/Downloads/market-data-provider";

            gitService.cloneRepository(gitRepoUrl, localRepoPath);
            List<String> methodContents = gitService.processJavaFiles(localRepoPath);
            String embeddings = embeddingService.generateEmbeddings(methodContents).toString();

            String id = "git-repo";
            String metaName = "java git repo";

            Metadata metadata = new Metadata();
            metadata.put("name", metaName);
            vectorDBService.insertVectorData(id, embeddings, metadata);

            System.out.println("Inserted the data into mongoVectorDB");
        } catch (Exception e) {
            System.err.println("Error processing repository: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        if (args.length < 2) {
//            System.out.println("Usage: java GitHubToVectorDB <github-repo-url> <local-repo-path>");
//            System.exit(1);
//        }

//        String gitRepoUrl = "https://github.com/Finfactor-Technologies/market-data-provider.git";
//        String localRepoPath = "/Users/mohitgupta/Desktop/Mohit/pfm/projects/market-data-provider";
//
//        GitService gitService1 = new GitService();
//        VectorDBService vectorDBService1  = new VectorDBService();
//        EmbeddingService embeddingService1 = new EmbeddingService();
//
//        GitHubToVectorDB processor = new GitHubToVectorDB(gitRepoUrl, localRepoPath, gitService1, embeddingService1, vectorDBService1);
//        processor.process();
    }
}
