package com.aiqa.tests;

import com.aiqa.base.BaseTest;
import com.aiqa.pages.LoginPage;
import com.aiqa.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void validUserCanLogin() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.login("standard_user", "secret_sauce");

        ProductsPage productsPage = new ProductsPage(page);
        Assert.assertEquals(productsPage.getTitle(), "Products");
        Assert.assertEquals(productsPage.getProductCount(), 6);
    }

    @Test
    public void lockedOutUserSeesError() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.login("locked_out_user", "secret_sauce");

        Assert.assertTrue(loginPage.getErrorMessage().contains("locked out"));
    }

    @Test
    public void wrongPasswordSeesError() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.login("standard_user", "wrong_password");

        Assert.assertTrue(loginPage.getErrorMessage().contains("do not match"));
    }
}
