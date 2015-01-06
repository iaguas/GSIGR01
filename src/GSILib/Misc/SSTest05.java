/* 
 * Práctica 02 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.Misc;

import GSILib.BSystem.BusinessSystem;
import java.io.File;

/**
 * This is the test class SSTest05.
 * This class ensures the correct operation of the Teletypes exportation to an
 * .ods file.
 * @version 1.1
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class SSTest05 {
    public static void main(String args[]){
        // Creamos la instancia de nuestro sistema de información.
        BusinessSystem bsystem = new BusinessSystem();  
        
        // Creamos el objeto fichero con el que manejaremos la hoja de calculo.
        final File f = new File("P02Ej05.ods");
        if(f.exists()){
            // Importamos los teletipos.
            int n = bsystem.importTeletypes(f);
            System.out.println("Se han importado " + n + " teletipos.");
        }
        else
            System.err.println("El archivo P02Ej05.ods no existe. No se puede ejecutar el programa.");
     
        
    }
}
