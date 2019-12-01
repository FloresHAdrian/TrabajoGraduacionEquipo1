/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.seminarios.controladores;

import gui.interfaces.IControladorAMSeminario;
import gui.interfaces.IControladorSeminarios;
import gui.interfaces.IGestorSeminarios;
import gui.interfaces.IGestorTrabajos;
import gui.seminarios.modelos.GestorSeminarios;
import gui.seminarios.modelos.ModeloTablaSeminarios;
import gui.seminarios.modelos.Seminario;
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
    
    //Sierven para manejar la tabla de seminarios
    private String operacion;
    private int seminarioSeleccionado;
    
    /**
     * Constructor
     * Sirve para mostrar la ventana se seminarios en forma modal
     * @param ventanaPadre ventana padre (VentanaTrabajos en este caso)
     * @param titulo Titulo del trabajo al cual se agregara el seminario
     */
    public ControladorSeminarios(JDialog ventanaPadre,String titulo){
        this.titulo=titulo;
        //Instancio el GestorSeminarios para poder cargar la lista de seminarios de todos los trabajos desde el Archivo
        IGestorSeminarios gsSeminarios = GestorSeminarios.instanciar();
                
        this.ventana= new VentanaSeminarios(ventanaPadre, this);
        this.ventana.setLocationRelativeTo(null);
        this.ventana.verTxtNombreTrabajo().setText(titulo);
        this.ventana.verTxtNombreTrabajo().setEditable(false);
        this.ventana.setTitle(TITULO);
        this.ventana.setVisible(true);
        
    }

    /**
     * Accion a ejecutar cuando se selecciona el boton Nuevo Seminario
     * @param evt 
     */
    @Override
    public void btnNuevoSeminarioClic(ActionEvent evt) {
        JTable tablaSeminarios=this.ventana.verTablaSeminarios();
        this.seminarioSeleccionado = tablaSeminarios.getSelectedRow();
        this.operacion=OPERACION_ALTA;
        IControladorAMSeminario controlador= new ControladorAMSeminario(this.ventana,null,titulo);

    }

    /**
     * Accion a ejecutar cuando se selecciona el boton Modificar
     * @param evt 
     */
    @Override
    public void btnModificarSeminarioClic(ActionEvent evt) {
        Seminario unSeminario= this.obtenerSeminarioSeleccionadao();
        GestorTrabajos gsT= GestorTrabajos.instanciar();
        if(unSeminario != null){//Si tiene seminario seleccionado
            IControladorAMSeminario controlador= new ControladorAMSeminario(this.ventana, unSeminario, titulo);
        }
    }

    /**
     * Accion a ejecutar cuando se selecciona el boton Volver
     * @param evt 
     */
    @Override
    public void btnVolverClic(ActionEvent evt) {
        this.ventana.dispose();
    }

    /**
     * Accion a ejecutar cuando la ventana gana foco
     * @param evt 
     */
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

        JTable tablaSeminarios = this.ventana.verTablaSeminarios();
        if (tablaSeminarios.getModel() instanceof ModeloTablaSeminarios)   //2 y 3: se vuelve de la ventana AMArea, o de borrar un área
            this.seleccionarSeminarioEnTabla(tablaSeminarios,titulo);
        else  //1: se viene de la ventana principal
            this.inicializarTablaSeminarios(tablaSeminarios,titulo);
        
        this.operacion = OPERACION_NINGUNA;
        

        GestorTrabajos gsT= GestorTrabajos.instanciar();
        JButton btnModificar= this.ventana.verBtnModificar();
        if(!gsT.dameTrabajo(titulo).tieneSeminarios()){//Si el trabajo no tien seminarios deshabilito el boton Modificar
            btnModificar.setEnabled(false);
        }
        else{
            btnModificar.setEnabled(true);
        }
            
    }
    
    /**
     * Meotod para que se selecionar una fila de la tabla seguna la operacion ejecutada
     * @param tablaSeminarios
     * @param titulo 
     */
    private void seleccionarSeminarioEnTabla(JTable tablaSeminarios, String titulo){
        IGestorSeminarios gs = GestorSeminarios.instanciar();
        IGestorTrabajos gsTrabajos = GestorTrabajos.instanciar();
        Trabajo elTrabajo= gsTrabajos.dameTrabajo(titulo);
        ModeloTablaSeminarios mts = (ModeloTablaSeminarios)tablaSeminarios.getModel();
        
        if(this.operacion.equals(OPERACION_ALTA)){//se vuelve de la ventana AMSeminario
            if(elTrabajo.verUltimoSeminario() == -1){//No se creo ningun seminario
                if(this.seminarioSeleccionado != -1){
                    tablaSeminarios.setRowSelectionInterval(this.seminarioSeleccionado, this.seminarioSeleccionado);
                }
            }else{//Se creo un seminario: se lo selecciona
                mts.fireTableDataChanged();//Se refresca la tabla
                tablaSeminarios.setRowSelectionInterval(elTrabajo.verUltimoSeminario(), elTrabajo.verUltimoSeminario());
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
    
    /**
     * Metodo para inicializar la tabla de seminarios por primera vez
     * @param tablaSeminarios
     * @param titulo 
     */
    private void inicializarTablaSeminarios(JTable tablaSeminarios,String titulo){
        ModeloTablaSeminarios mts = new ModeloTablaSeminarios(titulo);
        tablaSeminarios.setModel(mts);
        
        if(mts.getRowCount() > 0 ){
            this.seminarioSeleccionado = 0;
            tablaSeminarios.setRowSelectionInterval(this.seminarioSeleccionado, this.seminarioSeleccionado);
        }
        else{//Si no hay filas, no hay seminarios
            this.seminarioSeleccionado = -1;
        }
        
        //Le doy el ancho a las colummnas
        tablaSeminarios.getColumn(ModeloTablaSeminarios.COLUMNA_FECHA).setPreferredWidth(25);      
        tablaSeminarios.getColumn(ModeloTablaSeminarios.COLUMNA_NOTA).setPreferredWidth(25); 
        tablaSeminarios.getColumn(ModeloTablaSeminarios.COLUMNA_OBSERVACIONES).setPreferredWidth(195);
    }
    
    /**
     * Metodo para obtener el seminario seleccionado en la tabla si es que hay
     * @return Seminario: El seminario seleccionado o null
     */
    private Seminario obtenerSeminarioSeleccionadao(){
        JTable tablaSeminarios = this.ventana.verTablaSeminarios();
        if(tablaSeminarios.getSelectedRow() != -1){//La tabla tienen filas o sea tiene seminarios
            ModeloTablaSeminarios mts = (ModeloTablaSeminarios)tablaSeminarios.getModel();
            this.seminarioSeleccionado= tablaSeminarios.getSelectedRow();
            return mts.obtenerSeminario(tablaSeminarios.getSelectedRow());
        }
        else{//No hay seminarios
            this.seminarioSeleccionado = -1;
            return null;
        }
    }
    
}
