package com.aiqa.listeners;

import com.aiqa.ai.AiClient;
import com.aiqa.base.BaseTest;
import com.microsoft.playwright.Page;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class AiFailureListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            String testName = result.getMethod().getMethodName();

            StringWriter sw = new StringWriter();
            result.getThrowable().printStackTrace(new PrintWriter(sw));
            String stackTrace = shorten(sw.toString(), 3000);

            Path dir = Path.of("target", "ai-analysis");
            Files.createDirectories(dir);

            String pageInfo = "Not a UI test.";
            if (result.getInstance() instanceof BaseTest base && base.getPage() != null) {
                Page page = base.getPage();
                page.screenshot(new Page.ScreenshotOptions()
                        .setPath(dir.resolve(testName + ".png")));
                pageInfo = "URL: " + page.url()
                        + "\nVisible text:\n" + shorten(page.innerText("body"), 1500);
            }

            String prompt = """
                    You are a senior QA automation engineer.
                    A test failed. Analyze it.

                    Test name: %s

                    Error and stack trace:
                    %s

                    Page state at failure:
                    %s

                    Answer in this format, under 150 words:
                    1. Likely cause
                    2. Category: App bug, Test issue, Locator issue, Environment, or Flaky
                    3. Suggested fix
                    """.formatted(testName, stackTrace, pageInfo);

            String analysis = new AiClient().ask(prompt);

            Path file = dir.resolve(testName + ".md");
            Files.writeString(file, "# AI analysis: " + testName + "\n\n" + analysis);
            System.out.println("AI analysis saved to " + file.toAbsolutePath());

        } catch (Exception e) {
            System.out.println("AI analysis skipped: " + e.getMessage());
        }
    }

    private String shorten(String text, int max) {
        return text.length() > max ? text.substring(0, max) : text;
    }
}