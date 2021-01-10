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
    public Button goStorageButtonST,goShopButtonST,moveButtonST,goShopButtonTT, addButtonTT, saveButtonTT, goStorageButtonBIT,
            addButtonBIT, saveButtonBIT,deleteButtonTT,deleteButtonBIT,searchButtonCB,refreshButtonCB,searchButtonMD,refreshButtonMD,
            updateButtonMD,updateButtonCB, searchButtonCBTransaction, refreshButtonCBTransaction, updateButtonCBTransaction;
    @FXML
    public TableView storageTableViewST, shopTableViewST,shopStockTableViewTT, currentTransactionTableViewTT, transactionHistoryTableViewTT, storageTableViewBIT, currentTransactionTableViewBIT, transactionHistoryTableViewBIT, contactBookTableView,itemTableViewMD, transactionHistoryTableVIewCB;
    @FXML
    public TextField itemQuantityTextFieldST,customerNameTextField, customerAddressTextField, customerNumberTextField,
            enterItemIdTextField, enterItemQtyTextField, enterColumnTT, supplierNameTextFieldBIT, supplierAddressTextFieldBIT, supplierPhoneTextFieldBIT,
            itemIdTextFieldBIT, itemQuantityTextFieldBIT, sellPriceTextFieldBIT, buyPriceTextFieldBIT,itemNameTextFieldBIT,enterColumnBIT,
            enterTransactorIdCB, enterItemIdMD, enterItemNameMD,enterItemSellPriceMD,enterTransactorNameCB,enterTransactorAddressCB,enterTransactorPhoneNumberCB, enterTransactionIdCB, enterTransactionQtyCB
            , enterItemIdCB,enterShopIdCB;
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
    ObservableList<TransactionHistory>transactionHistoryObservableListCB = FXCollections.observableArrayList();
    ObservableList<Item> storageItemsObservableListBIT = FXCollections.observableArrayList();
    ObservableList<Item> shopItemsObservableListTT = FXCollections.observableArrayList();
    ObservableList<Transactor> transactorsObservableListCB = FXCollections.observableArrayList();
    ObservableList<Item> itemsObservableListMD = FXCollections.observableArrayList();




//Initializing Table Column for CurrentTransaction Table Transaction Tab
    public TableColumn<CurrentTransactionTableTT,Integer>ttransactionIdColumnTT;
    public TableColumn<CurrentTransactionTableTT,String>transactionIdColumnTT;
    public TableColumn<CurrentTransactionTableTT,String>transactionItemDescColumnTT;
    public TableColumn<CurrentTransactionTableTT,Integer>transactionQtyColumnTT;
    public TableColumn<CurrentTransactionTableTT,Integer>transactionPriceColumnTT;
    public TableColumn<CurrentTransactionTableTT,Integer>transactionTotalColumnTT;
    ObservableList<CurrentTransactionTableTT> currentTransactionTableDataTT = FXCollections.observableArrayList();

    public TableColumn<CurrentTransactionTableBIT,Integer>ttransactionIdColumnBIT;
    public TableColumn<CurrentTransactionTableBIT,String>transactionIdColumnBIT;
    public TableColumn<CurrentTransactionTableBIT,String>transactionItemNameColumnBIT;
    public TableColumn<CurrentTransactionTableBIT,Integer>transactionQtyColumnBIT;
    public TableColumn<CurrentTransactionTableBIT,Integer>transactionBuyPriceColumnBIT;
    public TableColumn<CurrentTransactionTableBIT,Integer>transactionSellPriceColumnBIT;
    public TableColumn<CurrentTransactionTableBIT,Integer>transactionTotalColumnBIT;
    ObservableList<CurrentTransactionTableBIT> currentTransactionTableDataBIT = FXCollections.observableArrayList();

//    initialize database class , so can access certain queries from database
    public Database database = new Database();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//  Initialize the combo box to fill the content
        storageComboBoxST.setItems(storageLists);
        shopComboBoxST.setItems(shopLists);
        shopComboBoxTT.setItems(shopLists);
        storageComboBoxBIT.setItems(storageLists);
        fromComboBox.setItems(shopStorageLists);

//        Populate all the table with the table from the interface
        try {
//            fill Transactor Table for the Contact book tab
            fillTransactor();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
//            Fill the Item table in Item Modification Table
            fillItem();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        fillTableTransactionHistoryTT(1);
        try {
//            Fill the transaction History Table on Buy item based on current date
            fillTableTransactionHistoryBIT();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
//            Fill the table transaction History on the contact book tab
            fillTableTransactionHistoryCB();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //storageTableViewST
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

        storageTableViewST.getColumns().addAll(itemIdStorage,  itemDescStorage, itemQtyStorage);

//        shopStockTableViewTT
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


        //shopTableViewST
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

        shopTableViewST.getColumns().addAll(itemIdShop, itemDescShop, itemQtyShop);

        //transactionHistoryTableViewTT
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



        //transactionHistoryTableViewBIT
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



        //contactBookTableView
        TableColumn transactorId = new TableColumn("Transactor ID");
        transactorId.setMinWidth(300);
        transactorId.setCellValueFactory(new PropertyValueFactory<Transactor,Integer>("transactorId"));

        TableColumn transactorName = new TableColumn("Name");
        transactorName.setMinWidth(300);
        transactorName.setCellValueFactory(new PropertyValueFactory<Transactor,String>("name"));

        TableColumn transactorAddress = new TableColumn("Transactor Address");
        transactorAddress.setMinWidth(300);
        transactorAddress.setCellValueFactory(new PropertyValueFactory<Transactor,String>("address"));

        TableColumn transactorPhoneNumber = new TableColumn("Phone Number");
        transactorPhoneNumber.setMinWidth(300);
        transactorPhoneNumber.setCellValueFactory(new PropertyValueFactory<Transactor,String >("phoneNumber"));
        contactBookTableView.setItems(transactorsObservableListCB);
        contactBookTableView.getColumns().addAll(transactorId,transactorName,transactorAddress,transactorPhoneNumber);



        //transactionHistoryTableVIewCB
        TableColumn transactionIdCB = new TableColumn("Transaction ID");
        transactionIdCB.setMinWidth(300);
        transactionIdCB.setCellValueFactory(new PropertyValueFactory<TransactionHistory,String>("transactionId"));

        TableColumn dateCB = new TableColumn("Date");
        dateCB.setMinWidth(300);
        dateCB.setCellValueFactory(new PropertyValueFactory<TransactionHistory,String>("date"));

        TableColumn itemIdCB = new TableColumn("Item ID");
        itemIdCB.setMinWidth(300);
        itemIdCB.setCellValueFactory(new PropertyValueFactory<TransactionHistory,String>("itemId"));

        TableColumn itemQtyCB = new TableColumn("Item Qty");
        itemQtyCB.setMinWidth(300);
        itemQtyCB.setCellValueFactory(new PropertyValueFactory<TransactionHistory,Integer>("itemQty"));

        TableColumn itemDescCB = new TableColumn("Item Desc");
        itemDescCB.setMinWidth(300);
        itemDescCB.setCellValueFactory(new PropertyValueFactory<TransactionHistory,String>("itemDesc"));

        TableColumn  totalPriceCB = new TableColumn("Total Price");
        totalPriceCB.setMinWidth(300);
        totalPriceCB.setCellValueFactory(new PropertyValueFactory<TransactionHistory,Integer>("price"));

        TableColumn customerNameCB = new TableColumn("Supplier Name");
        customerNameCB.setMinWidth(300);
        customerNameCB.setCellValueFactory(new PropertyValueFactory<TransactionHistory,String>("name"));

        TableColumn customerAddressCB = new TableColumn("Address");
        customerAddressCB.setMinWidth(300);
        customerAddressCB.setCellValueFactory(new PropertyValueFactory<TransactionHistory,String>("address"));

        TableColumn customerPhoneNumberCB = new TableColumn("Phone Number");
        customerPhoneNumberCB.setMinWidth(300);
        customerPhoneNumberCB.setCellValueFactory(new PropertyValueFactory<TransactionHistory,String>("phoneNumber"));

        TableColumn customerTransactionTypeCB = new TableColumn("Transaction Type");
        customerTransactionTypeCB.setMinWidth(300);
        customerTransactionTypeCB.setCellValueFactory(new PropertyValueFactory<TransactionHistory,String>("transactionType"));

        transactionHistoryTableVIewCB.setItems(transactionHistoryObservableListCB);
        transactionHistoryTableVIewCB.getColumns().addAll(transactionIdCB,dateCB,itemIdCB,itemQtyCB,itemDescCB,totalPriceCB,customerNameCB
                ,customerAddressCB,customerPhoneNumberCB, customerTransactionTypeCB);


        TableColumn itemIdMD = new TableColumn("Item ID");
        itemIdMD.setMinWidth(300);
        itemIdMD.setCellValueFactory(new PropertyValueFactory<Item,String>("itemId"));

        TableColumn itemNameMD = new TableColumn("Item Name");
        itemNameMD.setMinWidth(300);
        itemNameMD.setCellValueFactory(new PropertyValueFactory<Item,String>("itemDesc"));

//        for the sell price , use the Item class on itemQty parameter
        TableColumn itemSellPriceMD = new TableColumn("Item Sell Price");
        itemSellPriceMD.setMinWidth(300);
        itemSellPriceMD.setCellValueFactory(new PropertyValueFactory<Item,Integer>("itemQty"));

        itemTableViewMD.setItems(itemsObservableListMD);
        itemTableViewMD.getColumns().addAll(itemIdMD,itemNameMD,itemSellPriceMD);

//        setting the observable list for the current transaction Table TT;
//        The real transaction Id column is the first one

        ttransactionIdColumnTT.setMinWidth(300);
        transactionIdColumnTT.setCellValueFactory(new PropertyValueFactory<CurrentTransactionTableTT,String>("itemId"));
        transactionItemDescColumnTT.setCellValueFactory(new PropertyValueFactory<CurrentTransactionTableTT,String>("itemDesc"));
        transactionQtyColumnTT.setCellValueFactory(new PropertyValueFactory<CurrentTransactionTableTT,Integer>("itemQty"));
        transactionPriceColumnTT.setCellValueFactory(new PropertyValueFactory<CurrentTransactionTableTT,Integer>("itemPrice"));
        transactionTotalColumnTT.setCellValueFactory(new PropertyValueFactory<CurrentTransactionTableTT,Integer>("total"));
        ttransactionIdColumnTT.setCellValueFactory(new PropertyValueFactory<CurrentTransactionTableTT,Integer>("transactionId"));
        currentTransactionTableViewTT.setItems(currentTransactionTableDataTT);

//        Setting the observable list for the current transaction table BIT
        ttransactionIdColumnBIT.setMinWidth(300);
        ttransactionIdColumnBIT.setCellValueFactory(new PropertyValueFactory<CurrentTransactionTableBIT,Integer>("transactionId"));
        transactionIdColumnBIT.setCellValueFactory(new PropertyValueFactory<CurrentTransactionTableBIT,String>("itemId"));
        transactionItemNameColumnBIT.setMinWidth(300);
        transactionItemNameColumnBIT.setCellValueFactory(new PropertyValueFactory<CurrentTransactionTableBIT,String>("itemName"));
        transactionQtyColumnBIT.setCellValueFactory(new PropertyValueFactory<CurrentTransactionTableBIT,Integer>("itemQty"));
        transactionBuyPriceColumnBIT.setCellValueFactory(new PropertyValueFactory<CurrentTransactionTableBIT,Integer>("itemBuyPrice"));
        transactionSellPriceColumnBIT.setCellValueFactory(new PropertyValueFactory<CurrentTransactionTableBIT,Integer>("itemSellPrice"));
        transactionTotalColumnBIT.setCellValueFactory(new PropertyValueFactory<CurrentTransactionTableBIT,Integer>("total"));
        currentTransactionTableViewBIT.setItems(currentTransactionTableDataBIT);
    }

//   Function That triggers when Button is clicked


//Storing Center Tab
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
            storageItems.clear();
            String storageId = "ST002";
            if(storageComboBoxST.getValue() == "Storage 1")
            {
                storageId = "ST001";
            }
            fillStorageTable(storageId, storageItems, storageIdTextST, storageAddressTextST, storageNumberTextST);
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
            shopItems.clear();
            String shopId = "SH002";
            if(shopComboBoxST.getValue() == "Shop 1")
            {
                shopId = "SH001";
            }
            fillShopTable(shopId, shopItems, shopIdTextST, shopAddressTextST, shopNumberTextST);
        }
    }


    public void fromComboBoxPicked() throws SQLException {
        if (shopLists.contains(fromComboBox.getValue())) {
            toComboBox.setItems(storageLists);
        } else {
            toComboBox.setItems(shopStorageLists);
        }

        if (fromComboBox.getValue().toString() == "Shop 1") {
            fillComboBoxShopItemId("SH001", shop1ItemIDs);
            itemIDComboBoxST.setItems(shop1ItemIDs);
        }
        else if (fromComboBox.getValue().toString() == "Shop 2") {
            fillComboBoxShopItemId("SH002", shop2ItemIDs);
            itemIDComboBoxST.setItems(shop2ItemIDs);
        }
        else if (fromComboBox.getValue().toString() == "Storage 1") {
            fillComboBoxStorageItemId("ST001", storage1ItemIDs);
            itemIDComboBoxST.setItems(storage1ItemIDs);
        }
        else {
            fillComboBoxStorageItemId("ST002", storage2ItemIDs);
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
        Integer inputId;
        Integer inputQty;
        Alert a = new Alert(Alert.AlertType.WARNING);

        if (fromComboBox.getSelectionModel().isEmpty() && toComboBox.getSelectionModel().isEmpty() && itemIDComboBoxST.getSelectionModel().isEmpty() && itemQuantityTextFieldST.getText().isEmpty()) {
            a.setTitle("Warning");
            a.setContentText("Please pick the From Combo Box, To Combo Box, Item ID Combo Box and fill the Quantity Field!");
            a.show();
        } else if(fromComboBox.getSelectionModel().isEmpty() || toComboBox.getSelectionModel().isEmpty() || itemIDComboBoxST.getSelectionModel().isEmpty() || itemQuantityTextFieldST.getText().isEmpty()) {
            if(fromComboBox.getSelectionModel().isEmpty() && itemIDComboBoxST.getSelectionModel().isEmpty() && itemQuantityTextFieldST.getText().isEmpty()) {
                a.setTitle("Warning");
                a.setContentText("Please pick the From Combo Box, ID Combo Box and fill the Quantity Field!");
                a.show();
            }
            else if(toComboBox.getSelectionModel().isEmpty() && itemIDComboBoxST.getSelectionModel().isEmpty() && itemQuantityTextFieldST.getText().isEmpty()){
                a.setTitle("Warning");
                a.setContentText("Please pick the To Combo Box, Item ID Combo Box and fill the Quantity Field!");
                a.show();
            }
            else if(itemIDComboBoxST.getSelectionModel().isEmpty() && itemQuantityTextFieldST.getText().isEmpty() )
            {
                a.setTitle("Warning");
                a.setContentText("Please pick Item ID Combo Box and fill the Quantity Field!");
                a.show();
            }
            else if(fromComboBox.getSelectionModel().isEmpty())
            {
                a.setTitle("Warning");
                a.setContentText("Please pick the From Combo Box!");
                a.show();
            }
            else if(toComboBox.getSelectionModel().isEmpty())
            {
                a.setTitle("Warning");
                a.setContentText("Please pick the To Combo Box!");
                a.show();
            }

            else if(itemIDComboBoxST.getSelectionModel().isEmpty() || itemQuantityTextFieldST.getText().isEmpty()) {
                if (itemIDComboBoxST.getSelectionModel().isEmpty()) {
                    a.setTitle("Warning");
                    a.setContentText("Please pick the Item ID Combo Box!");
                    a.show();
                } else if (itemQuantityTextFieldST.getText().isEmpty()) {
                    a.setTitle("Warning");
                    a.setContentText("Please fill the Quantity Field!");
                    a.show();
                }
            }
        } else if(fromComboBox.getValue().toString() == "Storage 1" && toComboBox.getValue().toString() == "Storage 1") {
            a.setTitle("Warning!");
            a.setContentText("Can't move item to the same place!");
            a.show();
            toComboBox.getSelectionModel().clearSelection();
        } else if (fromComboBox.getValue().toString() == "Storage 2" && toComboBox.getValue().toString() == "Storage 2") {
            a.setTitle("Warning!");
            a.setContentText("Can't move item to the same place!");
            a.show();
            toComboBox.getSelectionModel().clearSelection();
        }else if (!itemQuantityTextFieldST.getText().isEmpty()) {
            if (!isInt(itemQuantityTextFieldST)) {
                a.setTitle("Warning");
                a.setContentText("Quantity Field must be numbers!");
                itemQuantityTextFieldST.clear();
                a.show();
            }
            else if (Integer.parseInt(itemQuantityTextFieldST.getText()) == 0) {
                a.setTitle("Warning");
                a.setContentText("Quantity Field more than 0!");
                itemQuantityTextFieldST.clear();
                a.show();
            }
        }
        else {
            inputId = (Integer) itemIDComboBoxST.getValue();
            inputQty = Integer.parseInt(itemQuantityTextFieldST.getText());

            if (fromComboBox.getValue().toString() == "Shop 1" && toComboBox.getValue().toString() == "Storage 1") {
                if (database.deleteItem("SH001", inputId, inputQty)) {
                    database.insertItem("ST001", inputId, inputQty);
                    this.successMove();
                } else {
                    this.failedMove();
                }
            } else if (fromComboBox.getValue().toString() == "Shop 2" && toComboBox.getValue().toString() == "Storage 1") {
                if (database.deleteItem("SH002", inputId, inputQty)) {
                    database.insertItem("ST001", inputId, inputQty);
                    this.successMove();
                } else {
                    this.failedMove();
                }
            } else if (fromComboBox.getValue().toString() == "Shop 1" && toComboBox.getValue().toString() == "Storage 2") {
                if (database.deleteItem("SH001", inputId, inputQty)) {
                    database.insertItem("ST002", inputId, inputQty);
                    this.successMove();
                } else {
                    this.failedMove();
                }
            } else if (fromComboBox.getValue().toString() == "Shop 2" && toComboBox.getValue().toString() == "Storage 2") {
                if (database.deleteItem("SH002", inputId, inputQty)) {
                    database.insertItem("ST002", inputId, inputQty);
                    this.successMove();
                } else {
                    this.failedMove();
                }
            } else if (fromComboBox.getValue().toString() == "Storage 1" && toComboBox.getValue().toString() == "Storage 2") {
                if (database.deleteItem("ST001", inputId, inputQty)) {
                    database.insertItem("ST002", inputId, inputQty);
                    this.successMove();
                } else {
                    this.failedMove();
                }
            } else if (fromComboBox.getValue().toString() == "Storage 2" && toComboBox.getValue().toString() == "Storage 1") {
                if (database.deleteItem("ST002", inputId, inputQty)) {
                    database.insertItem("ST001", inputId, inputQty);
                    this.successMove();
                } else {
                    this.failedMove();
                }
            } else if (fromComboBox.getValue().toString() == "Storage 1" && toComboBox.getValue().toString() == "Shop 1") {
                if (database.deleteItem("ST001", inputId, inputQty)) {
                    database.insertItem("SH001", inputId, inputQty);
                    this.successMove();
                } else {
                    this.failedMove();
                }
            } else if (fromComboBox.getValue().toString() == "Storage 1" && toComboBox.getValue().toString() == "Shop 2") {
                if (database.deleteItem("ST001", inputId, inputQty)) {
                    database.insertItem("SH002", inputId, inputQty);
                    this.successMove();
                } else {
                    this.failedMove();
                }
            } else if (fromComboBox.getValue().toString() == "Storage 2" && toComboBox.getValue().toString() == "Shop 1") {
                if (database.deleteItem("ST002", inputId, inputQty)) {
                    database.insertItem("SH001", inputId, inputQty);
                    this.successMove();
                } else {
                    this.failedMove();
                }
            } else if (fromComboBox.getValue().toString() == "Storage 2" && toComboBox.getValue().toString() == "Shop 2") {
                if (database.deleteItem("ST002", inputId, inputQty)) {
                    database.insertItem("SH002", inputId, inputQty);
                    this.successMove();
                } else {
                    this.failedMove();
                }
            }
        }
    }

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
//    Storing Center Tab



//    Transaction Tab
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
        currentTransactionTableDataTT.clear();
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
        else if(currentTransactionTableDataTT.size()==0){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Warning !");
            a.setContentText("There's No Transaction To Be Deleted!");
            a.show();
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
            database.resetAutoIncremenTransaction();
        }

    }


    public void saveButtonBITClicked() throws SQLException {
        if (currentTransactionTableDataBIT.size() == 0) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Warning !");
            a.setContentText("Please Input a Trasaction!");
            a.show();
        } else {
            currentTransactionTableDataBIT.clear();
            transactionHistoryObservableListBIT.clear();
            fillTableTransactionHistoryBIT();

        }
        currentTransactionTableDataBIT.clear();
    }
//    Transaction Tab



//    Buy Items Tab
    public void goStorageButtonBITClicked() throws SQLException{
        storageItemsObservableListBIT.clear();
        String storageId = "ST002";
        if (storageComboBoxBIT.getValue() == "Storage 1") {

            storageId = "ST001";
        }
        fillStorageTable(storageId,storageItemsObservableListBIT, storageIdTextBIT, storageAddressTextBIT, storageNumberTextBIT);

        System.out.println("done");
    }


    public void deleteButtonClickedBIT() throws SQLException {
        if (enterColumnBIT.getText().isEmpty()){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Warning !");
            a.setContentText("Please Input Column Transaction TextField!");
            a.show();
        }
        else if(intOnly(enterColumnBIT.getText(),"Column Transaction!")==0){

        }

        else if (currentTransactionTableDataBIT.size()==0){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Warning !");
            a.setContentText("There's No Transaction To Be Deleted!");
            a.show();
        }
        else{
            int column = Integer.parseInt(enterColumnBIT.getText())-1;
            int transactionId = currentTransactionTableDataBIT.get(column).getTransactionId();
            int itemId = Integer.parseInt(currentTransactionTableDataBIT.get(column).getItemId());
            String storageId = storageIdTextBIT.getText();
            int storageQty = database.getQtyFromStorage(itemId,storageId);
            int qty = storageQty-currentTransactionTableDataBIT.get(column).getItemQty();
            database.supplierToStorage(storageId,itemId,qty);
            database.deleteTransaction(transactionId);
            currentTransactionTableDataBIT.remove(column);
            transactionHistoryObservableListBIT.clear();
            storageItemsObservableListBIT.clear();
            fillStorageTable(storageId, storageItemsObservableListBIT, storageIdTextBIT, storageAddressTextBIT, storageNumberTextBIT);
            fillTableTransactionHistoryBIT();
            database.resetAutoIncremenTransaction();
        }

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

    static String supplierNameGlobal = "";
    public void addButtonClickedBIT(javafx.event.ActionEvent event) throws SQLException {
        String supplierName = supplierNameTextFieldBIT.getText();
        String supplierAddress = supplierAddressTextFieldBIT.getText();
        String supplierPhoneNumber = supplierPhoneTextFieldBIT.getText();
        String itemId = itemIdTextFieldBIT.getText();
        String itemName = itemNameTextFieldBIT.getText();

        if (storageIdTextBIT.getText().equals("None")) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Warning !");
            a.setContentText("Please select storage first to load info!");
            a.show();
        }

        else if (supplierName.isEmpty()||supplierAddress.isEmpty()||supplierPhoneNumber.isEmpty()||itemId.isEmpty()||itemName.isEmpty()||itemQuantityTextFieldBIT.getText().isEmpty()||sellPriceTextFieldBIT.getText().isEmpty()||buyPriceTextFieldBIT.getText().isEmpty()){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Warning !");
            a.setContentText("Please Input Supplier Name , Supplier Address, Supplier Phone Number, Item ID, Item Name, Item Quantity, Sell Price, Buy Price!");
            a.show();
        }

        else if(intOnly(itemId,"Item ID TextField!")==0||intOnly(itemQuantityTextFieldBIT.getText(),"Item Quantity TextField!")==0||intOnly(sellPriceTextFieldBIT.getText(),"Sell Price TextField")==0||intOnly(buyPriceTextFieldBIT.getText(),"Buy Price TextField")==0||intOnly(supplierPhoneNumber,"Supplier PhoneNumber TextField")==0){

        }

        else {
            int transactionId = database.getNextTransactionId();
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
            currentTransactionTableDataBIT.add(new CurrentTransactionTableBIT(itemId, itemName, itemQty, itemBuyPrice, itemSellPrice, itemTotal,transactionId));
            storageItemsObservableListBIT.clear();
            fillStorageTable(storageIdTextBIT.getText(), storageItemsObservableListBIT, storageIdTextBIT, storageAddressTextBIT, storageNumberTextBIT);
        }
    }

//    Buy Items Tab



// Contact Book and Trouble Center Tab
    public void searchButtonCBClicked () throws SQLException {
        Alert a =  new Alert(Alert.AlertType.WARNING);
        if(enterTransactorIdCB.getText().isEmpty())
        {
            a.setTitle("Warning!");
            a.setContentText("Please fill the Transactor Id Field!");
            a.show();
        }
        else if(intOnly(enterTransactorIdCB.getText(),"Transactor ID TextField!")==0)
        {

        }
        else
        {
            int transactorID = Integer.parseInt(enterTransactorIdCB.getText());
            if(enterTransactorIdCB.getText().equals("")){
                a.setTitle("Warning !");
                a.setContentText("Please input Transactor ID!");
                a.show();
            }
            else{
                transactorsObservableListCB.clear();
                searchTransactorId(transactorID);
            }
        }
    }

    public void updateTransactorCB() throws SQLException {
        Alert a = new Alert(Alert.AlertType.WARNING);
        //Block of Conditions to check all field to get info is filled or not in Update Button Transactor Contact Book Tab
        if(enterTransactorNameCB.getText().equals("") && enterTransactorAddressCB.getText().equals("") && enterTransactorPhoneNumberCB.getText().equals("") && enterTransactorIdCB.getText().equals("") && enterTransactorIdCB.getText().equals(""))
        {
            a.setTitle("Warning !");
            a.setContentText("Please input Transactor ID, Transactor Name, Transactor Address, and Transactor Phone Number Fields!");
            a.show();
        }

        else if (enterTransactorNameCB.getText().equals("") || enterTransactorAddressCB.getText().equals("") || enterTransactorPhoneNumberCB.getText().equals("") || enterTransactorIdCB.getText().equals("") || enterTransactorIdCB.getText().equals("")){
           if (enterTransactorNameCB.getText().equals("") && enterTransactorAddressCB.getText().equals("") && enterTransactorIdCB.getText().equals("")) {
               a.setTitle("Warning !");
               a.setContentText("Please input Transactor ID, Transactor Name and Transactor Address Fields!");
               a.show();
           }
            else if (enterTransactorNameCB.getText().equals("") && enterTransactorPhoneNumberCB.getText().equals("") && enterTransactorIdCB.getText().equals("")) {
                a.setTitle("Warning !");
                a.setContentText("Please input Transactor ID, Transactor Name and Transactor Phone Number Fields!");
                a.show();
            }
           else if (enterTransactorAddressCB.getText().equals("") && enterTransactorPhoneNumberCB.getText().equals("") && enterTransactorIdCB.getText().equals("")) {
               a.setTitle("Warning !");
               a.setContentText("Please input Transactor ID, Transactor Address and Transactor Phone Number Fields!");
               a.show();
           }
           else if (enterTransactorNameCB.getText().equals("") && enterTransactorAddressCB.getText().equals("")) {
               a.setTitle("Warning !");
               a.setContentText("Please input Transactor Name and Transactor Address!");
               a.show();
           }
           else if (enterTransactorNameCB.getText().equals("") && enterTransactorPhoneNumberCB.getText().equals("")) {
               a.setTitle("Warning !");
               a.setContentText("Please input Transactor Name and Transactor Phone Number!");
               a.show();
           }
           else if (enterTransactorAddressCB.getText().equals("") && enterTransactorPhoneNumberCB.getText().equals("")) {
               a.setTitle("Warning !");
               a.setContentText("Please input Transactor Address and Transactor Phone Number Fields!");
               a.show();
           }
           else if (enterTransactorNameCB.getText().equals("")) {
               a.setTitle("Warning !");
               a.setContentText("Please input Transactor Name!");
               a.show();
           }
           else if (enterTransactorAddressCB.getText().equals("")) {
               a.setTitle("Warning !");
               a.setContentText("Please input Transactor Address!");
               a.show();
           }
           else if (enterTransactorPhoneNumberCB.getText().equals("")) {
               a.setTitle("Warning !");
               a.setContentText("Please input Transactor Phone Number!");
               a.show();
           }
           else if (enterTransactorIdCB.getText().equals("")) {
               a.setTitle("Warning !");
               a.setContentText("Please input Transactor ID!");
               a.show();
           }
           else if(!isInt(enterTransactorIdCB))
           {
               a.setTitle("Warning !");
               a.setContentText("Please input numbers only in Transactor ID!");
               a.show();

           }
        }
        else{
            int transactorId = Integer.parseInt(enterTransactorIdCB.getText());
            String name = enterTransactorNameCB.getText();
            String phoneNumber = enterTransactorPhoneNumberCB.getText();
            String address = enterTransactorAddressCB.getText();
            transactorsObservableListCB.clear();
            updateTransactorSql(transactorId,name,address,phoneNumber);
            searchTransactorId(transactorId);
        }
    }

    public void updateQuantityCBClicked() throws SQLException {
        Alert a = new Alert(Alert.AlertType.WARNING);
        if (enterTransactionIdCB.getText().equals("") && enterTransactionQtyCB.getText().equals("") && enterItemIdCB.getText().equals("") &&  enterShopIdCB.getText().equals("")){
            a.setTitle("Warning !");
            a.setContentText("Please input Transaction ID, Transaction Item Quantity, Item ID and Shop ID!");
            a.show();
        }
        else if(enterTransactionIdCB.getText().equals("") || enterTransactionQtyCB.getText().equals("") || enterItemIdCB.getText().equals("") ||  enterShopIdCB.getText().equals(""))
        {
            if(enterTransactionIdCB.getText().equals("") && enterTransactionQtyCB.getText().equals("") && enterItemIdCB.getText().equals("") )
            {
                a.setTitle("Warning !");
                a.setContentText("Please input Transaction ID, Transaction Item Quantity and Item ID!");
                a.show();
            }
            else if(enterTransactionIdCB.getText().equals("") && enterTransactionQtyCB.getText().equals("") && enterShopIdCB.getText().equals(""))
            {
                a.setTitle("Warning !");
                a.setContentText("Please input Transaction ID, Transaction Item Quantity and Shop ID!");
                a.show();
            }
            else if(enterTransactionIdCB.getText().equals("") && enterItemIdCB.getText().equals("") && enterShopIdCB.getText().equals(""))
            {
                a.setTitle("Warning !");
                a.setContentText("Please input Transaction ID, Item ID and Shop ID!");
                a.show();
            }
            else if (enterTransactionQtyCB.getText().equals("") && enterItemIdCB.getText().equals(""))
            {
                a.setTitle("Warning !");
                a.setContentText("Please input Transaction Item Quantity and Item ID!");
                a.show();
            }
            else if (enterTransactionQtyCB.getText().equals("") && enterShopIdCB.getText().equals(""))
            {
                a.setTitle("Warning !");
                a.setContentText("Please input Transaction Item Quantity and Shop ID!");
                a.show();
            }

            else if(enterItemIdCB.getText().equals("") && enterShopIdCB.getText().equals(""))
            {
                a.setTitle("Warning !");
                a.setContentText("Please input Item ID and Shop ID!");
                a.show();
            }
            else if(enterTransactionIdCB.getText().equals(""))
            {
                a.setTitle("Warning !");
                a.setContentText("Please input Transaction ID!");
                a.show();
            }
            else if(enterTransactionQtyCB.getText().equals(""))
            {
                a.setTitle("Warning !");
                a.setContentText("Please input Transaction Item Quantity!");
                a.show();
            }
            else if(enterItemIdCB.getText().equals(""))
            {
                a.setTitle("Warning !");
                a.setContentText("Please input Item ID!");
                a.show();
            }
            else if(enterShopIdCB.getText().equals(""))
            {
                a.setTitle("Warning !");
                a.setContentText("Please input Shop ID!");
                a.show();
            }
        }
        else if(!isInt(enterTransactionQtyCB))
        {
            a.setTitle("Warning !");
            a.setContentText("Please input numbers only in Transaction Item Quantity!");
            a.show();
        }
        else {
            if (enterShopIdCB.getText().equals("SH001") || enterShopIdCB.getText().equals("SH002")) {

                int itemId = Integer.parseInt(enterItemIdCB.getText());
                int transactionId = Integer.parseInt(enterTransactionIdCB.getText());
                int itemTransactionQty = database.getTransactionQty(transactionId);
                int pricePcs = transactionHistoryObservableListCB.get(0).getPrice() / itemTransactionQty;
                Integer quantityReturned = Integer.parseInt(enterTransactionQtyCB.getText());
                int totalPriceNew = (itemTransactionQty - quantityReturned) * pricePcs;
                String shopId = enterShopIdCB.getText();
                int shopStock = database.getQtyFromShop(itemId, shopId);
                int reduceTransactionQty = itemTransactionQty - quantityReturned;
                int reduceShopStock = shopStock - quantityReturned;
                int transactorId = transactionHistoryObservableListCB.get(0).getTransactorId();
                System.out.println(reduceShopStock);

                database.updateTransaction(transactionId, reduceTransactionQty, totalPriceNew);
                database.updateShopStock(reduceShopStock, Integer.parseInt(enterItemIdCB.getText()), shopId);
                database.insertTransaction(Integer.parseInt(enterItemIdCB.getText()), quantityReturned, 0, transactorId, "Sell");
                transactorsObservableListCB.clear();
                transactionHistoryObservableListCB.clear();
                searchTransactorId(transactorId);
                searchTransactionId(transactionId);
                searchTransactionId(database.getNextTransactionId() - 1);
            } else {
                a.setTitle("Warning !");
                a.setContentText("Please input 'SH001' or 'SH002' in Shop ID!");
                a.show();
            }
        }
    }

    public void searchButtonTransactionCBClicked() throws SQLException {
        Alert a =  new Alert(Alert.AlertType.WARNING);
        if(enterTransactionIdCB.getText().equals(""))
        {
            a.setTitle("Warning!");
            a.setContentText("Please input in order to search!");
            a.show();
        }

        else if (intOnly(enterTransactionIdCB.getText(),"Transaction ID field!")==0){

        }

        else
        {
            int transactionID = 0;
            transactionID = Integer.parseInt(enterTransactionIdCB.getText());
                transactionHistoryObservableListCB.clear();
                searchTransactionId(transactionID);

        }
    }
// Contact Book and Trouble Center


//Item Modification Tab
    public void searchButtonClickedMD() throws SQLException {
        if (intOnly(enterItemIdMD.getText(),"Item ID TextField!")==0){

        }
        else{
            int itemId = Integer.parseInt(enterItemIdMD.getText());
            itemsObservableListMD.clear();
            searchItem(itemId);
        }

    }
    public void refreshButtonClickMD() throws SQLException {
        itemsObservableListMD.clear();
        fillItem();
    }
    public void updateButtonClickMD() throws SQLException {
        if(enterItemIdMD.getText().isEmpty()||enterItemSellPriceMD.getText().isEmpty()){

        }
        else if(intOnly(enterItemIdMD.getText(),"Item ID TextField!")==0||intOnly(enterItemSellPriceMD.getText(),"Item Sell Price TextField!")==0){

        }
        else{
            int itemId = Integer.parseInt(enterItemIdMD.getText());
            int itemSellPrice = Integer.parseInt(enterItemSellPriceMD.getText());
            String itemName = enterItemNameMD.getText();
            if (enterItemNameMD.getText().equals("") || enterItemSellPriceMD.getText().equals("")||enterItemIdMD.getText().equals("")){
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setTitle("Warning !");
                a.setContentText("Please input Item ID and Item Sell Price");
                a.show();
            }
            else{
                itemsObservableListMD.clear();
                updateItemSql(itemId,itemName,itemSellPrice);
                searchItem(itemId);
            }
        }

    }

    public void refreshButtonClickedCB() throws SQLException {
        transactorsObservableListCB.clear();
        fillTransactor();
    }
    public void refreshButtonTransactionClickedCB() throws SQLException{
        transactionHistoryObservableListCB.clear();
        fillTableTransactionHistoryCB();
    }
//    Item Modification Tab

//    Database queries Function

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



//    Getting the Table from Sql and insert it into the Interface
    public void fillStorageTable(String storageId, ObservableList storageItems, Text storageIdText, Text storageAddressText, Text storageNumberText){
        Connection con = null;
        PreparedStatement stat = null;
        ResultSet storage1 = null;
        ResultSet storage1Items = null;
        try {
            con = DriverManager.getConnection(database.host, database.userName, database.password);
            stat = con.prepareStatement(database.selectAllQuery + database.storageTable + " where storage_id = ?");

            stat.setString(1, storageId);

            storage1 = stat.executeQuery();
            while (storage1.next()) {
                storageIdText.setText(storage1.getString("storage_id"));
                storageAddressText.setText(storage1.getString("storage_address"));
                storageNumberText.setText(storage1.getString("storage_telephone_number"));
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
                storageItems.add(new Item(storage1Items.getString("item_id"), Integer.parseInt(storage1Items.getString("storage_stock_quantity")), storage1Items.getString("item_description")));
            }

        }catch(SQLException e)
        {
            System.out.println(e);
        }finally {
            try {storage1Items.close();}catch (Exception e){}
            try { stat.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }


    public void fillComboBoxStorageItemId(String storageId, ObservableList storageItemIds){
        Connection con = null;
        PreparedStatement stat = null;
        ResultSet storageItems = null;
        try {
            con = DriverManager.getConnection(database.host, database.userName, database.password);
            stat  = con.prepareStatement("select `Storage Stock`.*, Items.item_description \n" +
                    "from `Storage Stock`\n" +
                    "inner join Items\n" +
                    "on `Storage Stock`.item_id = Items.item_id where storage_id = ?");

            stat.setString(1, storageId);

            storageItems = stat.executeQuery();
            while (storageItems.next()){
                Integer shopItemId = storageItems.getInt("item_id");
                storageItemIds.add(shopItemId);
            }

        }catch(SQLException e)
        {
            System.out.println(e);
        }finally {
            try {storageItems.close();}catch (Exception e){}
            try { stat.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public void fillComboBoxShopItemId(String shopId, ObservableList shopItemIds){
        Connection con = null;
        PreparedStatement stat = null;
        ResultSet shopItems = null;
        try {
            con = DriverManager.getConnection(database.host, database.userName, database.password);
            stat  = con.prepareStatement("select `Shop Stock`.*, Items.item_description \n" +
                    "from `Shop Stock`\n" +
                    "inner join Items\n" +
                    "on `Shop Stock`.item_id = Items.item_id where shop_id = ?");

            stat.setString(1, shopId);

            shopItems = stat.executeQuery();
            while (shopItems.next()){
                Integer shopItemId = shopItems.getInt("item_id");
                shopItemIds.add(shopItemId);
            }

        }catch(SQLException e)
        {
            System.out.println(e);
        }finally {
            try {shopItems.close();}catch (Exception e){}
            try { stat.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
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


    public void fillTableTransactionHistoryBIT() throws SQLException {
        ResultSet rsTransactionHistory = null;
        Connection con = null;
        PreparedStatement stat = null;
        long today = System.currentTimeMillis();
        java.sql.Date currentDay = new java.sql.Date(today);
        try {
            con = DriverManager.getConnection(database.host, database.userName, database.password);
            stat = con.prepareStatement("select tn.transaction_id, tn.transaction_date, tn.item_id, tn.transaction_item_quantity, tn.`transaction_buy/sell`, tn.transaction_price, tn.transactor_id, tr.transactor_name, tr.transactor_address, tr.transactor_phone_number, tr.transactor_email, i.item_description\n" +
                    "from Transaction tn\n" +
                    "    inner join Transactor tr on tn.transactor_id = tr.transactor_id\n" +
                    "    inner join Items i on tn.item_id  = i.item_id\n" +
                    "    where tn.`transaction_buy/sell` = \"Buy\" and tn.transaction_date=? ");


            stat.setDate(1,currentDay);
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
    }

    public void fillTransactor() throws SQLException {
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement stat = null;
        try {
            con = DriverManager.getConnection(database.host, database.userName, database.password);
            stat = con.prepareStatement("select * from Transactor ");
            rs = stat.executeQuery();
            while (rs.next()){
                transactorsObservableListCB.add(new Transactor(rs.getInt("transactor_id"),rs.getString("transactor_name"),rs.getString("transactor_address"),rs.getString("transactor_phone_number")));
            }

        }catch(SQLException e)
        {
            System.out.println(e);
        }finally {
            rs.close();
            stat.close();
            con.close();

        }
    }

    public void fillItem() throws SQLException {
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement stat = null;
        try {
            con = DriverManager.getConnection(database.host, database.userName, database.password);
            stat = con.prepareStatement("select * from Items ");
            rs = stat.executeQuery();
            while (rs.next()){
                itemsObservableListMD.add(new Item(String.valueOf(rs.getInt("item_id")),rs.getInt("item_sell_price"),rs.getString("item_description")));

            }

        }catch(SQLException e)
        {
            System.out.println(e);
        }finally {
            rs.close();
            stat.close();
            con.close();

        }
    }

    public void fillTableTransactionHistoryCB() throws SQLException {
        ResultSet rsTransactionHistory = null;
        Connection con = null;
        PreparedStatement stat = null;
        try {
            con = DriverManager.getConnection(database.host, database.userName, database.password);
            stat = con.prepareStatement("select tn.transaction_id, tn.transaction_date, tn.item_id, tn.transaction_item_quantity, tn.`transaction_buy/sell`, tn.transaction_price, tn.transactor_id, tr.transactor_name, tr.transactor_address, tr.transactor_phone_number, tr.transactor_email, i.item_description\n" +
                    "from Transaction tn\n" +
                    "    inner join Transactor tr on tn.transactor_id = tr.transactor_id\n" +
                    "    inner join Items i on tn.item_id  = i.item_id\n");

            rsTransactionHistory = stat.executeQuery();
            while (rsTransactionHistory.next()){
                transactionHistoryObservableListCB.add(new TransactionHistory(rsTransactionHistory.getString("transaction_id"),rsTransactionHistory.getDate("transaction_date").toString(),rsTransactionHistory.getString("item_id")
                        ,rsTransactionHistory.getInt("transaction_item_quantity"),rsTransactionHistory.getString("item_description"),rsTransactionHistory.getString("transactor_name"),rsTransactionHistory.getString("transactor_address"),
                        rsTransactionHistory.getString("transactor_phone_number"),rsTransactionHistory.getInt("transaction_price"), rsTransactionHistory.getString("transaction_buy/sell"),rsTransactionHistory.getInt("transactor_id")));
            }

        }catch(SQLException e)
        {
            System.out.println(e);
        }finally {
            rsTransactionHistory.close();
            stat.close();
            con.close();

        }
    }
    //    Getting the Table from Sql and insert it into the Interface


//    Getting the Search data from the Sql and put it into the the Interface Table
    public void searchTransactorId(int transactorId) throws SQLException {
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement stat = null;
        try {
            con = DriverManager.getConnection(database.host, database.userName, database.password);
            stat = con.prepareStatement("select * from Transactor where transactor_id =?");
            stat.setInt(1,transactorId);
            rs = stat.executeQuery();
            while (rs.next()){
                transactorsObservableListCB.add(new Transactor(rs.getInt("transactor_id"),rs.getString("transactor_name"),rs.getString("transactor_address"),rs.getString("transactor_phone_number")));
            }

        }catch(SQLException e)
        {
            System.out.println(e);
        }finally {
            rs.close();
            stat.close();
            con.close();

        }
    }


    public void searchItem(int itemId) throws SQLException {
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement stat = null;
        try {
            con = DriverManager.getConnection(database.host, database.userName, database.password);
            stat = con.prepareStatement("select * from Items where item_id =?");
            stat.setInt(1,itemId);
            rs = stat.executeQuery();
            while (rs.next()){
                itemsObservableListMD.add(new Item(String.valueOf(rs.getInt("item_id")),rs.getInt("item_sell_price"),rs.getString("item_description")));
            }

        }catch(SQLException e)
        {
            System.out.println(e);
        }finally {
            rs.close();
            stat.close();
            con.close();

        }
    }

    public void searchTransactionId(int transactionId) throws SQLException {
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement stat = null;
        try {
            con = DriverManager.getConnection(database.host, database.userName, database.password);

            stat = con.prepareStatement("select tn.transaction_id, tn.transaction_date, tn.item_id, tn.transaction_item_quantity, tn.`transaction_buy/sell`, tn.transaction_price, tn.transactor_id, tr.transactor_name, tr.transactor_address, tr.transactor_phone_number, tr.transactor_email, i.item_description\n" +
                                        "from Transaction tn\n" +
                                        "inner join Transactor tr on tn.transactor_id = tr.transactor_id\n" +
                                        "inner join Items i on tn.item_id  = i.item_id\n" +
                                        "where tn.transaction_id= ?");

            stat.setInt(1,transactionId);
            rs = stat.executeQuery();
            while (rs.next()){
                transactionHistoryObservableListCB.add(new TransactionHistory(rs.getString("transaction_id"),rs.getDate("transaction_date").toString(),rs.getString("item_id")
                        ,rs.getInt("transaction_item_quantity"),rs.getString("item_description"),rs.getString("transactor_name"),rs.getString("transactor_address"),
                        rs.getString("transactor_phone_number"),rs.getInt("transaction_price"), rs.getString("transaction_buy/sell"),rs.getInt("transactor_id")));
            }

        }catch(SQLException e)
        {
            System.out.println(e);
        }finally {
            rs.close();
            stat.close();
            con.close();

        }
    }
//    Getting the Search data from the Sql and put it into the the Interface Table


//    By taking a paramerter , Update the corresponding data on mySql Table and update the Table from the Interface
    public void updateItemSql(int itemId,String itemName , Integer itemSellPrice) throws SQLException {
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement stat = null;

        try {
            con = DriverManager.getConnection(database.host, database.userName, database.password);
            stat = con.prepareStatement("update Items set item_description = ? , item_sell_price=? where item_id =?");
            stat.setString(1,itemName);
            stat.setInt(2,itemSellPrice);
            stat.setInt(3,itemId);
            stat.execute();

        }catch(SQLException e)
        {
            System.out.println(e);
        }finally {
            stat.close();
            con.close();

        }
    }

    public void updateTransactorSql(int transactorId,String name,String address,String phoneNumber) throws SQLException {
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement stat = null;
        try {
            con = DriverManager.getConnection(database.host, database.userName, database.password);
            stat = con.prepareStatement("update Transactor set transactor_name = ? , transactor_address=?, transactor_phone_number=? where transactor_id =?");
            stat.setString(1,name);
            stat.setString(2,address);
            stat.setString(3,phoneNumber);
            stat.setInt(4,transactorId);
            stat.execute();

        }catch(SQLException e)
        {
            System.out.println(e);
        }finally {
            stat.close();
            con.close();

        }
    }
//    By taking a paramerter , Update the corresponding data on mySql Table and update the Table from the Interface

//    Database Queries Function

//    Utility Function
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



