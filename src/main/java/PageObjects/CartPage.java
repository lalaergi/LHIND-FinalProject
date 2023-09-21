package PageObjects;

import BaseClasses.BasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;


import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class CartPage extends BasePage {
    private WebDriver driver;
    public CartPage(WebDriver driver) {
        super(driver);
        this.driver =driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//td[@class='amount']")
    List<WebElement> allAmountsOnSummary;
    @FindBy(xpath = "//th[@class='mark']")
    List<WebElement> allKeywordsSideAmount;
    @FindBy(xpath = "//tbody[@class='cart item']")
    List<WebElement> totalOfItemsOnCart;
    @FindBy(id="cart-totals")
    WebElement cartTotals;
    @FindBy(xpath = "//tbody[@class='cart item']//a[contains(@class,'action-delete')]")
    List<WebElement> allDeleteLinks;
    @FindBy(xpath = "//div[@class='cart-empty']")
    WebElement cartEmpty;
    @FindBy(xpath = "//strong[.='Order Total']")
    WebElement orderTotal;
    public String getUrl(){
        return driver.getCurrentUrl();
    }
    public void verifyTotal(){
        HashMap<String,Double> summaryValues = new HashMap<>();
        Double sum = 0.00;
        Double total = null;

        waitForVisibility(orderTotal);

        for (int i = 0; i < allKeywordsSideAmount.size(); i++) {
            summaryValues.put(allKeywordsSideAmount.get(i).getText(),
                    Double.parseDouble(allAmountsOnSummary.get(i).getText().replace("$","")));

        }
        for (Map.Entry<String,Double> values: summaryValues.entrySet()) {
            System.out.println(values.getKey());
            if(!values.getKey().contains("Total")){
                sum= sum + values.getValue();
            } else{
                total= values.getValue();
            }
        }
        Assert.assertEquals(sum,total);
    }
    public List<WebElement> getTotalOfItemsOnCart(){
        return totalOfItemsOnCart;
    }
    public List<WebElement> getDeleteItems(){
        return allDeleteLinks;
    }

    public WebElement getCartTotals(){
        return cartTotals;
    }

    public String cartEmptyMessage(){
        return cartEmpty.getText();
    }

    public void waitForVisibility(WebElement element){
        waitForVisibilityOf(element,element.getText());
    }
    public void clickElement(WebElement element){
        clickWithJs(element);
    }
}
