package com.ftpl.testGenAI.vectordb.model;

import java.util.List;

/**
 * Model class representing a chunk of code from a file.
 * Includes metadata about the file and chunk position.
 */
public class CodeChunk {
    private final String path;
    private final String filename;
    private final String content;
    private final long chunkIndex;
    private final long totalChunks;
    private List<Float> embedding;

    public CodeChunk(String path, String filename, String content, long chunkIndex, long totalChunks) {
        this.path = path;
        this.filename = filename;
        this.content = content;
        this.chunkIndex = chunkIndex;
        this.totalChunks = totalChunks;
    }

    public String getPath() {
        return path;
    }

    public String getFilename() {
        return filename;
    }

    public String getContent() {
        return content;
    }

    public Long getChunkIndex() {
        return chunkIndex;
    }

    public Long getTotalChunks() {
        return totalChunks;
    }

    public List<Float> getEmbedding() {
        return embedding;
    }

    public void setEmbedding(List<Float> embedding) {
        this.embedding = embedding;
    }

    @Override
    public String toString() {
        return "CodeChunk{" +
                "path='" + path + '\'' +
                ", filename='" + filename + '\'' +
                ", contentLength=" + (content != null ? content.length() : 0) +
                ", chunkIndex=" + chunkIndex +
                ", totalChunks=" + totalChunks +
                '}';
    }
}