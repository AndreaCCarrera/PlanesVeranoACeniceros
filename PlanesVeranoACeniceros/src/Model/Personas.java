/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Usuario
 */
public class Personas {
    
    private HashMap<Dni,Persona> personas;

    public Personas(HashMap<Dni, Persona> personas) {
        this.personas = personas;
    }
    
    public Personas() {
         this.personas = new HashMap<Dni,Persona>();
     }
    
    public boolean agregarPersona(Persona persona) {
        boolean b = false;
        if (personas.containsKey(persona.getDni())==false) {
            personas.put(persona.getDni(),persona);
            b = true;
        }
        return b;
    }

    public void mostrarPersonas() {
        if (personas.isEmpty()) {
            System.out.println("Aún no hay personas en la lista.");
        } else {
            System.out.println("Personas en la lista:");
            for (Map.Entry<Dni, Persona> entry : personas.entrySet()) {
                Dni dni = entry.getKey();
                Persona persona = entry.getValue();
                System.out.println(persona + ": " + dni);
            }
        }
    }
    
     public Persona buscarPersona(Dni dni) {
        
        Persona persona = null;
        if (personas.containsKey(dni)==true) {
           for (Map.Entry<Dni, Persona> entry : personas.entrySet()) {
                if(dni.equals(entry.getKey())){
                    persona = entry.getValue();
                }
           }
           
        }
        return persona;
    }
     
     public boolean borrarPersona(Dni dni) {
        boolean b = false;
        if (personas.containsKey(dni)) {
            personas.remove(dni);
            b = true;
       
        }
        return b;
}
     
     public boolean modificarPersona(Persona persona) {
        boolean b = false;
        if (personas.containsKey(persona.getDni())) {
            personas.put(persona.getDni(), persona);
            b = true;
        
        }
        return b;
        
    }
     
     public HashMap<Dni, Persona> obtenerPersonas() {
         return this.personas;
    }


}
     




