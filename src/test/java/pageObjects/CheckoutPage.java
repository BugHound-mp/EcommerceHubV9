package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage extends BasePage {

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//strong[normalize-space()='login page']")
    WebElement loginPageLink;

    @FindBy(id = "input-shipping-address")
    WebElement shippingAddressDropdown;

    @FindBy(id = "button-shipping-methods")
    WebElement shippingMethodsButton;

    @FindBy(id = "button-shipping-method")
    WebElement flatShippingButton;

    @FindBy(id = "button-payment-methods")
    WebElement paymentMethodsButton;

    @FindBy(id = "button-payment-method")
    WebElement codButton;

    @FindBy(xpath = "//div[@class='text-end']//button[contains(text(),'Confirm')]")
    WebElement confirmButton;

    private WebDriverWait waitForPage() {
        return new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private void clickWithFallback(WebElement element) {
        WebDriverWait wait = waitForPage();

        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (ElementClickInterceptedException | StaleElementReferenceException | NoSuchElementException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    public void clickLogin() {
        WebDriverWait wait = waitForPage();
        wait.until(ExpectedConditions.elementToBeClickable(loginPageLink)).click();
    }

    public void completeCheckout() {
        WebDriverWait wait = waitForPage();

        WebElement shippingAddress = wait.until(ExpectedConditions.visibilityOf(shippingAddressDropdown));
        new Select(shippingAddress).selectByIndex(1);

        wait.until(ExpectedConditions.elementToBeClickable(shippingMethodsButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(flatShippingButton)).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("modal-shipping")));

        WebElement paymentButton = wait.until(ExpectedConditions.elementToBeClickable(paymentMethodsButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", paymentButton);
        clickWithFallback(paymentButton);

        WebElement cod = wait.until(ExpectedConditions.elementToBeClickable(codButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", cod);
        clickWithFallback(cod);

        WebElement confirm = wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", confirm);
        clickWithFallback(confirm);
    }

    private void scroll(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}

