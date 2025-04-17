package com.ftpl.testGenAI.codegenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static List<File> listJavaFiles(File dir) {
        List<File> javaFiles = new ArrayList<>();
        File[] files = dir.listFiles();

        if (files == null) return javaFiles;

        for (File file : files) {
            if (file.isDirectory()) {
                javaFiles.addAll(listJavaFiles(file));
            } else if (file.getName().endsWith(".java")) {
                javaFiles.add(file);
            }
        }

        return javaFiles;
    }
}
