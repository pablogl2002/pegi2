/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import DBAccess.NavegacionDAOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Navegacion;
import model.User;

/**
 * FXML Controller class
 *
 * @author pablo
 */
public class LogInSignUpController implements Initializable {  
    private Stage primaryStage;
    
    @FXML
    private TextField nickField;
    @FXML
    private PasswordField passFieldLog;
    @FXML
    private Button bCancel;
    @FXML
    private TextField emailField;
    @FXML
    private TextField userFieldSign;
    @FXML
    private PasswordField passFieldSign;
    @FXML
    private PasswordField rePassFieldSign;
    
    private Navegacion baseDatos;
    @FXML
    private Label label_wUser;
    @FXML
    private Label label_wPass;
    @FXML
    private DatePicker picker_birthday;
    @FXML
    private Label label_wEmail;
    @FXML
    private Label label_wUserSign;
    @FXML
    private Label label_wBirthday;
    @FXML
    private Label label_wPassSign;
    @FXML
    private Label label_wRePassSign;
    @FXML
    private Label label_anotherError;
    @FXML
    private ImageView id_avatar;
    private Image avatar;
    
    public void initStage(Stage stage) {
         primaryStage = stage;
    }
     
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.baseDatos = Navegacion.getSingletonNavegacion();
            
            /*
            String nickName = "a";
            String email = "email@domain.es";
            String password = "a";
            LocalDate birthdate = LocalDate.now().minusYears(18);
            User result = baseDatos.registerUser(nickName, email, password, birthdate);
            */
            
        } catch (NavegacionDAOException ex) {
            Logger.getLogger(LogInSignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    private void iniErrorsLabels() {
        //labels Log In 
        label_wUser.visibleProperty().set(false);
        label_wPass.visibleProperty().set(false);
        
        //labels Sign Up
        label_wUserSign.visibleProperty().set(false);
        label_wEmail.visibleProperty().set(false);
        label_wBirthday.visibleProperty().set(false);
        label_wPassSign.visibleProperty().set(false);
        label_wRePassSign.visibleProperty().set(false);
        label_anotherError.visibleProperty().set(false);
        
        
        label_anotherError.setText("");
    }
    
    private void initSignUp() {
        try {
            iniErrorsLabels();
            
            String nickName = userFieldSign.textProperty().getValueSafe();
            String email = emailField.textProperty().getValueSafe();
            String password = passFieldSign.textProperty().getValueSafe();
            String rePassword = rePassFieldSign.textProperty().getValueSafe();
            LocalDate birthdate = picker_birthday.getValue();
            
            if (!User.checkNickName(nickName)) {
                label_wUserSign.visibleProperty().set(true);
                label_anotherError.setText("Username must be between 6 and 15 characters with no spaces.");
                label_anotherError.visibleProperty().set(true);
            } else {
                if (baseDatos.exitsNickName(nickName)) {
                    label_wUserSign.visibleProperty().set(true);
                    label_anotherError.setText("That username is already in use.");
                    label_anotherError.visibleProperty().set(true);
                }
            }
            if (!User.checkEmail(email)) {
                label_wEmail.visibleProperty().set(true);
                label_anotherError.setText("Please bring a correct email account.");
                label_anotherError.visibleProperty().set(true);
            }
            if (!User.checkPassword(password)) { 
                label_wPassSign.visibleProperty().set(true);
                label_anotherError.setText("The password must have between 8 and 20 characters, at least an uppercase letter, a lowercase letter, a digit and a special character without spaces");
                label_anotherError.visibleProperty().set(true);
            }
            if (!password.equals(rePassword)) {
                label_wRePassSign.visibleProperty().set(true);
                label_wPassSign.visibleProperty().set(true);
                label_anotherError.setText("Passwords don't match");
                label_anotherError.visibleProperty().set(true);
            }
            
            if (avatar == null) {
                avatar = new Image("/resources/avatars/default.png");
            }
            
            if (User.checkNickName(nickName) && !baseDatos.exitsNickName(nickName) && User.checkEmail(email) && User.checkPassword(password) && password.equals(rePassword)) {
                User usuario = baseDatos.registerUser(nickName, email, password, birthdate);               
                goToProblems(usuario);
            }

        } catch (NavegacionDAOException ex) {
            Logger.getLogger(LogInSignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initLogIn() {  
        iniErrorsLabels();
        
        String nick = nickField.textProperty().getValueSafe();
        String pass = passFieldLog.textProperty().getValueSafe();
        if (!baseDatos.exitsNickName(nick)) {
            label_wUser.visibleProperty().set(true);
        } else {
            User usuario = baseDatos.loginUser(nick, pass);
            if (usuario == null) {
                label_wPass.visibleProperty().set(true);
            } else { goToProblems(usuario); }
        }
    }

    @FXML
    private void handleBCancelOnAction(ActionEvent event) {
        bCancel.getScene().getWindow().hide();
    }

    @FXML
    private void handleLogInOnAction(ActionEvent event) {
        initLogIn();
    }

    @FXML
    private void handleRegisterOnAction(ActionEvent event) {
        initSignUp();
    } 
    
    private void goToProblems(User usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/ChooseProblemType.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setTitle("Problemas");
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);

            ChooseProblemTypeController ctr = loader.getController();
            ctr.initStage(primaryStage, usuario);        
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LogInSignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void changeAvatar(MouseEvent event) throws FileNotFoundException {
        FileChooser fc = new FileChooser();
        
        //fc.setInitialDirectory(new File("\\resources\\avatars\\"));
        fc.setTitle("Select an avatar");
        fc.getExtensionFilters().addAll(new ExtensionFilter("Image", "*.png", "*.jpg", "*.jpeg"));
        
        File selectFile = fc.showOpenDialog(primaryStage);
        if (selectFile != null) {
            FileInputStream in = new FileInputStream(selectFile);
            avatar = new Image(in);
            id_avatar.setImage(avatar);
           
        }   
    }
    
    @FXML
    private void mouseOverAvatar(MouseDragEvent event) {
        
    }
}
