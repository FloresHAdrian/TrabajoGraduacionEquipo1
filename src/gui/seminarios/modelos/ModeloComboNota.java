/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.seminarios.modelos;

import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author User
 */
public class ModeloComboNota extends DefaultComboBoxModel{

    public ModeloComboNota() {
         for (NotaAprobacion notaAprobacion : NotaAprobacion.values()) {
            this.addElement(notaAprobacion); 
        }
    }
    
    
    public NotaAprobacion obtenerNotaAprobacion(){
        return (NotaAprobacion)this.getSelectedItem();
    }
    
    public void seleccionarNotaAprobacion(NotaAprobacion nota){
        this.setSelectedItem(nota);
    }
    
    
}
