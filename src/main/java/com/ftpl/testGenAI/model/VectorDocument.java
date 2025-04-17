package com.ftpl.testGenAI.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "vectorCollection")
public class VectorDocument {

    @Id
    private String id;
    private List<String> vector;
    private String metadata;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public List<String> getVector() {
        return vector;
    }

    public void setVector(final List<String> vector) {
        this.vector = vector;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(final String metadata) {
        this.metadata = metadata;
    }
}
