/* 
 * Práctica 04 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.BTesting.keyboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author linux1
 */
public class Menu {
    private static BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
    
    /**
     * This is the static method which shows the option menu for the HumanResourceClient stub
     * @return number of the desired option
     * @throws IOException for the option reading from the keyboard
     */
    public static int getHROption() throws IOException{
        System.out.println("-- Acciones --");
        System.out.print(
            "Seleccione una opción: \n" +
            "  1. Añadir un periodista (journalist)\n" +
            "  2. Añadir un fotógrafo (photographer)\n" +
            "  3. Actualizar un periodista (journalist)\n" +
            "  4. Actualizar un fotógrafo (photographer)\n" + 
            "  0. Salir\n" +
            "  Opción: "
        );
        
        try{
            return Integer.parseInt(keyboard.readLine());
        }
        catch(NumberFormatException ex){
            return 0;
        }
    }
    
    /**
     * This is the static method which shows the option menu for the ValidationClient stub
     * @return number of the desired option
     * @throws IOException for the option reading from the keyboard
     */
    public static int getVLOption() throws IOException{
        System.out.println("-- Acciones --");
        System.out.print(
            "Seleccione una opción: \n" +
            "  1. Corregir una noticia\n" +
            "  2. Asociar un Journalist a un PrintableNews\n" +
            "  3. Mostrar noticias que no alcancen número min. de revisores\n" +
            "  4. Mostrar noticias que tengan menos revisores que el valor indicado\n" +
            "  0. Salir\n" +
            "  Opción: "
        );
     
        try{    
            return Integer.parseInt(keyboard.readLine());
        }
        catch(NumberFormatException ex){
            return 0;
        }
    }
}
