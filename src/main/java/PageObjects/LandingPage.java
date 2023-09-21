package PageObjects;


import BaseClasses.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static utilities.Constants.wishListUrl;


public class LandingPage extends BasePage {

    private WebDriver driver;


    public LandingPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }


    @FindBy(xpath = "//div[contains(@class,'panel header')]//a[contains(@href,'account/create')]")
    WebElement createAccountLink;

    @FindBy(xpath = "//div[contains(@class,'panel header')]//a[contains(@href,'account/login')]")
    WebElement signInLink;
    @FindBy(xpath = "//header//button[@data-action='customer-menu-toggle']")
    WebElement customerMenuToggle;
    @FindBy(xpath = "//header//li[@class='link wishlist']")
    WebElement wishList;
    public static String filterMenu = "//ul[@role='menu']//a[contains(.,'%s')]";
    public static String filterSubMenu = "//following-sibling::ul//a[contains(.,'%s')]";
    public String openPage(){
        openUrl();
        return getTitle();
    }
    public WishList goToWishList(){
        driver.get(wishListUrl);
        waitForUrl(wishListUrl,"WishList URL");
        return new WishList(driver);
    }
    public RegisterPage clickCreateButton(){
        click(createAccountLink,"Create Account Link");
        return new RegisterPage(driver);
    }
    public SignInPage clickSignIn(){
        click(signInLink,"Create SignIn Link");
        return new SignInPage(driver);
    }

    public void hoverOnTopLevelFilter(String menuItem){
        hover(By.xpath(String.format(filterMenu,menuItem)),"Element with menu item: " + menuItem);
    }
    public void hoverOnSecondLevelFilter(String menuItem,String subMenu){
        hover(By.xpath(String.format(filterMenu,menuItem)+String.format(filterSubMenu,subMenu)),"Element with submenu: " + subMenu + " inside: " +menuItem);
    }
    public ProductsPage clickOnThirdLevelFilter(String menuItem,String article){
        click(By.xpath(String.format(filterMenu,menuItem)+String.format(filterSubMenu,article)),"Element with article: " + article);
        return new ProductsPage(driver);
    }
    public String getTitle(){
        return driver.getTitle();
    }

}
