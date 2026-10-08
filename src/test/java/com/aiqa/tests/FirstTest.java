package com.aiqa.tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FirstTest {

    @Test
    public void loginPageHasCorrectTitle() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();
            page.navigate("https://www.saucedemo.com");
            Assert.assertEquals(page.title(), "Swag Labs");
        }
    }
}