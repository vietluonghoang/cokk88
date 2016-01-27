/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dagaz.controllers;

import java.util.ArrayList;

/**
 *
 * @author Hyungin Choi
 */
public class Controller {

    private ArrayList<Configuration> configs;
    private ArrayList<Thread> threads;

    public Controller(ArrayList<Configuration> configs) {
        this.configs = configs;
        initThreads();
    }

    public ArrayList<Configuration> getConfigs() {
        return configs;
    }

    public void setConfigs(ArrayList<Configuration> configs) {
        this.configs = configs;
    }

    public void initThreads() {
        threads = new ArrayList<>();
        for (Configuration cnfg : configs) {
            Thread thrd = new Thread(new SingleThread(cnfg));
            threads.add(thrd);
        }
    }

    public void runAll() {
        initThreads();
        for (Thread thrd : threads) {
            thrd.start();
        }
    }

}
