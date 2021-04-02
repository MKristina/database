package DAO;

import java.sql.*;
import java.util.*;
import java.util.function.Function;

public class MyConnection {
    //final String DB_DRIVER = "oracle.jdbc.OracleDriver";
    private final Connection conn;

    public MyConnection(String username, String password, String url) throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        TimeZone timeZone = TimeZone.getTimeZone("GMT+7");
        TimeZone.setDefault(timeZone);
        Locale.setDefault(Locale.ENGLISH);

        String DB_URL = url;
        Properties DB_PROPS = new Properties();
        DB_PROPS.setProperty("user", username);
        DB_PROPS.setProperty("password", password);

        conn = DriverManager.getConnection(DB_URL, DB_PROPS);
    }


    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
            System.out.println("connection was closed");
        } else {
            System.out.println("connection is not registered");
        }
    }
    public void insert(List<String> queryList) {

        //createConnection();
        for(String query : queryList) {
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.executeUpdate(query);
//                ResultSet result = pstmt.executeQuery();
                System.out.println("INSERT");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public List<List<String>> select(String sql){
        return select(sql, result -> {
            try {
                ArrayList<String> list = new ArrayList<>(1);
                list.add(result.getString(1));
                return list;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }


    public List<List<String>> select(String sql, Function<ResultSet, List<String>> toString){
        List<List<String>> names = new LinkedList<>();
        try (Statement statement = conn.createStatement()) {
            ResultSet result = statement.executeQuery(sql);
            while (result.next()){
                names.add(toString.apply(result));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }


    public void executeQuery(String sqlQuery) throws SQLException {
        try(PreparedStatement preStatement = conn.prepareStatement(sqlQuery)) {
            preStatement.executeUpdate(sqlQuery);
            //  ResultSet result = preStatement.executeQuery();
        }
    }
}