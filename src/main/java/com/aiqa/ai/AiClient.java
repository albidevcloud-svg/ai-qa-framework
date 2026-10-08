package com.aiqa.ai;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AiClient {

    private static final String URL = "https://api.anthropic.com/v1/messages";
    private static final String MODEL = System.getProperty("ai.model", "claude-sonnet-5-5");

    private final String apiKey;
    private final HttpClient http = HttpClient.newHttpClient();

    public AiClient() {
        apiKey = System.getenv("ANTHROPIC_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("ANTHROPIC_API_KEY is not set");
        }
    }

    public String ask(String prompt) {
        JsonObject message = new JsonObject();
        message.addProperty("role", "user");
        message.addProperty("content", prompt);

        JsonArray messages = new JsonArray();
        messages.add(message);

        JsonObject body = new JsonObject();
        body.addProperty("model", MODEL);
        body.addProperty("max_tokens", 2000);
        body.add("messages", messages);

        HttpRequest request = HttpRequest.newBuilder(URI.create(URL))
                .header("x-api-key", apiKey)
                .header("anthropic-version", "2023-06-01")
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();

        try {
            HttpResponse<String> response =
                    http.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException(
                        "AI call failed: " + response.statusCode() + " " + response.body());
            }

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            return json.getAsJsonArray("content")
                    .get(0).getAsJsonObject()
                    .get("text").getAsString();
        } catch (Exception e) {
            throw new RuntimeException("AI call failed: " + e.getMessage(), e);
        }
    }
}