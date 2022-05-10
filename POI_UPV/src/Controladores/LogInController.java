/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import DBAccess.NavegacionDAOException;
import com.sun.javafx.scene.control.skin.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Navegacion;
import static model.Navegacion.getSingletonNavegacion;

/**
 * FXML Controller class
 *
 * @author pablo
 */
public class LogInController implements Initializable {

    private Stage primaryStage;
    private BooleanProperty validNick;
    
    @FXML
    private Button bAccept;
    @FXML
    private Button bCancel;
    private TextField emailField;
    @FXML
    private Label lIncorrectEmail;
    @FXML
    private PasswordField passField;
    @FXML
    private Label lWrongPass;
    @FXML
    private Hyperlink register_link;
    @FXML
    private TextField nickField;

    Navegacion aux; 
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            aux = getSingletonNavegacion();
            validNick = new SimpleBooleanProperty();
            
            validNick.setValue(Boolean.FALSE);
            
            nickField.focusedProperty().addListener((obsevable, oldValue, newValue) -> {
                if (!newValue) {
                    aux.exitsNickName(nickField.textProperty().getValueSafe());
                }
            });         
            
            bAccept.disableProperty().bind(validNick);
            
        } catch (NavegacionDAOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    

    @FXML
    private void handleBAcceptOnAction(ActionEvent event) {
        emailField.textProperty().setValue("");
        passField.textProperty().setValue("");
        validNick.setValue(Boolean.FALSE);
        
        if (aux.loginUser(nickField.textProperty().getValueSafe(), passField.textProperty().getValueSafe()) == null) {
                Alert mensaje= new Alert(Alert.AlertType.INFORMATION);
                mensaje.setTitle("Error");
                mensaje.setHeaderText("Contraseña incorrecta");
                mensaje.showAndWait();
            }
    }

    @FXML
    private void handleBCancelOnAction(ActionEvent event) {
        bCancel.getScene().getWindow().hide();
    }

    @FXML
    private void registerAction(ActionEvent event){
        try {
            FXMLLoader signUp = new FXMLLoader(getClass().getResource("/Vistas/SignUp.fxml"));
            Parent root = signUp.load();
            SignUpController ventanaRegistro = signUp.getController();
            Scene scene = new Scene(root, 600, 400);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
