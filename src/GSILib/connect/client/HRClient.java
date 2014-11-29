/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.connect.client;

import GSILib.BModel.workers.Journalist;
import GSILib.BModel.workers.Photographer;
import GSILib.BTesting.keyboard.ReadFromKeyboard;
import GSILib.connect.HumanRecGateway;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.exit;
import java.rmi.RemoteException;

/**
 *
 * @author Iñaki
 */
public class HRClient {
    private static final int RMI_PORT=1099;
    
    /**
     * This is the main method for the HumanResourceClient stub
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
                tag = "HRGateway";
        }
        catch (IOException ioe){
            System.out.println("Exception en la lectura: " + ioe.getMessage());
            tag = "null";
        }
        
        // Creamos un cliente
        
        RMIClient client = new RMIClient(remoteMachine, port, tag);
        
        HumanRecGateway human = (HumanRecGateway) client.getRemoteObject();
        
        if (human == null){
            System.err.println("No se pudo encontrar un objeto remoto (" + remoteMachine + ":" + port + "-" + tag + ")");
            exit(0);
        }
        
        System.out.println("---------------");
        System.out.println("*** Testing ***");
        System.out.println("---------------");
        
        int option = Menu.getHROption();
        
        System.out.println("Opcion: " + option);
        
        switch (option){
            case 1:
                
                // Leemos el Journalist por teclado
                
                System.out.println("-- Nuevo Journalist --");
                Journalist journalist = ReadFromKeyboard.newJournalist();
                
                System.out.print("Añadiendo Journalist...");
                if(human.addWorker(journalist))
                    System.out.println(" [done]");
                else
                    System.out.println(" [fail]");
                break;
                
            case 2:
                
                // Leemos el Photographer por teclado
                
                System.out.println("-- Nuevo Photographer --");
                Photographer photographer = ReadFromKeyboard.newPhotographer();
                
                System.out.print("Añadiendo Photographer...");
                if(human.addWorker(photographer))
                    System.out.println(" [done]");
                else
                    System.out.println(" [fail]");
                break;
                
            case 3: 
                
                // Leemos el Journalist por teclado
                
                System.out.println("-- Actualizar Journalist --");
                Journalist journalistToUpdate = ReadFromKeyboard.newJournalist();
                
                System.out.print("Actualizando Journalist...");
                if(human.updateWorker(journalistToUpdate))
                    System.out.println(" [done]");
                else
                    System.out.println(" [fail]");
                break;
                
            case 4:
                
                // Leemos el Photographer por teclado
                
                System.out.println("-- Actualizar Photographer --");
                Photographer photographerToUpdate = ReadFromKeyboard.newPhotographer();
                
                System.out.print("Actualizando Photographer...");
                if(human.updateWorker(photographerToUpdate))
                    System.out.println(" [done]");
                else
                    System.out.println(" [fail]");
                break;
        }
    }
}
