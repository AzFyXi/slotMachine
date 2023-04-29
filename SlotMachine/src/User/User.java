package User;

import slotmachine.FreeAttempt;

import java.util.Collection;

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
    public boolean haveMoney(int moneyBet) {
        if(moneyBet>=this.money) {
            takeMoneyBet(moneyBet);
        }
        return moneyBet>=this.money;
    }
    public boolean haveFreeAttempts() {
        return freeAttempts!= null;
    }

}
