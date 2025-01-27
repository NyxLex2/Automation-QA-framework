package elements;

import driver.DriverPool;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Arrays;

public class EnhancedElement {
    private WebElement webElement;
    private static final Logger LOGGER = Logger.getLogger(EnhancedElement.class);
    private final String controlName;
    private final String pageName;
    private final WebDriver driver;
    private final WebElement root;
    private final By byLocator;

    public EnhancedElement(WebElement webElement, String pageName, String name) {
        this.webElement = webElement;
        this.pageName = pageName;
        this.controlName = name;
        this.driver = DriverPool.getDriver();
        this.root = webElement;
        this.byLocator = null;
    }

    public EnhancedElement(final String name, final String page, final By by, final SearchContext searchContext) {
        this.controlName = name;
        this.pageName = page;
        this.byLocator = by;
        this.root = (searchContext instanceof WebElement) ? (WebElement) searchContext : null;
        this.driver = DriverPool.getDriver();
    }

    public EnhancedElement waitAndClick() {
        waitAndClick(10);
        return this;
    }

    public void waitAndClick(int timeout) {
        LOGGER.info("Click element " + controlName + " on " + pageName + " page");
        WebElement webElement = getVisibleElement(timeout);

        try {
            webElement.click();
        } catch (WebDriverException var5) {
            LOGGER.debug("click by JS due to " + var5.getMessage());
//            clickByJS(webElement);
        }
    }

    private WebElement getVisibleElement(int timeoutSeconds) {
        LOGGER.info("Trying to find element " + controlName + " on " + pageName + " page");
        WebDriverWait webDriverWait = new WebDriverWait(this.driver, Duration.ofSeconds(timeoutSeconds));
        return root == null ? webDriverWait.until(ExpectedConditionsHelper.visibilityOfElementLocated(byLocator)) :
                webDriverWait.until(ExpectedConditionsHelper.visibilityOf(getElement(webDriverWait)));
    }

    private WebElement getElement(WebDriverWait webDriverWait) {
        LOGGER.info("Trying to find element " + this.controlName + " on " + this.pageName + " page");
        return byLocator != null ? webDriverWait.until(ExpectedConditionsHelper.presenceOfElementLocated(root, byLocator)) : webDriverWait.until(ExpectedConditionsHelper.visibilityOf(root));
    }

    public void waitAndSendKeys(String text) {
        waitAndSendKeys(10, text);
    }

    public void waitAndSendKeys(int timeout, CharSequence... text) {
        WebElement webElement = getVisibleElement(timeout);
        LOGGER.info("Trying to send keys to element " + controlName + " on " + pageName + " page  text: " + Arrays.asList(text));
        webElement.sendKeys(text);
    }

    public EnhancedElement clear() {
        WebElement webElement = getVisibleElement(10);
        webElement.clear();
        return this;
    }

    public boolean waitForMeVisible() {
        return waitForMeVisible(10);
    }


    public void assertIsVisible(int timeout) {
        Assert.assertTrue(waitForMeVisible(timeout), controlName + " locator should be visible: " + byLocator);
    }

    public boolean waitForMeVisible(int timeout) {
        try {
            return getVisibleElement(timeout) != null;
        } catch (Throwable var4) {
            LOGGER.warn("Element " + webElement + " should be visible on " + pageName + " page ");
            return false;
        }
    }

    public boolean waitForMeInVisible() {
        return waitForMeInVisible(10);
    }

    public boolean waitForMeInVisible(int timeoutSeconds) {
        LOGGER.info("Trying to waitForMeInVisible " + controlName + " text on " + pageName + " page");

        try {
            WebDriverWait webDriverWait = new WebDriverWait(this.driver, Duration.ofSeconds(timeoutSeconds));
            if (root == null) {
                return webDriverWait.until(ExpectedConditionsHelper.invisibilityOfElementLocated(byLocator));
            } else {
                throw new UnsupportedOperationException("waitForMeInVisible not supported with WebElement search context");
            }
        } catch (Exception var4) {
            LOGGER.info("Element " + controlName + " is visible on " + pageName + " page");
            return false;
        }
    }

    public String waitGetText() {
        return waitGetText(10);
    }

    public String waitGetText(int timeout) {
        WebElement webElement = getVisibleElement(timeout);
        LOGGER.info("Getting text from element " + controlName + " on " + pageName + " page");
        String res = webElement.getText();
        LOGGER.info("text of element " + controlName + " on " + pageName + " page : " + res);
        return res;
    }


}
