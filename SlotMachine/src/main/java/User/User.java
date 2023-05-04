package User;

import SlotMachine.FreeAttempt;

import java.util.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class User {
    private String name;
    private int money;
    private int moneyBet;
    private int totalEarn;
    List<FreeAttempt> freeAttempts;

    //Constructor
    public User(String name, int money, List<FreeAttempt> freeAttempts) {
        this.name = name;
        this.money = money;
        this.freeAttempts = freeAttempts;
        this.moneyBet = 0;
        this.totalEarn= -1;
    }

    public User(String name, int money) {
        this.name = name;
        this.money = money;
        this.freeAttempts = null;
        this.moneyBet= 0;
        this.totalEarn= -1;
    }

    //Getters and Setters
    public String getName() {
        return name;
    }
    public int getMoney() {
        return money;
    }
    public List<FreeAttempt> getFreeAttempts() {
        return freeAttempts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setFreeAttempts(List<FreeAttempt> freeAttempts) {
        this.freeAttempts = freeAttempts;
    }

    public void setFreeAttempts(int multiplier, int rowRemaining) {
        FreeAttempt newFreeAttempt = new FreeAttempt(multiplier, rowRemaining);
        freeAttempts.add(newFreeAttempt);
    }
    public int getMoneyBet() {
        return moneyBet;
    }

    public void setMoneyBet(int moneyBet) {
        this.moneyBet = moneyBet;
    }

    public int getTotalEarn() {
        return totalEarn;
    }

    public void setTotalEarn(int totalEarn) {
        this.totalEarn = totalEarn;
    }

    //toString

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", money=" + money +
                ", moneyBet=" + moneyBet +
                ", totalEarn=" + totalEarn +
                ", freeAttempts=" + freeAttempts +
                '}';
    }

    // Methods
    public void takeMoneyBet(int moneyBet) {
        this.money = this.money - moneyBet;
    }
    public void haveMoney(int moneyBet) {
        if(moneyBet>=this.money) {
            takeMoneyBet(moneyBet);
        } else {
            System.out.println("User doesn't have enough money");
        }
    }
    public void betMoreMoney() {
        if (this.moneyBet == 0) { this.moneyBet = 2000; }
        else if (this.moneyBet == 2000) { this.moneyBet = 4000; }
        else if (this.moneyBet == 4000) { this.moneyBet = 6000; }
        else if (this.moneyBet == 6000) { this.moneyBet = 10000; }
        else if (this.moneyBet == 10000) { return; }
    }

    public void betLessMoney() {
        if (this.moneyBet == 10000) { this.moneyBet = 6000; }
        else if (this.moneyBet == 6000) { this.moneyBet = 4000; }
        else if (this.moneyBet == 4000) { this.moneyBet = 2000; }
        else if (this.moneyBet == 2000) { this.moneyBet = 0; }
        else if (this.moneyBet == 0) { return; }
    }

    public boolean haveFreeAttempts() {
        return freeAttempts != null && !freeAttempts.isEmpty();
    }
    public boolean useFreeAttempt() {
        if (haveFreeAttempts()) {
            FreeAttempt currentFreeAttempt = freeAttempts.iterator().next();
            currentFreeAttempt.useRow();
            if (currentFreeAttempt.getRowRemaining() == 0) {
                this.freeAttempts.remove(currentFreeAttempt);
            } else if(currentFreeAttempt.getRowRemaining() > 0) {
                currentFreeAttempt.setRowRemaining(currentFreeAttempt.getRowRemaining() - 1);
                this.freeAttempts.remove(currentFreeAttempt);
                ((List<FreeAttempt>) this.freeAttempts).set(0, currentFreeAttempt);
            }

            return true;
        }
        return false;
    }
}

