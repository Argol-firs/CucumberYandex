package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import utils.Waiting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class for working with the YandexMarket page
 */
public class PageYandexMarket {

    /**
     * Field for storing arrays of data
     */

    private String catalogButton = "//button[@id='catalogPopupButton' and @type='button' and @aria-label='Каталог']";
    private String selectorProduct = "//div//li[@data-zone-name='category-link']";
    private String selectorProductURL = ".//a[@href]";
    private String  selectorProductNamePage = ".//span";
    private String selectorProductMarket ="//div[contains(@data-apiary-widget-name, 'NavigationTree')]//div[@data-zone-name]";
    private String selectorProductMarketURL = ".//a[@href]";
    private List<WebElement> searchProduct = new ArrayList<>();
    private List<Map<String, Object>> productList = new ArrayList<>();
    private List<WebElement> computerProduct = new ArrayList<>();
    private List<Map<String, Object>> computerList = new ArrayList<>();


    /**
     * Catalog button click method
     */
    public void clickCatalogButton() {
        DriverManager.getDriver().findElement(By.xpath(catalogButton)).click();
    }

    /**
     * Method for obtaining a list of YandexMarket products
     * @see YandexMainPage#getYandexServices()
     */
    public List<Map<String, Object>> getMarketServices() {
        Waiting.waitUntilElementBeVisible(DriverManager.getDriver().findElement(By.xpath("//div[@data-tid='d72ded0c']")));
        searchProduct = DriverManager.getDriver().findElements(By.xpath(selectorProduct));
        for (WebElement result : searchProduct) {
            productList.add(Map.of(
                            "WEB_ELEMENT", result,
                            "URL", result.findElement(By.xpath(selectorProductURL)),
                            "NAME_PAGE", result.findElement(By.xpath(selectorProductNamePage)).getText()
                    )
            );
        }
        return productList;
    }


    /**
     * The method finds the requested element in the collection and focuses the mouse position on it
     * @see YandexMainPage#openYandexMarket(String)
     */
    public boolean getMarketProductList(String namePage) {
        Actions actions = new Actions(DriverManager.getDriver());
        WebElement linkProduct = (WebElement) getMarketServices().stream()
                .filter(x -> x.get("NAME_PAGE").toString().contains(namePage))
                .findFirst()
                .get().get("WEB_ELEMENT");
        actions.moveToElement(linkProduct.findElement(By.xpath(selectorProductURL))).build().perform();
        return true;
    }

    /**
     * Method for obtaining a list of items on the Computers tab
     * @see YandexMainPage#getYandexServices()
     */
    public List<Map<String, Object>> getProductServices() {
        computerProduct= DriverManager.getDriver().findElements(By.xpath(selectorProductMarket));
        for (WebElement result : computerProduct) {
            computerList.add(Map.of(
                            "WEB_ELEMENT", result,
                            "URL", result.findElement(By.xpath(selectorProductMarketURL)),
                            "NAME", result.getAttribute("textContent")
                    )
            );
        }
        return computerList;
    }

    /**
     * The method finds the requested element in the collection and follows its link
     * @see YandexMainPage#openYandexMarket(String)
     */
    public boolean getCategoryProductList(String namePage) {
        WebElement linkProduct = (WebElement) getProductServices().stream()
                .filter(x -> x.get("NAME").toString().equals(namePage))
                .findFirst()
                .get().get("WEB_ELEMENT");
        linkProduct.findElement(By.xpath(selectorProductMarketURL)).click();
        List<String> tabs = new ArrayList<>(DriverManager.getDriver().getWindowHandles());
        for (String tab : tabs) {
            DriverManager.getDriver().switchTo().window(tab);
        if (DriverManager.getDriver().getTitle().contains(namePage))
            return true;
        }
        Assertions.fail("Не найден раздел " + namePage);
        return false;
    }
}
