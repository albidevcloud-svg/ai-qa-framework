# AI-Assisted QA Automation Framework

![Tests](https://github.com/albidevcloud-svg/ai-qa-framework/actions/workflows/tests.yml/badge.svg)

A test automation framework I built on my own to practice UI testing, API testing, and using AI in QA work.

## What it does

- **UI tests** for saucedemo.com: login, cart, sorting, checkout
- **API tests** for jsonplaceholder: GET, POST, PUT, DELETE, and a 404 case
- **AI test case generator:** turns a user story into test cases with Claude
- **AI failure analyzer:** when a test fails, it sends the error and page text to Claude and saves a short diagnosis and a screenshot
- **CI:** GitHub Actions runs every test on every push

## Tech stack

Java 21, Maven, Playwright, TestNG, REST Assured, Gson, GitHub Actions, Claude API

## Project structure

    src/main/java/com/aiqa/ai          AI client and test case generator
    src/test/java/com/aiqa/base        BaseTest (browser setup and teardown)
    src/test/java/com/aiqa/pages       Page Objects
    src/test/java/com/aiqa/tests       UI and API tests
    src/test/java/com/aiqa/listeners   AI failure analyzer

## How to run

1. Install JDK 21 or newer and Maven.
2. Set your API key (only needed for the AI features):

       setx ANTHROPIC_API_KEY "your-key"

3. Run the tests:

       mvn clean test

4. Watch the browser: `mvn clean test -Dheadless=false`
5. Generate test cases: run `TestCaseGenerator`.

## How I use AI, and how I check it

- AI writes a first draft of test cases. I review every case, keep the good ones, and rewrite or drop the rest.
- The failure analyzer gives a likely cause. I treat it as a hint, not a verdict. I confirm the cause myself before I fix anything.
- The API key is never in the code. It comes from an environment variable or a GitHub secret.

## What I learned

- [Write 2 or 3 real things here. For example: Maven problems I fixed, how the AI response parsing broke and how I fixed it.]

## Known limits

- The jsonplaceholder API fakes create, update, and delete. It does not save data.
- AI output can be wrong. It is reviewed, not trusted.