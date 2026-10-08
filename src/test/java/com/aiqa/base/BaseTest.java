package com.aiqa.base;

import com.aiqa.listeners.AiFailureListener;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners(AiFailureListener.class)
public class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected Page page;

    @BeforeMethod
    public void setUp() {
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "true"));
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(headless));
        page = browser.newPage();
        page.navigate("https://www.saucedemo.com");
    }

    @AfterMethod
    public void tearDown() {
        browser.close();
        playwright.close();
    }

    public Page getPage() {
        return page;
    }
}