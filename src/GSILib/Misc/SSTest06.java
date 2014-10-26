/* 
 * Práctica 02 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.Misc;

import GSILib.BSystem.BusinessSystem;
import java.io.File;

/**
 *
 * @author inigo
 */


public class SSTest06 {
    public static void main(String args[]){
        // Creamos la instancia de nuestro sistema de información.
        BusinessSystem bsystem = new BusinessSystem();  
        
        // Creamos el objeto fichero con el que manejaremos la hoja de calculo.
        final File f = new File("P02Ej06.ods");
        
        // Importamos los teletipos.
        bsystem.importPrintableNews(f);
        
    }
}
