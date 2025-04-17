package com.ftpl.testGenAI.vectordb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftpl.testGenAI.config.VectorDBConfig;
import com.ftpl.testGenAI.service.EmbeddingService;
import com.ftpl.testGenAI.services.VectorDBService;
import dev.langchain4j.data.embedding.Embedding;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Class for interacting with Ollama using code from MongoDB Atlas.
 */
public class OllamaCodeAssistant {
    private static final int TOP_K = 5;
    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";
    private static final String OLLAMA_MODEL = "codellama"; // Could be codellama, llama3, etc.


    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private EmbeddingService embeddingService;

    @Autowired
    private VectorDBService vectorDBService;


//    public String answerCodeQuestion(String question) throws Exception {
//        // Step 1: Get embeddings for the query
//        Embedding queryEmbedding = embeddingService.embed(question);
//
//        // Step 2: Search relevant code in MongoDB
//        List<Map<String, Object>> searchResults = vectorDBService.searchSimilarCode(
//                VectorDBConfig.COLLECTION_NAME,
//                queryEmbedding.vectorAsList(),
//                TOP_K);
//
//        // Step 3: Format context for Ollama
//        StringBuilder contextBuilder = new StringBuilder();
//        contextBuilder.append("I'm going to provide relevant code from the repository to help answer your question.\n\n");
//
//        for (Map<String, Object> result : searchResults) {
//            String path = (String) result.get("path");
//            String filename = (String) result.get("filename");
//            String content = (String) result.get("content");
//            double score = (Double) result.get("score");
//
//            contextBuilder.append("File: ").append(path).append("\n");
//            contextBuilder.append("Relevance score: ").append(String.format("%.2f", score)).append("\n");
//            contextBuilder.append("```java\n").append(content).append("\n```\n\n");
//        }
//
//        // Step 4: Query Ollama with context and question
//        String context = contextBuilder.toString();
//        String prompt = "You are a helpful code assistant. Use the following context from a Java repository to answer the question.\n\n" +
//                "CONTEXT:\n" + context + "\n\n" +
//                "QUESTION: " + question + "\n\n" +
//                "ANSWER:";
//
//        // Create the request body for Ollama
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("model", OLLAMA_MODEL);
//        requestBody.put("prompt", prompt);
//        requestBody.put("stream", false);
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(OLLAMA_URL))
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(requestBody)))
//                .build();
//
//        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//
//        // Parse response from Ollama
//        Map<String, Object> responseMap = objectMapper.readValue(response.body(), Map.class);
//        return responseMap.get("response").toString();
//    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java OllamaCodeAssistant <mongo-uri> <db-name> <question>");
            System.exit(1);
        }
        String question = args[2];

        try {
            OllamaCodeAssistant assistant = new OllamaCodeAssistant();
//            String answer = assistant.answerCodeQuestion(question);
            System.out.println("Question: " + question);
//            System.out.println("\nAnswer: " + answer);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
