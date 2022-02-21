package com.revature;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImp1 implements EmployeeDao {
    Connection connection;
    public EmployeeDaoImp1() throws SQLException {
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public void addEmployee(Employee employee) throws SQLException {
        String sqlAdd = "insert into employee(id, name, email, password) values (?, ?, ?, ?)";
        PreparedStatement ps1 = connection.prepareStatement(sqlAdd);
        ps1.setInt(1,employee.getId());
        ps1.setString(2, employee.getName());
        ps1.setString(3, employee.getEmail());
        ps1.setString(4, employee.getPassword());
        int count = ps1.executeUpdate();
        System.out.println("Employee Account Created!");

    }

    @Override
    public void updateEmployee(Employee employee, int id) throws SQLException {
        String sqlUpdate = "update employee set id = ?, name = ?, email = ?, password = ? where id = '"+id+"'";
        PreparedStatement ps2 = connection.prepareStatement(sqlUpdate);
        ps2.setInt(1,employee.getId());
        ps2.setString(2, employee.getName());
        ps2.setString(3, employee.getEmail());
        ps2.setString(4, employee.getPassword());
        int count = ps2.executeUpdate();
        System.out.println("Employee Account Updated!");
    }

    @Override
    public void deleteEmployee(int id) throws SQLException {
        String sqlDelete = "delete from employee where id = ?";
        PreparedStatement ps3 = connection.prepareStatement(sqlDelete);
        ps3.setInt(1,id);
        int count = ps3.executeUpdate();
        System.out.println("Employee Account Deleted!");
    }

    @Override
    public List<Employee> getEmployees() throws SQLException {
        String sqlSelectAll = "select * from employee";
        PreparedStatement ps4 = connection.prepareStatement(sqlSelectAll);
        ResultSet rs = ps4.executeQuery();
        List<Employee> empList = new ArrayList<>();
        while (rs.next()){
            empList.add(new Employee(rs.getInt("id"),
                    rs.getString("name"),
                        rs.getString("email"),
                            rs.getString("password")));
        }
        return empList;
    }

    @Override
    public Employee getEmployeeByEmail(String email) throws SQLException {
        String sqlSelect = "select * from employee where id = '"+email+"'";
        PreparedStatement ps5 = connection.prepareStatement(sqlSelect);
        ResultSet rs = ps5.executeQuery();
        while(rs.next()){
            System.out.println("Username: "+rs.getInt("id")+" | " +
                                    "Name: "+rs.getString("name")+" | " +
                                        "Email: "+rs.getString("email")+" | " +
                                            "Password "+rs.getString("password"));
        }
        return null;
    }

    public Boolean getEmployeeLogin(String email, String password) throws SQLException {
        String sqlLogin = "select email, password from user where email = '" + email + "' AND password = '" + password + "'";
        PreparedStatement ps6 = connection.prepareStatement(sqlLogin);
        ResultSet rs = ps6.executeQuery();
        while(rs.next()){
                rs.getString("email");
                rs.getString("password");
            if(email.equals(rs.getString("email"))){
                if(password.equals(rs.getString("password"))){
                    return true;
                }else {
                    return false;
                }
            }else{
                return false;
            }
        }
        return false;
    }

    @Override
    public List<Account> getUsers() throws SQLException {
        String sqlSelectAll = "select * from account";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sqlSelectAll);
        List<Account> accountList = new ArrayList<Account>();
        while (rs.next()) {
            accountList.add(new Account
                    (rs.getString("username"),
                            rs.getDouble("balance"),
                            rs.getDouble("deposits"),
                            rs.getDouble("withdraws"),
                            rs.getDouble("transfers"),
                            rs.getDouble("investments")));
        }
        return accountList;
    }

    @Override
    public User getUser(String username) throws SQLException {
        String sqlSelect = "select * from user where username = '" + username + "'";
        PreparedStatement ps5 = connection.prepareStatement(sqlSelect);
        ResultSet rs = ps5.executeQuery();
        User user = null;
        while (rs.next()) {
            user = new User(rs.getInt("userId"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("username"),
                    rs.getString("password"));
            return user;
        }
        return user;
    }
}