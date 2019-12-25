package server;

import java.sql.*;

public class AuthenticationService {
    private static Connection connectionToDataBase;
    private static Statement authStatement;
    private static PreparedStatement preparedStatement;

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connectionToDataBase = DriverManager.getConnection("jdbc:sqlite:main.db");
        authStatement = connectionToDataBase.createStatement();
    }

//
//    public static void main(String[] args) {
//        try {
//            connect();
//
//            System.out.println("connection to data base");
////            registration("log","pass1","use");
////            authInChat("log1","pass1");
////            registration(" ", " ", " ");
////            changeUserName("user32", "user3");
//            getNicknameByLoginAndPassword("log3", "pass3");
//
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            disconnect();
//        }
//    }

    public static void disconnect() {
        try {
            authStatement.close();
            connectionToDataBase.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static String getNicknameByLoginAndPassword(String login, String password) {
        String userName="????";

        try {
            connect();
            preparedStatement = connectionToDataBase.prepareStatement("SELECT name FROM users WHERE login = ? AND password =?");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            userName=resultSet.getString("name");
            disconnect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userName;
    }


    public static boolean registration(String login, String password, String nickname) {
        boolean registrationResult = false;

        try {
            connect();
            preparedStatement = connectionToDataBase.prepareStatement("INSERT INTO users (login, password, name) VALUES (?,?,?)");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, nickname);
            if (preparedStatement.executeUpdate() != 0) {
                registrationResult = true;
            }
            disconnect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

         return registrationResult;
    }

    public static void changeUserName(String oldUserName, String newUserName){
        try {
            connect();
            preparedStatement = connectionToDataBase.prepareStatement("UPDATE users SET name =? WHERE name = ?");
            preparedStatement.setString(1, newUserName);
            preparedStatement.setString(2, oldUserName);
            preparedStatement.executeUpdate();
            disconnect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static boolean authInChat(String login, String password) {

        boolean authResult = false;
        try {
            connect();
            preparedStatement = connectionToDataBase.prepareStatement("SELECT login, password FROM users WHERE login = ? AND password =?");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {         // так как значения по полю логина в базе данных может быть только уникальным,
                    authResult = true;      //то проверка идет исключительно на то нашлась ли строка по условию (в .executeQuery(), что-то есть)
            }                               // а именно -- WHERE login = ? AND password =? -- если нет, то сочетание логина и пароля указано неверное
            disconnect();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return authResult;
    }


}
