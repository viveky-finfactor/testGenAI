package com.ftpl.testGenAI.codegenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class OllamaChatModel implements ChatLanguageModel {

    private static final String OLLAMA_API_URL = "http://localhost:11434/api/generate";
    private final String modelName;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public OllamaChatModel(final String modelName) {
        this.modelName = modelName;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public String generate(final List<ChatMessage> messages) {
        try {
            final ChatMessage lastMessage = messages.get(messages.size() - 1);
            String prompt = "";

            if (lastMessage instanceof UserMessage) {
                final UserMessage userMessage = (UserMessage) lastMessage;
                prompt = userMessage.contents().toString();
            }

            final ObjectNode requestJson = objectMapper.createObjectNode();
            requestJson.put("model", modelName);
            requestJson.put("prompt", prompt);
            requestJson.put("stream", false); // disable streaming

            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(OLLAMA_API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestJson.toString()))
                    .build();

            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            final String responseBody = response.body();

            final ObjectNode responseJson = (ObjectNode) objectMapper.readTree(responseBody);
            return responseJson.get("response").asText();
        } catch (final Exception e) {
            throw new RuntimeException("Failed to generate response from Ollama", e);
        }
    }

    public static String cleanGeneratedContent(String rawOutput) {
        // Remove everything before the first "Feature:" or "package", whichever is appropriate
        int featureIndex = rawOutput.indexOf("Feature:");
        int packageIndex = rawOutput.indexOf("package ");
        int startIndex = featureIndex >= 0 ? featureIndex : (packageIndex >= 0 ? packageIndex : 0);

        // Remove code block markers like ```cucumber or ```java
        String cleaned = rawOutput.substring(startIndex)
                .replaceAll("(?m)^```[a-zA-Z]*\\s*", "")  // remove code block start
                .replaceAll("(?m)^```\\s*", "");          // remove code block end

        return cleaned.trim();
    }

}
