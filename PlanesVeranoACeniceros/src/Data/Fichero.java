package Data;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Model.Dni;
import Model.Persona;
import Model.Plan;
import java.io.BufferedWriter;
import java.io.IOException;
import planesveranoaceniceros.PlanesVeranoACeniceros;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class Fichero {

    public void leerPersonas() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/planesveranoaceniceros/personas.txt");

            if (inputStream == null) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error al importar datos");
                    alert.setHeaderText("Error al importar datos");
                    alert.setContentText("El archivo personas.txt no se encuentra en el path especificado");
                    alert.showAndWait();
                });
                return;
            }

            try (Scanner scPersonas = new Scanner(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                while (scPersonas.hasNextLine()) {
                    String dato = scPersonas.nextLine();
                    String[] datos = dato.split("-");
                    if (datos.length < 7) {
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error al importar datos");
                            alert.setHeaderText("Error al importar datos");
                            alert.setContentText("El formato del archivo personas.txt es incorrecto");
                            alert.showAndWait();
                        });
                        return;
                    }
                    String nombre = datos[0];
                    String mote = datos[1];
                    String password = datos[2];
                    int edad = Integer.parseInt(datos[3]);
                    String dniNum = datos[4];
                    String dniNumberStr = dniNum.substring(0, dniNum.length() - 1);
                    int dniNumber = Integer.parseInt(dniNumberStr);
                    boolean organizador = Boolean.parseBoolean(datos[5]);
                    boolean participante = Boolean.parseBoolean(datos[6]);

                    Dni dni = new Dni(dniNumber);
                    Persona persona = new Persona(nombre, mote, edad, dni, password, organizador, participante);
                    PlanesVeranoACeniceros.personas.agregarPersona(persona);
                }
            }
        } catch (IllegalArgumentException e) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al importar datos");
                alert.setHeaderText("Error al importar datos");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            });
        }
    }

    public void leerPlanes() {
    try {
        InputStream inputStream = getClass().getResourceAsStream("/planesveranoaceniceros/planes.txt");

        if (inputStream == null) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al importar datos");
                alert.setHeaderText("Error al importar datos");
                alert.setContentText("El archivo planes.txt no se encuentra en el path especificado");
                alert.showAndWait();
            });
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        try (Scanner scPlanes = new Scanner(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            while (scPlanes.hasNextLine()) {
                String dato = scPlanes.nextLine();
                String[] datos = dato.split("-", 6);
                if (datos.length < 5) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error al importar datos");
                        alert.setHeaderText("Error al importar datos");
                        alert.setContentText("El formato del archivo planes.txt es incorrecto");
                        alert.showAndWait();
                    });
                    return;
                }

                String nombre = datos[0];
                LocalDate fecha;
                try {
                    fecha = LocalDate.parse(datos[1], formatter);
                } catch (DateTimeParseException e) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error al importar datos");
                        alert.setHeaderText("Error al importar datos");
                        alert.setContentText("Error al parsear la fecha: " + datos[1]);
                        alert.showAndWait();
                    });
                    return;
                }

                String lugar = datos[2];
                String descripcion = datos[3];
                int numParticipantes = Integer.parseInt(datos[4]);

                HashMap<Dni, Persona> participantes = new HashMap<>();
                    
                if (datos.length > 5 && !datos[5].equals("{}")) {
                    String participantesStr = datos[5].substring(1, datos[5].length() - 1);
                    String[] participantesArray = participantesStr.split(",");

                    for (String participante : participantesArray) {
                        String[] participanteDatos = participante.split("-");
                        if (participanteDatos.length < 7) {
                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error al importar datos");
                                alert.setHeaderText("Error al importar datos");
                                alert.setContentText("El formato de los datos de participantes es incorrecto");
                                alert.showAndWait();
                            });
                            return;
                        }

                        String dniNum = participanteDatos[4];
                        String dniNumberStr = dniNum.substring(0, dniNum.length() - 1);
                        int dniNumber = Integer.parseInt(dniNumberStr);
                        Dni dni = new Dni(dniNumber);
                        Persona persona = PlanesVeranoACeniceros.personas.obtenerPersonas().get(dni);

                        if (persona != null) {
                            participantes.put(dni, persona);
                        } else {
                            String nombrePersona = participanteDatos[0];
                            String motePersona = participanteDatos[1];
                            String passwordPersona = participanteDatos[2];
                            int edadPersona = Integer.parseInt(participanteDatos[3]);
                            boolean organizadorPersona = Boolean.parseBoolean(participanteDatos[5]);
                            boolean participantePersona = Boolean.parseBoolean(participanteDatos[6]);
                            persona = new Persona(nombrePersona, motePersona, edadPersona, dni, passwordPersona, organizadorPersona, participantePersona);
                            PlanesVeranoACeniceros.personas.agregarPersona(persona);
                            participantes.put(dni, persona);
                        }
                    }
                }

                Plan plan = new Plan(nombre, fecha.toString(), lugar, descripcion, participantes, numParticipantes);
                plan.calcularParticipantes();
                PlanesVeranoACeniceros.planes.agregarPlan(plan);
            }
        }
    } catch (IllegalArgumentException e) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al importar datos");
            alert.setHeaderText("Error al importar datos");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        });
    }
}
    
    public void escribirPlanes() {
        String filePath = "src/planesveranoaceniceros/planes.txt";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath), StandardCharsets.UTF_8)) {
            for (Plan plan : PlanesVeranoACeniceros.planes.obtenerPlanes().values()) {
                StringBuilder participantesStr = new StringBuilder("{");
                for (Map.Entry<Dni, Persona> entry : plan.getParticipantes().entrySet()) {
                    Persona persona = entry.getValue();
                    String dniCleaned = persona.getDni().toString().replaceAll("\\s", "");
                    String participante = String.format("%s-%s-%s-%d-%s-%b-%b",
                            persona.getNombre(),
                            persona.getMote(),
                            persona.getPassword(),
                            persona.getEdad(),
                            dniCleaned, // Usar el DNI sin espacios en blanco
                            persona.isOrganizador(),
                            persona.isParticipante());
                    participantesStr.append(participante).append(",");
                }
                if (participantesStr.length() > 1) {
                    participantesStr.setLength(participantesStr.length() - 1);
                }
                participantesStr.append("}");

                
                LocalDate fecha = LocalDate.parse(plan.getFecha(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
              
                String fechaFormateada = fecha.format(formatter);

                String linea = String.format("%s-%s-%s-%s-%d-%s",
                        plan.getNombre(),
                        fechaFormateada,
                        plan.getLugar(),
                        plan.getDescripcion(),
                        plan.getNum(),
                        participantesStr.toString());
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al exportar datos");
                alert.setHeaderText("Error al exportar datos");
                alert.setContentText("No se pudo escribir en el archivo planes.txt: " + e.getMessage());
                alert.showAndWait();
            });
            e.printStackTrace();
        }
    }
}