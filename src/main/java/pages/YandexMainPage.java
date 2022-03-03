package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class for working with Yandex page
 */
public class YandexMainPage {

    /**
     * Field for storing arrays of data
    * */
    private String SearchItems = "//li[@class='services-new__list-item']";
    private String URL = ".//a[@href]";
    private String NamePage = ".//div[@class='services-new__item-title']";
    private List<WebElement> searchItem = new ArrayList<>();
    private List<Map<String, Object>> service = new ArrayList<>();

    /**
     * Method for obtaining a list of services provided by Yandex
     * "WEB_ELEMENT" - web element representation
     * "URL" - link to the element
     * "NAME_PAGE" - element name
     */
    public List<Map<String, Object>> getYandexServices() {
        searchItem = DriverManager.getDriver().findElements(By.xpath(SearchItems));
        for (WebElement result : searchItem) {
            service.add(Map.of(
                    "WEB_ELEMENT", result,
                    "URL", result.findElement(By.xpath(URL)),
                    "NAME_PAGE", result.findElement(By.xpath(NamePage)).getText()
                    )
            );
        }
        return service;
    }

    /**
     * The method finds the requested element in the collection and opens it on a new page
     * @param namePage string query for required element
     * @return completion of the transition to the page
     */
    public boolean openYandexMarket(String namePage) {
        WebElement linkMarket = (WebElement) getYandexServices().stream()
                .filter(x -> x.get("NAME_PAGE").toString().contains(namePage))
                .findFirst()
                .get().get("WEB_ELEMENT");
        linkMarket.findElement(By.xpath(URL)).click();
        List<String> tabs = new ArrayList<>(DriverManager.getDriver().getWindowHandles());
        for (String tab : tabs) {
            DriverManager.getDriver().switchTo().window(tab);
            if (DriverManager.getDriver().getTitle().contains(namePage)) {
                return true;
            }
        }
        Assertions.fail("Не найдена ссылка на " + namePage);
        return false;
    }
}

