package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegistrationPage extends BasePage {

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "input-firstname")
    private WebElement txtFirstName;

    @FindBy(id = "input-lastname")
    private WebElement txtLastName;

    @FindBy(id = "input-email")
    private WebElement txtEmail;

    @FindBy(id = "input-telephone")
    private WebElement txtTelephone;

    @FindBy(id = "input-password")
    private WebElement txtPassword;

    @FindBy(id = "input-confirm")
    private WebElement txtConfirmPassword;

    @FindBy(xpath = "//button[normalize-space()='Continue']")
    private WebElement btnContinue;

    @FindBy(xpath = "//*[contains(@class,'alert-success') or contains(text(),'Your Account Has Been Created')]")
    private WebElement successMessage;

    @FindBy(xpath = "//h1[normalize-space()='My Account']")
    private WebElement accountHeading;

    public void setFirstName(String value) {
        txtFirstName.clear();
        txtFirstName.sendKeys(value);
    }

    public void setLastName(String value) {
        txtLastName.clear();
        txtLastName.sendKeys(value);
    }

    public void setEmail(String value) {
        txtEmail.clear();
        txtEmail.sendKeys(value);
    }

    public void setTelephone(String value) {
        txtTelephone.clear();
        txtTelephone.sendKeys(value);
    }

    public void setPassword(String value) {
        txtPassword.clear();
        txtPassword.sendKeys(value);
    }

    public void setConfirmPassword(String value) {
        txtConfirmPassword.clear();
        txtConfirmPassword.sendKeys(value);
    }

    public void clickContinue() {
        btnContinue.click();
    }

    public boolean isRegistrationSuccessDisplayed() {
        return successMessage.isDisplayed();
    }

    public boolean isAccountPageDisplayed() {
        return accountHeading.isDisplayed();
    }
}
