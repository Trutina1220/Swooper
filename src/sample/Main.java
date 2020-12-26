package sample;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    Stage window;
    String userName = "Kevin";
    String password = "1234";

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        window.setTitle("Swooper");

        window.setOnCloseRequest(e ->
        {
            e.consume();
            closeProgram();
        });

        //Declare a new gridpane for the login window
        GridPane gridPane =  new GridPane();
        //Set padding of the gridpane
        gridPane.setPadding(new Insets(10,10,10,10));
        //Set the vertical gap to 8 pixels for each element inside it
        gridPane.setVgap(8);
        //Set the horizontal gap to 10 pixels for each element in side it
        gridPane.setHgap(10);

        //Subtitle  Welcome Label
        Label welcomeLabel = new Label("Welcome Boss, Please Login !");
        GridPane.setConstraints(welcomeLabel,0,0);

        //Declare new USERNAME Label also set the position.
        Label userNameLabel =  new Label("Username : ");
        GridPane.setConstraints(userNameLabel,1,1);

        //Declare new USERNAME TextField to Input name of the admin alse set the position and the prompt text
        TextField userNameInput =  new TextField("");
        userNameInput.setPromptText("Your Username");
        GridPane.setConstraints(userNameInput, 2,1);

        //Declare password label also set the position
        Label passLabel = new Label("Password : ");
        GridPane.setConstraints(passLabel,1,2);

        //Create a final variable passInput that was created from Password Field Class.
        //Aet the prompt text and also the position
        final PasswordField passInput = new PasswordField();
        passInput.setPromptText("Your Password");
        GridPane.setConstraints(passInput,2,2);

        //Create a login Button to log in also set the position.
        Button logInButton =  new Button("Log In");
        GridPane.setConstraints(logInButton, 2,3);

        //Add a method to get all the elements and put it inside the gridpane
        gridPane.getChildren().addAll(userNameLabel, userNameInput, passLabel, passInput, logInButton, welcomeLabel);
        //Set the Alignment position to center
        gridPane.setAlignment(Pos.CENTER);


        //Declare a new logInScene and set the width along with the height
        Scene logInScene =  new Scene(gridPane,600,300);
        //Get the StyleSheet for the Login Scene from a css file named Color.css
        //Set the Window or the Primary Stage to the Login Scene first
        window.setScene(logInScene);
        //Show the window
        //This will display the login Scene
        window.show();

        //Again in this part i use lambda expression
        //The Purpose is that every time there is an action in the loginbutton it will run the block of code inside the lambda expression
        logInButton.setOnAction(event ->
        {
            //This conditional Statement here is to validate whether those textfield (username and pass) are empty.
            //If its true (empty), the program will show an error message to tell the user that they must fill in order to access the inventory
            if (userNameInput.getText().isEmpty() || passInput.getText().isEmpty())
            {
                //Declare alert box to tell the user that all field must be filled
                Alert a =  new Alert(Alert.AlertType.WARNING);
                //Set the title of the alert box
                a.setTitle("Warning !");
                //Set the Message inside
                a.setContentText("All Field must be Filled !");
                // Get the Stage.
                Stage stage = (Stage) a.getDialogPane().getScene().getWindow();
                // Add a custom icon.
                stage.getIcons().add(new Image(this.getClass().getResource("icon.png").toString()));
                //Show the alert box
                a.show();
            }
            //This conditional statement here is to validate whether The username Input and the password field is correct or not
            //If not, the program will show an alert box saying that the username and password are not valid.
            //If yes, the program will continue tot he else block below.
            else if(!userNameInput.getText().isEmpty() || !passInput.getText().isEmpty()) {
                //Conditional statement to validate the input is correct or not
                if (!userNameInput.getText().equals(userName) || !passInput.getText().equals(password)) {
                    //Declare an alert box to inform the user that they input the wrong username and password
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    //Set the title of the alert box
                    a.setTitle("Warning !");
                    //Set the message of the alert
                    a.setContentText("Username and Password are invalid!");
                    // Get the Stage.
                    Stage stage = (Stage) a.getDialogPane().getScene().getWindow();
                    // Add a custom icon.
                    stage.getIcons().add(new Image(this.getClass().getResource("icon.png").toString()));
                    //Set the username Input and Password Input to empty, if the inputs are not correct.
                    userNameInput.clear();
                    passInput.clear();
                    //Show the Alert Message
                    a.show();
                }
                //Conditonal Statement to load the Inventory program-+
                else
                {
                    //Use try catch (Exception handling) to check are there any exception while loading the fxml file
                    Parent root = null;
                    try {
                        //Declare root from Parent Class (Node) and load the fxml as root.
                        root = FXMLLoader.load(getClass().getResource("sample.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //Set the title of the window as mentioned below.
                    window.setTitle("Swooper");


                    //In this part i assign a function if the window is closed by the user using the x red button that all app has on the top left.
                    //I use lambda expression so it means that every event which refers to the x red button is clicked will apply the method inside the lambda
                    window.setOnCloseRequest(event1 ->
                    {
                        //These function are the same as i mentioned above, the difference is this is for the Fxml
                        event1.consume();
                        closeProgram();
                    });
                    //Declare A new Scene and set the width along with height for the KevAndNic_Gui_App.fxml
                    Scene inventoryScene = new Scene(root, 1800, 1200);
                    //Set the Scene to the KevAndNic_Gui_App.fxml
                    window.setScene(inventoryScene);
                    //Show the Inventory (KevAndNic_Gui_App.fxml) GUI.
                    window.show();

                }

            }

        });
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void closeProgram() {
        Boolean answer = ConfirmBox.display("WARNING !", "Are you Sure to Close Swooper?");
        if (answer) {
            window.close();
        }
    }
}

