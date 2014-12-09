/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.net;

import GSILib.BSystem.BusinessSystem;
import GSILib.net.Message.Request;

/**
 *
 * @author Alvaro
 */
public class PostHandler {
    
    public PostHandler(Request request, BusinessSystem bs){
        System.out.println("Post request: \n" + request.toString());
    }
}
