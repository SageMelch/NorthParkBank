package com.revature;

import java.sql.*;
import java.util.ResourceBundle;


public class ConnectionFactory {

    private static Connection connection = null;

    private ConnectionFactory() {
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("com/revature/dbConfig");
            String url = resourceBundle.getString("url");
            String username = resourceBundle.getString("username");
            String password = resourceBundle.getString("password");
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
        return connection;
    }

    public PreparedStatement prepareStatement(String sql) {
        return prepareStatement(sql);
    }
}



