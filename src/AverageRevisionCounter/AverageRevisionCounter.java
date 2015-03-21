package AverageRevisionCounter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kaustuv
 */
public class AverageRevisionCounter {

    public static void main(String[] args) {
        String url = "https://en.wikipedia.org/w/api.php?action=query&titles=Anarchy&format=json&prop=extracts&continue&redirects&prop=revisions&rvdir=newer&rvlimit=max&rvprop=ids|flags|timestamp";
        try {
            InputStream is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = rd.readLine();
            System.out.println(jsonText);
        } catch (IOException ex) {
            Logger.getLogger(AverageRevisionCounter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
