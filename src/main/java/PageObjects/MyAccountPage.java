package PageObjects;

import BaseClasses.BasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import static utilities.Constants.*;

public class MyAccountPage extends BasePage {
    private WebDriver driver;

    @FindBy(xpath = "//div[@class='messages' and @role='alert']")
    WebElement toastMessage;
    @FindBy(xpath = "//header//button[@data-action='customer-menu-toggle']")
    WebElement profileToggle;
    @FindBy(xpath = "//header//li[@class='link wishlist']/following-sibling::li[@class='authorization-link']/a")
    WebElement signOutBtn;
    @FindBy(xpath = "//header//span[contains(@class,'logged-in')]")
    WebElement accountName;
    @FindBy(xpath = "//header//li[@class='link wishlist']")
    WebElement wishList;
    public MyAccountPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public String getAccountName(){
        waitForTextToBePresent(accountName,"Welcome, " + firstName + " " + lastName + "!");
        return accountName.getText();
    }

    public LandingPage logOut(){
        hoverAndClick(profileToggle,"Account Profile Dropdown");
        click(signOutBtn,"Sign Out Button");
        waitForUrl(mainUrl,"Landing Page Url");
        return new LandingPage(driver);
    }

    public String getWishList(){
        hoverAndClick(profileToggle,"Account Profile Dropdown");
        return wishList.getText();
    }

    public String getToastMessage(){
        waitForUrl(customerAccountUrl,"My Account Url");
        waitForTextToBePresent(toastMessage,successMessageRegistration);
        return toastMessage.getText();
    }
    public String getToastMessageWishlist(){
        waitForTextToBePresent(toastMessage,toastMessageWishlist);
        return toastMessage.getText();
    }

    public String getTitle(){
        return driver.getTitle();
    }

}
