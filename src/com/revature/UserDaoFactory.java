package com.revature;

import java.sql.SQLException;

public class UserDaoFactory {
    static UserDao userDao;

    private UserDaoFactory(){

    }
    static UserDao getUserDao() throws SQLException {
        if(userDao == null){
            userDao = new UserDaoImp1();
        }
        return userDao;
    }
}
