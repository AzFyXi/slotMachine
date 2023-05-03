package User;

import SlotMachine.FreeAttempt;

import java.util.*;

public class User {
    private String name;
    private int money;
    private int moneyBet;
    Collection<FreeAttempt> freeAttempts;

    //Constructor
    public User(String name, int money, Collection<FreeAttempt> freeAttempts) {
        this.name = name;
        this.money = money;
        this.freeAttempts = freeAttempts;
    }

    public User(String name, int money) {
        this.name = name;
        this.money = money;
        this.freeAttempts = null;
    }

    //Getters and Setters
    public String getName() {
        return name;
    }
    public int getMoney() {
        return money;
    }
    public Collection<FreeAttempt> getFreeAttempts() {
        return freeAttempts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setFreeAttempts(Collection<FreeAttempt> freeAttempts) {
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

    //toString
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", money=" + money +
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
        return freeAttempts!= null;
    }

    public boolean useFreeAttempts() { // Uses the user's free attempts
        if (haveFreeAttempts()) {
            FreeAttempt currentFreeAttempt = null;
            for (FreeAttempt freeAttempt : freeAttempts) {
                currentFreeAttempt = freeAttempt;
                break; // We only want the first element
            }

            if (currentFreeAttempt != null) {
                currentFreeAttempt.useRow();

                if (currentFreeAttempt.getRowRemaining() == 0) {
                    Collection<FreeAttempt> updatedFreeAttempts = new ArrayList<>(freeAttempts);
                    updatedFreeAttempts.remove(currentFreeAttempt);
                    freeAttempts = updatedFreeAttempts;
                }
            }
            return true;
        } else {
            this.money -= this.moneyBet; //Remove the money bet
        }
        return false;
    }



}
