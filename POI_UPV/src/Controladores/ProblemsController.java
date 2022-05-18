/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import DBAccess.NavegacionDAOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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
                
                pregunta.wrappingWidthProperty().set(580);
                
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
}
