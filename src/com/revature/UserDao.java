package com.revature;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    void addUser(User user) throws SQLException;
    void updateUser(int id, String name, String email, String username, String password, User user) throws SQLException;
    void deleteUser(int userId) throws SQLException;
    List<User> getUsers() throws SQLException;
    User getUser(User user) throws SQLException;
    Boolean getLogin(String username, String password) throws SQLException;
    String getUserByUsername(String username) throws SQLException;

}
