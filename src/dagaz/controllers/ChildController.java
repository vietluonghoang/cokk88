/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dagaz.controllers;

import dagaz.entities.MatchState;
import dagaz.exception.ArenaNotAvailable;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.StaticImageScreenRegion;

/**
 *
 * @author Hyungin Choi
 */
public class ChildController {

    private boolean isActive = true;
    private WebDriver driver;
    private String arena;
    private String windowName;
    private Configuration config;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private MatchState match;
    private int homeWin = 0;
    private int awayWin = 0;
    private int drawWin = 0;
    private boolean isWaitForMatch = false;
    private boolean isAlreadyNotice = false;
    private String imgRoot = "./img/";
    private int channel = 0;

    public ChildController(WebDriver driver, String arena, Configuration config) {
        this.driver = driver;
        this.arena = arena;
        this.config = config;
        this.config.setIsKeepRunning(true);
    }

    public ChildController(WebDriver driver, Configuration config) {
        this.driver = driver;
        this.config = config;
    }

    public String getWindowName() {
        return windowName;
    }

    public void setWindowName(String windowName) {
        this.windowName = windowName;
    }

    public String getArena() {
        return arena;
    }

    public boolean isIsActive() {
        return isActive && driver != null;
    }

    public void placeBet() throws ArenaNotAvailable {
        if (isActive) {
            if (!driver.getCurrentUrl().contains(config.getCurrentSession())) {
                driver.get(config.getTargetURL() + "?session=" + config.getCurrentSession());
                driver.manage().window().maximize();
            }
            if (!isReady()) {
                WebDriverWait wait = new WebDriverWait(driver, 10);
                try {
                    WebElement btnArena = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='maincontent']/div[@class='leftcol']/div[@class='arena-ctn cf']/a[text()='" + arena + "']"))));
                    btnArena.click();
                } catch (Exception ex) {
                    throw new ArenaNotAvailable(arena);
                }
            }

            if(driver.findElements(By.xpath("//span[@class='ui-dialog-title']")).size()>0){
                closeDialog();
            }
            if (hasMatch()) {
                if ((match == null) || (match.getMatchNumber() != Integer.parseInt(driver.findElement(By.id("match-number")).getText()))) {
                    if (match != null) {
                        getResult(match.getMatchNumber());
                    } else {
                        this.homeWin = Integer.parseInt(driver.findElement(By.id("banker-win")).getText());
                        this.awayWin = Integer.parseInt(driver.findElement(By.id("player-win")).getText());
                        this.drawWin = Integer.parseInt(driver.findElement(By.id("win-win")).getText());
                    }
                    match = new MatchState(driver);
                }
                match.updateState();
                if (match.isLastState() && !match.isIsPlaced()) {
                    if (driver.findElements(By.xpath("//table[@id='accepted_bet']//tr")).size() < 2) {
                        try {
                            if (!isConnectionReady()) {
                                switchChannel();
                            }
                            betNow();
                        } catch (Exception e) {
                            config.getTaDetails().append("\n[ERROR]\n\t[" + arena + "] Failed to place bet.");
                        }
                    }
                    if (!match.isLastState() && match.isStateChanged() && !match.isIsPlaced()) {
                        config.getTaDetails().append("\n- [" + arena + "] - [" + match.getMatchNumber() + "] Missssssssss...................");
                    }
                }
            } else {
                if (!isWaitForMatch && !isAlreadyNotice) {
                    config.getTaDetails().append("\n[" + arena + "] No matches available.");
                    isAlreadyNotice = true;
                }
            }
        }
    }

    private StaticImageScreenRegion videoFooterSection() {
        try {
            WebElement element = driver.findElement(By.id("divFlashHolder"));
            Point point = element.getLocation();
            File ss = (File) ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            BufferedImage src = ImageIO.read(ss);
            BufferedImage screen = src.getSubimage(point.getX(), point.getY() + 300, element.getSize().getWidth(), element.getSize().getHeight() - 300);
            return new StaticImageScreenRegion(screen);
        } catch (IOException ex) {
            Logger.getLogger(ChildController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private void switchChannel() {
        try {
            StaticImageScreenRegion region = videoFooterSection();
            if (region != null) {
                List<ScreenRegion> re = region.findAll(new ImageTarget(ImageIO.read(new File(imgRoot + "wifi.png"))));
                if (re.size() < 1) {
                    throw new IOException("Failed to find the wifi icon.");
                } else {
                    for (ScreenRegion screenRegion : re) {
                        Actions action = new Actions(driver);
                        int nextChannel = 0;
                        if (channel == 5) {
                            nextChannel = 1;
                        } else {
                            nextChannel = channel + 1;
                        }
                        WebElement element = driver.findElement(By.id("divFlashHolder"));
                        action.moveToElement(element, screenRegion.getCenter().getX() + 40,
                                300 + screenRegion.getCenter().getY()).click().build().perform();
                        action = new Actions(driver);
                        action.moveToElement(element, screenRegion.getCenter().getX() + 40,
                                300 + screenRegion.getCenter().getY() - (120 - (nextChannel * 20))).click().build().perform();
                        channel = nextChannel;
                        config.getTaDetails().append("\n\t ---- Connection trouble ----\n\t[" + arena + "] - Switching to channel #" + channel + "\n\t---------------");
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ChildController.class.getName()).log(Level.SEVERE, null, ex);
            config.getTaDetails().append("\n- [" + arena + "] - Failed to switch channel.");
        }
    }

    private boolean isConnectionReady() {
        try {
            StaticImageScreenRegion region = videoFooterSection();
            if (region == null) {
                return false;
            }
            List<ScreenRegion> result = region.findAll(new ImageTarget(ImageIO.read(new File(imgRoot + "meron.png"))));
            if (result.size() < 1) {
                StaticImageScreenRegion retriedRegion = videoFooterSection();
                List<ScreenRegion> retried = retriedRegion.findAll(new ImageTarget(ImageIO.read(new File(imgRoot + "meron.png"))));
                if (retried.size() < 1) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } catch (IOException ex) {
            Logger.getLogger(ChildController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private void betNow() {
        if (isAboutToClose()) {
            WebElement btnBet = driver.findElement(By.id("choose-" + config.getBetSide(arena).toLowerCase()));
            config.getTaDetails().append("\n- [" + arena + "] - [" + match.getMatchNumber() + "]");
            config.getTaDetails().append("\n\tMeron Bet Rate: " + match.getHomeBetRate() + " - Trend: " + match.getHomeBetTrend());
            config.getTaDetails().append("\n\tWala Bet Rate: " + match.getAwayBetRate() + " - Trend: " + match.getAwayBetTrend());
            config.getTaDetails().append("\n\tDraw Bet Rate: " + match.getDrawBetRate() + " - Trend: " + match.getDrawBetTrend());
            if (btnBet.isEnabled() && isMatchCondition()) {
                btnBet.click();
                driver.findElement(By.id("input-stake")).sendKeys(config.getBetCoin(arena));
                driver.findElement(By.id("place-bet")).click();
                closeDialog();
                config.getTaDetails().append("\n\tSide: " + config.getBetSide(arena) + " - Coin: " + config.getBetCoin(arena));
                config.getTaDetails().append("\n\tPlace bet after " + match.getBetOpenElapsedTime() + "s");
                match.setIsPlaced(true);
            } else {
                config.getTaDetails().append("\n\tSkippppp...........Not match the condition.");
                match.setIsPlaced(true);
            }
        }
        if (!isConnectionReady() && match.getBetOpenElapsedTime() > 40) {
            WebElement btnBet = driver.findElement(By.id("choose-" + config.getBetSide(arena).toLowerCase()));
            config.getTaDetails().append("\n- [" + arena + "] - [" + match.getMatchNumber() + "]");
            config.getTaDetails().append("\n ---- Connection trouble ----");
            config.getTaDetails().append("\n\tMeron Bet Rate: " + match.getHomeBetRate() + " - Trend: " + match.getHomeBetTrend());
            config.getTaDetails().append("\n\tWala Bet Rate: " + match.getAwayBetRate() + " - Trend: " + match.getAwayBetTrend());
            config.getTaDetails().append("\n\tDraw Bet Rate: " + match.getDrawBetRate() + " - Trend: " + match.getDrawBetTrend());
            if (btnBet.isEnabled() && isMatchCondition()) {
                btnBet.click();
                driver.findElement(By.id("input-stake")).sendKeys(config.getBetCoin(arena));
                driver.findElement(By.id("place-bet")).click();
                closeDialog();
                config.getTaDetails().append("\n\tSide: " + config.getBetSide(arena) + " - Coin: " + config.getBetCoin(arena));
                config.getTaDetails().append("\n\tPlace bet after " + match.getBetOpenElapsedTime() + "s");
                match.setIsPlaced(true);
            } else {
                config.getTaDetails().append("\n\tSkippppp...........Not match the condition.");
                match.setIsPlaced(true);
            }
        }
    }

    private void closeDialog() {
        WebDriverWait wait = new WebDriverWait(driver, 3);
        try {
            WebElement ttlDialog = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[@class='ui-dialog-title']"))));
            if (ttlDialog.getText().equals("Match Cancelled")) {
                driver.findElement(By.xpath("//button[@class='ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close']")).click();
            }
            if (ttlDialog.getText().equals("...")) {
                driver.findElement(By.xpath("//div[@class='ui-dialog-buttonset']/button/span[text()='Yes']")).click();
            }
        } catch (Exception ex) {
        }
    }

    private boolean isMatchCondition() {
        switch (config.getBetSide(arena).toLowerCase()) {
            case "wala":
                return config.getBetOnly(arena).contains(match.getAwayBetTrend());
            case "meron":
                return config.getBetOnly(arena).contains(match.getHomeBetTrend());
            case "draw":
                return config.getBetOnly(arena).contains(match.getDrawBetTrend());
            default:
                return false;
        }
    }

    private boolean isAboutToClose() {
        try {
            StaticImageScreenRegion region = videoFooterSection();
            if (region == null) {
                return false;
            }
            if (region.findAll(new ImageTarget(ImageIO.read(new File(imgRoot + "ending-green.png")))).size() > 0
                    || region.findAll(new ImageTarget(ImageIO.read(new File(imgRoot + "ending-yellow.png")))).size() > 0
                    || region.findAll(new ImageTarget(ImageIO.read(new File(imgRoot + "ending-red.png")))).size() > 0) {
                return true;
            }
            return false;
        } catch (IOException ex) {
            Logger.getLogger(ChildController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private void getResult(int matchNumber) {
        int homeWin = Integer.parseInt(driver.findElement(By.id("banker-win")).getText());
        int awayWin = Integer.parseInt(driver.findElement(By.id("player-win")).getText());
        int drawWin = Integer.parseInt(driver.findElement(By.id("win-win")).getText());
        if (this.homeWin < homeWin) {
            if (homeWin == (this.homeWin + 1)) {
                config.getTaDetails().append("\n[RESULT] \n\t- [" + arena + "] - [" + matchNumber + "] Meron win.");
            }
            this.homeWin = homeWin;
        }
        if (this.awayWin < awayWin) {
            if (awayWin == (this.awayWin + 1)) {
                config.getTaDetails().append("\n[RESULT] \n\t- [" + arena + "] - [" + matchNumber + "] Wala win.");
            }
            this.awayWin = awayWin;
        }
        if (this.drawWin < drawWin) {
            if (drawWin == (this.drawWin + 1)) {
                config.getTaDetails().append("\n[RESULT] \n\t- [" + arena + "] - [" + matchNumber + "] Draw.");
            }
            this.drawWin = drawWin;
        }
    }

    private boolean isReady() {
        if ((driver.findElements(By.xpath("//div[@class='maincontent']/div[@class='leftcol']/div[@class='arena-ctn cf']/a[text()='"
                + arena + "' and @class = 'arena-ctn-itm left active']")).size() > 0)
                && driver.getCurrentUrl().contains("arena=")) {
            return true;
        }
        return false;
    }

    private boolean hasMatch() {
        List<WebElement> matches = driver.findElements(By.id("match-number"));
        if (matches.size() > 0) {
            try {
                if (Integer.parseInt(matches.get(0).getText()) > 0) {
                    isWaitForMatch = false;
                    isAlreadyNotice = false;
                    return true;
                }
            } catch (Exception e) {
                isWaitForMatch = true;
                return false;
            }
        }
        isWaitForMatch = true;
        return false;
    }

    public void shutdownWindow() {
        if (isActive) {
            driver.switchTo().window(windowName).close();
            this.isActive = false;
        }
    }

    public void setActiveWindow() {
        if (isActive) {
            driver.switchTo().window(windowName);
        }
    }
}
