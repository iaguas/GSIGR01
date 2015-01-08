/* 
 * Práctica 04 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.connect.client;

import GSILib.BTesting.keyboard.Menu;
import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.workers.Journalist;
import GSILib.BTesting.keyboard.ReadFromKeyboard;
import GSILib.connect.ValidationGateway;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.exit;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * This is a class that shows how to utilize the RMIClient with the Validation 
 * Gateway
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class VLClient {
    
    // Valores por defecto
    
    private static final String RMI_HOST = "localhost";
    private static final int RMI_PORT = 1099;
    private static final String RMI_TAG = "VLGateway";
    
    /**
     * This is the main method for the ValidationClient stub
     * @param args arguments for Main Method
     * @throws RemoteException that handle RMI associated errors
     * @throws IOException exception derived from io.
     */
    public static void main(String[] args) throws RemoteException, IOException {
        
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        
        // Leemos por teclado a ip
        
        System.out.print("Introduzca la ip del servidor: ");
        String remoteMachine;
        try {
            remoteMachine = keyboard.readLine();
            if (remoteMachine.equals(""))
                remoteMachine = RMI_HOST;
        } catch (IOException ioe) {
            System.out.println("Exception when reading: " + ioe.getMessage());
            remoteMachine = "null";
        }
        
        // Leemos por teclado el puerto
        
        System.out.print("Introduzca el puerto de servidor: ");
        int port;
        try{
            port = Integer.parseInt(keyboard.readLine());
        } catch (NumberFormatException nfe){
            port = RMI_PORT;
        } catch (IOException ioe){
            System.out.println("Exception en la lectura: " + ioe.getMessage());
            port = RMI_PORT;
        }
        
        // Leemos por teclado el tag
        
        System.out.print("Introduzca el tag del objeto remoto: ");
        String tag;
        try{
            tag = keyboard.readLine();
            if (tag.equals(""))
                tag = RMI_TAG;
        }
        catch (IOException ioe){
            System.out.println("Exception en la lectura: " + ioe.getMessage());
            tag = "null";
        }
        
        // Creamos un cliente
        
        RMIClient client = new RMIClient(remoteMachine, port, tag);
        
        ValidationGateway validation = (ValidationGateway) client.getRemoteObject();
        
        if (validation == null){
            System.err.println("No se pudo encontrar un objeto remoto (" + remoteMachine + ":" + port + "-" + tag + ")");
            exit(0);
        }
        
        System.out.println("---------------");
        System.out.println("*** Testing ***");
        System.out.println("---------------");
        
        // Nuevo Journalist
        
        ArrayList interestsOfAlvaro = new ArrayList();
        
        interestsOfAlvaro.add("Discutir");
        interestsOfAlvaro.add("Tocar las narices");
        interestsOfAlvaro.add("Jugar al CS");

        Journalist journalistAlvaro = new Journalist("8", "Alvaro", "27/12/1993", interestsOfAlvaro);
        
        // Nuevo Journalist
        
        ArrayList interestsOfPirata = new ArrayList();
        
        interestsOfPirata.add("Trifulcas de bar");
        interestsOfPirata.add("Cantar conciones");
        interestsOfPirata.add("Adorar a MEV");

        Journalist journalistPirata = new Journalist("42", "Pirata", "01/01/1970", interestsOfPirata);
        
        boolean exit = false;
        while(! exit){
            
            int option = Menu.getVLOption();
            
            switch (option){
                case 1:

                    // Corregir una PrintableNews

                    PrintableNews printableNews = ReadFromKeyboard.newPrintableNews(journalistAlvaro);

                    System.out.print("Updating PrintableNews...");
                    if (validation.correctNews(printableNews))
                        System.out.println(" [done]");
                    else
                        System.out.println(" [fail]");

                    break;

                case 2:

                    // Añadimos un Journalist como reviewer de una PrintableNews

                    printableNews = ReadFromKeyboard.newPrintableNews(journalistAlvaro);

                    System.out.print("Adding Reviewer...");
                    if (validation.validateNews(printableNews, journalistPirata))
                        System.out.println(" [done]");
                    else
                        System.out.println(" [fail]");

                    break;

                case 3: 

                    // Pedimos la lista de PrintableNews

                    System.out.println("Recuperando lista de noticias...");

                    for (PrintableNews singlePrintableNews : validation.getPendingNews()){
                        System.out.println(singlePrintableNews.toXML());
                    }
                    break;

                case 4:

                    // Leemos la cota por teclado

                    System.out.print("Introduzca el minimo de revisores por noticia: ");
                    int cota = Integer.parseInt(keyboard.readLine());

                    // Pedimos la lista de PrintableNews

                    System.out.println("Recuperando lista de noticias con cota de revisores...");
                    for (PrintableNews singlePrintableNews : validation.getPendingNews(cota)){
                        System.out.println(singlePrintableNews.toXML());
                    }
                    break;
                  
                case 0:
                    
                    // Salimos del programa
                    
                    exit = true;
                    break;
            }
        }
    }
}
