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

public class Controller implements Initializable {
    @FXML
    public Button goStorageButtonST,goShopButtonST,moveButtonST,goShopButtonTT, addButtonTT, saveButtonTT, goShopButtonBIT, addButtonBIT, saveButtonBIT;
    @FXML
    public TableView storageTableViewST, shopTableViewST,shopStockTableViewTT, currentTransactionTableViewTT, transactionHistoryTableViewTT, storageTableViewBIT, currentTransactionTableViewBIT, transactionHistoryTableViewBIT;
    @FXML
    public TextField itemIdTextField, itemQuantityTextField,customerNameTextField, customerAddressTextField, customerNumberTextField,
            enterItemIdTextField, enterItemQtyTextField, supplierNameTextFieldBIT, supplierAddressTextFieldBIT, supplierPhoneTextFieldBIT,
            itemIdTextFieldBIT, itemQuantityTextFieldBIT, sellPriceTextFieldBIT, buyPriceTextFieldBIT,itemNameTextFieldBIT;
    @FXML
    public ComboBox storageComboBoxST, shopComboBoxST, fromComboBox, toComboBox, shopComboBoxTT, storageComboBoxBIT;

    @FXML
    public Text customerNameLabelTT,customerAddressLabelTT,customerPhoneLabelTT,supplierNameLabelBIT,supplierAddressLabelBIT,supplierPhoneLabelBIT;

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
