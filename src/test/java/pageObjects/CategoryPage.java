package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CategoryPage extends BasePage {

    private final By laptopsLink = By.xpath("//a[normalize-space()='Laptops & Notebooks']");
    private final By showAllLink = By.xpath("//a[normalize-space()='Show All Laptops & Notebooks']");
    private final By hpProductLink = By.xpath("//a[normalize-space()='HP LP3065']");

    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    private WebDriverWait waitForPage() {
        return new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private void scrollToCenter(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", element);
    }

    private void clickWithRetry(By locator) {
        WebDriverWait wait = waitForPage();
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                scrollToCenter(element);
                element.click();
                return;
            } catch (ElementClickInterceptedException | StaleElementReferenceException |
                     NoSuchElementException e) {
                if (attempt == 3) {
                    throw e;
                }
                wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            } catch (ElementNotInteractableException e) {
                if (attempt == 3) {
                    throw e;
                }
                wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            }
        }
    }

    public void clickLaptopsAndNotebooks() {
        clickWithRetry(laptopsLink);
    }

    public void openLaptopCategoryPage(String appUrl) {
        driver.get(appUrl + "index.php?route=product/category&language=en-gb&path=18");
        waitForPage().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[normalize-space()='Laptops & Notebooks']")));
    }

    public void openHPProductPage(String appUrl) {
        driver.get(appUrl + "index.php?route=product/product&language=en-gb&product_id=47&path=18");
        waitForPage().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(.,'HP LP3065')] | //a[normalize-space()='HP LP3065']")));
    }

    public void clickShowAll() {
        clickWithRetry(showAllLink);
    }

    public void selectHPProduct() {
        WebDriverWait wait = waitForPage();
        By productLocator = hpProductLink;

        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(productLocator));
                WebElement product = wait.until(ExpectedConditions.elementToBeClickable(productLocator));
                scrollToCenter(product);
                product.click();
                return;
            } catch (ElementClickInterceptedException | StaleElementReferenceException |
                     NoSuchElementException e) {
                if (attempt == 3) {
                    throw e;
                }
            } catch (ElementNotInteractableException e) {
                if (attempt == 3) {
                    throw e;
                }
            } catch (TimeoutException e) {
                if (attempt == 3) {
                    throw e;
                }
            }
        }
    }
}