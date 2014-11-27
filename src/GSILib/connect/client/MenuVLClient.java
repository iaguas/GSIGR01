/*
 * Esto es una prueba
 * Each line should be prefixed with  *
 * TODO: javadoc de esta clase
 */

package GSILib.connect.client;

import java.util.Scanner;

/**
 *
 * @author linux1
 */
public class MenuVLClient {
    private static Scanner keyboard = new Scanner (System.in);
    
    /* Existen las siguientes opciones para el cliente que se conecte 
       mediante este stub:
       1. Corregir una noticia, subiendo una nueva que la sustituya
       2. Asociar un Journalist a un PrintableNews
       3. Mostrar las noticias cumpla alguna condición
        3.1. Que no alcance el mínimo número de reviewers
        3.2. Que tenga, a lo sumo, un cierto número de reviewers pasado por parámetro

    */
    
    public static void display(){
        System.out.println("-- Acciones --");
        System.out.print(
            "Seleccione una opción: \n" +
            "  1. Corregir una noticia (se vacía la lista de reviewers)\n" +
            "  2. Asociar un Journalist a un PrintableNews\n" +
            "  3. Mostrar noticias que no alcancen número min. de reviewers\n" +
            "  4. Mostrar noticias que tengan menos reviewers que el valor indicado\n " +
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
              display2();
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
        System.out.print("Introduzca la id de la noticia a corregir: ");
        int idnot = keyboard.nextInt();
        keyboard.nextLine();
        System.out.print("Escriba la nueva cabecera: ");
        String head = keyboard.nextLine();
        System.out.print("Escriba el nuevo cuerpo de la noticia: ");
        String body = keyboard.nextLine();
        // Se ha de recuperar el la noticia entera para poder modificarla
        // TODO: Resultado en clase que usa el menú
    }
    
    // Muestra el menú para la opción 2
    private static void display2(){
        System.out.print("Introduzca la id de la noticia: ");
        int idnot = keyboard.nextInt();
        keyboard.nextLine();
        System.out.print("Introduzca del Journalist a asociar a la noticia: ");
        int idjour = keyboard.nextInt();
        keyboard.nextLine();
        // Se han de obtener los objetos printablenews y journalist a partir de sus ids
        // TODO: Resultado en clase que usa el menú
    }
    
    // Da la condición de salida
    private static void exit(){
        System.out.println("Saliendo...");
        System.exit(1);
    }
}
