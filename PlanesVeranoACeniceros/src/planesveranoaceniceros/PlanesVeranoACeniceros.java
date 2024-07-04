/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planesveranoaceniceros;

import Data.Fichero;
import Model.Persona;
import Model.Plan;
import Model.Dni;
import Model.Personas;
import Model.Planes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import static planesveranoaceniceros.PlanesVeranoACeniceros.fichero;

public class PlanesVeranoACeniceros extends Application {

    public static Planes planes = new Planes();
    public static Personas personas = new Personas();
    public static Plan seleccionado = new Plan();
    public static Fichero fichero = new Fichero();
    public static Persona persona = new Persona();
    public static Boolean login;

    @Override
    public void start(Stage stage) throws Exception {
        crearPersonas();
        crearPlanes();
        //Métodos creados para poder probar si falla la lectura de archivos.
        //generarPersonas();
        //generarPlanes();
        //añadirPersonasYPlanes();
        Parent root = FXMLLoader.load(getClass().getResource("/View/FXMLLogin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void crearPersonas() {
        
        fichero.leerPersonas();
    }

    public static void crearPlanes() {
        
        fichero.leerPlanes();
    }
    
    public static void generarPersonas() {
        Dni d1 = new Dni(12345678);
        Dni d2 = new Dni(11112222);
        Dni d3 = new Dni(33334444);
        Dni d4 = new Dni(55556666);
        Dni d5 = new Dni(77778888);
        personas.agregarPersona(new Persona("Pepe", "Pérez", 31, d1, "password", true, false));
        personas.agregarPersona(new Persona("Jorge", "Jiménez", 20, d2, "password", true, false));
        personas.agregarPersona(new Persona("Javier", "Díaz", 29, d3, "password", true, false));
        personas.agregarPersona(new Persona("Alba", "Ríos", 20, d4, "password", true, false));
        personas.agregarPersona(new Persona("Elvira", "López", 40, d5, "password", true, false));
    }

    public static void generarPlanes() {
        Plan plan1 = new Plan("Fiesta en la Playa", "2024-07-10", "Valencia", "Fiesta de verano con música y barbacoa");
        Plan plan2 = new Plan("Excursión en la Montaña", "2024-08-05", "Sierra Nevada", "Caminata y picnic en la naturaleza");
        Plan plan3 = new Plan("Concierto de Raphael", "2024-07-20", "Sevilla", "Concierto de bandas locales en el parque");
        planes.agregarPlan(plan1);
        planes.agregarPlan(plan2);
        planes.agregarPlan(plan3);
    }

    public static void añadirPersonasYPlanes() {
        HashMap<Dni, Persona> personasMap = personas.obtenerPersonas();
        HashMap<String, Plan> planesMap = planes.obtenerPlanes();

      Plan plan1 = planesMap.get("Fiesta en la Playa");
      Plan plan2 = planesMap.get("Excursión en la Montaña");
      Plan plan3 = planesMap.get("Concierto de Raphael");

        Dni d1 = new Dni(12345678);
        Dni d2 = new Dni(11112222);
        Dni d3 = new Dni(33334444);

        Persona persona1 = personasMap.get(d1);
        Persona persona2 = personasMap.get(d2);
        Persona persona3 = personasMap.get(d3);

        if (persona1 != null) {
            plan1.agregarPersona(persona1);
            plan2.agregarPersona(persona1);
        }
        if (persona2 != null) {
            plan2.agregarPersona(persona2);
            plan3.agregarPersona(persona2);
        }
        if (persona3 != null) {
            plan3.agregarPersona(persona3);
        }
        
        plan1.calcularParticipantes();
        plan2.calcularParticipantes();
        plan3.calcularParticipantes();
        
    }
}