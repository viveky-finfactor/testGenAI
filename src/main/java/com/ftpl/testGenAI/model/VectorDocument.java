package com.ftpl.testGenAI.model;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.segment.TextSegment;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vectorCollection")
public class VectorDocument implements dev.langchain4j.data.document.Document {

    @Id
    private String id;

    private String text;

    private Metadata metadata;

    public VectorDocument() {}

    public VectorDocument(String id, String text, Metadata metadata) {
        this.id = id;
        this.text = text;
        this.metadata = metadata;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String text() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Metadata metadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public TextSegment toTextSegment() {
        return dev.langchain4j.data.document.Document.super.toTextSegment();
    }
}
