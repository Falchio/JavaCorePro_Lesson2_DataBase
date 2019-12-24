package server;

import java.sql.*;

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
//            changeUserName("user333", "user3");
            System.out.println("connection to data base");

            searchUser("log1","pass1");


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
            ResultSet resultSet = authStatement.executeQuery("SELECT login, password FROM users WHERE login = '"+ login +"' AND password ='"+password +"'");

            while (resultSet.next()){
                if (resultSet.getString("login").equals(login) && resultSet.getString("password").equals(password)){
                    System.out.println("true2");
                }
                else {
                    System.out.println("false");
                }
                System.out.println(resultSet.getString("login") + " " + resultSet.getString("password"));
            }


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }


}
