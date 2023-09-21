package BaseClasses;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.File;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static utilities.Constants.*;


public class BasePage extends BaseTest{

    private static WebDriver driver;
    JavascriptExecutor js;
    public static String screenshotName;

    public BasePage(WebDriver driver){
        this.driver = driver;
        js = (JavascriptExecutor) this.driver;
    }

    protected void openUrl(){
        driver.get(mainUrl);
    }
    protected void writeConsoleLog(String text){
        System.out.println(text);
    }

    protected WebElement find(By locator) {
        return driver.findElement(locator);
    }

    protected void click(By locator,String elementDescp) {
        waitForClickable(locator,elementDescp, timeout5);
        find(locator).click();
        writeConsoleLog("Clicked: " + elementDescp);
    }
    protected void click(WebElement element,String elementDescp) {
        waitForClickable(element,elementDescp, timeout5);
        element.click();
        writeConsoleLog("Clicked: " + elementDescp);
    }
    protected void clickWithJs(WebElement element){
        js.executeScript("arguments[0].click();", element);
    }
    protected void type(String text, By locator,String elementDescp) {
        waitForVisibilityOf(locator,elementDescp, timeout5);
        find(locator).sendKeys(text);
        writeConsoleLog("Send Keys in " + elementDescp + " with text : " + text);
    }
    protected void type(String text, WebElement element,String elementDescp) {
        waitForVisibilityOf(element,elementDescp ,timeout5);
        element.sendKeys(text);
        writeConsoleLog("Send Keys in " + elementDescp + " with text : " + text);
    }
    protected void hoverAndClick(WebElement element,String elementDescp){
        waitForVisibilityOf(element,elementDescp,timeout5);
        new Actions(driver)
                .moveToElement(element)
                .click()
                .perform();
        writeConsoleLog("Hover and clicking: " + elementDescp);
    }
    protected void scrollAndClick(WebElement element,String elementDescp){
        waitForVisibilityOf(element,elementDescp,timeout5);
        new Actions(driver)
                .scrollToElement(element)
                .click()
                .perform();
           writeConsoleLog("Scroll and clicking: " + elementDescp);
    }
    protected void hover(By locator,String elementDescp){
        waitForVisibilityOf(locator,elementDescp,timeout5);
        new Actions(driver)
                .moveToElement(driver.findElement(locator))
                .perform();
           writeConsoleLog("Hover: " + elementDescp);
    }
    protected void hover(WebElement element,String elementDescp){
        waitForVisibilityOf(element,elementDescp,timeout5);
        new Actions(driver)
                .moveToElement(element)
                .perform();
           writeConsoleLog("Hover: " + elementDescp);
    }
    private void waitForElement(ExpectedCondition<WebElement> condition, Integer timeOutInSeconds) {
        timeOutInSeconds = timeOutInSeconds != null ? timeOutInSeconds : timeout30;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
        wait.until(condition);
    }
    private void waitForBoolean(ExpectedCondition<Boolean> condition, Integer timeOutInSeconds) {
        timeOutInSeconds = timeOutInSeconds != null ? timeOutInSeconds : timeout30;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
        wait.until(condition);
    }
    protected void waitForVisibilityOf(By locator,String elementDescp,Integer... timeOutInSeconds) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                waitForElement(ExpectedConditions.visibilityOfElementLocated(locator),
                        (timeOutInSeconds.length > 0 ? timeOutInSeconds[0] : null));
                writeConsoleLog("Element Found: " + elementDescp);
                break;
            } catch (StaleElementReferenceException e) {
            }
            attempts++;
        }
    }
    protected void waitForVisibilityOf(WebElement element,String elementDescp, Integer... timeOutInSeconds) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                waitForElement(ExpectedConditions.visibilityOf(element),
                        (timeOutInSeconds.length > 0 ? timeOutInSeconds[0] : null));
                writeConsoleLog("Element Found: " + elementDescp);
                break;
            } catch (StaleElementReferenceException e) {
            }
            attempts++;
        }
    }
    protected void waitForClickable(By locator,String elementDescp, Integer... timeOutInSeconds) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                waitForElement(ExpectedConditions.elementToBeClickable(locator),
                        (timeOutInSeconds.length > 0 ? timeOutInSeconds[0] : null));
                writeConsoleLog("Element Found: " + elementDescp);
                break;
            } catch (StaleElementReferenceException e) {
            }
            attempts++;
        }
    }
    protected void waitForClickable(WebElement element,String elementDescp, Integer... timeOutInSeconds) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                waitForElement(ExpectedConditions.elementToBeClickable(element),
                        (timeOutInSeconds.length > 0 ? timeOutInSeconds[0] : null));
                writeConsoleLog("Element Found: " + elementDescp);
                break;
            } catch (StaleElementReferenceException ignored) {
            }
            attempts++;
        }
    }


    protected void waitForUrl(String url,String urlDescp, Integer... timeOutInSeconds) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                waitForBoolean(ExpectedConditions.urlToBe(url),
                        (timeOutInSeconds.length > 0 ? timeOutInSeconds[0] : null));
                writeConsoleLog("URL MATCHED WITH: " + urlDescp);
                break;
            } catch (StaleElementReferenceException e) {
            }
            attempts++;
        }
    }
    protected void waitForTextToBePresent(WebElement element,String text, Integer... timeOutInSeconds) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                waitForBoolean(ExpectedConditions.textToBePresentInElement(element,text),
                        (timeOutInSeconds.length > 0 ? timeOutInSeconds[0] : null));
                      writeConsoleLog("TEXT FOUND: " + text);
                break;
            } catch (StaleElementReferenceException e) {
            }
            attempts++;
        }
    }

    protected void pause(int timeOut) {
        try {
            TimeUnit.SECONDS.sleep(timeOut);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected String getTitle(){
       return driver.getTitle();
    }

    public static void captureScreenshot() {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Date d = new Date();

        screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
        try {
            FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "/src/main/java/reports/" + screenshotName));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

}
