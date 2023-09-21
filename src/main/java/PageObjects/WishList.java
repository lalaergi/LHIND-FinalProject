package PageObjects;

import BaseClasses.BasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static utilities.Constants.successMessageAddedToCart;

public class WishList extends BasePage {
    private WebDriver driver;

    public WishList(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//form[contains(@id,'wishlist-view-form')]//li[@class='product-item']")
    List<WebElement> allProductWishList;
    @FindBy(id="product-addtocart-button")
    WebElement addToCart;
    @FindBy(xpath = "//div[@attribute-code='size']//div[contains(@class,'swatch-option')]")
    List<WebElement> allSizes;
    @FindBy(xpath = "//div[@attribute-code='color']//div[contains(@class,'swatch-option')]")
    List<WebElement> allColors;
    @FindBy(xpath = "//div[@data-ui-id='message-success']")
    WebElement successMessage;

    @FindBy(linkText = "shopping cart")
    WebElement shoppingCartLink;

    public List<WebElement> allProductWishList(){
        return allProductWishList;
    }
    public List<WebElement> getAllSizes(){
        return allSizes;
    }
    public List<WebElement> getAllColors(){
        return allColors;
    }

    public void navigateBack(){
        driver.navigate().back();
    }
    public void waitSuccessMessage(){
        waitForTextToBePresent(successMessage,successMessageAddedToCart);
    }
    public void hoverToProduct(WebElement element){
        hoverAndClick(element, "Product :" + element.getText());
    }
    public void clickToProduct(WebElement element){
        click(element, element.getText());
    }
    public void waitForAddToCart(){
        waitForClickable(addToCart, "Add to Cart Button");
    }
    public void clickrAddToCart(){
        click(addToCart, "Add to Cart Button");
    }
    public int getTotalSize(){
        return allSizes.size();
    }
    public int getTotalColor(){

        return allColors.size();
    }
    public CartPage navigateToShoppingCart(){
        click(shoppingCartLink,"Shoping Cart Link");

        return new CartPage(driver);
    }

    public String getTitle(){
        return driver.getTitle();
    }
}
