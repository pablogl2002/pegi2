/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import DBAccess.NavegacionDAOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.User;

/**
 * FXML Controller class
 *
 * @author pablo
 */
public class EditProfileController implements Initializable {
    
    @FXML
    private Button bCancel;
    @FXML
    private Label label_wUserSign11;
    @FXML
    private Label label_wUserSign1;
    @FXML
    private Label label_wPassSign1;
    @FXML
    private Label label_wPassSign11;
    @FXML
    private Label label_wRePassSign1;
    @FXML
    private PasswordField oldPassField;
    @FXML
    private PasswordField reNewPassField;   
    @FXML
    private ImageView id_avatar;
    @FXML
    private ImageView id_avatarEdit;
    
    private Image avatar;
    private Stage stage;
    private User usuario;
    @FXML
    private PasswordField newPassField;
    private Boolean passProp = false;
    @FXML
    private TextField oldMailField;
    @FXML
    private TextField newMailField;
    @FXML
    private Label label_wBirthday;
    @FXML
    private DatePicker oldBirth_picker;
    @FXML
    private Label label_wBirthday1;
    @FXML
    private DatePicker newBirth_picker;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuario = LogInSignUpController.getUser();
        avatar = usuario.getAvatar();
        id_avatar.setImage(avatar);
        oldMailField.setText(usuario.getEmail());
        
        
        
        oldPassField.textProperty().addListener((ob, oldV, newV) -> {
            //if ( oldV != null && oldV.equals(newV)) { passProp = true; System.out.println("ta chida"); }
            
        });
        
    }    

    @FXML
    private void applyButton(ActionEvent event) throws NavegacionDAOException {
        if (!newMailField.getText().equals(usuario.getEmail()) && oldMailField.getText().equals(usuario.getEmail()) && newMailField.getText() != null) {
            usuario.setEmail(newMailField.getText());      
            System.out.println("Cambiado mail");
        }
        
        if (oldPassField.getText().equals(usuario.getPassword()) && !newPassField.getText().equals(usuario.getPassword()) && newPassField.getText().equals(reNewPassField)) {
            usuario.setPassword(newPassField.getText());
            System.out.println("Cambiada contraseña");
        }
        
        if (!avatar.equals(usuario.getAvatar())) {
            usuario.setAvatar(avatar);
            System.out.println("Cambiado avatar");
        }
    }

    @FXML
    private void handleBCancelOnAction(ActionEvent event) {
        bCancel.getScene().getWindow().hide();
    }

    @FXML
    private void changeAvatar(MouseEvent event) throws FileNotFoundException {
        FileChooser fc = new FileChooser();
        
        //fc.setInitialDirectory(new File("\\resources\\avatars\\"));
        fc.setTitle("Select an avatar");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image", "*.png", "*.jpg", "*.jpeg"));
        
        File selectFile = fc.showOpenDialog(this.stage);
        FileInputStream in = new FileInputStream(selectFile);
        
        if (selectFile != null) {
            avatar = new Image(in);
            id_avatar.setImage(avatar);
        }    
    }

    /*
    public void initStage(Stage stage) {
        this.stage = stage;
    }
    */
}
