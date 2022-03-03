package utils;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.DriverManager;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static utils.Constants.DEFAULT_TIMEOUT;

/**
 * Class for expect methods in the project
 */
public class Waiting {


    public static boolean isVisible(WebElement element) {
        return element.isDisplayed();
    }

    /**
     * Wait until element is visible
     */
    public static void waitUntilElementBeVisible(WebElement element) {
        new WebDriverWait(DriverManager.getDriver(), DEFAULT_TIMEOUT)
                .until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waiting for an element to disappear
     */
    public static boolean isElementNotExist(WebElement element) {
        int timer = 0;
        DriverManager.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        try {
            while (isVisible(element) && timer < DEFAULT_TIMEOUT/3) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timer++;
            }
            return false;
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return true;
        } finally {
            DriverManager.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
    }
}
