package com.revature;

public class Account {
    private String username;
    private double balance;
    private double deposits;
    private double withdraws;
    private double transfers;
    private double investments;

    public Account(String username, double balance, double deposits, double withdraws, double transfers, double investments) {
        this.username = username;
        this.balance = balance;
        this.deposits = deposits;
        this.withdraws = withdraws;
        this.transfers = transfers;
        this.investments = investments;
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getDeposits() {
        return deposits;
    }

    public void setDeposits(double deposits) {
        this.deposits = deposits;
    }

    public double getWithdraws() {
        return withdraws;
    }

    public void setWithdraws(double withdraws) {
        this.withdraws = withdraws;
    }


    public double getTransfers() {
        return transfers;
    }

    public void setTransfers(double transfers) {
        this.transfers = transfers;
    }

    public double getInvestments() {
        return investments;
    }

    public double setInvestments(double investments, String username) {
        return investments;
    }

    public String toString() {
        return "Username: " + username + " | Balance: " + balance + " | Deposit: " + deposits + " | Withdraw: " + withdraws + " | Transfer: " + transfers + " | Investments: " + investments;
    }
}
