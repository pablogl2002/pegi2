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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.User;
import static model.User.checkEmail;
import static model.User.checkPassword;

/**
 * FXML Controller class
 *
 * @author pablo
 */
public class EditProfileController implements Initializable {
    
    @FXML
    private Button bCancel;
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
    @FXML
    private TextField oldMailField;
    @FXML
    private TextField newMailField;
    @FXML
    private DatePicker oldBirth_picker;
    @FXML
    private DatePicker newBirth_picker;
    @FXML
    private Button aapplyButton;
    @FXML
    private Text DatosUsuario;
    
    private String oldEmail;
    private String oldPass;
    private LocalDate oldDate;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuario = LogInSignUpController.getUser();
        avatar = usuario.getAvatar();
        id_avatar.setImage(avatar);
        oldMailField.setText(usuario.getEmail());
        DatosUsuario.setText("Email:\n" + usuario.getEmail() + "\nContrase??a: " + usuario.getPassword() + "\nCumplea??os: " + usuario.getBirthdate());
        
        oldEmail = usuario.getEmail();
        oldPass = usuario.getPassword();
        
        oldPassField.textProperty().addListener((ob, oldV, newV) -> {
            //if ( oldV != null && oldV.equals(newV)) { passProp = true; System.out.println("ta chida"); }
        });
        
    }    

    private boolean comprobarCumplea??os (){
        LocalDate hoy = LocalDate.now();
        LocalDate nacimiento = newBirth_picker.getValue();
        long edad = ChronoUnit.YEARS.between(nacimiento, hoy);
        return (edad >= 12);
    }
    
    
    @FXML
    private void applyButton(ActionEvent event) throws NavegacionDAOException {
        if (newMailField.getText() != null && checkEmail(newMailField.getText())) {
            usuario.setEmail(newMailField.getText());
            System.out.println("Cambiado correo");
        }
        
        if (newPassField.getText().equals(reNewPassField.getText()) && checkPassword(newPassField.getText())) {
            usuario.setPassword(newPassField.getText());
            System.out.println("Cambiado contrase??a");
        }
        
        if (!avatar.equals(usuario.getAvatar())) {
            usuario.setAvatar(avatar);
            System.out.println("Cambiado avatar");
        }
        
        if (!oldBirth_picker.valueProperty().getValue().equals(newBirth_picker.valueProperty().getValue()) && comprobarCumplea??os()
                && oldBirth_picker.valueProperty().getValue().equals(usuario.getBirthdate())) {
            usuario.setBirthdate(newBirth_picker.valueProperty().getValue());
            System.out.println("Cambiado cumplea??os");
        }
        
        Stage ventana = (Stage) aapplyButton.getScene().getWindow();
        ventana.close();
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
