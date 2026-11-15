package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AffiliatePage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC08_AffiliateValidation extends BaseClass {

    @Test(
        groups = { "validation", "regression" },
        retryAnalyzer = RetryAnalyzer.class
    )
    public void testAffiliateValidation() throws Exception {
        log.info("========== Affiliate Validation Test Started ==========");

        try {
            getDriver().get(p.getProperty("appURL"));

            HomePage homePage = new HomePage(getDriver());
            homePage.clickMyAccount();
            homePage.goToLogin();

            LoginPage loginPage = new LoginPage(getDriver());
            loginPage.setEmail("sid@cloudberry.services");
            loginPage.setPwd("Test123");
            loginPage.clickLogin();

            AffiliatePage affiliatePage = new AffiliatePage(getDriver());
            affiliatePage.navigateToAffiliateForm();
            affiliatePage.fillAffiliateDetails(
                    "CloudBerry",
                    "cloudberry.services",
                    "123456",
                    "Shadab Siddiqui"
            );

            boolean affiliateAdded = affiliatePage.isAffiliateAdded();
            log.info("Affiliate submission success status: {}", affiliateAdded);
            Assert.assertTrue(affiliateAdded, "Affiliate record was not added successfully.");

            log.info("Affiliate Validation Test PASSED");

        } catch (AssertionError e) {
            log.error("Affiliate Validation Test assertion failed", e);
            throw e;
        } catch (Exception e) {
            log.error("Affiliate Validation Test failed due to unexpected exception", e);
            throw e;
        } finally {
            log.info("========== Affiliate Validation Test Finished ==========");
        }
    }
}
