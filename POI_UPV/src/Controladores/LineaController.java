/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Controladores.PruebaProblemasMapaController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sergio Pardo
 */
public class LineaController extends PruebaProblemasMapaController  implements Initializable {

    @FXML
    private Slider sliderGrosorLinea;
    @FXML
    private Button aceptarLinea;
    @FXML
    private ColorPicker selectorColorLinea;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sliderGrosorLinea.setMin(1.0);
        sliderGrosorLinea.setMax(10);
        sliderGrosorLinea.setValue(1.0);
        
        // TODO
    }    

    @FXML
    private void colorLinea(ActionEvent event) {
        colorLinea = selectorColorLinea.getValue();
        linePainting.setStroke(colorLinea);
    }

    @FXML
    private void AceptarLinea(ActionEvent event) {
        intAyuda = 2;
        aceptarLinea.getScene().getWindow().hide();
        
        
        
    }
    
}

