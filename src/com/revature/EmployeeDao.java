package com.revature;

import java.sql.SQLException;
import java.util.List;

interface EmployeeDao {
    void addEmployee(Employee employee) throws SQLException;
    void updateEmployee(Employee employee, int id) throws SQLException;
    void deleteEmployee(int id) throws SQLException;
    List<Employee> getEmployees() throws SQLException;
    Employee getEmployeeByEmail(String email) throws SQLException;
    public Boolean getEmployeeLogin(String email, String password) throws SQLException;

    List<Account> getUsers() throws SQLException;

    User getUser(String username) throws SQLException;
}
