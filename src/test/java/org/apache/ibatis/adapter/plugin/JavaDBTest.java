package org.apache.ibatis.adapter.plugin;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Test for Java DB
 *
 * @author philo
 * @create 2018-05-09 11:19 AM
 **/
public class JavaDBTest {
    static Connection conn;

    @Test
    public void runJavaDBTest() {
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        String connectionURL = "jdbc:derby:myDatabase;create=true";
        String createString = "CREATE TABLE Employee (NAME VARCHAR(32) NOT NULL, ADDRESS VARCHAR(50) NOT NULL)";
        try {
            Class.forName(driver);
        } catch (java.lang.ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(connectionURL);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(createString);

            PreparedStatement psInsert = conn.prepareStatement("insert into Employee values (?,?)");

            psInsert.setString(1, "Philo He");
            psInsert.setString(2, "No.735, ShengXia Road, Shanghai");

            psInsert.executeUpdate();

            Statement stmt2 = conn.createStatement();
            ResultSet rs = stmt2.executeQuery("select * from Employee");
            int num = 0;
            while (rs.next()) {
                System.out.println(++num + ": Name: " + rs.getString(1) + "\n Address" + rs.getString(2));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

