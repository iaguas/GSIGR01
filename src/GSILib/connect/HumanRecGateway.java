package GSILib.connect;

import GSILib.BModel.workers.Journalist;
import GSILib.BModel.workers.Photographer;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *  This interface represents the access HR people has to the BusinessSystem.
 * @author carlos.lopez
 */
public interface HumanRecGateway extends Remote {

    /**
     * Adds a new worker to the System
     * @param jn The new journalist
     * @return  Boolean if and only if it could be added and did not exist in the system before.
     * @throws RemoteException If something goes wrong in the connection/serialization.
     */
    public Boolean addWorker(Journalist jn) throws RemoteException;
    
    /**
     * Adds a new worker to the System
     * @param pg The new journalist
     * @return  Boolean if and only if it could be added and did not exist in the system before.
     * @throws RemoteException  If something goes wrong in the connection/serialization.
     */
    public Boolean addWorker(Photographer pg)  throws RemoteException;;
    
    /**
     * Updates the information about a worker to the system. The journalist with the same ID is
     *      replaced by the new one, although it remains author of his pictures, etc.
     * @param jn The new version of the journalist
     * @return  Boolean True if and only if the journalist existed in the system before and could be replaced.
     * @throws RemoteException  If something goes wrong in the connection/serialization.
     */    
    public Boolean updateWorker(Journalist jn)  throws RemoteException;;
    
    /**
     * Updates the information about a worker to the system. The photographer with the same ID is
     *      replaced by the new one, although it remains author of his pictures, etc.
     * @param pg The new version of the photographer
     * @return  Boolean True if and only if the photographer existed in the system before and could be replaced.
     * @throws RemoteException  If something goes wrong in the connection/serialization.
     */    
    public Boolean updateWorker(Photographer pg)  throws RemoteException;;
    
    
}
