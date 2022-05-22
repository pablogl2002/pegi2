/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Sergio Pardo
 */
public class CirculoController extends PruebaProblemasMapaController  implements Initializable {

    @FXML
    private Slider SliderCirculoGrosor;
    private int grosorCirculos;
    private Color colorCirculos;
    @FXML
    private ColorPicker selectorColorCirculo;
    @FXML
    private Button aceptarCirculo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        SliderCirculoGrosor.setMin(1.0);
        SliderCirculoGrosor.setMax(10);
        SliderCirculoGrosor.setValue(1.0);
        
    }    

    
    @FXML
    private void AceptarCirculo(ActionEvent event) {
        intAyuda = 2;
        colorCirculos = (Color) selectorColorCirculo.getValue();
        grosorCirculos = (int) SliderCirculoGrosor.getValue();
        Stage ventana = (Stage) aceptarCirculo.getScene().getWindow();
        ventana.close();
    }
    public Color getColor() {
        return colorCirculos;
       
    }
    public int getTamaño() {
        
        return grosorCirculos;
       
    }
    }

    