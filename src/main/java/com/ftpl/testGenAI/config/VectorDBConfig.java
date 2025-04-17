package com.ftpl.testGenAI.config;

/**
 * Configuration constants for the MongoDB integration.
 */
public class VectorDBConfig {
    // Collection name in MongoDB Atlas
    public static final String COLLECTION_NAME = "code_repository";

    // MongoDB Atlas default connection details
    public static final String DEFAULT_URI = "mongodb+srv://finfactor_user:finsense123@testgenai.kw5jzhg.mongodb.net/?retryWrites=true&w=majority&appName=testGenAI";
    public static final String DEFAULT_DB_NAME = "test";

    // Ollama default connection details
    public static final String OLLAMA_API_URL = "http://localhost:11434/api/generate";
    public static final String DEFAULT_MODEL = "codellama";

    private VectorDBConfig() {
        // Private constructor to prevent instantiation
    }
}
