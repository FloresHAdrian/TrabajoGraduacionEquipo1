/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.seminarios.modelos;

import gui.interfaces.IGestorSeminarios;
import gui.trabajos.modelos.GestorTrabajos;
import gui.trabajos.modelos.Trabajo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author User
 */
public class GestorSeminarios implements IGestorSeminarios{
    private static GestorSeminarios gestor;//Variable de clase
    public static final String TEXTO_SEMINARIOS= "./seminarios.txt";
    
    private GestorSeminarios(){
        this.leerSeminarios();
    }
    
    /**
     * Metodo para crear una unica instancia de GestorSeminarios
     * @return 
     */
    public static GestorSeminarios instanciar(){
        if(gestor ==null)
            gestor= new GestorSeminarios();
        return gestor;
    }
    /**Valida que estén correctos los datos para crear un nuevo seminario
     * Si el seminario está aprobado con observaciones, o desaprobado, se deben especificar las observaciones
     * 
     * @param fechaExposicion
     * @param notaAprobacion
     * @param observaciones
     * @return String  - cadena con el resultado de la validación (ERROR | ERROR_OBSERVACIONES | DATOS_CORRECTOS)
     */
    @Override
    public String validarSeminario(LocalDate fechaExposicion, NotaAprobacion notaAprobacion, String observaciones) {
        if(fechaExposicion== null || notaAprobacion== null)
            return ERROR;
        //controlamos la nota. Si es Aprobado con observaciones o Desaprobado las observaciones no peden ser nulas.
        if(notaAprobacion.equals(NotaAprobacion.APROBADO_CO)||notaAprobacion.equals(NotaAprobacion.DESAPROBADO) ){
            if(observaciones==null || observaciones.trim().isEmpty())
                return ERROR_OBSERVACIONES;
        }
        
        return DATOS_CORRECTOS;
    }
    
    /**
     * Valida que estén correctos los datos para crear un nuevo seminario
     * Si el seminario está aprobado con observaciones, o desaprobado, se deben especificar las observaciones
     * @param notaAprobacion nota de aprobación del seminario
     * @param observaciones observaciones del seminario
     * @return String  - cadena con el resultado de la validación (ERROR | ERROR_OBSERVACIONES | DATOS_CORRECTOS)
  
     */

    @Override
    public String validarSeminario(NotaAprobacion notaAprobacion, String observaciones) {
        //controlamos la nota. Si es Aprobado con observaciones o Desaprobado las observaciones no peden ser nulas.
        if(notaAprobacion.equals(NotaAprobacion.APROBADO_CO)||notaAprobacion.equals(NotaAprobacion.DESAPROBADO) ){
            if(observaciones==null || observaciones.trim().isEmpty())
                return ERROR_OBSERVACIONES;
        }
        
        return DATOS_CORRECTOS; 
    }
    
    
  
    /**
     * Guarda todos los seminarios de todos los trabajos
     * @return String  - cadena con el resultado de la operacion (ESCRITURA_OK | ESCRITURA_ERROR)
     */   
    @Override
    public String guardarSeminarios() {
 
        BufferedWriter bw = null;
        File f = new File(TEXTO_SEMINARIOS);
        GestorTrabajos gstrabajos = GestorTrabajos.instanciar();
        
        try {
            List<Trabajo> listaTrabajos = gstrabajos.buscarTrabajos(null);
            for (Trabajo t : listaTrabajos) {
                String cadena = t.verTitulo()+";";
                List<Seminario> listaSeminarios = t.verSeminarios();
                bw = new BufferedWriter(new FileWriter(f));
                for (int i = 0; i < listaSeminarios.size(); i++) {
                    //Le agrego un caracter ";" como separador
                    //Y escribo la cadena resultante en el archivo
                    Seminario unSeminario = listaSeminarios.get(i);
                    String patron = "dd/MM/YYYY";
                    LocalDate fExposicion = unSeminario.verFechaExposicion();
                    String fechaExposicion = fExposicion.format(DateTimeFormatter.ofPattern(patron));
                    cadena+= fechaExposicion + ";";
                    cadena += unSeminario.verNotaAprobacion() + ";";
                    cadena += unSeminario.verObservaciones() + ";";
//                    bw.write(cadena);
                    if (i < listaSeminarios.size() - 1) {
//                        bw.newLine();
                    }

                }
                bw.write(cadena);
                bw.newLine();
            }
            return ESCRITURA_OK;
            
            } catch (IOException ioe) {
                return ESCRITURA_ERROR;
            } finally {
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }

        }
    /**
     * Metodo para leer la lista de personas de un archivo de texto
     * @return String-cadena con el resultado de la operacion
     */

    public String leerSeminarios() {
        BufferedReader br = null;
        File f = new File(TEXTO_SEMINARIOS);//busca el archivo

        if (f.exists()) {

            try {
                GestorTrabajos gsTrabajos = GestorTrabajos.instanciar();
                for (Trabajo t : gsTrabajos.buscarTrabajos(null)) {  
                    br = new BufferedReader(new FileReader(f));
                    String cadena;
                    while ((cadena = br.readLine()) != null) {
                        String vector[] = cadena.split(";");////Permite separar subcadenas y guardar en un array de String
                        String titulo = vector[0];//guardamos titulo en el primer vector
                        Trabajo trabajo = gsTrabajos.dameTrabajo(titulo);//usamos el  metodo dame trabajo del gestor para obtener trabajo por el titulo que enviamos
                        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");//formato de la fecha
                        LocalDate fechaExposicion = LocalDate.parse(vector[1], format);//guardamos fecha en el segundo vector
                        // preguntamos si el vector 3 es aprobado con observaciones, sin observaciones o desaprobado
                        
                        if (vector[2].equalsIgnoreCase("Aprobado C/O")) {
//                            NotaAprobacion nota = NotaAprobacion.valueOf(vector[2]);//guardamos nota de aprobacion en el tercer vector
                            String observaciones = vector[3];//guardamos observaciones en el tercer vector
//                            trabajo.nuevoSeminario(fechaExposicion, NotaAprobacion.DESAPROBADO, observaciones);
                            trabajo.agregarSeminario(new Seminario(fechaExposicion, NotaAprobacion.APROBADO_CO, observaciones));
                        }
                        if (vector[2].equalsIgnoreCase("Aprobado S/O")) {
//                            NotaAprobacion nota = NotaAprobacion.valueOf(vector[2]);
                            String observaciones = "-";//guardamos observaciones en el tercer vector
                            trabajo.agregarSeminario(new Seminario(fechaExposicion, NotaAprobacion.APROBADO_SO, observaciones));
                        }

                        if (vector[2].equalsIgnoreCase("Desaprobado")) {
//                            NotaAprobacion nota = NotaAprobacion.valueOf(vector[2]);//guardamos nota de aprobacion en el tercer vector
                            String observaciones = vector[3];//guardamos observaciones en el tercer vector
                            trabajo.agregarSeminario(new Seminario(fechaExposicion, NotaAprobacion.DESAPROBADO, observaciones));
                        }
                    }

                }
                return LECTURA_OK;
            } catch (IOException ioe) {
                return LECTURA_ERROR;
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        }
        return IGestorSeminarios.ARCHIVO_INEXISTENTE;
    }
}
       
               
           
           
           
       

    



