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
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.User;

/**
 * FXML Controller class
 *
 * @author pablo
 */
public class ChooseProblemTypeController implements Initializable {

    @FXML
    private ImageView id_avatar;
    @FXML
    private TextArea infoArea;
    
    private String window = "";
    private Stage primaryStage;
    private int auxNum = -1;
    @FXML
    private Menu id_menuPerfil;
    private User usuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id_menuPerfil.setText("Perfil");
    }    

    public void initStage(Stage stage, User user) {
        primaryStage = stage;
        usuario = user;
        id_avatar.setImage(usuario.getAvatar());
        id_menuPerfil.setText(usuario.getNickName());
    }
    
    @FXML
    private void editProfile(ActionEvent event) {
        
    }

    @FXML
    private void logOut(ActionEvent event) {
        
    }

    @FXML
    private void pressSalir(ActionEvent event) {
        
    }
    
    @FXML
    private void pressAceptar(ActionEvent event) {
        try {
            if (auxNum == 0) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Problems.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                primaryStage.setTitle("Problemas");
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);

                ProblemsController pro = loader.getController();
                pro.initStage(primaryStage);
            }
            if (auxNum == 1) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/RandomProblems.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                primaryStage.setTitle("Problemas Aleatorios");
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);

                RandomProblemsController rPro = loader.getController();
                rPro.initStage(primaryStage);
            }
             primaryStage.show();
        } catch (IOException ex) {
                Logger.getLogger(ChooseProblemTypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void verProblemas(ActionEvent event) {
        infoArea.setText("Ver todos los problemas.");
        auxNum = 0;
    }

    @FXML
    private void randomProblems(ActionEvent event) {
        infoArea.setText("Realizar problemas de forma aleatoria.");
        auxNum = 1;
    }
}
