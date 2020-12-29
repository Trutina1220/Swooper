package sample;

import java.sql.*;

public class Database {
    String userName ="fvLuF0YWF7";
    String password ="FH81vMRjVU";
    String host ="jdbc:mysql://remotemysql.com:3306/fvLuF0YWF7";
    String sql = "select * from ";
    String employeeTable ="fvLuF0YWF7.Employee";
    public Connection connection;
    public Connection getConnection(){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(host,userName,password);
        }catch (Exception e){
            e.printStackTrace();
        }

        return connection;
    }
    public void printAllEmployee() {
        try {
            Connection con = DriverManager.getConnection(this.host, this.userName, this.password);
            Statement stat = con.createStatement();

            ResultSet rs = stat.executeQuery(sql + this.employeeTable);

            while (rs.next()) {
                String id = rs.getString("employee_id");
                String name = rs.getString("employee_name");
                String position = rs.getString("employee_position");
                String phone = rs.getString("employee_phone_number");
                String email = rs.getString("employee_email");
                String t = id + " " + name + " " + position + "  " + phone + "  " + email;
                System.out.println(t);

            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

    }
}
