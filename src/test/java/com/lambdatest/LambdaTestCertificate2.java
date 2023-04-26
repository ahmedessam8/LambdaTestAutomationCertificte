package com.lambdatest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LambdaTestCertificate2 {
    public String username = "ahibrahim";
    public String accesskey = "iRkBh11YgpK93GeSru5Tg5pZjMEp20U18ZvAwtQvDKBanOIm9H";
    public static RemoteWebDriver driver = null;
    public String gridURL = "@hub.lambdatest.com/wd/hub";
    boolean status = false;
//    private RemoteWebDriver driver;
    private String Status = "Pass";

    By integrations = By.cssSelector("[class='text-center mt-25'] [href]");
    By getStartedBtn = By.linkText("Get Started");
    By LTIntegrationsPageTitle =By.xpath("//h1[contains(text(),'LambdaTest Integrations')]");


    @BeforeMethod
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "Microsoft Edge");
        capabilities.setCapability("version", "87.0 ");
        capabilities.setCapability("platform", "macOS Sierra"); // If this cap isn't specified, it will just get the any available one
        capabilities.setCapability("build", "LambdaTestSampleApp");
        capabilities.setCapability("name", "LambdaTestJavaSample");
        try {
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL), capabilities);
        } catch (MalformedURLException e) {
            System.out.println("Invalid grid URL");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void basicTest() throws InterruptedException {
        String spanText;
        System.out.println("Loading Url");

        driver.get("https://www.lambdatest.com");
        WebDriverWait wait = new WebDriverWait(driver,5);
            wait.until(ExpectedConditions.visibilityOfElementLocated(getStartedBtn));
        JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,700)", "");
        WebElement link =driver.findElement(integrations);
        js.executeScript("arguments[0].target='_blank'; arguments[0].click();", link);
        // Save the window handles in a List (or array)
        List<String> windowHandles = new ArrayList<String>(driver.getWindowHandles());

        // Print the window handles of the opened windows (now there are two windows open)
        System.out.println("Window Handles: " + windowHandles);

        // Verify whether the URL is the same as the expected URL (if not, throw an Assert)
        String expectedUrl = "https://www.lambdatest.com/";
        for (String windowHandle : windowHandles) {
            String actualUrl = driver.getCurrentUrl();
            if (!actualUrl.equals(expectedUrl)) {
                throw new AssertionError("Expected URL: " + expectedUrl + ", but found: " + actualUrl);
            }
        }
        driver.close();


    }

    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }

}