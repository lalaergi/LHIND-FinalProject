import BaseClasses.BaseTest;
import PageObjects.*;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.util.List;

import static utilities.Constants.*;


public class Tests extends BaseTest {
    static LandingPage landingPage;
    static RegisterPage registerPage;
    static MyAccountPage myAccountPage;
    static SignInPage signInPage;
    static ProductsPage productsPage;
    static WishList wishListPage;
    static CartPage cartPage;
    String generateUniqueEmail;
    int getTotalProductFilter;

    @BeforeMethod()
    public void initializeObjects(){
        landingPage = new LandingPage(getDriver());
        registerPage = new RegisterPage(getDriver());
        myAccountPage = new MyAccountPage(getDriver());
        signInPage = new SignInPage(getDriver());
        wishListPage = new WishList(getDriver());

    }


    @Test(priority = 1)
    public void createAccount(){
        String titleLandingPage = landingPage.openPage();
        Assert.assertEquals(titleLandingPage.toLowerCase().trim(),landingPageTitle);
        registerPage = landingPage.clickCreateButton();
        String createAccTitle = registerPage.getTitle();
        Assert.assertEquals(createAccTitle.toLowerCase().trim(),createAccountTitle);
        generateUniqueEmail = String.format(emailAddress,Math.round(Math.random()*100000+1));
        myAccountPage = registerPage.performRegistration(firstName,lastName,generateUniqueEmail,password);
        String toastMessage = myAccountPage.getToastMessage();
        Assert.assertEquals(toastMessage,successMessageRegistration);
        landingPage = myAccountPage.logOut();
        titleLandingPage = landingPage.getTitle();
        Assert.assertEquals(titleLandingPage.toLowerCase().trim(),landingPageTitle);
    }

    @Test(priority = 2,dependsOnMethods = {"createAccount"})
    public void signIn(){
        signInPage = landingPage.clickSignIn();
        String signInTitle = signInPage.getTitle();
        Assert.assertEquals(signInTitle.toLowerCase().trim(),signInPageTitle);
        myAccountPage = signInPage.performLogin(generateUniqueEmail,password);
        Assert.assertEquals(myAccountPage.getTitle().toLowerCase().trim(),landingPageTitle);
        Assert.assertTrue(myAccountPage.getAccountName().contains(firstName + " " + lastName));
        landingPage = myAccountPage.logOut();
        Assert.assertEquals(landingPage.getTitle().toLowerCase().trim(),landingPageTitle);
    }


    @Test(priority = 3, dependsOnMethods = {"signIn"})
    public void checkPageFilters(){
        performLogin();
        landingPage.hoverOnTopLevelFilter(womenFilter);
        landingPage.hoverOnSecondLevelFilter(womenFilter,topsFilter);
        productsPage = landingPage.clickOnThirdLevelFilter(womenFilter,jacketFilter);
        productsPage.clickFilterName(colorFilter);
        String choosenOption = productsPage.clickOneOfOptions(colorFilter);
        productsPage.checkDisplayedProducts(choosenOption);
        productsPage.clickFilterName(priceFilter);
        int totalItemSideFilter = productsPage.getNoSideFirstOptionActiveFilter();
        getTotalProductFilter = productsPage.clickFirstOptionActiveFilter();
        Assert.assertEquals(totalItemSideFilter,getTotalProductFilter);
        productsPage.checkPriceValueWithProducts();
    }
    @Test(priority = 4,dependsOnMethods = {"checkPageFilters"})
    public void wishList(){
        productsPage.removePriceFilter();
        int currentTotalOfProducts = productsPage.returnTotalOfProducts();
        Assert.assertNotEquals(currentTotalOfProducts,getTotalProductFilter);
        String message = productsPage.addFirstTwoItemsInWishlist();
        Assert.assertTrue(message.contains(toastMessageWishlist),"Wishlist message" + message + " doesn't contain " + toastMessageWishlist);
        String wishListText = myAccountPage.getWishList();
        Assert.assertEquals(wishListText,totalOf2WishListMessage);
    }
    @Test(priority = 5,dependsOnMethods = {"checkPageFilters"})
    public void shoppingCart() {
        wishListPage = landingPage.goToWishList();
        List<WebElement> allWishListItems = wishListPage.allProductWishList();
        int totalAllWishListItems = allWishListItems.size();
        for (int i = 1; i <= totalAllWishListItems; i++) {
            wishListPage.hoverToProduct(allWishListItems.get(i - 1));
            wishListPage.waitForAddToCart();
            List<WebElement> sizes = wishListPage.getAllSizes();
            int randomOption = (int) Math.floor((Math.random() * sizes.size()));
            wishListPage.clickToProduct(sizes.get(randomOption));
            List<WebElement> colors = wishListPage.getAllColors();
            randomOption = (int) Math.floor((Math.random() * colors.size()));
            wishListPage.clickToProduct(colors.get(randomOption));
            wishListPage.clickrAddToCart();
            wishListPage.waitSuccessMessage();
            if (i == totalAllWishListItems) {
                break;
            } else {
                wishListPage.navigateBack();
            }
        }
        cartPage = wishListPage.navigateToShoppingCart();
        String currentUrl = cartPage.getUrl();
        Assert.assertEquals(currentUrl, cartUrl);
        cartPage.waitForVisibility(cartPage.getCartTotals());
        cartPage.verifyTotal();
    }

    @Test(dependsOnMethods = {"shoppingCart"})
    public void emptyShoppingCart(){
        List<WebElement> allCartItems = cartPage.getTotalOfItemsOnCart();
        List<WebElement> allDeleteItems = cartPage.getDeleteItems();
        int initialSize = allCartItems.size();
        int changedSize;
        for (int i = 0; i < allCartItems.size(); i++) {
            cartPage.waitForVisibility(allDeleteItems.get(i));
            cartPage.clickElement(allDeleteItems.get(i));
            if(initialSize == i+1){
                Assert.assertTrue(cartPage.cartEmptyMessage().contains(emptyCartMessage));
            } else {
                changedSize = cartPage.getTotalOfItemsOnCart().size();
                Assert.assertEquals(changedSize, initialSize -1);
                initialSize--;
            }
        }
    }


    public void performLogin(){
        signInPage = landingPage.clickSignIn();
        String signInTitle = signInPage.getTitle();
        Assert.assertEquals(signInTitle.toLowerCase().trim(),signInPageTitle);
        myAccountPage = signInPage.performLogin(generateUniqueEmail,password);
        Assert.assertEquals(myAccountPage.getTitle().toLowerCase().trim(),landingPageTitle);
        Assert.assertTrue(myAccountPage.getAccountName().contains(firstName + " " + lastName));
    }
}
