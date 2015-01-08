/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.BTesting;

import GSILib.BSystem.*;
import GSILib.BModel.*;
import GSILib.BModel.workers.*;
import GSILib.BModel.documents.*;
import GSILib.BModel.documents.visualNews.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * This is the class EOTester.
 * This class is used in order to know that all things in the practise are done 
 * OK.
 * @version 1.0s
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class EOTester {
    
    /**
     * Returns a simulated BusinessSystem with example entities
     * @return bs a BusinessSystem
     */
    public static BusinessSystem getTestingBusinessSystem(){
        
        // Nuevo BusinessSystem

        BusinessSystem bs = new BusinessSystem();
        
        // Nuevo Joournalist
        
        ArrayList interestsOfAlvaro = new ArrayList();

        interestsOfAlvaro.add("Discutir");
        interestsOfAlvaro.add("Tocar las narices");
        interestsOfAlvaro.add("Jugar al CS");

        Journalist journalistAlvaro = new Journalist("8", "Alvaro Octal", "1993/12/27", interestsOfAlvaro);
        bs.addJournalist(journalistAlvaro);

        // Nuevo Journalist

        ArrayList interestsOfPirata = new ArrayList();

        interestsOfPirata.add("Comer pasta");
        interestsOfPirata.add("beber cerveza");
        interestsOfPirata.add("Cantar canciones");
        interestsOfPirata.add("trifulcas de bar");

        Journalist journalistPirata = new Journalist("2", "Pirata", "2000/01/01", interestsOfPirata);
        bs.addJournalist(journalistPirata);

        Photographer photographer = new Photographer("12", "Arguitxu Arzcarrena", "01/01/1990", "Bilbao", "Carpa");
        bs.addPhotographer(photographer);
        
        // Nueva Picture

        Picture pictureRed = new Picture("http://images.example.com/testRed.png", photographer);
        Picture pictureBlue = new Picture("http://images.example.com/testBlue.png", photographer);
        bs.addPicture(pictureRed);
        bs.addPicture(pictureBlue);

        // Nueva PrintableNews

        PrintableNews printableNewsXML = new PrintableNews("Sobre XML", "Se recomienda no hacer atributos tipo string muy largos, que el los navegadores los desprecian y te dan errores de parseo", journalistAlvaro);
        printableNewsXML.addReviewer(journalistPirata);
        printableNewsXML.addPicture(pictureRed);
        printableNewsXML.addPicture(pictureBlue);
        bs.insertNews(printableNewsXML);
        
        // Nueva PrintableNews
        
        PrintableNews printableNewsBFHL = new PrintableNews("Battlefield Hardline es contundente con su historia", "Es mentira, lo sabemos, lo saben, y no cuela", journalistAlvaro);
        bs.insertNews(printableNewsBFHL);
        
        // Nuevo Newspaper
 
        Date currentDate = new Date();
        
        bs.createNewspaper(currentDate);
        bs.addNewsToIssue(bs.getNewspaper(currentDate), printableNewsXML);
        bs.addNewsToIssue(bs.getNewspaper(currentDate), printableNewsBFHL);
        
        // Nueva WebNews
        
        WebNews webNewsSnake = new WebNews("Snake quiere un trozo del pastel", "Snake quiere un trozo del pastel de Call of Duty y lo demuestra con el tráiler de Metal Gear Online", journalistPirata);
        bs.insertNews(webNewsSnake);
        
        return bs;
    }
    
    
    public static void main(String[] args) {
        BusinessSystem bsystem = new BusinessSystem();
        
        Journalist jr = new Journalist("4444Y","Bill Newman","23/06/1954",new ArrayList<>());
        bsystem.addJournalist(new Journalist("5555R","Ken Jones","16/05/1953",new ArrayList<>()));
        bsystem.addJournalist(new Journalist("6666S","Tim Cook","23/04/1956",new ArrayList<>()));
        bsystem.addJournalist(new Journalist("7777T","Bill McDonald","08/07/1978",new ArrayList<>()));
        bsystem.addPhotographer(new Photographer("3333C","Tom Ulman","24/03/1984","C/Falsa 123","C/Verdadera 456"));
        bsystem.insertNews(new PrintableNews("title1","body1",jr));
                
        /* S1: Si introduce a un trabajador, este puede ser localizado luego a
               partir de su ID.
        */
        // Comenzamos probando hacer un trabajador de tipo Journalist.
        // Completamos primero la lista de intereses
        ArrayList<String> interest = new ArrayList<>();
        interest.add("lectura");
        interest.add("natacion");
        interest.add("belenismo");
        // Añadimos al nuevo periodista.
        jr = new Journalist("1111A","Jonh Smith","09/02/1993",interest);
        bsystem.addJournalist(jr);
        // Buscamos el empleado por el ID.
        Journalist findJr = bsystem.findJournalist("1111A");
        // Imprimimos el resultado en la consola
        System.out.println("S1)");
        System.out.println("Comparamos el periodista introducido con el encontrado:");
        System.out.println(jr);
        System.out.println(findJr);
        if (jr.equals(findJr))
            System.out.println("Los dos periodistas son iguales (es el mismo).");
        else
            System.out.println("Los periodistas son diferentes (no son el mismo).");
                
        // Probamos lo mismo con un trabajador de tipo Photographer.
        // Creamos el fotógrafo.
        Photographer p = new Photographer("2222B","Tom Smith","24/03/1984","C/Falsa 123","C/Verdadera 456");
        // Añadimos el nuevo fotógrafo.
        bsystem.addPhotographer(p);
        Photographer findP = bsystem.findPhotographer("2222B");
        // Imprimimos el resultado en la consola.
        System.out.println("Comparamos el fotógrafo introducido con el encontrado:");
        System.out.println(p);
        System.out.println(findP);
        if (p.equals(findP))
            System.out.println("Los dos fotógrafos son iguales (es el mismo).\n");
        else
            System.out.println("Los fotógrafos son diferentes (no son el mismo).\n");
        
        /* S2: Si busca a un periodista que no existe con findJournalist, el 
               resultado es null.
        */
        // No se han introducido periodistas que tengan el ID a 0000Z. Se comprueba.
        jr = bsystem.findJournalist("0000Z");
        // Imprimimos el resultado en la consola.
        System.out.println("S2)");
        System.out.println("El resultado de buscar un peridista que no existe es: " + jr + ".\n");
         
        
        /* S3: Si se busca a un periodista con el ID de un fotográfo, el
               resultado es null.
        */
        // En S1 hemos introducido un fotógrafo con ID 2222B. Probamos a buscarlo
        // como si de un periodista se tratase.
        jr = bsystem.findJournalist("2222A");
        // Imprimimos el resultado en la consola.
        System.out.println("S3)");
        System.out.println("El resultado de buscar un periodista con el ID de un fotografo es: " + jr + ".\n");
         
        
        /* S4: Si intenta introducir a un peridista con el ID de un fotógrafo, 
               anteriormente introducido, el método addJournalist devuelve un 
               resultado falso.
        */
        // Para este ejemplo volvemos a utilizar el ID 2222B del apartado S1.
        boolean ok = bsystem.addJournalist(new Journalist("2222B","Anne James","23/09/1974",new ArrayList<>()));
        // Imprimimos el resultado en la consola.
        System.out.println("S4)");
        System.out.println("El resultado de introducir un periodista con el ID de un fotografo es: " + ok + ".\n");
        
         
        /* S5: Si busca un periodista al que ha eliminado (con removeJournalist)
               el resultado de findJournalist es null.
        */
        // En primer lugar, eliminamos al periodista 1111A creado en S1).
        bsystem.removeJournalist(bsystem.findJournalist("1111A"));
        // Después, intentamos buscarlo.
        findJr = bsystem.findJournalist("1111A");
        // Imprimimos el resultado en la consola.
        System.out.println("S5)");
        System.out.println("El resultado de buscar un periodista borrado es: " + findJr + ".\n");

        
        /* S6: Introduzca varias noticias (de varias clases intanciables) y
               asígnelas a un periodista presente en el sistema. Trate de
               recuperarlas usando getDocuments.
        */
        // Buscamos y guardamos los periodistas que vamos a utilizar en S6).
        Journalist jr1 = bsystem.findJournalist("5555R");
        jr = bsystem.findJournalist("6666S");
        
        // Creamos las nuevas noticias.
        PrintableNews pn = new PrintableNews("title1","body1",jr1);
        Teletype tt = new Teletype("title2","body2",jr);
        WebNews wn = new WebNews("title3","body3",jr,"http://midominio.com/noticias/id");
        // Insertamos las noticias.
        bsystem.insertNews(pn);
        bsystem.insertNews(tt);
        bsystem.insertNews(wn);
        // Recuperamos los documentos con el método propuesto.
        Document[] documentList = bsystem.getDocuments(bsystem.findJournalist("6666S"));
        // Imprimimos en las consola el resultado.
        System.out.println("S6)");
        System.out.println("Los documentos encontrados son los siguientes: ");
        for(Document d: documentList){
            System.out.println(d);
        }
        
        
        /* S7: Trate de recuperar las noticas escritas por un periodista que no
               tenga noticias asociadas.
        */
        // Primero recuperamos un periodista por su ID. No tiene ninguna noticia asignada.
        jr = bsystem.findJournalist("7777T");
        // Después, recuperamos las noticias del periodista anterior.
        documentList = bsystem.getDocuments(jr);
        // Imprimimos el resultado en la consola.
        System.out.println("\nS7)");
        System.out.println("La lista de documentos para un periodista que no ha escrito es: ");
        if (documentList.length==0){
            System.out.println("No hay documentos para mostrar.");
        }
        for(Document d: documentList){
            System.out.println(d);
        }
        
        
        /* S8: Introduzca varias fotos en el sistema. Más adelante, asocie varias
               fotos a una de las noticias introducidas anteriormente
               (evidenetemente, no puede ser un Teleprinter). Finalmente,
               recupere la noticia usando getDocuments y compruebe que la
               asociación ha sido adecuada.
        */
        // Creamos varias fotos asignadas a un único fotógrafo (podría ser a varios).
        Photographer pg = new Photographer("1234Z","Jonh Smith", "30/04/1953","C/ Falsa 123", "C/ Verdadera 567");
        Picture pic1 = new Picture("http://midominio.com/photos/pic1", pg);
        Picture pic2 = new Picture("http://midominio.com/photos/pic2", pg);
        Picture pic3 = new Picture("http://midominio.com/photos/pic3", pg);
        // Añadimos las fotos al sistema.
        bsystem.addPicture(pic1);
        bsystem.addPicture(pic2);
        bsystem.addPicture(pic3);
        // Asociamos las fotos a una noticia
        pn.addPicture(pic1);
        pn.addPicture(pic2);
        pn.addPicture(pic3);
        // Recuperamos la noticia
        Document[] docs = bsystem.getDocuments(jr1);
        // Como el periodista solo tiene una noticia, la seleccionamos.
        pn = (PrintableNews) docs[0];
        // Mostramos que tiene todas las fotos.
        System.out.println("\nS8)");
        System.out.println("La noticia ha sido actualizada de forma adecuada:");
        System.out.println(pn);
        
        /*
         * NOTA: Además de los ejercicios obligatorios presentados, en este 
         *       proyecto se han implementado los métodos equals y toString en
         *       todas las clases. Asi mismo, se ha documetado todo el código de
         *       manera apropiada.
         *       Se hace, también, la entrega del informe que corresponde a las 
         *       pregutas de la Práctica 01.
         */
    }
}