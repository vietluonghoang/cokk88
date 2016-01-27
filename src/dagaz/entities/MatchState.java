/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dagaz.entities;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author Hyungin Choi
 */
public class MatchState {

    private boolean lastState;
    private boolean stateChanged;
    private Date lastStateChangeTimeStamp;
    private ArrayList<String> homeBetRate;
    private int homeBetTrend;
    private ArrayList<String> drawBetRate;
    private int drawBetTrend;
    private ArrayList<String> awayBetRate;
    private int awayBetTrend;
    private int matchNumber;
    private WebDriver driver;
    private boolean isPlaced = false;

    public MatchState(WebDriver driver) {
        this.driver = driver;
        this.matchNumber = Integer.parseInt(driver.findElement(By.id("match-number")).getText());
    }

    public boolean isLastState() {
        return lastState;
    }

    public boolean isStateChanged() {
        return stateChanged;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public ArrayList<String> getHomeBetRate() {
        return homeBetRate;
    }

    public int getHomeBetTrend() {
        return homeBetTrend;
    }

    public ArrayList<String> getDrawBetRate() {
        return drawBetRate;
    }

    public int getDrawBetTrend() {
        return drawBetTrend;
    }

    public ArrayList<String> getAwayBetRate() {
        return awayBetRate;
    }

    public int getAwayBetTrend() {
        return awayBetTrend;
    }

    public float getBetOpenElapsedTime() {
        if (lastStateChangeTimeStamp == null) {
            return 0;
        }
        return (((new Date()).getTime() - lastStateChangeTimeStamp.getTime())) / 1000;
    }

    public boolean isIsPlaced() {
        return isPlaced;
    }

    public void setIsPlaced(boolean isPlaced) {
        this.isPlaced = isPlaced;
    }

    public void updateState() {
        boolean currentState = isConfirmed();
        if (lastState != currentState) {
            lastStateChangeTimeStamp = new Date();
            lastState = currentState;
            stateChanged = true;
            if (currentState) {
                homeBetRate = new ArrayList<String>();
                drawBetRate = new ArrayList<String>();
                awayBetRate = new ArrayList<String>();
            }
        } else {
            stateChanged = false;
        }
        if (currentState) {
            String home = driver.findElement(By.id("choose-meron")).getText();
            if (homeBetRate.size() == 0) {
                homeBetRate.add(home);
                homeBetTrend = 0;
            } else {
                if (!homeBetRate.get(homeBetRate.size() - 1).equals(home)) {
                    if (abs(Float.parseFloat(homeBetRate.get(homeBetRate.size() - 1))) > abs(Float.parseFloat(home))) {
                        homeBetTrend = -1;
                    } else {
                        homeBetTrend = 1;
                    }
                    homeBetRate.add(home);
                }
            }

            String draw = driver.findElement(By.id("choose-draw")).getText();
            if (drawBetRate.size() == 0) {
                drawBetRate.add(draw);
                drawBetTrend = 0;
            } else {
                if (!drawBetRate.get(drawBetRate.size() - 1).equals(draw)) {
                    if (abs(Float.parseFloat(drawBetRate.get(drawBetRate.size() - 1))) > (Float.parseFloat(draw))) {
                        drawBetTrend = -1;
                    } else {
                        drawBetTrend = 1;
                    }
                    drawBetRate.add(draw);
                }
            }

            String away = driver.findElement(By.id("choose-wala")).getText();
            if (awayBetRate.size() == 0) {
                awayBetRate.add(away);
                awayBetTrend = 0;
            } else {
                if (!awayBetRate.get(awayBetRate.size() - 1).equals(away)) {
                    if (abs(Float.parseFloat(awayBetRate.get(awayBetRate.size() - 1))) > abs(Float.parseFloat(away))) {
                        awayBetTrend = -1;
                    } else {
                        awayBetTrend = 1;
                    }
                    awayBetRate.add(away);
                }
            }
        }
    }

    private boolean isConfirmed() {
        return ("Meron / Wala confirmed".equals(driver.findElement(By.id("match-confirm")).getText()) && "Bet now open".equals(driver.findElement(By.id("match-des")).getText()));
    }
}
