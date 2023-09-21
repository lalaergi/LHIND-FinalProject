package PageObjects;

import BaseClasses.BasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;



public class RegisterPage extends BasePage {
    private WebDriver driver;

    public RegisterPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    //region Selectors
    @FindBy(id = "firstname")
    WebElement firstNameInput;
    @FindBy(id = "lastname")
    WebElement lastNameInput;
    @FindBy(id = "email_address")
    WebElement emailAddInput;
    @FindBy(id = "password")
    WebElement passwordInput;
    @FindBy(id = "password-confirmation")
    WebElement passwordConfirmationInput;
    @FindBy(xpath = "//button[contains(@title,'Create')]")
    WebElement createAccountButton;

    //endregion
    public MyAccountPage performRegistration(String firstName,String lastName,String emailAdd,String password){

        type(firstName,firstNameInput,"First Name Input");
        type(lastName,lastNameInput,"Last Name Input");
        type(emailAdd,emailAddInput,"Email Addd Input");
        type(password,passwordInput,"Password Input");
        type(password,passwordConfirmationInput,"Password Confirmation Input");
        click(createAccountButton,"Create Account Button");
        pause(1);
        return new MyAccountPage(driver);
    }

    public String getTitle(){
        return driver.getTitle();
    }


}
