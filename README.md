# Test Documentation - Periplus Shopping Cart Automated Test

## Overview
This document outlines the test cases for verifying the shopping cart functionality of the Periplus online bookstore website (https://www.periplus.com/).

---
## Testing Documentation Video
https://youtu.be/xXhSQ7cPV7g 

## Test Environment
- **Website:** https://www.periplus.com/
- **Framework:** Selenium WebDriver 4.41.0
- **Test Framework:** TestNG 7.12.0
- **Browser:** Google Chrome
- **Language:** Java

---

## Test Cases

### TC-001: Add Single Product to Cart
| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-001 |
| **Test Case Name** | Add Single Product to Cart |
| **Objective** | Verify that a user can successfully add a product to the shopping cart |
| **Preconditions** | User is logged in to the website (handled by @BeforeMethod), Cart is empty (handled by @AfterMethod cleanup) |
| **Test Data** | Search: Coding, Index: 0, Qty: 1 |
| **Test Steps** | 1. Navigate to Periplus homepage (handled by @BeforeMethod)<br>2. User login is automatically performed (handled by @BeforeMethod ensureLoggedIn)<br>3. Search for product using search field with keyword "Coding"<br>4. Select first product from search results (index 0)<br>5. Click "Add to Cart" button<br>6. Navigate to cart page |
| **Expected Result** | Product appears in the shopping cart with correct details |
---

### TC-002: Add Product with Different Quantities
| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-002 |
| **Test Case Name** | Add Product with Different Quantities |
| **Objective** | Verify that users can add products with various quantities |
| **Preconditions** | User is logged in to the website (handled by @BeforeMethod), Cart is empty (handled by @AfterMethod cleanup) |
| **Test Data** | Iteration 1: Search: AI, Index: 0, Qty: 1<br>Iteration 2: Search: Comics, Index: 0, Qty: 2<br>Iteration 3: Search: Novel, Index: 1, Qty: 1 |
| **Test Steps** | 1. Navigate to Periplus homepage and login (handled by @BeforeMethod)<br>2. Search for product using DataProvider (AI/Comics/Novel)<br>3. Select product by index from results (0 or 1)<br>4. Set quantity to specified value (1 or 2) using adjustQuantity method<br>5. Click "Add to Cart" button<br>6. Navigate to cart page<br>7. Verify cart contains the product with correct quantity and total (price x quantity) |
| **Expected Result** | Cart displays the exact quantity selected by user and cart total equals price x quantity |
---

### TC-003: Remove Product from Cart
| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-003 |
| **Test Case Name** | Remove Product from Cart |
| **Objective** | Verify that users can remove products from their cart |
| **Preconditions** | User is logged in (handled by @BeforeMethod), Cart is empty (handled by @AfterMethod cleanup) |
| **Test Data** | Search: Coding, Index: 0, Qty: 1 |
| **Test Steps** | 1. Navigate to Periplus homepage and login (handled by @BeforeMethod)<br>2. Search for product using keyword "Coding"<br>3. Select first product from search results (index 0)<br>4. Click "Add to Cart" button<br>5. Navigate to cart page<br>6. Get initial product count<br>7. Click remove button on the product<br>8. Verify cart becomes empty and empty cart message is displayed |
| **Expected Result** | Product is removed and cart becomes empty with message displayed |
---

### TC-004: Update Product Quantity in Cart
| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-004 |
| **Test Case Name** | Update Product Quantity in Cart |
| **Objective** | Verify that users can update product quantities within the cart |
| **Preconditions** | User is logged in (handled by @BeforeMethod), Cart is empty (handled by @AfterMethod cleanup) |
| **Test Data** | Search: Coding, Index: 0, Initial Qty: 1, Increased to: 3 |
| **Test Steps** | 1. Navigate to Periplus homepage and login (handled by @BeforeMethod)<br>2. Search for product using keyword "Coding"<br>3. Select first product from search results (index 0)<br>4. Add product to cart with default quantity 1<br>5. Navigate to cart page<br>6. Get initial cart total<br>7. Click increase quantity button twice (quantity increases from 1 to 3)<br>8. Verify quantity is 3 and total increased accordingly |
| **Expected Result** | Quantity updates to 3 and cart total reflects new quantity |
---

### TC-005: Cart Total Calculation
| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-005 |
| **Test Case Name** | Cart Total Calculation |
| **Objective** | Verify that cart total is calculated correctly (price x quantity) |
| **Preconditions** | User is logged in (handled by @BeforeMethod), Cart is empty (handled by @AfterMethod cleanup) |
| **Test Data** | Search: Coding, Index: 0, Qty: 2 |
| **Test Steps** | 1. Navigate to Periplus homepage and login (handled by @BeforeMethod)<br>2. Search for product using keyword "Coding"<br>3. Select first product from search results (index 0)<br>4. Get the product price before adding<br>5. Set quantity to 2 using adjustQuantity method<br>6. Click "Add to Cart" button<br>7. Navigate to cart page<br>8. Verify cart total equals product price x 2 |
| **Expected Result** | Cart total = product price x quantity |
---

### TC-006: Add Multiple Products to Cart
| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-006 |
| **Test Case Name** | Add Multiple Products to Cart |
| **Objective** | Verify that users can add multiple different products in a single session |
| **Preconditions** | User is logged in (handled by @BeforeMethod), Cart is empty (handled by @AfterMethod cleanup) |
| **Test Data** | Product 1: Search: AI, Index: 0, Qty: 1<br>Product 2: Search: Novel, Index: 0, Qty: 2 |
| **Test Steps** | 1. Navigate to Periplus homepage and login (handled by @BeforeMethod)<br>2. Search for first product using keyword "AI"<br>3. Select first product (index 0), set quantity 1, add to cart<br>4. Return to homepage using navigateToHome()<br>5. Search for second product using keyword "Novel"<br>6. Select first product (index 0), set quantity 2, add to cart<br>7. Navigate to cart page<br>8. Verify cart has at least one product |
| **Expected Result** | Cart contains products after adding multiple items |
---

### TC-007: Add Out of Stock Product to Cart
| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-007 |
| **Test Case Name** | Add Out of Stock Product to Cart |
| **Objective** | Verify that out of stock products cannot be added to cart |
| **Preconditions** | User is logged in (handled by @BeforeMethod), Cart is empty (handled by @AfterMethod cleanup) |
| **Test Data** | Search: Coding, Filter: Currently Unavailable, Index: 0 |
| **Test Steps** | 1. Navigate to Periplus homepage and login (handled by @BeforeMethod)<br>2. Search for product using keyword "Coding"<br>3. Click filter dropdown and select "Currently Unavailable"<br>4. Select first unavailable product from results (index 0)<br>5. Click "Add to Cart" button<br>6. Verify unavailable modal is displayed<br>7. Verify modal text contains "not available" or "0 stock"<br>8. Close the modal<br>9. Navigate to cart page<br>10. Verify cart remains empty |
| **Expected Result** | Modal indicates product is unavailable and cart remains empty |
---

### TC-008: Decrease Quantity in Cart
| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-008 |
| **Test Case Name** | Decrease Quantity in Cart |
| **Objective** | Verify that decreasing product quantity correctly updates subtotal and total |
| **Preconditions** | User is logged in (handled by @BeforeMethod), Cart is empty (handled by @AfterMethod cleanup) |
| **Test Data** | Search: Coding, Index: 0, Initial Qty: 3, Decreased to: 2 |
| **Test Steps** | 1. Navigate to Periplus homepage and login (handled by @BeforeMethod)<br>2. Search for product using keyword "Coding"<br>3. Select first product from search results (index 0)<br>4. Set quantity to 3 using adjustQuantity method<br>5. Click "Add to Cart" button<br>6. Navigate to cart page<br>7. Verify initial quantity is 3<br>8. Get initial cart total<br>9. Click decrease quantity button once<br>10. Verify quantity is 2 and total decreased by product price |
| **Expected Result** | Quantity decreases to 2, total = initial total - product price |
---

### TC-009: Cart Persistence Between Sessions
| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-009 |
| **Test Case Name** | Cart Persistence Between Sessions |
| **Objective** | Verify that cart contents persist after user logs out and logs back in |
| **Preconditions** | User is logged in (handled by @BeforeMethod), Cart is empty (handled by @AfterMethod cleanup) |
| **Test Data** | Search: Coding, Index: 0, Qty: 2 |
| **Test Steps** | 1. Navigate to Periplus homepage and login (handled by @BeforeMethod)<br>2. Search for product using keyword "Coding"<br>3. Select first product from search results (index 0)<br>4. Set quantity to 2 using adjustQuantity method<br>5. Click "Add to Cart" button<br>6. Navigate to cart page<br>7. Verify cart contains the product with correct quantity (2) and total<br>8. Hover over username and click logout<br>9. Verify user is logged out<br>10. Login again with same credentials<br>11. Navigate to cart page<br>12. Verify product, quantity (2), and total persist |
| **Expected Result** | Cart contents (product, quantity, total) remain unchanged after logout and login |

---

## Test Execution Summary

| Test ID | Test Method | Type | Expected Result | Actual Result | Test Result |
|---------|-------------|------|-----------------|---------------|-------------|
| TC-001 | testAddProductToCart | Positive | Product appears in cart | Product appears in cart | Pass |
| TC-002 | testAddProductWithQuantity | Positive | Cart displays exact quantity (AI:1, Comics:2, Novel:1) and correct total | Cart displays exact quantity (AI:1, Comics:2, Novel:1) and correct total | Pass |
| TC-003 | testRemoveProductFromCart | Positive | Cart becomes empty with message displayed | Cart becomes empty with message displayed | Pass |
| TC-004 | testUpdateQuantityInCart | Positive | Quantity updates to 3, total reflects new quantity | Quantity updates to 3, total reflects new quantity | Pass |
| TC-005 | testCartTotalCalculation | Positive | Cart total = price x quantity | Cart total = price x quantity | Pass |
| TC-006 | testAddMultipleProductsToCart | Positive | Cart contains AI (qty 1) + Novel (qty 2) | Cart contains AI (qty 1) + Novel (qty 2) | Pass |
| TC-007 | testAddOutOfStockProduct | Negative | Modal indicates unavailable, cart remains empty | Modal shown with text 'not available/0 stock', cart empty | Pass |
| TC-008 | testDecreaseQuantityInCart | Positive | Quantity decreases to 2, total decreases | Quantity decreases to 2, total decreases | Pass |
| TC-009 | testCartPersistenceBetweenSessions | Positive | Cart contents persist after logout/login | Cart contents persist after logout/login | Pass |

---

## Running the Tests

### Prerequisites
- Java 23
- Maven
- Chrome browser

### Commands
```bash
# Run all tests
mvn clean test
```

---
