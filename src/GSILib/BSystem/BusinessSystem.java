/**
 * This interface retrieves the basic behaviour of an editorial office.
 * It does support basic operations of introducing, retrieving and listing the most
 *		basic information in the System 
 */
package GSILib.BSystem;

/* Según Molina estas no hacen falta pq ya están en la interface */
import GSILib.BModel.*;
import GSILib.BModel.workers.*;
import GSILib.BModel.documents.*;
import GSILib.BModel.documents.visualNews.*;
/* Esto no cambia, esto es cosa solo de la implementación */
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Alvaro Gil & Iñigo Aguas & Iñaki Garcia Gil & Iñigo Aguas & Iñaki Garcia
 */
public class BusinessSystem implements EditorialOffice{
    
    private List<Worker> workers = new ArrayList<Worker>();
    private List<Document> documents = new ArrayList<Document>();
    private List<Picture> pictures = new ArrayList<Picture>();
    private LinkedHashMap<Date, Newspaper> newspapers = new LinkedHashMap<Date, Newspaper>();
    
    private AtomicInteger atomicInteger = new AtomicInteger();
    
    @Override
    public boolean addJournalist(Journalist jr){
        //jr.setId(String);
        // TODO. Para poder introducir una nueva instancia de periodista hay que 
        //       asegurarse de que no haya ningún worker que tenga el mismo id.
        //       Igual hay que cambiar la coleccion???
        return this.workers.add(jr);
    }

    @Override
    public boolean existJournalist(Journalist jr) {
        return this.workers.contains(jr);
    }

    @Override
    public boolean removeJournalist(Journalist jr) {
        return this.workers.remove(jr);
    }

    @Override
    public Journalist findJournalist(String ID) {
        int workersLength = workers.size();
        for (int i = 0; i < workersLength; i++) {
            if (ID.equals(workers.get(i).getId())) {
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
        wn.setId(this.atomicInteger.getAndIncrement());
        return this.documents.add(wn);
    }
    
    @Override
    public boolean insertNews(PrintableNews pn) {
        pn.setId(this.atomicInteger.getAndIncrement());
        return this.documents.add(pn);
    }

    @Override
    public boolean insertNews(Teletype tp) {
        tp.setId(this.atomicInteger.getAndIncrement());
        return this.documents.add(tp);
    }

    @Override
    public boolean removeNews(WebNews wn) {
        return this.documents.remove(wn);
    }
    
    @Override
    public boolean removeNews(PrintableNews pn) {
        return this.documents.remove(pn);
    }

    @Override
    public boolean removeNews(Teletype tp) {
        return this.documents.remove(tp);
    }

    @Override
    public boolean addPrize(Document d, String prize) {
        return d.addPrize(prize);
    }

    @Override
    public boolean removePrize(Document d, String prize) {
        return d.removePrize(prize);
    }

    @Override
    public Journalist getAuthor(Document d) {
        return d.getAuthor();
    }

    @Override
    public Document[] getDocuments(Journalist j) {
        Document[] documentsOfAJournalist = null;
        int nextIndex = 0;
        
        for (int i = 0; i < this.documents.size(); i++){
            if (this.documents.get(i).getAuthor().equals(j)){ // Esto no debería funcionar pq no son el mismo objeto.
                documentsOfAJournalist[nextIndex] = this.documents.get(i);
                nextIndex++;
            }
        }
        return documentsOfAJournalist;
    }

    @Override
    public boolean addReviewer(PrintableNews pn, Journalist rw) {
        return pn.addReviewer(rw);
    }

    @Override
    public boolean removeReviewer(PrintableNews pn, Journalist rw) {
        return pn.removeReviewer(rw);
    }

    @Override
    public Journalist[] listReviewers(PrintableNews pn) {
        return pn.getReviewers();
    }

    @Override
    public boolean addPicture(Picture p) {
        return this.pictures.add(p);
    }

    @Override
    public boolean removePicture(Picture p) {
        return this.pictures.remove(p);
    }

    @Override
    public Picture[] getPictures(Photographer p) {
        Picture[] picturesOfAPhotographer = null;
        int nextIndex = 0;
        
        for (int i = 0; i < this.pictures.size(); i++){
            if (this.pictures.get(i).getAutor() == p){
                picturesOfAPhotographer[nextIndex] = this.pictures.get(i);
                nextIndex++;
            }
        }
        return picturesOfAPhotographer;
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
    public Newspaper getNewspaper(Date d) {
        return this.newspapers.get(d);
    }

    @Override
    public boolean deleteNewspaper(Date d) {
        return newspapers.remove(d) != null;
    }

    @Override
    public boolean addNewsToIssue(Newspaper np, PrintableNews pn) {
        return np.addNews(pn);
    }

    @Override
    public Journalist[] getJournalist(Newspaper np) {
        return np.getAuthors();
    }

    @Override
    public Photographer getAuthor(Picture p) {
        return p.getAutor();
    }
}
