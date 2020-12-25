package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Controller {
    @FXML
    public BorderPane mainPane;
    public void transactionWindowButton(ActionEvent event) throws IOException {
        System.out.println("transaction window");
        Pane view = FXMLLoader.load(getClass().getResource("Transaction.fxml"));
        mainPane.setCenter(view);
    }
    public void sortingCenterWindowButton(ActionEvent event) throws IOException {
        System.out.println("clicking sorting center button");
        Pane view = FXMLLoader.load(getClass().getResource("SortingCenter.fxml"));
        mainPane.setCenter(view);
    }
}
