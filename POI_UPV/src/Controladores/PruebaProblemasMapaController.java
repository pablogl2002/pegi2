/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author jsoler
 */
public class PruebaProblemasMapaController implements Initializable {

    //=======================================
    // hashmap para guardar los puntos de interes POI
    private final HashMap<String, Poi> hm = new HashMap<>();
    // ======================================
    // la variable zoomGroup se utiliza para dar soporte al zoom
    // el escalado se realiza sobre este nodo, al escalar el Group no mueve sus nodos
    private Group zoomGroup;

    @FXML
    private ListView<Poi> map_listview;
    @FXML
    private ScrollPane map_scrollpane;
    @FXML
    private Slider zoom_slider;
    @FXML
    private MenuButton map_pin;
    @FXML
    private MenuItem pin_info;
    @FXML
    private Label posicion;
    private Line linePainting;
    private double baseX;
    private double baseY;
    private double inicioYTrans;
    private double inicioXTrans;
    private int intAyuda;
    private double inicioXArc;
    private Stage primaryStage;
    
    
    Circle circlePainting;
    TextField texto = new TextField();
    @FXML
    private ImageView transportador;
    
    @FXML
    void zoomIn(ActionEvent event) {
        //================================================
        // el incremento del zoom dependerá de los parametros del 
        // slider y del resultado esperado
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal += 0.1);
    }

    @FXML
    void zoomOut(ActionEvent event) {
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal + -0.1);
    }
    
    // esta funcion es invocada al cambiar el value del slider zoom_slider
    private void zoom(double scaleValue) {
        //===================================================
        //guardamos los valores del scroll antes del escalado
        double scrollH = map_scrollpane.getHvalue();
        double scrollV = map_scrollpane.getVvalue();
        //===================================================
        // escalamos el zoomGroup en X e Y con el valor de entrada
        zoomGroup.setScaleX(scaleValue);
        zoomGroup.setScaleY(scaleValue);
        //===================================================
        // recuperamos el valor del scroll antes del escalado
        map_scrollpane.setHvalue(scrollH);
        map_scrollpane.setVvalue(scrollV);
    }

    @FXML
    void listClicked(MouseEvent event) {
        Poi itemSelected = map_listview.getSelectionModel().getSelectedItem();

        // Animación del scroll hasta la posicion del item seleccionado
        double mapWidth = zoomGroup.getBoundsInLocal().getWidth();
        double mapHeight = zoomGroup.getBoundsInLocal().getHeight();
        double scrollH = itemSelected.getPosition().getX() / mapWidth;
        double scrollV = itemSelected.getPosition().getY() / mapHeight;
        final Timeline timeline = new Timeline();
        final KeyValue kv1 = new KeyValue(map_scrollpane.hvalueProperty(), scrollH);
        final KeyValue kv2 = new KeyValue(map_scrollpane.vvalueProperty(), scrollV);
        final KeyFrame kf = new KeyFrame(Duration.millis(500), kv1, kv2);
        timeline.getKeyFrames().add(kf);
        timeline.play();

        // movemos el objto map_pin hasta la posicion del POI
        double pinW = map_pin.getBoundsInLocal().getWidth();
        double pinH = map_pin.getBoundsInLocal().getHeight();
        map_pin.setLayoutX(itemSelected.getPosition().getX());
        map_pin.setLayoutY(itemSelected.getPosition().getY());
        pin_info.setText(itemSelected.getDescription());
        map_pin.setVisible(true);
    }

    private void initData() {
        hm.put("2F", new Poi("2F", "Edificion del DSIC", 325, 225));
        hm.put("Agora", new Poi("Agora", "Agora", 600, 360));
        map_listview.getItems().add(hm.get("2F"));
        map_listview.getItems().add(hm.get("Agora"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initData();
        //==========================================================
        // inicializamos el slider y enlazamos con el zoom
        zoom_slider.setMin(0.5);
        zoom_slider.setMax(1.5);
        zoom_slider.setValue(1.0);
        zoom_slider.valueProperty().addListener((o, oldVal, newVal) -> zoom((Double) newVal));

        //=========================================================================
        //Envuelva el contenido de scrollpane en un grupo para que
        //ScrollPane vuelva a calcular las barras de desplazamiento tras el escalado
        Group contentGroup = new Group();
        zoomGroup = new Group();
        contentGroup.getChildren().add(zoomGroup);
        zoomGroup.getChildren().add(map_scrollpane.getContent());
        map_scrollpane.setContent(contentGroup);
        map_scrollpane.setPannable(false);
    }

    @FXML
    private void muestraPosicion(MouseEvent event) {
        posicion.setText("sceneX: " + (int) event.getSceneX() + ", sceneY: " + (int) event.getSceneY() + "\n"
                + "         X: " + (int) event.getX() + ",          Y: " + (int) event.getY());
    }

    @FXML
    private void cerrarAplicacion(ActionEvent event) {
        ((Stage)zoom_slider.getScene().getWindow()).close();
    }

    @FXML
    private void acercaDe(ActionEvent event) {
        Alert mensaje= new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Acerca de");
        mensaje.setHeaderText("IPC - 2022");
        mensaje.showAndWait();
    }
    
    @FXML
     private void RatonPulsado(MouseEvent event) {
      
        if (event.isSecondaryButtonDown()){intAyuda = 935;}//Estoy hay que cambiarlo porque al usar el click derecho se quita la funcion de dibujar y demás
        
        if (intAyuda == 2) {
            linePainting = new Line(event.getX(), event.getY(), event.getX(), event.getY());
            zoomGroup.getChildren().add(linePainting);
        }
        
        if (intAyuda == 1) {
            transportador.setOpacity(0.5);
            transportador.fitHeightProperty().set(240.0);
            transportador.fitWidthProperty().set(240.0);
            inicioXTrans = event.getSceneX();
            inicioYTrans = event.getSceneY();
            baseX = transportador.getTranslateX();
            baseY = transportador.getTranslateY();
            event.consume();
        }
        
        if (intAyuda == 3) {
            circlePainting = new Circle(1);
            circlePainting.setStroke(Color.RED);
            circlePainting.setFill(null);
            
            zoomGroup.getChildren().add(circlePainting);
            
            circlePainting.setCenterX(event.getX());
            circlePainting.setCenterY(event.getY());
            inicioXArc = event.getX();
        }
        
        if (intAyuda == 4){
            zoomGroup.getChildren().add(texto);
            texto.setLayoutX(event.getX());
            texto.setLayoutY(event.getY());
            texto.requestFocus();
            texto.setOnAction(e -> {
                Text textoT= new Text(texto.getText());
                textoT.setX(texto.getLayoutX());
                textoT.setY(texto.getLayoutY());
                textoT.setStyle("-fx-font-family: Gafata; -fx-font-size: 40;");
                zoomGroup.getChildren().add(textoT);
                zoomGroup.getChildren().remove(texto);
                e.consume();
            });
        }
        
        linePainting.setOnContextMenuRequested(e -> {
            ContextMenu menuContext = new ContextMenu();
            MenuItem borrarItem = new MenuItem("eliminar");
            menuContext.getItems().add(borrarItem);
            borrarItem.setOnAction(ev -> {
                zoomGroup.getChildren().remove((Node)e.getSource());
                ev.consume();
            });
            MenuItem borrarTodo = new MenuItem("Borrar Todo");
            menuContext.getItems().add(borrarTodo);
            borrarTodo.setOnAction(ev -> {
                zoomGroup.getChildren().remove(1,zoomGroup.getChildren().size());
                ev.consume();
            });
            menuContext.show(linePainting, e.getSceneX(), e.getSceneY());
            e.consume();
        });
        
        circlePainting.setOnContextMenuRequested(e -> {
            ContextMenu menuContext = new ContextMenu();
            MenuItem borrarItem = new MenuItem("eliminar");
            menuContext.getItems().add(borrarItem);
            borrarItem.setOnAction(ev -> {
                zoomGroup.getChildren().remove((Node)e.getSource());
                ev.consume();
            });
            MenuItem borrarTodo = new MenuItem("Borrar Todo");
            menuContext.getItems().add(borrarTodo);
            borrarTodo.setOnAction(ev -> {
                zoomGroup.getChildren().remove(1,zoomGroup.getChildren().size());
                ev.consume();
            });
            menuContext.show(circlePainting, e.getSceneX(), e.getSceneY());
            e.consume();
        });
        
        
     }
     
    @FXML
     private void RatonArrastrado(MouseEvent event) {
            
        double despX = event.getSceneX() - inicioXTrans;
        double despY = event.getSceneY() - inicioYTrans;
            
        if (intAyuda == 1){
     
            despX = event.getSceneX() - inicioXTrans;
            despY = event.getSceneY() - inicioYTrans;
            transportador.setTranslateX(baseX + despX);
            transportador.setTranslateY(baseY + despY);
            event.consume();
            
        }
        if (intAyuda == 3){
            double radio = Math.abs(event.getX()- inicioXArc);
            circlePainting.setRadius(radio);
            event.consume();
            
        }
        
        if (intAyuda == 2){
            linePainting.setEndX(event.getX());
            linePainting.setEndY(event.getY());
            event.consume();
        }
    }
    @FXML
      private void RatonSoltado(MouseEvent event) {
        if (intAyuda == 2){
            linePainting.setEndX(event.getX());
            linePainting.setEndY(event.getY());
            event.consume();
        }
    }

    @FXML
    private void Condicion1(ActionEvent event) {
        intAyuda = 1;
    }

    @FXML
    private void Condicion2(ActionEvent event) {
        intAyuda = 2;
    }

    @FXML
    private void Condicion3(ActionEvent event) {
        intAyuda = 3;
    }

    @FXML
    private void Condicion4(ActionEvent event) {
        intAyuda = 4;
    }

    @FXML
    private void Borrar(ActionEvent event) {
        
    }

    @FXML
    private void moverFondo(ActionEvent event) {
        map_scrollpane.setPannable(true);
        intAyuda = 115;
    }

    public void initStage(Stage stage) {
        primaryStage = stage;
    }
}
