/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.principal.controladores;

import gui.areas.controladores.ControladorAreas;
import gui.areas.modelos.Area;
import gui.interfaces.IControladorAreas;
import gui.interfaces.IControladorPrincipal;
import gui.personas.modelos.Alumno;
import gui.personas.modelos.Cargo;
import gui.personas.modelos.Profesor;
import gui.principal.vistas.VentanaPrincipal;
import gui.seminarios.modelos.NotaAprobacion;
import gui.trabajos.modelos.AlumnoEnTrabajo;
import gui.trabajos.modelos.GestorTrabajos;
import gui.trabajos.modelos.Rol;
import gui.trabajos.modelos.RolEnTrabajo;
import gui.trabajos.modelos.Trabajo;
import gui.trabajos.vistas.VentanaTrabajos;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ControladorPrincipal implements IControladorPrincipal {
    private VentanaPrincipal ventana;

    /**
     * Constructor
     * Muestra la ventana principal
     */
    public ControladorPrincipal() {
        this.ventana = new VentanaPrincipal(this);
        this.ventana.setLocationRelativeTo(null);
        this.ventana.setVisible(true);
    }

    /**
     * Acción a ejecutar cuando se selecciona el botón Areas
     * @param evt evento
     */                            
    @Override
    public void btnAreasClic(ActionEvent evt) {
        IControladorAreas controlador = new ControladorAreas(this.ventana);
    }

    /**
     * Acción a ejecutar cuando se selecciona el botón Personas
     * @param evt evento
     */                            
    @Override
    public void btnPersonasClic(ActionEvent evt) {
    }

    /**
     * Acción a ejecutar cuando se selecciona el botón Trabajos
     * @param evt evento
     */                            
    @Override
    public void btnTrabajosClic(ActionEvent evt) {
        VentanaTrabajos ventana= new VentanaTrabajos(this.ventana, true);
    }
    
    /**
     * Acción a ejecutar cuando se selecciona el botón Salir
     * @param evt evento
     */                            
    @Override
    public void btnSalirClic(ActionEvent evt) {
        int opcion = JOptionPane.showOptionDialog(null, CONFIRMACION, TITULO_VENTANA, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, this);
        if (opcion == JOptionPane.YES_OPTION) {
            this.ventana.dispose();
            System.exit(0);
        }       
    }
        
    public static void main(String[] args) {
                //Areas de prueba
        Area unArea1= new Area("Redes");
        List<Area> areas=  new ArrayList<>();
        areas.add(unArea1);
        
        //Personas de prueba
        Alumno unAlumno1= new Alumno("Flores", "Hector Adrian", 12000001, "1811372");
        Alumno unAlumno2= new Alumno("Perez", "Juan", 20345667, "1245679");
        
        Profesor unProfesor1= new Profesor("A1", "B1", 1, Cargo.ADG);
        Profesor unProfesor2= new Profesor("A2", "B2", 2, Cargo.TITULAR);
        Profesor unProfesor3= new Profesor("A3", "B3", 3, Cargo.EXTERNO);
        Profesor unProfesor4= new Profesor("A4", "B4", 4, Cargo.ADG);
        Profesor unProfesor5= new Profesor("A5", "B5", 5, Cargo.JTP);
        
        LocalDate fecha1 = LocalDate.of(2019,9,10);
        LocalDate fecha2 = LocalDate.of(2019, 9, 15);
        LocalDate fecha3 = LocalDate.of(2019,11,20);
        LocalDate fecha4 = LocalDate.of(2019, 12, 5);
        //Profesor y Alumnos en trabajo de prueba
        RolEnTrabajo unRolEnTrabajo1= new RolEnTrabajo(unProfesor1, Rol.TUTOR, fecha1);
        RolEnTrabajo unRolEnTrabajo2 = new RolEnTrabajo(unProfesor2, Rol.COTUTOR, fecha1);
        RolEnTrabajo unRolEnTrabajo3 = new RolEnTrabajo(unProfesor3, Rol.JURADO, fecha1);
        RolEnTrabajo unRolEnTrabajo4 = new RolEnTrabajo(unProfesor4, Rol.JURADO, fecha1);
        RolEnTrabajo unRolEnTrabajo5 = new RolEnTrabajo(unProfesor5, Rol.JURADO, fecha1);
        
        AlumnoEnTrabajo unAlumnoEnTrabajo1=new AlumnoEnTrabajo(unAlumno1, fecha1);
        AlumnoEnTrabajo unAlumnoEnTrabajo2=new AlumnoEnTrabajo(unAlumno2, fecha1);
        
        //Listas para los roles
        List<RolEnTrabajo> ret= new ArrayList<>();
        ret.add(unRolEnTrabajo1);ret.add(unRolEnTrabajo2);ret.add(unRolEnTrabajo3);ret.add(unRolEnTrabajo4);ret.add(unRolEnTrabajo5);
        //Lista para alumnos en trabajo
        List<AlumnoEnTrabajo> aet =  new ArrayList<>();
        aet.add(unAlumnoEnTrabajo1);aet.add(unAlumnoEnTrabajo2);
        
        //Todo esta hecho conla suposicion de q los datos estan correctos
//        Trabajo unTrabajo = new Trabajo("Titulo", 4, areas, fecha2, fecha3, ret, aet);
//        unTrabajo.mostrar();
        GestorTrabajos gsTrabajos= GestorTrabajos.instanciar();
        gsTrabajos.nuevoTrabajo("Titulo de Prueba", 4, fecha2, fecha3, areas, ret, aet);
        Trabajo unTrabajo2= gsTrabajos.dameTrabajo("Titulo de Prueba");
//        unTrabajo2.mostrar();
//        unTrabajo2.nuevoSeminario(fecha4, NotaAprobacion.DESAPROBADO, "No llega a lo minimo requerido");
        
        
        IControladorPrincipal controladorPrincipal = new ControladorPrincipal();
    }    
}
