package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import utils.Waiting;

import java.util.ArrayList;
import java.util.List;

public class PageMarketProduct {

    /**
     * Field for storing arrays of data
     */

    private String castFrom = "//legend[contains(text(), 'Цена')]/..//p[@data-range-input-type='from']//input[@name='Цена от']";
    private String castTo = "//legend[contains(text(), 'Цена')]/..//p[@data-range-input-type='to']//input[@name='Цена до']";
    private String chooseManufacturer = "//fieldset[@data-autotest-id='7893318']//li//label";
    private String buttonVisiblAll = "//button[text()='Показать всё']";
    private String buttonClose = "//button[text()='Свернуть']";
    private String dropDownButton = "//button[contains(@id, 'dropdown-control')]";
    private String dropDownButton12Items = "//button[contains(text(), 'Показывать по 12')]";
    private String loaderList = "//div[@class='_12KyU _38i7T' and @data-tid='8bc8e36b']";
    private String rezultSearch = "//div[@data-zone-name='SearchResults']//h3//a";
    private String rezultSearchNew = "//article//h3//a[@title]";
    private String headerSearch = "//input[@type='text' and @id='header-search']";
    String productMain;
    String productMainNew;

    private static List<WebElement> Manufactured = new ArrayList<>();
    static List<WebElement> rezult = new ArrayList<>();
    static List<WebElement> rezultNew = new ArrayList<>();

    /**
     * Price from setting method
     * @param from string value of price from
     * @return set value
     */
    public String castPriceFrom(String from) {
        Waiting.waitUntilElementBeVisible(DriverManager.getDriver().findElement(By.xpath(castFrom)));
        DriverManager.getDriver().findElement(By.xpath(castFrom)).click();
        DriverManager.getDriver().findElement(By.xpath(castFrom)).sendKeys(from);
        Waiting.isElementNotExist(DriverManager.getDriver().findElement(By.xpath(loaderList)));
        return from;
    }

    /**
     * Price to setting method
     * @param to string value of price to
     * @return set value
     */
    public String castPriceTo(String to) {
        DriverManager.getDriver().findElement(By.xpath(castTo)).click();
        DriverManager.getDriver().findElement(By.xpath(castTo)).sendKeys(to);
        Waiting.isElementNotExist(DriverManager.getDriver().findElement(By.xpath(loaderList)));
        return to;
    }


    /**
     * Manufacturer selection and checkbox method
     * @param name vendor name value
     * @return check if the selected value matches the existing ones
     */
    public boolean clickCheckbox(String name) {
        DriverManager.getDriver().findElement(By.xpath(buttonVisiblAll)).click();
        DriverManager.getDriver().findElement(By.xpath(buttonClose)).click();
        Actions actions = new Actions(DriverManager.getDriver());
        Manufactured = DriverManager.getDriver().findElements(By.xpath(chooseManufacturer));
        for (WebElement check : Manufactured) {
            if (check.getText().equals(name)) {
                actions.moveToElement(check).build().perform();
                check.click();
                Waiting.isElementNotExist(DriverManager.getDriver().findElement(By.xpath(loaderList)));
                return true;
            }
        }
        Assertions.fail("Производитель " + name + " не найден");
        return false;
    }

    /**
     * Method for counting the number of items shown on a page
     * @return webelement array size
     */
    public int searchRezultProductSize(){
        rezult = DriverManager.getDriver().findElements(By.xpath(rezultSearch));
        return rezult.size();
    }

    /**
     * The method finds the "show by" button and sets the value to "show by 12"
     */
    public boolean clickDropdown() {
        if(searchRezultProductSize() > 48) {
            Actions actions = new Actions(DriverManager.getDriver());
            actions.moveToElement(DriverManager.getDriver().findElement(By.xpath(dropDownButton))).build().perform();
            DriverManager.getDriver().findElement(By.xpath(dropDownButton)).click();
            DriverManager.getDriver().findElement(By.xpath(dropDownButton12Items)).click();
            Waiting.isElementNotExist(DriverManager.getDriver().findElement(By.xpath(loaderList)));
            return true;
        }
        return false;
    }

    public boolean take12Items() {
        if (clickDropdown() == true) {
            DriverManager.getDriver().findElement(By.xpath(dropDownButton12Items)).click();
            Waiting.isElementNotExist(DriverManager.getDriver().findElement(By.xpath(loaderList)));
            return true;
        }
        return false;
    }

    /**
     * Storing the first element in an array of search results
     */
    public String firstElementPage(){
        if(searchRezultProductSize() !=0) {
            rezultNew = DriverManager.getDriver().findElements(By.xpath(rezultSearchNew));
            productMain = rezultNew.get(0).getText();
            return productMain;
        }
        return null;
    }

    /**
     * The method enters the first saved element in the search bar and presses ENTER
     */
    public boolean clickSearchElementCollection() {
        if(searchRezultProductSize() !=0) {
            DriverManager.getDriver().findElement(By.xpath(headerSearch)).click();
            DriverManager.getDriver().findElement(By.xpath(headerSearch)).sendKeys(firstElementNewPage());
            DriverManager.getDriver().findElement(By.xpath(headerSearch)).sendKeys(Keys.ENTER);
            return true;
        }
        return false;
    }

    /**
     * The method finds the first element after the search and saves it to a variable
     */
    public String firstElementNewPage(){
        if(searchRezultProductSize() !=0) {
            rezultNew = DriverManager.getDriver().findElements(By.xpath(rezultSearchNew));
            productMainNew = rezultNew.get(0).getText();
            return productMainNew;
        }
        return null;
    }

    public void compareElement(String productA, String productB) {
        if(firstElementPage()!=null && firstElementNewPage()!=null) {
            Assertions.assertEquals(productA, productB, "Разные товары");
        }
    }
}
