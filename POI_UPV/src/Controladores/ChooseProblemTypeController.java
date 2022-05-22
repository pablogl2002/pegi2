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
import javafx.stage.Modality;
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
    public User usuario;

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
        try {
            Stage actualStage = new Stage();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/EditProfile.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            actualStage.setTitle("Editar Perfil");
            actualStage.setScene(scene);
            actualStage.setResizable(false);
            actualStage.initModality(Modality.APPLICATION_MODAL);
            
            //EditProfileController ctr = loader.getController();
            //ctr.initStage(actualStage);
            actualStage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(ChooseProblemTypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void logOut(ActionEvent event) {
        LogInSignUpController.setUser(null);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/LogInSignUp.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            primaryStage.setTitle("Problemas");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            
            LogInSignUpController ctr = loader.getController();
            ctr.initStage(primaryStage);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(ProblemsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/*
    @FXML
    private void pressSalir(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/LogInSignUp.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setTitle("Iniciar Sesión o Registrar Cuenta");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            
            LogInSignUpController ctr = loader.getController();
            ctr.initStage(primaryStage);

            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(ChooseProblemTypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
*/    
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
                pro.initStage(primaryStage, usuario);
            }
            
            if (auxNum == 1) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/PruebaProblemasMapa.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                primaryStage.setTitle("Problemas Aleatorios");
                primaryStage.setScene(scene);
                primaryStage.setResizable(true);

                PruebaProblemasMapaController rPro = loader.getController();
                rPro.initStage(primaryStage, usuario, -1);
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

    @FXML
    private void verEvolucion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Estadisticas.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setTitle("Problemas Aleatorios");
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);

            EstadisticasController rPro = loader.getController();
            rPro.initStage(primaryStage, usuario);
        } catch (IOException ex) {
            Logger.getLogger(ChooseProblemTypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
