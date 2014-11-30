package GSILib.connect.server;

import GSILib.BModel.workers.Journalist;
import GSILib.BModel.workers.Photographer;
import GSILib.BSystem.PublicBusinessSystem;
import GSILib.connect.HumanRecGateway;
import java.rmi.RemoteException;

/**
 * This class implements the methods of HumanRecGateway interface.
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */


public class PublicHumanRecGw implements HumanRecGateway{

    PublicBusinessSystem pbs = new PublicBusinessSystem();
    
    public PublicHumanRecGw(PublicBusinessSystem pbs){
        this.pbs = pbs;
    }
    
    @Override
    public Boolean addWorker(Journalist jn) throws RemoteException {
        return pbs.addWorker(jn);
    }

    @Override
    public Boolean addWorker(Photographer pg) throws RemoteException {
        return pbs.addWorker(pg);
    }

    @Override
    public Boolean updateWorker(Journalist jn) throws RemoteException {
        return pbs.updateWorker(jn);
    }

    @Override
    public Boolean updateWorker(Photographer pg) throws RemoteException {
        return pbs.updateWorker(pg);
    }
    
}
