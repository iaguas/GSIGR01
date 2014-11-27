/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */

package GSILib.connect.client;

import java.util.Scanner;

/**
 *
 * @author linux1
 */
public class MenuHRClient {
    private static Scanner keyboard = new Scanner (System.in);
    
    /* Existen las siguientes opciones para el cliente que se conecte 
        mediante este stub:
        1. Añadir un journalist
        2. Añadir un fotógrafo
        3. Añadir un trabajador
        4. Actualizar un journalist
        5. Actualizar un trabajador
        
    */
    
    public static void display(){
        System.out.println("-- Acciones --");
        System.out.println(
            "Seleccione una opción: \n" +
            "  1. Añadir un periodista (journalist)\n" +
            "  2. Añadir un fotógrafo (photographer)\n" +
            "  3. Añadir un trabajador (worker)\n" +
            "  4. Actualizar un periodista (journalist)\n " +
            "  5. Actualizar un trabajador (worker)\n " + 
            "  6. Salir\n " +
            "  Opción:  "
        );
     
        int selection = keyboard.nextInt();
        keyboard.nextLine();
    }
}
