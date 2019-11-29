/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.seminarios.modelos;

import gui.trabajos.modelos.GestorTrabajos;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Adrian
 */
public class ModeloTablaSeminarios extends AbstractTableModel{
    //Constantes para los nombre de las columnas
    public static final String COLUMNA_FECHA = "Fecha";
    public static final String COLUMNA_NOTA = "Nota";
    public static final String COLUMNA_OBSERVACIONES="Observaciones";
    //Lista que voy a sacar del trabajo
    List<Seminario> listaSeminarios;
    //Lista para los nombre de las columnas
    List<String> nombreColumnas= new ArrayList<>();
    
    public ModeloTablaSeminarios(String titulo){
        this.nombreColumnas.add(COLUMNA_FECHA);
        this.nombreColumnas.add(COLUMNA_NOTA);
        this.nombreColumnas.add(COLUMNA_OBSERVACIONES);
        GestorTrabajos gsTrabajos= GestorTrabajos.instanciar();
        this.listaSeminarios = (gsTrabajos.dameTrabajo(titulo)).verSeminarios();
    }
    
    /**
     * Obtiene la cantidad de filas de la tabla
     * @return 
     */
    @Override
    public int getRowCount() {
        return this.listaSeminarios.size();
    }

    /**
     * Obtienen la cantidad de columnas de la tabla
     * @return 
     */
    @Override
    public int getColumnCount() {
        return this.nombreColumnas.size();
    }

    /**
     * Obtiene el valor de la celda especificada
     * @param fila
     * @param columna
     * @return 
     */
    @Override
    public Object getValueAt(int fila, int columna) {
        Seminario unSeminario= this.listaSeminarios.get(fila);
        switch(columna){
            case 0: return unSeminario.verFechaExposicion();
            case 1: return unSeminario.verNotaAprobacion();
            case 2: return unSeminario.verObservaciones();
            default: return ";Hola profe como esta?";
        }
    }
    
    @Override
    public String getColumnName(int columna){
        return this.nombreColumnas.get(columna);
    }
    
    /**
     * Devuelve el seminario de l fila especificada en la tabla
     * @param fila
     * @return 
     */
    public Seminario obtenerSeminario(int fila){//Revisar donde uso este metodo
        return this.listaSeminarios.get(fila);
    }
    
}
