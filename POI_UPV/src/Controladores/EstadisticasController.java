/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;

/**
 * FXML Controller class
 *
 * @author pablo
 */
public class EstadisticasController implements Initializable {

    private Stage primaryStage;
    private User usuario;
    
    @FXML
    private Menu id_menuPerfil;
    @FXML
    private ImageView id_avatar;
    @FXML
    private TableColumn<?, ?> userColumn;
    @FXML
    private TableColumn<?, ?> mailColumn;
    @FXML
    private TableColumn<?, ?> ratioColumn;
    @FXML
    private TableColumn<?, ?> dateColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void editProfile(ActionEvent event) {
        try {
            Stage actualStage = new Stage();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/EditProfile.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            actualStage.setTitle("Editar Perfil");
            actualStage.setScene(scene);
            actualStage.setResizable(false);
            actualStage.initModality(Modality.APPLICATION_MODAL);
            
            actualStage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(ChooseProblemTypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void logOut(ActionEvent event) {
        LogInSignUpController.setUser(null);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/ChooseProblemType.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            primaryStage.setTitle("Problemas");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            
            ChooseProblemTypeController ctr = loader.getController();
            ctr.initStage(primaryStage, usuario);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(ProblemsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void initStage(Stage stage, User user) {
        primaryStage = stage;
        usuario = user;
        id_avatar.setImage(usuario.getAvatar());
        id_menuPerfil.setText(usuario.getNickName());
    }
    
}
