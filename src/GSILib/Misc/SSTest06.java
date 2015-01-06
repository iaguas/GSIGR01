/* 
 * Práctica 02 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.Misc;

import GSILib.BSystem.BusinessSystem;
import java.io.File;

/**
 * This is the test class SSTest06.
 * This class ensures the correct operation of the PrintableNews exportation to 
 * an .ods file.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class SSTest06 {
    public static void main(String args[]){
        // Creamos la instancia de nuestro sistema de información.
        BusinessSystem bsystem = new BusinessSystem();  
        
        // Creamos el objeto fichero con el que manejaremos la hoja de calculo.
        final File f = new File("P02Ej06.ods");
        
        if(f.exists()){
            // Importamos las Printable News.
            int n = bsystem.importPrintableNews(f);
            System.out.println("Se han importado " + n + " Printable News.");
        }
        else
            System.err.println("El archivo P02Ej06.ods no existe. No se puede ejecutar el programa.");
        
    }
}
