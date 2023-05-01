package User;

import slotmachine.FreeAttempt;

import java.util.*;

public class User {
    private String name;
    private int money;
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
        this.money = this.money + 1000;
    }
    public void betLessMoney() {
        this.money = this.money - 1000;
    }
    public boolean haveFreeAttempts() {
        return freeAttempts!= null;
    }

    public void useFreeAttempts() {
        if (haveFreeAttempts()) {
            FreeAttempt currentFreeAttempt = null;
            for (FreeAttempt freeAttempt : freeAttempts) {
                currentFreeAttempt = freeAttempt;
                break; // Nous ne voulons que le premier élément
            }

            if (currentFreeAttempt != null) {
                currentFreeAttempt.useRow();

                if (currentFreeAttempt.getRowRemaining() == 0) {
                    Collection<FreeAttempt> updatedFreeAttempts = new ArrayList<>(freeAttempts);
                    updatedFreeAttempts.remove(currentFreeAttempt);
                    freeAttempts = updatedFreeAttempts;
                }
            }
        }
    }



}
