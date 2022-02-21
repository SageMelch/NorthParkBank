package com.revature;

import java.sql.SQLException;

public class EmployeeDaoFactory {
    private static EmployeeDao dao;

    private EmployeeDaoFactory(){

    }
    static EmployeeDao getEmployeeDao() throws SQLException {
        if(dao == null) {
            dao = new EmployeeDaoImp1();
        }
        return dao;
    }
}
