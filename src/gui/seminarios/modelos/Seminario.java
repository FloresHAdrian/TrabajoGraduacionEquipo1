/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.seminarios.modelos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Seminario implements Comparable<Seminario>{
    private LocalDate fechaExposicion;
    private NotaAprobacion notaAprobacion;
    private String observaciones;

    /**
     * Constructor
     * @param fechaExposicion fecha de exposición del seminario
     * @param notaAprobacion condición del seminario (aprobado, desaprobado, etc)
     * @param observaciones observaciones sobre la condición
     */
    public Seminario(LocalDate fechaExposicion, NotaAprobacion notaAprobacion, String observaciones) {
        this.fechaExposicion = fechaExposicion;
        this.notaAprobacion = notaAprobacion;
        this.observaciones = observaciones;
        
    }
       
    /**
     * Devuelve la fecha de exposición del seminario
     * @return LocalDate  - fecha de exposición del seminario
     */    
    public LocalDate verFechaExposicion() {
        return this.fechaExposicion;
    }  
    
    /**
     * Devuelve la nota de aprobación del seminario
     * @return NotaAprobacion  - nota de aprobación del seminario
     */        
    public NotaAprobacion verNotaAprobacion() {
        return this.notaAprobacion;
    }

    /**
     * Asigna la nota de aprobación
     * @param notaAprobacion nota de aprobación
     */
    public void asignarNotaAprobacion(NotaAprobacion notaAprobacion) {
        if (notaAprobacion != null)
            this.notaAprobacion = notaAprobacion;
    }
            
    /**
     * Devuelve las observaciones del seminario
     * @return String  - observaciones del seminario
     */
    public String verObservaciones() {
        return this.observaciones;
    }

    /**
     * Asigna las observaciones del seminario
     * @param observaciones observaciones
     */
    public void asignarObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.fechaExposicion);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Seminario other = (Seminario) obj;
        if (!Objects.equals(this.fechaExposicion, other.fechaExposicion)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Seminario{" + "fechaExposicion=" + fechaExposicion + ", notaAprobacion=" + notaAprobacion + ", observaciones=" + observaciones + '}';
    }
    
        /**
     * Metodo para mostrar el seminario
     */
    public void mostrar(){
        //Formateo la fecha para presentarla en pantalla de la forma dd/mm/aaaa
        DateTimeFormatter patron=DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaExpoFormateada= this.fechaExposicion!=null ? this.fechaExposicion.format(patron):"";
        
        //Como se que se agrega un seminario con una fecha y nota, asumno que no son null, por lo tanto no hago ese control     
        System.out.print("Seminario rendido: "+fechaExpoFormateada);   
        if(notaAprobacion.equals(NotaAprobacion.APROBADO_CO) || notaAprobacion.equals(NotaAprobacion.APROBADO_SO))//Como lo dos son aprobado muestro solo mensaje
            System.out.print("  Nota: Aprobado  ");
        if(notaAprobacion.equals(NotaAprobacion.DESAPROBADO))
            System.out.print("  Nota: Desaprobado");         
        if(observaciones!=null)
            System.out.println("  Observaciones: "+observaciones);
        else
            System.out.println("  Observaciones: -");         
    }

    @Override
    public int compareTo(Seminario s) {
        return this.fechaExposicion.compareTo(s.fechaExposicion);
    }

 
}
