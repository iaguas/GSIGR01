 Error reading included file file:///Users/inigo/Dropbox/Universidad/7_semestre/04%20Gestión%20de%20Sistemas%20Informáticos/Prácticas/Git3/GSIGR01/nbproject/licenseheader.txt
package GSILib.Misc;

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
