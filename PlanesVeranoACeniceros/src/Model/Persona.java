/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.HashMap;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Usuario
 */
public class Persona {
    
    private StringProperty nombre;
    private StringProperty mote;
    private StringProperty password;
    private IntegerProperty edad;
    private ObjectProperty dni;
    private boolean organizador;
    private boolean participante; 

    public Persona(String nombre, String mote, int edad, Dni dni, String password, boolean organizador, boolean participante) {
        this.nombre = new SimpleStringProperty(nombre);
        this.mote= new SimpleStringProperty(mote);
        this.password= new SimpleStringProperty(password);
        this.edad = new SimpleIntegerProperty(edad);
        this.dni = new SimpleObjectProperty(dni);
        this.organizador = organizador;
        this.participante = participante;
    }
    
     public Persona(String nombre, String mote, int edad, Dni dni, String password) {
        this.nombre = new SimpleStringProperty(nombre);
        this.mote= new SimpleStringProperty(mote);
        this.password= new SimpleStringProperty(password);
        this.edad = new SimpleIntegerProperty(edad);
        this.dni = new SimpleObjectProperty(dni);
        this.organizador = false;
        this.participante = true;
    }
    
    public Persona() {
        this.nombre = new SimpleStringProperty();
        this.mote = new SimpleStringProperty();
        this.password= new SimpleStringProperty();
        this.edad = new SimpleIntegerProperty();
        this.dni = new SimpleObjectProperty();
        this.organizador= false;
        this.participante = false;
    }
  
   
    public String getPassword() {
        return this.password.get();
    }

    public String getNombre() {
        return this.nombre.get();
    }

    public String getMote() {
        return this.mote.get();
    }

    public int getEdad() {
        return this.edad.get();
    }

    public Dni getDni() {
        return (Dni) this.dni.get();
    }

    public boolean isOrganizador() {
        return organizador;
    }

    public boolean isParticipante() {
        return participante;
    }

    public void setOrganizador(boolean organizador) {
        this.organizador = organizador;
    }

    public void setParticipante(boolean participante) {
        this.participante = participante;
    }
    

    public void setPassword(String password) {
        this.password.set(password);
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public void setMote(String mote) {
        this.mote.set(mote);
    }
      
    public void setEdad(int edad) {
        this.edad.set(edad);
    }

    public void setDni(Dni dni) {
        this.dni.set(dni);
    }

    public StringProperty Nombre(){
        return this.nombre;
    }
    
    public StringProperty Mote(){
        return this.mote;
    }
    
    public StringProperty Edad(){
        return new SimpleStringProperty(Integer.toString(this.edad.get()));
    }
    
    public ObjectProperty Dni(){
        return this.dni;
    }
    
    @Override
    public String toString() {
        return "Persona{" + "nombre=" + nombre + ", apellidos=" + mote + ", edad=" + edad +'}';
    }
    
   public boolean estaApuntado(Plan plan) {
    HashMap<Dni, Persona> participantes = plan.getParticipantes();
    return participantes.containsValue(this);
}
    
}
