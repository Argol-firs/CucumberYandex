package tests.stepDef;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.DriverManager;
import pages.PageMarketProduct;
import pages.PageYandexMarket;
import pages.YandexMainPage;

public class Steps {

    @Before
    public void beforeTest() {
        DriverManager.initDriver();
    }

    @After
    public void afterTest() {
        DriverManager.quidDriver();
    }

    @Given("перейти на сайт {string}")
    public void openPageSite(String uri) {
        DriverManager.getDriver().get(uri);
    }

    @When("выбрать и перейти на сервис {string}")
    public void openService(String service) {
        new YandexMainPage().openYandexMarket(service);
    }

    @Then("открыть категории товаров")
    public void openCategories() {
        new PageYandexMarket().clickCatalogButton();
    }

    @Then("выбрать категорию товаров {string}")
    public void selectCategories(String categories) {
        new PageYandexMarket().getMarketProductList(categories);
    }

    @Then("выбрать подкатегорию {string} и перейти по ссылке")
    public void selectSubCategories(String subcategory) {
        new PageYandexMarket().getCategoryProductList(subcategory);
    }

    @Then("установить цену от {string} до {string}")
    public void inputPriceProduct(String priceFrom, String priceTo) {
        new PageMarketProduct().castPriceFrom(priceFrom);
        new PageMarketProduct().castPriceTo(priceTo);
    }

    @Then("выбрать производителя {string}")
    public void selectManufacturer(String manufacturer) {
        new PageMarketProduct().clickCheckbox(manufacturer);
    }

    @Then("установить отображение элементов на странице не более 12")
    public void selectItemVisible() {
        new PageMarketProduct().clickDropdown();
        new PageMarketProduct().take12Items();
    }

    @Then("найти первое предложение из представленных")
    public String findFirstProduct() {
        String productA = new PageMarketProduct().firstElementPage();
        return productA;
    }

    @Then("венести первое предложение из представленных в поисковую строку")
    public void searchFirstProduct() {
        new PageMarketProduct().clickSearchElementCollection();
    }

    @Then("найти первое предложение из вновь представленных")
    public String findFirstNewProduct() {
        String productB = new PageMarketProduct().firstElementNewPage();
        return productB;
    }

    @Then("сравнить товары")
    public void compareItems() {
        new PageMarketProduct().compareElement(findFirstProduct(), findFirstNewProduct());
    }

    @And("закрыть браузер")
    public void stopBrowser() {
        new DriverManager().getDriver().quit();
    }
}
