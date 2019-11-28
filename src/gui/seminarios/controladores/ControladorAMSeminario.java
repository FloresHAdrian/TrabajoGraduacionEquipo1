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
import gui.seminarios.modelos.ModeloComboNota;
import gui.seminarios.modelos.NotaAprobacion;
import gui.seminarios.modelos.Seminario;
import gui.seminarios.vistas.VentanaAMSeminario;

import gui.trabajos.modelos.GestorTrabajos;
import gui.trabajos.modelos.Trabajo;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class ControladorAMSeminario implements IControladorAMSeminario{
      private VentanaAMSeminario ventana;
      private Seminario seminario;
      private String titulo;
   

    public ControladorAMSeminario(Dialog VentanaPadre, Seminario seminario, String titulo) {
        this.titulo= titulo;
        this.ventana = new VentanaAMSeminario(this, VentanaPadre);
        this.seminario = seminario;
        
        if(seminario != null){
            ventana.setTitle(IControladorSeminarios.MODIFICAR);
         Date date = Date.from(seminario.verFechaExposicion().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

           ventana.verFechaExposicion().setDate(date);
           ventana.verFechaExposicion().setEnabled(false);
            ventana.verTxtObservaciones().setText(seminario.verObservaciones());
            
        }
        this.inicializarComboNota(this.ventana.verComboNota());
        ventana.setTitle(IControladorSeminarios.NUEVO);
        
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        
        
//        this.inicializarComboNota(this.ventana.verComboNota());
      
    }
    
    

    @Override
    public void btnGuardarClic(ActionEvent evt) {
        this.guardar();
        
    }
    
    public void guardar() {
      
        LocalDate fecha=null;
        String observaciones = this.ventana.verTxtObservaciones().getText().trim();
        NotaAprobacion nota = (NotaAprobacion) this.ventana.verComboNota().getSelectedItem();
        
 
        
        if (this.ventana.verFechaExposicion().getCalendar() != null) {
            Date date = this.ventana.verFechaExposicion().getCalendar().getTime();
        
            fecha = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            System.out.println("Convierte el date a localdate");
        }
        
//        LocalDate fecha= (LocalDate)this.ventana.verFecha();
        IGestorTrabajos gestorT = GestorTrabajos.instanciar();
        Trabajo unTrabajo= gestorT.dameTrabajo(titulo);
        IGestorSeminarios gestor = GestorSeminarios.instanciar();
        String resultado;

        if (seminario==null) {
     
            try{
 
                System.out.println(observaciones);
                System.out.println(nota);
                System.out.println(fecha);
                System.out.println(unTrabajo.nuevoSeminario(fecha, nota, observaciones));
                resultado = unTrabajo.nuevoSeminario(fecha, nota, observaciones);
             
            }catch(NullPointerException n){
//                ban=1;
              resultado = IGestorTrabajos.SEMINARIO_ERROR;
//              JOptionPane.showMessageDialog(null, "La Fecha no es Correcta");
                
            }
            
        } else {
            resultado = gestorT.dameTrabajo(titulo).modificarSeminario(seminario, nota, observaciones);
        }
        try{
            if (resultado.equals(IGestorTrabajos.SEMINARIO_EXITO)) {
                this.ventana.dispose();
            } else {
                gestorT.cancelar();
                JOptionPane.showMessageDialog(null, resultado, IControladorSeminarios.TITULO, JOptionPane.ERROR_MESSAGE);
            }
        }catch(Exception e){
            
                    e.printStackTrace();
                    }
        }
    

    @Override
    public void btnCancelarClic(ActionEvent evt) {
        IGestorTrabajos gestorT= GestorTrabajos.instanciar();
        gestorT.cancelar();
        this.ventana.dispose();
    }

//    @Override
//    public void comboNotaCambiarSeleccion(ActionEvent evt) {
//                        int index = combo.getSelectedIndex();
//                if(ultimoIndiceSeleccionado != 0 && index == 0){
//                        int opc = JOptionPane.showConfirmDialog(this, "¿Realmente desea cambiar de marca?",
//                                "Cambio de marca", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE );
//                       
//                        if(opc == JOptionPane.NO_OPTION){
//                                combo.setSelectedIndex(ultimoIndiceSeleccionado);
//                        } else{
//                                ultimoIndiceSeleccionado = index;
//                        }
//                } else{
//                        ultimoIndiceSeleccionado = index;
//                }
//        }

    @Override
    public void comboNotaCambiarSeleccion(ActionEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void txtObservacionesPresionarTecla(KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!Character.isLetter(c)) { //sólo se aceptan letras, Enter, Del, Backspace y espacio
            switch (c) {
                case KeyEvent.VK_ENTER:  //cuando se pulse Enter
                    this.guardar();
                    break;
                case KeyEvent.VK_BACK_SPACE:
                case KeyEvent.VK_DELETE:
                case KeyEvent.VK_SPACE:
                    break;
                default:
                    evt.consume(); //consume el evento para que no sea procesado por la fuente
            }
        }
    }

        
    private void inicializarComboNota(JComboBox comboNota) {
        ModeloComboNota mcn = new ModeloComboNota();
        comboNota.setModel(mcn);
        if(this.seminario!=null)
        mcn.seleccionarNotaAprobacion(seminario.verNotaAprobacion());

    }
   
    

    }
    

