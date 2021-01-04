package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Item {
    private SimpleStringProperty itemId;
    private SimpleIntegerProperty itemQty;
    private SimpleStringProperty itemDesc;

    public Item(String itemId, Integer itemQty) {
        this.itemId = new SimpleStringProperty(itemId);
        this.itemQty = new SimpleIntegerProperty(itemQty);

    }
    public Item(String itemId, Integer itemQty, String itemDesc) {
        this.itemId = new SimpleStringProperty(itemId);
        this.itemQty = new SimpleIntegerProperty(itemQty);
        this.itemDesc = new SimpleStringProperty(itemDesc);

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

    public int getItemQty() {
        return itemQty.get();
    }

    public SimpleIntegerProperty itemQtyProperty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty.set(itemQty);
    }

    public String getItemDesc() {
        return itemDesc.get();
    }

    public SimpleStringProperty itemDescProperty() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc.set(itemDesc);
    }
}
