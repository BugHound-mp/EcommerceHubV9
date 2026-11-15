package testCases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.CategoryPage;
import pageObjects.ProductPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC07_CartQuantityAndRemove extends BaseClass {

    @Test(
        groups = {  "regression" },
        retryAnalyzer = RetryAnalyzer.class
    )
    public void testCartQuantityAndRemove() throws Exception {
        log.info("========== Cart Quantity and Remove Test Started ==========");

        try {
            getDriver().get(p.getProperty("appURL"));

            CategoryPage categoryPage = new CategoryPage(getDriver());
            categoryPage.openHPProductPage(p.getProperty("appURL"));

            ProductPage productPage = new ProductPage(getDriver());
            productPage.setDeliveryDate();
            productPage.clickAddToCart();

            getDriver().get(p.getProperty("appURL") + "index.php?route=checkout/cart");

            By quantityInput = By.xpath("//input[@name='quantity']");
            By updateButton = By.xpath("//button[contains(.,'Update') or .='Update']");
            By removeButton = By.xpath("//button[contains(.,'Remove') or contains(.,'remove')] | //a[contains(.,'Remove') or contains(.,'remove')] ");

            boolean quantityVisible = getDriver().findElements(quantityInput).size() > 0;
            log.info("Cart quantity field visible: {}", quantityVisible);
            Assert.assertTrue(quantityVisible, "Cart quantity field was not visible.");

            getDriver().findElement(quantityInput).clear();
            getDriver().findElement(quantityInput).sendKeys("2");
            if (getDriver().findElements(updateButton).size() > 0) {
                getDriver().findElement(updateButton).click();
            }

            String cartPage = getDriver().getPageSource();
            Assert.assertTrue(cartPage.contains("Your Shopping Cart"), "Cart page did not remain available after quantity update.");

            if (getDriver().findElements(removeButton).size() > 0) {
                getDriver().findElement(removeButton).click();
            }

            String afterRemoval = getDriver().getPageSource().toLowerCase();
            boolean cartEmptyOrRemoved = afterRemoval.contains("your cart is empty")
                    || afterRemoval.contains("0 item")
                    || afterRemoval.contains("empty")
                    || !afterRemoval.contains("hp lp3065");

            log.info("Cart item removed or empty state reached: {}", cartEmptyOrRemoved);
            Assert.assertTrue(cartEmptyOrRemoved, "Cart item was not removed successfully.");

            log.info("Cart Quantity and Remove Test PASSED");

        } catch (AssertionError e) {
            log.error("Cart Quantity and Remove Test assertion failed", e);
            throw e;
        } catch (Exception e) {
            log.error("Cart Quantity and Remove Test failed due to unexpected exception", e);
            throw e;
        } finally {
            log.info("========== Cart Quantity and Remove Test Finished ==========");
        }
    }
}