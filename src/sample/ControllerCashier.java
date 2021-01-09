package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ControllerCashier implements Initializable {

    public Button goShopButtonTT, addButtonTT, saveButtonTT;

    public TableView currentTransactionTableViewTT, transactionHistoryTableViewTT, shopStockTableViewTT;

    public TextField customerNameTextField, customerAddressTextField, customerNumberTextField,
    enterItemIdTextField, enterItemQtyTextField, enterColumnTT;

    public ComboBox shopComboBoxTT;

    public Text shopIdTextTT, shopAddressTextTT, shopNumberTextTT, customerNameLabelTT,customerAddressLabelTT,
            customerPhoneLabelTT, totalSalesTodayTT,grandTotalTT, userNameTextTT;

    public Database database = new Database();
    //Initializing Table Column for CurrentTransaction Table Transaction Tab
    public TableColumn<CurrentTransactionTableTT,Integer> ttransactionIdColumnTT;
    public TableColumn<CurrentTransactionTableTT,String>transactionIdColumnTT;
    public TableColumn<CurrentTransactionTableTT,String>transactionItemDescColumnTT;
    public TableColumn<CurrentTransactionTableTT,Integer>transactionQtyColumnTT;
    public TableColumn<CurrentTransactionTableTT,Integer>transactionPriceColumnTT;
    public TableColumn<CurrentTransactionTableTT,Integer>transactionTotalColumnTT;
    ObservableList<CurrentTransactionTableTT> currentTransactionTableDataTT = FXCollections.observableArrayList();
    ObservableList<Item> shopItemsObservableListTT = FXCollections.observableArrayList();

    ObservableList<String> shop1= FXCollections.observableArrayList(
            "Shop 1"
    );
    ObservableList<String> shop2 = FXCollections.observableArrayList(
             "Shop 2"
    );
    ObservableList<TransactionHistory>transactionHistoryObservableList = FXCollections.observableArrayList();



    public void goShopButtonTTClicked() throws SQLException {
        shopItemsObservableListTT.clear();
        String shopId = "SH002";
        if (shopComboBoxTT.getValue() == "Shop 1") {
            shopId = "SH001";
        }
        fillShopTable(shopId,shopItemsObservableListTT, shopIdTextTT, shopAddressTextTT, shopNumberTextTT);
    }


    public void saveButtonTTClicked() throws SQLException{
        if(currentTransactionTableDataTT.size()==0){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Warning !");
            a.setContentText("Please input a Transaction");
            a.show();
        }
        else {
            fillTableTransactionHistoryTT(0);
            grandTotalGlobal = 0;
            grandTotalTT.setText("Rp" + String.valueOf(grandTotalGlobal));
        }
    }



    public void deleteButtonClickedTT(){
        if(enterColumnTT.getText().equals("")){

            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Warning !");
            a.setContentText("Please Fill the Column of the Transaction You want to delete from the Table!");
            a.show();

        }

        else if(intOnly(enterColumnTT.getText(),"Column Transaction TextField!")==0){

        }

        else{
            int column = Integer.parseInt(enterColumnTT.getText())-1;
            int transactionId = currentTransactionTableDataTT.get(column).getTransactionId();
            int itemId = Integer.parseInt(currentTransactionTableDataTT.get(column).getItemId());
            String shopId = shopIdTextTT.getText();
            int shopQty = database.getQtyFromShop(itemId,shopId);
            int total = currentTransactionTableDataTT.get(column).getTotal();
            grandTotalGlobal -= total;
            grandTotalTT.setText("Rp"+String.valueOf(grandTotalGlobal));
//        how to get the returned amount quantity
            int qty = currentTransactionTableDataTT.get(column).getItemQty()+shopQty;
            database.updateShopStock(qty,itemId,shopId);
//
            database.deleteTransaction(transactionId);
            currentTransactionTableDataTT.remove(column);
            transactionHistoryObservableList.clear();
            shopItemsObservableListTT.clear();
            fillShopTable(shopId, shopItemsObservableListTT, shopIdTextTT, shopAddressTextTT, shopNumberTextTT);
            fillTableTransactionHistoryTT(1);
            totalSalesTodayTT.setText("Rp"+String.valueOf(database.getTotalSales("Sell")));
        }

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userNameTextTT.setText(Main.userNameLoggedIn);

        if(userNameTextTT.getText().equals("Cashier1"))
        {
            shopComboBoxTT.setItems(shop1);
        }
        else if(userNameTextTT.getText().equals("Cashier2"))
        {
            shopComboBoxTT.setItems(shop2);
        }

        fillTableTransactionHistoryTT(1);

        TableColumn itemIdShopTT = new TableColumn("Item ID");
        itemIdShopTT.setMinWidth(300);
        itemIdShopTT.setCellValueFactory(new PropertyValueFactory<Item,String>("itemId"));

        TableColumn itemDescShopTT = new TableColumn("Item Desc");
        itemDescShopTT.setMinWidth(300);
        itemDescShopTT.setCellValueFactory(new PropertyValueFactory<Item,String>("itemDesc"));

        TableColumn itemQtyShopTT = new TableColumn("Item Quantity");
        itemQtyShopTT.setMinWidth(300);
        itemQtyShopTT.setCellValueFactory(new PropertyValueFactory<Item,Integer>("itemQty"));
        shopStockTableViewTT.setItems(shopItemsObservableListTT);
        shopStockTableViewTT.getColumns().addAll(itemIdShopTT,itemDescShopTT,itemQtyShopTT);



        TableColumn transactionId = new TableColumn("Transaction ID");
        transactionId.setMinWidth(300);
        transactionId.setCellValueFactory(new PropertyValueFactory<TransactionHistory,String>("transactionId"));

        TableColumn date = new TableColumn("Date");
        date.setMinWidth(300);
        date.setCellValueFactory(new PropertyValueFactory<TransactionHistory,String>("date"));

        TableColumn itemId = new TableColumn("Item ID");
        itemId.setMinWidth(300);
        itemId.setCellValueFactory(new PropertyValueFactory<TransactionHistory,String>("itemId"));

        TableColumn itemQty = new TableColumn("Item Qty");
        itemQty.setMinWidth(300);
        itemQty.setCellValueFactory(new PropertyValueFactory<TransactionHistory,Integer>("itemQty"));

        TableColumn itemDesc = new TableColumn("Item Desc");
        itemDesc.setMinWidth(300);
        itemDesc.setCellValueFactory(new PropertyValueFactory<TransactionHistory,String>("itemDesc"));

        TableColumn  totalPrice = new TableColumn("Total Price");
        totalPrice.setMinWidth(300);
        totalPrice.setCellValueFactory(new PropertyValueFactory<TransactionHistory,Integer>("price"));

        TableColumn customerName = new TableColumn("Customer Name");
        customerName.setMinWidth(300);
        customerName.setCellValueFactory(new PropertyValueFactory<TransactionHistory,String>("name"));

        TableColumn customerAddress = new TableColumn("Address");
        customerAddress.setMinWidth(300);
        customerAddress.setCellValueFactory(new PropertyValueFactory<TransactionHistory,String>("address"));

        TableColumn customerPhoneNumber = new TableColumn("Phone Number");
        customerPhoneNumber.setMinWidth(300);
        customerPhoneNumber.setCellValueFactory(new PropertyValueFactory<TransactionHistory,String>("phoneNumber"));

        transactionHistoryTableViewTT.setItems(transactionHistoryObservableList);
        transactionHistoryTableViewTT.getColumns().addAll(transactionId,date,itemId,itemQty,itemDesc,totalPrice,customerName
                ,customerAddress,customerPhoneNumber);







    }

    public void fillShopTable(String shopId, ObservableList shopItems, Text shopIdText, Text shopAddressText, Text shopNumberText){
        Connection con = null;
        PreparedStatement stat = null;
        ResultSet shop1 = null;
        ResultSet shop1Items = null;
        try {
            con = DriverManager.getConnection(database.host, database.userName, database.password);
            stat = con.prepareStatement(database.selectAllQuery + database.shopTable + " where shop_id = ?");

            stat.setString(1, shopId);

            shop1 = stat.executeQuery();
            while (shop1.next()) {
                shopIdText.setText(shop1.getString("shop_id"));
                shopAddressText.setText(shop1.getString("shop_address"));
                shopNumberText.setText(shop1.getString("shop_telephone_number"));
            }

        } catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }finally {
            try {shop1.close();}catch (Exception e){}
            try { stat.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }

        try {
            con = DriverManager.getConnection(database.host, database.userName, database.password);
            stat  = con.prepareStatement("select `Shop Stock`.*, Items.item_description \n" +
                    "from `Shop Stock`\n" +
                    "inner join Items\n" +
                    "on `Shop Stock`.item_id = Items.item_id where shop_id = ?");

            stat.setString(1, shopId);

            shop1Items = stat.executeQuery();
            while (shop1Items.next()){
                shopItems.add(new Item(shop1Items.getString("item_id"),Integer.parseInt(shop1Items.getString("shop_stock_quantity")),shop1Items.getString("item_description")));
            }

        }catch(SQLException e)
        {
            System.out.println(e);
        }finally {
            try {shop1Items.close();}catch (Exception e){}
            try { stat.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public void fillTableTransactionHistoryTT(int num){
        long today = System.currentTimeMillis();
        java.sql.Date currentDay = new java.sql.Date(today);
        if (num ==0){
            currentTransactionTableDataTT.clear();
        }


        transactionHistoryObservableList.clear();
        Connection con = null;
        PreparedStatement stat = null;
        ResultSet sqlData = null;
        try {
            con = DriverManager.getConnection(database.host, database.userName, database.password);
            stat = con.prepareStatement("select tn.transaction_id, tn.transaction_date, tn.item_id, tn.transaction_item_quantity, tn.`transaction_buy/sell`, tn.transaction_price, tn.transactor_id, tr.transactor_name, tr.transactor_address, tr.transactor_phone_number, tr.transactor_email, i.item_description\n" +
                    "from Transaction tn\n" +
                    "    inner join Transactor tr on tn.transactor_id = tr.transactor_id\n" +
                    "    inner join Items i on tn.item_id  = i.item_id\n" +
                    "    where tn.`transaction_buy/sell` = \"Sell\" and tn.transaction_date=?;");

            stat.setDate(1,currentDay);
            sqlData= stat.executeQuery();
            while(sqlData.next()){
                transactionHistoryObservableList.add(new TransactionHistory(sqlData.getString("transaction_id"),sqlData.getDate("transaction_date").toString(),sqlData.getString("item_id")
                        ,sqlData.getInt("transaction_item_quantity"),sqlData.getString("item_description"),sqlData.getString("transactor_name"),sqlData.getString("transactor_address"),
                        sqlData.getString("transactor_phone_number"),sqlData.getInt("transaction_price")));
            }

        }catch(SQLException e)
        {
            System.out.println(e);
        }{
            try {sqlData.close();}catch (Exception e){}
            try { stat.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }
    static String customerNameGlobal ="";
    static int grandTotalGlobal= 0;
    public void addButtonClickedTT(javafx.event.ActionEvent event) throws SQLException {

        currentTransactionTableDataTT.clear();
        if (shopIdTextTT.getText().equals("None")){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Warning !");
            a.setContentText("Please select Shop first to load info!");
            a.show();
        }
        else if(customerNameTextField.getText().equals("")||customerAddressTextField.getText().equals("")||customerNumberTextField.getText().equals("")||enterItemIdTextField.getText().equals("")||enterItemQtyTextField.getText().equals(""))
        {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Warning !");
            a.setContentText("Please Fill Customer Name , Customer Address , Customer Phone Number , Item ID , and Item Quantity !");
            a.show();
        }
        else if(intOnly(customerNumberTextField.getText(),"Customer Phone Number TextField!")==0){

        }

        else if(intOnly(enterItemIdTextField.getText(),"Item ID TextField!")==0||intOnly(enterItemQtyTextField.getText(),"Item Quantity TextField!")==0){

        }

        else {
            System.out.println(shopIdTextTT.getText());
            String customerName = customerNameTextField.getText();
            String customerAddress = customerAddressTextField.getText();
            String customerPhoneNumber = customerNumberTextField.getText();
            String itemId = enterItemIdTextField.getText();
            String itemDesc = database.getItemDescription(Integer.parseInt(itemId));
            int transactionId = database.getNextTransactionId();
            int itemQty = Integer.parseInt(enterItemQtyTextField.getText());
            int itemPrice = database.getItemSellPrice(Integer.parseInt(itemId));
            int itemTotal = itemPrice * itemQty;
            int shopItemQty = database.getQtyFromShop(Integer.parseInt(itemId), shopIdTextTT.getText());
            grandTotalGlobal += itemTotal;

            customerNameLabelTT.setText(customerName);
            customerAddressLabelTT.setText(customerAddress);
            customerPhoneLabelTT.setText(String.valueOf(customerPhoneNumber));

            if (itemQty > shopItemQty) {
                Alert a = new Alert(Alert.AlertType.WARNING);

                a.setTitle("Warning");
                a.setContentText("Quantity Not Enough!");
                a.show();
            } else {
                shopItemQty = shopItemQty - itemQty;

                if (!customerNameGlobal.equals(customerName)) {
                    database.insertTransactor(customerName, customerAddress, customerPhoneNumber);
                }
                customerNameGlobal = customerName;
                int transactorId = database.getTransactorId(customerName);
                database.insertTransaction(Integer.parseInt(itemId), itemQty, itemTotal, transactorId, "Sell");
                database.updateShopStock(shopItemQty, Integer.parseInt(itemId), shopIdTextTT.getText());
                totalSalesTodayTT.setText("Rp"+String.valueOf(database.getTotalSales("Sell")));
                currentTransactionTableDataTT.add(new CurrentTransactionTableTT(itemId, itemDesc, itemQty, itemPrice, itemTotal,transactionId));

            }

            grandTotalTT.setText("Rp"+String.valueOf(grandTotalGlobal));
            fillTableTransactionHistoryTT(1);
            shopItemsObservableListTT.clear();
            fillShopTable(shopIdTextTT.getText(), shopItemsObservableListTT, shopIdTextTT, shopAddressTextTT, shopNumberTextTT);
            System.out.println("done");

        }
    }

    public int intOnly(String integerOnly , String textField){
        int x = 0;
        try{
            x=Integer.parseInt(integerOnly);
        }catch (Exception e ){
            Alert a =  new Alert(Alert.AlertType.WARNING);
            a.setTitle("Warning!");
            a.setContentText("Please enter numbers only in "+textField);
            a.show();
        }
        return x;
    }
}
