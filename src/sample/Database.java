package sample;

//import com.sun.rowset.CachedRowSetImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import javax.sql.rowset.CachedRowSet;
import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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


//    kevin

    public ResultSet getShopStorageInfo(String choice) throws SQLException {
//        Connection con = null;
//        PreparedStatement stat = null;
        ResultSet rsShopStorage = null;
//        CachedRowSet rowSet = new CachedRowSetImpl();
//        if (choice.contains("SH"))
//        {
//            try {
//                con = DriverManager.getConnection(this.host, this.userName, this.password);
//                stat = con.prepareStatement(selectAllQuery + this.shopTable + " where shop_id = ?");
//
//                stat.setString(1, choice);
//
//                rsShopStorage = stat.executeQuery();
//                rowSet.populate(rsShopStorage);
//
//
//            } catch(SQLException e)
//            {
//                System.out.println(e.getMessage());
//            }
//        } else {
//            try {
//                con = DriverManager.getConnection(this.host, this.userName, this.password);
//                stat = con.prepareStatement(selectAllQuery + this.storageTable + " where storage_id = ?");
//
//                stat.setString(1, choice);
//
//                rsShopStorage = stat.executeQuery();
//                rowSet.populate(rsShopStorage);
//
//            } catch(SQLException e)
//            {
//                System.out.println(e.getMessage());
//            }
//        }
//        con.close();
//        stat.close();
//        return rowSet;
        return rsShopStorage;
    }


//    kevin
    public ResultSet getShopStorageItems(String choice) throws SQLException {
        ResultSet rsShopStorageItem = null;
//        Connection con = null;
//        PreparedStatement stat = null;
//        CachedRowSet rowSet = new CachedRowSetImpl();
//        removeEmptyItem(choice);
//
//        if(choice.contains("SH"))
//        {
//            try {
//                 con = DriverManager.getConnection(this.host, this.userName, this.password);
//                 stat  = con.prepareStatement("select `Shop Stock`.*, Items.item_description \n" +
//                        "from `Shop Stock`\n" +
//                        "inner join Items\n" +
//                        "on `Shop Stock`.item_id = Items.item_id where shop_id = ?");
//
//                stat.setString(1, choice);
//
//                rsShopStorageItem = stat.executeQuery();
//                rowSet.populate(rsShopStorageItem);
//
//            }catch(SQLException e)
//            {
//                System.out.println(e);
//            }
//        } else {
//            try {
//                con = DriverManager.getConnection(this.host, this.userName, this.password);
//                stat = con.prepareStatement("select `Storage Stock`.*, Items.item_description \n" +
//                        "from `Storage Stock`\n" +
//                        "inner join Items\n" +
//                        "on `Storage Stock`.item_id = Items.item_id where storage_id = ?");
//
//                stat.setString(1, choice);
//
//                rsShopStorageItem = stat.executeQuery();
//                rowSet.populate(rsShopStorageItem);
//
//
//            }catch(SQLException e)
//            {
//                System.out.println(e);
//            }
//        }
//        con.close();
//        stat.close();
//        return rowSet;
        return rsShopStorageItem;
    }


    public void insertItem(String toChoice, Integer itemID, Integer itemQuantity)
    {
        Connection con = null;
        PreparedStatement selectStat = null;
        ResultSet rsSelect = null;
        if(toChoice.contains("SH")) {
            ObservableList shopItemIdExisted = FXCollections.observableArrayList();
            try {
                 con = DriverManager.getConnection(this.host, this.userName, this.password);
                 selectStat = con.prepareStatement("Select * from " + shopStockTable + " where shop_id = ?");
                 selectStat.setString(1, toChoice);
                 rsSelect = selectStat.executeQuery();

                while (rsSelect.next())
                {
                    shopItemIdExisted.add(rsSelect.getInt("item_id"));
                }
                if(shopItemIdExisted.contains(itemID))
                {
                    PreparedStatement updateStat = con.prepareStatement("Update " + shopStockTable + " set shop_stock_quantity = shop_stock_quantity + " +
                            itemQuantity + " where item_id =? and shop_id = ?");

                    updateStat.setInt(1, itemID);
                    updateStat.setString(2, toChoice);

                    Integer statusUpdate = updateStat.executeUpdate();
                    if (statusUpdate > 0) {
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("Success Update!");
                        a.show();
                    }
                }
                else {

                    PreparedStatement insertStat = con.prepareStatement("Insert into " + shopStockTable + " (shop_id, item_id, shop_stock_quantity) values (?,?,?)");

                    insertStat.setString(1, toChoice);
                    insertStat.setInt(2, itemID);
                    insertStat.setInt(3, itemQuantity);

                    Integer statusInsert = insertStat.executeUpdate();
                    if (statusInsert > 0) {
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("Success Insert!");
                        a.show();
                    }
                    else
                    {
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("Fail Insert!");
                        a.show();
                    }
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        } else {
            ObservableList storageItemIdExisted = FXCollections.observableArrayList();
            try {
                 con = DriverManager.getConnection(this.host, this.userName, this.password);
                 selectStat = con.prepareStatement("Select * from " + storageStockTable + "where storage_id = ?");
                 selectStat.setString(1, toChoice);

                 rsSelect = selectStat.executeQuery();

                while(rsSelect.next())
                {
                    storageItemIdExisted.add(rsSelect.getInt("item_id"));
                }
                if(storageItemIdExisted.contains(itemID))
                {

                    PreparedStatement updateStat = con.prepareStatement("Update " + storageStockTable + " set storage_stock_quantity = storage_stock_quantity + "
                            + itemQuantity + " where item_id = ? and storage_id = ?");
                    updateStat.setInt(1, itemID);
                    updateStat.setString(2,toChoice);

                    Integer statusUpdate = updateStat.executeUpdate();
                    if(statusUpdate > 0)
                    {
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("Success Update!");
                        a.show();
                    }
                }
                else {
                    PreparedStatement insertStat = con.prepareStatement("Insert into " + storageStockTable + "  (storage_id, item_id, storage_stock_quantity) values (?,?,?)");
                    insertStat.setString(1, toChoice);
                    insertStat.setInt(2, itemID);
                    insertStat.setInt(3, itemQuantity);

                    Integer statusInsert = insertStat.executeUpdate();
                    if (statusInsert > 0) {
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("Success Insert!");
                        a.show();
                    }
                    else
                    {
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("Fail Insert!");
                        a.show();
                    }
                }
            }
            catch (SQLException e) {
                System.out.println(e);
            }
        }
        try { selectStat.close(); } catch (Exception e) { /* ignored */ }
        try { con.close(); } catch (Exception e) { /* ignored */ }
        try { rsSelect.close(); } catch (Exception e) { /* ignored */ }
    }


    public void deleteTransaction(int transactionId){
        PreparedStatement preparedStatement = null;
        Connection con = null;

        try {

            con = DriverManager.getConnection(host,userName,password);
            preparedStatement = con.prepareStatement("delete from Transaction where transaction_id = ?");
            preparedStatement.setInt(1,transactionId);
            preparedStatement.execute();

            con.close();


        }catch (SQLException err){
            System.out.println(err.getMessage());

        }finally {
            try { preparedStatement.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }






    public boolean deleteItem(String fromChoice, Integer itemID, Integer deleteItemQuantity) {
        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rsSelect = null;
        Integer existingQuantity = 0;
        boolean deletedItem = true;

        if (fromChoice.contains("SH")) {
            try {
                con = DriverManager.getConnection(this.host, this.userName, this.password);
                stat = con.prepareStatement("Select shop_stock_quantity from " + shopStockTable + " where shop_id = ? and item_id = ?");
                stat.setString(1, fromChoice);
                stat.setInt(1, itemID);
                rsSelect = stat.executeQuery();

                while(rsSelect.next())
                {
                    existingQuantity = rsSelect.getInt("shop_stock_quantity");
                }
                if(deleteItemQuantity > existingQuantity)
                {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setTitle("Warning!");
                    a.setContentText("Quantity Inputted is greater than existing quantity");
                    a.show();
                    deletedItem = false;
                }
                else
                {
                    stat = con.prepareStatement("Update " + shopStockTable + " set shop_stock_quantity = shop_stock_quantity - "
                            + deleteItemQuantity + " where item_id = ? and shop_id = ?");


                    stat.setInt(1, itemID);
                    stat.setString(2, fromChoice);

                    Integer statusDelete = stat.executeUpdate();
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    if(statusDelete == 0)
                    {
                        a.setTitle("Error Delete");
                        deletedItem = false;
                    }
                    else
                    {
                        a.setTitle("Success Delete");
                        deletedItem = true;
                    }
                    a.show();

                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        } else {
            try {
                con = DriverManager.getConnection(this.host, this.userName, this.password);
                stat = con.prepareStatement("Select storage_stock_quantity from " + storageStockTable + " where storage_id = ? and item_id = ?");
                stat.setString(1, fromChoice);
                stat.setInt(2,itemID);
                rsSelect = stat.executeQuery();

                while (rsSelect.next()) {
                    existingQuantity = rsSelect.getInt("storage_stock_quantity");
                }
                if (deleteItemQuantity > existingQuantity) {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setTitle("Warning!");
                    a.setContentText("Quantity Inputted is greater than existing quantity");
                    a.show();
                    deletedItem = false;
                } else {
                    stat = con.prepareStatement("Update " + storageStockTable + " set storage_stock_quantity = storage_stock_quantity - "
                            + deleteItemQuantity + " where item_id = ? and storage_id = ?");


                    stat.setInt(1, itemID);
                    stat.setString(2, fromChoice);

                    Integer statusDelete = stat.executeUpdate();
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    if (statusDelete == 0) {
                        a.setTitle("Error Delete");
                        deletedItem = false;
                    } else {
                        a.setTitle("Success Delete");
                        deletedItem = true;
                    }
                    a.show();
                }
            }
            catch (SQLException e) {
                System.out.println(e);
            }
        }

        try { rsSelect.close(); } catch (Exception e) { /* ignored */ }
        try { con.close(); } catch (Exception e) { /* ignored */ }
        try { stat.close(); } catch (Exception e) { /* ignored */ }

        return deletedItem;
    }


    public void removeEmptyItem(String shopStorageID) throws SQLException {
        Connection con = null;
        PreparedStatement stat = null;
        Statement selectStat = null;
        ResultSet rsSelect = null;
        if(shopStorageID.contains("SH"))
        {
            con = DriverManager.getConnection(this.host, this.userName, this.password);
            PreparedStatement delAllStat = con.prepareStatement("Delete from " + shopStockTable + " where shop_id = ? and shop_stock_quantity = ?");
            delAllStat.setString(1, shopStorageID);
            delAllStat.setInt(2,0);

            Integer statdel = delAllStat.executeUpdate();
            if(statdel>1)
            {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("deleted 0s in shop");
                a.show();
            }

        }
        else
        {
            con = DriverManager.getConnection(this.host, this.userName, this.password);
            PreparedStatement delAllStat = con.prepareStatement("Delete from " + storageStockTable + " where storage_id = ? and storage_stock_quantity = ?");
            delAllStat.setString(1,shopStorageID);
            delAllStat.setInt(2,0);

          Integer statdel = delAllStat.executeUpdate();
            if(statdel>1)
            {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("deleted 0s in Storage");
                a.show();
            }
        }
        try { rsSelect.close(); } catch (Exception e) { /* ignored */ }
        try { con.close(); } catch (Exception e) { /* ignored */ }
        try { stat.close(); } catch (Exception e) { /* ignored */ }
        try { selectStat.close();} catch (Exception e) { /* ignored */ }
    }


    public void insertTransaction(Integer itemId, Integer itemQty, Integer totalPrice, Integer transactorId,String buyOrSell){
        long today = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(today);
        Connection con = null;
        PreparedStatement preparedStatement =null;

        try {
            con = DriverManager.getConnection(host,userName,password);
             preparedStatement= con.prepareStatement(
                    "insert into Transaction (transaction_date, item_id, transaction_item_quantity, `transaction_buy/sell`, transaction_price, transactor_id)\n" +
                    "values (?,?,?,?,?,?)");
            preparedStatement.setDate(1,date);
            preparedStatement.setInt(2,itemId);
            preparedStatement.setInt(3,itemQty);
            preparedStatement.setString(4,buyOrSell);
            preparedStatement.setInt(5,totalPrice);
            preparedStatement.setInt(6,transactorId);
            preparedStatement.execute();
            con.close();
        }catch (SQLException err){
            System.out.println(err.getMessage());
        }finally {
            try { preparedStatement.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }

    }

    public void updateTransaction(int transactionId, int qty){
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = DriverManager.getConnection(host,userName,password);
            preparedStatement = con.prepareStatement("update `Transaction` set  transaction_item_quantity = ? where transaction_id = ?");
            preparedStatement.setInt(1,qty);
            preparedStatement.setInt(2,transactionId);
            preparedStatement.execute();
            con.close();
        }catch (SQLException err){
            System.out.println(err.getMessage());
        }finally {
            try { preparedStatement.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public void updateShopStock(int itemQty, int itemId, String shop_id){
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = DriverManager.getConnection(host,userName,password);
            preparedStatement = con.prepareStatement("update `Shop Stock` set  shop_stock_quantity = ? where item_id = ? and shop_id =?");
            preparedStatement.setInt(1,itemQty);
            preparedStatement.setInt(2,itemId);
            preparedStatement.setString(3,shop_id);
            preparedStatement.execute();
            con.close();
        }catch (SQLException err){
            System.out.println(err.getMessage());
        }finally {
            try { preparedStatement.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public void updateStorageStock(int itemQty, int itemId, String storageId){
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = DriverManager.getConnection(host,userName,password);
            preparedStatement = con.prepareStatement("update `Storage Stock` set  storage_stock_quantity = ? where item_id = ? and storage_id =?");
            preparedStatement.setInt(1,itemQty);
            preparedStatement.setInt(2,itemId);
            preparedStatement.setString(3,storageId);
            preparedStatement.execute();
            con.close();
        }catch (SQLException err){
            System.out.println(err.getMessage());
        }finally {
            try { preparedStatement.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public void insertTransactor(String name, String address,String phoneNumber){
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = DriverManager.getConnection(host,userName,password);
            preparedStatement = con.prepareStatement("\n" +
                    "insert into Transactor (transactor_name, transactor_address, transactor_phone_number,transactor_email) values (?,?,?,?)");
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,address);
            preparedStatement.setString(3,phoneNumber);
            preparedStatement.setString(4,"-");
            preparedStatement.execute();
            con.close();
        }catch (SQLException err){
            System.out.println(err.getMessage());
        }finally {
            try { preparedStatement.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }




    public void registerItem(Integer itemId,String itemDesc,Integer itemSellPrice){
        Connection con =null;
        PreparedStatement preparedStatement=null;
        try {
            con = DriverManager.getConnection(host,userName,password);
            preparedStatement = con.prepareStatement("insert into Items (item_id,item_description, item_sell_price)\n" +
                    "values (?,?,?)");
            preparedStatement.setInt(1,itemId);
            preparedStatement.setString(2,itemDesc);
            preparedStatement.setInt(3,itemSellPrice);
            preparedStatement.execute();
            con.close();

        }catch (SQLException err){
            System.out.println(err.getMessage());
        }finally {
            try { preparedStatement.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public void supplierToStorage(String storage_id, Integer item_id,Integer itemQty) throws SQLException {

        Integer storageStockId =0;
        Connection con = null;
        PreparedStatement preparedStatement= null;
        ResultSet rs= null;
        try {
             rs = null;

            con = DriverManager.getConnection(host,userName,password);
            preparedStatement = con.prepareStatement("select storage_stock_id from `Storage Stock` where item_id=? and storage_id=?");
            preparedStatement.setInt(1,item_id);
            preparedStatement.setString(2,storage_id);
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                storageStockId = rs.getInt("storage_stock_id");
            }
            con.close();


        }catch (SQLException err){
            System.out.println(err.getMessage());

        }finally {
            try{ rs.close();}catch (Exception e){}
            try { preparedStatement.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }

        if (storageStockId!=0){

            try {
                 con = DriverManager.getConnection(host,userName,password);
                 preparedStatement = con.prepareStatement("update `Storage Stock`\n" +
                        "set  storage_stock_quantity =?\n" +
                        "where item_id = ? and storage_id=?");
                preparedStatement.setInt(1,itemQty);
                preparedStatement.setInt(2,item_id);
                preparedStatement.setString(3,storage_id);
                preparedStatement.execute();
                con.close();

            }catch (SQLException err){
                System.out.println("error"+err.getMessage());

            }finally {
                try { preparedStatement.close(); } catch (Exception e) { /* ignored */ }
                try { con.close(); } catch (Exception e) { /* ignored */ }
            }
        }
        else {
            try {
                 con = DriverManager.getConnection(host,userName,password);
                 preparedStatement = con.prepareStatement("insert into `Storage Stock` (storage_id, item_id, storage_stock_quantity)\n" +
                        "values (?,?,?);");
                preparedStatement.setString(1,storage_id);
                preparedStatement.setInt(2,item_id);
                preparedStatement.setInt(3,itemQty);
                preparedStatement.execute();
                con.close();
            }
            catch (SQLException err){
                System.out.println(err.getMessage());
            }finally {
                try { preparedStatement.close(); } catch (Exception e) { /* ignored */ }
                try { con.close(); } catch (Exception e) { /* ignored */ }
            }

        }

    }

    public Integer getTransactorId(String supplierName){
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            Integer transactorId  =0;
             con = DriverManager.getConnection(host,userName,password);
             preparedStatement = con.prepareStatement("select transactor_id from `Transactor` where transactor_name = ?;");
            preparedStatement.setString(1,supplierName);
             rs = preparedStatement.executeQuery();
            while (rs.next()){
                transactorId = rs.getInt("transactor_id");
            }
            con.close();
            return transactorId;

        }catch (SQLException err){
            System.out.println(err.getMessage());
            return 0;
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { preparedStatement.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public Integer getNextTransactionId(){
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            Integer transactionId  =0;
            con = DriverManager.getConnection(host,userName,password);
            preparedStatement = con.prepareStatement("select max(transaction_id) as max from Transaction;");
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                transactionId = rs.getInt("max")+1;
            }
            con.close();
            return transactionId;

        }catch (SQLException err){
            System.out.println(err.getMessage());
            return 0;
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { preparedStatement.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public Integer getTotalSales(String buyOrSell){
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection con = null;
        Integer total = 0;
        try {

            long today = System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(today);
             con = DriverManager.getConnection(host,userName,password);
             preparedStatement = con.prepareStatement("SELECT SUM(transaction_price) AS totalSales FROM Transaction where transaction_date = ? and  `transaction_buy/sell` = ? ");
            preparedStatement.setDate(1,date);
            preparedStatement.setString(2,buyOrSell);
             rs = preparedStatement.executeQuery();
            while (rs.next()){
                total = rs.getInt("totalSales");
            }
            con.close();


        }
        catch (SQLException err){
            System.out.println(err.getMessage());
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { preparedStatement.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return total;
    }

    public Integer getQtyFromShop(Integer itemId,String shopId){
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection con = null;
        Integer qty =0;
        try {

             con = DriverManager.getConnection(host,userName,password);
            preparedStatement = con.prepareStatement("select shop_stock_quantity from `Shop Stock` where item_id =? and shop_id =?;");
            preparedStatement.setInt(1,itemId);
            preparedStatement.setString(2,shopId);
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                qty = rs.getInt("shop_stock_quantity");
            }
            con.close();


        }catch (SQLException err){
            System.out.println(err.getMessage());

        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { preparedStatement.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return qty;
    }

    public String getItemDescription(Integer itemID){
        String itemDesc = "Invalid";
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {


            con = DriverManager.getConnection(host,userName,password);
            preparedStatement = con.prepareStatement("select item_description from Items where item_id =?;");
            preparedStatement.setInt(1,itemID);
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                itemDesc = rs.getString("item_description");
                con.close();

            }
        }catch (SQLException err){
            System.out.println(err.getMessage());

        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { preparedStatement.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return itemDesc;
    }

    public Integer getItemSellPrice(Integer itemID){
        Integer itemSellPrice = 0;
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {


             con = DriverManager.getConnection(host,userName,password);
            preparedStatement = con.prepareStatement("select item_sell_price from Items where item_id =?;");
            preparedStatement.setInt(1,itemID);
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                itemSellPrice = rs.getInt("item_sell_price");
                con.close();

            }
        }catch (SQLException err){
            System.out.println(err.getMessage());

        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { preparedStatement.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return itemSellPrice;
    }



    public Integer getQtyFromStorage(Integer itemId,String storadeId){
        Integer qty =0;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        Connection con = null;
        try {


            con = DriverManager.getConnection(host,userName,password);
             preparedStatement = con.prepareStatement("select storage_stock_quantity from `Storage Stock` where item_id =? and storage_id =?");
            preparedStatement.setInt(1,itemId);
            preparedStatement.setString(2,storadeId);
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                 qty = rs.getInt("storage_stock_quantity");
            }
            con.close();


        }catch (SQLException err){
            System.out.println(err.getMessage());
            return 0;
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { preparedStatement.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return qty;
    }

    public int checkItemExist(int itemId){
        int exist = 0;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        Connection con = null;
        try {
            con = DriverManager.getConnection(host,userName,password);
            preparedStatement = con.prepareStatement("SELECT EXISTS(SELECT * FROM Items WHERE item_id = ?) AS myCheck;");
            preparedStatement.setInt(1,itemId);
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                exist = rs.getInt("myCheck");
            }
            con.close();


        }catch (SQLException err){
            System.out.println(err.getMessage());

        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { preparedStatement.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        return exist;
    }



}
