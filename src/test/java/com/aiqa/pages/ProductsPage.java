package com.aiqa.pages;

import com.microsoft.playwright.Page;

public class ProductsPage {

    private final Page page;

    public ProductsPage(Page page) {
        this.page = page;
    }

    public String getTitle() {
        return page.textContent(".title");
    }

    public int getProductCount() {
        return page.locator(".inventory_item").count();
    }
}
