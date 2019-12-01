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

    /**
     * Constructor
     *
     * @param ventanaPadre VentanaSeminarios
     * @param seminario seminario a modificar
     * @param titulo titulo de la ventana
     *
     */
    public ControladorAMSeminario(Dialog ventanaPadre, Seminario seminario, String titulo) {
        this.titulo = titulo;
        this.ventana = new VentanaAMSeminario(this, ventanaPadre);
        this.seminario = seminario;

        if (this.seminario != null) {//modificación de seminario
            ventana.setTitle(IControladorSeminarios.MODIFICAR);
            Date date = Date.from(seminario.verFechaExposicion().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            ventana.verFechaExposicion().setDate(date);
            ventana.verFechaExposicion().setEnabled(false);
            ventana.verTxtObservaciones().setText(seminario.verObservaciones());
            ventana.verTxtObservaciones().setEnabled(false);//texto observaciones deshabilitado cuando se llama a modificar un seminario
                                                         //Si la nota admite observaciones, comboNotaCambiarSeleccion habilita el cuadro                                  
        } else {
            //para que muestra la fecha actual por defecto
            LocalDate fActual = LocalDate.now();
            GregorianCalendar fechaActual = GregorianCalendar.from(fActual.atStartOfDay(ZoneId.systemDefault()));
            this.ventana.verFechaExposicion().setCalendar(fechaActual);
            ventana.setTitle(IControladorSeminarios.NUEVO);
        }
        

        this.inicializarComboNota(this.ventana.verComboNota());
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);

    }

    /**
     * Acción a ejecutar cuando se selecciona el botón Guardar
     * @param evt evento
     */

    @Override
    public void btnGuardarClic(ActionEvent evt) {
        this.guardar();

    }

    /**
     * Crea o modifica un seminario
     */

    public void guardar() {

        LocalDate fecha = null;
        String observaciones = this.ventana.verTxtObservaciones().getText().trim();
        NotaAprobacion nota = (NotaAprobacion) this.ventana.verComboNota().getSelectedItem();
        //para que se guarde una fecha convertimos a LocalDate
        if (this.ventana.verFechaExposicion().getCalendar() != null) {
            Date date = this.ventana.verFechaExposicion().getCalendar().getTime();

            fecha = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        //instanciamos el gestor trabajos para obtener el titulo del trabajo
        IGestorTrabajos gestorT = GestorTrabajos.instanciar();
        Trabajo unTrabajo = gestorT.dameTrabajo(titulo);
        String resultado;

        if (seminario == null) {//nuevo seminario
            resultado = unTrabajo.nuevoSeminario(fecha, nota, observaciones);
        } else {//modificar seminario
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

    /**
     * Acción a ejecutar cuando se selecciona el botón Cancelar
     * @param evt evento
     */
    @Override
    public void btnCancelarClic(ActionEvent evt) {
        IGestorTrabajos gestorT = GestorTrabajos.instanciar();
        gestorT.dameTrabajo(titulo).cancelar();
        this.ventana.dispose();
    }

    /**
     * Acción a ejecutar cuando cambia la selección en el combo
     * @param evt evento
     */
    @Override
    public void comboNotaCambiarSeleccion(ActionEvent evt) {
        ModeloComboNota mcn = (ModeloComboNota) this.ventana.verComboNota().getModel();
        NotaAprobacion nota = mcn.obtenerNotaAprobacion();

        if (nota != null) {
            switch (nota) {
                case APROBADO_SO://si es aprobado s/o no se puede escribir las observaciones
                    this.ventana.verTxtObservaciones().setText(null);
                    this.ventana.verTxtObservaciones().setEnabled(false);
                    break;

                case APROBADO_CO://si es aprobado c/o o desaprobado  se puede escribir las observaciones
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

    /**
     * inicializa el comobobox cuando se muestra la ventana por primera vez
     *
     * @param comboNota combobox para las notas
     */
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
