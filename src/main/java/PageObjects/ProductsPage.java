package PageObjects;

import BaseClasses.BasePage;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

import static utilities.Constants.toastMessageWishlist;

public class ProductsPage extends BasePage {
    private WebDriver driver;
    public ProductsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    //region Selectors
    @FindBy(xpath = "//span[contains(@class,'filter-value') and contains(.,'$')]")
    WebElement selectedPriceValue;
    @FindBy(xpath = "//div[@class='product-item-info']")
    List<WebElement> totalProducts;
    @FindBy(xpath = "//div[@class='filter-current']//li[@class='item' and contains(.,'Price')]//a")
    WebElement removeSelectedPriceFilter;

    @FindBy(xpath = "")
    public static String filterNameXpath = "//div[@class='filter-options']//div[.='%s']";
    public static String filterOptions = "/following-sibling::div//a";
    public static String elementLabel = "[contains(@aria-label,'%s')]";
    public static String productsWithFilteredColor = "//div[@aria-label='%s']";
    public static String firstFilterOptionOfActiveFilter = "//div[@aria-hidden='false']//ol[@class='items']/li[1]";
    public static String noItemsSideFirstOption="//span[@class='count']";
    public static By priceInProductInfo = By.xpath("//div[contains(@class,'product-item-info')]//span[@class='price']");
    public static By addToWishlist = By.xpath("//div[@data-role='add-to-links']//a[contains(@class,'towishlist')]");
    //endregion

    public void clickFilterName(String filterName){
        click(By.xpath(String.format(filterNameXpath,filterName)),"Click filter name: " + filterName);
    }
    public String clickOneOfOptions(String filterName){
        String formatedFilterName =String.format(filterNameXpath,filterName) + filterOptions;
        List<WebElement> elementOptions = driver.findElements(By.xpath(formatedFilterName));
        int randomOption = (int) Math.floor(Math.random() * elementOptions.size());
        String elementText = elementOptions.get(randomOption).getAttribute("aria-label");
        String formatedFilterWithOptions = formatedFilterName + String.format(elementLabel,elementText);
        clickWithJs(driver.findElement(By.xpath(formatedFilterWithOptions)));

        return elementText;
    }
    public void checkDisplayedProducts(String color){
        List<WebElement> foundProductsWithSelectedColor = driver.findElements(By.xpath(String.format(productsWithFilteredColor,color)));
        for (WebElement currentProduct: foundProductsWithSelectedColor) {
            String colorOutline = currentProduct.getCssValue("outline-color");
            Assert.assertEquals(colorOutline, "rgba(255, 85, 1, 1)", "Wrong Color Outline");
        }
    }

    public int getNoSideFirstOptionActiveFilter(){

        return Integer.parseInt(StringUtils.getDigits
                (driver.findElement
                        (By.xpath(firstFilterOptionOfActiveFilter+noItemsSideFirstOption)).getText()
                )
        );
    }
    public int clickFirstOptionActiveFilter(){
        click(By.xpath(firstFilterOptionOfActiveFilter),"");
        pause(1);
        return totalProducts.size();
    }

    public void checkPriceValueWithProducts(){
        String[] prices = selectedPriceValue.getText().replace("$","").split("-");
        for (WebElement element : totalProducts) {
            String currentPrice = element.findElement(priceInProductInfo).getText().replace("$","");
            if(Double.parseDouble(currentPrice) < Double.parseDouble(prices[0])
                    || Double.parseDouble(currentPrice) > Double.parseDouble(prices[1])){
                Assert.fail("Product info with current price: "
                        + currentPrice + " doesn't fulfill the filter starting from"
                        + prices[0] + " to : " + prices[1]);
            }
        }
    }

    public void removePriceFilter(){
        click(removeSelectedPriceFilter,"Remove selected price filter");
    }
    public int returnTotalOfProducts(){
        pause(1);
        return totalProducts.size();
    }
    public String addFirstTwoItemsInWishlist(){
        String toastMessage = "";
        for (int i = 0; i <= 1; i++) {
            hover(totalProducts.get(i), "Product :" + i);
            pause(1);
            clickWithJs(totalProducts.get(i).findElement(addToWishlist));
            MyAccountPage myAccountPage = new MyAccountPage(driver);
            toastMessage = myAccountPage.getToastMessageWishlist();
            Assert.assertTrue(toastMessage.contains(toastMessageWishlist));
            driver.navigate().back();
            pause(1);
        }
        return toastMessage;
    }


    public String getTitle(){
        return driver.getTitle();
    }
}
