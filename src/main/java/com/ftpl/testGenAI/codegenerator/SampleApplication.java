package com.ftpl.testGenAI.codegenerator;

public class SampleApplication {

    public boolean login(String username, String password) {
        // Simulate login logic
        return "user123".equals(username) && "password".equals(password);
    }

    public void register(String username, String password, String email) {
        // Simulate registration logic
        System.out.println("Registered user: " + username + " with email: " + email);
    }
}
