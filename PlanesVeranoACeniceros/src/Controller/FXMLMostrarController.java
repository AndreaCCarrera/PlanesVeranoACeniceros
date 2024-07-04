/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Dni;
import Model.Persona;
import Model.Personas;
import Model.Plan;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import planesveranoaceniceros.PlanesVeranoACeniceros;


/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class FXMLMostrarController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private ObservableList<Persona> listaMostrar;
    private Plan planSeleccionado = PlanesVeranoACeniceros.seleccionado;
    
    @FXML private Label titulo;
    
    @FXML private TableView<Persona> tablaPersonas;
    
    @FXML private TableColumn <Persona, String> cNombre;
    
    @FXML private TableColumn <Persona, String> cMote;
    
    @FXML private TableColumn <Persona, String> cEdad;
    
    @FXML private TableColumn <Persona, Object> cDni;
   
    
    @FXML
    private void handleButtonVolver(ActionEvent event) {
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.close();

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        HashMap<Dni, Persona> mapaPersonas = PlanesVeranoACeniceros.personas.obtenerPersonas();
        listaMostrar = FXCollections.observableArrayList(mapaPersonas.values());
        
        cNombre.setCellValueFactory(cellData->cellData.getValue().Nombre());
        cMote.setCellValueFactory(cellData->cellData.getValue().Mote());
        cEdad.setCellValueFactory(cellData->cellData.getValue().Edad());
        cDni.setCellValueFactory(cellData->cellData.getValue().Dni());
        
        tablaPersonas.setItems(listaMostrar);
        
          if (planSeleccionado != null) {
            listaMostrar = FXCollections.observableArrayList();
            for (Persona persona : mapaPersonas.values()) {
                if (persona.estaApuntado(planSeleccionado)) {
                    listaMostrar.add(persona);
                }
            }
        } else {
            listaMostrar = FXCollections.observableArrayList(mapaPersonas.values());
        }
        
        tablaPersonas.setItems(listaMostrar);

    }
    
    public void setPlanSeleccionado(Plan planSeleccionado) {
        this.planSeleccionado = PlanesVeranoACeniceros.seleccionado;
    }
    
}
