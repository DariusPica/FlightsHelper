package Controller;

import Domain.User;
import Service.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

     Service srv;


     @FXML
     Button loginButton;

     @FXML
     TextField usernameText;

    @FXML
    PasswordField passwordText;


    public void setService(Service s)
    {
        this.srv=s;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        loginButton.setOnMouseClicked(x->{

            String usernameString=usernameText.getText();
            String passwordString=passwordText.getText();


            User user= srv.getLoginRequest(usernameString,passwordString);

            if(user!=null) {
                Stage stage = new Stage();

                FXMLLoader loader = null;
                loader = new FXMLLoader(getClass().getClassLoader().getResource("user.fxml"));


                UserController controller = new UserController();
                controller.setService(srv);
                controller.setCurrentStage(stage);
                loader.setController(controller);

                try {
                    stage.setScene(new Scene(loader.load()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                stage.show();
            }

            else
            {
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Utilizator necunoscut");
                alert.setContentText("Acest utilizator nu exista in baza de date");
                alert.showAndWait();
            }

        });
    }
}
