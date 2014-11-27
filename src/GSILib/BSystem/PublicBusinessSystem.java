/* 
 * Práctica 04 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.BSystem;
 
import GSILib.BModel.Document;
import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.workers.Journalist;
import GSILib.BModel.workers.Photographer;
import GSILib.connect.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
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
        p.setRegularResidence(pg.getRegularResidence());
        p.setHolidayResidence(pg.getHolidayResidence());

        // Si hemos llegado hasta aquí es que todo ha ido bien. 
        return true;    
    }

    @Override
    public PrintableNews[] getPendingNews() throws RemoteException {
        
        return this.getPendingNews(super.minReviewers);
    }

    @Override
    public PrintableNews[] getPendingNews(Integer numReviewers) throws RemoteException {
        
        List<PrintableNews> pendingNews = new ArrayList();
        int reviewers;
        
        for(Document document : super.documents){
            if (document.getClass().getName().equals("GSILib.BModel.documents.visualNews.PrintableNews")){
                
                // Tratamos solo las PrintableNews
                
                PrintableNews printableNews = (PrintableNews) document;
                
                if(printableNews.getReviewers() == null)
                    reviewers = 0;
                else
                    reviewers = printableNews.getReviewers().length;
                
                if(reviewers < numReviewers){
                    pendingNews.add(printableNews);
                    System.out.println(printableNews.toXML());
                }
            }
        }
        
        if (pendingNews.isEmpty())
            return null;
        return pendingNews.toArray(new PrintableNews[pendingNews.size()]);
    }

    @Override
    public boolean correctNews(PrintableNews printableNews) throws RemoteException {
        printableNews.getId();
        int i = -1;
        boolean found = false;
        while(i < super.documents.size() && !found){
            i++;
            if (super.documents.get(i).getId() == printableNews.getId())
                found = true;
        }
        if (found){
            super.documents.set(i, printableNews);
            return true;
        }
        return false;                
    }

    @Override
    public boolean validateNews(PrintableNews printableNews, Journalist journalist) throws RemoteException {
        return printableNews.addReviewer(journalist);
    }
    
}
