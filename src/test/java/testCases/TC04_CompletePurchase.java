package testCases;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.CategoryPage;
import pageObjects.CheckoutPage;
import pageObjects.ConfirmationPage;
import pageObjects.LoginPage;
import pageObjects.ProductPage;

import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC04_CompletePurchase extends BaseClass {

    @Test(
        groups = {"sanity", "regression"},
        retryAnalyzer = utilities.RetryAnalyzer.class
    )
    public void testCompletePurchase() {

        log.info("========== Complete Purchase Test Started ==========");

        try {

            // Step 1: Navigate to application home page
            log.debug("Navigating to application home page");

            getDriver().get(p.getProperty("appURL"));

            log.info("Application home page loaded successfully");

            // Step 2: Initialize CategoryPage
            log.debug("Initializing CategoryPage object");

            CategoryPage cp = new CategoryPage(getDriver());

            // Step 3: Navigate to the direct HP product page
            log.debug("Opening HP product page directly");

            cp.openHPProductPage(p.getProperty("appURL"));

            log.info("HP product page opened successfully");

            // Step 4: Set delivery date and add to cart
            log.debug("Setting product delivery date");

            ProductPage pp = new ProductPage(getDriver());
            pp.setDeliveryDate();

            log.info("Delivery date set successfully");

            // Step 5: Add Product to Cart
            log.debug("Clicking Add to Cart button");

            pp.clickAddToCart();

            log.info("Product added to cart");

            // Step 6: Proceed to Checkout
            log.debug("Clicking Checkout button");

            pp.clickCheckout();

            log.info("Navigated to Checkout page");

            // Step 7: Initialize CheckoutPage
            log.debug("Initializing CheckoutPage object");

            CheckoutPage cop = new CheckoutPage(getDriver());

            // Step 8: Select Login option
            log.debug("Selecting Login option at Checkout");

            cop.clickLogin();

            log.info("Login option selected");

            // Step 9: Initialize LoginPage
            log.debug("Initializing LoginPage object");

            LoginPage lp = new LoginPage(getDriver());

            // Step 10: Enter Login Credentials
            log.debug("Entering email address");

            lp.setEmail("sid@cloudberry.services");

            log.info("Email entered successfully");

            log.debug("Entering password");

            lp.setPwd("Test123");

            log.info("Password entered successfully");

            // Step 11: Login
            log.debug("Clicking Login button");

            lp.clickLogin();

            log.info("Login completed");

            // Step 12: Complete Checkout
            log.debug("Completing checkout process");

            cop.completeCheckout();

            log.info("Checkout process completed");

            // Step 13: Verify Order Confirmation
            log.debug("Initializing ConfirmationPage object");

            ConfirmationPage confirmationPage =
                    new ConfirmationPage(getDriver());

            log.debug("Verifying order placement confirmation");

            try {

                boolean orderStatus =
                        confirmationPage.isOrderPlaced();

                log.debug(
                    "Order placement confirmation status: {}",
                    orderStatus
                );

                Assert.assertTrue(
                    orderStatus,
                    "Order placement failed!"
                );

                log.info("Order placement verification PASSED");

            } catch (AssertionError e) {

                log.error(
                    "Order placement assertion FAILED",
                    e
                );

                // Rethrow to trigger RetryAnalyzer
                throw e;
            }

            log.info("Complete Purchase Test PASSED");

        } catch (AssertionError e) {

            // Assertion failure already logged.
            // Rethrow so TestNG can invoke RetryAnalyzer.
            throw e;

        } catch (InterruptedException e) {

            log.error(
                "Complete Purchase Test interrupted",
                e
            );

            Thread.currentThread().interrupt();

            throw new RuntimeException(
                "Test execution interrupted", e
            );

        } catch (Exception e) {

            log.error(
                "Complete Purchase Test FAILED due to exception",
                e
            );

            throw e;

        } finally {

            log.info(
                "========== Complete Purchase Test Finished =========="
            );
        }
    }
}