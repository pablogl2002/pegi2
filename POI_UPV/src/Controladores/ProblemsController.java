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
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
    
    private Stage primaryStage;
    private User usuario;
    @FXML
    private Accordion acordeonProblemas;
    private TitledPane[] tPane;
    private Navegacion datos;
    private List<Problem> problemas;
    @FXML
    private Menu id_menuPerfil;
    @FXML
    private ImageView id_avatar;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            // TODO
            datos = Navegacion.getSingletonNavegacion();
            problemas = datos.getProblems();
            tPane = new TitledPane[problemas.size()];
            
            for (int i = 0; i < problemas.size(); i++) {
                tPane[i] = new TitledPane();
                tPane[i].setText("Problema " + (i + 1));
                                
                VBox qCont = new VBox();
                HBox bCont = new HBox();
                Button bRealizar = new Button("Realizar Ejercicio");                
                
                Text pregunta = new Text(problemas.get(i).getText());
                
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

    private void goToProblem(int i) {
        System.out.println(i);
    }
    
    @FXML
    private void goToBack(ActionEvent event) {
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
    private void goToRandom(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/PruebaProblemasMapa.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            primaryStage.setTitle("Problemas Aleatorios");
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            
            PruebaProblemasMapaController rPro = loader.getController();
            rPro.initStage(primaryStage, usuario, 1);
        } catch (IOException ex) {
            Logger.getLogger(ProblemsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
