package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AuthenticationService {
    private static Connection connection;
    private static Statement authStatement;

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:main.db");
        authStatement = connection.createStatement();
    }

    public static void main(String[] args) {
        try {
            connect();
//            registration("log3", "pass3", "user33");
            changeUserName("user333", "user3");


            System.out.println("connection to data base");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            disconnect();
        }
    }

    public static void disconnect() {
        try {
            authStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public  String getNicknameByLoginAndPassword(String login, String password) {
        return null;
    }


    public static boolean registration(String login, String password, String nickname) {
        try {
            connect();
            authStatement.executeUpdate("INSERT INTO users (login, password, name) VALUES ('"+login+" ', '" + password +"', '"+ nickname +"')");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static void changeUserName(String oldUserName, String newUserName){
        try {
            connect();
            authStatement.executeUpdate("UPDATE users SET name ='"+newUserName+"' WHERE name = '"+ oldUserName +"'");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static boolean searchUser(String login, String password){
        try {
            connect();
            authStatement.executeUpdate("SELECT (login, name) FROM users WHERE");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }


}
