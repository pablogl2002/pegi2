/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Session;
import model.User;

/**
 * FXML Controller class
 *
 * @author pablo
 */
public class EstadisticasController implements Initializable {

    private Stage primaryStage;
    private User usuario;
    
    private Menu id_menuPerfil;
    @FXML
    private ImageView id_avatar;
    private TableColumn<Session, String> dateColumn;
    private TableColumn<Session, Integer> correctColumn;
    private TableColumn<Session, Integer> failsColumn;
    private static Session aux;
    private static TableView<Session> tabla;
    private List<Session> list;
    private DatePicker datePicker;
    @FXML
    private Button salirButton;
    @FXML
    private TextField correctField;
    @FXML
    private TextField faultField;
    
    private int hits = 0;
    private int faults = 0;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }
    

    @FXML
    private void salir(ActionEvent event) {
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

    void initStage(Stage stage, User user) {
        primaryStage = stage;
        usuario = user;
        id_avatar.setImage(usuario.getAvatar());
        id_menuPerfil.setText(usuario.getNickName());
        
        List<Session> sesion = usuario.getSessions();
        
        for (int i = 0; i < sesion.size(); i++) {
            hits += sesion.get(i).getHits();
            faults += sesion.get(i).getFaults();
        }
        
        correctField.setText(Integer.toString(hits));
        faultField.setText(Integer.toString(faults));
    }

}
