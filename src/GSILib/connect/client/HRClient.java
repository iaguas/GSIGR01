/* 
 * Práctica 04 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.connect.client;

import GSILib.BModel.workers.Journalist;
import GSILib.connect.HumanRecGateway;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 *
 * @author Alvaro
 */
public class HRClient{
    
    public static void main(String[] args) {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // Leemos por teclado a ip
        
        System.out.print("Introduzca la ip del servidor: ");
        String remoteMachine;
        try {
            remoteMachine = br.readLine();
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
            port = Integer.parseInt(br.readLine());
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
            tag = br.readLine();
            if (tag.equals(""))
                tag = "null";
        }
        catch (IOException ioe){
            System.out.println("Exception en la lectura: " + ioe.getMessage());
            tag = "null";
        }
        
        System.out.println("---------------");
        System.out.println("***   RMI   ***");
        System.out.println("---------------");
        
        /* Existen las siguientes opciones para el cliente que se conecte 
        mediante este stub:
        1. Añadir un journalist
        2. Añadir un fotógrafo
        3. Añadir un trabajador
        4. Actualizar un journalist
        5. Actualizar un trabajador
        
        */
        
        // Nuevo Journalist
        
        ArrayList interestsOfAlvaro = new ArrayList();
        
        interestsOfAlvaro.add("Discutir");
        interestsOfAlvaro.add("Tocar las narices");
        interestsOfAlvaro.add("Jugar al CS");

        Journalist journalistAlvaro = new Journalist("8", "Alvaro Octal", "27/12/1993", interestsOfAlvaro);
        
        // 
        
        // RMI stuff
        
        try {
            // Nos conectamos
            
            Registry registry = LocateRegistry.getRegistry(remoteMachine, port);
            
            // Enlazamos el objeto remoto como local
           
            HumanRecGateway human = (HumanRecGateway) registry.lookup(tag);
            
            // Done! start coding
            
            System.out.println("Añadiendo Journalist... " + human.addWorker(journalistAlvaro));
            journalistAlvaro.setName("Pepito");
            System.out.println("Actualizando Journalist... " + human.updateWorker(journalistAlvaro));
        } catch (ConnectException ex){
            System.err.println("No se pudo encontrar el servidor: " + remoteMachine + ":" + port);
        } catch (RemoteException ex) {
            System.err.println("Exception in connection : " + ex.getMessage());
        } catch (NotBoundException ex){
            System.err.println("No se pudo encontrar el tag: " + tag);
        }
    }
}