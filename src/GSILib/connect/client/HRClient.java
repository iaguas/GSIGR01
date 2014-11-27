/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.connect.client;

import GSILib.BModel.workers.Journalist;
import GSILib.BModel.workers.Photographer;
import GSILib.BSystem.PublicBusinessSystem;
import GSILib.connect.HumanRecGateway;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.exit;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Iñaki
 */
public class HRClient {
    private static int RMI_PORT=1099;
    private static Scanner keyboard;
    
    /**
     * TODO: JavaDoc
     * @param args 
     */
    public static void main(String[] args) throws RemoteException, IOException {
        
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
        
       // Ésta es una de las opciones
        switch (option){
            case 1:
                System.out.print("Introduzca la id para el periodista: ");
                String idjour = keyboard.nextLine();
                System.out.print("Introduzca el nombre: ");
                String journame = keyboard.nextLine();
                System.out.print("Introduzca la fecha de nacimiento: ");
                String jourbd = keyboard.nextLine();
                System.out.println("Introduzca el/los interes(es) (deje en vacio para terminar): \n");
                String inter = "not_empty";
                ArrayList interests = new ArrayList<>();
                int cont = 1;
                // Condición: variable inter, no vacio
                while(!"".equals(inter)){
                    System.out.print("Interes " + cont + ": ");
                    inter = keyboard.nextLine();
                    if(!"".equals(inter)){
                        interests.add(inter);
                        cont++;
                    }
                }
                Journalist newJournalist = new Journalist(idjour,journame,jourbd,interests);
                System.out.println("Añadiendo Journalist...");
                if(human.addWorker(newJournalist)){
                    System.out.println("Journalist con id " + idjour + " ha sido añadido con éxito!");
                }
            case 2:
                System.out.print("Introduzca la id para el fotógrafo: ");
                String idphoto = keyboard.nextLine();
                System.out.print("Introduzca el nombre: ");
                String photoname = keyboard.nextLine();
                System.out.print("Introduzca la fecha de nacimiento: ");
                String photobd = keyboard.nextLine();
                System.out.print("Introduzca el lugar de residencia habitual: ");
                String resiregu = keyboard.nextLine();
                System.out.print("Introduzca el lugar de residencia de vacaciones: ");
                String resivac = keyboard.nextLine();
                Photographer newPhotographer = new Photographer(idphoto,photoname,photobd,resiregu,resivac);
                if(human.addWorker(newPhotographer)){
                    System.out.println("Photographer con id " + idphoto + " ha sido añadido con éxito!");
                }
            case 3: 
                System.out.print("Introduzca la id del periodista a modificar: ");
                String ideditjour = keyboard.nextLine();
                System.out.println("A continuación, introduzca todos los campos nuevos: ");
                System.out.print("Introduzca el nombre: ");
                String editjourname = keyboard.nextLine();
                System.out.print("Introduzca la fecha de nacimiento: ");
                String editjourbd = keyboard.nextLine();
                System.out.println("Introduzca el/los interes(es) (deje en vacio para terminar): \n");
                String editinter = "not_empty";
                ArrayList editinterests = new ArrayList<>();
                int editcont = 1;
                // Condición: variable inter, no vacio
                while(!"".equals(editinter)){
                    System.out.print("Interes " + editcont + ": ");
                    inter = keyboard.nextLine();
                    if(!"".equals(inter)){
                        editinterests.add(inter);
                        editcont++;
                    }
                }
                Journalist editJournalist = new Journalist(ideditjour,editjourname,editjourbd,editinterests);
                System.out.println("Actualizando Journalist...");
                if(human.updateWorker(editJournalist)){
                    System.out.println("Journalist con id " + ideditjour + " ha sido añadido con éxito!");
                }
            case 4:
                System.out.print("Introduzca la id del fotógrafo a modificar: ");
                String ideditphoto = keyboard.nextLine();
                System.out.println("A continuación, introduzca todos los campos nuevos: ");
                System.out.print("Introduzca el nombre: ");
                String editphotoname = keyboard.nextLine();
                System.out.print("Introduzca la fecha de nacimiento: ");
                String editphotobd = keyboard.nextLine();
                System.out.print("Introduzca el lugar de residencia habitual: ");
                String editresiregu = keyboard.nextLine();
                System.out.print("Introduzca el lugar de residencia de vacaciones: ");
                String editresivac = keyboard.nextLine();
                Photographer editPhotographer = new Photographer(ideditphoto,editphotoname,editphotobd,editresiregu,editresivac);
                if(human.updateWorker(editPhotographer)){
                    System.out.println("Photographer con id " + ideditphoto + " ha sido añadido con éxito!");
                }                     
        }
        // Nuevo Journalist
        
        ArrayList interestsOfAlvaro = new ArrayList();
        
        interestsOfAlvaro.add("Discutir");
        interestsOfAlvaro.add("Tocar las narices");
        interestsOfAlvaro.add("Jugar al CS");

        Journalist journalistAlvaro = new Journalist("8", "Alvaro", "27/12/1993", interestsOfAlvaro);
        
        System.out.println("Añadiendo Journalist [" + human.addWorker(journalistAlvaro) + "]");
        
        journalistAlvaro.setName("Alvaro Octal");
        
        System.out.println("Actualizando Journalist [" + human.updateWorker(journalistAlvaro) + "]");
    }
}
