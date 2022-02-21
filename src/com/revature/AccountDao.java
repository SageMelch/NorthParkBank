package com.revature;

import java.sql.SQLException;
import java.util.List;

public interface AccountDao {

    Account Balance(User user) throws SQLException;
    void Deposit(double deposit, User user)throws SQLException;
    void Withdraw(double withdraw, User user)throws SQLException;
    void Transfer(Account account, String username, double transfer) throws SQLException;
    void Investments(Account account, User user) throws SQLException;

    List<Account> getAccounts() throws SQLException;

    Account getAccount(String username) throws SQLException;
    void addAccount(Account account, User user) throws SQLException;

    String toString();

}


