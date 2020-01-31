package com.calc.test.pages;

import com.calc.test.pages.calculator.CalculatorPage;
import org.openqa.selenium.WebDriver;

/**
 * Created by Roshan
 */
public class PageFactory {

    private static WebDriver driver;

    public static WebDriver getDriver() {
        return driver;
    }

    public static void setDriver(WebDriver driverInstance) {
        driver = driverInstance;
    }

    public static CalculatorPage getCalculatorPage() {
        return new CalculatorPage();
    }


}
