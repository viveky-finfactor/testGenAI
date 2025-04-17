package com.ftpl.testGenAI.model;

import lombok.Data;
import java.util.Map;

@Data
public class VectorRequest {
    private String id;
    private String text;
    private Map<String, Object> metadata;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(final Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "VectorRequest{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", metadata=" + metadata +
                '}';
    }
}
