package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CurrentTransactionTableBIT {


    private SimpleStringProperty itemId;
    private SimpleStringProperty itemName;
    private SimpleIntegerProperty itemQty;
    private SimpleIntegerProperty itemBuyPrice;
    private SimpleIntegerProperty itemSellPrice;
    private SimpleIntegerProperty total;

    public CurrentTransactionTableBIT(String itemId, String itemName, Integer itemQty, Integer itemBuyPrice, Integer itemSellPrice, Integer total) {
        this.itemId = new SimpleStringProperty(itemId);
        this.itemName = new SimpleStringProperty(itemName);
        this.itemQty = new SimpleIntegerProperty(itemQty);
        this.itemBuyPrice = new SimpleIntegerProperty(itemBuyPrice);
        this.itemSellPrice = new SimpleIntegerProperty(itemSellPrice);
        this.total = new SimpleIntegerProperty(total);
    }

    public String getItemId() {
        return itemId.get();
    }

    public SimpleStringProperty itemIdProperty() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId.set(itemId);
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

    public int getItemQty() {
        return itemQty.get();
    }

    public SimpleIntegerProperty itemQtyProperty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty.set(itemQty);
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
