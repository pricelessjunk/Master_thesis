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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author kaustuv
 */
public class AverageRevisionCounter {

    DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    long count = 0L;

    public static void main(String[] args) {

        String inputFile = "../files/random10000Names.txt";
//        String inputFile = "../files/ran.txt";
        String line = "";

        AverageRevisionCounter av = new AverageRevisionCounter();

        //String url = "https://en.wikipedia.org/w/api.php?action=query&titles=Anarchy&format=json&prop=extracts&continue&redirects&prop=revisions&rvdir=newer&rvlimit=max&rvprop=ids|flags|timestamp";
        try {
            BufferedReader bf = null;
            try {
                bf = new BufferedReader(new FileReader(inputFile));
                Date[] range = new Date[2];
                range[0] = new Date();                  //Todays date to minimum date
                range[1] = new Date(0L);    //Earliest date to maximum date
                int processedPage = 0;

                while ((line = bf.readLine()) != null) {
                    processedPage++;
                    String continueId = "0";
                    try {
                        while (!continueId.equals("-1")) {
                            URL url = av.generateUrl(line, continueId);
                            InputStream is = url.openStream();
                            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                            String jsonText = rd.readLine();

                            continueId = Integer.toString(av.processJson(new JSONObject(jsonText), range));

                            rd.close();
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(AverageRevisionCounter.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if (processedPage % 100 == 0) {
                        System.out.println("Pages Processed :" + processedPage);
                    }
                }

                System.out.println("Max Date: " + range[1]);
                System.out.println("Min Date: " + range[0]);
                System.out.println("Count: " + av.count);

                av.computeAverage(range);
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
        sb.append("&").append("titles=").append(title.trim().replace(" ", "+"));
        sb.append("&").append("format=").append("json");
        sb.append("&").append("prop=").append("extracts");
        sb.append("&").append("continue");
        sb.append("&").append("redirects");
        sb.append("&").append("prop=").append("revisions");
        sb.append("&").append("rvdir=").append("newer");
        sb.append("&").append("rvlimit=").append("max");
        sb.append("&").append("rvprop=").append("flags|timestamp");
        sb.append("&").append("rvcontinue=").append(continueId);

        String url = sb.toString();
        System.out.println("url: " + url);
        return new URL(url);
    }

    private int processJson(JSONObject jo, Date[] range) throws ParseException {
        JSONObject pages = jo.getJSONObject("query").getJSONObject("pages");
        Iterator<String> pageIdsIter = pages.keys();
        String pageId = pageIdsIter.next();

        if (!pages.getJSONObject(pageId).has("revisions")) {
            return -1;
        }

        JSONArray revisions = pages.getJSONObject(pageId).getJSONArray("revisions");

        count += revisions.length();
        for (int i = 0; i < revisions.length(); i++) {
            JSONObject rev = revisions.getJSONObject(i);

            if (!rev.has("minor")) {
                Date date = format.parse(rev.getString("timestamp").substring(0, 10));

                if (date.compareTo(range[0]) < 0) {         //date is before min date replace
                    range[0] = date;
                }

                if (date.compareTo(range[1]) > 0) {      //date is after max date replace
                    range[1] = date;
                }
            }
        }

        if (!jo.has("continue")) {
            return -1;
        }

        return jo.getJSONObject("continue").getInt("rvcontinue");

    }

    private void computeAverage(Date[] range) {
        long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
        double diffInDays = ((range[1].getTime() - range[0].getTime()) / DAY_IN_MILLIS);

        System.out.println("Average : " + (Double) Double.parseDouble(Long.toString(count)) / diffInDays);
    }
}
