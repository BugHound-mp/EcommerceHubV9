package testCases;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC09_InvalidLogin extends BaseClass {

    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() {
        return new Object[][] {
            { "wronguser@mail.com", "wrongpassword" },
            { "mownika0304@gmail.com", "WrongPassword123" },
            { "invalid@domain.com", "" },
            { "", "Test123@" }
        };
    }

    @Test(
        groups = { "negative", "regression" },
        dataProvider = "invalidLoginData",
        retryAnalyzer = RetryAnalyzer.class
    )
    public void testInvalidLogin(String email, String password) {
        log.info("========== Invalid Login Test Started ==========");
        log.info("Attempting login with email='{}', password='{}'", email, password);

        try {
            HomePage homePage = new HomePage(getDriver());
            homePage.clickMyAccount();
            homePage.goToLogin();

            LoginPage loginPage = new LoginPage(getDriver());
            loginPage.setEmail(email);
            loginPage.setPwd(password);
            loginPage.clickLogin();

            boolean errorDisplayed = loginPage.isErrorMessageDisplayed();
            log.info("Login error banner displayed: {}", errorDisplayed);
            Assert.assertTrue(errorDisplayed, "Expected invalid login error message was not displayed.");

            String errorText = loginPage.getErrorMessageText();
            log.info("Login error message: {}", errorText);

            Assert.assertTrue(
                errorText.toLowerCase().contains("warning") ||
                errorText.toLowerCase().contains("no match") ||
                errorText.toLowerCase().contains("invalid") ||
                errorText.toLowerCase().contains("credentials") ||
                errorText.toLowerCase().contains("incorrect"),
                "Error text did not match the expected invalid login warning."
            );

            log.info("Invalid Login Test PASSED for email='{}'", email);

        } catch (AssertionError e) {
            log.error("Invalid Login Test assertion failed for email='{}'", email, e);
            throw e;
        } catch (Exception e) {
            log.error("Invalid Login Test failed for email='{}'", email, e);
            throw e;
        } finally {
            log.info("========== Invalid Login Test Finished ==========");
        }
    }
}
