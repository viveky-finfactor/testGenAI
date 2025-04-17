package com.ftpl.testGenAI.codegenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProjectCodeReader {

    public static String extractAllMethodSignatures(String rootDirectoryPath) {
        List<String> methodDetails = new ArrayList<>();
        File rootDir = new File(rootDirectoryPath);

        // Validate if the directory exists and is a directory
        if (!rootDir.exists() || !rootDir.isDirectory()) {
            throw new IllegalArgumentException("Invalid source directory: " + rootDirectoryPath);
        }

        // Recursively search for Java files
        processDirectory(rootDir, methodDetails);

        return String.join("\n", methodDetails); // Join all method signatures as a string
    }

    // Recursive function to process the directory
    private static void processDirectory(File directory, List<String> methodDetails) {
        // Get all files and directories inside the current directory
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // If it's a directory, recursively call the function
                    processDirectory(file, methodDetails);
                } else if (file.getName().endsWith(".java")) {
                    // If it's a Java file, extract methods from it
                    extractMethodSignaturesFromFile(file, methodDetails);
                }
            }
        }
    }

    // Extract method signatures from a given Java file
    private static void extractMethodSignaturesFromFile(File file, List<String> methodDetails) {
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Regular expression to capture method signatures (basic)
                Pattern pattern = Pattern.compile("\\s*(public|private|protected|static|\\s)*\\s*(\\w+\\s+)+([a-zA-Z_][a-zA-Z0-9_]*)\\s*\\(.*\\)\\s*\\{?");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    // Add the method signature to the list
                    methodDetails.add(matcher.group());
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + file.getPath());
        }
    }

    private static void collectJavaFiles(final File dir, final List<File> javaFiles) {
        final File[] files = dir.listFiles();
        if (files == null) return;

        for (final File file : files) {
            if (file.isDirectory()) {
                collectJavaFiles(file, javaFiles);
            } else if (file.getName().endsWith(".java")) {
                javaFiles.add(file);
            }
        }
    }
}
