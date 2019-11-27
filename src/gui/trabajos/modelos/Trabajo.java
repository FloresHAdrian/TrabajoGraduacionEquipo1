/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.trabajos.modelos;

import gui.seminarios.modelos.Seminario;
import gui.areas.modelos.Area;
import gui.interfaces.IGestorSeminarios;
import gui.interfaces.IGestorTrabajos;
import gui.seminarios.modelos.GestorSeminarios;
import gui.seminarios.modelos.NotaAprobacion;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Trabajo {                 
    private String titulo;
    private int duracion;
    private List<Area> areas = new ArrayList<>();
    private LocalDate fechaPresentacion; //fecha en que se presenta el trabajo al encargado de recibirlo
    private LocalDate fechaAprobacion; //fecha en que se aprueba en comisión la propuesta de trabajo
    private LocalDate fechaFinalizacion; //fecha en la que finaliza el trabajo (los alumnos lo presentan)   
    private List<RolEnTrabajo> ret = new ArrayList<>();
    private List<AlumnoEnTrabajo> aet = new ArrayList<>();
    private List<Seminario> seminarios = new ArrayList<>();
    
    private int ultimoSeminario = - 1;
    //sirve para manejar la tabla tablaSeminarios

    /**
     * Constructor para crear un trabajo nuevo (sin fecha de exposición)
     * @param titulo título del trabajo
     * @param duracion duración del trabajo (en meses)
     * @param areas áreas del trabajo
     * @param fechaPresentacion fecha de presentación de la propuesta de trabajo
     * @param fechaAprobacion fecha en la que se aprobó la propuesta de trabajo
     * @param ret profesores que intervienen en el trabajo con sus respectivos roles (jurado, tutor y/o cotutor)
     * @param aet alumnos que participan en el trabajo
     */
    public Trabajo(String titulo, int duracion, List<Area> areas, LocalDate fechaPresentacion, LocalDate fechaAprobacion, List<RolEnTrabajo> ret, List<AlumnoEnTrabajo> aet) {
        this(titulo, duracion, areas, fechaPresentacion, fechaAprobacion, null, ret, aet);
    }
    
    /**
     * Constructor para crear un trabajo nuevo
     * @param titulo título del trabajo
     * @param duracion duración del trabajo (en meses)
     * @param areas áreas del trabajo
     * @param fechaPresentacion fecha de presentación de la propuesta de trabajo
     * @param fechaAprobacion fecha en la que se aprobó la propuesta de trabajo
     * @param fechaFinalizacion fecha de finalización del trabajo
     * @param ret profesores que intervienen en el trabajo con sus respectivos roles (jurado, tutor y/o cotutor)
     * @param aet alumnos que participan en el trabajo
     */
    public Trabajo(String titulo, int duracion, List<Area> areas, LocalDate fechaPresentacion, LocalDate fechaAprobacion, LocalDate fechaFinalizacion, List<RolEnTrabajo> ret, List<AlumnoEnTrabajo> aet) {
        this.titulo = titulo;
        this.duracion = duracion;
        this.areas = areas;
        this.fechaPresentacion = fechaPresentacion;
        this.fechaAprobacion = fechaAprobacion;
        this.fechaFinalizacion = fechaFinalizacion;
        this.ret = ret;
        this.aet = aet;
    }
        
    /**
     * Devuelve el título del trabajo
     * @return String  - título del trabajo
     */
    public String verTitulo() {
        return this.titulo;
    }

    /**
     * Devuelve la duración del trabajo
     * @return int  - duración del trabajo
     */    
    public int verDuracion() {
        return this.duracion;
    }
    
    /**
     * Devuelve las áreas del trabajo
     * @return List<Area>  - áreas del trabajo
     */            
    public List<Area> verAreas() {
        return this.areas;
    }    

    /**
     * Devuelve la fecha de presentación del trabajo
     * @return LocalDate  - fecha de presentación del trabajo
     */    
    public LocalDate verFechaPresentacion() {
        return this.fechaPresentacion;
    }

    /**
     * Devuelve la fecha de aprobación del trabajo
     * @return LocalDate  - fecha de aprobación del trabajo
     */        
    public LocalDate verFechaAprobacion() {
        return this.fechaAprobacion;
    }

    /**
     * Devuelve la fecha de finalización del trabajo
     * @return LocalDate  - fecha de finalización del trabajo
     */        
    public LocalDate verFechaFinalizacion() {
        return this.fechaFinalizacion;
    }

    /**
     * Asigna la fecha de finalización del trabajo
     * @param fechaFinalizacion fecha de finalización del trabajo
     */
    public void asignarFechaFinalizacion(LocalDate fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }     
        
    /**
     * Devuelve el último profesor con el rol especificado (TUTOR | COTUTOR)
     * El último tutor o cotutor es el que tiene su fecha de finalización nula
     * Si no hay cotutor, devuelve null
     * @param rol rol que cumple el profesor
     * @return Profesor  - profesor con el rol especificado
     */
//    public Profesor verTutorOCotutor(Rol rol) {
//    }

    /**
     * Devuelve el jurado del trabajo, ordenado por apellido y nombre
     * El jurado es el último, o sea quienes tienen fecha de finalización nula
     * @return List<Profesor>  - lista con el jurado del trabajo
     */
//    public List<Profesor> verJurado() {
//    }
    
    /**
     * Devuelve la lista de profesores con sus roles en el trabajo
     * La lista viene ordenada de la siguiente forma:
     * 1. Primero los tutores, luego los cotutores y luego el jurado
     * 2. Si hay 2 o más tutores (o 2 o más cotutores), se ordenan por la fecha desde la que empezaron en el proyecto
     * 3. En el caso del jurado, se ordenan por la fecha en que empezaron en el proyecto, y luego por apellido y nombre
     * @return List<RolEnTrabajo>  - lista de profesores con sus roles en el trabajo
     */
    public List<RolEnTrabajo> verProfesoresConRoles() {
        return this.ret;
    }
    
    /**
     * Devuelve la lista de alumnos del trabajo (los que actualmente participan y los que no)
     * La lista viene ordenada de la siguiente forma:
     * 1. Los alumnos se ordenan por la fecha en que comenzaron en el proyecto, y luego por apellido y nombre
     * @return List<AlumnoEnTrabajo>  - lista de alumnos del trabajo (los que actualmente participan y los que no)
     */
    public List<AlumnoEnTrabajo> verAlumnos() {
        return this.aet;
    }    
    
    /**
     * Devuelve la lista de los alumnos que actualmente participan del trabajo (sin fecha de finalización)
     * La lista viene ordenada de la siguiente forma:
     * 1. Los alumnos se ordenan por la fecha en que comenzaron en el trabajo, y luego por apellido y nombre
     * @return List<AlumnoEnTrabajo>  - lista de los alumnos que actualmente participan del trabajo
     */
    public List<AlumnoEnTrabajo> verAlumnosActuales() {
        List<AlumnoEnTrabajo> alumnosActuales =  new ArrayList<>();
        for(AlumnoEnTrabajo a: aet){
            if(a.verFechaHasta()!=null)
                alumnosActuales.add(a);
        }
        return alumnosActuales;
    }        
    
    /**
     * Devuelve la cantidad de profesores con el rol especificado en el trabajo
     * @param rol rol de los profesores
     * @return int  - cantidad de profesores con el rol especificado en el trabajo
     */
//    public int cantidadProfesoresConRol(Rol rol) {
//    }
    
    /**
     * Devuelve la cantidad de alumnos (actuales y no) en el trabajo
     * @return int  - cantidad de alumnos en el trabajo
     */
    public int cantidadAlumnos() {
        return this.aet.size();
    }    
    
    /**
     * Devuelve la cantidad de seminarios que tiene el trabajo
     * @return int  - cantidad de seminarios que tiene el trabajo
     */
    public int cantidadSeminarios() {
        return this.seminarios.size();
    }
    
    /**
     * Informa si el trabajo tiene presentado seminarios
     * @return boolean  - true si el trabajo tiene al menos un seminario, false en caso contrario
     */
    public boolean tieneSeminarios() {
        return !this.seminarios.isEmpty();
    }
                
    /**
     * Informa si el profesor especificado participa en el trabajo
     * @param profesor profesor a buscar
     * @return boolean  - true si el profesor participa en el trabajo, false en caso constrario
     */
//    public boolean tieneEsteProfesor(Profesor profesor) {
//    }
    
    /**
     * Agrega el profesor con su rol al trabajo
     * No puede haber 2 profesores iguales en el trabajo
     * @param rolEnTrabajo 
     */
    public void agregarRolEnTrabajo(RolEnTrabajo rolEnTrabajo) {
    }
            
    /**
     * Agrega el seminario especificado 
     * Este método se usa cuando se leen los seminarios del archivo
     * @param seminario seminario a agregar
     */
    public void agregarSeminario(Seminario seminario) {
    }
    
    /**
     * Informa si el trabajo tiene o no el seminario especificado
     * @param seminario seminario a buscar
     * @return boolean  - true si el trabajo tiene el seminario especificado, false en caso contrario
     */
    public boolean tieneEsteSeminario(Seminario seminario) {
        return seminarios.contains(seminario);
    }
    
    /**
     * Crea un seminario siempre y cuando no haya otro con la misma fecha
     * Y que la fecha de exposición del seminario sea posterior a la de aprobación del trabajo
     * Si el seminario está aprobado con observaciones, o desaprobado, se deben especificar las observaciones
     * @param fechaExposicion fecha de exposición del seminario
     * @param notaAprobacion nota de aprobación del seminario
     * @param observaciones observaciones del seminario
     * @return String  - cadena con el resultado de la operación (TRABAJO_FINALIZADO | ERROR_FECHA_EXPOSICION | DUPLICADOS | ERROR | ERROR_OBSERVACIONES | EXITO)
     */
    public String nuevoSeminario(LocalDate fechaExposicion, NotaAprobacion notaAprobacion, String observaciones) {
        GestorSeminarios gsSeminarios= GestorSeminarios.instanciar();
        String estado= gsSeminarios.validarSeminario(fechaExposicion, notaAprobacion, observaciones);
        if(estado.equals(IGestorSeminarios.ERROR)){
            return estado;
        }
        if(estado.equals(IGestorSeminarios.ERROR_OBSERVACIONES)){
            return estado;
        }
        if(estado.equals(IGestorSeminarios.DATOS_CORRECTOS)){
            if(fechaExposicion.isAfter(this.fechaAprobacion)){
                Seminario unSeminario= new Seminario(fechaExposicion, notaAprobacion, observaciones);
                if(!seminarios.contains(unSeminario)){
                    seminarios.add(unSeminario);
                    return IGestorSeminarios.EXITO;
                }
                else{
                    return IGestorSeminarios.ERROR_FECHA_EXPOSICION;
                }
            }
            
        }
        if(estado.equals(IGestorSeminarios.ERROR)){
            return estado;
        }
//        estado.equals(IGestorSeminarios.ERROR_OBSERVACIONES)){
        return estado;
//        }
    }
    
    /**
     * Modifica un seminario siempre y cuando no haya otro con la misma fecha
     * Si el seminario está aprobado con observaciones, o desaprobado, se deben especificar las observaciones
     * @param seminario seminario a modificar
     * @param notaAprobacion nota de aprobación del seminario
     * @param observaciones observaciones del seminario
     * @return String  - cadena con el resultado de la operación (ERROR | ERROR_OBSERVACIONES | EXITO)
     */    
    public String modificarSeminario(Seminario seminario, NotaAprobacion notaAprobacion, String observaciones) {

        if (tieneEsteSeminario(seminario)) {
            GestorSeminarios gsSeminarios = GestorSeminarios.instanciar();
            String estado = gsSeminarios.validarSeminario(notaAprobacion, observaciones);
            if (estado.equals(IGestorSeminarios.DATOS_CORRECTOS)) {
                for (Seminario s : this.seminarios) {
                    if (s.equals(seminario)) {
                        s.asignarNotaAprobacion(notaAprobacion);
                        s.asignarObservaciones(observaciones);
                    }
                }
                return IGestorTrabajos.SEMINARIO_EXITO;
            }
            return estado;

        }
        return IGestorTrabajos.SEMINARIO_INEXISTENTE;
    }

    
    /**
     * Devuelve la posición del último seminario agregado/modificado
     * Sirve para manejar la tabla tablaSeminarios
     * Cada vez que se agrega/modifica un seminario, este valor toma la posición del seminario agregado/modificado en el ArrayList
     * @return int  - posición del último seminario agregado/modificado
     */
    public int verUltimoSeminario() {
        return this.ultimoSeminario;
    }
    
    /**
     * Devuelve los seminarios ordenados según su fecha de exposición
     * @return List<Seminario>  - lista de seminarios ordenada según la fecha de exposición
     */
    public List<Seminario> verSeminarios() {
        return this.seminarios;
    }
        
    /**
     * Informa si el trabajo está o no finalizado
     * @return boolean  - true si el trabajo está finalizado, false en caso contrario
     */
//    public boolean estaFinalizado() {
//    }

    /**
     * Cancela el agregado/modificación del seminario
     * Sirve para manejar la tabla de seminarios
     */
    public void cancelar() {
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="Metodo Mostrar">
    /**
     * Metodo para mostrar la informacion de un Trabajo
     */
    public void mostrar(){
        int bandera1=1;         //
        int bandera2=1;         //Banderas que utilizo para mostrar un mensaje una sola vez
        int bandera3=1;         //
        
        //Formateo la fecha para mostrarla en la fomra dd/mm/aaaa
        DateTimeFormatter patron=DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaPreFormateada= this.fechaPresentacion.format(patron);
        String fechaAprobFormateada=this.fechaAprobacion!=null ? this.fechaAprobacion.format(patron):"";
        
        System.out.println("\n\n");
        System.out.println("Titulo: "+titulo.toUpperCase());
        System.out.println("Duracion: "+duracion+" meses");
        System.out.println("Fecha Presentacion: "+fechaPreFormateada);
        
        if(fechaAprobacion!=null)//Cuando se crea un trabajo la fechaAprobacion puede ser nula
            System.out.println("Fecha Aprobacion: "+fechaAprobFormateada);
        else
            System.out.println("Fecha Aprobacion: -");
        
        System.out.println("\nAreas");
        System.out.println("-------------------------------");
        for(Area a: areas)
            System.out.println(a);
        
        if(seminarios!=null && !seminarios.isEmpty()){//Si la lista de seminarios esta vacia no la muestro
            System.out.println("-------------------------------");
//            Comparator<Seminario> compSeminario = (s1 ,s2) -> s2.getFechaExposicion().compareTo(s1.getFechaExposicion());
//            Collections.sort(listSeminario, compSeminario);
            
            for(Seminario s:seminarios){
                s.mostrar();
            }
        
        }
   
        System.out.println("\nAlumnos");
        System.out.println("-------------------------------");
        for(AlumnoEnTrabajo a: aet){
            System.out.println(a.verAlumno());
        }
        System.out.println("");
        
        for(RolEnTrabajo p:ret){ //Uso 3 "for" diferentes para mostrar los elementos correspondientes en la lista
            if(p.verRol().equals(Rol.TUTOR)){
                if(bandera1==1){
                    System.out.println("Tutor");
                    System.out.println("-------------------------------");
                    bandera1=0;
                }
                System.out.println(p.verProfesor());
            }
        }
        for(RolEnTrabajo p:ret){
            if(p.verRol().equals(Rol.COTUTOR)){
                if(bandera2==1){
                    System.out.println("\nCotutor");
                    System.out.println("-------------------------------");
                    bandera2=0;
                }
                System.out.println(p.verProfesor());
            }
        }
        for(RolEnTrabajo p:ret){
            if(p.verRol().equals(Rol.JURADO)){
                if(bandera3==1){
                    System.out.println("\nJurado");
                    System.out.println("-------------------------------");
                    bandera3=0;
                }
                System.out.println(p.verProfesor());
            }
        }
//        System.out.println("");
    }
//</editor-fold>
}
