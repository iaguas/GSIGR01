 package GSILib.BSystem;
 
import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.workers.Journalist;
import GSILib.BModel.workers.Photographer;
import GSILib.connect.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
 
/**
 *
 * @author inigo
 */

public class PublicBusinessSystem extends BusinessSystem implements HumanRecGateway, ValidationGateway{

    @Override
    public Boolean addWorker(Journalist jn) throws RemoteException {
        return this.addJournalist(jn);
    }

    @Override
    public Boolean addWorker(Photographer pg) throws RemoteException {
        return this.addPhotographer(pg);
    }

    @Override
    public Boolean updateWorker(Journalist jn) throws RemoteException {
        // Rescatamos el periodista que queremos cambiar.
        Journalist j = this.findJournalist(jn.getId());
        // Asignamos todos los campos del nuevo periodista al viejo.
        j.setName(jn.getName());
        j.setBirthDate(jn.getBirthDate());
        j.setId(jn.getId());
        j.setInterests(new ArrayList<>(Arrays.asList(jn.getInterests())));
        
        // Si hemos llegado hasta aquí es que todo ha ido bien. 
        return true;
    }

    @Override
    public Boolean updateWorker(Photographer pg) throws RemoteException {
        // Rescatamos el periodista que queremos cambiar.
        Photographer p = this.findPhotographer(pg.getId());
        // Asignamos todos los campos del nuevo periodista al viejo.
        p.setName(pg.getName());
        p.setBirthDate(pg.getBirthDate());
        p.setId(pg.getId());
        p.setRegularResidence(pg.getRegularResidence());
        p.setHolidayResidence(pg.getHolidayResidence());

        // Si hemos llegado hasta aquí es que todo ha ido bien. 
        return true;    
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
