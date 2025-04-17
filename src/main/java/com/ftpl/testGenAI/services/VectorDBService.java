package com.ftpl.testGenAI.services;

import com.ftpl.testGenAI.integration.vectorDB;
import com.ftpl.testGenAI.model.VectorDocument;
import dev.langchain4j.data.document.Metadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VectorDBService {
    private vectorDB vectorDBObj;

    public VectorDBService(vectorDB vectorDBObj){
        this.vectorDBObj = vectorDBObj;
    }

    /**
     * Insert a new vector document into the database.
     *
     * @param id       Unique identifier.
     * @param text     The text (document content).
     * @param metadata The metadata object.
     */
    public void insertVectorData(String id, String text, Metadata metadata) {
        VectorDocument vectorDocument = new VectorDocument(id, text, metadata);
        vectorDBObj.insertVectorDocument(vectorDocument);
    }

    public List<VectorDocument> getVectorDataById(String id) {
        return vectorDBObj.findVectorDocumentById(id);
    }

    public void updateVectorData(String id, String text, Metadata metadata) {
        VectorDocument vectorDocument = new VectorDocument(id, text, metadata);
        vectorDBObj.updateVectorDocumentById(id, vectorDocument);
    }

    public List<VectorDocument> findAll() {
        return vectorDBObj.findAllVectorDocuments();
    }
}
