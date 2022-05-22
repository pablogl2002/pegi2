/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.paint.Color;

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
    public Line linePainting;
    private double baseX;
    private double baseY;
    private double inicioYTrans;
    private double inicioXTrans;
    public int intAyuda;
    private double inicioXArc;
    private Stage primaryStage;
    public double grosorLinea;
    public Color colorLinea;
    public Color colorCirculo;
    public double grosorCirculo;
    public Color colorTexto;
    public int grosorTexto;
    
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
      
        if (event.isSecondaryButtonDown()){
            intAyuda = 24;
            
        }//Estoy hay que cambiarlo porque al usar el click derecho se quita la funcion de dibujar y demás
        
        if (intAyuda == 2) {
            linePainting = new Line(event.getX(), event.getY(), event.getX(), event.getY());
            //colorLinea = BLACK;
            
            linePainting.setStroke(colorLinea);
            linePainting.setStrokeWidth(grosorLinea);
            zoomGroup.getChildren().add(linePainting);
            linePainting.setOnContextMenuRequested(e -> {
                ContextMenu menuContext = new ContextMenu();
                MenuItem borrarItem = new MenuItem("eliminar");
                menuContext.getItems().add(borrarItem);
                borrarItem.setOnAction(ev -> {
                    zoomGroup.getChildren().remove((Node)e.getSource());
                    ev.consume();
                });    
                MenuItem marcarPosicion = new MenuItem("marcarPosición");
                menuContext.getItems().add(marcarPosicion);
                marcarPosicion.setOnAction(ev -> {
                    Line linePaintingX = new Line(event.getX(), event.getY(), event.getX(), event.getY());
                    linePaintingX.setStroke(colorLinea);
                    linePaintingX.setStrokeWidth(grosorLinea);
                    Line linePaintingY = new Line(event.getX(), event.getY(), event.getX(), event.getY());
                    linePaintingY.setStroke(colorLinea);
                    linePaintingY.setStrokeWidth(grosorLinea);
                    zoomGroup.getChildren().add(linePaintingX);
                    zoomGroup.getChildren().add(linePaintingY);
                    double anchoMinimo = event.getX();
                    double largoMinimo = event.getY();
                    linePaintingX.setStartX(0);
                    linePaintingX.setStartY(event.getY());
                    linePaintingX.setEndX(8974);
                    linePaintingX.setEndY(largoMinimo);
                    linePaintingY.setStartX(event.getX());
                    linePaintingY.setStartY(0);
                    linePaintingY.setEndX(anchoMinimo);
                    linePaintingY.setEndY(5746);
                    ev.consume();
                });
                MenuItem editarItem = new MenuItem("editar");
                menuContext.getItems().add(editarItem);
                editarItem.setOnAction(ev -> {
                    try {
                        FXMLLoader loaderLinea = new FXMLLoader(getClass().getResource("/Vistas/Linea.fxml"));
                        Parent root = loaderLinea.load();
                        LineaController controlador = loaderLinea.getController();

                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setTitle("Opciones Linea");
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setScene(scene);
                        stage.showAndWait();

                        colorLinea = controlador.getColor();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Line lineaNueva = (Line) e.getSource();
                    lineaNueva.setStroke(colorLinea);
                });
                 menuContext.show(zoom_slider.getScene().getWindow(), e.getSceneX(), e.getSceneY());
                e.consume();
            });
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
            circlePainting.setStroke(colorCirculo);
            circlePainting.setStrokeWidth(grosorCirculo);
            circlePainting.setFill(null);
            
            zoomGroup.getChildren().add(circlePainting);
            
            circlePainting.setCenterX(event.getX());
            circlePainting.setCenterY(event.getY());
            inicioXArc = event.getX();
            
            circlePainting.setOnContextMenuRequested(e -> {
                ContextMenu menuContext = new ContextMenu();
                MenuItem borrarItem = new MenuItem("eliminar");
                menuContext.getItems().add(borrarItem);
                borrarItem.setOnAction(ev -> {
                    zoomGroup.getChildren().remove((Node)e.getSource());
                    ev.consume();
                });
                MenuItem editarItem = new MenuItem("editar");
                menuContext.getItems().add(editarItem);
                editarItem.setOnAction(ev -> {
                    try {
                        FXMLLoader loaderCirculo = new FXMLLoader(getClass().getResource("/Vistas/Circulo.fxml"));
                        Parent root = loaderCirculo.load();
                        CirculoController controlador = loaderCirculo.getController();

                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setTitle("Opciones Circulo");
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setScene(scene);
                        stage.showAndWait();

                        colorCirculo = controlador.getColor();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Circle circuloNuevo = (Circle) e.getSource();
                    circuloNuevo.setStroke(colorCirculo);
                });
                 menuContext.show(zoom_slider.getScene().getWindow(), e.getSceneX(), e.getSceneY());
                e.consume();
            });
            
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
                textoT.prefHeight(grosorTexto);
                textoT.setFont(Font.font(grosorTexto));
                //textoT.setFont(Font.font("Gafata",FontWeight.BOLD,grosorTexto));
                textoT.setFill( colorTexto) ;
                zoomGroup.getChildren().add(textoT);
                zoomGroup.getChildren().remove(texto);
                e.consume();
                textoT.setOnContextMenuRequested(r -> {
                ContextMenu menuContext = new ContextMenu();
                MenuItem borrarItem = new MenuItem("eliminar");
                menuContext.getItems().add(borrarItem);
                borrarItem.setOnAction(ev -> {
                    zoomGroup.getChildren().remove((Node)r.getSource());
                    ev.consume();
                });
                MenuItem editarItem = new MenuItem("editar");
                menuContext.getItems().add(editarItem);
                editarItem.setOnAction(ev -> {
                    try {
                        FXMLLoader loaderTexto = new FXMLLoader(getClass().getResource("/Vistas/Texto.fxml"));
                        Parent root = loaderTexto.load();
                        TextoController controlador = loaderTexto.getController();

                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setTitle("Opciones Texto");
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setScene(scene);
                        stage.showAndWait();

                        colorTexto = controlador.getColor();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Text textoNuevo = (Text) r.getSource();
                    textoNuevo.setFill(colorTexto);
                });
                 menuContext.show(zoom_slider.getScene().getWindow(), r.getSceneX(), r.getSceneY());
                r.consume();
            });
            });
            
            
        }
       
        
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
    private void Condicion2(ActionEvent event) throws IOException {
        intAyuda = 2;

            FXMLLoader loaderLinea = new FXMLLoader(getClass().getResource("/Vistas/Linea.fxml"));
            Parent root = loaderLinea.load();
            LineaController controlador = loaderLinea.getController();


            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Opciones Linea");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            
            colorLinea = controlador.getColor();
            grosorLinea = controlador.getTamaño();

    }

    @FXML
    private void Condicion3(ActionEvent event) throws IOException {
        intAyuda = 3;
        FXMLLoader loaderCirculo = new FXMLLoader(getClass().getResource("/Vistas/Circulo.fxml"));
            Parent root2 = loaderCirculo.load();
            CirculoController controlador2 = loaderCirculo.getController();


            Scene scene2 = new Scene(root2);
            Stage stage2 = new Stage();
            stage2.setTitle("Opciones Circulo");
            stage2.initModality(Modality.APPLICATION_MODAL);
            stage2.setScene(scene2);
            stage2.showAndWait();
            
            colorCirculo = controlador2.getColor();
            grosorCirculo = controlador2.getTamaño();
    }

    @FXML
    private void Condicion4(ActionEvent event) throws IOException {
        intAyuda = 4;
        FXMLLoader loaderTexto = new FXMLLoader(getClass().getResource("/Vistas/Texto.fxml"));
            Parent root3 = loaderTexto.load();
            TextoController controlador3 = loaderTexto.getController();


            Scene scene2 = new Scene(root3);
            Stage stage2 = new Stage();
            stage2.setTitle("Opciones Texto");
            stage2.initModality(Modality.APPLICATION_MODAL);
            stage2.setScene(scene2);
            stage2.showAndWait();
            
            colorTexto = controlador3.getColor();
            grosorTexto = controlador3.getTamaño();
            
    }

    @FXML
    private void Borrar(ActionEvent event) {      
        zoomGroup.getChildren().remove(1,zoomGroup.getChildren().size()); 
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
