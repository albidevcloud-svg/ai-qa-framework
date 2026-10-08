package com.aiqa.pages;

import com.microsoft.playwright.Page;

public class CheckoutPage {

    private final Page page;

    public CheckoutPage(Page page) {
        this.page = page;
    }

    public void fillInfo(String firstName, String lastName, String zip) {
        page.fill("[data-test='firstName']", firstName);
        page.fill("[data-test='lastName']", lastName);
        page.fill("[data-test='postalCode']", zip);
        page.click("[data-test='continue']");
    }

    public void finish() {
        page.click("[data-test='finish']");
    }

    public String getConfirmationMessage() {
        return page.textContent(".complete-header");
    }

    public String getErrorMessage() {
        return page.textContent("[data-test='error']");
    }
}
