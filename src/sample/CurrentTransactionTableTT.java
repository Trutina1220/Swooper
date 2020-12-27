package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CurrentTransactionTableTT {
    private SimpleStringProperty itemId;

    public String getItemId() {
        return itemId.get();
    }

    public SimpleStringProperty itemIdProperty() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId.set(itemId);
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

    public int getItemPrice() {
        return itemPrice.get();
    }

    public SimpleIntegerProperty itemPriceProperty() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice.set(itemPrice);
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

    private SimpleIntegerProperty itemQty;
    private SimpleIntegerProperty itemPrice;
    private SimpleIntegerProperty total;

    public CurrentTransactionTableTT(String id, Integer itemQty, Integer itemPrice, Integer total){
        this.itemId = new SimpleStringProperty(id);
        this.itemQty = new SimpleIntegerProperty(itemQty);
        this.itemPrice = new SimpleIntegerProperty(itemPrice);
        this.total = new SimpleIntegerProperty(total);
    }

}
