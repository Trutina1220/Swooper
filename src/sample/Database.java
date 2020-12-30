package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class Database {
    String userName ="fvLuF0YWF7";
    String password ="FH81vMRjVU";
    String host ="jdbc:mysql://remotemysql.com:3306/fvLuF0YWF7";
    String sql = "select * from ";
    String employeeTable ="fvLuF0YWF7.Employee";
    String shopTable = "fvLuF0YWF7.Shop";
    String storageTable = "fvLuF0YWF7.Storage";
    String itemTable = "fvLuF0YWF7.Item";
    String shopStockTable = "fvLuF0YWF7.`Shop Stock`";
    String storageStockTable = "fvLuF0YWF7.`Storage Stock`";
    public Connection connection;
    public Connection getConnection(){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(host,userName,password);
            return connection;
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

    public ResultSet getShopStorageInfo(String choice)
    {
        ResultSet rsShopStorage = null;
        if (choice.contains("SH"))
        {
            try {
                Connection con = DriverManager.getConnection(this.host, this.userName, this.password);
                PreparedStatement stat = con.prepareStatement(sql + this.shopTable + " where shop_id = ?");

                stat.setString(1, choice);


                rsShopStorage = stat.executeQuery();


            } catch(SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }
        else
        {
            try {
                Connection con = DriverManager.getConnection(this.host, this.userName, this.password);
                PreparedStatement stat = con.prepareStatement(sql + this.storageTable + " where storage_id = ?");

                stat.setString(1, choice);

                rsShopStorage = stat.executeQuery();


            } catch(SQLException e)
            {
                System.out.println(e.getMessage());
            }

        }
        return rsShopStorage;
    }

    public ResultSet getShopStorageItems(String choice)
    {
        ResultSet rsShopStorageItem = null;

        if(choice.contains("SH"))
        {
            try {
                Connection con = DriverManager.getConnection(this.host, this.userName, this.password);
                PreparedStatement stat = con.prepareStatement("select `Shop Stock`.*, Items.item_description \n" +
                        "from `Shop Stock`\n" +
                        "inner join Items\n" +
                        "on `Shop Stock`.item_id = Items.item_id where shop_id = ?");

                stat.setString(1, choice);

                rsShopStorageItem = stat.executeQuery();



            }catch(SQLException e)
            {
                System.out.println(e);
            }
        }
        else
        {
            try {
                Connection con = DriverManager.getConnection(this.host, this.userName, this.password);
                PreparedStatement stat = con.prepareStatement("select `Storage Stock`.*, Items.item_description \n" +
                        "from `Storage Stock`\n" +
                        "inner join Items\n" +
                        "on `Storage Stock`.item_id = Items.item_id where storage_id = ?");

                stat.setString(1, choice);

                rsShopStorageItem = stat.executeQuery();


            }catch(SQLException e)
            {
                System.out.println(e);
            }

        }
        return rsShopStorageItem;

    }
}
