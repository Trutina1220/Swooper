package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Transactor {
    public SimpleIntegerProperty transactorId;
    public SimpleStringProperty name;
    public SimpleStringProperty address;
    public SimpleStringProperty phoneNumber;

    public Transactor (int transactorId, String name , String address, String phoneNumber){
        this.transactorId = new SimpleIntegerProperty(transactorId);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.phoneNumber =  new SimpleStringProperty(phoneNumber);
    }

    public int getTransactorId() {
        return transactorId.get();
    }

    public SimpleIntegerProperty transactorIdProperty() {
        return transactorId;
    }

    public void setTransactorId(int transactorId) {
        this.transactorId.set(transactorId);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }
}
