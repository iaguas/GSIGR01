/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.BSystem;

import GSILib.BModel.Document;
import GSILib.BModel.workers.Journalist;
import GSILib.BModel.workers.Photographer;
import GSILib.BModel.Worker;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Alvaro
 */
public class BusinessSystem implements EditorialOffice{
    
    private List<Worker> workers = new ArrayList<Worker>();
    
    @Override
    public boolean addJournalist(Journalist jr){
        return workers.add(jr);
    }

    @Override
    public boolean existJournalist(Journalist jr) {
        return workers.contains(jr);
    }

    @Override
    public boolean removeJournalist(Journalist jr) {
        return workers.remove(jr);
    }

    @Override
    public Journalist findJournalist(String ID) {
        int workersLength = workers.size();
        for (int i = 0; i < workersLength; i++) {
            if (workers.get(i).getId().equals(ID)) {
                return (Journalist) workers.get(i);
            }
        }
        return null;
    }

    @Override
    public boolean addPhotographer(Photographer pr) {
        return workers.add(pr);
    }

    @Override
    public boolean existPhotographer(Photographer pr) {
        return workers.contains(pr);
    }

    @Override
    public boolean removePhotographer(Photographer pr) {
        return workers.remove(pr);
    }

    @Override
    public Photographer findPhotographer(String ID) {
        int workersLength = workers.size();
        for (int i = 0; i < workersLength; i++) {
            if (workers.get(i).getId().equals(ID)) {
                return (Photographer) workers.get(i);
            }
        }
        return null;
    }

    @Override
    public boolean insertNews(WebNews wn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeNews(WebNews wn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addPrize(Document d, String prize) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removePrize(Document d, String prize) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Journalist getAuthor(Document d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Document[] getDocuments(Journalist j) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addReviewer(PrintableNews pn, Journalist rw) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeReviewer(PrintableNews pn, Journalist rw) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Journalist[] listReviewers(PrintableNews pn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addPicture(Picture p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removePicture(Picture p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Picture[] getPictures(Photographer p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WebResource getWebResource(String URL) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WebNews[] getIndexedNews(String keyword) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean createNewspaper(Date d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JournalIssue getNewspaper(Date d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteNewspaper(Date d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addNewsToIssue(JournalIssue ji, PrintableNews pn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Journalist[] getJournalist(JournalIssue ji) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Photographer getAuthor(Picture p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
