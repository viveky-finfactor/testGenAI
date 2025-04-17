package com.ftpl.testGenAI.codegenerator;

public class CucumberTestGenerator {

    // Method to generate Cucumber test case based on method details
    public static String generateCucumberTestCases(final String methodDetails) {
        // Example of Cucumber test case generation
        final String featureTemplate = "Feature: %s\n\n";
        final String scenarioTemplate = "  Scenario: Testing %s method\n" +
                "    Given I am testing the %s method\n" +
                "    When I call the %s method\n" +
                "    Then I should get a valid response\n\n";

        final StringBuilder cucumberTests = new StringBuilder();

        final String[] methodLines = methodDetails.split("\n\n");
        for (final String line : methodLines) {
            if (line.startsWith("Method:")) {
                final String methodName = line.split(":")[1].trim();
                final String feature = String.format(featureTemplate, "Method " + methodName);
                final String scenario = String.format(scenarioTemplate, methodName, methodName, methodName);

                cucumberTests.append(feature);
                cucumberTests.append(scenario);
            }
        }

        return cucumberTests.toString();
    }

    public static void main(final String[] args) {
        final String methodDetails = "Method: login\nReturn Type: boolean\nParameters: 2\n\n" +
                "Method: register\nReturn Type: void\nParameters: 3";
        final String cucumberTestCases = generateCucumberTestCases(methodDetails);
        System.out.println(cucumberTestCases);
    }
}
