package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;


import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAdmin implements Initializable {
    @FXML
    public Button goStorageButtonST,goShopButtonST,moveButtonST,goShopButtonTT, addButtonTT, saveButtonTT, goShopButtonBIT, addButtonBIT, saveButtonBIT;
    @FXML
    public TableView storageTableViewST, shopTableViewST,shopStockTableViewTT, currentTransactionTableViewTT, transactionHistoryTableViewTT, storageTableViewBIT, currentTransactionTableViewBIT, transactionHistoryTableViewBIT;
    @FXML
    public TextField itemIdTextFieldST, itemQuantityTextFieldST,customerNameTextField, customerAddressTextField, customerNumberTextField,
            enterItemIdTextField, enterItemQtyTextField, supplierNameTextFieldBIT, supplierAddressTextFieldBIT, supplierPhoneTextFieldBIT,
            itemIdTextFieldBIT, itemQuantityTextFieldBIT, sellPriceTextFieldBIT, buyPriceTextFieldBIT,itemNameTextFieldBIT;
    @FXML
    public ComboBox storageComboBoxST, shopComboBoxST, fromComboBox, toComboBox, shopComboBoxTT, storageComboBoxBIT ;

    @FXML
    public Text storageIdTextST, storageAddressTextST, storageNumberTextST, shopIdTextST, shopAddressTextST, shopNumberTextST,
            customerNameLabelTT,customerAddressLabelTT,customerPhoneLabelTT,supplierNameLabelBIT,supplierAddressLabelBIT,supplierPhoneLabelBIT;


    ObservableList<String> storageLists = FXCollections.observableArrayList(
            "Storage 1", "Storage 2"
    );
    ObservableList<String> shopLists = FXCollections.observableArrayList(
            "Shop 1", "Shop 2"
    );
    ObservableList<String> shopStorageLists = FXCollections.observableArrayList(
            "Shop 1", "Shop 2", "Storage 1", "Storage 2"
    );



    public TableColumn<CurrentTransactionTableTT,String>transactionIdColumnTT;
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


//static int for transactionTable
    static int transactionId;
    static int transactorId;





    @Override
    public void initialize(URL location, ResourceBundle resources) {

        storageComboBoxST.setItems(storageLists);
        shopComboBoxST.setItems(shopLists);
        storageComboBoxBIT.setItems(storageLists);
        fromComboBox.setItems(shopStorageLists);



        //Storage Tab
        TableColumn itemIdStorage = new TableColumn("Item ID");
        itemIdStorage.setMinWidth(100);
        itemIdStorage.setCellValueFactory(
                //sets from what class to take the data from and what is the type of data
                new PropertyValueFactory<Item, String>("itemId"));

        TableColumn itemQtyStorage = new TableColumn("Item ID");
        itemQtyStorage.setMinWidth(100);
        itemQtyStorage.setCellValueFactory(
                //sets from what class to take the data from and what is the type of data
                new PropertyValueFactory<Item, Integer>("itemQty"));

        storageTableViewST.getItems().addAll(itemIdStorage, itemQtyStorage);

        TableColumn itemIdShop = new TableColumn("Item ID");
        itemIdShop.setMinWidth(100);
        itemIdShop.setCellValueFactory(
                //sets from what class to take the data from and what is the type of data
                new PropertyValueFactory<Item, String>("itemId"));

        TableColumn itemQtySshop = new TableColumn("Item ID");
        itemQtySshop.setMinWidth(100);
        itemQtySshop.setCellValueFactory(
                //sets from what class to take the data from and what is the type of data
                new PropertyValueFactory<Item, Integer>("itemQty"));

        storageTableViewST.getItems().addAll(itemIdShop, itemQtySshop);







//        setting the observable list for the current transaction Table TT;
        transactionIdColumnTT.setCellValueFactory(new PropertyValueFactory<CurrentTransactionTableTT,String>("itemId"));
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
    public void fromComboBoxPicked(){
        if(shopLists.contains(fromComboBox.getValue()))
        {
            toComboBox.setItems(storageLists);
        }
        else
        {
            toComboBox.setItems(shopStorageLists);
        }
    }
    public void goStorageButtonSTClicked()
    {
//        storageComboBoxBIT.getValue()
        storageIdTextST.setText("S-001");
        storageAddressTextST.setText("jl.001");
        storageNumberTextST.setText("0811212122");
    }
    public void goShopButtonSTClicked()
    {
//        shopComboBoxST.getValue()
        shopIdTextST.setText("S-001");
        shopAddressTextST.setText("jl.001");
        shopNumberTextST.setText("0811212122");
    }

    public void MoveButtonSTClicked()
    {
        String inputId = itemIdTextFieldST.getText();
        String inputQty = itemQuantityTextFieldST.getText();
    }



    public void addButtonClickedTT(javafx.event.ActionEvent event) {
        String customerName = customerNameTextField.getText();
        String customerAddress = customerAddressTextField.getText();
        String customerPhoneNumber = customerNumberTextField.getText();
        String itemId = enterItemIdTextField.getText();
        int itemQty = Integer.parseInt(enterItemQtyTextField.getText());
        int itemPrice = 800;
        int itemTotal = itemPrice*itemQty;

        customerNameLabelTT.setText(customerName);
        customerAddressLabelTT.setText(customerAddress);
        customerPhoneLabelTT.setText(String.valueOf(customerPhoneNumber));

        currentTransactionTableDataTT.add(new CurrentTransactionTableTT(itemId,itemQty,itemPrice,itemTotal));

    }

    public void addButtonClickedBIT(javafx.event.ActionEvent event){
        String supplierName = supplierNameTextFieldBIT.getText();
        String supplierAddress = supplierAddressTextFieldBIT.getText();
        String supplierPhoneNumber = supplierPhoneTextFieldBIT.getText();
        String itemId = itemIdTextFieldBIT.getText();
        String itemName = itemNameTextFieldBIT.getText();
        int itemQty = Integer.parseInt(itemIdTextFieldBIT.getText());
        int itemSellPrice = Integer.parseInt(sellPriceTextFieldBIT.getText());
        int itemBuyPrice = Integer.parseInt(buyPriceTextFieldBIT.getText());
        int itemTotal = itemBuyPrice*itemQty;

        supplierNameLabelBIT.setText(supplierName);
        supplierAddressLabelBIT.setText(supplierAddress);
        supplierPhoneLabelBIT.setText(supplierPhoneNumber);

        currentTransactionTableDataBIT.add(new CurrentTransactionTableBIT(itemId,itemName,itemQty,itemBuyPrice,itemSellPrice,itemTotal));


    }

}