package com.example.emsss.model;

public class Wallet {

    private double balance;

    public Wallet() {
        this.balance = 0.0;
    }

    public Wallet(double initialBalance) {
        this.balance = initialBalance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }


    public boolean deductFunds(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Funds deducted successfully. New balance: " + balance);
            return true;
        } else {
            System.out.println("Insufficient balance or invalid amount.");
            return false;
        }
    }
    public void addfunds(double amount) {
            balance += amount;
            System.out.println("Funds added successfully. New balance: " + balance);
    }



}
