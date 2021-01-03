package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CurrentTransactionTableTT extends Item {
    private SimpleIntegerProperty itemPrice;
    private SimpleIntegerProperty total;
    private SimpleIntegerProperty transactionId;

    public CurrentTransactionTableTT(String id,String itemDesc, Integer itemQty, Integer itemPrice, Integer total,Integer transactionId){
        super(id, itemQty,itemDesc);
        this.itemPrice = new SimpleIntegerProperty(itemPrice);
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

    public int getItemPrice() {
        return itemPrice.get();
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

    public SimpleIntegerProperty itemPriceProperty() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice.set(itemPrice);
    }
}
