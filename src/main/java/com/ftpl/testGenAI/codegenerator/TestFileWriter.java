package com.ftpl.testGenAI.codegenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TestFileWriter {

    public static void writeTestCasesToFile(String content, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
