package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Item {
    private SimpleStringProperty itemId;
    private SimpleIntegerProperty itemQty;

    public Item(String itemId, Integer itemQty) {
        this.itemId = new SimpleStringProperty(itemId);
        this.itemQty = new SimpleIntegerProperty(itemQty);

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

}
