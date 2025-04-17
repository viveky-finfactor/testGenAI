package com.ftpl.testGenAI.model;

import java.util.List;

public class VectorRequest {
    private String id;
    private List<String> vector;
    private String metadata;

    // Default constructor
    public VectorRequest() {
    }

    public VectorRequest(String id, List<String> vector, String metadata) {
        this.id = id;
        this.vector = vector;
        this.metadata = metadata;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getVector() {
        return vector;
    }

    public void setVector(List<String> vector) {
        this.vector = vector;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "VectorRequest{" +
                "id='" + id + '\'' +
                ", vector=" + vector +
                ", metadata='" + metadata + '\'' +
                "}";
    }
}
