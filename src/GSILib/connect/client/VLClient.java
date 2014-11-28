/* 
 * Práctica 04 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.connect.client;

import GSILib.BModel.Newspaper;
import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.workers.Journalist;
import GSILib.connect.ValidationGateway;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.exit;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author Iñaki
 */
public class VLClient {
    private static int RMI_PORT=1099;
    
    
    /**
     * This is the main method for the ValidationClient stub
     * @param args
     * @throws RemoteException, IOException that handle RMI associated errors
     */
    public static void main(String[] args) throws RemoteException, IOException {
        
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        
        // Leemos por teclado a ip
        
        System.out.print("Introduzca la ip del servidor: ");
        String remoteMachine;
        try {
            remoteMachine = keyboard.readLine();
            if (remoteMachine.equals(""))
                remoteMachine = "localhost";
        } catch (IOException ioe) {
            System.out.println("Exception when reading: " + ioe.getMessage());
            remoteMachine="localhost";
        }
        
        // Leemos por teclado el puerto
        
        System.out.print("Introduzca el puerto de servidor: ");
        int port;
        try{
            port = Integer.parseInt(keyboard.readLine());
        } catch (NumberFormatException nfe){
            port = 1099;
        } catch (IOException ioe){
            System.out.println("Exception en la lectura: " + ioe.getMessage());
            port = 1099;
        }
        
        // Leemos por teclado el tag
        
        System.out.print("Introduzca el tag del objeto remoto: ");
        String tag;
        try{
            tag = keyboard.readLine();
            if (tag.equals(""))
                tag = "VLGateway";
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
        
        int option = Menu.getVLOption();
        
        switch (option){
            case 1:
                /*System.out.print("Introduzca la id de la noticia a corregir: ");
                int idnot = keyboard.nextInt();
                keyboard.nextLine();
                System.out.print("Escriba la nueva cabecera: ");
                String head = keyboard.nextLine();
                System.out.print("Escriba el nuevo cuerpo de la noticia: ");
                String body = keyboard.nextLine();
                PrintableNews editPrintableNews = new PrintableNews(head,body,jourExample);
                editPrintableNews.setId(idnot);
                if(validation.correctNews(editPrintableNews)){
                    System.out.println("PrintableNews con id " + idnot + " ha sido añadido con éxito!");
                }*/
                break;
            case 2:
                /*System.out.print("Introduzca la id para la noticia: ");
                int idNotasoc = keyboard.nextInt();
                keyboard.nextLine();
                System.out.print("Introduzca la id para el periodista revisor: ");
                String idJourasoc = keyboard.nextLine();
                Journalist asocJournalist = validation.findJournalist(idJourasoc);
                // ¿¿Cómo recorremos los PrintableNews??                
                //PrintableNews asocPrintableNews = new PrintableNews(head,body,jourExample);
                //if(validation.validateNews(asocPrintableNews,jourExample)){
                //    System.out.println("Photographer con id " + idphoto + " ha sido añadido con éxito!");
                //}*/
                break;
            case 3: 
                
                // Pedimos la lista de PrintableNews
                
                System.out.println("Recuperando lista de noticias...");
                
                for (PrintableNews printableNews : validation.getPendingNews()){
                    System.out.println(printableNews.toXML());
                }
                break;
                
            case 4:
                
                // Leemos la cota por teclado
                
                System.out.print("Introduzca el minimo de revisores por noticia: ");
                int cota = Integer.parseInt(keyboard.readLine());
                
                // Pedimos la lista de PrintableNews
                
                System.out.println("Recuperando lista de noticias con cota de revisores...");
                for (PrintableNews printableNews : validation.getPendingNews(cota)){
                    System.out.println(printableNews.toXML());
                }
                break;
        }
    }
}
