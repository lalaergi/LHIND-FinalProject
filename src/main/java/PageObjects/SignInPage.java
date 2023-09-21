package PageObjects;

import BaseClasses.BasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class SignInPage extends BasePage {

    private WebDriver driver;


    public SignInPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
    @FindBy(id = "email")
    WebElement emailInput;

    @FindBy(id = "pass")
    WebElement passwordInput;
    @FindBy(id = "send2")
    WebElement signInBtn;

    public MyAccountPage performLogin(String email,String password){
        type(email,emailInput,"Email Input");
        type(password,passwordInput,"Password Input");
        click(signInBtn,"Sign In Button");
        pause(1);
        return new MyAccountPage(driver);
    }

    public String getTitle(){
        return driver.getTitle();
    }


}
