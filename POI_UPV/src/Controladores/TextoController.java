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
public class TextoController extends PruebaProblemasMapaController implements Initializable {

    @FXML
    private ColorPicker selectorColorTexto;
    @FXML
    private Slider sliderGrosorTexto;
    @FXML
    private Button aceptarTexto;
    private Color colorTexto;
    private int grosorTexto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sliderGrosorTexto.setMin(20.0);
        sliderGrosorTexto.setMax(100.0);
        sliderGrosorTexto.setValue(10.0);
        
        // TODO
    }    

    @FXML
    private void AceptarTexto(ActionEvent event) {
        intAyuda = 2;
        colorTexto = (Color) selectorColorTexto.getValue();
        grosorTexto = (int) sliderGrosorTexto.getValue();
        Stage ventana = (Stage) aceptarTexto.getScene().getWindow();
        ventana.close();
    }
    
    public Color getColor() {
        return colorTexto;
       
    }
    public int getTamaño() {
        return grosorTexto;
       
    }
}
