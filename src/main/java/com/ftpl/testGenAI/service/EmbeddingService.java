package com.ftpl.testGenAI.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for generating embeddings from code chunks.
 */
@Component
public class EmbeddingService {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private final static EmbeddingModel embeddingModel = OllamaEmbeddingModel.builder()
            .baseUrl("http://localhost:11434")
            .modelName("nomic-embed-text")
            .build();

    /**
     * Generates embeddings for a list of code chunks and returns them as a list of String representations.
     *
     * @param methodContents The list of code chunks (e.g., method bodies) to generate embeddings for.
     * @return A list of String representations of the generated embeddings. Each String is a JSON representation
     * of the embedding vector. Returns an empty list if the input list is null or empty.
     */
    public List<String> generateEmbeddings(List<String> methodContents) {
        if (methodContents == null || methodContents.isEmpty()) {
            return new ArrayList<>();
        }

        System.out.println("Generating embeddings for " + methodContents.size() + " code chunks");

        List<String> embeddings = new ArrayList<>();
        int counter = 0;

        for (String methodContent : methodContents) {
            counter++;
            if (counter % 50 == 0) {
                System.out.println("Generated embeddings for " + counter + " chunks so far");
            }

            // Generate embedding for the method content
            Embedding embedding = embed(methodContent);

            // Convert embedding to a string format (e.g., JSON)
            String embeddingString = convertEmbeddingToString(embedding);

            // Add the embedding string to the list
            if (embeddingString != null) {
                embeddings.add(embeddingString);
            }
        }

        System.out.println("Finished generating embeddings. Total generated: " + embeddings.size());
        return embeddings;
    }

    /**
     * Converts an Embedding object to its String representation (JSON format of the embedding vector).
     *
     * @param embedding The Embedding object to convert.
     * @return A String representation of the embedding vector in JSON format, or null if an error occurs during serialization.
     */
    public String convertEmbeddingToString(Embedding embedding) {
        if (embedding == null || embedding.vector() == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(embedding.vector());
        } catch (IOException e) {
            // Log the error instead of just printing the stack trace in a production environment
            System.err.println("Error converting embedding to String: " + e.getMessage());
            return null;
        }
    }


    /**
     * Generates an embedding for a single text.
     * This method supports generating embedding vectors for individual code snippets.
     *
     * @param text The input text (e.g., a code snippet) to generate an embedding for.
     * @return The generated Embedding object. Returns null if the input text is null or empty.
     */
    public Embedding embed(String text) {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }
        return embeddingModel.embed(text).content();
    }

    /**
     * Generates embeddings for a list of texts.
     * This method facilitates batch embedding generation for optimization.
     *
     * @param texts The list of input texts to generate embeddings for.
     * @return A list of Embedding objects. Returns an empty list if the input list is null or empty.
     */
    public List<Embedding> embedBatch(List<String> texts) {
        if (texts == null || texts.isEmpty()) {
            return new ArrayList<>();
        }
        return texts.stream()
                .filter(text -> text != null && !text.trim().isEmpty()) // Avoid embedding null or empty strings
                .map(text -> embeddingModel.embed(text).content())
                .collect(Collectors.toList());
    }
}
