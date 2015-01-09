/* 
 * Práctica 05 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib;

import GSILib.BTesting.EOTester;
import GSILib.BTesting.XMLTesting;
import GSILib.connect.client.HRClient;
import GSILib.connect.client.VLClient;
import GSILib.connect.server.BusinessServer;
import GSILib.Misc.*;
import GSILib.net.NewsWebServer;
import GSILib.persistence.XMLParsingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.text.ParseException;
import org.xml.sax.SAXException;

/**
 * This class contains the executable for a .jar file that this project can be converted to.
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public abstract class EjecutableGSIGR01 {
    // Para cuando los main no tienen nada que recibir.
    private static final String none[] = {};
    // Para poder controlar el teclado
    private static final BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
    // Para acceder a los argumentos del main.
    private static String mainArgs[];
    
    /**
     * This is the main method who start the virtual machine.
     * @param mainArgs arguments stack of args of main method.
     * @throws SAXException exception derivated from data consistence.
     * @throws XMLParsingException exception derivated from XML parsing.
     * @throws IOException exception derivated from io.
     * @throws ParseException exception derivated from parsing.
     * @throws Exception exception derivated an exception.
     */
    public static void main(String mainArgs[]) throws SAXException, XMLParsingException, IOException, ParseException, Exception{
        // Guardamos los argumentos para utilizarlos más adelante, si procede.
        EjecutableGSIGR01.mainArgs = mainArgs;
        
        System.out.println("+------------------------------------------------+");
        System.out.println("|       Gestión de Sistemas de Información       |");
        System.out.println("|              Prácticas - Grupo 01              |");
        System.out.println("|     Iñigo Aguas, Álvaro Gil e Iñaki García     |");
        System.out.println("| Universidad Pública de Navarra - curso 2014-15 |");
        System.out.println("+------------------------------------------------+");
        
        // Iniciamos la selección y ejecución de la práctica.
            int option = menuPrincipal();
            principalHandler(option);
    }
    
 
    /**
     * This method shows the main menu for the resume of labs of subject and reads
     * the option selected by user.
     * @return option selected by user via keyboard.
     */
    private static int menuPrincipal(){
        System.out.println("-- MENÚ PRINCIPAL --");
        System.out.print(
            "Seleccione una opción: \n" +
            "  1. Ejecutar pruebas de la práctica 01\n" +
            "  2. Ejecutar pruebas de la práctica 02\n" +
            "  3. Ejecutar pruebas de la práctica 03\n" +
            "  4. Ejecutar pruebas de la práctica 04\n" +
            "  5. Ejecutar el servidor de la práctica 05\n" + 
            "  0. Salir\n" +
            "  Opción: "
        );
        
        try{
            return Integer.parseInt(keyboard.readLine());
        }
        catch(NumberFormatException | IOException ex){
            return -1;
        }
    }
       
    /**
     * This method reads the option and it starts a test of the lab selected.
     * @param option Sected by the user in a menu method.
     * @throws SAXException
     * @throws XMLParsingException
     * @throws IOException
     * @throws ParseException
     * @throws Exception 
     */
    private static void principalHandler(int option) throws SAXException, XMLParsingException, IOException, ParseException, Exception{
        
        switch (option) {
            case 1: // Práctica 01
                EOTester.main(none);
                break;
            case 2: // Práctica 02
                int optionL2 = menuLab2();
                lab2Handler(optionL2);
                break;
            case 3: // Práctica 03
                XMLTesting.main(none);
                break;
            case 4: // Práctica 04
                int optionL4 = menuLab4();
                lab4Handler(optionL4);
                break;
            case 5: // Práctica 05
                NewsWebServer.main(mainArgs);
                break;
            case 0: // Salimos sin hacer nada.
                break;
            default: // No hemos introducido una opción válida o ha habido problemas, preguntamos de nuevo.
                System.err.println("ERROR. Ha introducido una opción que no se ha podido procesar.");
                int newOption = menuPrincipal();
                principalHandler(newOption);
        }
    }
    
    /**
     * This method shows the menu for lab 2 and reads the option selected by user.
     * @return option selected by user via keyboard.
     */
    private static int menuLab2(){
        System.out.println("\t- MENÚ LAB 2 -");
        System.out.print(
            "\tSeleccione una opción: \n" +
            "\t  1. Ejercicio 2.\n" +
            "\t  2. Ejercicio 3.\n" +
            "\t  3. Ejercicio 4.\n" +
            "\t  4. Ejercicio 5.\n" +
            "\t  5. Ejercicio 6. importTeletypes().\n" + 
            "\t  6. Demostración importPrintableNews().\n" + 
            "\t  7. Implementación ODSPersistent.saveToFile().\n" + 
            "\t  8. Implementación ODSPersistent.loadFromFile().\n" + 
            "\t  0. Volver al menú principal.\n" +
            "\t  Opción: "
        );
        
        try{
            return Integer.parseInt(keyboard.readLine());
        }
        catch(NumberFormatException | IOException ex){
            return -1;
        }
    }
       
    /**
     * This method reads the option and it starts a test of the lab selected.
     * @param option Sected by the user in a menu method.
     */
    private static void lab2Handler(int option) throws SAXException, XMLParsingException, IOException, ParseException, Exception{
        
        switch (option) {
            case 1: //
                SSTest01.main(none);
                break;
            case 2: //
                SSTest02.main(none);
                break;
            case 3: // 
                SSTest03.main(none);
                break;
            case 4: // 
                SSTest04.main(none);
                break;
            case 5: // 
                SSTest05.main(none);
                break;
            case 6: //
                SSTest06.main(none);
                break;
            case 7: //
                SSTest07.main(none);
                break;
            case 8: //
                SSTest08.main(none);
                break;
            case 0: // Salimos sin hacer nada para volver al menú principal.
                break;
            default: // No hemos introducido una opción válida o ha habido problemas, preguntamos de nuevo.
                System.err.println("ERROR. Ha introducido una opción que no se ha podido procesar.");
                int newOption = menuLab2();
                lab2Handler(newOption);
        }
    }    
   
    /**
     * This method shows the menu for lab 2 and reads the option selected by user.
     * @return option selected by user via keyboard.
     */
    private static int menuLab4(){
        System.out.println("\t- MENÚ LAB 4 -");
        System.out.print(
            "\tSeleccione una opción: \n" +
            "\t  1. Ejecutar demostración de la clase BusinessServer.\n" +
            "\t  2. Ejecutar demostración de la clase HRClient\n" +
            "\t  3. Ejecutar demostración de la clase VLClient\n" +
            "\t  0. Volver al menú principal.\n" +
            "\t  Opción: "
        );
        
        try{
            return Integer.parseInt(keyboard.readLine());
        }
        catch(NumberFormatException | IOException ex){
            return -1;
        }
    }  

    /**
     * This method reads the option and it starts a test of the lab selected.
     * @param option Sected by the user in a menu method.
     * @throws IOException
     * @throws RemoteException
     * @throws SAXException 
     */
    private static void lab4Handler(int option) throws IOException, RemoteException, SAXException {
        
        switch (option) {
            case 1: //
                BusinessServer.main(none);
                break;
            case 2: // 
                HRClient.main(none);
                break;
            case 3: // 
                VLClient.main(none);
                break;
            case 0: // Salimos sin hacer nada para volver al menú principal.
                break;
            default: // No hemos introducido una opción válida o ha habido problemas, preguntamos de nuevo.
                System.err.println("ERROR. Ha introducido una opción que no se ha podido procesar.");
                int newOption = menuLab4();
                lab4Handler(newOption);
        }
    }
}
