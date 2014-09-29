/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GSILib.BModel;

import GSILib.BModel.Picture;
import GSILib.BModel.documents.visualNews.WebNews;
/*import GSILib.BModel.workers.Journalist;*/
import GSILib.BModel.workers.Photographer;

/**
 *
 * @author inigo.aguas & Iñaki Garcia
 */

public interface WebResource {
    /* Constructor y método de WebNews*/   
    /*public WebNews(String headline, String body, Journalist journalist, String url);*/
    public boolean addKeyWord(String keyword);
    
    /* Constructor y métodos de Picture */
    /*public Picture(String url, Photographer photographer);*/
    // Get privates
    /**
     * Retrieves the information on the URL of a Picture. If the Picture is not stored in the system,
	 *		the result is null.
     *
     * @return The URL associated with the picture
     */
    public String getUrl();
    
    /**
     * Retrieves the information on the author of a Picture. If the Picture is not stored in the system,
	 *		the result is null.
     *
     * @return The photographer associated with the picture
     */ 
    public Photographer getAutor();    
}
