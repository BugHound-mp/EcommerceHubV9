package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@id='input-email']")
    private WebElement txtEmail;

    @FindBy(xpath = "//input[@id='input-password']")
    private WebElement txtPassword;

    @FindBy(xpath = "//button[normalize-space()='Login']")
    private WebElement btnLogin;

    @FindBy(xpath = "//a[contains(.,'Register')]")
    private WebElement linkRegister;

    @FindBy(xpath = "//*[contains(@class,'alert-danger') or contains(@class,'alert-warning') or contains(@class,'alert')][not(contains(@class,'alert-success'))]")
    private WebElement alertError;

    public void setEmail(String email) {
        txtEmail.clear();
        txtEmail.sendKeys(email);
    }

    public void setPwd(String pwd) {
        txtPassword.clear();
        txtPassword.sendKeys(pwd);
    }

    public void clickLogin() {
        btnLogin.click();
    }

    public void clickRegisterLink() {
        linkRegister.click();
    }

    public boolean isErrorMessageDisplayed() {
        return alertError.isDisplayed();
    }

    public String getErrorMessageText() {
        return alertError.getText();
    }

    public boolean isLoginPageDisplayed() {
        return txtEmail.isDisplayed() && txtPassword.isDisplayed();
    }
}