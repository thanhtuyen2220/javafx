package sample.Utils;

import java.sql.*;

public class SQLDatabaseConnection {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
//    private static final String CONN = "jdbc:mysql://localhost:3306/db";
    private static final String CONN = "jdbc:mysql://localhost:3306/db?useTimezone=false&serverTimezone=GMT"/*+ "&useUnicode=true&characterEncoding=UTF-8"*/;
    public static Connection getConnection() throws SQLException {
        Connection conn = null;

        try{
            conn = DriverManager.getConnection(CONN,USERNAME,PASSWORD);
        }catch(SQLException e) {
            System.err.println(e);
//        }finally{
//            if(conn != null){
//                conn.close();
//            }
//        }
        }
        return conn;
    }

}