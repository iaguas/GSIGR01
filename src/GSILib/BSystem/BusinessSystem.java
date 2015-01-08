/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.BSystem;

import GSILib.BModel.*;
import GSILib.BModel.workers.*;
import GSILib.BModel.documents.*;
import GSILib.BModel.documents.visualNews.*;
import GSILib.Serializable.XMLHandler;
import GSILib.Serializable.XMLRepresentable;
import GSILib.persistence.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This is the class BusinessSystem.
 * In this class we implement the system it self with help of all the classes of
 * the model.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class BusinessSystem implements EditorialOffice, ODSPersistent, XMLRepresentable{
    
    // Colecciones de objetos propias del sistema.
    protected final HashMap<String, Worker> workers = new HashMap<>(); // Trabajadores (periodistas y fotografos)
    protected final List<Document> documents = new ArrayList<>(); // Documentos (todos los tipos de noticias)
    protected final HashMap<String, Picture> pictures = new HashMap<>(); // Imagenes
    protected final LinkedHashMap<String, Newspaper> newspapers = new LinkedHashMap<>(); // Periodicos
    
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
    
    protected final int minReviewers = 6;

    // Clase para dar el ID a las noticias (de cualquier tipo) de forma única.
    private final AtomicInteger atomicInteger = new AtomicInteger();
    
    @Override
    public boolean addJournalist(Journalist jr){
        // Comprobamos que el periodista no se encuentra ya entre los trabajadores.
        if(! this.workers.containsKey(jr.getId())){
            // Añadimos el periodista si no está
            this.workers.put(jr.getId(), jr);
            // Devolvemos que todo ha ido bien.
            return true;
        }
        else{
            // Devolvemos que algo ha fallado.
            return false;
        }
    }

    @Override
    public boolean existJournalist(Journalist jr) {
        // Comprobamos que existe el periodista y lo devolvemos.
        return this.workers.containsValue(jr);
    }

    @Override
    public boolean removeJournalist(Journalist jr) {
        // Recogemos la ID del periodista.
        String id = jr.getId();
        // Lo borramos del sistema con la ID seleccionada.
        this.workers.remove(id);
        // Devolemos el resultado de la operación.
        return !this.workers.containsKey(id);
    }

    @Override
    public Journalist findJournalist(String ID) {
        // Comprobamos que el periodista existe.
        if(this.workers.containsKey(ID)){
            // Devolvemos el periodista.
            return (Journalist) this.workers.get(ID);
        }
        else{
            // Devolvemos null porque el periodista no existe.
            return null;
        }
    }
    
    @Override
    public Journalist[] getJournalists(){
        
        // Creamos una nueva lista
        
        ArrayList<Journalist> journalists = new ArrayList();
        
        for(Worker worker : this.workers.values()){
            if (worker.getClass().getName().equals("GSILib.BModel.workers.Journalist")){
                journalists.add((Journalist) worker);
            }
        }
        
        // Lo devolvemos como un array
        
        if(journalists.isEmpty())
            return null;
        return journalists.toArray(new Journalist[journalists.size()]);
    }

    @Override
    public boolean addPhotographer(Photographer pr) {
        // Comprobamos que el fotografo no se encuentra ya entre los trabajadores.
        if(! this.workers.containsKey(pr.getId())){
            // Añadimos el fotografo si no está
            this.workers.put(pr.getId(), pr);
            // Devolvemos que todo va bien.
            return true;
        }
        else{
            // Devolvemos que algo ha fallado.
            return false;
        }
    }

    @Override
    public boolean existPhotographer(Photographer pr) {
        // Comprobamos que el fotografo existe y lo devolvemos.
        return workers.containsKey(pr.getId());
    }

    @Override
    public boolean removePhotographer(Photographer pr) {
        // Recogemos la ID del periodista.
        String id = pr.getId();
        // Lo borramos del sistema con la ID seleccionada.
        this.workers.remove(id);
        // Devolemos el resultado de la operación.
        return !this.workers.containsKey(id);
    }

    @Override
    public Photographer findPhotographer(String ID) {
        // Comprobamos que el fotografo existe.
        if(this.workers.containsKey(ID)){
            // Devolvemos el fotografo.
            return (Photographer) this.workers.get(ID);
        }
        else{
            // Devolvemos null porque el fotografo no existe.
            return null;
        }
    }

    @Override
    public boolean insertNews(WebNews wn) {
        // Asignamos un ID de forma unívoca a la noticia.
        wn.setId(this.atomicInteger.getAndIncrement());
        // Introducimos la noticia en la colección de documentos.
        return this.documents.add(wn);
    }
    
    @Override
    public boolean insertNews(PrintableNews pn) {
        // Asignamos un ID de forma unívoca a la noticia.
        pn.setId(this.atomicInteger.getAndIncrement());
        // Introducimos la noticia en la colección de documentos.
        return this.documents.add(pn);
    }

    @Override
    public boolean insertNews(Teletype tp) {
        // Asignamos un ID de forma unívoca a la noticia.
        tp.setId(this.atomicInteger.getAndIncrement());
        // Introducimos la noticia en la colección de documentos.
        return this.documents.add(tp);
    }

    @Override
    public boolean removeNews(WebNews wn) {
        // Eliminamos la noticia de la colección de documentos.
        return this.documents.remove(wn);
    }
    
    @Override
    public boolean removeNews(PrintableNews pn) {
        // Eliminamos la noticia de la colección de documentos.
        return this.documents.remove(pn);
    }

    @Override
    public boolean removeNews(Teletype tp) {
        // Eliminamos la noticia de la colección de documentos.
        return this.documents.remove(tp);
    }

    @Override
    public boolean addPrize(Document d, String prize) {
        // Añadimos el premio a la colección de premios.
        return d.addPrize(prize);
    }

    @Override
    public boolean removePrize(Document d, String prize) {
        // Eliminamos el premio de la colección de premios.
        return d.removePrize(prize);
    }
    
    @Override
    public String[] listPrizes(Document d){
        // Nota. Para que luego no nos diga que somos cutres hazlo con un iterator
        // tienes ejemplos en listDocuments y listReviewers.
        return d.getPrizes();
    }

    @Override
    public Journalist getAuthor(Document d) {
        // Devolvemos el autor de un documento.
        return d.getAuthor();
    }

    @Override
    public Document[] getDocuments(Journalist j) {
        // Declaramos una lista de documentos que inicializamos.
        ArrayList<Document> documentsOfAJournalist = new ArrayList<>();
        // Recorremos todos los documentos
        for (Document d : this.documents) {
            // Comprobamos cuales coinciden con el autor.
            if (d.getAuthor().equals(j)) {
                // Guardamos los que coinciden en la lista.
                documentsOfAJournalist.add(d);
            }
        }
        // Convertimos la lista a tabla y la devolvemos.
        Document[] docs = documentsOfAJournalist.toArray(new Document[documentsOfAJournalist.size()]);
        return docs;
    }
    
    @Override
    public PrintableNews[] getPrintableNewsFromAuthor(Journalist journalist){
        
        // Creamos una list de PrintableNews
        
        ArrayList<PrintableNews> printableNews = new ArrayList();
        
        for(Document document: this.documents){
            if(document.getClass().getName().equals("GSILib.BModel.documents.visualNews.PrintableNews") && document.getAuthor().equals(journalist)){
                printableNews.add((PrintableNews) document);
            }
        }
        
        // Lo devolvemos como un array
        
        if(printableNews.isEmpty())
            return null;
        return printableNews.toArray(new PrintableNews[printableNews.size()]); 
    }

    @Override
    public boolean addReviewer(PrintableNews pn, Journalist rw) {
        // Añadimos un revisor a la lista de revisores.
        return pn.addReviewer(rw);
    }

    @Override
    public boolean removeReviewer(PrintableNews pn, Journalist rw) {
        // Eliminamos un revisor de la lista de revisores.
        return pn.removeReviewer(rw);
    }

    @Override
    public Journalist[] listReviewers(PrintableNews pn) {
        // Extraemos los revisores de una noticia y los devolvemos.
        return pn.getReviewers();
    }

    @Override
    public boolean addPicture(Picture p) {
        // Comprobamos que la foto no exista ya.
        if(! this.pictures.containsKey(p.getUrl())){
            // Si no existe, la añadimos.
            this.pictures.put(p.getUrl(), p);
            // Devolvemos que todo ha ido bien.
            return true;
        }
        else{
            // Devolvemo que algo ha ido mal.
            return false;
        }
    }

    @Override
    public boolean removePicture(Picture p) {
        // Rescatamos el identificador (URL) de la imagen.
        String url = p.getUrl();
        // Eliminamo la imagen con esa URL.
        this.pictures.remove(url);
        // Devolvemos la comprobación de que se ha eliminado.
        return !this.pictures.containsKey(url);
    }

    @Override
    public Picture[] getPictures(Photographer p) {
        // Declaramos una lista de imagenes vacia.
        ArrayList<Picture> picsOfAPhotographer = new ArrayList<>();
        // Recorremos todas las imagenes en busca de aquellas que haya hecho el fotografo.
        for (Map.Entry e : pictures.entrySet()) {
            picsOfAPhotographer.add((Picture) e.getValue());
        }
        // Convertimos la lista a tabla y la devolvemos.
        Picture[] pics = picsOfAPhotographer.toArray(new Picture[picsOfAPhotographer.size()]);
        return pics;
    }
    
    @Override
    public Picture getPicture(String url) {
        // Devolvemos la imagen.
        return this.pictures.get(url);
    }

    @Override
    public WebResource getWebResource(String URL) {
        
        if(this.pictures.containsKey(URL)){
            // Devolvemos la foto solicitada.
            return (WebResource) pictures.get(URL);
        }
        else {
            
            WebNews webNews;
            
            for(Document document: documents){
                if (document.getClass().getName().equals("GSILib.BModel.documents.visualNews.WebNews")){
                    
                    webNews = (WebNews) document;
                    
                    // Si es la webnews que buscamos, la devolvemos.
                    if(webNews.getUrl().equals(URL)){
                        return (WebResource) webNews;
                    }
                }  
            }
        }
        // Si no encontramos nada, devolvemos null.
        return null;
    }
    
    @Override
    public WebNews[] getWebNews() {
        
        List<WebNews> webNews = new ArrayList();
        
        for(Document document : this.documents){
            if (document.getClass().getName().equals("GSILib.BModel.documents.visualNews.WebNews")){
                webNews.add((WebNews) document);
            }
        }
        if(webNews.isEmpty())
            return null;
        return webNews.toArray(new WebNews[webNews.size()]);
    }
    
    @Override
    public WebNews getWebNews(String URL) {
            
        WebNews webNews;

        for(Document document: documents){
            if (document.getClass().getName().equals("GSILib.BModel.documents.visualNews.WebNews")){

                webNews = (WebNews) document;

                // Si es la webnews que buscamos, la devolvemos.
                
                if(webNews.getUrl().equals(URL)){
                    return webNews;
                }
            }  
        }
        
        return null;
    }
    
    @Override
    public PrintableNews getPrintableNews(int printableNewsID) {
        
        for(Document document: documents){ 
            
            if (document.getClass().getName().equals("GSILib.BModel.documents.visualNews.PrintableNews")){
                
                if(document.getId() == printableNewsID){
                    return (PrintableNews) document;
                }
            }  
        }
        
        return null;
    }

    @Override
    public WebNews[] getIndexedNews(String keyword) {
        // Creamos una lista de webnews.
        ArrayList<WebNews> list = new ArrayList<>();
        // Creamos una instancia de webnews.
        WebNews wn = new WebNews("void","void",new Journalist("void", "void", "void", null),"void");
        
        // Recorremos todos los documentos buscando las webnews.
        for(Document d: documents)
            // Buscamos solo los documentos de tipo webnews.
            if (d.getClass().getName().equals("GSILib.BModel.documents.visualNews.WebNews")){
                wn = (WebNews) d;
                // Si es la webnews que buscamos, la devolvemos.
                if(wn.getKeyWords().contains(keyword))
                    list.add(wn);
            }
        
        // Devolvemos la tabla de webnews
        WebNews[] arrayWebNews = list.toArray(new WebNews[list.size()]);
        return arrayWebNews;
    }  

    @Override
    public boolean createNewspaper(Date date) {
        
        Newspaper newspaper = new Newspaper(date);
        
        if (! this.newspapers.containsKey(newspaper.getDate())){
            
            
            this.newspapers.put(newspaper.getDate(), newspaper);
        
            // Retornamos el resultado de la creación.

            return this.newspapers.containsKey(newspaper.getDate());
        }
        else{
            return false;
        }
    }
    
    @Override
    public boolean createNewspaper(String date){
        try {
            System.out.println(date);
            return this.createNewspaper(this.simpleDateFormat.parse(date));
        } catch (ParseException ex) {
            System.out.println("fallo sdf");
            return false;
        }
    }
    
    @Override
    public Newspaper insertNewspaper(Newspaper newspaper){
        return this.newspapers.put(newspaper.getDate(), newspaper);
    }

    @Override
    public Newspaper getNewspaper(Date date) {
        
        // Recogemos un periodico en función a su fecha.
        
        return this.getNewspaper(this.simpleDateFormat.format(date));
    }
    
    @Override
    public Newspaper getNewspaper(String date) {
        
        // Recogemos un periodico en función a su fecha.
        
        return this.newspapers.get(date);
    }
    
    @Override
    public Newspaper[] getNewspapersByPartialKey(String date){
        
        // Creamos una lista de Newspapers
        
        ArrayList<Newspaper> newspapers = new ArrayList<Newspaper>(this.newspapers.values());
        ArrayList<Newspaper> newspapersFiltered = new ArrayList<>();
        
        for(Newspaper newspaper : newspapers){
            if(newspaper.getDate().startsWith(date)){
                newspapersFiltered.add(newspaper);
            }
        }
        
        // Devolvemos un array
                     
        if (newspapersFiltered.isEmpty())
            return null;
        return newspapersFiltered.toArray(new Newspaper[newspapersFiltered.size()]);
    }
    
    @Override
    public Newspaper[] getNewspapers(){
        
        // Creamos una lista de Newspapers
        
        ArrayList<Newspaper> newspapers = new ArrayList<Newspaper>(this.newspapers.values());
        
        // Devolvemos un array
                     
        if (newspapers.isEmpty())
            return null;
        return newspapers.toArray(new Newspaper[newspapers.size()]);
    }
    
    @Override
    public boolean deleteNewspaper(Date d) {
        // Eliminamos el periodico de una fecha.
        return newspapers.remove(d) != null;
    }

    @Override
    public boolean addNewsToIssue(Newspaper np, PrintableNews pn) {
        // Añadimos una noticia a un periodico.
        return np.addNews(pn);
    }

    @Override
    public Journalist[] getJournalist(Newspaper np) {
        // Retornamos el nombre de los periodistas que han trabajado en un
        // periodico concreto.
        return np.getAuthors();
    }

    @Override
    public Photographer getAuthor(Picture p) {
        // Retornamos el autor de una imagen.
        return p.getAuthor();
    }
 
    /**
     * Reads the Teletypes from an .ods file and imports them into the system
     * @param f The input file (.ods, Open Office spreadsheet) to read from
     * @return the number of Teletypes correctly added to the system
     */
    public int importTeletypes(File f){
        // Leemos y almacenamos los datos que hay en la hoja correspondiente.
        // Teletypes (hoja 0)
        Sheet sheet = null;
        try {
            sheet = SpreadSheet.createFromFile(f).getSheet(0);
        } 
        catch (IOException ex) {
            System.err.printf("No se encontró el archivo.\n");
        }
        
        int i = 0;
        // Iteramos sobre todos los elementos de la hoja de cálculo.
        while(sheet.getRowCount()>i && (! sheet.getCellAt(i,0).isEmpty())){
            // Importamos el ID del periodista del teletipo.
            String authorID = sheet.getCellAt(0,i).getTextValue();
            // Importamos el titular del teletipo.
            String headline = sheet.getCellAt(1,i).getTextValue();
            // Importamos el cuerpo del teletipo.
            String body = sheet.getCellAt(2,i).getTextValue();
            // Buscamos al periodista en el sistema.
            Teletype tt = new Teletype(headline, body, this.findJournalist(authorID));
            // Para importar los premios hacemos otro bucle.
            int j = 3;
            while(sheet.getColumnCount()>j && (! sheet.getCellAt(j,i).getTextValue().equals("")) ){
                // Importamos los premios conforme los leemos.
                tt.addPrize((String) sheet.getCellAt(j,i).getValue());
                j++;
            }
            // Guardamos el teletipo en el sistema.
            this.insertNews(tt);
            // Avanzamos a la siguiente fila.
            i++;
        }
        return i;
    }
    
    /**
     * Reads the PrintableNews from an .ods file and imports them into the system.
     * In order to make a way to import variable thing, we are going to put a separator
     * between the data. It is, first of all, we are going to take the usual data of
     * a printable news and then we take the prizes, then an asterics (*), then 
     * the reviewers, then an # and last the pictures. Putting the * and # is 
     * compulsory.
     * @param f The input file (.ods, Open Office spreadsheet) to read from
     * @return the number of PrintableNews correctly added to the system
     */
    public int importPrintableNews(File f){
        // Leemos y almacenamos los datos que hay en la hoja correspondiente.
        // PrintableNews (hoja 1)
        Sheet sheet = null;
        try {
            sheet = SpreadSheet.createFromFile(f).getSheet(0);
        } 
        catch (IOException ex) {
            System.err.printf("No se encontró el archivo.\n");
        }
        
        int i = 0;
        // Iteramos sobre todos los elementos de la hoja de cálculo.
        while(sheet.getRowCount()>i && (! sheet.getCellAt(i,0).isEmpty())){
            // Importamos el ID del periodista revisor de la noticia imprimible.
            String authorID = sheet.getCellAt(0,i).getTextValue();
            // Importamos el titular de la noticia imprimible.
            String headline = sheet.getCellAt(1,i).getTextValue();
            // Importamos el cuerpo de la noticia imprimible.
            String body = sheet.getCellAt(2,i).getTextValue();
            // Buscamos al periodista en el sistema.
            PrintableNews pn = new PrintableNews(headline, body, this.findJournalist(authorID));
            
            // Para importar los premios hacemos otro bucle.
            int j = 3;
            while(sheet.getColumnCount()>j && !(sheet.getCellAt(j,i).getTextValue()).equals("*") && (! sheet.getCellAt(j,i).isEmpty())){
                // Importamos los premios conforme los leemos.
                pn.addPrize(sheet.getCellAt(j,i).getTextValue());
                j++;
            }
            
            // Avanzamos las columnas para coger el siguiente valor, las fotos.
            j++;
            
            // Para importar las fotos hacemos otro bucle.
            while(sheet.getColumnCount()>j && !(sheet.getCellAt(j,i).getTextValue()).equals("#") && (! sheet.getCellAt(j,i).isEmpty())){
                // Importamos los premios conforme los leemos.
                pn.addReviewer(findJournalist(sheet.getCellAt(j,i).getTextValue()));
                j++;
            }
            
            // Avanzamos las columnas para coger el siguiente valor, los revisores.
            j++; 
            // Para importar los revisores, hacemos otro bucle más
            while(sheet.getColumnCount()>j && (! sheet.getCellAt(j,i).isEmpty())){
                // Importamos los premios conforme los leemos.
                pn.addPicture(this.getPicture(sheet.getCellAt(j,i).getTextValue()));
                j++;
            }
            // Guardamos el la noticia imprimible en el sistema.
            this.insertNews(pn);
            // Avanzamos a la siguiente fila.
            i++;
        }
        return i;
    }

    @Override
    public boolean loadFromFile(File f) {
        // Variables de control
        int i, j;
        
        // Leemos y almacenamos los datos que hay en la hoja correspondiente.
        Sheet sheetJournalist = null;
        Sheet sheetPhotographers = null;
        Sheet sheetTeletypes = null;
        Sheet sheetPrintableNews = null;
        Sheet sheetWebNews = null;
        Sheet sheetPictures = null;
        Sheet sheetNewspapers = null;
        try {
            sheetJournalist = SpreadSheet.createFromFile(f).getSheet(0);
            sheetPhotographers = SpreadSheet.createFromFile(f).getSheet(1);
            sheetTeletypes = SpreadSheet.createFromFile(f).getSheet(2);
            sheetPrintableNews = SpreadSheet.createFromFile(f).getSheet(3);
            sheetWebNews = SpreadSheet.createFromFile(f).getSheet(4);
            sheetPictures = SpreadSheet.createFromFile(f).getSheet(5);
            sheetNewspapers = SpreadSheet.createFromFile(f).getSheet(6);
        } 
        catch (IOException ex) {
            System.err.printf("No se encontró el archivo.\n");
        }
        
        
        System.out.println(sheetNewspapers.getCellAt(0,0).getTextValue());
        
        // Empezamos a importar las cosas por el orden necesario para poder hacer
        // que nunca dé null al buscar algo en el almacén de datos.
        // Comenzamos importandos los trabajadores.
        
        i = 0;
        // Iteramos sobre todos los periodistas de la hoja de cálculo.
        while(sheetJournalist.getRowCount()>i && (! sheetJournalist.getCellAt(i,0).isEmpty())){
            // Importamos el ID del periodista.
            String id = sheetJournalist.getCellAt(0,i).getTextValue();
            // Importamos el nombre del periodista
            String name = sheetJournalist.getCellAt(1,i).getTextValue();
            // Importamos la fecha de nacimiento del periodista.
            String dateBorn = sheetJournalist.getCellAt(2,i).getTextValue();
            // Importamos los intereses del periodista
            j = 3;
            // Para importar los intereses hacemos otro bucle.
            ArrayList<String> interests = new ArrayList<>();
            while(sheetJournalist.getColumnCount()>j && (! sheetJournalist.getCellAt(j,i).getTextValue().equals("")) ){
                // Importamos los intereses conforme los leemos.
                interests.add(sheetJournalist.getCellAt(j,i).getTextValue());
                j++;
            }
            
            // Creamos el periodista al sistema.
            Journalist jr = new Journalist(id, name, dateBorn, interests);
            // Guardamos el periodista en el sistema.
            this.addJournalist(jr);
            // Avanzamos a la siguiente fila.
            i++;
        }
        
        i = 0;
        // Iteramos sobre todos los fotografos de la hoja de cálculo.
        while(sheetJournalist.getRowCount()>i && (! sheetJournalist.getCellAt(i,0).isEmpty())){
            // Importamos el ID del periodista.
            String id = sheetJournalist.getCellAt(0,i).getTextValue();
            // Importamos el nombre del periodista
            String name = sheetJournalist.getCellAt(1,i).getTextValue();
            // Importamos la fecha de nacimiento del periodista.
            String dateBorn = sheetJournalist.getCellAt(2,i).getTextValue();
            // Importamos los intereses del periodista
            j = 3;
            // Para importar los intereses hacemos otro bucle.
            ArrayList<String> interests = new ArrayList<>();
            while(sheetJournalist.getColumnCount()>j && (! sheetJournalist.getCellAt(j,i).getTextValue().equals("")) ){
                // Importamos los intereses conforme los leemos.
                interests.add(sheetJournalist.getCellAt(j,i).getTextValue());
                j++;
            }
            
            // Creamos el periodista al sistema.
            Journalist jr = new Journalist(id, name, dateBorn, interests);
            // Guardamos el periodista en el sistema.
            this.addJournalist(jr);
            // Avanzamos a la siguiente fila.
            i++;
        }
        
        i = 0;
        // Iteramos sobre todos los fotografos de la hoja de cálculo.
        while(sheetPhotographers.getRowCount()>i && (! sheetPhotographers.getCellAt(i,0).isEmpty())){
            // Importamos el ID del fotografo.
            String id = sheetPhotographers.getCellAt(0,i).getTextValue();
            // Importamos el nombre del fotografo
            String name = sheetPhotographers.getCellAt(1,i).getTextValue();
            // Importamos la fecha de nacimiento del fotografo.
            String dateBorn = sheetPhotographers.getCellAt(2,i).getTextValue();
            // Importamos la dirección habitual.
            String usualAdress = sheetPhotographers.getCellAt(3,i).getTextValue();
            // Importamos la dirección de vacaciones.
            String holidayAdress = sheetPhotographers.getCellAt(4,i).getTextValue();

            // Creamos el periodista al sistema.
            Photographer pg = new Photographer(id, name, dateBorn, usualAdress, holidayAdress);            
            // Guardamos el periodista en el sistema.
            this.addPhotographer(pg);
            // Avanzamos a la siguiente fila.
            i++;
        }
        
        i=0;
        // Iteramos sobre todos las fotos de la hoja de cálculo.
        while(sheetPictures.getRowCount()>i && (! sheetPictures.getCellAt(i,0).isEmpty())){
            // Importamos el ID del fotografo.
            String authorId = sheetPictures.getCellAt(0,i).getTextValue();
            // Importamos la url de la foto.
            String url = sheetPictures.getCellAt(1,i).getTextValue();
                      
            // Guardamos el periodista en el sistema.
            this.addPicture(new Picture(url, this.findPhotographer(authorId)));
            // Avanzamos a la siguiente fila.
            i++;
        }
        
        i = 0;
        // Iteramos sobre todos los teletipos de la hoja de cálculo.
        while(sheetTeletypes.getRowCount()>i && (! sheetTeletypes.getCellAt(i,0).isEmpty())){
            // Importamos el ID del periodista del teletipo.
            String authorID = sheetTeletypes.getCellAt(1,i).getTextValue();
            // Importamos el titular del teletipo.
            String headline = sheetTeletypes.getCellAt(2,i).getTextValue();
            // Importamos el cuerpo del teletipo.
            String body = sheetTeletypes.getCellAt(3,i).getTextValue();
            // Buscamos al periodista en el sistema.
            Teletype tt = new Teletype(headline, body, this.findJournalist(authorID));
            // Para importar los premios hacemos otro bucle.
            j = 4;
            String str = sheetTeletypes.getCellAt(j,i).getTextValue();
            while(sheetTeletypes.getColumnCount()>j && (! sheetTeletypes.getCellAt(j,i).getTextValue().equals("")) ){
                // Importamos los premios conforme los leemos.
                tt.addPrize((String) sheetTeletypes.getCellAt(j,i).getValue());
                j++;
            }
            // Guardamos el teletipo en el sistema.
            this.insertNews(tt);
            // Avanzamos a la siguiente fila.
            i++;
        }
        
        i = 0;
        // Iteramos sobre todas las noticias imprimibles de la hoja de cálculo.
        while(sheetPrintableNews.getRowCount()>i && (! sheetPrintableNews.getCellAt(i,0).isEmpty())){
            // Importamos el ID del periodista revisor de la noticia imprimible.
            String authorID = sheetPrintableNews.getCellAt(1,i).getTextValue();
            // Importamos el titular de la noticia imprimible.
            String headline = sheetPrintableNews.getCellAt(2,i).getTextValue();
            // Importamos el cuerpo de la noticia imprimible.
            String body = sheetPrintableNews.getCellAt(3,i).getTextValue();
            // Buscamos al periodista en el sistema.
            PrintableNews pn = new PrintableNews(headline, body, this.findJournalist(authorID));
            
            // Para importar los premios hacemos otro bucle.
            j = 4;
            while(sheetPrintableNews.getColumnCount()>j && !(sheetPrintableNews.getCellAt(j,i).getTextValue()).equals("*") && (! sheetPrintableNews.getCellAt(j,i).isEmpty())){
                // Importamos los premios conforme los leemos.
                pn.addPrize(sheetPrintableNews.getCellAt(j,i).getTextValue());
                j++;
            }
            
            // Avanzamos las columnas para coger el siguiente valor, las fotos.
            j++;
            
            // Para importar los revisores hacemos otro bucle.
            while(sheetPrintableNews.getColumnCount()>j &&  !(sheetPrintableNews.getCellAt(j,i).getTextValue()).equals("#") && (! sheetPrintableNews.getCellAt(j,i).isEmpty())){
                // Importamos los revisores conforme los leemos.
                pn.addReviewer(findJournalist(sheetPrintableNews.getCellAt(j,i).getTextValue()));
                j++;
            }
            
            // Avanzamos las columnas para coger el siguiente valor, los revisores.
            j++; 
            // Para importar las fotos, hacemos otro bucle más
            while(sheetPrintableNews.getColumnCount()>j && (! sheetPrintableNews.getCellAt(j,i).isEmpty())){
                // Importamos las fotos conforme los leemos.
                pn.addPicture(this.getPicture(sheetPrintableNews.getCellAt(j,i).getTextValue()));
                j++;
            }

            // Guardamos el la noticia imprimible en el sistema.
            this.insertNews(pn);
            // Avanzamos a la siguiente fila.
            i++;
        }
        
        i = 0;
        // Iteramos sobre todas las noticias web de la hoja de cálculo.
        while(sheetWebNews.getRowCount()>i && (! sheetWebNews.getCellAt(i,0).isEmpty())){
            // Importamos el ID del periodista revisor de la noticia web.
            String authorID = sheetWebNews.getCellAt(1,i).getTextValue();
            // Importamos el titular de la noticia web.
            String headline = sheetWebNews.getCellAt(2,i).getTextValue();
            // Importamos el cuerpo de la noticia web.
            String body = sheetWebNews.getCellAt(3,i).getTextValue();
            // Importamos la url de la noticica web.
            String url = sheetWebNews.getCellAt(4,i).getTextValue();
            // Buscamos al periodista en el sistema.
            WebNews wn = new WebNews(headline, body, this.findJournalist(authorID), url);
            
            // Para importar los premios hacemos otro bucle.
            j = 5;
            while(sheetWebNews.getColumnCount()>j && !(sheetWebNews.getCellAt(j,i).getTextValue()).equals("*") && (! sheetWebNews.getCellAt(j,i).isEmpty())){
                // Importamos los premios conforme los leemos.
                wn.addPrize(sheetWebNews.getCellAt(j,i).getTextValue());
                j++;
            }
            
            // Avanzamos las columnas para coger el siguiente valor, las fotos.
            j++;
            
            // Para importar los revisores hacemos otro bucle.
            while(sheetWebNews.getColumnCount()>j &&  !(sheetWebNews.getCellAt(j,i).getTextValue()).equals("#") && (! sheetWebNews.getCellAt(j,i).isEmpty())){
                // Importamos los revisores conforme los leemos.
                wn.addPicture(this.getPicture(sheetWebNews.getCellAt(j,i).getTextValue()));
                j++;
            }
            
            // Avanzamos las columnas para coger el siguiente valor, los revisores.
            j++; 
            // Para importar las fotos, hacemos otro bucle más
            while(sheetWebNews.getColumnCount()>j && (! sheetWebNews.getCellAt(j,i).isEmpty())){
                // Importamos las fotos conforme los leemos.
                wn.addKeyWord(sheetWebNews.getCellAt(j,i).getTextValue());
                j++;
            }

            // Guardamos el la noticia imprimible en el sistema.
            this.insertNews(wn);
            // Avanzamos a la siguiente fila.
            i++;
        }
        return true;
    }

    @Override
    public boolean saveToFile(File file) {
        // Creamos una nueva hoja de cálculo.
        SpreadSheet mySpreadSheet = SpreadSheet.create(7,1000,1000);
        // Rescatamos la hoja dentro de la hoja de cálculo para cada tipo de dato.
        final Sheet sheetJournalists = mySpreadSheet.getSheet(0);
        sheetJournalists.setName("Journalists");
        final Sheet sheetPhotographers = mySpreadSheet.getSheet(1);
        sheetPhotographers.setName("Photographers");
        
        final Sheet sheetTeletypes = mySpreadSheet.getSheet(2);
        sheetTeletypes.setName("Teletypes");
        final Sheet sheetPrintableNews = mySpreadSheet.getSheet(3);
        sheetPrintableNews.setName("PrintableNews");
        final Sheet sheetWebNews = mySpreadSheet.getSheet(4);
        sheetWebNews.setName("WebNews");
        
        final Sheet sheetPictures = mySpreadSheet.getSheet(5);
        sheetPictures.setName("Pictures");
        final Sheet sheetNewspapers = mySpreadSheet.getSheet(6);
        sheetNewspapers.setName("Newspapers");
        
        // Recorremos la lista de Workers
        int numJournalist = 0, numPhotographer = 0;
        Iterator iteratorWorkers = this.workers.entrySet().iterator();
        while (iteratorWorkers.hasNext()) {
            // Cargamos ese par
            Map.Entry pair = (Map.Entry)iteratorWorkers.next();
            // Se guardan los periodistas
            if (pair.getValue().getClass().getName().equals("GSILib.BModel.workers.Journalist")){
                // Especificamos que es un Journalist con un casting
                Journalist journalist = (Journalist) pair.getValue();
                // Guardamos los datos del Journalist
                sheetJournalists.setValueAt(pair.getKey(), 0, numJournalist);
                sheetJournalists.setValueAt(journalist.getName(), 1, numJournalist);
                sheetJournalists.setValueAt(journalist.getBirthDate(), 2, numJournalist);
                String[] interests = journalist.getInterests();
                if (interests != null){
                    for (int i=0; i<interests.length; i++){
                        sheetJournalists.setValueAt(interests[i], i+3, numJournalist);
                    }
                }
                
                numJournalist++;
            }
            // Se guardan los fotógrafos
            else if (pair.getValue().getClass().getName().equals("GSILib.BModel.workers.Photographer")){
                // Especificamos que es un fotógrafo con un casting
                Photographer photographer = (Photographer) pair.getValue();
                // Guardamos los datos del Photographer
                sheetPhotographers.setValueAt(pair.getKey(), 0, numPhotographer);
                sheetPhotographers.setValueAt(photographer.getName(), 1, numPhotographer);
                sheetPhotographers.setValueAt(photographer.getBirthDate(), 2, numPhotographer);
                sheetPhotographers.setValueAt(photographer.getRegularResidence(), 3, numPhotographer);
                sheetPhotographers.setValueAt(photographer.getHolidayResidence(), 4, numPhotographer);

                numPhotographer++;
            }
            // Error
            else{
                System.err.printf("El trabajador no pertenece a ninguna de las subclases.\n");
            }  
        }
        
        // Recorremos la lista de Documents
        int numTeletype = 0, numPrintableNews = 0, numWebNews = 0;
        int colsTeletype, colsPrintableNews, colsWebNews; 
        for(Document document: this.documents){
            // Guardo los datos de los Teletypes.
            if (document.getClass().getName().equals("GSILib.BModel.documents.Teletype")){
                colsTeletype = 4;
                // Especificamos que es un Teletype con un casting
                Teletype teletype = (Teletype) document;
                // Guardamos los datos del Journalist
                sheetTeletypes.setValueAt(document.getId(), 0, numTeletype);
                sheetTeletypes.setValueAt(document.getAuthor().getId(), 1, numTeletype);
                sheetTeletypes.setValueAt(document.getHeadline(), 2, numTeletype);
                sheetTeletypes.setValueAt(document.getBody(), 3, numTeletype);
                // Campos multivaluados
                String[] teletypePrizes = this.listPrizes(document);
                if (teletypePrizes != null){
                    for (int i=0; i<teletypePrizes.length; i++){
                        sheetTeletypes.setValueAt(teletypePrizes[i], i + colsTeletype, numTeletype);
                    }
                }
                numTeletype++;
            }
            
            // Guardo los datos de las PrintableNews.
            if (document.getClass().getName().equals("GSILib.BModel.documents.visualNews.PrintableNews")){
                colsPrintableNews = 4;
                // Especificamos que es un PrintableNew con un casting
                PrintableNews printableNews = (PrintableNews) document;
                // Guardamos los datos del PrintableNew
                sheetPrintableNews.setValueAt(document.getId(), 0, numPrintableNews);
                sheetPrintableNews.setValueAt(document.getAuthor().getId(), 1, numPrintableNews);
                sheetPrintableNews.setValueAt(document.getHeadline(), 2, numPrintableNews);
                sheetPrintableNews.setValueAt(document.getBody(), 3, numPrintableNews);
                
                // Guardamos los revisores
                String[] printableNewsPrizes = this.listPrizes(document);
                if (printableNewsPrizes != null){
                    for (int i=0; i<printableNewsPrizes.length; i++){
                        sheetPrintableNews.setValueAt(printableNewsPrizes[i], colsPrintableNews, numPrintableNews);
                        colsPrintableNews++;
                    }
                }

                // Metemos el * que diferenciará los campos multievaluados
                sheetPrintableNews.setValueAt("*", colsPrintableNews, numPrintableNews);
                colsPrintableNews++;
                // Guardamos los revisores
                Journalist[] reviewList = printableNews.getReviewers();
                if(reviewList != null){
                    for(Journalist reviewer : reviewList){
                        sheetPrintableNews.setValueAt(reviewer.getId(), colsPrintableNews, numPrintableNews);
                        colsPrintableNews++;
                    }
                }
                
                // Metemos la # que diferenciará los siguientes multievaluados
                sheetPrintableNews.setValueAt("#", colsPrintableNews, numPrintableNews);
                colsPrintableNews++;
                //Guardamos las fotos
                Picture[] printableNewsPics = printableNews.getPictures();
                if(printableNewsPics != null){
                    for (Picture p: printableNewsPics){
                        sheetPrintableNews.setValueAt(p.getUrl(), colsPrintableNews, numPrintableNews);
                        colsPrintableNews++;
                    }
                }
                numPrintableNews++;
            }
            
            // Guardo los datos de las WebNews.
            if (document.getClass().getName().equals("GSILib.BModel.documents.visualNews.WebNews")){
                colsWebNews = 5;
                // Especificamos que es un WebNews con un casting
                WebNews webNews = (WebNews) document;
                // Guardamos los datos del WebNews
                sheetWebNews.setValueAt(document.getId(), 0, numWebNews);
                sheetWebNews.setValueAt(document.getAuthor().getId(), 1, numWebNews);
                sheetWebNews.setValueAt(document.getHeadline(), 2, numWebNews);
                sheetWebNews.setValueAt(document.getBody(), 3, numWebNews);
                sheetWebNews.setValueAt(webNews.getUrl(), 4, numWebNews);
                
                // Guardamos los revisores
                String[] printableNewsPrizes = this.listPrizes(document);
                if (printableNewsPrizes != null){
                    for (int i=0; i<printableNewsPrizes.length; i++){
                        sheetPrintableNews.setValueAt(printableNewsPrizes[i], colsWebNews, numWebNews);
                        colsWebNews++;
                    }
                }
                
                // Metemos el * que diferenciará los campos multievaluados
                sheetWebNews.setValueAt("*", colsWebNews, numWebNews);
                colsWebNews++;

                // Introducimos las fotos.
                Picture[] webNewsPics = webNews.getPictures();
                if(webNewsPics != null){
                    for (Picture p: webNewsPics){
                        sheetWebNews.setValueAt(p.getUrl(), colsWebNews, numWebNews);
                        colsWebNews++;
                    }
                }
                
                // Metemos el # que diferenciará los campos multievaluados
                sheetWebNews.setValueAt("#", colsWebNews, numWebNews);
                colsWebNews++;
                
                // Introducimos las palabras clave.
                List<String> webNewsKeyWords = webNews.getKeyWords();
                if (! webNewsKeyWords.isEmpty()){
                    for (int i=0; i<webNewsKeyWords.size(); i++){
                        sheetWebNews.setValueAt(webNewsKeyWords.get(i), colsWebNews, numWebNews);
                        colsWebNews++;
                    }
                }
                numWebNews++;
            }
        }
        
        // Recorremos la lista de Pictures
        int numPicture = 0;
        Iterator iteratorPictures = this.pictures.entrySet().iterator();
        while (iteratorPictures.hasNext()) {
            // Cargamos ese par
            Map.Entry pair = (Map.Entry)iteratorPictures.next();
            // Cargamos el valor de ese par como Picture
            Picture picture = (Picture) pair.getValue();
            // Rellenar la tabla
            //sheetPictures.setValueAt(pair.getKey(), 0, numPicture);
            sheetPictures.setValueAt(picture.getAuthor().getId(), 0, numPicture);
            sheetPictures.setValueAt(picture.getUrl(), 1, numPicture);
            numPicture++;  
        }
        
        // Recorremos la lista de Newspapers
        
        // Preparamos el formato con el que guardaremos la fecha.
//        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Format formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        
        int numNewspaper = 0;
        Iterator iteratorNewspapers = this.newspapers.entrySet().iterator();
        while (iteratorNewspapers.hasNext()) {
            // Cargamos ese par
            Map.Entry pair = (Map.Entry)iteratorNewspapers.next();
            // Cargamos el valor de ese par como Newspaper
            Newspaper newspaper = (Newspaper) pair.getValue();
            // Rellenar la tabla
            sheetNewspapers.setValueAt(newspaper.getDate(), 0, numNewspaper);
            // Rellenar la tabla - Campos multivaluados
            PrintableNews[] printableNews = newspaper.getPrintableNews();
            if (printableNews != null){
                for (int i=0; i<printableNews.length; i++){
                    sheetNewspapers.setValueAt(printableNews[i].getId(), i+1, numNewspaper);
                }
            }
            numNewspaper++;  
        }
        
        // Guardamos la hoja de cálculo con todos los datos.
        try {
            OOUtils.open(mySpreadSheet.saveAs(file));
        } 
        catch (IOException ex) {
            System.err.printf("No se pudo guardar el archivo.\n");
        }
        return true;
    }  
    
    /**
     * Helper method which creates a XML element "BusinessSystem"
     * @param xml XML Handler for the system.
     * @return XML element snippet representing a BusinessSystem
     */
    public Element getElement(XMLHandler xml){

        Element xmlBS = xml.engine.createElement("BusinessSystem");
        
        // Para una raiz BusinessSystem, introducimos otra raiz Workers
        
        Element xmlBSWorkers = xml.engine.createElement("Workers");
        
        Iterator iteratorWorkers = this.workers.entrySet().iterator();
        while (iteratorWorkers.hasNext()) {
            
            Map.Entry worker = (Map.Entry)iteratorWorkers.next();
            // Se guardan los periodistas
            if (worker.getValue().getClass().getName().equals("GSILib.BModel.workers.Journalist")){
                
                // Para una raiz Workers, introducimos otra raiz Journalist
                
                Journalist journalist = (Journalist) worker.getValue();
                
                xmlBSWorkers.appendChild(journalist.getElement(xml));
            }
            else if (worker.getValue().getClass().getName().equals("GSILib.BModel.workers.Photographer")){
                
                // Para una raiz Workers, introducimos otra raiz Photographer
                
                Photographer photographer = (Photographer) worker.getValue();
                
                xmlBSWorkers.appendChild(photographer.getElement(xml));
            }
            else{
                System.err.print("Unrecognised Class.\n");
            }
        }
        
        xmlBS.appendChild(xmlBSWorkers);

        // Para una raiz BusinessSystem, introducimos otra raiz Documents
        
        Element xmlBSDocuments = xml.engine.createElement("Documents");
        
        for(Document document : this.documents){
            
            // Para una raiz Documents, introducimos los elementos xml
            
            if (document.getClass().getName().equals("GSILib.BModel.documents.Teletype")){
                
                // Para una raiz Documents, introducimos otra raiz Teletype
                
                Teletype teletype = (Teletype) document;
                
                xmlBSDocuments.appendChild(teletype.getElement(xml));
            }
            else if (document.getClass().getName().equals("GSILib.BModel.documents.visualNews.WebNews")){
                
                // Para una raiz Documents, introducimos otra raiz WebNews
                
                WebNews webNews = (WebNews) document;
                
                xmlBSDocuments.appendChild(webNews.getElement(xml));
            }
            else if (document.getClass().getName().equals("GSILib.BModel.documents.visualNews.PrintableNews")){
                
                // Para una raiz Documents, introducimos otra raiz PrintableNews
                
                PrintableNews printableNews = (PrintableNews) document;
                
                xmlBSDocuments.appendChild(printableNews.getElement(xml));
            } 
            else{
                System.err.print("Unrecognised Class.\n");
            }
        }
        
        xmlBS.appendChild(xmlBSDocuments);
        
        // Para una raiz BusinessSystem, introducimos otra raiz Workers
        
        Element xmlBSPictures = xml.engine.createElement("Pictures");
        
        Iterator iteratorPictures = this.pictures.entrySet().iterator();
        while (iteratorPictures.hasNext()) {
            
            Map.Entry entry = (Map.Entry)iteratorPictures.next();
                
            // Para una raiz Pictures, introducimos otra raiz Picture

            Picture picture = (Picture) entry.getValue();

            xmlBSPictures.appendChild(picture.getElement(xml));
        }
        
        xmlBS.appendChild(xmlBSPictures);
        
        // Para una raiz BusinessSystem, introducimos otra raiz Newspapers
        
        Element xmlBSNewspapers = xml.engine.createElement("Newspapers");
        
        Iterator iteratorNewspapers = this.newspapers.entrySet().iterator();
        while (iteratorNewspapers.hasNext()) {
            
            Map.Entry entry = (Map.Entry)iteratorNewspapers.next();
                
            // Para una raiz Pictures, introducimos otra raiz Newspaper

            Newspaper newspaper = (Newspaper) entry.getValue();

            xmlBSNewspapers.appendChild(newspaper.getElement(xml));
        }
        
        xmlBS.appendChild(xmlBSNewspapers);
        
        return xmlBS;
    }
    
    /**
     * Gets this businessSystem in XML string.
     * @return the xml string of this businessSystem.
     */
    @Override
    public String toXML() {
        
        // Instanciamos el motor de XML
        
        XMLHandler xml = new XMLHandler();
        
        Writer out = new StringWriter();
        try{
            OutputFormat format = new OutputFormat(xml.engine);
            format.setIndenting(true);
            
            XMLSerializer serializerToString = new XMLSerializer(out , format);
            serializerToString.serialize(this.getElement(xml));

        } catch(IOException ie) {
            ie.printStackTrace();
        }
        
        return out.toString();
    }
    
    /**
     * Stores this journalist in XML.
     * @return if the businessSystem was successfully stored into the xml file.
     */
    @Override
    public boolean saveToXML(File file) {
        
        // Instanciamos el motor de XML
        
        XMLHandler xml = new XMLHandler();
        
        try{
            
            OutputFormat format = new OutputFormat(xml.engine);
            format.setIndenting(true);
            
            XMLSerializer serializerTofile = new XMLSerializer(
                new FileOutputStream(file)
                , format);
            serializerTofile.serialize(this.getElement(xml));
            
            return true;
        } catch(IOException ie) {
            ie.printStackTrace();
        }
        
        return false;
    }

    /**
     * Stores this businessSystem in XML.
     * @return if the businessSystem was successfully stored into the xml file.
     */
    @Override
    public boolean saveToXML(String filePath) {
       
        // Instanciamos el motor de XML
        
        XMLHandler xml = new XMLHandler();
        
        try{
            
            OutputFormat format = new OutputFormat(xml.engine);
            format.setIndenting(true);
            XMLSerializer serializerTofile = new XMLSerializer(
                new FileOutputStream(
                    new File(filePath))
                , format);
            serializerTofile.serialize(this.getElement(xml));
            
            return true;
        } catch(IOException ie) {
            ie.printStackTrace();
        }
        
        return false;
    }

    /**
     * Main method which imports a BusinessSystem from an XML file which contains
     * complete data of all the classes which conform the business system.
     * @param file XML type file
     * @return a business system with all the inclusions that XML defines.
     * @throws XMLParsingException exception derived from XML parsing.
     * @throws IOException exception derived from io.
     * @throws SAXException exception derived from SAXException (data consistency).     
     * @throws java.text.ParseException exception derived from Parsing.
     */
    public static BusinessSystem loadFromFileXML(File file) throws XMLParsingException, IOException, SAXException, ParseException{

        // Creamos el BusinessSystem
        
        BusinessSystem bs = new BusinessSystem();
        
        // Obtenemos el string de XML
        
        String businessSystemFromXML = new String(Files.readAllBytes(Paths.get(file.toString())));
        
        // Obtenemos el elemento XML
        
        XMLHandler xml = null;
        
        try{
            xml = new XMLHandler(businessSystemFromXML);
        }
        catch (SAXException e){
            throw new XMLParsingException("prueba");
        }
        
        Element xmlBS = (Element) xml.engine.getElementsByTagName("BusinessSystem").item(0);
        
        // Cargamos los Journalists
        
        NodeList journalistsNodes = (NodeList) ((Element) xmlBS.getElementsByTagName("Workers").item(0)).getElementsByTagName("Journalist");

        for (int i = 0; i < journalistsNodes.getLength(); i++) {
            
            // Cargamos un Journalist
            
            bs.addJournalist(new Journalist((Element) journalistsNodes.item(i)));
        }
        
        // Cargamos los Photographers
        
        NodeList photographersNodes = (NodeList) ((Element) xmlBS.getElementsByTagName("Workers").item(0)).getElementsByTagName("Photographer");

        for (int i = 0; i < photographersNodes.getLength(); i++) {
            
            // Cargamos un Photographer
            
            bs.addPhotographer(new Photographer((Element) photographersNodes.item(i)));
        }
        
        // Cargamos las Pictures
        
        NodeList picturesNodes = (NodeList) ((Element) xmlBS.getElementsByTagName("Pictures").item(0)).getElementsByTagName("Picture");

        for (int i = 0; i < picturesNodes.getLength(); i++) {
            
            // Obtenemos el Photographer
            
            Photographer photographer = bs.findPhotographer(((Element) ((Element) picturesNodes.item(i)).getElementsByTagName("Photographer").item(0)).getAttribute("id"));
            
            bs.addPicture(new Picture((Element) picturesNodes.item(i), photographer));
        }
        
        // Cargamos los Teletypes
        
        NodeList teletypesNodes = (NodeList) ((Element) xmlBS.getElementsByTagName("Documents").item(0)).getElementsByTagName("Teletype");

        for (int i = 0; i < teletypesNodes.getLength(); i++) {
            
            // Obtenemos el Journalist
            
            Journalist journalist = bs.findJournalist(((Element) ((Element) teletypesNodes.item(i)).getElementsByTagName("Journalist").item(0)).getAttribute("id"));
            
            // Cargamos un Teletype
            
            bs.insertNews(new Teletype((Element) teletypesNodes.item(i), journalist));
        }
        
        // Cargamos las PrintableNews
        
        NodeList printableNewsNodes = (NodeList) ((Element) xmlBS.getElementsByTagName("Documents").item(0)).getElementsByTagName("PrintableNews");

        for (int i = 0; i < printableNewsNodes.getLength(); i++) {
            
            // Obtenemos el Journalist
            
            Journalist journalist = bs.findJournalist(((Element) ((Element) printableNewsNodes.item(i)).getElementsByTagName("Journalist").item(0)).getAttribute("id"));
            
            // Cargamos un PrintableNews
            
            PrintableNews printableNews = new PrintableNews((Element) printableNewsNodes.item(i), journalist);
            
            // Cargamos las sus Pictures
        
            NodeList printableNewsPicturesNodes = (NodeList) ((Element) ((Element) printableNewsNodes.item(i)).getElementsByTagName("Pictures").item(0)).getElementsByTagName("Picture");

            for (int j = 0; j < printableNewsPicturesNodes.getLength(); j++) {
                
                printableNews.addPicture(bs.getPicture(((Element) printableNewsPicturesNodes.item(j)).getAttribute("url")));
            }
            
            // Cargamos las sus reviewers
        
            NodeList printableNewsReviewersNodes = (NodeList) ((Element) ((Element) printableNewsNodes.item(i)).getElementsByTagName("Reviewers").item(0)).getElementsByTagName("Journalist");

            for (int j = 0; j < printableNewsReviewersNodes.getLength(); j++) {
                
                printableNews.addReviewer(bs.findJournalist(((Element) printableNewsReviewersNodes.item(j)).getAttribute("id")));
            }
            
            bs.insertNews(printableNews);
        }
        
        // Cargamos las WebNews
        
        NodeList webNewsNodes = (NodeList) ((Element) xmlBS.getElementsByTagName("Documents").item(0)).getElementsByTagName("WebNews");

        for (int i = 0; i < webNewsNodes.getLength(); i++) {
            
            // Obtenemos el Journalist
            
            Journalist journalist = bs.findJournalist(((Element) ((Element) printableNewsNodes.item(i)).getElementsByTagName("Journalist").item(0)).getAttribute("id"));
            
            // Cargamos un WebNews
            
            WebNews webNews = new WebNews((Element) webNewsNodes.item(i), journalist);
            
            // Cargamos las sus Pictures
        
            NodeList webNewsPicturesNodes = (NodeList) ((Element) ((Element) webNewsNodes.item(i)).getElementsByTagName("Pictures").item(0)).getElementsByTagName("Picture");

            for (int j = 0; j < webNewsPicturesNodes.getLength(); j++) {
                
                webNews.addPicture(bs.getPicture(((Element) webNewsPicturesNodes.item(j)).getAttribute("url")));
            }
            
            // Cargamos las sus keywords
        
            NodeList webNewsKeywordsNodes = (NodeList) ((Element) ((Element) webNewsNodes.item(i)).getElementsByTagName("keywords").item(0)).getElementsByTagName("keyword");

            for (int j = 0; j < webNewsKeywordsNodes.getLength(); j++) {
                
                webNews.addKeyWord(((Element) webNewsKeywordsNodes.item(j)).getTextContent());
            }
            
            bs.insertNews(webNews);
        }
        
        // Cargamos los Newspapers
        
        NodeList newspapersNodes = (NodeList) ((Element) xmlBS.getElementsByTagName("Newspapers").item(0)).getElementsByTagName("Newspaper");

        for (int i = 0; i < newspapersNodes.getLength(); i++) {
            
            // Cargamos un Newspaper
            
            Newspaper newspaper = new Newspaper((Element) newspapersNodes.item(i));
            
            // Cargamos las PrintableNews
        
            NodeList newspapersPrintableNewsNodes = ((Element) newspapersNodes.item(i)).getElementsByTagName("keywords");

            for (int j = 0; j < newspapersPrintableNewsNodes.getLength(); j++) {
                
                newspaper.addNews(bs.getPrintableNews(Integer.parseInt(((Element) newspapersPrintableNewsNodes).getAttribute("id"))));
            }
            
            bs.insertNewspaper(newspaper);
        }
        
        return bs;
    }
    /**
     * Auxiliary method to store a file type to an XML
     * @param file file desired to store
     * @return true if correctly stored, false otherwise
     */
    public boolean saveToFileXML(File file){
        
        return this.saveToXML(file);
    }
    
    @Override
    public String getJournalistOptions(){
        
        // Lista de Journalists
                    
        String journalistOptions = "";
        Journalist[] journalists = this.getJournalists();
        for(Journalist journalist : journalists){
            journalistOptions = journalistOptions.concat("<option value=\"" + journalist.getId() + "\">" + journalist.getName() + "</option>");
        }
        
        return journalistOptions;
    }
    
    @Override 
    public String getNewspaperOptions(){
        // Lista de Newspapers
                    
        String newspaperOpctions = "";
        Newspaper[] newspapers = this.getNewspapers();
        for(Newspaper newspaper : newspapers){
            newspaperOpctions = newspaperOpctions.concat("<option value=\"" + newspaper.getDate() + "\">" + newspaper.getDate() + "</option>");
        }
        
        return newspaperOpctions;
    }
}
