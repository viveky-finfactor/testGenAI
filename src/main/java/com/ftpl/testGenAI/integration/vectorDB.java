package com.ftpl.testGenAI.integration;

import com.ftpl.testGenAI.model.VectorDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class vectorDB {

    @Autowired
    private MongoTemplate mongoTemplate;

    // method for insert Document
    public void insertVectorDocument(VectorDocument vectorDocument){
        mongoTemplate.insert(vectorDocument);
    }

    // method for find Document by id
    public List<VectorDocument> findVectorDocumentById(String id) {
        return mongoTemplate.find(new Query(Criteria.where("_id").is(id)), VectorDocument.class);
    }

    // method to find vector document by vector
    public List<VectorDocument> findVectorDocumentByVector(List<Double> vector) {
        return mongoTemplate.find(new Query(Criteria.where("vector").all(vector)), VectorDocument.class);
    }

    // method for delete Document by id
    public void deleteVectorDocumentById(String id) {
        mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), VectorDocument.class);
    }

    // method for update Document by id
    public void updateVectorDocumentById(String id, VectorDocument vectorDocument) {
        mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(id)), (UpdateDefinition) vectorDocument, VectorDocument.class);
    }
}
