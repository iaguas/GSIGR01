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
        System.out.println(
            "Seleccione una opción: \n" +
            "  1. Corregir una noticia\n" +
            "  2. Asociar un Journalist a un PrintableNews\n" +
            "  3. Mostrar noticias que no alcancen número min. de revisores\n" +
            "  4. Mostrar noticias que tengan menos revisores que el valor indicado\n " +
            "  5. Salir\n " +
            "  Opción:  "                
        );
     
        int selection = keyboard.nextInt();
        keyboard.nextLine();
    }
}
