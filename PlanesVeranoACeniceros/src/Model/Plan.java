/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Usuario
 */
public class Plan {
 
    private StringProperty nombre;
    private StringProperty fecha;
    private StringProperty lugar;
    private StringProperty descripcion;
    private HashMap<Dni,Persona> participantes;
    private IntegerProperty num;
    
    public Plan(String nombre, String fecha, String lugar, String descripcion, HashMap<Dni, Persona> participantes, Integer num) {
    this.nombre = new SimpleStringProperty(nombre);
    this.fecha = new SimpleStringProperty(fecha);
    this.lugar = new SimpleStringProperty(lugar);
    this.descripcion = new SimpleStringProperty(descripcion);
    this.participantes = participantes;
    this.num = new SimpleIntegerProperty(num);
    calcularParticipantes();
    }
       
    public Plan(String nombre, String fecha, String lugar, String descripcion) {
    this.nombre = new SimpleStringProperty(nombre);
    this.fecha = new SimpleStringProperty(fecha);
    this.participantes = new HashMap <Dni, Persona>();
    this.lugar = new SimpleStringProperty(lugar);
    this.descripcion = new SimpleStringProperty(descripcion);
    this.num = new SimpleIntegerProperty(0);
    }
    
    public Plan(String nombre, String fecha, String lugar, String descripcion, Integer num) {
    this.nombre = new SimpleStringProperty(nombre);
    this.fecha = new SimpleStringProperty(fecha);
    this.participantes = new HashMap <Dni, Persona>();
    this.lugar = new SimpleStringProperty(lugar);
    this.descripcion = new SimpleStringProperty(descripcion);
    this.num = new SimpleIntegerProperty(num);
    }
    
    
    public Plan() {
    this.nombre = new SimpleStringProperty();
    this.fecha = new SimpleStringProperty();
    this.lugar = new SimpleStringProperty();
    this.descripcion = new SimpleStringProperty();
    this.participantes = new HashMap <Dni, Persona>();
    this.num = new SimpleIntegerProperty();
       
}

    public String getNombre() {
        return this.nombre.get();
    }

    public String getFecha() {
         return this.fecha.get();
    }

    public String getLugar() {
         return this.lugar.get();
    }
    
    public String getDescripcion() {
         return this.descripcion.get();
    }

    public HashMap<Dni, Persona> getParticipantes() {
        return participantes;
    }

    public int getNum() {
         return this.num.get();
    }

    public void setNombre(String nombre) {
         this.nombre.set(nombre);
    }

    public void setFecha(String fecha) {
         this.fecha.set(fecha);
    }

    public void setLugar(String lugar) {
         this.lugar.set(lugar);
    }
    
    public void setDescripcion(String descripcion) {
         this.descripcion.set(descripcion);
    }

    public void setParticipantes(HashMap<Dni, Persona> participantes) {
        this.participantes = participantes;
    }

    public void setNum(int num) {
         this.num.set(num);
    }
    
     public StringProperty Nombre(){
        return this.nombre;
    }
    
    public StringProperty Fecha(){
        return this.fecha;
    }
    
    public StringProperty Lugar(){
         return this.lugar;
    }
    
    public StringProperty Descripcion(){
        return this.descripcion;
    }
    
    public SimpleStringProperty Num(){
         return new SimpleStringProperty(Integer.toString(this.num.get()));
    }
    
    
    public boolean agregarPersona(Persona persona) {
        boolean b = false;
        if (participantes.containsKey(persona.getDni())==false) {
            participantes.put(persona.getDni(),persona);
            b = true;
            calcularParticipantes();
        }
        return b;
    }

    public void mostrarPersonas() {
        if (participantes.isEmpty()) {
            System.out.println("Aún no hay personas en la lista.");
        } else {
            System.out.println("Personas en la lista:");
            for (Map.Entry<Dni, Persona> entry : participantes.entrySet()) {
                Dni dni = entry.getKey();
                Persona persona = entry.getValue();
                System.out.println(persona + ": " + dni);
            }
        }
    }
    
    public boolean borrarPersona(Dni dni) {
        boolean b = false;
        if (participantes.containsKey(dni)) {
            participantes.remove(dni);
            b = true;
            calcularParticipantes();
       
        }
        return b;
}
     
     public boolean modificarPersona(Persona persona) {
        boolean b = false;
        if (participantes.containsKey(persona.getDni())) {
            participantes.put(persona.getDni(), persona);
            b = true;
            calcularParticipantes();
        
        }
        return b;
        
    }
     
     public boolean buscarPersona(Persona persona) {
        boolean b = false;
        if (participantes.containsKey(persona.getDni())) {
            b = true;
                  
        }
        return b;
        
    }
     
     public HashMap<Dni, Persona> obtenerPersonas() {
         return this.participantes;
    }
    
    public void calcularParticipantes(){
        int cont = 0;
        if (participantes.isEmpty()) {
            num.set(cont);
        } else {
            for (Map.Entry<Dni, Persona> entry : participantes.entrySet()) {
                cont++;                
            }
        }
        num.set(cont);
    }

    @Override
    public String toString() {
        return "Plan{" + "nombre=" + nombre + ", fecha=" + fecha + ", lugar=" + lugar + ", participantes=" + participantes + ", num=" + num + '}';
    }
     
}


