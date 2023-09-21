package BaseClasses;

import Driver.DriverFactory;


import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.time.Duration;



public class BaseTest{

    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private void setDriver(WebDriver driver){
        this.driver.set(driver);
    }
    protected WebDriver getDriver(){
       return this.driver.get();
    }

    @BeforeTest(alwaysRun = true)
    @Parameters({"browserName"})
    public synchronized void startDriver(String browserName){
        setDriver(DriverFactory.getDriverManager(browserName).createDriver());
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        getDriver().manage().window().maximize();
    }

    @AfterTest(alwaysRun = true)
    public synchronized void tearDown(){
        if(driver != null){
            getDriver().quit();
        }
    }


}
