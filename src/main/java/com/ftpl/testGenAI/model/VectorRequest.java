package com.ftpl.testGenAI.model;

import java.util.List;

public class VectorRequest {
    private String id;

    private List<Double> vector;
    private String metadata;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    // Getters and Setters
    public List<Double> getVector() {
        return vector;
    }

    public void setVector(List<Double> vector) {
        this.vector = vector;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
