/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import Controladores.LogInSignUpController;
import Controladores.ProblemsController;
import Controladores.PruebaProblemasMapaController;
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
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/LogInSignUp.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/PruebaProblemasMapa.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        stage.setTitle("Iniciar Sesión o Registrar Cuenta");
        stage.setScene(scene);
        stage.setResizable(true);
        
        //ProblemsController ctr = loader.getController();
        LogInSignUpController ctr = loader.getController();
        //PruebaProblemasMapaController ctr = loader.getController();
        ctr.initStage(stage);
        
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
