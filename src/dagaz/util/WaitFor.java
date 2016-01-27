/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dagaz.util;

import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author Hyungin Choi
 */
public class WaitFor {

    private WebDriver driver;
    private long timeOutInMiliSeconds;

    public WaitFor(WebDriver driver, long timeOutInMiliSeconds) {
        this.driver = driver;
        this.timeOutInMiliSeconds = timeOutInMiliSeconds;
    }

    public boolean visibility(String xpath) {
        long startTime = (new Date()).getTime();

        while (((new Date()).getTime() - startTime) < timeOutInMiliSeconds) {
            List<WebElement> elements = driver.findElements(By.xpath(xpath));
            if (elements.size() > 0 && elements.get(0).isDisplayed()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean interactable(String xpath) {
        long startTime = (new Date()).getTime();

        while (((new Date()).getTime() - startTime) < timeOutInMiliSeconds) {
            List<WebElement> elements = driver.findElements(By.xpath(xpath));
            if (elements.size() > 0 && elements.get(0).isEnabled()) {
                return true;
            }
        }
        return false;
    }
}
