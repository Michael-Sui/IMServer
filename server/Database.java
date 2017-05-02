package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Michael on 2017/4/28.
 */
public class Database {
    private Connection connection;
    private PreparedStatement statement;
    private final String serverName;
    private final String database;
    private final String url;
    private final String user;
    private final String password;

    public Database() {
        connection = null;
        statement = null;
        serverName = "127.0.0.1";
        database = "im";
        url = "jdbc:mysql://" + serverName + "/" + database + "?useSSL=false";
        user = "root";
        password = "mysql";
    }

    public boolean logIn(String name, String pwd) {
        boolean result = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM users WHERE NAME = ?;";
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
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

    public boolean signUp(String name, String pwd) {
        boolean result = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM users WHERE NAME = ?;";
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = false;
            } else {
                sql = "INSERT INTO users VALUES (?, ?);";
                statement = connection.prepareStatement(sql);
                statement.setString(1, name);
                statement.setString(2, pwd);
                statement.execute();
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

    public void loadUserList(ArrayList<String> userList) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            String sql = "SELECT name FROM users;";
            statement = connection.prepareStatement(sql);
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
