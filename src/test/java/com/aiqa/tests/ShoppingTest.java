package com.aiqa.tests;

import com.aiqa.base.BaseTest;
import com.aiqa.pages.CartPage;
import com.aiqa.pages.CheckoutPage;
import com.aiqa.pages.LoginPage;
import com.aiqa.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShoppingTest extends BaseTest {

    private ProductsPage productsPage;

    @BeforeMethod(dependsOnMethods = "setUp")
    public void loginFirst() {
        new LoginPage(page).login("standard_user", "secret_sauce");
        productsPage = new ProductsPage(page);
    }

    @Test
    public void addingItemUpdatesCartBadge() {
        productsPage.addToCart("sauce-labs-backpack");
        Assert.assertEquals(productsPage.getCartBadgeCount(), 1);
    }

    @Test
    public void cartShowsAddedItems() {
        productsPage.addToCart("sauce-labs-backpack");
        productsPage.addToCart("sauce-labs-bike-light");
        productsPage.openCart();

        Assert.assertEquals(new CartPage(page).getItemCount(), 2);
    }

    @Test
    public void sortByPriceLowToHigh() {
        productsPage.sortBy("lohi");
        List<Double> prices = productsPage.getPrices();

        List<Double> sorted = new ArrayList<>(prices);
        Collections.sort(sorted);
        Assert.assertEquals(prices, sorted);
    }

    @Test
    public void userCanCompleteCheckout() {
        productsPage.addToCart("sauce-labs-backpack");
        productsPage.openCart();
        new CartPage(page).clickCheckout();

        CheckoutPage checkout = new CheckoutPage(page);
        checkout.fillInfo("Alex", "Tester", "77471");
        checkout.finish();

        Assert.assertEquals(checkout.getConfirmationMessage(), "Thank you for your order!");
    }

    @Test
    public void checkoutFailsWithoutFirstName() {
        productsPage.addToCart("sauce-labs-backpack");
        productsPage.openCart();
        new CartPage(page).clickCheckout();

        CheckoutPage checkout = new CheckoutPage(page);
        checkout.fillInfo("", "Tester", "77471");

        Assert.assertTrue(checkout.getErrorMessage().contains("First Name is required"));
    }
}