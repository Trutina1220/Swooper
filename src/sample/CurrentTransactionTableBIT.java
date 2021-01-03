package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CurrentTransactionTableBIT extends Item {
    private SimpleIntegerProperty itemBuyPrice;
    private SimpleStringProperty itemName;
    private SimpleIntegerProperty itemSellPrice;
    private SimpleIntegerProperty total;
    private SimpleIntegerProperty transactionId;


    public CurrentTransactionTableBIT(String itemId, String itemName, Integer itemQty, Integer itemBuyPrice, Integer itemSellPrice, Integer total, Integer transactionId) {
        super(itemId, itemQty);
        this.itemName = new SimpleStringProperty(itemName);
        this.itemBuyPrice = new SimpleIntegerProperty(itemBuyPrice);
        this.itemSellPrice = new SimpleIntegerProperty(itemSellPrice);
        this.total = new SimpleIntegerProperty(total);
        this.transactionId = new SimpleIntegerProperty(transactionId);
    }


    public int getTransactionId() {
        return transactionId.get();
    }

    public SimpleIntegerProperty transactionIdProperty() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId.set(transactionId);
    }

    public int getItemBuyPrice() {
        return itemBuyPrice.get();
    }

    public SimpleIntegerProperty itemBuyPriceProperty() {
        return itemBuyPrice;
    }

    public void setItemBuyPrice(int itemBuyPrice) {
        this.itemBuyPrice.set(itemBuyPrice);
    }

    public int getItemSellPrice() {
        return itemSellPrice.get();
    }

    public String getItemName() {
        return itemName.get();
    }

    public SimpleStringProperty itemNameProperty() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public SimpleIntegerProperty itemSellPriceProperty() {
        return itemSellPrice;
    }

    public void setItemSellPrice(int itemSellPrice) {
        this.itemSellPrice.set(itemSellPrice);
    }

    public int getTotal() {
        return total.get();
    }

    public SimpleIntegerProperty totalProperty() {
        return total;
    }

    public void setTotal(int total) {
        this.total.set(total);
    }
}
