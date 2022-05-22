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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
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
    
    @FXML
    private Menu id_menuPerfil;
    @FXML
    private ImageView id_avatar;
    @FXML
    private TableColumn<Session, String> dateColumn;
    @FXML
    private TableColumn<Session, Integer> correctColumn;
    @FXML
    private TableColumn<Session, Integer> failsColumn;
    private static Session aux;
    @FXML
    private static TableView<Session> tabla;
    private List<Session> list;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button salirButton;
    
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
        
        list = usuario.getSessions();
        dateColumn.setCellValueFactory(new PropertyValueFactory<Session, String>("timeStamp"));
        correctColumn.setCellValueFactory(new PropertyValueFactory<Session, Integer>("hits"));
        failsColumn.setCellValueFactory(new PropertyValueFactory<Session, Integer>("faults"));

        for(int i=0;i<list.size();i++) {
            Session sesion = list.get(i);
            tabla.getItems().add(sesion);
        }
        
    }

    @FXML
    private void filtrar(InputMethodEvent event) {
        LocalTime h = LocalTime.now();
        LocalDateTime t = LocalDateTime.of(datePicker.getValue(), h);
        for (int i = 0; i < list.size(); i++) {
            
            if (list.get(i).getTimeStamp().isBefore(t)) {
                Session sesion = list.get(i);
                tabla.getItems().add(sesion); 
            }
        }
    }

    @FXML
    private void salir(ActionEvent event) {
        salirButton.getScene().getWindow().hide();
    }
    
}
