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
import java.util.GregorianCalendar;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class ControladorAMSeminario implements IControladorAMSeminario {

    private VentanaAMSeminario ventana;
    private Seminario seminario;
    private String titulo;

    public ControladorAMSeminario(Dialog VentanaPadre, Seminario seminario, String titulo) {
        this.titulo = titulo;
        this.ventana = new VentanaAMSeminario(this, VentanaPadre);
        this.seminario = seminario;

        if (this.seminario != null) {
            ventana.setTitle(IControladorSeminarios.MODIFICAR);
            Date date = Date.from(seminario.verFechaExposicion().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

            ventana.verFechaExposicion().setDate(date);
            ventana.verFechaExposicion().setEnabled(false);
            ventana.verTxtObservaciones().setText(seminario.verObservaciones());

        } else {
            LocalDate fActual = LocalDate.now();
            GregorianCalendar fechaActual = GregorianCalendar.from(fActual.atStartOfDay(ZoneId.systemDefault()));
            this.ventana.verFechaExposicion().setCalendar(fechaActual);

            ventana.setTitle(IControladorSeminarios.NUEVO);
        }

        this.inicializarComboNota(this.ventana.verComboNota());

        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);

    }

    @Override
    public void btnGuardarClic(ActionEvent evt) {
        this.guardar();

    }

    public void guardar() {

        LocalDate fecha = null;
        String observaciones = this.ventana.verTxtObservaciones().getText().trim();
        NotaAprobacion nota = (NotaAprobacion) this.ventana.verComboNota().getSelectedItem();

        if (this.ventana.verFechaExposicion().getCalendar() != null) {
            Date date = this.ventana.verFechaExposicion().getCalendar().getTime();

            fecha = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

        IGestorTrabajos gestorT = GestorTrabajos.instanciar();
        Trabajo unTrabajo = gestorT.dameTrabajo(titulo);
        String resultado;

        if (seminario == null) {
            resultado = unTrabajo.nuevoSeminario(fecha, nota, observaciones);
        } else {
            resultado = gestorT.dameTrabajo(titulo).modificarSeminario(seminario, nota, observaciones);
        }
        if (resultado.equals(IGestorSeminarios.EXITO)) {
            JOptionPane.showMessageDialog(null, resultado, IControladorSeminarios.TITULO, JOptionPane.PLAIN_MESSAGE);
            this.ventana.dispose();
        } else {
            gestorT.dameTrabajo(titulo).cancelar();

            JOptionPane.showMessageDialog(null, resultado, IControladorSeminarios.TITULO, JOptionPane.WARNING_MESSAGE);

        }
    }

    @Override
    public void btnCancelarClic(ActionEvent evt) {
        IGestorTrabajos gestorT = GestorTrabajos.instanciar();
        gestorT.dameTrabajo(titulo).cancelar();
        this.ventana.dispose();
    }

    @Override
    public void comboNotaCambiarSeleccion(ActionEvent evt) {
        JComboBox comboNota = this.ventana.verComboNota();
        ModeloComboNota mcn = (ModeloComboNota) this.ventana.verComboNota().getModel();
        NotaAprobacion nota = mcn.obtenerNotaAprobacion();

        if (nota != null) {
            switch (nota) {
                case APROBADO_SO:
                    this.ventana.verTxtObservaciones().setText(null);
                    this.ventana.verTxtObservaciones().setEnabled(false);
                    break;

                case APROBADO_CO:
                case DESAPROBADO:
                    this.ventana.verTxtObservaciones().setEnabled(true);
                    this.ventana.verTxtObservaciones().selectAll();
                    this.ventana.verTxtObservaciones().requestFocus();
                    break;
            }
        } else {
            this.ventana.verTxtObservaciones().setEnabled(false);
        }

    }

    private void inicializarComboNota(JComboBox comboNota) {
        ModeloComboNota mcn = new ModeloComboNota();
        comboNota.setModel(mcn);
        if (this.seminario != null) {
            mcn.seleccionarNotaAprobacion(seminario.verNotaAprobacion());
        } else {
            mcn.seleccionarNotaAprobacion(null);
        }
    }

}
