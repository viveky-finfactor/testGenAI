package com.ftpl.testGenAI.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class MongoConfigProperties {

    private String username;
    private String password;
    private String cluster;
    private String database;

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(final String cluster) {
        this.cluster = cluster;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(final String database) {
        this.database = database;
    }

    public String getConnectionString() {
        return String.format("mongodb+srv://%s:%s@%s/%s?retryWrites=true&w=majority",
                username, password, cluster, database);
    }
}
