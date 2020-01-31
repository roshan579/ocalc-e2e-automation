package com.calc.test.pages;

import com.calc.test.framework.helpers.WaitHelper;
import com.calc.test.framework.helpers.WebElementHelper;
import com.calc.test.framework.hooks.BrowserManagement;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Roshan
 */
public class BasePage {

    private static final String BASE_PATH = "src/test/java/";
    public Properties properties;

    public BasePage() {
        try {
            String filename = this.getClass().getName().replaceAll("\\.", "/") + ".properties";
            properties = new Properties();
            FileInputStream input = new FileInputStream(BASE_PATH + filename);
            properties.load(input);
            String baseFileName = BasePage.class.getName().replaceAll("\\.", "/") + ".properties";
            FileInputStream baseInput = new FileInputStream(BASE_PATH + baseFileName);
            properties.load(baseInput);
        } catch (IOException ioe) {
            ioe.getMessage();
        }
    }

    public static void setAttribute(WebElement element, String attributeName, String attValue) throws Exception {
        if (PageFactory.getDriver() instanceof JavascriptExecutor) {
            try {
                ((JavascriptExecutor) PageFactory.getDriver()).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
                        element, attributeName, attValue);
            } catch (Exception ex) {
                throw new Exception("Could not set attribute " + attributeName + " value for element : " + element + " and value : " + attValue +
                        " bcoz of : " + ex.getMessage());
            }
        }
    }

    public static void setAttributeValue(WebElement element, String value) throws Exception {
        if (PageFactory.getDriver() instanceof JavascriptExecutor) {
            try {
                ((JavascriptExecutor) PageFactory.getDriver()).executeScript("arguments[0].value=arguments[1];",
                        element, value);
            } catch (Exception ex) {
                throw new Exception("Could not set property value for element : " + element + " and value : " + value +
                        " bcoz of : " + ex.getMessage());
            }
        }
    }

    public String getProperty(String name) {
        return properties.getProperty(name);
    }

    public void loadPage() throws Exception {
        int count = 0;
        boolean loaded = false;
        do {
            try {
                WaitHelper.waitUntilExists(this, "main_page_iframe");
                WaitHelper.waitUntilVisible(getElement("main_page_iframe"));
                PageFactory.getDriver().switchTo().frame(getProperty("main_page_iframe"));
                WaitHelper.waitUntilExists(this, "canvas_element");
                WaitHelper.waitUntilVisible(getElement("canvas_element"));
                String styleValue = WebElementHelper.getAttributeValue(getElement("canvas_element"), "style");
                if (styleValue.contains("position: absolute; display: block;")) {
                    loaded = true;
                } else {
                    loaded = false;
                }
            } catch (Exception ex) {
                System.out.println("Loading Online Calculator launch page took more time due to : " + ex.getMessage());
                BrowserManagement.closeBrowser();
                BrowserManagement.prepareBrowser();
            }
            count++;
        } while (!loaded && count < WaitHelper.WEBELEMENT_DEFAULT_INPUT_TRY_COUNT);
    }

    public void clickElement(String element, boolean disappear) throws Exception {
        int seconds = WaitHelper.WEBELEMENT_DEFAULT_TIMEOUT;
        long time = 1000 * seconds;
        boolean timeout = false;
        while (!timeout && time > 0) {
            try {
                Thread.sleep(100);
                getElementWithoutWait(element).click();
                Thread.sleep(100);
                if (disappear && isElementStillPresent(element)) {
                    throw new Exception("Will try to click again");
                }
                timeout = true;
            } catch (Exception ex) {
                if (disappear && !isElementStillPresent(element)) {
                    timeout = true;
                } else {
                    timeout = false;
                    Thread.sleep(100);
                    time = time - 100;
                }
            }
        }
        if (!timeout) {
            throw new Exception("Element not clickable at the moment : " + element);
        }
        WaitHelper.waitFor(1000);
    }

    public boolean isElementStillPresent(String element) {
        try {
            WebElement present = getWebElement(element, false);
            return (present != null);
        } catch (Exception ex) {
            return false;
        }
    }

    public WebElement getElement(String name) throws Exception {
        String[] property = getElementProperty(name);
        WebElement element;
        try {
            element = getWebElement(property[0], property[1], true);
            if (element == null) {
                throw new Exception(String.format("Element %s is null : \n", name));
            }
        } catch (StaleElementReferenceException sfe) {
            element = getWebElement(property[0], property[1], true);
            if (element == null) {
                throw new Exception(String.format("Element %s is null : \n", name));
            }
        } catch (Exception ex) {
            throw new Exception("Element is not found in the page : " + name);
        }
        return element;
    }

    public WebElement getElementWithoutWait(String name) throws Exception {
        String[] property = getElementProperty(name);
        WebElement element;
        try {
            element = getWebElement(property[0], property[1], false);
            if (element == null) {
                throw new Exception(String.format("Element %s is null : \n", name));
            }
        } catch (StaleElementReferenceException sfe) {
            element = getWebElement(property[0], property[1], false);
            if (element == null) {
                throw new Exception(String.format("Element %s is null : \n", name));
            }
        } catch (Exception ex) {
            throw new Exception(String.format("Element is not found in the page : \n", name) + ex.getMessage());
        }
        return element;
    }

    public String[] getElementProperty(String name) throws Exception {
        String element[] = new String[2];
        element[0] = getProperty(name);
        element[1] = getProperty(name + "Type");
        if (element[0] != null && element[1] == null) {
            throw new Exception("Element type cannot be must be provided in the page properties file");
        }
        return element;
    }

    public WebElement getElement(WebElement parent, String name) throws Exception {
        String elementName = getProperty(name);
        String type = getProperty(name + "Type");
        WebElement element;
        if (StringUtils.isBlank(elementName) && StringUtils.isBlank(type)) {
            throw new Exception("Element type cannot be must be provided in the page properties file");
        }
        try {
            element = getWebElement(parent, elementName, type, true);
            if (element == null) {
                throw new Exception(String.format("Element %s is null : \n", name));
            }
        } catch (StaleElementReferenceException sfe) {
            element = getWebElement(parent, elementName, type, true);
            if (element == null) {
                throw new Exception(String.format("Element %s is null : \n", name));
            }
        } catch (Exception ex) {
            throw new Exception("Element is not found in the page : " + name);
        }
        return element;
    }

    public WebElement getElement(WebElement parent, String elementName, String type) throws Exception {
        WebElement element;
        if (StringUtils.isBlank(elementName) && StringUtils.isBlank(type)) {
            throw new Exception("Element type cannot be must be provided in the page properties file");
        }
        try {
            element = getWebElement(parent, elementName, type, true);
            if (element == null) {
                throw new Exception("element is null" + elementName);
            }
        } catch (StaleElementReferenceException sfe) {
            element = getWebElement(parent, elementName, type, true);
            if (element == null) {
                throw new Exception("element is null" + elementName);
            }
        } catch (Exception ex) {
            throw new Exception("Element is not found in the page : " + elementName);
        }
        return element;
    }

    public WebElement getElement(String elementName, String type) throws Exception {
        WebElement element;
        if (StringUtils.isBlank(elementName) && StringUtils.isBlank(type)) {
            throw new Exception("Element type cannot be must be provided in the page properties file");
        }
        try {
            element = getWebElement(elementName, type, true);
        } catch (StaleElementReferenceException sfe) {
            element = getWebElement(elementName, type, true);
        }
        return element;
    }

    public List<WebElement> getElements(String name) throws Exception {
        String elementName = getProperty(name);
        String type = getProperty(name + "Type");
        List<WebElement> elementList = new ArrayList<WebElement>();
        try {
            elementList = getWebElements(elementName, type, true);
            if (elementList.size() == 0) {
                throw new Exception("element list size is 0" + name);
            }
        } catch (StaleElementReferenceException sfe) {
            elementList = getWebElements(elementName, type, true);
            if (elementList.size() == 0) {
                throw new Exception("element list size is 0" + name);
            }
        } catch (Exception ex) {
            throw new Exception(String.format("Element %s is not found in the page : ", name));
        }
        return elementList;
    }

    public List<WebElement> getElements(WebElement parent, String name) throws Exception {
        String[] element = getElementProperty(name);
        List<WebElement> elementList;
        try {
            elementList = getWebElements(parent, element[0], element[1], true);
            if (elementList.size() == 0) {
                throw new Exception("element list size is 0 " + name);
            }
        } catch (StaleElementReferenceException sfe) {
            elementList = getWebElements(parent, element[0], element[1], true);
            if (elementList.size() == 0) {
                throw new Exception("element list size is 0 " + name);
            }
        } catch (Exception ex) {
            throw new Exception(String.format("Element %s is not found in the page due to : " + ex.getMessage(), name));
        }
        return elementList;
    }

    public WebElement getWebElement(String property, boolean wait) throws Exception {
        String elementName = getProperty(property);
        String type = getProperty(property + "Type");
        WebElement element = null;
        int time = 1000 * WaitHelper.WEBELEMENT_DEFAULT_TIMEOUT;
        boolean timeout = false;
        while (!timeout && time > 0) {
            try {
                if (type.equalsIgnoreCase("id")) {
                    element = PageFactory.getDriver().findElement(By.id(elementName));
                } else if (type.equalsIgnoreCase("css")) {
                    element = PageFactory.getDriver().findElement(By.cssSelector(elementName));
                } else if (type.equalsIgnoreCase("class")) {
                    element = PageFactory.getDriver().findElement(By.className(elementName));
                } else if (type.equalsIgnoreCase("partialLink")) {
                    element = PageFactory.getDriver().findElement(By.partialLinkText(elementName));
                } else if (type.equalsIgnoreCase("xpath")) {
                    element = PageFactory.getDriver().findElement(By.xpath(elementName));
                } else if (type.equalsIgnoreCase("name")) {
                    element = PageFactory.getDriver().findElement(By.name(elementName));
                } else if (type.equalsIgnoreCase("tagname")) {
                    element = PageFactory.getDriver().findElement(By.tagName(elementName));
                } else {
                    throw new Exception(String.format("Element type %s is not supported at the moment : ", type));
                }
                timeout = true;
            } catch (Exception ex) {
                if (!wait) {
                    timeout = true;
                } else {
                    Thread.sleep(100);
                    time = time - 100;
                }
            }
        }
        if (!timeout) {
            throw new Exception("Element not available at the moment : " + elementName);
        }
        return element;
    }

    public WebElement getWebElement(String elementName, String type, boolean wait) throws Exception {
        WebElement element = null;
        int time = 1000 * WaitHelper.WEBELEMENT_DEFAULT_TIMEOUT;
        boolean timeout = false;
        while (!timeout && time > 0) {
            try {
                if (type.equalsIgnoreCase("id")) {
                    element = PageFactory.getDriver().findElement(By.id(elementName));
                } else if (type.equalsIgnoreCase("css")) {
                    element = PageFactory.getDriver().findElement(By.cssSelector(elementName));
                } else if (type.equalsIgnoreCase("class")) {
                    element = PageFactory.getDriver().findElement(By.className(elementName));
                } else if (type.equalsIgnoreCase("partialLink")) {
                    element = PageFactory.getDriver().findElement(By.partialLinkText(elementName));
                } else if (type.equalsIgnoreCase("xpath")) {
                    element = PageFactory.getDriver().findElement(By.xpath(elementName));
                } else if (type.equalsIgnoreCase("name")) {
                    element = PageFactory.getDriver().findElement(By.name(elementName));
                } else if (type.equalsIgnoreCase("tagname")) {
                    element = PageFactory.getDriver().findElement(By.tagName(elementName));
                } else {
                    throw new Exception(String.format("Element type %s is not supported at the moment : ", type));
                }
                timeout = true;
            } catch (Exception ex) {
                if (!wait) {
                    timeout = true;
                } else {
                    Thread.sleep(100);
                    time = time - 100;
                }
            }
        }
        if (!timeout) {
            throw new Exception("Element not available at the moment : " + elementName);
        }
        return element;
    }

    public WebElement getWebElement(WebElement parent, String property, boolean wait) throws Exception {
        String elementName = getProperty(property);
        String type = getProperty(property + "Type");
        if (parent == null) {
            return getWebElement(property, wait);
        }
        WebElement element = null;

        int time = 1000 * WaitHelper.WEBELEMENT_DEFAULT_TIMEOUT;
        boolean timeout = false;
        while (!timeout && time > 0) {
            try {
                if (type.equalsIgnoreCase("id")) {
                    element = parent.findElement(By.id(elementName));
                } else if (type.equalsIgnoreCase("css")) {
                    element = parent.findElement(By.cssSelector(elementName));
                } else if (type.equalsIgnoreCase("class")) {
                    element = parent.findElement(By.className(elementName));
                } else if (type.equalsIgnoreCase("partialLink")) {
                    element = parent.findElement(By.partialLinkText(elementName));
                } else if (type.equalsIgnoreCase("xpath")) {
                    element = parent.findElement(By.xpath(elementName));
                } else if (type.equalsIgnoreCase("name")) {
                    element = parent.findElement(By.name(elementName));
                } else if (type.equalsIgnoreCase("tagname")) {
                    element = parent.findElement(By.tagName(elementName));
                } else {
                    throw new Exception(String.format("Element type %s is not supported at the moment : ", type));
                }
                timeout = true;
            } catch (Exception ex) {
                if (!wait) {
                    timeout = true;
                } else {
                    Thread.sleep(100);
                    time = time - 100;
                }
            }
        }
        if (!timeout) {
            throw new Exception("Element not available at the moment : " + elementName);
        }
        return element;
    }

    public WebElement getWebElement(WebElement parent, String elementName, String type, boolean wait) throws Exception {
        if (parent == null) {
            return getWebElement(elementName, type, wait);
        }
        WebElement element = null;

        int time = 1000 * WaitHelper.WEBELEMENT_DEFAULT_TIMEOUT;
        boolean timeout = false;
        while (!timeout && time > 0) {
            try {
                if (type.equalsIgnoreCase("id")) {
                    element = parent.findElement(By.id(elementName));
                } else if (type.equalsIgnoreCase("css")) {
                    element = parent.findElement(By.cssSelector(elementName));
                } else if (type.equalsIgnoreCase("class")) {
                    element = parent.findElement(By.className(elementName));
                } else if (type.equalsIgnoreCase("partialLink")) {
                    element = parent.findElement(By.partialLinkText(elementName));
                } else if (type.equalsIgnoreCase("xpath")) {
                    element = parent.findElement(By.xpath(elementName));
                } else if (type.equalsIgnoreCase("name")) {
                    element = parent.findElement(By.name(elementName));
                } else if (type.equalsIgnoreCase("tagname")) {
                    element = parent.findElement(By.tagName(elementName));
                } else {
                    throw new Exception(String.format("Element type %s is not supported at the moment : ", type));
                }
                timeout = true;
            } catch (Exception ex) {
                if (!wait) {
                    timeout = true;
                } else {
                    Thread.sleep(100);
                    time = time - 100;
                }
            }
        }
        if (!timeout) {
            throw new Exception("Element not available at the moment : " + elementName);
        }
        return element;
    }

    public List<WebElement> getWebElements(String property, boolean wait) throws Exception {
        String locator = getProperty(property);
        String type = getProperty(property + "Type");
        int seconds = WaitHelper.WEBELEMENT_DEFAULT_TIMEOUT;
        long time = 1000 * seconds;
        boolean timeout = false;
        List<WebElement> elementList = new ArrayList<WebElement>();
        while (!timeout && time > 0) {
            try {
                if (type.equalsIgnoreCase("id")) {
                    elementList = PageFactory.getDriver().findElements(By.id(locator));
                } else if (type.equalsIgnoreCase("css")) {
                    elementList = PageFactory.getDriver().findElements(By.cssSelector(locator));
                } else if (type.equalsIgnoreCase("class")) {
                    elementList = PageFactory.getDriver().findElements(By.className(locator));
                } else if (type.equalsIgnoreCase("partialLink")) {
                    elementList = PageFactory.getDriver().findElements(By.partialLinkText(locator));
                } else if (type.equalsIgnoreCase("xpath")) {
                    elementList = PageFactory.getDriver().findElements(By.xpath(locator));
                } else if (type.equalsIgnoreCase("name")) {
                    elementList = PageFactory.getDriver().findElements(By.name(locator));
                } else if (type.equalsIgnoreCase("tagname")) {
                    elementList = PageFactory.getDriver().findElements(By.tagName(locator));
                } else {
                    throw new Exception(String.format("Element type %s is not supported at the moment : ", type));
                }
                if (elementList.isEmpty()) {
                    if (!wait) {
                        timeout = true;
                    } else {
                        Thread.sleep(100);
                        time = time - 100;
                    }
                } else {
                    timeout = true;
                }
            } catch (WebDriverException e) {
                if (!wait) {
                    timeout = true;
                } else {
                    Thread.sleep(100);
                    time = time - 100;
                }
            }
        }
        if (!timeout) {
            throw new Exception(String.format("Element %s could not be found in the page : ", locator));
        }
        return elementList;

    }

    public List<WebElement> getWebElements(String locator, String type, boolean wait) throws Exception {
        int seconds = WaitHelper.WEBELEMENT_DEFAULT_TIMEOUT;
        long time = 1000 * seconds;
        boolean timeout = false;
        List<WebElement> elementList = new ArrayList<WebElement>();
        while (!timeout && time > 0) {
            try {
                if (type.equalsIgnoreCase("id")) {
                    elementList = PageFactory.getDriver().findElements(By.id(locator));
                } else if (type.equalsIgnoreCase("css")) {
                    elementList = PageFactory.getDriver().findElements(By.cssSelector(locator));
                } else if (type.equalsIgnoreCase("class")) {
                    elementList = PageFactory.getDriver().findElements(By.className(locator));
                } else if (type.equalsIgnoreCase("partialLink")) {
                    elementList = PageFactory.getDriver().findElements(By.partialLinkText(locator));
                } else if (type.equalsIgnoreCase("xpath")) {
                    elementList = PageFactory.getDriver().findElements(By.xpath(locator));
                } else if (type.equalsIgnoreCase("name")) {
                    elementList = PageFactory.getDriver().findElements(By.name(locator));
                } else if (type.equalsIgnoreCase("tagname")) {
                    elementList = PageFactory.getDriver().findElements(By.tagName(locator));
                } else {
                    throw new Exception(String.format("Element type %s is not supported at the moment : ", type));
                }
                if (elementList.isEmpty()) {
                    if (!wait) {
                        timeout = true;
                    } else {
                        Thread.sleep(100);
                        time = time - 100;
                    }
                } else {
                    timeout = true;
                }
            } catch (WebDriverException e) {
                if (!wait) {
                    timeout = true;
                } else {
                    Thread.sleep(100);
                    time = time - 100;
                }
            }
        }
        if (!timeout) {
            throw new Exception(String.format("Element %s could not be found in the page : ", locator));
        }
        return elementList;

    }

    public List<WebElement> getWebElements(WebElement parent, String property, boolean wait) throws Exception {
        String locator = getProperty(property);
        String type = getProperty(property + "Type");
        int seconds = WaitHelper.WEBELEMENT_DEFAULT_TIMEOUT;
        long time = 1000 * seconds;
        boolean timeout = false;
        List<WebElement> elementList = new ArrayList<WebElement>();
        while (!timeout && time > 0) {
            try {
                if (type.equalsIgnoreCase("id")) {
                    elementList = parent.findElements(By.id(locator));
                } else if (type.equalsIgnoreCase("css")) {
                    elementList = parent.findElements(By.cssSelector(locator));
                } else if (type.equalsIgnoreCase("class")) {
                    elementList = parent.findElements(By.className(locator));
                } else if (type.equalsIgnoreCase("partialLink")) {
                    elementList = parent.findElements(By.partialLinkText(locator));
                } else if (type.equalsIgnoreCase("xpath")) {
                    elementList = parent.findElements(By.xpath(locator));
                } else if (type.equalsIgnoreCase("name")) {
                    elementList = parent.findElements(By.name(locator));
                } else if (type.equalsIgnoreCase("tagname")) {
                    elementList = parent.findElements(By.tagName(locator));
                } else {
                    throw new Exception(String.format("Element type %s is not supported at the moment : ", type));
                }
                if (elementList.isEmpty()) {
                    if (!wait) {
                        timeout = true;
                    } else {
                        Thread.sleep(100);
                        time = time - 100;
                    }
                } else {
                    timeout = true;
                }
            } catch (WebDriverException e) {
                if (!wait) {
                    timeout = true;
                } else {
                    Thread.sleep(100);
                    time = time - 100;
                }
            }
        }
        if (!timeout) {
            throw new Exception(String.format("Element %s could not be found in the page : ", locator));
        }
        return elementList;

    }

    public List<WebElement> getWebElements(WebElement parent, String locator, String type, boolean wait)
            throws Exception {
        int seconds = WaitHelper.WEBELEMENT_DEFAULT_TIMEOUT;
        long time = 1000 * seconds;
        boolean timeout = false;
        List<WebElement> elementList = new ArrayList<WebElement>();
        while (!timeout && time > 0) {
            try {
                if (type.equalsIgnoreCase("id")) {
                    elementList = parent.findElements(By.id(locator));
                } else if (type.equalsIgnoreCase("css")) {
                    elementList = parent.findElements(By.cssSelector(locator));
                } else if (type.equalsIgnoreCase("class")) {
                    elementList = parent.findElements(By.className(locator));
                } else if (type.equalsIgnoreCase("partialLink")) {
                    elementList = parent.findElements(By.partialLinkText(locator));
                } else if (type.equalsIgnoreCase("xpath")) {
                    elementList = parent.findElements(By.xpath(locator));
                } else if (type.equalsIgnoreCase("name")) {
                    elementList = parent.findElements(By.name(locator));
                } else if (type.equalsIgnoreCase("tagname")) {
                    elementList = parent.findElements(By.tagName(locator));
                } else {
                    throw new Exception(String.format("Element type %s is not supported at the moment : ", type));
                }
                if (elementList.isEmpty()) {
                    if (!wait) {
                        timeout = true;
                    } else {
                        Thread.sleep(100);
                        time = time - 100;
                    }
                } else {
                    timeout = true;
                }
            } catch (WebDriverException e) {
                if (!wait) {
                    timeout = true;
                } else {
                    Thread.sleep(100);
                    time = time - 100;
                }
            }
        }
        if (!timeout) {
            throw new Exception(String.format("Element %s could not be found in the page : ", locator));
        }
        return elementList;

    }

    public void moveFocusOnElement(WebElement element) {
        try {
            new Actions(PageFactory.getDriver()).moveToElement(element).perform();
        } catch (Exception ex) {
            ex.getMessage();
        }
    }


}

