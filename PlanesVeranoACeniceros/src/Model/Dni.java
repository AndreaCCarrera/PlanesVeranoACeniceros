/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Objects;

/**
 *
 * @author Usuario
 */
public class Dni {
    
     private int numero;
    private String letra;
    
    public Dni(){
        this.numero=0;
        this.letra="";
    }
    
    public Dni(int numero){
        this.numero=numero;
        this.letra=generador(numero);
    }
    
    public int getNumero() {
        return numero;
    }
    
    public String getLetra() {
        return letra;
    }
    
    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    public String toString(){
       return(this.numero+" "+this.letra);
     }
    
    public String generador(int numero){
    
     
     String letter[]={"T", "R", "W", "A" , "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};
   
     return letter[numero%23];
    
    }
    
    public boolean comprobador(int numero, String letra){
    
     boolean b = false;
     
     String l[]={"T", "R", "W", "A" , "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};
   
        if(letra.equals(l[numero%23])){

            b=true;
        }
    return b;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dni dni = (Dni) o;
        return numero == dni.numero && Objects.equals(letra, dni.letra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, letra);
    }
}
