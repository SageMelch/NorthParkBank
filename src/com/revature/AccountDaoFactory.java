package com.revature;

import java.sql.SQLException;

public class AccountDaoFactory {
    static AccountDao accountDao;

    private AccountDaoFactory(){

    }
    static AccountDao accountUserDao() throws SQLException {
        if(accountDao == null){
            accountDao = new dao.AccountDaoImp1();
        }
        return accountDao;
    }

}