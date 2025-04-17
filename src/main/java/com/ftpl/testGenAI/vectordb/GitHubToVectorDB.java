package com.ftpl.testGenAI.vectordb;

import com.ftpl.testGenAI.service.EmbeddingService;
import com.ftpl.testGenAI.service.GitService;
import com.ftpl.testGenAI.services.VectorDBService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Main class for the GitHub to Vector DB pipeline using MongoDB Atlas.
 */
public class GitHubToVectorDB {

    private  GitService gitService;

    private  EmbeddingService embeddingService;

    private  VectorDBService vectorDBService;

    private final String gitRepoUrl;
    private final String localRepoPath;

    public GitHubToVectorDB(String gitRepoUrl, String localRepoPath, GitService gitService, EmbeddingService embeddingService, VectorDBService vectorDBService) {
        this.gitRepoUrl = gitRepoUrl;
        this.localRepoPath = localRepoPath;
        this.gitService = gitService;
        this.vectorDBService = vectorDBService;
        this.embeddingService = embeddingService;

    }

    public void process() {
        try {
            System.out.println("Starting GitHub to MongoDB pipeline...");

            // Step 1: Clone GitHub repository
            gitService.cloneRepository(gitRepoUrl, localRepoPath);
            List<String> methodContents = gitService.processJavaFiles(localRepoPath);
            List<String> embeddings = embeddingService.generateEmbeddings(methodContents);

            String id = "git-repo";
            String metaName = "java git repo";
            vectorDBService.insertVectorData(id, embeddings, metaName);

            System.out.println("Insert the data into mongoVectorDB");
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

        String gitRepoUrl = "https://github.com/Finfactor-Technologies/market-data-provider.git";
        String localRepoPath = "/Users/shakshith/Desktop/IdeaProjects";



        GitHubToVectorDB processor = new GitHubToVectorDB(gitRepoUrl, localRepoPath, );
        processor.process();
    }
}
