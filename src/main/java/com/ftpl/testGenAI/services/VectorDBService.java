package com.ftpl.testGenAI.services;

import com.ftpl.testGenAI.model.VectorDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ftpl.testGenAI.integration.vectorDB;

import java.util.List;

@Service
public class VectorDBService {

    @Autowired
    private vectorDB vectorDB;

    public void insertVectorData(String id, List<Double> vector, String metadata) {
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
    public void updateVectorData(String id, List<Double> vector, String metadata) {
        VectorDocument vectorDocument = new VectorDocument();
        vectorDocument.setVector(vector);
        vectorDocument.setMetadata(metadata);

        // Update the vector document in the database
        vectorDB.updateVectorDocumentById(id, vectorDocument);
    }
}