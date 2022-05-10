/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author pablo
 */
public class SignUpController implements Initializable {

    @FXML
    private Button bAccept;
    @FXML
    private Button bCancel;
    @FXML
    private TextField emailField;
    @FXML
    private Label lIncorrectEmail;
    @FXML
    private TextField emailField1;
    @FXML
    private Label lIncorrectEmail1;
    @FXML
    private PasswordField passField;
    @FXML
    private Label lWrongPass;
    @FXML
    private PasswordField rePassField;
    @FXML
    private Label lPassDifferente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleBAcceptOnAction(ActionEvent event) {
    }

    @FXML
    private void handleBCancelOnAction(ActionEvent event) {
    }
    
}
