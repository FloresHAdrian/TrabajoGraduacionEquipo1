/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.seminarios.controladores;

import gui.interfaces.IControladorSeminarios;
import gui.seminarios.modelos.ModeloTablaSeminarios;
import gui.seminarios.vistas.VentanaSeminarios;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JTable;

/**
 *
 * @author Adrian
 */
public class ControladorSeminarios implements IControladorSeminarios{
    private VentanaSeminarios ventana;
    private String titulo;
    private String operacion;
    
    public ControladorSeminarios(JDialog ventanaPadre,String titulo){
        this.ventana= new VentanaSeminarios(ventanaPadre, this);
        this.ventana.setLocationRelativeTo(null);
        this.ventana.setVisible(true);
        
        this.titulo=titulo;
        System.out.println("TITULO EN EL CONSTRUCTOR"+titulo);
//        ModeloTablaSeminarios modelo = new ModeloTablaSeminarios(titulo);
//        JTable tabla = this.ventana.verTablaSeminarios();
//        tabla.setModel(modelo);
    }

    @Override
    public void btnNuevoSeminarioClic(ActionEvent evt) {
//        JTable tablaSeminarios
//        IControladorAMSeminario controlador= new IControladorAMSeminario(this.ventana);

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void btnModificarSeminarioClic(ActionEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void btnVolverClic(ActionEvent evt) {
        this.ventana.dispose();
    }

    @Override
    public void ventanaGanaFoco(WindowEvent evt) {
                //La ventana gana el foco cuando:
        //  1. Se presiona el botón "Areas" en la ventana principal
	//  2. Se vuelve de la ventana de alta de áreas
        //  3. Se vuelve de borrar un área

        //1.  Implica que la tabla no tiene asignado ModeloTablaAreas
        //    Hay que asignárselo, si tiene filas, hay que seleccionar la primera

        //2. y 3. Implica que la tabla ya tiene asignado ModeloTablaAreas
        //2.  Se puede volver:
            //2.1 Sin haber creado ningún área: seleccionar el área que estaba seleccionada
            //2.2 Habiendo creado un área: seleccionar el área recién creada
            
        //3. Se puede volver:
            //3.1 Sin haber borrado el área: seleccionar el área que estaba seleccionada
            //3.2 Habiendo borrado el área: si hay filas, seleccionar la primera
                
        System.out.println("EL TITULO EN EL WINDOW EVENT ES:"+this.titulo);    
        JTable tablaSeminarios = this.ventana.verTablaSeminarios();
        ModeloTablaSeminarios modelo = new ModeloTablaSeminarios(titulo);
        
        tablaSeminarios.setModel(modelo);
//        if (tablaSeminarios.getModel() instanceof ModeloTablaSeminarios)   //2 y 3: se vuelve de la ventana AMArea, o de borrar un área
//            this.seleccionarSeminarioEnTabla(tablaSeminarios);
//        else  //1: se viene de la ventana principal
//            this.inicializarTablaProfesores(tablaSeminarios);
//        
//        this.operacion = OPERACION_NINGUNA;
    }
    
    private void inicializarTablaSeminarios(String titulo){
        
    }
    
    
    
}
