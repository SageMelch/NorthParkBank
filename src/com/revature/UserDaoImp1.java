package com.revature;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImp1 implements UserDao {
    Connection connection;

    public UserDaoImp1() throws SQLException, ClassCastException {
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public void addUser(User user) throws SQLException {
        String sqlAdd = "insert into user values (?, ?, ?, ?, ?)";
        PreparedStatement ups1 = connection.prepareStatement(sqlAdd);
        ups1.setInt(1,user.getUserId());
        ups1.setString(2, user.getName());
        ups1.setString(3, user.getEmail());
        ups1.setString(4, user.getUsername());
        ups1.setString(5, user.getPassword());

        int count = ups1.executeUpdate();
        System.out.println("User Account Created!");
    }

    @Override
    public void updateUser(int id, String name, String email, String username, String password, User user) throws SQLException {
        String sqlUpdate = "update user set userId = "+id+", name = "+name+", email = "+email+", username = "+username+", password = "+password+" where username = '"+user.getUsername()+"'";
        PreparedStatement ups2 = connection.prepareStatement(sqlUpdate);
        int count = ups2.executeUpdate(sqlUpdate);
        System.out.println("User Account Updated!");

    }

    @Override
    public void deleteUser(int userId) throws SQLException {
        String sqlDelete = "delete from user where userId = ?";
        PreparedStatement ups3 = connection.prepareStatement(sqlDelete);
        ups3.setInt(1, userId);
        int count = ups3.executeUpdate();
        System.out.println("User Account Deleted!");
    }

    @Override
    public List<User> getUsers() throws SQLException {
        String sqlSelectAll = "select * from user";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sqlSelectAll);
        List<User> userList = new ArrayList<User>();
        while (rs.next()) {
            userList.add(new User
                    (rs.getInt("userId"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("username"),
                            rs.getString("password")));
        }
        return userList;
    }

    @Override
    public User getUser(User user) throws SQLException {
        String sqlSelect = "select * from user where username = '"+user.getUsername()+"'";
        PreparedStatement ps5 = connection.prepareStatement(sqlSelect);
        ResultSet rs = ps5.executeQuery();
        while(rs.next()){
            user = new User(rs.getInt("userId"),
                    rs.getString("name"),
                        rs.getString("email"),
                            rs.getString("username"),
                                rs.getString("password"));
        }
        return user;
    }

    @Override
    public Boolean getLogin(String username, String password) throws SQLException {
        String sqlLogin = "select * from user where username = '" + username + "' AND password = '" + password + "'";
        PreparedStatement ups5 = connection.prepareStatement(sqlLogin);
        ResultSet rs = ups5.executeQuery();
        while (rs.next()) {
            User user =
                    new User(rs.getInt("userId"),
                                rs.getString("name"),
                                    rs.getString("email"),
                                        rs.getString("username"),
                                            rs.getString("password"));

            if (username.equals(rs.getString("username"))) {
                if (password.equals(rs.getString("password"))) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public String getUserByUsername(String username) throws SQLException {
        String uname1 = null;
        try {
            String sqlSelectUsername = "select username from user where username = ?";
            PreparedStatement ups6 = connection.prepareStatement(sqlSelectUsername);
            ResultSet rs = ups6.executeQuery(sqlSelectUsername);
            uname1 = rs.getString("username");
            if (uname1.equals(username)) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                return uname1;
            } else {
                System.out.println("No Username Exists!");
            }
        } catch (SQLSyntaxErrorException e) {
            e.printStackTrace();
        }
        System.out.println(uname1);
        return uname1;
    }
}