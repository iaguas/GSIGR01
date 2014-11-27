/* 
 * Práctica 04 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.connect.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 *
 * @author linux1
 */
public class Menu {
    private static BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
    
    /**
     * TODO: JavaDoc
     * @return
     * @throws IOException 
     */
    public static int getHROption() throws IOException{
        System.out.println("-- Acciones --");
        System.out.print(
            "Seleccione una opción: \n" +
            "  1. Añadir un periodista (journalist)\n" +
            "  2. Añadir un fotógrafo (photographer)\n" +
            "  3. Actualizar un periodista (journalist)\n" +
            "  4. Actualizar un trabajador (worker)\n" + 
            "  5. Salir\n" +
            "  Opción: "
        );
     
        return Integer.parseInt(keyboard.readLine());
    }
    
    /**
     * TODO: JavaDoc
     * @return 
     */
    public static int getVLOption() throws IOException{
        System.out.println("-- Acciones --");
        System.out.print(
            "Seleccione una opción: \n" +
            "  1. Corregir una noticia\n" +
            "  2. Asociar un Journalist a un PrintableNews\n" +
            "  3. Mostrar noticias que no alcancen número min. de revisores\n" +
            "  4. Mostrar noticias que tengan menos revisores que el valor indicado\n" +
            "  5. Salir\n" +
            "  Opción: "                
        );
     
        return Integer.parseInt(keyboard.readLine());
    }
}
