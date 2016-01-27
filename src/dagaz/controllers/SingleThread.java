/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dagaz.controllers;

import dagaz.driver.RunningDriver;
import dagaz.exception.ArenaNotAvailable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.SessionNotFoundException;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author Hyungin Choi
 */
public class SingleThread implements Runnable {

    private Configuration config;
    private WebDriver driver;
    private ArrayList<ChildController> childControllers;
    ArrayList<String> availableArenas;
    private Date lastRenewTimeStamp;

    public SingleThread(Configuration config) {
        this.config = config;
    }

    public String getName() {
        return Thread.currentThread().getName();
    }

    @Override
    public void run() {
        config.getTaDetails().append("\n" + getName());
        while (config.isIsKeepRunning()) {
            try {
                if (driver == null || config.isIsNeedRenew() || !isLoggedIn()) {
                    try {
                        renewWindows();
                    } catch (Exception ex) {
                        config.getTaDetails().append("\nERROR: " + ex.getMessage());
                    }
                }
                if (childControllers != null && childControllers.size() > 0) {
                    for (ChildController childController : childControllers) {
                        try {
                            if (childController.isIsActive()) {
                                childController.setActiveWindow();
                                childController.placeBet();
                            }
                        } catch (ArenaNotAvailable ex) {
                            config.getTaDetails().append("\nArena [" + childController.getArena() + "] is not available! Removing the arena from the active list.");
                            config.setIsNeedRenew(true);
                        } catch (NoSuchElementException e) {
                            config.getTaDetails().append("\nArena [" + childController.getArena() + "] is not available! Removing the arena from the active list.");
                            config.getTaDetails().append("\n" + e.getMessage());
                            config.setIsNeedRenew(true);
                        } catch (SessionNotFoundException ex) {
                            config.getTaDetails().append("\nDriver seems to be died. Killing now.....");
                            config.setIsNeedRenew(true);
                        } catch (Exception ex) {
                            Logger.getLogger(SingleThread.class.getName()).log(Level.SEVERE, null, ex);
                            config.setIsNeedRenew(true);
                        }
                    }
                } else {
                    config.setIsKeepRunning(false);
                }
                if (isNeedRenew()) {
                    config.setIsNeedRenew(true);
                }
            } catch (UnreachableBrowserException unreachEx) {
                try {
                    config.setIsNeedRenew(true);
                } catch (Exception ex) {
                    System.out.println("------------------\nUNREACHABLE BROWSER EXCEPTION\n--------------------");
                }
            } catch (Exception ex) {
                System.out.println("----------------\nTOP LEVEL EXCEPTION HANDLER\n---------------");
                Logger.getLogger(SingleThread.class.getName()).log(Level.SEVERE, null, ex);
                config.setIsNeedRenew(true);
            }
        }
        config.getTaDetails().append("\nquiting..........");
        driver.quit();
    }

    private void renewWindows() throws Exception {
        resetRenewTimeStamp();
        config.getTaDetails().append("\n--- Renewing..........");
        if (driver != null) {
            driver.quit();
            driver = null;
            Thread.sleep(5000);
        }
        driver = new RunningDriver().firefoxDriver();
        driver.get(config.getTargetURL());
        driver.manage().window().maximize();
        login();

        ArrayList<String> matchedArenas = new ArrayList<String>();
        if (config.getArenas().size() < 1) {
            throw new Exception("No chosen arenas. Please add some....");
        }

        for (String arena : config.getArenas()) {
            if (availableArenas.contains(arena)) {
                matchedArenas.add(arena);
            } else {
                config.getTaDetails().append("\nArena [" + arena + "] is not available at the moment.");
            }
        }

        if (matchedArenas.size() < 1) {
            config.getTaDetails().append("\nAvailable Arenas: " + availableArenas);
            config.getTaDetails().append("\nDemand Arenas: " + config.getArenas());
            throw new Exception("No matched Arena. Please check the selected Arenas!");
        }
        while (driver.getWindowHandles().size() < matchedArenas.size()) {
            driver.findElement(By.tagName("body")).sendKeys(Keys.chord(Keys.CONTROL, "n"));
        }
        childControllers = new ArrayList<ChildController>();
        for (String arena : matchedArenas) {
            childControllers.add(new ChildController(driver, arena, config));
        }
        for (String windowName : driver.getWindowHandles()) {
            for (ChildController childController : childControllers) {
                if (childController.getWindowName() == null) {
                    childController.setWindowName(windowName);
                    break;
                }
            }
        }
        config.setIsNeedRenew(false);
    }

    private boolean isLoggedIn() {
        driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
        for (WebElement webElement : driver.findElements(By.id("site-username"))) {
            if (webElement.getText().toLowerCase().equals(config.getUsername())) {
                return true;
            }
        }
        return false;
    }

    private void login() {
        driver.findElement(By.id("close-popup")).click();
        driver.findElement(By.id("username")).sendKeys(config.getUsername());
        driver.findElement(By.id("password")).sendKeys(config.getPassword());
        driver.findElement(By.id("btnLogin")).click();
        driver.findElement(By.xpath("//a[@class='button3' and contains(text(),'Agree')]")).click();
        driver.findElement(By.xpath("//a[@class='button3' and contains(text(),'Proceed')]")).click();
        List<WebElement> btnClose = driver.findElements(By.xpath("//button/span[@class='ui-button-icon-primary ui-icon ui-icon-closethick']"));
        for (WebElement we : btnClose) {
            we.click();
        }
        config.setCurrentSession(getSession());
        availableArenas = new ArrayList<String>();
        availableArenas = getAvailableArenas();
    }

    private ArrayList<String> getAvailableArenas() {
        ArrayList<String> arenas = new ArrayList<String>();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        try {
            WebElement btnArena = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='maincontent']/div[@class='leftcol']/div[@class='arena-ctn cf']/a[@class='arena-ctn-itm left active']"))));
        } catch (Exception ex) {
            Logger.getLogger(SingleThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<WebElement> list = driver.findElements(By.xpath("//div[@class='maincontent']/div[@class='leftcol']/div[@class='arena-ctn cf']/a"));
        for (WebElement arena : list) {
            if (arena.isDisplayed()) {
                arenas.add(arena.getText());
            }
        }
        return arenas;
    }

    private String getSession() {
        String url = driver.getCurrentUrl();
        String[] allParams = url.split("/");
        String[] specificParams = allParams[allParams.length - 1].split("&");
        for (String param : specificParams) {
            if (param.contains("session")) {
                String[] paramPair = param.split("=");
                return paramPair[paramPair.length - 1];
            }
        }
        return null;
    }

    private boolean isNeedRenew() {
        if ((lastRenewTimeStamp != null) && (((((new Date()).getTime() - lastRenewTimeStamp.getTime())) / 1000) > 600)) {
            ArrayList<String> arenas = getAvailableArenas();
            for (String arena : arenas) {
                if (!availableArenas.contains(arena)) {
                    if (config.getArenas().contains(arena)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void resetRenewTimeStamp() {
        lastRenewTimeStamp = new Date();
    }
}
