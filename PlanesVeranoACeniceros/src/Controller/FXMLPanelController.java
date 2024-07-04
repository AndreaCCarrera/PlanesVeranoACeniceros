/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Data.Fichero;
import Model.Dni;
import Model.Persona;
import Model.Plan;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import planesveranoaceniceros.PlanesVeranoACeniceros;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class FXMLPanelController implements Initializable {
    
    private ObservableList<Persona> listaPersonas;
    private ObservableList<Plan> listaPlanes;
    
    
    @FXML
    private Label label;
    
    @FXML
    private Label lbPersona;
    
    @FXML
    private Button btAnadir;
    
    @FXML
    private Button btEliminar;
       
    @FXML
    private Button btModificar;
       
    @FXML
    private ImageView imFondo;
     
    @FXML
    private TableView<Plan> tbPlanes;
    
    @FXML
    private TextField textFieldNombre;
    
    @FXML
    private TextField textFieldLugar;
    
    @FXML
    private TextField textFieldDescripcion;
    
    @FXML
    private DatePicker dateFecha;
    
    @FXML private TableColumn <Plan, String> cNombre;
    
    @FXML private TableColumn <Plan, String> cFecha;
    
    @FXML private TableColumn <Plan, String> cLugar;
    
    @FXML private TableColumn <Plan, String> cDescripcion;
    
    @FXML private TableColumn <Plan, String> cNumApuntados;
    
    private Fichero f = new Fichero();
    
    @FXML
    private void handleButtonVer(ActionEvent event) throws IOException {
    PlanesVeranoACeniceros.seleccionado = tbPlanes.getSelectionModel().getSelectedItem();

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXMLMostrar.fxml"));
    Parent root = loader.load();
    FXMLMostrarController controller = loader.getController();
    
    Scene scene = new Scene(root);
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setScene(scene);
    stage.showAndWait();
}
    
    
   @FXML
    private void handleButtonAnadir(ActionEvent event) throws IOException {
        Plan selectedPlan = tbPlanes.getSelectionModel().getSelectedItem();
         Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        if(btAnadir.getText().equals("AÑADIR")){
            

            if (textFieldNombre != null && !textFieldNombre.getText().isEmpty()
                    && textFieldLugar != null && !textFieldLugar.getText().isEmpty()
                    && textFieldDescripcion != null && !textFieldDescripcion.getText().isEmpty()
                    && dateFecha != null && dateFecha.getValue() != null) {

                String nombre = textFieldNombre.getText();
                String lugar = textFieldLugar.getText();
                String descripcion = textFieldDescripcion.getText();
                LocalDate fecha = dateFecha.getValue();

                alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("CONFIRMAR AGREGADO");
                alerta.setHeaderText("¿Estás seguro de que deseas agregar el plan?");
                alerta.setContentText("El plan será agregado a la lista.");
                alerta.initStyle(StageStyle.UTILITY);
                Optional<ButtonType> resultado = alerta.showAndWait();

                if (resultado.get() == ButtonType.OK) {
                    Plan nuevoPlan = new Plan(nombre, fecha.toString(), lugar, descripcion);

                    if (PlanesVeranoACeniceros.planes.agregarPlan(nuevoPlan)) {
                        listaPlanes.add(nuevoPlan); 
                        label.setText("Plan agregado correctamente.");
                        textFieldNombre.setText("");
                        textFieldLugar.setText("");
                        textFieldDescripcion.setText("");
                        dateFecha.setValue(null);
                        actualizarTabla();
                        f.escribirPlanes();
                    } else {
                        label.setText("No se pudo agregar el plan.");
                    }
                } else {
                    label.setText("Operación cancelada.");
                }
            } else {
                label.setText("Por favor, rellena todos los campos del plan.");
            }
            
        }else{
            
            if (selectedPlan != null){
                if(selectedPlan.buscarPersona(PlanesVeranoACeniceros.persona)){
                    label.setText("Ya estás en el plan seleccionado.");
                }else{
                    alerta.setTitle("CONFIRMAR INSCRIPCIÓN");
                    alerta.setHeaderText("¿Estás seguro de que quieres apuntarte al plan?");
                    alerta.setContentText("¡Seguro que lo pasas fenomenal!");
                    alerta.initStyle(StageStyle.UTILITY);
                    Optional<ButtonType> resultado = alerta.showAndWait();
                        if (resultado.get() == ButtonType.OK) {
                            PlanesVeranoACeniceros.seleccionado.agregarPersona(PlanesVeranoACeniceros.persona);
                            PlanesVeranoACeniceros.seleccionado.calcularParticipantes();
                            PlanesVeranoACeniceros.planes.modificarPlan(PlanesVeranoACeniceros.seleccionado);       
                            actualizarTabla();
                            f.escribirPlanes();
                            label.setText("¡Genial, te has apuntado al plan!");
                        }else{
                            label.setText("¡Qué pena, otra vez será!");
                        }                        
                }
            }else{
                label.setText("Por favor, selecciona un plan.");
            }
                
        }
            
          
    }

    
  @FXML
    private void handleButtonModificar(ActionEvent event) throws IOException {
        Plan selectedPlan = tbPlanes.getSelectionModel().getSelectedItem();
        if (selectedPlan != null) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            if (textFieldNombre != null && !textFieldNombre.getText().isEmpty()
                    && textFieldLugar != null && !textFieldLugar.getText().isEmpty()
                    && textFieldDescripcion != null && !textFieldDescripcion.getText().isEmpty()
                    && dateFecha != null && dateFecha.getValue() != null) {

                String nombre = textFieldNombre.getText();
                String lugar = textFieldLugar.getText();
                String descripcion = textFieldDescripcion.getText();
                LocalDate fecha = dateFecha.getValue();

                alerta.setTitle("CONFIRMAR CAMBIOS");
                alerta.setHeaderText("¿Estás seguro de que quieres cambiar el plan?");
                alerta.setContentText("Todos los apuntados se verán afectados.");
                alerta.initStyle(StageStyle.UTILITY);
                Optional<ButtonType> resultado = alerta.showAndWait();
                if (resultado.get() == ButtonType.OK) {

                    if (!nombre.isEmpty() && fecha != null && !lugar.isEmpty() && !descripcion.isEmpty()) {

                        int numeroPersonas = selectedPlan.getNum();

                        Plan nuevoPlan = new Plan(nombre, fecha.toString(), lugar, descripcion, numeroPersonas);

                        HashMap<Dni, Persona> participantesOriginales = selectedPlan.getParticipantes();

                        nuevoPlan.setParticipantes(participantesOriginales);
                        if (PlanesVeranoACeniceros.planes.modificarPlan(nuevoPlan)) {

                            listaPlanes.set(listaPlanes.indexOf(selectedPlan), nuevoPlan);
                            label.setText("¡Ya está el plan a tu gusto!");
                            actualizarTabla();
                            f.escribirPlanes();

                            textFieldNombre.setText("");
                            textFieldLugar.setText("");
                            textFieldDescripcion.setText("");
                            dateFecha.setValue(null);
                        } else {
                            label.setText("No se pudo modificar el plan.");
                        }
                    } else {
                        label.setText("Por favor, rellena todos los campos del plan.");
                    }
                } else {
                    label.setText("Te has arrepentido, ¿eh? ;)");
                }
            } else {
                label.setText("Por favor, rellena todos los campos del plan.");
            }
        } else {
            label.setText("Por favor, selecciona un plan.");
        }
    }
    
    @FXML
private void handleButtonEliminar(ActionEvent event) throws IOException {
    Plan selectedPlan = tbPlanes.getSelectionModel().getSelectedItem();
    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
    Alert error = new Alert(Alert.AlertType.ERROR);
    TextInputDialog segundoFactor = new TextInputDialog();

    if (btEliminar.getText().equals("BORRAR")) {
        if (selectedPlan != null) {
            alerta.setTitle("CONFIRMAR BORRADO");
            alerta.setHeaderText("¿Está seguro de que desea borrar este plan?");
            alerta.setContentText("Esta operación es irreversible.");
            alerta.initStyle(StageStyle.UTILITY);
            Optional<ButtonType> resultado = alerta.showAndWait();
            if (resultado.get() == ButtonType.OK) {
                segundoFactor.setTitle("CONFIRMACIÓN DE SEGURIDAD");
                    segundoFactor.setHeaderText("Nombre del plan a borrar");
                    segundoFactor.setContentText("Por favor,el nombre completo del plan.");
                    Optional<String> respuesta = segundoFactor.showAndWait();            
                if(respuesta.get().equals(selectedPlan.getNombre())){
                    if (PlanesVeranoACeniceros.planes.borrarPlan(selectedPlan.getNombre())) {
                        listaPlanes.remove(selectedPlan);
                        label.setText("Plan borrado correctamente.");
                        actualizarTabla();
                        f.escribirPlanes();
                    } else {
                        label.setText("No se pudo borrar el plan.");
                    }
                    
                }else{
                    error.setTitle("ERROR DE CONFIRMACIÓN");
                    error.setHeaderText("Nombre del plan incorrecto.");
                    error.setContentText("Los datos no coinciden.");
                    error.showAndWait();
                }
            } else {
                label.setText("Operación cancelada.");
            }
        } else {
            label.setText("Por favor, selecciona el plan a borrar.");
        }
    } else {
        if (selectedPlan != null) {
            if (!selectedPlan.buscarPersona(PlanesVeranoACeniceros.persona)) {
                label.setText("No estás en el plan seleccionado.");
            } else {
                alerta.setTitle("CONFIRMAR BAJA");
                alerta.setHeaderText("¿Estás seguro de que quieres borrarte del plan?");
                alerta.setContentText("¡Qué pena, otra vez será!");
                alerta.initStyle(StageStyle.UTILITY);
                Optional<ButtonType> resultado = alerta.showAndWait();
                if (resultado.get() == ButtonType.OK) {
                    selectedPlan.borrarPersona(PlanesVeranoACeniceros.persona.getDni());
                    PlanesVeranoACeniceros.planes.modificarPlan(selectedPlan);
                    listaPlanes.set(listaPlanes.indexOf(selectedPlan), selectedPlan); // Actualiza la lista                    
                    actualizarTabla();
                    f.escribirPlanes();
                    label.setText("Baja del plan confirmada :( ");
                } else {
                    label.setText("¡Genial, no te arrepentirás!");
                }
            }
        } else {
            label.setText("Por favor, selecciona un plan.");
        }
    }
}
       
    @FXML
    private void handleButtonVolver(ActionEvent event) throws IOException {
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
                
    }
    
    
    @FXML
    private void handleButtonReset(ActionEvent event) throws IOException {
        
        tbPlanes.getSelectionModel().clearSelection();
        textFieldNombre.setText("");
        textFieldLugar.setText("");
        textFieldDescripcion.setText("");
        dateFecha.setValue(null);
        label.setText("");
                
    } 
       
    private boolean validarCamposPlan() {
       return !textFieldNombre.getText().isEmpty() && !textFieldLugar.getText().isEmpty() &&
           !textFieldDescripcion.getText().isEmpty() && dateFecha.getValue() != null;
    }

    private void mostrarDetallesPlan(Plan plan) throws IOException {
        if (plan != null) {
            textFieldNombre.setText(plan.getNombre());
            textFieldLugar.setText(String.valueOf(plan.getLugar()));
            textFieldDescripcion.setText(String.valueOf(plan.getDescripcion()));        
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fecha = LocalDate.parse(plan.getFecha(), formatter);
            dateFecha.setValue(fecha);

        } else {
            handleButtonReset(null);
        }
    }
    
    public void actualizarTabla() {
        HashMap<String, Plan> mapaPlanes = PlanesVeranoACeniceros.planes.obtenerPlanes();
        listaPlanes.setAll(mapaPlanes.values());
        tbPlanes.refresh();
}
      
   
  @Override
        public void initialize(URL url, ResourceBundle rb) {
        lbPersona.setText("Welcome to the party, " + PlanesVeranoACeniceros.persona.getNombre());
        if (PlanesVeranoACeniceros.login) {
            btModificar.setVisible(false);
            btAnadir.setText("AÑADIR");
            btEliminar.setText("BORRAR");
        } else {
            btAnadir.setText("APUNTARSE");
            btEliminar.setText("BORRARSE");
        }

        HashMap<String, Plan> mapaPlanes = PlanesVeranoACeniceros.planes.obtenerPlanes();
        listaPlanes = FXCollections.observableArrayList(mapaPlanes.values());

        cNombre.setCellValueFactory(cellData -> cellData.getValue().Nombre());
        cFecha.setCellValueFactory(cellData -> cellData.getValue().Fecha());
        cLugar.setCellValueFactory(cellData -> cellData.getValue().Lugar());
        cDescripcion.setCellValueFactory(cellData -> cellData.getValue().Descripcion());
        cNumApuntados.setCellValueFactory(cellData -> cellData.getValue().Num());

         tbPlanes.setItems(listaPlanes);

    tbPlanes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            try {
                mostrarDetallesPlan(newValue);
            } catch (IOException ex) {
                Logger.getLogger(FXMLPanelController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                handleButtonReset(null);
            } catch (IOException ex) {
                Logger.getLogger(FXMLPanelController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    });


        tbPlanes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        PlanesVeranoACeniceros.seleccionado = newValue;
        if (newValue != null) {
            textFieldNombre.setText(newValue.getNombre());
            textFieldLugar.setText(newValue.getLugar());
            textFieldDescripcion.setText(newValue.getDescripcion());
            dateFecha.setValue(LocalDate.parse(newValue.getFecha()));
        }
    });
    }
    
}
