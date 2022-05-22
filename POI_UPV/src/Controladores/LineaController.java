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
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/* FXML Controller class
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
    private Color color;
    private int tamañoRalla;

    /* Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sliderGrosorLinea.setMin(1.0);
        sliderGrosorLinea.setMax(10);
        sliderGrosorLinea.setValue(1.0);


        // TODO
    }

    @FXML
    private void AceptarLinea(ActionEvent event) {
        intAyuda = 2;
        color = selectorColorLinea.getValue();
        tamañoRalla = (int) sliderGrosorLinea.getValue();
        Stage ventana = (Stage) aceptarLinea.getScene().getWindow();
        ventana.close();
    }
    
    public Color getColor() {
        return color;

    }
    
    public int getTamaño() {
        return tamañoRalla;

    }

}

