package GSILib.connect.server;

import GSILib.BModel.workers.Journalist;
import GSILib.BModel.workers.Photographer;
import GSILib.BSystem.PublicBusinessSystem;
import GSILib.connect.HumanRecGateway;
import java.rmi.RemoteException;

/**
 *
 * @author inigo
 */


public class PublicHumanRecGw extends PublicBusinessSystem implements HumanRecGateway{

    @Override
    public Boolean addWorker(Journalist jn) throws RemoteException {
        return this.addWorker(jn);
    }

    @Override
    public Boolean addWorker(Photographer pg) throws RemoteException {
        return this.addWorker(pg);
    }

    @Override
    public Boolean updateWorker(Journalist jn) throws RemoteException {
        return this.updateWorker(jn);
    }

    @Override
    public Boolean updateWorker(Photographer pg) throws RemoteException {
        return this.updateWorker(pg);
    }
    
}
