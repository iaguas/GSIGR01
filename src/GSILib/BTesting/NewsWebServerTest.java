/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.BTesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map.Entry;

/**
 *
 * @author linux1
 */

// REFERENCIAS
// http://stackoverflow.com/questions/3732109/simple-http-server-in-java-using-only-java-se-api
// http://stackoverflow.com/questions/2793150/using-java-net-urlconnection-to-fire-and-handle-http-requests

public class NewsWebServerTest {
    String url = "http://localhost";
    String charset = "UTF-8";  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()
    String param1 = "value1";
    String param2 = "value2";
    String query;
    InputStream response;
    URLConnection connection;
    HttpURLConnection httpConnection;
    
    public NewsWebServerTest() throws UnsupportedEncodingException {
        this.query = String.format("param1=%s&param2=%s", 
                URLEncoder.encode(param1, charset),
                URLEncoder.encode(param2, charset));
    }
    
    public void defaultRequestMethod() throws MalformedURLException, IOException{
        // Manejo de la petición HTTP GET
        // Fijar el formato de una petición HTTP          
        URLConnection connection = new URL(url + "?" + query).openConnection();
        // Permitir el paso de parámetros para la petición
        connection.setRequestProperty("Accept-Charset", charset);
        // Respuesta
        InputStream response = connection.getInputStream();
        /*
        Se puede usar este método openStream() si no hay necesidad de fijar cabeceras
        InputStream response = new URL(url).openStream();
        */
    }            
    
    // Omitimos las peticiones POST de nuestro sistema
    
    // Devuelve información sobre la respuesta HTTP
    public void httpResponseInfo() throws IOException{
        // Estado de respuesta HTTP
        int status = httpConnection.getResponseCode();
        // Cabeceras de respuesta HTTP
        for (Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
            System.out.println(header.getKey() + "=" + header.getValue());
        }
        // Codificación de la respuesta HTTP
        String contentType = connection.getHeaderField("Content-Type");
        String charset = null;

        for (String param : contentType.replace(" ", "").split(";")) {
            if (param.startsWith("charset=")) {
                charset = param.split("=", 2)[1];
                break;
            }
        }

        if (charset != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response, charset))) {
                for (String line; (line = reader.readLine()) != null;) {
                    // ... System.out.println(line) ?
                }
            }
        }    
    }    
    
}
