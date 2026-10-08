package com.aiqa.ai;

import java.nio.file.Files;
import java.nio.file.Path;

public class TestCaseGenerator {

    public static void main(String[] args) throws Exception {
        String userStory = args.length > 0 ? args[0] :
                "As a shopper, I want to log in with my username and password "
                        + "so that I can see the product list and buy items.";

        String prompt = """
                You are a senior QA engineer.
                Write test cases for this user story.

                User story: %s

                Return a Markdown table with these columns:
                ID, Title, Type, Steps, Expected Result.
                Type must be one of: Positive, Negative, Edge.
                Write 10 test cases. Return only the table.
                """.formatted(userStory);

        System.out.println("Asking AI for test cases...");
        String result = new AiClient().ask(prompt);

        Path out = Path.of("generated", "test-cases.md");
        Files.createDirectories(out.getParent());
        Files.writeString(out, "# Test cases\n\nUser story: " + userStory + "\n\n" + result);

        System.out.println(result);
        System.out.println("\nSaved to " + out.toAbsolutePath());
    }
}