/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dagaz.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JTextArea;

/**
 *
 * @author Hyungin Choi
 */
public class Configuration {

    private String targetURL;
    private String username;
    private String password;
    private ArrayList<String> arenas;
    private String currentSession;
    private boolean isNeedRenew = true;
    private Map<String, String> betSide;
    private Map<String, String> betCoin;
    private Map<String, ArrayList<Integer>> betOnly;
    private JTextArea taDetails;
    private boolean isKeepRunning = true;

    public Configuration(String targetURL, String username, String password, ArrayList<String> arenas, JTextArea taDetails) {
        this.targetURL = targetURL;
        this.username = username;
        this.password = password;
        this.arenas = arenas;
        this.taDetails = taDetails;
        betCoin = new HashMap<String, String>();
        betSide = new HashMap<String, String>();
        betOnly = new HashMap<String, ArrayList<Integer>>();
    }

    public String getTargetURL() {
        return targetURL;
    }

    public void setTargetURL(String targetURL) {
        this.targetURL = targetURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getArenas() {
        return arenas;
    }

    public void setArenas(ArrayList<String> arenas) {
        this.arenas = arenas;
    }

    public String getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(String currentSession) {
        this.currentSession = currentSession;
    }

    public boolean isIsNeedRenew() {
        return isNeedRenew;
    }

    public void setIsNeedRenew(boolean isNeedRenew) {
        this.isNeedRenew = isNeedRenew;
    }

    public String getBetSide(String arenaName) {
        return betSide.get(arenaName);
    }

    public void setBetSide(String arenaName, String sideName) {
        this.betSide.put(arenaName, sideName);
    }

    public void removeBetSide(String arenaName) {
        this.betSide.remove(arenaName);
    }

    public String getBetCoin(String arenaName) {
        return betCoin.get(arenaName);
    }

    public void setBetCoin(String arenaName, String coinNumber) {
        this.betCoin.put(arenaName, coinNumber);
    }

    public void removeBetCoin(String arenaName) {
        this.betCoin.remove(arenaName);
    }

    public ArrayList<Integer> getBetOnly(String arenaName) {
        return betOnly.get(arenaName);
    }

    public void setBetOnly(String arenaName, ArrayList<Integer> betCondition) {
        this.betOnly.put(arenaName, betCondition);
    }

    public void removeBetOnly(String arenaName) {
        this.betOnly.remove(arenaName);
    }

    public JTextArea getTaDetails() {
        return taDetails;
    }

    public boolean isIsKeepRunning() {
        return isKeepRunning;
    }

    public void setIsKeepRunning(boolean isKeepRunning) {
        this.isKeepRunning = isKeepRunning;
        if (!isKeepRunning) {
            this.isNeedRenew = true;
        }
    }
}
