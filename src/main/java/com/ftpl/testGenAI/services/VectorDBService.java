package com.ftpl.testGenAI.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftpl.testGenAI.model.VectorDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ftpl.testGenAI.integration.vectorDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class VectorDBService {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private vectorDB vectorDB;

    public void insertVectorData(String id, List<String> vector, String metadata) {
        // Create a new VectorDocument
        VectorDocument vectorDocument = new VectorDocument();
        vectorDocument.setId(id);
        vectorDocument.setVector(vector);
        vectorDocument.setMetadata(metadata);

        // Insert the vector document into the database
        vectorDB.insertVectorDocument(vectorDocument);
    }

    // Get Vector Data by ID
    public List<VectorDocument> getVectorDataById(String id) {
        return vectorDB.findVectorDocumentById(id);
    }

    // Update Vector Data by ID
    public void updateVectorData(String id, List<String> vector, String metadata) {
        VectorDocument vectorDocument = new VectorDocument();
        vectorDocument.setVector(vector);
        vectorDocument.setMetadata(metadata);

        // Update the vector document in the database
        vectorDB.updateVectorDocumentById(id, vectorDocument);
    }

    /**
     * Converts a list of string representations of vectors to a list of Double lists.
     *
     * @param vectorStrings A list of strings, where each string represents a vector
     * (e.g., "[1.0, 2.0, 3.0]").
     * @return A list of lists of Doubles, representing the deserialized vectors.
     * Returns an empty list if the input is null or empty, or if an error
     * occurs during deserialization.
     */
    public List<List<Double>> convertVectorStringsToDoubleLists(List<String> vectorStrings) {
        if (vectorStrings == null || vectorStrings.isEmpty()) {
            return new ArrayList<>();
        }

        List<List<Double>> vectors = new ArrayList<>();
        for (String vectorString : vectorStrings) {
            try {
                // Use Jackson's ObjectMapper to deserialize the string into a List<Double>
                List<Double> doubleList = objectMapper.readValue(vectorString, new TypeReference<List<Double>>() {
                });
                vectors.add(doubleList);
            } catch (IOException e) {
                System.err.println("Error deserializing vector string: " + vectorString + ". Skipping.  Error: "
                        + e.getMessage());
                // Consider logging the error using a proper logging framework (e.g., SLF4J)
                // Skip the invalid vector string and continue processing the others. This is important
                // to prevent the entire process from crashing due to a single malformed vector.
            }
        }
        return vectors;
    }

    /**
     * Searches the vector database for similar code chunks based on an embedding vector.
     *
     * @param collectionName The name of the collection to search in.
     * @param queryVector    The embedding vector to use for the similarity search.
     * @param topK           The maximum number of results to return.
     * @return A list of maps, where each map represents a search result.  The map
     * contains the fields from the VectorDocument (id, vector, metadata)
     * plus a "score" field indicating the similarity. Returns an empty list
     * if no results are found or if an error occurs.
     */
//    public List<Map<String, Object>> searchSimilarCode(String collectionName, List<Double> queryVector, int topK) {
//        try {
//            return vectorDB.searchVectorDocuments(collectionName, queryVector, topK);
//        } catch (Exception e) {
//            System.err.println("Error searching for similar code: " + e.getMessage());
//            //  IMPORTANT:  Use a proper logging framework (e.g., SLF4J) instead of printStackTrace().
//            e.printStackTrace();
//            return List.of();
//        }
//    }
}