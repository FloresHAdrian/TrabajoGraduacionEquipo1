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
        this.ventana = new VentanaAMSeminario(this, VentanaPadre);
        this.seminario = seminario;
        this.titulo= titulo;
        ventana.setTitle(IControladorSeminarios.NUEVO);
        
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        
        this.inicializarComboNota(this.ventana.verComboNota());
      
    }
    
    

    @Override
    public void btnGuardarClic(ActionEvent evt) {
        this.guardar();
        
    }
    
    public void guardar() {
        String observaciones = this.ventana.verTxtObservaciones().getText().trim();
        NotaAprobacion nota = (NotaAprobacion) this.ventana.verComboNota().getSelectedItem();
        Date date = this.ventana.verFechaExposicion().getCalendar().getTime();
        LocalDate fecha = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        LocalDate fecha= (LocalDate)this.ventana.verFecha()
        IGestorTrabajos gestorT = GestorTrabajos.instanciar();

        IGestorSeminarios gestor = GestorSeminarios.instanciar();
        String resultado;

        if (seminario.verObservaciones() == null || seminario.verFechaExposicion() == null || seminario.verNotaAprobacion() == null) {
            resultado = gestor.validarSeminario(fecha, nota, observaciones);
        } else {
            resultado = gestorT.dameTrabajo(titulo).modificarSeminario(seminario, nota, observaciones);
        }

        if (resultado.equals(IGestorTrabajos.SEMINARIO_EXITO)) {
            this.ventana.dispose();
        } else {
            gestorT.cancelar();
            JOptionPane.showMessageDialog(null, resultado, IControladorSeminarios.TITULO, JOptionPane.ERROR_MESSAGE);
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
    

