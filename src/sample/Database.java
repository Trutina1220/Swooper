package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class Database {
    String userName ="fvLuF0YWF7";
    String password ="FH81vMRjVU";
    String host ="jdbc:mysql://remotemysql.com:3306/fvLuF0YWF7";
    String selectAllQuery = "select * from ";
    String shopTable = "fvLuF0YWF7.Shop";
    String storageTable = "fvLuF0YWF7.Storage";
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

    public ResultSet getShopStorageInfo(String choice)
    {
        ResultSet rsShopStorage = null;
        if (choice.contains("SH"))
        {
            try {
                Connection con = DriverManager.getConnection(this.host, this.userName, this.password);
                PreparedStatement stat = con.prepareStatement(selectAllQuery + this.shopTable + " where shop_id = ?");

                stat.setString(1, choice);


                rsShopStorage = stat.executeQuery();

            } catch(SQLException e)
            {
                System.out.println(e.getMessage());
            }
        } else {
            try {
                Connection con = DriverManager.getConnection(this.host, this.userName, this.password);
                PreparedStatement stat = con.prepareStatement(selectAllQuery + this.storageTable + " where storage_id = ?");

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
        } else {
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




    public void insertItem(String toChoice, String itemID, Integer itemQuantity)
    {
        if(toChoice.contains("SH")) {
            try {
                Connection con = DriverManager.getConnection(this.host, this.userName, this.password);
                Statement selectStat = con.createStatement();
                ResultSet rsSelect = selectStat.executeQuery("Select * from " + shopStockTable);
                while (rsSelect.next())
                {
                    if(rsSelect.getString("item_id") == itemID && rsSelect.getString("shop_id") == toChoice)
                    {
                        PreparedStatement updateStat = con.prepareStatement("Update " + shopStockTable + " set shop_stock_quantity = shop_stock_quantity + " +
                                itemQuantity + " where item_id =? and shop_id = ?");

                        updateStat.setString(1, itemID);
                        updateStat.setString(2, toChoice);

                        Integer statusUpdate = updateStat.executeUpdate();
                        if(statusUpdate > 0)
                        {
                            Alert a = new Alert(Alert.AlertType.INFORMATION);
                            a.setTitle("Success Moving!");
                            a.setContentText("Reload the table to check!");
                            a.show();
                        }
                    } else {
                        PreparedStatement insertStat = con.prepareStatement("Insert into " + shopStockTable + " values (?,?,?)");

                        insertStat.setString(1, toChoice);
                        insertStat.setString(2, itemID);
                        insertStat.setString(3, itemQuantity.toString());

                        Integer statusInsert = insertStat.executeUpdate();
                        if(statusInsert > 0)
                        {
                            Alert a = new Alert(Alert.AlertType.INFORMATION);
                            a.setTitle("Success Insert!");
                            a.setContentText("Reload the table to check!");
                            a.show();
                        }
                    }
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        } else {
            try {
                Connection con = DriverManager.getConnection(this.host, this.userName, this.password);
                Statement selectStat = con.createStatement();
                ResultSet rsSelect = selectStat.executeQuery("Select * from " + shopStockTable);

                while(rsSelect.next())
                {
                    if(rsSelect.getString("item_id") == itemID && rsSelect.getString("storage_id") == toChoice){
                        PreparedStatement updateStat = con.prepareStatement("Update " + storageStockTable + " set storage_stock_quantity = storage_stock_quantity + "
                                + itemQuantity + " where item_id = ? and storage_id = ?");
                        updateStat.setString(1, itemID);
                        updateStat.setString(2,toChoice);

                        Integer statusUpdate = updateStat.executeUpdate();
                        if(statusUpdate > 0)
                        {
                            Alert a = new Alert(Alert.AlertType.INFORMATION);
                            a.setTitle("Success Moving!");
                            a.setContentText("Reload the table to check!");
                            a.show();
                        }

                    } else {
                        PreparedStatement insertStat = con.prepareStatement("Insert into " + storageStockTable + " values (?,?,?)");
                        insertStat.setString(1, toChoice);
                        insertStat.setString(2, itemID);
                        insertStat.setString(3, itemQuantity.toString());

                        Integer statusInsert = insertStat.executeUpdate();
                        if(statusInsert > 0)
                        {
                            Alert a = new Alert(Alert.AlertType.INFORMATION);
                            a.setTitle("Success Insert!");
                            a.setContentText("Reload the table to check!");
                            a.show();
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public void deleteItem(String fromChoice, String itemID, Integer deleteItemQuantity) {

        if (fromChoice.contains("SH")) {
            try {
                Connection con = DriverManager.getConnection(this.host, this.userName, this.password);

                PreparedStatement stat = con.prepareStatement("Update " + shopStockTable + " set shop_stock_quantity = shop_stock_quantity - "
                        + deleteItemQuantity + " where item_id = ? and shop_id = ?");
                stat.setString(1, itemID);
                stat.setString(2, fromChoice);

                Integer statusDelete = stat.executeUpdate();
                if(statusDelete == 0)
                {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setTitle("Warning!");
                    a.setContentText("Quantity Inputted is larger than the stock!");
                    a.show();
                }


                Statement selectStat = con.createStatement();
                ResultSet rsSelect = selectStat.executeQuery("Select * from " + shopStockTable);
                while (rsSelect.next())
                {
                    if(rsSelect.getInt("shop_stock_quantity") == 0)
                    {
                        PreparedStatement delAllStat = con.prepareStatement("Delete from " + shopStockTable + " where item_id = ? and shop_id = ?");
                        delAllStat.setString(1, itemID);
                        delAllStat.setString(2,fromChoice);

                        delAllStat.executeUpdate();
                    }
                }

            } catch (SQLException e) {
                System.out.println(e);
            }

        } else {
            try {
                Connection con = DriverManager.getConnection(this.host, this.userName, this.password);
                PreparedStatement stat = con.prepareStatement("Update " + storageStockTable + " set storage_stock_quantity = storage_stock_quantity - "
                        + deleteItemQuantity + " where item_id = ? and storage_id = ?");
                stat.setString(1, itemID);
                stat.setString(2, fromChoice);

               Integer statusDelete = stat.executeUpdate();
               if(statusDelete == 0)
               {
                   Alert a = new Alert(Alert.AlertType.WARNING);
                   a.setTitle("Warning!");
                   a.setContentText("Quantity Inputted is larger than the stock!");
                   a.show();
               }

                Statement selectStat = con.createStatement();
                ResultSet rsSelect = selectStat.executeQuery("Select * from " + storageStockTable);
                while (rsSelect.next())
                {
                    if(rsSelect.getInt("storage_stock_quantity") == 0)
                    {
                        PreparedStatement delAllStat = con.prepareStatement("Delete from " + storageStockTable + " where item_id = ? and storage_id = ?");
                        delAllStat.setString(1, itemID);
                        delAllStat.setString(2,fromChoice);

                        delAllStat.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
}
