package com.revature;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountDaoImp1 implements AccountDao {
    Connection connection;
    public AccountDaoImp1() throws SQLException{
        this.connection = ConnectionFactory.getConnection();
    }
    @Override
    public Account Balance(User user) throws SQLException {
        String sqlBalance = "select balance from account where username = '"+user.getUsername()+"'";
        PreparedStatement ups8 = connection.prepareStatement(sqlBalance);
        ResultSet rs = ups8.executeQuery();
        Account account = new Account(user.getUsername(),rs.getDouble("balance"),
                    rs.getDouble("deposits"),
                        rs.getDouble("withdraws"),
                            rs.getDouble("transfers"),
                                rs.getDouble("investments"));
        return account;
    }

    @Override
    public void Deposit(double deposit, User user) throws SQLException {
        String sqlDeposit = "update account set balance = balance + '"+deposit+"', deposits = deposits + '"+deposit+"' where username = '"+user.getUsername()+"'";
        PreparedStatement ups2 = connection.prepareStatement(sqlDeposit);
        if(deposit < 0){
            System.out.println("Invalid Input");
        }else{
            int count = ups2.executeUpdate(sqlDeposit);
        }
    }

    @Override
    public void Withdraw(double withdraw, User user) throws SQLException {
        String sqlWithdraw = "update account set balance = balance - "+withdraw+", withdraws = withdraws + "+withdraw+" where username = '"+user.getUsername()+"'";
        PreparedStatement ups3 = connection.prepareStatement(sqlWithdraw);
        try{
            if(withdraw < 0){
                System.out.println("Invalid Input");
            }else{
                int count = ups3.executeUpdate(sqlWithdraw);
            }
        }catch (NullPointerException ex){
            ex.printStackTrace();
            System.out.println("Not Enough Funds!");
        }
    }

    @Override
    public void Transfer(Account account, String username, double transfer) throws SQLException {
        String sqlTransferFrom = "update account set balance = balance - " + transfer + ", transfers = transfers - " + transfer + " where username = '" + account.getUsername() + "'";
        String sqlTransferTo = "update account set balance = balance + " + transfer + ", transfers = transfers + " + transfer + " where username = '" + username + "'";
        PreparedStatement ups4_1 = connection.prepareStatement(sqlTransferFrom);
        PreparedStatement ups4_2 = connection.prepareStatement(sqlTransferTo);
        if (transfer < 0) {
            System.out.println("Invalid Funds");
        } else if (transfer > account.getBalance()) {
            System.out.println("Invalid Funds");
        } else {
            if(account.getUsername().equals(username)){
                System.out.println("Invalid Username!");
            }else if(account.getUsername() == null){
                System.out.println("Invalid Username!");
            }else{
                int count1 = ups4_1.executeUpdate();
                int count2 = ups4_2.executeUpdate();
            }
        }
    }


    @Override
    public void Investments(Account account, User user) throws SQLException {
        String sqlInvestments = "update account set investments = investments + balance, balance = balance - balance where username = '"+user.getUsername()+"'";
        PreparedStatement ups5 = connection.prepareStatement(sqlInvestments);
        int count = ups5.executeUpdate();
    }

    @Override
    public List<Account> getAccounts() throws SQLException {
        String sqlSelectAll = "select * from account";
        PreparedStatement ups6 = connection.prepareStatement(sqlSelectAll);
        ResultSet rs = ups6.executeQuery(sqlSelectAll);
        List<Account> accountList = new ArrayList<>();
        while(rs.next()){
            accountList.add(new Account(rs.getString("username"),
                    rs.getDouble("balance"),
                    rs.getDouble("deposits"),
                    rs.getDouble("withdraws"),
                    rs.getDouble("transfers"),
                    rs.getDouble("investments")));
        }
        return accountList;
    }

    @Override
    public Account getAccount(String username) throws SQLException {
        String sqlSelect = "select * from account where username = '" + username + "'";
        PreparedStatement ups7 = connection.prepareStatement(sqlSelect);
        ResultSet rs = ups7.executeQuery(sqlSelect);
        while (rs.next()) {
            return new Account(rs.getString("username"),
                    rs.getDouble("balance"),
                    rs.getDouble("deposits"),
                    rs.getDouble("withdraws"),
                    rs.getDouble("transfers"),
                    rs.getDouble("investments"));
        }
        return null;
    }
    @Override
    public void addAccount(Account account, User user) throws SQLException {
        String sqlAdd = "insert into account values (?, ?, ?, ?, ?, ?)";
        PreparedStatement ups1 = connection.prepareStatement(sqlAdd);
        ups1.setString(1, user.getUsername());
        ups1.setDouble(2, account.getBalance());
        ups1.setDouble(3, account.getDeposits());
        ups1.setDouble(4, account.getWithdraws());
        ups1.setDouble(5, account.getTransfers());
        ups1.setDouble(6, account.getInvestments());

        int count = ups1.executeUpdate();
        System.out.println("Account Created!");
    }

}
