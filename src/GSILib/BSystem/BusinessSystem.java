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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Alvaro Gil & Iñigo Aguas & Iñaki Garcia 
 */
public class BusinessSystem implements EditorialOffice{
    
    private HashMap<String, Worker> workers = new HashMap<>();
    private List<Document> documents = new ArrayList<>();
    private HashMap<String, Picture> pictures = new HashMap<>();
    private LinkedHashMap<Date, Newspaper> newspapers = new LinkedHashMap<>();
    
    private AtomicInteger atomicInteger = new AtomicInteger();
    
    @Override
    public boolean addJournalist(Journalist jr){
        if(! this.workers.containsKey(jr.getId())){
            this.workers.put(jr.getId(), jr);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean existJournalist(Journalist jr) {
        return this.workers.containsValue(jr);
    }

    @Override
    public boolean removeJournalist(Journalist jr) {
        String id = jr.getId();
        this.workers.remove(id);
        if(this.workers.containsKey(id))
            return false;
        else
            return true;
    }

    @Override
    public Journalist findJournalist(String ID) {
        if(this.workers.containsKey(ID)){
            return (Journalist) this.workers.get(ID);
        }
        else{
            return null;
        }
    }

    @Override
    public boolean addPhotographer(Photographer pr) {
        if(! this.workers.containsKey(pr.getId())){
            this.workers.put(pr.getId(), pr);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean existPhotographer(Photographer pr) {
        return workers.containsKey(pr.getId());
    }

    @Override
    public boolean removePhotographer(Photographer pr) {
        String id = pr.getId();
        this.workers.remove(id);
        if(this.workers.containsKey(id))
            return false;
        else
            return true;
    }

    @Override
    public Photographer findPhotographer(String ID) {
        if(this.workers.containsKey(ID)){
            return (Photographer) this.workers.get(ID);
        }
        else{
            return null;
        }
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
        ArrayList<Document> documentsOfAJournalist = new ArrayList<>();
        
        for (int i = 0; i < this.documents.size(); i++){
            if (this.documents.get(i).getAuthor().equals(j)){
                documentsOfAJournalist.add(this.documents.get(i));
            }
        }
        Document[] docs = documentsOfAJournalist.toArray(new Document[documentsOfAJournalist.size()]);
        return docs;
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
        if(! this.pictures.containsKey(p.getUrl())){
            this.pictures.put(p.getUrl(), p);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean removePicture(Picture p) {
        String url = p.getUrl();
        this.pictures.remove(url);
        if(this.pictures.containsKey(url))
            return false;
        else
            return true;
    }

    @Override
    public Picture[] getPictures(Photographer p) {
        ArrayList<Picture> picsOfAPhotographer = new ArrayList<>();

        for (Map.Entry e : pictures.entrySet()) {
            picsOfAPhotographer.add((Picture) e.getValue());
        }
        
        Picture[] pics = picsOfAPhotographer.toArray(new Picture[picsOfAPhotographer.size()]);
        return pics;
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
