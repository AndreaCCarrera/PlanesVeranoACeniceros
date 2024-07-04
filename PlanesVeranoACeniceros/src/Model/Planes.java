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
public class Planes {
    
     private HashMap<String,Plan> planes;
     
     public Planes(HashMap<String,Plan> planes) {
        this.planes = planes;
    }
    
    public Planes() {
         this.planes = new HashMap<String,Plan>();
     }
    
    public boolean agregarPlan(Plan plan) {
        boolean b = false;
        if (planes.containsKey(plan.getNombre())==false) {
            planes.put(plan.getNombre(),plan);
            b = true;
        }
        return b;
    }

    public void mostrarPlanes() {
        if (planes.isEmpty()) {
            System.out.println("Aún no hay personas en la lista.");
        } else {
            System.out.println("Planes en la lista:");
            for (Map.Entry<String, Plan> entry : planes.entrySet()) {
                String nombre = entry.getKey();
                Plan plan = entry.getValue();
                System.out.println(plan + ": " + nombre);
            }
        }
    }
    
     public Plan buscarPlan(String nombre) {
        
        Plan plan = null;
        if (planes.containsKey(nombre)==true) {
           for (Map.Entry<String, Plan> entry : planes.entrySet()) {
                if(nombre.equals(entry.getKey())){
                    plan = entry.getValue();
                }
           }
           
        }
        return plan;
    }
     
     public boolean borrarPlan(String nombre) {
        boolean b = false;
        if (planes.containsKey(nombre)) {
            planes.remove(nombre);
            b = true;
       
        }
        return b;
}
     
     public boolean modificarPlan(Plan plan) {
        boolean b = false;
        if (planes.containsKey(plan.getNombre())) {
            planes.put(plan.getNombre(), plan);
            b = true;
            
        
        }
        return b;
        
    }
     
     public HashMap<String, Plan> obtenerPlanes() {
         return this.planes;
    }
}
    

