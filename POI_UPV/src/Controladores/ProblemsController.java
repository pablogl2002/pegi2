/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import DBAccess.NavegacionDAOException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Navegacion;
import model.Problem;
import model.User;

/**
 * FXML Controller class
 *
 * @author pablo
 */
public class ProblemsController implements Initializable {

    @FXML
    private Menu id_menuPerfil;
    @FXML
    private ImageView id_avatar;
    @FXML
    private Accordion acordeonProblemas;
    
    private Stage primaryStage;
    private User usuario;
    private TitledPane[] tPane;
    private Navegacion datos;
    private List<Problem> problemas;
    static int i;

    public void initStage(Stage stage, User user) {
        primaryStage = stage;
        usuario = user;
        id_avatar.setImage(usuario.getAvatar());
        id_menuPerfil.setText(usuario.getNickName());
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            datos = Navegacion.getSingletonNavegacion();
            problemas = datos.getProblems();
            tPane = new TitledPane[problemas.size()];
            
            for (i = 0; i < problemas.size(); i++) {
                tPane[i] = new TitledPane();
                tPane[i].setText("Problema " + (i + 1));
                VBox qCont = new VBox();
                HBox bCont = new HBox();
                Button bRealizar = new Button("Realizar Ejercicio");
                
                Problem aux = problemas.get(i);
                Text pregunta = new Text(aux.getText());
                
                bRealizar.setOnAction(a -> {
                    goToMap("Problemas Ordenados");
                    
                });
                
                bCont.getChildren().add(bRealizar);
                
                bCont.alignmentProperty().set(Pos.CENTER_RIGHT);
                
                qCont.getChildren().add(pregunta);
                qCont.getChildren().add(bCont);
                
                pregunta.wrappingWidthProperty().set(560);
                
                qCont.alignmentProperty().set(Pos.CENTER);
                tPane[i].setContent(qCont);
            }
            
            acordeonProblemas.getPanes().addAll(tPane);
            acordeonProblemas.setExpandedPane(tPane[0]);
        } catch (NavegacionDAOException ex) {
            Logger.getLogger(ProblemsController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    @FXML
    private void goToBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/ChooseProblemType.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            primaryStage.setTitle("Problemas Aleatorios");
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            
            ChooseProblemTypeController rPro = loader.getController();
            rPro.initStage(primaryStage, usuario);
        } catch (IOException ex) {
            Logger.getLogger(ProblemsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goToRandom(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/PruebaProblemasMapa.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            PruebaProblemasMapaController rPro = loader.getController();
            rPro.initStage(primaryStage, usuario, -1000);
            primaryStage.setTitle("Problemas Aleatorios");
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            
        } catch (IOException ex) {
            Logger.getLogger(ProblemsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void goToMap(String title) {
        int index = -1;
        
        for (int i = 0; i < tPane.length; i++) {
            if (tPane[i].isExpanded()) {
                index = i;
                System.out.println(i);
            }
        }
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/PruebaProblemasMapa.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            PruebaProblemasMapaController rPro = loader.getController();
            rPro.initStage(primaryStage, usuario, index);
            primaryStage.setTitle(title);
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            
            
        } catch (IOException ex) {
            Logger.getLogger(ProblemsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
