/*
 * Esto es una prueba
 * Each line should be prefixed with  *
 * TODO: javadoc de esta clase
 */

package GSILib.connect.client;

import java.util.ArrayList;
import java.util.List;
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
        3. Actualizar un journalist
        4. Actualizar un trabajador
        
    */
    
    public static void display(){
        System.out.println("-- Acciones --");
        System.out.println(
            "Seleccione una opción: \n" +
            "  1. Añadir un periodista (journalist)\n" +
            "  2. Añadir un fotógrafo (photographer)\n" +
            "  3. Actualizar un periodista (journalist)\n " +
            "  4. Actualizar un trabajador (worker)\n " + 
            "  5. Salir\n " +
            "  Opción:  "
        );
     
        int selection = keyboard.nextInt();
        keyboard.nextLine();
        
        switch (selection) {
            case 1:
              display1();
              break;
            case 2:
              //display2();
              break;
            case 3:
              // TODO: display3();        
              break;
            case 4:
              // TODO: display4();
              break;
            case 5:
              exit();
              break;
            default:
              System.out.println("Opción no válida.");
              break;
        }
    }
    
    // Muestra el menú para la opción 1   
    private static void display1(){
        System.out.print("Introduzca la id para el periodista: ");
        int idjour = keyboard.nextInt();
        keyboard.nextLine();
        System.out.print("Introduzca el nombre: ");
        String journame = keyboard.nextLine();
        System.out.print("Introduzca la fecha de nacimiento: ");
        String jourbd = keyboard.nextLine();
        System.out.println("Introduzca el/los interes(es) (deje en vacio para terminar): \n");
        String inter = "not_empty";
        List<String> interests = new ArrayList<>();
        int cont = 1;
        // Condición: variable inter, no vacio
        while(!"".equals(inter)){
            System.out.print("Interes " + cont + ": ");
            inter = keyboard.nextLine();
            if(!"".equals(inter)){
                interests.add(inter);
            }
        }
        // TODO: Resultado en clase que usa el menú
    }
    
    // Da la condición de salida
    private static void exit(){
        System.out.println("Saliendo...");
        System.exit(1);
    }
}
