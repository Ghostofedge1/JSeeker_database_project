package com.example.jseeker;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class MSConnection {
    public static Connection connect() {
        String url = "jdbc:sqlserver://DESKTOP-G1E0P0N:1433;database=JSeeker_db;encrypt=true;trustServerCertificate=true;";
        String user = "user1";
        String password = "12345";
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            conn = DriverManager.getConnection(url, user, password);


        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        } finally {
            return conn;
        }
    }
//        public static void main(String[] args) throws ClassNotFoundException {
//            Connectiontosqlserver c1=new Connectiontosqlserver();
//            Connection   conn=c1.connect();
//            try {
//                PreparedStatement  stat=conn.prepareStatement("select * from Course");
//
//                ResultSet  rs=  stat.executeQuery();
//
//                while(rs.next()){
//                    for(int i=1;i<=2;i++){
//                        System.out.print(rs.getString(i)+"  ");
//                    }
//
//                    System.out.println();
//                }
//
//            }
//            catch (SQLException e) {
//                System.out.println("Failed to connect to the database.");
//                e.printStackTrace();
//            }
//
//        }
//    }

}
