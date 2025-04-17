package com.ftpl.testGenAI.codegenerator;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.voyageai.VoyageAiEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.filter.comparison.*;
import dev.langchain4j.store.embedding.mongodb.IndexMapping;
import dev.langchain4j.store.embedding.mongodb.MongoDbEmbeddingStore;
import org.bson.Document;

import java.io.*;
import java.util.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        String embeddingApiKey = System.getenv("VOYAGE_AI_KEY");
        String uri = System.getenv("MONGODB_URI");

        // 1. Extract all method details from the project (e.g., your GitHub-based backend app)
        final ChatLanguageModel model = new OllamaChatModel("codellama:7b-instruct");
        final String allMethodDetails = ProjectCodeReader.extractAllMethodSignatures("/Users/mohitgupta/Desktop/Mohit/pfm/projects/third-party-lib");

        // 2. Generate .feature file from extracted methods
        final String featurePrompt = "You are a Java developer working on a Spring Boot backend application. " +
                "Based on the following list of method signatures, generate a comprehensive Cucumber `.feature` file that covers " +
                "realistic usage scenarios for each REST API endpoint. Your response should be in Gherkin syntax, and include: " +
                "\n- Meaningful feature titles," +
                "\n- Detailed scenarios with appropriate step definitions," +
                "\n- Proper given/when/then steps for each method." +
                "\n\nDo not include any markdown or explanations. Only return the raw content of the `.feature` file." +
                "\n\nMethods:\n" + allMethodDetails;


        final String rawFeature = ((OllamaChatModel) model).generate(List.of(new UserMessage(featurePrompt)));
        final String cleanedFeature = cleanGeneratedFeature(rawFeature);
        TestFileWriter.writeTestCasesToFile(cleanedFeature, "generated.feature");

        // 3. Generate Java Step Definitions from the .feature file
        final String stepDefsPrompt = "Generate Java-based Cucumber step definition classes for the following Cucumber `.feature` file. " +
                "Only return the Java code. Do not include any explanations or markdown formatting.\n\n" +
                "Feature File:\n" + cleanedFeature;

        final String rawStepDefs = ((OllamaChatModel) model).generate(List.of(new UserMessage(stepDefsPrompt)));
        final String cleanedStepDefs = cleanGeneratedJava(rawStepDefs);
        TestFileWriter.writeTestCasesToFile(cleanedStepDefs, "StepDefinitions.java");

        System.out.println("✅ Feature file written to generated.feature");
        System.out.println("✅ Step definitions written to StepDefinitions.java");
    }


    // Cleans `.feature` content (removes markdown code blocks etc.)
    public static String cleanGeneratedFeature(final String rawOutput) {
        final int featureIndex = rawOutput.indexOf("Feature:");
        final int startIndex = featureIndex >= 0 ? featureIndex : 0;

        return rawOutput.substring(startIndex)
                .replaceAll("(?m)^```[a-zA-Z]*\\s*", "")
                .replaceAll("(?m)^```\\s*", "")
                .trim();
    }

    // Cleans `.java` code content
    public static String cleanGeneratedJava(final String rawOutput) {
        final int packageIndex = rawOutput.indexOf("package ");
        final int importIndex = rawOutput.indexOf("import ");
        final int classIndex = rawOutput.indexOf("public class ");
        final int startIndex = packageIndex >= 0 ? packageIndex :
                importIndex >= 0 ? importIndex :
                        classIndex >= 0 ? classIndex : 0;

        return rawOutput.substring(startIndex)
                .replaceAll("(?m)^```[a-zA-Z]*\\s*", "")
                .replaceAll("(?m)^```\\s*", "")
                .trim();
    }
}
