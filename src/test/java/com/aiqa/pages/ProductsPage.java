package com.aiqa.pages;

import com.microsoft.playwright.Page;
import java.util.List;
import java.util.stream.Collectors;

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

    public void addToCart(String productSlug) {
        page.click("[data-test='add-to-cart-" + productSlug + "']");
    }

    public int getCartBadgeCount() {
        if (page.locator(".shopping_cart_badge").count() == 0) {
            return 0;
        }
        return Integer.parseInt(page.textContent(".shopping_cart_badge"));
    }

    public void openCart() {
        page.click(".shopping_cart_link");
    }

    public void sortBy(String value) {
        page.selectOption(".product_sort_container", value);
    }

    public List<Double> getPrices() {
        return page.locator(".inventory_item_price").allTextContents().stream()
                .map(text -> Double.parseDouble(text.replace("$", "")))
                .collect(Collectors.toList());
    }
}