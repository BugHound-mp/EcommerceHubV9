package pageObjects;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage extends BasePage {

    private static final By WISHLIST_ICON = By.xpath("//button[contains(@class,'wishlist') or contains(@class,'heart')] | //div//button//i[@class='fa-solid fa-heart']");
    private static final By SUCCESS_ALERT = By.xpath("//div[contains(@class,'alert-success') and contains(.,'Success')] | //div[contains(@class,'alert alert-success alert-dismissible')] ");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@id='input-option-225']")
    WebElement input_DeliveryDate;

    @FindBy(xpath = "//button[@id='button-cart']")
    WebElement btn_AddToCart;

    @FindBy(xpath = "//div[@class='alert alert-success alert-dismissible']")
    WebElement alert_Success;

    @FindBy(xpath = "//a[@title='Checkout']//i[@class='fa-solid fa-share']")
    WebElement link_Checkout;

    @FindBy(xpath = "//div//button//i[@class='fa-solid fa-heart']")
    WebElement wishlistIcon;

    @FindBy(xpath = "//div[@class='alert alert-success alert-dismissible']")
    WebElement successMessage;

    private WebDriverWait waitForPage() {
        return new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private void scrollToView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    public void addToWishlist() {
        WebDriverWait wait = waitForPage();
        WebElement wishlist = wait.until(ExpectedConditions.elementToBeClickable(WISHLIST_ICON));
        scrollToView(wishlist);
        wishlist.click();
    }

    public void setDeliveryDate() {
        LocalDate date = LocalDate.now().plusDays(5);
        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        scrollToView(input_DeliveryDate);
        input_DeliveryDate.clear();
        input_DeliveryDate.sendKeys(formattedDate);
    }

    public void clickAddToCart() throws InterruptedException {
        WebDriverWait wait = waitForPage();
        WebElement addToCart = wait.until(ExpectedConditions.elementToBeClickable(btn_AddToCart));
        scrollToView(addToCart);
        addToCart.click();
    }

    public boolean isSuccessMessageDisplayed() {
        WebDriverWait wait = waitForPage();
        WebElement success = wait.until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_ALERT));
        return success.getText().toLowerCase().contains("success");
    }

    public void clickCheckout() throws InterruptedException {
        WebDriverWait wait = waitForPage();
        WebElement checkoutLink = wait.until(ExpectedConditions.elementToBeClickable(link_Checkout));
        scrollToView(checkoutLink);
        checkoutLink.click();
    }
}