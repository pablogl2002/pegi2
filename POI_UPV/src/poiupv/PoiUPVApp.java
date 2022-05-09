/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import Controladores.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author jose
 */
public class PoiUPVApp extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/LogInSignUp.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/ChooseProblemType.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        primaryStage.setTitle("Log In / Sign Up");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        
        //LogInSignUpController logIn = loader.getController();
        ChooseProblemTypeController logIn = loader.getController();
        logIn.initStage(primaryStage);        
        
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}