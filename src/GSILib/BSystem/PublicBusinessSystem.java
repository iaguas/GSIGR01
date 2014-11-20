 package GSILib.BSystem;
 
import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.workers.Journalist;
import GSILib.BModel.workers.Photographer;
import GSILib.connect.*;
import java.rmi.RemoteException;
 
/**
 *
 * @author inigo
 */

public class PublicBusinessSystem extends BusinessSystem implements HumanRecGateway, ValidationGateway{

    @Override
    public Boolean addWorker(Journalist jn) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean addWorker(Photographer pg) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean updateWorker(Journalist jn) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean updateWorker(Photographer pg) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PrintableNews[] getPendingNews() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PrintableNews[] getPendingNews(Integer numReviewers) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean correctNews(PrintableNews pn) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean validateNews(PrintableNews pn, Journalist jn) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
