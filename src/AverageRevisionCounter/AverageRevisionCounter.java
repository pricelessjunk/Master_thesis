package AverageRevisionCounter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import Commons.Constants;
import org.json.JSONObject;

/**
 *
 * @author kaustuv
 */
public class AverageRevisionCounter {

    public static void main(String[] args) {

        String inputFile = "../files/random10000Names.txt";
        String line = "";
        String continueId = "";
        AverageRevisionCounter av = new AverageRevisionCounter();

        //String url = "https://en.wikipedia.org/w/api.php?action=query&titles=Anarchy&format=json&prop=extracts&continue&redirects&prop=revisions&rvdir=newer&rvlimit=max&rvprop=ids|flags|timestamp";
        try {
            BufferedReader bf = null;
            try {
                bf = new BufferedReader(new FileReader(inputFile));

                while ((line = bf.readLine()) != null) {
                    URL url = av.generateUrl(line, continueId);
                    InputStream is = url.openStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                    String jsonText = rd.readLine();
                    
                    continueId = av.processJson(new JSONObject(jsonText));
                    
                    System.out.println(jsonText);
                    rd.close();
                }
            } finally {
                bf.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(AverageRevisionCounter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private URL generateUrl(String title, String continueId) throws MalformedURLException {
        StringBuilder sb = new StringBuilder(Constants.WIKI_API_BASE);
        sb.append("?").append("action=").append("query");
        sb.append("&").append("titles=").append(title);
        sb.append("&").append("format=").append("json");
        sb.append("&").append("prop=").append("extracts");
        sb.append("&").append("continue");
        sb.append("&").append("redirects");
        sb.append("&").append("prop=").append("revisions");
        sb.append("&").append("rvdir=").append("newer");
        sb.append("&").append("rvlimit=").append("max");
        sb.append("&").append("rvprop=").append("ids|flags|timestamp");
        sb.append("&").append("rvcontinue=").append(continueId);

        System.out.println("url: " + sb.toString());

        return new URL(sb.toString());
    }

    private String processJson(JSONObject jo){
        
        
        return null;
    }
}
