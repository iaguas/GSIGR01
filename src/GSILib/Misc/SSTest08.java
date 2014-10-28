/* 
 * Práctica 02 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.Misc;

import GSILib.BSystem.BusinessSystem;
import java.io.File;

/**
 * This is the test class SSTest08.
 * This class tests the correct load of BusinessSystem from a .ods file.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class SSTest08 {
    public static void main(String args[]){
        // Creamos la instancia de nuestro sistema de información.
        BusinessSystem bsystem = new BusinessSystem();  
        
        // Creamos el objeto fichero con el que manejaremos la hoja de calculo.
        final File f = new File("businessSystem.ods");
        
        // Importamos los teletipos.
        bsystem.loadFromFile(f);
        
    }
}
