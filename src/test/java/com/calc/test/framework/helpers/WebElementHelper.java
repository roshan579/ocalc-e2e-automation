package com.calc.test.framework.helpers;

import com.calc.test.pages.PageFactory;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import static org.openqa.selenium.Keys.ENTER;
import static org.openqa.selenium.Keys.TAB;

/**
 * Created by Roshan
 */
public class WebElementHelper {

    public static final int WEBELEMENT_DEFAULT_TIMEOUT = 20;

    public static boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception ex) {
            return false;
        }
    }
    public static boolean isEnabled(WebElement webElement) {
      if (webElement != null) {
          String disabled = webElement.getAttribute("disabled");
          if (disabled == null) {
              disabled = webElement.getAttribute("aria-disabled");
              if (disabled == null) {
                  return true;
              } else {
                  return !disabled.equalsIgnoreCase("true");
              }
          } else {
              return !(disabled.equalsIgnoreCase("disabled") || disabled.equalsIgnoreCase("true"));
          }
      }
      return false;
  }

  public static boolean isHidden(WebElement webElement) {
      if (webElement != null) {
          String hidden = webElement.getAttribute("hidden");
          if (hidden == null) {
              hidden = webElement.getAttribute("aria-hidden");
              if (hidden == null) {
                  return false;
              } else {
                  return hidden.equalsIgnoreCase("true");
              }
          } else {
              return (hidden.equalsIgnoreCase("hidden") || hidden.equalsIgnoreCase("true"));
          }
      }
      return false;
  }
    public static void enterText(WebElement element, String value) throws Exception {
        clickElement(element);
        element.clear();
        element.sendKeys(value);
        element.sendKeys(TAB);
    }
    public static void enterTextWithEnter(WebElement element, String value) throws Exception {
        clickElement(element);
        element.clear();
        element.sendKeys(value);
        element.sendKeys(ENTER);
    }

    public static void enterTextWithOutTab(WebElement element, String value) throws Exception {
        clickElement(element);
        element.clear();
        element.sendKeys(value);
    }


    public static void clickElement(WebElement element) throws Exception {
        int seconds = WEBELEMENT_DEFAULT_TIMEOUT;
        long time = 1000 * seconds;
        boolean timeout = false;
        while (!timeout && time > 0) {
            try {
                element.click();
                timeout = true;
                Thread.sleep(100);
            } catch (Exception ex) {
                timeout = false;
                Thread.sleep(100);
                time = time - 100;
            }
        }
        if (!timeout) {
            throw new Exception("Element not clickable at the moment");
        }
    }

    public static String getElementValue(WebElement element) throws Exception {
        WaitHelper.waitUntilVisible(element);
        return getText(element);
    }

    public static String getElementValue2(WebElement element) throws Exception {
        WaitHelper.waitUntilVisible(element);
        return element.getAttribute("value");
    }

    public static String getElementValueWithoutWait(WebElement element) throws Exception {
        return element.getText();
    }

    public static String getAttributeValue(WebElement element, String attribute) throws Exception {
        WaitHelper.waitUntilVisible(element);
        return element.getAttribute(attribute);
    }

    public static void selectByValue(WebElement element, String value) throws Exception {
        WaitHelper.waitUntilVisible(element);
        Select dropDown = (Select) element;
        dropDown.selectByValue(value);
        WaitHelper.waitForJStoLoad();
    }

    public static void selectByIndex(WebElement element, int index) throws Exception {
        WaitHelper.waitUntilVisible(element);
        Select dropDown = (Select) element;
        dropDown.selectByIndex(index);
        WaitHelper.waitForJStoLoad();
    }

    public static void clickWithJquery(String cssPath) {
        JavascriptExecutor jse = (JavascriptExecutor) PageFactory.getDriver();
        jse.executeScript("document.querySelector('" + cssPath + "').click();");
    }

    public static boolean isElementEnabled(WebElement element) {
        try {
            return element.isEnabled();
        } catch (Exception ex) {
            return false;
        }
    }

    public static String getInnerTextIntValue(WebElement element) throws Exception {
        int seconds = WEBELEMENT_DEFAULT_TIMEOUT;
        long time = 1000 * seconds;
        boolean timeout = false;
        String text = null;
        while (!timeout && time > 0) {
            try {
                text = element.getAttribute("innerText");
                if (!StringUtils.isEmpty(text)) {
                    Thread.sleep(100);
                    timeout = true;
                }
            } catch (Exception ex) {
                timeout = false;
                Thread.sleep(100);
            }
            time = time - 100;
        }
        if (!timeout) {
            throw new Exception("Failed to retrieve inner text from the element : "+element);
        }
        return text.replaceAll("[^\\d]", "");
    }

    public static String getText(WebElement element) throws Exception {
        int seconds = WEBELEMENT_DEFAULT_TIMEOUT;
        long time = 1000 * seconds;
        boolean timeout = false;
        String text = null;
        while (!timeout && time > 0) {
            try {
                text = element.getText();
                if (!StringUtils.isEmpty(text)) {
                    Thread.sleep(100);
                    timeout = true;
                }
            } catch (Exception ex) {
                timeout = false;
                Thread.sleep(100);
            }
            time = time - 100;
        }
        if (!timeout) {
            throw new Exception("Failed to retrieve text from the element : "+element);
        }
        return text;
    }

    public static String getText(WebElement element, int seconds) throws Exception {
        long time = 1000 * seconds;
        boolean timeout = false;
        String text = "";
        while (!timeout && time > 0) {
            try {
                text = element.getText();
                if (!StringUtils.isEmpty(text)) {
                    Thread.sleep(100);
                    timeout = true;
                }
            } catch (Exception ex) {
                timeout = false;
                Thread.sleep(100);
            }
            time = time - 100;
        }
        return text;
    }

    public static void scrollDown() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) PageFactory.getDriver();
        js.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");
}

  public static void refreshPage() {
    WebDriver driver = PageFactory.getDriver();
    driver.navigate().refresh();
  }

    public static void moveFocusOnElement(WebElement element) {
        try {
            new Actions(PageFactory.getDriver()).moveToElement(element).perform();
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public static boolean isSelected(WebElement webElement) {
        if (webElement != null) {
            String selected = webElement.getAttribute("selected");
            if (selected == null) {
                selected = webElement.getAttribute("aria-selected");
                if (selected == null) {
                    return false;
                } else {
                    return selected.equalsIgnoreCase("true");
                }
            } else {
                return (selected.equalsIgnoreCase("selected") || selected.equalsIgnoreCase("true"));
            }
        }
        return false;
    }

    public static boolean isChecked(WebElement webElement) {
        if (webElement != null) {
            String checked = webElement.getAttribute("checked");
            if (checked == null) {
                checked = webElement.getAttribute("aria-checked");
                if (checked == null) {
                    return false;
                } else {
                    return checked.equalsIgnoreCase("true");
                }
            } else {
                return (checked.equalsIgnoreCase("checked") || checked.equalsIgnoreCase("true"));
            }
        }
        return false;
    }

    public static void clickElementUsingJquery(WebElement element) throws Exception {
        try {
            if (element != null) {
                moveFocusOnElement(element);
                JavascriptExecutor js = (JavascriptExecutor) PageFactory.getDriver();
                js.executeScript("arguments[0].click();", element);
            }
        } catch (Exception ex) {
            throw new Exception("Could not click on the element due to : " + ex.getMessage());
        }
    }


    public static WebElement findParentElementOfChild(WebElement childElement) throws Exception{
        try {
            if(childElement == null){
                return null;
            }else{
                return (WebElement) ((JavascriptExecutor) PageFactory.getDriver()).executeScript(
                        "return arguments[0].parentNode;", childElement);
            }
        } catch (Exception ex) {
            throw new Exception("Could not find the parent element of child : "+childElement+" due to : " + ex.getMessage());
        }
    }




}
