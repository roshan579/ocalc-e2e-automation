package com.calc.test.framework.hooks;

import com.calc.test.framework.context.ConfigurationDataContext;
import com.calc.test.framework.helpers.PropertyHelper;
import com.calc.test.pages.PageFactory;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class BrowserManagement {

    public static WebDriver driver;
    public static Scenario scenario;
    public static String downLoadDirectory;
    public static String applicationURL;


    @Before(order = 1)
    public static void readConfigurationDetails() {
        try {
            String host = System.getProperty("environment.host");
            System.out.println("HOST System property: " + host);
            if (StringUtils.isBlank(host)) {
                host = PropertyHelper.getProperty("local.host").toLowerCase();
                System.out.println("HOST local property: " + host);
            }
            applicationURL = host;
            System.out.println("App URL Generated:\n" + applicationURL);
            ConfigurationDataContext.applicationURL = applicationURL;
        } catch (Exception ex) {
            throw new Error("Host details are improper due to : " + ex.getMessage());
        }
    }

    @Before(value = {"~@NoBrowser"}, order = 2)
    public static void prepareBrowser() {
        try {
            downLoadDirectory = System.getProperty("user.dir") + "\\src\\test\\resources\\download";
            File downloadPath = new File(downLoadDirectory);
            if (downloadPath.exists()) {
                FileUtils.forceDelete(downloadPath);
                Thread.sleep(100);
                downloadPath.mkdir();
            } else {
                downloadPath.mkdir();
            }
            driver = getWebDriver();
            driver.navigate().to(applicationURL);
            System.out.println("App URL Opened in Browser:\n" + applicationURL);
            PageFactory.setDriver(driver);
            try {
                driver.manage().window().maximize();
            } catch (WebDriverException ex) {
                System.out.println("This version of driver cannot maximise the browser due to : " + ex.getMessage());
            }
            for (String winHandle : driver.getWindowHandles()) {
                driver.switchTo().window(winHandle);
            }
        } catch (Exception ex) {
            System.out.println("could not open browser due to : \n" + ex.getMessage());
        }
    }

    @After(value = {"~@NoBrowser"}, order = 101)
    public static void updateScreenShot(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
            }
        } catch (Exception ex) {
            System.out.println("Error in taking the screen shot due to : " + ex.getMessage());
        }
    }

    @After(value = {"~@NoBrowser"}, order = 99)
    public static void closeBrowser() {
        try {
            for (String winHandle : driver.getWindowHandles()) {
                driver.switchTo().window(winHandle).close();
            }
        } catch (Exception ex) {
            System.out.println("There was an error thrown while driver quit call \n" + ex.getMessage());
        }
    }


    public static WebDriver getWebDriver() throws Exception {
        WebDriver driver;
        String browserType = PropertyHelper.getProperty("BrowserType");
        if (browserType.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", PropertyHelper.getProperty("chromeDriver74"));
            ChromeOptions options = new ChromeOptions();
            options.addArguments("disable-extensions");
            options.addArguments("disable-infobars");
            options.addArguments("start-maximized");
            //options.addArguments("headless");
            options.setExperimentalOption("useAutomationExtension", false);
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
            chromePrefs.put("profile.default_content_settings.popups", 0);
            chromePrefs.put("download.default_directory", downLoadDirectory);
            options.setExperimentalOption("prefs", chromePrefs);
            driver = new ChromeDriver(options);
        } else if (browserType.equalsIgnoreCase("safari")) {
            driver = new SafariDriver();
        } else {
            throw new Exception("Driver type is not defined");
        }
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        return driver;
    }


}
