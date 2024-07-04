/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Dni;
import Model.Persona;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import planesveranoaceniceros.PlanesVeranoACeniceros;
import static planesveranoaceniceros.PlanesVeranoACeniceros.fichero;


/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private ImageView imFondo;

    @FXML
    private TextField textFieldDni;

    @FXML
    private TextField textFieldPassword;

    @FXML
    private Button btEntrar;

    @FXML
    private Button btSalir;

    @FXML
    private Label lbDni;

    @FXML
    private Label lbPasword;

    @FXML
    private Label lbTitulo;
    
    @FXML
    private Label lbAvisos;
    
    @FXML
    private void handleButtonEntrar(ActionEvent event) throws IOException {
    
    String dni;
    String password;

    if (textFieldDni.getText().isEmpty() || textFieldPassword.getText().isEmpty()) {
        lbAvisos.setText("Por favor, introduzca sus claves.");
    
    } else {
        
        //Acceso de prueba por si falla el fichero personas
       /* lbAvisos.setText("");
        dni = textFieldDni.getText();
        password = textFieldPassword.getText();

        if (dni.equals("99999999A") && password.equals("admin")) {
            abrirScene("/View/FXMLPanel.fxml");
        } else if (dni.equals("99999999A") && password.equals("user")) {
            abrirScene("/View/FXMLApuntarse.fxml");
        } else {
            lbAvisos.setText("Las claves no son correctas.");
        }
    }*/
  
        String dniNum = textFieldDni.getText();
        String dniNumberStr = dniNum.substring(0, dniNum.length() - 1);
        int dniNumber = Integer.parseInt(dniNumberStr);
        Dni dni2 = new Dni(dniNumber);
        password = textFieldPassword.getText();

        Persona persona = PlanesVeranoACeniceros.personas.buscarPersona(dni2);
        if (persona == null) {
            lbAvisos.setText("La persona no está en el sistema.");
            return;
        }

        if (!persona.getPassword().equals(password)) {
            lbAvisos.setText("Contraseña incorrecta.");
            return;
        }

        if (persona.isOrganizador() && persona.isParticipante()) {
            lbAvisos.setText("");
            Optional<String> resultado = mostrarDialogoSeleccion();
            PlanesVeranoACeniceros.persona = persona;

            if (resultado.isPresent()) {
                if (resultado.get().equalsIgnoreCase("Comprometido")) {
                    PlanesVeranoACeniceros.login = true;
                    abrirScene("/View/FXMLPanel.fxml");
                } else if (resultado.get().equalsIgnoreCase("Jeta")) {
                    PlanesVeranoACeniceros.login = false;
                    abrirScene("/View/FXMLPanel.fxml");
                } else {
                    lbAvisos.setText("Introduzca un valor válido.");
                }
            } else {
                lbAvisos.setText("No se ha seleccionado un rol.");
            }
        } else if (persona.isOrganizador()) {
            lbAvisos.setText("");
            PlanesVeranoACeniceros.persona = persona;
            PlanesVeranoACeniceros.login = true;
            abrirScene("/View/FXMLPanel.fxml");
        } else if (persona.isParticipante()) {
            lbAvisos.setText("");
            PlanesVeranoACeniceros.persona = persona;
            PlanesVeranoACeniceros.login = false;
            abrirScene("/View/FXMLPanel.fxml");
        } else {
            lbAvisos.setText("La persona no tiene permisos para acceder.");
        }
    }
  }

private void abrirScene(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

private Optional<String> mostrarDialogoSeleccion() {
    TextInputDialog dialogo = new TextInputDialog();
    dialogo.setTitle("Selección de Rol");
    dialogo.setHeaderText("Selecciona tu rol:");
    dialogo.setContentText("¿Quieres entrar como Comprometido o como Jeta?");
    dialogo.getEditor().setPromptText("Comprometido o Jeta");

    return dialogo.showAndWait();
}

    
    @FXML
    private void handleButtonSalir(ActionEvent event) {
        
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("CONFIRMAR SALIR");
        alerta.setHeaderText("¿Está seguro de que desea salir");
        alerta.setContentText("Su sesión será cerrada y los cambios guardados en los ficheros.");
        alerta.initStyle(StageStyle.UTILITY);
        Optional<ButtonType> resultado = alerta.showAndWait();
        if(resultado.get()==ButtonType.OK){
          fichero.escribirPlanes();
          System.exit(0);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
