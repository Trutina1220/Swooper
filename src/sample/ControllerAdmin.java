package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ControllerAdmin implements Initializable {
    @FXML
    public Button goStorageButtonST,goShopButtonST,moveButtonST,goShopButtonTT, addButtonTT, saveButtonTT, goStorageButtonBIT, addButtonBIT, saveButtonBIT;
    @FXML
    public TableView storageTableViewST, shopTableViewST,shopStockTableViewTT, currentTransactionTableViewTT, transactionHistoryTableViewTT, storageTableViewBIT, currentTransactionTableViewBIT, transactionHistoryTableViewBIT;
    @FXML
    public TextField itemQuantityTextFieldST,customerNameTextField, customerAddressTextField, customerNumberTextField,
            enterItemIdTextField, enterItemQtyTextField, supplierNameTextFieldBIT, supplierAddressTextFieldBIT, supplierPhoneTextFieldBIT,
            itemIdTextFieldBIT, itemQuantityTextFieldBIT, sellPriceTextFieldBIT, buyPriceTextFieldBIT,itemNameTextFieldBIT;
    @FXML
    public ComboBox itemIDComboBoxST, storageComboBoxST, shopComboBoxST, fromComboBox, toComboBox, shopComboBoxTT, storageComboBoxBIT ;

    @FXML
    public Text storageIdTextST, storageAddressTextST, storageNumberTextST, shopIdTextST, shopAddressTextST, shopNumberTextST, shopIdTextTT,
            shopAddressTextTT, shopNumberTextTT, customerNameLabelTT,customerAddressLabelTT,customerPhoneLabelTT,storageIdTextBIT, storageAddressTextBIT,
            storageNumberTextBIT, supplierNameLabelBIT, supplierAddressLabelBIT,totalSalesTodayTT,supplierPhoneLabelBIT,grandTotalTT;


    ObservableList<String> storageLists = FXCollections.observableArrayList(
            "Storage 1", "Storage 2"
    );
    ObservableList<String> shopLists = FXCollections.observableArrayList(
            "Shop 1", "Shop 2"
    );
    ObservableList<String> shopStorageLists = FXCollections.observableArrayList(
            "Shop 1", "Shop 2", "Storage 1", "Storage 2"
    );
    ObservableList<Item> shopItems = FXCollections.observableArrayList();
    ObservableList<Item> storageItems = FXCollections.observableArrayList();
    ObservableList<Integer> shop1ItemIDs = FXCollections.observableArrayList();
    ObservableList<Integer> shop2ItemIDs = FXCollections.observableArrayList();
    ObservableList<Integer> storage1ItemIDs= FXCollections.observableArrayList();
    ObservableList<Integer> storage2ItemIDs = FXCollections.observableArrayList();
    ObservableList<TransactionHistory>transactionHistoryObservableList = FXCollections.observableArrayList();
    ObservableList<TransactionHistory>transactionHistoryObservableListBIT = FXCollections.observableArrayList();
    ObservableList<Item> storageItemsObservableListBIT = FXCollections.observableArrayList();
    ObservableList<Item> shopItemsObservableListTT = FXCollections.observableArrayList();







    public TableColumn<CurrentTransactionTableTT,String>transactionIdColumnTT;
    public TableColumn<CurrentTransactionTableTT,String>transactionItemDescColumnTT;
    public TableColumn<CurrentTransactionTableTT,Integer>transactionQtyColumnTT;
    public TableColumn<CurrentTransactionTableTT,Integer>transactionPriceColumnTT;
    public TableColumn<CurrentTransactionTableTT,Integer>transactionTotalColumnTT;
    ObservableList<CurrentTransactionTableTT> currentTransactionTableDataTT = FXCollections.observableArrayList();

    public TableColumn<CurrentTransactionTableBIT,String>transactionIdColumnBIT;
    public TableColumn<CurrentTransactionTableBIT,String>transactionItemNameColumnBIT;
    public TableColumn<CurrentTransactionTableBIT,Integer>transactionQtyColumnBIT;
    public TableColumn<CurrentTransactionTableBIT,Integer>transactionBuyPriceColumnBIT;
    public TableColumn<CurrentTransactionTableBIT,Integer>transactionSellPriceColumnBIT;
    public TableColumn<CurrentTransactionTableBIT,Integer>transactionTotalColumnBIT;
    ObservableList<CurrentTransactionTableBIT> currentTransactionTableDataBIT = FXCollections.observableArrayList();

//    initialize database connection
    public Database database = new Database();


//static int for transactionTable
    static int transactionId;
    static int transactorId;





    @Override
    public void initialize(URL location, ResourceBundle resources) {

        storageComboBoxST.setItems(storageLists);
        shopComboBoxST.setItems(shopLists);
        shopComboBoxTT.setItems(shopLists);
        storageComboBoxBIT.setItems(storageLists);
        fromComboBox.setItems(shopStorageLists);



        //Storage Tab
        TableColumn itemIdStorage = new TableColumn("Item ID");
        itemIdStorage.setMinWidth(300);
        itemIdStorage.setCellValueFactory(
                //sets from what class to take the data from and what is the type of data
                new PropertyValueFactory<Item, String>("itemId"));

        TableColumn itemQtyStorage = new TableColumn("Item Quantity");
        itemQtyStorage.setMinWidth(300);
        itemQtyStorage.setCellValueFactory(
                //sets from what class to take the data from and what is the type of data
                new PropertyValueFactory<Item, Integer>("itemQty"));

        TableColumn itemDescStorage = new TableColumn("Item Desc");
        itemDescStorage.setMinWidth(300);
        itemDescStorage.setCellValueFactory(
                //sets from what class to take the data from and what is the type of data
                new PropertyValueFactory<Item, Integer>("itemDesc"));
        storageTableViewST.setItems(storageItems);

        storageTableViewST.getColumns().addAll(itemIdStorage, itemQtyStorage, itemDescStorage);

//        Storage tab BIT
        TableColumn itemIdStorageBIT = new TableColumn("Item ID");
        itemIdStorageBIT.setMinWidth(300);
        itemIdStorageBIT.setCellValueFactory(new PropertyValueFactory<Item,String>("itemId"));

        TableColumn itemDescStorageBIT = new TableColumn("Item Desc");
        itemDescStorageBIT.setMinWidth(300);
        itemDescStorageBIT.setCellValueFactory(new PropertyValueFactory<Item,String>("itemDesc"));

        TableColumn itemQtyStorageBIT = new TableColumn("Item Quantity");
        itemQtyStorageBIT.setMinWidth(300);
        itemQtyStorageBIT.setCellValueFactory(new PropertyValueFactory<Item,Integer>("itemQty"));
        storageTableViewBIT.setItems(storageItemsObservableListBIT);
        storageTableViewBIT.getColumns().addAll(itemIdStorageBIT,itemDescStorageBIT,itemQtyStorageBIT);

        //        shop tab TT
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

        TableColumn itemIdShop = new TableColumn("Item ID");
        itemIdShop.setMinWidth(300);
        itemIdShop.setCellValueFactory(
                //sets from what class to take the data from and what is the type of data
                new PropertyValueFactory<Item, String>("itemId"));

        TableColumn itemQtyShop = new TableColumn("Item Quantity");
        itemQtyShop.setMinWidth(300);
        itemQtyShop.setCellValueFactory(
                //sets from what class to take the data from and what is the type of data
                new PropertyValueFactory<Item, Integer>("itemQty"));

        TableColumn itemDescShop = new TableColumn("Item Desc");
        itemDescShop.setMinWidth(300);
        itemDescShop.setCellValueFactory(
                //sets from what class to take the data from and what is the type of data
                new PropertyValueFactory<Item, String>("itemDesc"));

        shopTableViewST.setItems(shopItems);

        shopTableViewST.getColumns().addAll(itemIdShop, itemQtyShop, itemDescShop);


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



        TableColumn transactionIdBIT = new TableColumn("Transaction ID");
        transactionIdBIT.setMinWidth(300);
        transactionIdBIT.setCellValueFactory(new PropertyValueFactory<TransactionHistory,String>("transactionId"));

        TableColumn dateBIT = new TableColumn("Date");
        dateBIT.setMinWidth(300);
        dateBIT.setCellValueFactory(new PropertyValueFactory<TransactionHistory,String>("date"));

        TableColumn itemIdBIT = new TableColumn("Item ID");
        itemIdBIT.setMinWidth(300);
        itemIdBIT.setCellValueFactory(new PropertyValueFactory<TransactionHistory,String>("itemId"));

        TableColumn itemQtyBIT = new TableColumn("Item Qty");
        itemQtyBIT.setMinWidth(300);
        itemQtyBIT.setCellValueFactory(new PropertyValueFactory<TransactionHistory,Integer>("itemQty"));

        TableColumn itemDescBIT = new TableColumn("Item Desc");
        itemDescBIT.setMinWidth(300);
        itemDescBIT.setCellValueFactory(new PropertyValueFactory<TransactionHistory,String>("itemDesc"));

        TableColumn  totalPriceBIT = new TableColumn("Total Price");
        totalPriceBIT.setMinWidth(300);
        totalPriceBIT.setCellValueFactory(new PropertyValueFactory<TransactionHistory,Integer>("price"));

        TableColumn customerNameBIT = new TableColumn("Supplier Name");
        customerNameBIT.setMinWidth(300);
        customerNameBIT.setCellValueFactory(new PropertyValueFactory<TransactionHistory,String>("name"));

        TableColumn customerAddressBIT = new TableColumn("Address");
        customerAddressBIT.setMinWidth(300);
        customerAddressBIT.setCellValueFactory(new PropertyValueFactory<TransactionHistory,String>("address"));

        TableColumn customerPhoneNumberBIT = new TableColumn("Phone Number");
        customerPhoneNumberBIT.setMinWidth(300);
        customerPhoneNumberBIT.setCellValueFactory(new PropertyValueFactory<TransactionHistory,String>("phoneNumber"));

        transactionHistoryTableViewBIT.setItems(transactionHistoryObservableListBIT);
        transactionHistoryTableViewBIT.getColumns().addAll(transactionIdBIT,dateBIT,itemIdBIT,itemQtyBIT,itemDescBIT,totalPriceBIT,customerNameBIT
                ,customerAddressBIT,customerPhoneNumberBIT);











//        setting the observable list for the current transaction Table TT;
        transactionIdColumnTT.setCellValueFactory(new PropertyValueFactory<CurrentTransactionTableTT,String>("itemId"));
        transactionItemDescColumnTT.setCellValueFactory(new PropertyValueFactory<CurrentTransactionTableTT,String>("itemDesc"));
        transactionQtyColumnTT.setCellValueFactory(new PropertyValueFactory<CurrentTransactionTableTT,Integer>("itemQty"));
        transactionPriceColumnTT.setCellValueFactory(new PropertyValueFactory<CurrentTransactionTableTT,Integer>("itemPrice"));
        transactionTotalColumnTT.setCellValueFactory(new PropertyValueFactory<CurrentTransactionTableTT,Integer>("total"));
        currentTransactionTableViewTT.setItems(currentTransactionTableDataTT);

//        Setting the observable list for the current transaction table BIT
        transactionIdColumnBIT.setCellValueFactory(new PropertyValueFactory<CurrentTransactionTableBIT,String>("itemId"));
        transactionItemNameColumnBIT.setCellValueFactory(new PropertyValueFactory<CurrentTransactionTableBIT,String>("itemName"));
        transactionQtyColumnBIT.setCellValueFactory(new PropertyValueFactory<CurrentTransactionTableBIT,Integer>("itemQty"));
        transactionBuyPriceColumnBIT.setCellValueFactory(new PropertyValueFactory<CurrentTransactionTableBIT,Integer>("itemBuyPrice"));
        transactionSellPriceColumnBIT.setCellValueFactory(new PropertyValueFactory<CurrentTransactionTableBIT,Integer>("itemSellPrice"));
        transactionTotalColumnBIT.setCellValueFactory(new PropertyValueFactory<CurrentTransactionTableBIT,Integer>("total"));
        currentTransactionTableViewBIT.setItems(currentTransactionTableDataBIT);
    }



    public void saveButtonBITClicked() throws SQLException{
        currentTransactionTableDataBIT.clear();
        transactionHistoryObservableListBIT.clear();
        ResultSet rsTransactionHistory = null;
        Connection con = null;
        PreparedStatement stat = null;
        try {
            con = DriverManager.getConnection(database.host, database.userName, database.password);
             stat = con.prepareStatement("select tn.transaction_id, tn.transaction_date, tn.item_id, tn.transaction_item_quantity, tn.`transaction_buy/sell`, tn.transaction_price, tn.transactor_id, tr.transactor_name, tr.transactor_address, tr.transactor_phone_number, tr.transactor_email, i.item_description\n" +
                    "from Transaction tn\n" +
                    "    inner join Transactor tr on tn.transactor_id = tr.transactor_id\n" +
                    "    inner join Items i on tn.item_id  = i.item_id\n" +
                    "    where tn.`transaction_buy/sell` = \"Buy\";");


            rsTransactionHistory = stat.executeQuery();
            while (rsTransactionHistory.next()){
                transactionHistoryObservableListBIT.add(new TransactionHistory(rsTransactionHistory.getString("transaction_id"),rsTransactionHistory.getDate("transaction_date").toString(),rsTransactionHistory.getString("item_id")
                        ,rsTransactionHistory.getInt("transaction_item_quantity"),rsTransactionHistory.getString("item_description"),rsTransactionHistory.getString("transactor_name"),rsTransactionHistory.getString("transactor_address"),
                        rsTransactionHistory.getString("transactor_phone_number"),rsTransactionHistory.getInt("transaction_price")));
            }

        }catch(SQLException e)
        {
            System.out.println(e);
        }finally {
            rsTransactionHistory.close();
            stat.close();
            con.close();

        }
        System.out.println("run");
    }



    public void saveButtonTTClicked() throws SQLException{
        fillTableTransactionHistoryTT(0);
        grandTotalGlobal = 0;

        grandTotalTT.setText("Rp"+String.valueOf(grandTotalGlobal));
    }

    public void fillTableTransactionHistoryTT(int num){
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
                    "    where tn.`transaction_buy/sell` = \"Sell\";");


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





    public void goStorageButtonSTClicked() throws SQLException {
        if(storageComboBoxST.getSelectionModel().isEmpty())
        {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Warning !");
            a.setContentText("Please select storage first to load info!");
            a.show();
        }
        else
        {
            if(storageComboBoxST.getValue() == "Storage 1")
            {
                storageItems.clear();
                ResultSet storage1 = database.getShopStorageInfo("ST001");
                ResultSet storage1Items = database.getShopStorageItems("ST001");

                while (storage1.next()) {
                    storageIdTextST.setText(storage1.getString("storage_id"));
                    storageAddressTextST.setText(storage1.getString("storage_address"));
                    storageNumberTextST.setText(storage1.getString("storage_telephone_number"));
                }
                while (storage1Items.next())
                {
                    storageItems.add(new Item(storage1Items.getString("item_id"), Integer.parseInt(storage1Items.getString("storage_stock_quantity")), storage1Items.getString("item_description")));
                }
                storage1.close();
                storage1Items.close();
            }

            else
            {
                storageItems.clear();
                ResultSet storage2 = database.getShopStorageInfo("ST002");
                ResultSet storage2Items = database.getShopStorageItems("ST002");

                while (storage2.next()) {
                    storageIdTextST.setText(storage2.getString("storage_id"));
                    storageAddressTextST.setText(storage2.getString("storage_address"));
                    storageNumberTextST.setText(storage2.getString("storage_telephone_number"));
                }
                while (storage2Items.next())
                {
                    storageItems.add(new Item(storage2Items.getString("item_id"), Integer.parseInt(storage2Items.getString("storage_stock_quantity")),storage2Items.getString("item_description")));
                }
                storage2.close();
                storage2Items.close();
            }
        }
    }




    public void goShopButtonSTClicked() throws SQLException {
        if(shopComboBoxST.getSelectionModel().isEmpty())
        {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Warning !");
            a.setContentText("Please select shop first to load info!");
            a.show();
        }
        else
        {
            if(shopComboBoxST.getValue() == "Shop 1")
            {
                shopItems.clear();
                ResultSet shop1 = database.getShopStorageInfo("SH001");
                ResultSet shop1Items = database.getShopStorageItems("SH001");


                while (shop1.next()) {
                    shopIdTextST.setText(shop1.getString("shop_id"));
                    shopAddressTextST.setText(shop1.getString("shop_address"));
                    shopNumberTextST.setText(shop1.getString("shop_telephone_number"));
                }

                while(shop1Items.next())
                {
                    shopItems.add(new Item(shop1Items.getString("item_id"), Integer.parseInt(shop1Items.getString("shop_stock_quantity")), shop1Items.getString("item_description")));
                }
                shop1.close();
                shop1Items.close();

            }
            else
            {
                shopItems.clear();
                ResultSet shop2 = database.getShopStorageInfo("SH002");
                ResultSet shop2Items = database.getShopStorageItems("SH002");

                while (shop2.next()) {
                    shopIdTextST.setText(shop2.getString("shop_id"));
                    shopAddressTextST.setText(shop2.getString("shop_address"));
                    shopNumberTextST.setText(shop2.getString("shop_telephone_number"));
                }

                while (shop2Items.next())
                {
                    shopItems.add(new Item(shop2Items.getString("item_id"), Integer.parseInt(shop2Items.getString("shop_stock_quantity")), shop2Items.getString("item_description")));
                }
                shop2.close();
                shop2Items.close();
            }

        }

    }


    public void fromComboBoxPicked() throws SQLException {
        if (shopLists.contains(fromComboBox.getValue())) {
            toComboBox.setItems(storageLists);
        } else {
            toComboBox.setItems(shopStorageLists);
        }

        if (fromComboBox.getValue().toString() == "Shop 1") {
            ResultSet rsShopItems = database.getShopStorageItems("SH001");
            while (rsShopItems.next()) {
                Integer shop1ItemID = rsShopItems.getInt("item_id");
                shop1ItemIDs.add(shop1ItemID);
            }
            itemIDComboBoxST.setItems(shop1ItemIDs);
        }
        else if (fromComboBox.getValue().toString() == "Shop 2") {
            ResultSet rsShopItems = database.getShopStorageItems("SH002");
            while (rsShopItems.next()) {
                Integer shop2ItemID = rsShopItems.getInt("item_id");
                shop2ItemIDs.add(shop2ItemID);
            }
            itemIDComboBoxST.setItems(shop2ItemIDs);
        }
        else if (fromComboBox.getValue().toString() == "Storage 1") {
            ResultSet rsStorageItems = database.getShopStorageItems("ST001");
            while (rsStorageItems.next()) {
                Integer storage1ItemID = rsStorageItems.getInt("item_id");
                storage1ItemIDs.add(storage1ItemID);
            }
            itemIDComboBoxST.setItems(storage1ItemIDs);
        }
        else {
            ResultSet rsStorageItems = database.getShopStorageItems("ST002");
            while (rsStorageItems.next()) {
                Integer storage2ItemID = rsStorageItems.getInt("item_id");
                storage2ItemIDs.add(storage2ItemID);
            }
            itemIDComboBoxST.setItems(storage2ItemIDs);
        }
    }

    public void successMove()
    {
//        fromComboBox.getSelectionModel().clearSelection();
        toComboBox.getSelectionModel().clearSelection();
        itemIDComboBoxST.getSelectionModel().clearSelection();
        itemQuantityTextFieldST.clear();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Success Move");
        a.setContentText("Reload the shop and storage stock to check!");
        a.show();
    }

    public void failedMove()
    {
//        fromComboBox.getSelectionModel().clearSelection();
        toComboBox.getSelectionModel().clearSelection();
        itemIDComboBoxST.getSelectionModel().clearSelection();
        itemQuantityTextFieldST.clear();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Fail Move");
        a.setContentText("Recheck quantity input and try again!");
        a.show();
    }




    public void MoveButtonSTClicked() throws SQLException {

        if (fromComboBox.getSelectionModel().isEmpty() || toComboBox.getSelectionModel().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.WARNING);

            a.setTitle("Warning");
            a.setContentText("Please Pick the from and to box!");
            a.show();

        } else if (fromComboBox.getValue().toString() == "Storage 1" && toComboBox.getValue().toString() == "Storage 1") {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Warning!");
            a.setContentText("Can't move item to the same place!");
            a.show();
            toComboBox.getSelectionModel().clearSelection();
        } else if (fromComboBox.getValue().toString() == "Storage 2" && toComboBox.getValue().toString() == "Storage 2") {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Warning!");
            a.setContentText("Can't move item to the same place!");
            a.show();
            toComboBox.getSelectionModel().clearSelection();
        } else if (itemIDComboBoxST.getSelectionModel().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.WARNING);

            a.setTitle("Warning");
            a.setContentText("Please Pick the item ID in the combo box!");
            a.show();

        } else if (itemQuantityTextFieldST.getText().isEmpty()) {

            Alert a = new Alert(Alert.AlertType.WARNING);

            a.setTitle("Warning");
            a.setContentText("Quantity Field is Empty!");
            a.show();
        } else if (!itemQuantityTextFieldST.getText().isEmpty()) {
            if (!isInt(itemQuantityTextFieldST)) {
                Alert a = new Alert(Alert.AlertType.WARNING);

                a.setTitle("Warning");
                a.setContentText("Quantity Field Must be Numbers!");
                itemQuantityTextFieldST.clear();
                a.show();

            }
            else if (Integer.parseInt(itemQuantityTextFieldST.getText()) == 0) {
                Alert a = new Alert(Alert.AlertType.WARNING);

                a.setTitle("Warning");
                a.setContentText("Quantity Field more than 0!");
                itemQuantityTextFieldST.clear();
                a.show();

            }

        }

        Integer inputId = (Integer) itemIDComboBoxST.getValue();
        Integer inputQty = Integer.parseInt(itemQuantityTextFieldST.getText());


        if (fromComboBox.getValue().toString() == "Shop 1" && toComboBox.getValue().toString() == "Storage 1") {
            if(database.deleteItem("SH001", inputId, inputQty) == true) {
                database.insertItem("ST001", inputId, inputQty);
                this.successMove();
            }
            else {
                this.failedMove();
            }
        } else if (fromComboBox.getValue().toString() == "Shop 2" && toComboBox.getValue().toString() == "Storage 1") {
            if(database.deleteItem("SH002", inputId, inputQty)) {
                database.insertItem("ST001", inputId, inputQty);
                this.successMove();
            }
            else {
                this.failedMove();
            }
        } else if (fromComboBox.getValue().toString() == "Shop 1" && toComboBox.getValue().toString() == "Storage 2") {
            if(database.deleteItem("SH001", inputId, inputQty)) {
                database.insertItem("ST002", inputId, inputQty);
                this.successMove();
            }
            else {
                this.failedMove();
            }
        } else if (fromComboBox.getValue().toString()== "Shop 2" && toComboBox.getValue().toString() == "Storage 2") {
            if(database.deleteItem("SH002", inputId, inputQty)) {
                database.insertItem("ST002", inputId, inputQty);
                this.successMove();
            }
            else {
                this.failedMove();
            }
        } else if (fromComboBox.getValue().toString() == "Storage 1" && toComboBox.getValue().toString() == "Storage 2") {
            if(database.deleteItem("ST001", inputId, inputQty)) {
                database.insertItem("ST002", inputId, inputQty);
                this.successMove();
            }
            else {
                this.failedMove();
            }
        } else if (fromComboBox.getValue().toString() == "Storage 2" && toComboBox.getValue().toString() == "Storage 1") {
            if(database.deleteItem("ST002", inputId, inputQty)) {
                database.insertItem("ST001", inputId, inputQty);
                this.successMove();
            }
            else {
                this.failedMove();
            }
        } else if (fromComboBox.getValue().toString() == "Storage 1" && toComboBox.getValue().toString() == "Shop 1") {
            if(database.deleteItem("ST001", inputId, inputQty)) {
                database.insertItem("SH001", inputId, inputQty);
                this.successMove();
            }
            else {
                this.failedMove();
            }
        } else if (fromComboBox.getValue().toString() == "Storage 1" && toComboBox.getValue().toString() == "Shop 2") {
            if(database.deleteItem("ST001", inputId, inputQty)) {
                database.insertItem("SH002", inputId, inputQty);
                this.successMove();
            }
            else {
                this.failedMove();
            }
        } else if (fromComboBox.getValue().toString() == "Storage 2" && toComboBox.getValue().toString() == "Shop 1") {
            if(database.deleteItem("ST002", inputId, inputQty)) {
                database.insertItem("SH001", inputId, inputQty);
                this.successMove();
            }
            else {
                this.failedMove();
            }
        } else if (fromComboBox.getValue().toString() == "Storage 2" && toComboBox.getValue().toString() == "Shop 2") {
            if(database.deleteItem("ST002", inputId, inputQty)) {
                database.insertItem("SH002", inputId, inputQty);
                this.successMove();
            }
            else {
                this.failedMove();
            }
        }
    }





//    public boolean isUnique(TextField itemIdTextField)
//    {
//        //For each loop to check every product inside products observable array list.
//        for(Item item:shopItems)
//        {
//            //if the part number inputted by the user exist in the observable list the function returns false
//            if(item.getItemId().equals(itemIdTextFieldST.getText()))
//            {
//                return false;
//            }
//        }
//        //returns true by default
//        return true;
//    }

    public boolean isInt(TextField input) {
        //Using Exception handling try catch to validate the input of a text field
        try {
            Integer.parseInt(input.getText());
            //return true if the parse process is success
            return true;

        } catch (NumberFormatException exception) {
            //return false if there is an error while parsing
            return false;
        }
    }




    public void goShopButtonTTClicked() throws SQLException {
        shopItemsObservableListTT.clear();
        Connection con = null;
        PreparedStatement stat = null;
        ResultSet shop1 = null;
        ResultSet shop1Items = null;
        String shopId = "SH002";
        if (shopComboBoxTT.getValue() == "Shop 1") {
            shopId = "SH001";
        }
        try {
                con = DriverManager.getConnection(database.host, database.userName, database.password);
                stat = con.prepareStatement(database.selectAllQuery + database.shopTable + " where shop_id = ?");

                stat.setString(1, shopId);

                shop1 = stat.executeQuery();
            while (shop1.next()) {
                shopIdTextTT.setText(shop1.getString("shop_id"));
                shopAddressTextTT.setText(shop1.getString("shop_address"));
                shopNumberTextTT.setText(shop1.getString("shop_telephone_number"));
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
                shopItemsObservableListTT.add(new Item(shop1Items.getString("item_id"),Integer.parseInt(shop1Items.getString("shop_stock_quantity")),shop1Items.getString("item_description")));
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


    public void goStorageButtonBITClicked() throws SQLException{
        storageItemsObservableListBIT.clear();
        Connection con = null;
        PreparedStatement stat = null;
        String storageId = "ST002";
        if (storageComboBoxBIT.getValue() == "Storage 1") {

           storageId = "ST001";
        }

        ResultSet storage1 = null;
        ResultSet storage1Items = null;
        try {
            con = DriverManager.getConnection(database.host, database.userName, database.password);
            stat = con.prepareStatement(database.selectAllQuery + database.storageTable + " where storage_id = ?");

            stat.setString(1, storageId);

            storage1 = stat.executeQuery();
            while (storage1.next()) {
                storageIdTextBIT.setText(storage1.getString("storage_id"));
                storageAddressTextBIT.setText(storage1.getString("storage_address"));
                storageNumberTextBIT.setText(storage1.getString("storage_telephone_number"));
            }

        } catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }finally {
            try {storage1.close();}catch (Exception e){}
            try { stat.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }

        try {
            con = DriverManager.getConnection(database.host, database.userName, database.password);
            stat = con.prepareStatement("select `Storage Stock`.*, Items.item_description \n" +
                    "from `Storage Stock`\n" +
                    "inner join Items\n" +
                    "on `Storage Stock`.item_id = Items.item_id where storage_id = ?");

            stat.setString(1, storageId);

            storage1Items = stat.executeQuery();
            while (storage1Items.next())
            {
                storageItemsObservableListBIT.add(new Item(storage1Items.getString("item_id"), Integer.parseInt(storage1Items.getString("storage_stock_quantity")), storage1Items.getString("item_description")));
            }

        }catch(SQLException e)
        {
            System.out.println(e);
        }finally {
            try {storage1Items.close();}catch (Exception e){}
            try { stat.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
        System.out.println("done");
    }

    static String customerNameGlobal ="";
    static int grandTotalGlobal= 0;

    public void addButtonClickedTT(javafx.event.ActionEvent event) throws SQLException {

        if (shopIdTextTT.getText().equals("None")){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Warning !");
            a.setContentText("Please select Shop first to load info!");
            a.show();
        }
        else {
            System.out.println(shopIdTextTT.getText());
            String customerName = customerNameTextField.getText();
            String customerAddress = customerAddressTextField.getText();
            String customerPhoneNumber = customerNumberTextField.getText();
            String itemId = enterItemIdTextField.getText();
            String itemDesc = database.getItemDescription(Integer.parseInt(itemId));
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
                database.updateShopStorage(shopItemQty, Integer.parseInt(itemId), shopIdTextTT.getText());
                totalSalesTodayTT.setText("Rp"+String.valueOf(database.getTotalSales("Sell")));
                currentTransactionTableDataTT.add(new CurrentTransactionTableTT(itemId, itemDesc, itemQty, itemPrice, itemTotal));

            }


            grandTotalTT.setText("Rp"+String.valueOf(grandTotalGlobal));
            fillTableTransactionHistoryTT(1);
            System.out.println("done");


        }

    }

    static String supplierNameGlobal = "";
    public void addButtonClickedBIT(javafx.event.ActionEvent event) throws SQLException {
        if (storageIdTextBIT.getText().equals("None")) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Warning !");
            a.setContentText("Please select storage first to load info!");
            a.show();
        } else {
            String supplierName = supplierNameTextFieldBIT.getText();
            String supplierAddress = supplierAddressTextFieldBIT.getText();
            String supplierPhoneNumber = supplierPhoneTextFieldBIT.getText();
            String itemId = itemIdTextFieldBIT.getText();
            String itemName = itemNameTextFieldBIT.getText();
            int itemQty = Integer.parseInt(itemQuantityTextFieldBIT.getText());
            int itemSellPrice = Integer.parseInt(sellPriceTextFieldBIT.getText());
            int itemBuyPrice = Integer.parseInt(buyPriceTextFieldBIT.getText());
            int itemTotal = itemBuyPrice * itemQty;


            supplierNameLabelBIT.setText(supplierName);
            supplierAddressLabelBIT.setText(supplierAddress);
            supplierPhoneLabelBIT.setText(supplierPhoneNumber);
            int storageQty = database.getQtyFromStorage(Integer.parseInt(itemId), storageIdTextBIT.getText())+ itemQty;
            System.out.println(storageQty);
            if (!supplierNameGlobal.equals(supplierName)) {
                database.insertTransactor(supplierName, supplierAddress, supplierPhoneNumber);
            }
            int transactorId = database.getTransactorId(supplierName);

            database.registerItem(Integer.parseInt(itemId), itemName, itemSellPrice);
//
            database.insertTransaction(Integer.parseInt(itemId), itemQty, itemTotal, transactorId, "Buy");
            database.supplierToStorage(storageIdTextBIT.getText(), Integer.parseInt(itemId), storageQty);
//
            currentTransactionTableDataBIT.add(new CurrentTransactionTableBIT(itemId, itemName, itemQty, itemBuyPrice, itemSellPrice, itemTotal));

        }
    }

}
