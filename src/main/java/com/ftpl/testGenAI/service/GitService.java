package com.ftpl.testGenAI.service;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.CompilationUnit;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class GitService {

    public void cloneRepository(String gitRepoUrl, String localRepoPath) throws GitAPIException {
        System.out.println("Cloning repository from " + gitRepoUrl + " to " + localRepoPath);

        File localPath = new File(localRepoPath);
        if (localPath.exists()) {
            System.out.println("Repository already exists locally, skipping clone");
            return;
        }

        Git.cloneRepository()
                .setURI(gitRepoUrl)
                .setDirectory(localPath)
                .call();

        System.out.println("Repository cloned successfully");
    }

    public List<String> processJavaFiles(String localRepoPath) throws IOException {
        System.out.println("Processing Java files...");
        List<String> methodContents = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(localRepoPath))) {
            List<Path> javaFiles = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .collect(Collectors.toList());

            System.out.println("Found " + javaFiles.size() + " Java files");

            int fileCounter = 0;
            for (Path javaFile : javaFiles) {
                fileCounter++;
                if (fileCounter % 10 == 0) {
                    System.out.println("Processed " + fileCounter + " files so far");
                }

                String relativePath = Paths.get(localRepoPath).relativize(javaFile).toString();
                String fileContent = Files.readString(javaFile);

                // Parse the file content to extract methods
                CompilationUnit cu = StaticJavaParser.parse(fileContent);
                cu.findAll(MethodDeclaration.class).forEach(method -> {
                    String methodContent = method.toString();
                    methodContents.add(methodContent);
                });
            }
        }

        System.out.println("Extracted methods from Java files");
        return methodContents;
    }
}
