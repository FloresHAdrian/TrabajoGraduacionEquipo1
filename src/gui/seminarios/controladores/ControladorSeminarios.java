/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.seminarios.controladores;

import gui.interfaces.IControladorSeminarios;
import gui.interfaces.IGestorTrabajos;
import gui.seminarios.modelos.ModeloTablaSeminarios;
import gui.seminarios.modelos.Seminario;
import gui.seminarios.vistas.VentanaAMSeminario;
import gui.seminarios.vistas.VentanaSeminarios;
import gui.trabajos.modelos.GestorTrabajos;
import gui.trabajos.modelos.Trabajo;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
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
    private int seminarioSeleccionado;
    
    public ControladorSeminarios(JDialog ventanaPadre,String titulo){
        this.titulo=titulo;
        this.ventana= new VentanaSeminarios(ventanaPadre, this);
        this.ventana.setLocationRelativeTo(null);
        this.ventana.setTitle(TITULO);
        this.ventana.setVisible(true);
        
        System.out.println("TITULO EN EL CONSTRUCTOR CONTROLADOR SEMINARIOS:  "+this.titulo);
    }

    @Override
    public void btnNuevoSeminarioClic(ActionEvent evt) {
//        JTable tablaSeminarios
        this.operacion=OPERACION_ALTA;
        VentanaAMSeminario ventanaAMSeminario= new VentanaAMSeminario(this.ventana, true);


    }

    @Override
    public void btnModificarSeminarioClic(ActionEvent evt) {
        GestorTrabajos gsT= GestorTrabajos.instanciar();
        JButton btnModificar= this.ventana.verBtnModificar();
        if(!gsT.dameTrabajo("Titulo de Prueba").tieneSeminarios()){
            btnModificar.setEnabled(false);
        }
        else{
            btnModificar.setEnabled(true);
        }
    }

    @Override
    public void btnVolverClic(ActionEvent evt) {
        this.ventana.dispose();
    }

    @Override
    public void ventanaGanaFoco(WindowEvent evt) {
        //La ventana gana el foco cuando:
        //  1. Se presiona el botón "Seminarios" en la ventana principal
	//  2. Se vuelve de la ventana de alta de seminarios
        //  3. Se vuelve de modificar un área

        //1.  Implica que la tabla no tiene asignado ModeloTablaSeminarios
        //    Hay que asignárselo, si tiene filas, hay que seleccionar la primera

        //2. y 3. Implica que la tabla ya tiene asignado ModeloTablaSeminarios
        //2.  Se puede volver:
            //2.1 Sin haber creado ningún seminario: seleccionar el seminario que estaba seleccionada
            //2.2 Habiendo creado un seminario: seleccionar el seminario recién creado
            
        //3. Se puede volver:
            //3.1 Sin haber modificado el seminario: seleccionar el seminario que estaba seleccionada
            //3.2 Habiendo modificado el seminario: si hay filas, seleccionar la primera

        GestorTrabajos gsT= GestorTrabajos.instanciar();
        JButton btnModificar= this.ventana.verBtnModificar();
        if(!gsT.dameTrabajo("Titulo de Prueba").tieneSeminarios()){
            btnModificar.setEnabled(false);
        }
        else{
            btnModificar.setEnabled(true);
        }
            
        JTable tablaSeminarios = this.ventana.verTablaSeminarios();
        if (tablaSeminarios.getModel() instanceof ModeloTablaSeminarios)   //2 y 3: se vuelve de la ventana AMArea, o de borrar un área
            this.seleccionarSeminarioEnTabla(tablaSeminarios,titulo);
        else  //1: se viene de la ventana principal
            this.inicializarTablaSeminarios(tablaSeminarios,titulo);
        
        this.operacion = OPERACION_NINGUNA;
    }
    
    private void seleccionarSeminarioEnTabla(JTable tablaSeminarios, String titulo){
        IGestorTrabajos gsTrabajos = GestorTrabajos.instanciar();
        Trabajo elTrabajo= gsTrabajos.dameTrabajo(titulo);
        ModeloTablaSeminarios mts = (ModeloTablaSeminarios)tablaSeminarios.getModel();
        
        if(this.operacion.equals(OPERACION_ALTA)){//se vuelve de la ventana AMSeminario
            if(elTrabajo.verUltimoSeminario() == -1){//No se creo mimgun seminario
                if(this.seminarioSeleccionado != -1){
                    tablaSeminarios.setRowSelectionInterval(this.seminarioSeleccionado, this.seminarioSeleccionado);
                }
            }else{//Se creo un seminario: se lo selecciona
                mts.fireTableDataChanged();//Se refresca la tabla
                tablaSeminarios.setRowSelectionInterval(this.seminarioSeleccionado, this.seminarioSeleccionado);
            }
            
        }
        else if(this.operacion.equals(OPERACION_MODIFICACION)){//Se vuelve de modificar un seminario
            if(elTrabajo.verUltimoSeminario() == -1){//No modifico un seminario: se selecciona la que estaba seleccionada
                tablaSeminarios.setRowSelectionInterval(this.seminarioSeleccionado, this.seminarioSeleccionado);
            }
            else{//Se modifico un seminario
                mts.fireTableDataChanged();
                tablaSeminarios.setRowSelectionInterval(this.seminarioSeleccionado, this.seminarioSeleccionado);
            }
        }
    }
    
    private void inicializarTablaSeminarios(JTable tablaSeminarios,String titulo){
        ModeloTablaSeminarios mts = new ModeloTablaSeminarios(titulo);
        tablaSeminarios.setModel(mts);
        
        if(mts.getRowCount() > 0 ){
            this.seminarioSeleccionado = 0;
            tablaSeminarios.setRowSelectionInterval(this.seminarioSeleccionado, this.seminarioSeleccionado);
        }
        else{
            this.seminarioSeleccionado = -1;
        }
    }
    
    private Seminario obtenerSeminarioSeleccionadao(){
        JTable tablaSeminarios = this.ventana.verTablaSeminarios();
        if(tablaSeminarios.getSelectedRow() != -1){
            ModeloTablaSeminarios mts = (ModeloTablaSeminarios)tablaSeminarios.getModel();
            this.seminarioSeleccionado= tablaSeminarios.getSelectedRow();
            return mts.obtenerSeminario(tablaSeminarios.getSelectedRow());
        }
        else{
            this.seminarioSeleccionado = -1;
            return null;
        }
    }
    
}
