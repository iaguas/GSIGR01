/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.connect.client;

import GSILib.BSystem.PublicBusinessSystem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Iñaki
 */
public class VLClient {
    private static int RMI_PORT=1099;
    
    public static void main(String args[]) throws RemoteException{
        // TODO: retirar éste código
        /* // Instanciamos el lector de teclado 
        Scanner keyboard = new Scanner(System.in);
        // Todavía por decidir cuales serán los datos de entrada y en qué
        // formatos recogerlos
        System.out.println("Bienvenido al servicio BusinessSystem de acceso remoto");
        System.out.println("Introduzca los valores requeridos para acceder al sistema");
        
        System.out.println("Dirección del servidor: ");
        String direccion = keyboard.nextLine();
        // Puerto de escucha de conexiones
        System.out.println("Puerto del servidor: ");
        int puerto = keyboard.nextInt();
        
        System.out.println("Tag del objeto remoto: ");
        String tag = keyboard.nextLine();
        
        // Comprobación de conexión correcta
        */
        // Todavía por decidir cuales serán los datos de entrada y en qué
        // formatos recogerlos
        System.out.println("Bienvenido al servicio BusinessSystem de acceso remoto");
        System.out.println("Introduzca los valores requeridos para acceder al sistema");
        
        System.out.println("Dirección del servidor: ");
        // Inicializamos un BufferedReader para la lectura por teclado
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String remoteMachine;
        try{
            remoteMachine = br.readLine();
        }
        catch (IOException ioe){
            System.out.println("Exception en la lectura" + ioe.getMessage());
            // Asignamos la máquina local como la máquina remota por defecto
            remoteMachine="localhost";
        }
        
        // Puerto de escucha de conexiones
        System.out.println("Puerto del servidor: ");
        int port;
        try{
            port = Integer.parseInt(br.readLine());
        }
        catch (IOException ioe){
            System.out.println("Exception en la lectura" + ioe.getMessage());
            // Asignamos el puerto por defecto 1099
            port = 1099;
        }
        
        System.out.println("Tag del objeto remoto: ");
        String tag;
        try{
            tag = br.readLine();
        }
        catch (IOException ioe){
            System.out.println("Exception en la lectura" + ioe.getMessage());
            // Asignamos el tag por defecto como nulo
            tag = "null";
        }
        
        // Comprobamos poder hacer la conexión
        try{
            // Conectando al registro remoto
            Registry registry = LocateRegistry.getRegistry(remoteMachine);
            // Enlazando objeto remoto como si fuera local
            PublicBusinessSystem comp = (PublicBusinessSystem) registry.lookup(tag);
            // Usar el objeto: realizar las operaciones deseadas
            // TODO: introducir las funcionalidades que pueda ejecutar esta clase
            // (métodos de PublicBusinessSystem)
            //System.out.println("5 plus 6 is " + comp.sum(5,6));
        } 
        catch (RemoteException | NotBoundException ex) {
            System.out.println("Exception in connection : "+ex.getMessage());
        }
        
        
    }
    
}
