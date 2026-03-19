package com.periplus.tests;

import com.periplus.base.BaseTest;
import com.periplus.pages.CartPage;
import com.periplus.pages.LoginPage;
import com.periplus.pages.ProductPage;
import com.periplus.pages.ProductsPage;
import utilities.ConfigManager;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartTest extends BaseTest {

    private final String searchTerm = ConfigManager.getProperty("search.term", "Coding");

    @DataProvider(name = "productSearchData")
    public Object[][] getSearchData() {
        return new Object[][] {
            {"AI", 0, 1},
            {"Comics", 0, 2},
            {"Novel", 1, 1}
        };
    }

    @Test
    public void testAddProductToCart() {
        logger.info("Starting test: Add product to cart");

        ProductsPage productsPage = homePage.searchForProduct(searchTerm);
        Assert.assertTrue(productsPage.hasProducts(), "Search should return results");

        ProductPage productPage = productsPage.selectFirstProduct();

        String productTitle = productPage.getProductTitle();
        double productPrice = productPage.getProductPrice();
        logger.info("Selected product: " + productTitle + " with price: " + productPrice);

        productPage.addToCart();

        CartPage cartPage = productPage.goToCart();

        Assert.assertTrue(cartPage.hasItems(), "Cart should have items after adding product");
        logger.info("Product added to cart successfully");
    }

    @Test(dataProvider = "productSearchData")
    public void testAddProductWithQuantity(String search, int productIndex, int quantity) {
        logger.info("Starting test: Add product with quantity - search: " + search + ", quantity: " + quantity);

        Map<String, Double> productPrices = new HashMap<>();
        Map<String, Integer> productQuantities = new HashMap<>();

        ProductsPage productsPage = homePage.searchForProduct(search);
        ProductPage productPage = productsPage.selectProductByIndex(productIndex);

        String productId = productPage.getProductId();
        double productPrice = productPage.getProductPrice();
        logger.info("Product ID: " + productId + ", Price: " + productPrice);

        if (quantity > 1) {
            productPage.adjustQuantity(quantity);
        }

        productPage.addToCart();

        productPrices.put(productId, productPrice);
        productQuantities.put(productId, quantity);

        homePage = homePage.navigateToHome();

        CartPage cartPage = homePage.goToCart();

        Assert.assertTrue(cartPage.hasItems(), "Cart should contain items");

        double expectedTotal = 0.0;
        for (String prodId : productPrices.keySet()) {
            double price = productPrices.get(prodId);
            int qty = productQuantities.get(prodId);

            Assert.assertTrue(cartPage.containsProduct(prodId),
                    "Cart should contain product: " + prodId);

            Assert.assertEquals(cartPage.getProductQuantity(prodId), qty,
                    "Product quantity should match for: " + prodId);

            double expectedSubtotal = price * qty;
            expectedTotal += expectedSubtotal;

            logger.info("Validated product " + prodId + ": price=" + price + ", qty=" + qty + ", subtotal=" + expectedSubtotal);
        }

        double cartTotal = cartPage.getCartTotal();
        Assert.assertEquals(cartTotal, expectedTotal, 0.01,
                "Cart total should match sum of all product subtotals");

        logger.info("Test completed - Cart total: " + cartTotal);
    }

    @Test
    public void testRemoveProductFromCart() {
        logger.info("Starting test: Remove product from cart");

        ProductsPage productsPage = homePage.searchForProduct(searchTerm);
        ProductPage productPage = productsPage.selectFirstProduct();

        String productId = productPage.getProductId();
        productPage.addToCart();

        CartPage cartPage = productPage.goToCart();
        Assert.assertTrue(cartPage.hasItems(), "Cart should have items");
        Assert.assertTrue(cartPage.containsProduct(productId), "Cart should contain the added product");

        int initialCount = cartPage.getNumberOfProducts();
        logger.info("Initial cart items: " + initialCount);

        cartPage.removeProduct(productId);

        int finalCount = cartPage.getNumberOfProducts();

        if (finalCount == 0) {
            Assert.assertTrue(cartPage.isEmpty(), "Cart should be empty after removing all products");
            Assert.assertTrue(cartPage.isEmptyMessageDisplayed(), "Empty cart message should be displayed");
        }

        logger.info("Product removed successfully");
    }

    @Test
    public void testUpdateQuantityInCart() {
        logger.info("Starting test: Update quantity in cart");

        ProductsPage productsPage = homePage.searchForProduct(searchTerm);
        ProductPage productPage = productsPage.selectFirstProduct();

        String productId = productPage.getProductId();
        productPage.addToCart();

        CartPage cartPage = productPage.goToCart();
        double initialTotal = cartPage.getCartTotal();

        cartPage.increaseQuantity();
        cartPage.increaseQuantity();

        int newQuantity = cartPage.getProductQuantity(productId);
        Assert.assertEquals(newQuantity, 3, "Quantity should be updated to 3");

        double newTotal = cartPage.getCartTotal();
        Assert.assertTrue(newTotal > initialTotal, "Total should increase with quantity");
        logger.info("Quantity updated from 1 to 3, total changed from " + initialTotal + " to " + newTotal);
    }

    @Test
    public void testCartTotalCalculation() {
        logger.info("Starting test: Cart total calculation");

        ProductsPage productsPage = homePage.searchForProduct(searchTerm);
        ProductPage productPage = productsPage.selectFirstProduct();
        double productPrice = productPage.getProductPrice();
        productPage.adjustQuantity(2);
        productPage.addToCart();

        CartPage cartPage = productPage.goToCart();
        double cartTotal = cartPage.getCartTotal();

        double expectedTotal = productPrice * 2;
        Assert.assertEquals(cartTotal, expectedTotal, 0.01, "Cart total should equal price times quantity");
        logger.info("Cart total verified: " + cartTotal + " = " + productPrice + " x 2");
    }

    @Test
    public void testAddMultipleProductsToCart() {
        logger.info("Starting test: Add multiple products to cart");

        List<String> searchTerms = Arrays.asList("AI", "Novel");
        List<Integer> productIndices = Arrays.asList(0, 0);
        List<Integer> quantities = Arrays.asList(1, 2);

        for (int i = 0; i < searchTerms.size(); i++) {
            String search = searchTerms.get(i);
            int productIndex = productIndices.get(i);
            int quantity = quantities.get(i);

            ProductsPage productsPage = homePage.searchForProduct(search);
            ProductPage productPage = productsPage.selectProductByIndex(productIndex);

            if (quantity > 1) {
                productPage.adjustQuantity(quantity);
            }
            productPage.addToCart();

            homePage = homePage.navigateToHome();
        }

        CartPage cartPage = CartPage.navigateToCart(driver);

        Assert.assertTrue(cartPage.hasItems(), "Cart should not be empty");
        Assert.assertTrue(cartPage.getNumberOfProducts() >= 1, "Cart should have at least one product");

        logger.info("Multiple products test completed successfully");
    }

    @Test
    public void testAddOutOfStockProduct() {
        logger.info("Starting test: Add out of stock product to cart");

        ProductsPage productsPage = homePage.searchForProduct(searchTerm);
        Assert.assertTrue(productsPage.hasProducts(), "Search should return results");

        productsPage.filterByAvailability("Currently Unavailable");

        List<WebElement> unavailableProducts = productsPage.getUnavailableProducts();
        Assert.assertTrue(unavailableProducts.size() > 0, "Should have at least one out of stock product after filtering");

        ProductPage productPage = productsPage.selectProductByIndex(0);

        productPage.addToCart();

        Assert.assertTrue(productPage.isUnavailableModalDisplayed(),
                "Should show unavailable modal when adding out of stock product to cart");

        String modalText = productPage.getUnavailableModalText();
        Assert.assertTrue(modalText.contains("not available") || modalText.contains("0 stock"),
                "Modal should indicate product is unavailable");

        productPage.closeModal();

        CartPage cartPage = productPage.goToCart();
        Assert.assertFalse(cartPage.hasItems(), "Out of stock product should NOT be added to cart");

        logger.info("Out of stock product test completed successfully");
    }

    @Test
    public void testDecreaseQuantityInCart() {
        logger.info("Starting test: Decrease quantity in cart");

        ProductsPage productsPage = homePage.searchForProduct(searchTerm);
        ProductPage productPage = productsPage.selectFirstProduct();

        String productId = productPage.getProductId();
        double productPrice = productPage.getProductPrice();

        productPage.adjustQuantity(3);
        productPage.addToCart();

        CartPage cartPage = productPage.goToCart();

        Assert.assertTrue(cartPage.hasItems(), "Cart should not be empty");
        Assert.assertEquals(cartPage.getProductQuantity(productId), 3, "Initial quantity should be 3");

        double initialTotal = cartPage.getCartTotal();

        cartPage.decreaseQuantity();

        int newQuantity = 2;
        Assert.assertEquals(cartPage.getProductQuantity(productId), newQuantity, "Quantity should be decreased");

        double expectedNewTotal = initialTotal - productPrice;
        Assert.assertEquals(cartPage.getCartTotal(), expectedNewTotal, 0.01, "Cart total should reflect decreased quantity");

        logger.info("Quantity decrease test completed successfully");
    }

    @Test
    public void testCartPersistenceBetweenSessions() {
        logger.info("Starting test: Cart persistence between sessions");
        
        ProductsPage productsPage = homePage.searchForProduct(searchTerm);
        ProductPage productPage = productsPage.selectFirstProduct();

        String productId = productPage.getProductId();
        double productPrice = productPage.getProductPrice();
        int quantity = 2;
        productPage.adjustQuantity(quantity);
        productPage.addToCart();

        CartPage cartPage = productPage.goToCart();

        Assert.assertTrue(cartPage.hasItems(), "Cart should have items after adding product");
        Assert.assertTrue(cartPage.containsProduct(productId), "Cart should contain the added product");
        Assert.assertEquals(cartPage.getProductQuantity(productId), quantity, "Quantity should match");
        double initialTotal = cartPage.getCartTotal();
        double expectedTotal = productPrice * quantity;
        Assert.assertEquals(initialTotal, expectedTotal, 0.01, "Cart total should match price x quantity");

        logger.info("Step 1 completed - Product added to cart: " + productId + ", qty: " + quantity + ", total: " + initialTotal);

        homePage.logout();

        Assert.assertFalse(homePage.isLoggedIn(), "User should be logged out");
        logger.info("Step 2 completed - User logged out");

        LoginPage loginPage = homePage.goToLoginPage();
        loginPage.login(testEmail, testPassword);
        logger.info("Step 3 completed - User logged in again");

        cartPage = homePage.goToCart();

        Assert.assertTrue(cartPage.hasItems(), "Cart should still have items after login");
        Assert.assertTrue(cartPage.containsProduct(productId), "Cart should contain the same product");
        Assert.assertEquals(cartPage.getProductQuantity(productId), quantity, "Quantity should persist");
        double persistedTotal = cartPage.getCartTotal();
        Assert.assertEquals(persistedTotal, expectedTotal, 0.01, "Cart total should persist");

        logger.info("Step 4 completed - Cart persisted: " + productId + ", qty: " + cartPage.getProductQuantity(productId) + ", total: " + persistedTotal);
        logger.info("Cart persistence test completed successfully");
    }
}
