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
| **Preconditions** | User is logged in to the website, Cart is empty |
| **Test Steps** | 1. Navigate to Periplus homepage<br>2. Search for a product using search field<br>3. Select a product from search results<br>4. Click "Add to Cart" button<br>5. Navigate to cart page |
| **Expected Result** | Product appears in the shopping cart with correct details |
---

### TC-002: Add Product with Different Quantities
| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-002 |
| **Test Case Name** | Add Product with Different Quantities |
| **Objective** | Verify that users can add products with various quantities |
| **Preconditions** | User is logged in to the website, Cart is empty |
| **Test Steps** | 1. Search for a product using DataProvider (AI, Comics, Novel)<br>2. Select product by index from results<br>3. Set quantity to specified value (1 or 2)<br>4. Add to cart<br>5. Navigate to cart page<br>6. Verify cart shows correct quantity and total |
| **Expected Result** | Cart displays the exact quantity selected by user and cart total equals price x quantity |
---

### TC-003: Remove Product from Cart
| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-003 |
| **Test Case Name** | Remove Product from Cart |
| **Objective** | Verify that users can remove products from their cart |
| **Preconditions** | User has at least one product in the cart |
| **Test Steps** | 1. Add a product to cart<br>2. Navigate to cart page<br>3. Get initial product count<br>4. Click remove button on product<br>5. Verify product is removed and empty cart message is displayed |
| **Expected Result** | Product is removed and cart becomes empty with message displayed |
---

### TC-004: Update Product Quantity in Cart
| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-004 |
| **Test Case Name** | Update Product Quantity in Cart |
| **Objective** | Verify that users can update product quantities within the cart |
| **Preconditions** | User has a product in the cart |
| **Test Steps** | 1. Add product with quantity 1<br>2. Navigate to cart<br>3. Get initial total<br>4. Click increase quantity button twice (total becomes 3)<br>5. Verify quantity is 3 and total increased |
| **Expected Result** | Quantity updates to 3 and cart total reflects new quantity |
---

### TC-005: Cart Total Calculation
| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-005 |
| **Test Case Name** | Cart Total Calculation |
| **Objective** | Verify that cart total is calculated correctly (price x quantity) |
| **Preconditions** | User is logged in |
| **Test Steps** | 1. Search for a product<br>2. Note the product price<br>3. Add product with quantity 2<br>4. Navigate to cart<br>5. Verify total equals price x 2 |
| **Expected Result** | Cart total = product price x quantity |
---

### TC-006: Add Multiple Products to Cart
| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-006 |
| **Test Case Name** | Add Multiple Products to Cart |
| **Objective** | Verify that users can add multiple different products in a single session |
| **Preconditions** | User is logged in |
| **Test Steps** | 1. Search for first product (AI) and add to cart with quantity 1<br>2. Return to homepage<br>3. Search for second product (Novel) and add to cart with quantity 2<br>4. Navigate to cart<br>5. Verify cart has at least one product |
| **Expected Result** | Cart contains products after adding multiple items |
---

### TC-007: Add Out of Stock Product to Cart
| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-007 |
| **Test Case Name** | Add Out of Stock Product to Cart |
| **Objective** | Verify that out of stock products cannot be added to cart |
| **Preconditions** | User is logged in |
| **Test Steps** | 1. Search for a product<br>2. Filter by "Currently Unavailability"<br>3. Select an unavailable product<br>4. Try to add to cart<br>5. Verify unavailable modal is displayed<br>6. Navigate to cart |
| **Expected Result** | Modal indicates product is unavailable and cart remains empty |
---

### TC-008: Decrease Quantity in Cart
| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-008 |
| **Test Case Name** | Decrease Quantity in Cart |
| **Objective** | Verify that decreasing product quantity correctly updates subtotal and total |
| **Preconditions** | User has a product in the cart with quantity 3 |
| **Test Steps** | 1. Add product with quantity 3<br>2. Navigate to cart<br>3. Get initial quantity (3) and total<br>4. Click decrease quantity button<br>5. Verify quantity is 2 and total decreased by product price |
| **Expected Result** | Quantity decreases to 2, total = initial total - product price |
---

### TC-009: Cart Persistence Between Sessions
| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-009 |
| **Test Case Name** | Cart Persistence Between Sessions |
| **Objective** | Verify that cart contents persist after user logs out and logs back in |
| **Preconditions** | User is logged in, Cart is empty |
| **Test Steps** | 1. Search for a product and add to cart with quantity 2<br>2. Verify cart contains the product with correct quantity and total<br>3. Hover over username and click logout<br>4. Verify user is logged out<br>5. Login again with same credentials<br>6. Navigate to cart and verify product, quantity, and total persist |
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
