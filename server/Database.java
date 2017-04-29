package server;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Michael on 2017/4/28.
 */
public class Database {
    private static Connection connection = null;
    private static Statement statement = null;
    private static final String serverName = "127.0.0.1";
    private static final String database = "im";
    private static final String url = "jdbc:mysql://" + serverName + "/" + database + "?useSSL=false";
    private static final String user = "root";
    private static final String password = "mysql";
    public static boolean logIn(String name, String pwd) {
        boolean result = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM users WHERE NAME = '" + name + "';");
            if (rs.next() && rs.getString("pwd").equals(pwd)) {
                result = true;
            }
        } catch (Exception e) {
            System.out.println("Database:数据库logIn错误！");
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public static boolean signUp(String name, String pwd) {
        boolean result = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM users WHERE NAME = '" + name + "';");
            if (rs.next()) {
                result = false;
            } else {
                statement.executeUpdate("INSERT INTO users VALUES ('" + name + "','" + pwd + "');");
                result = true;
            }
        } catch (Exception e) {
            System.out.println("数据库signUp错误！");
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public static void loadUserList(ArrayList<String> userList) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT name FROM users;");
            while (rs.next()) {
                userList.add(rs.getString("name"));
            }
        } catch (Exception e) {
            System.out.println("数据库loadList错误！");
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
