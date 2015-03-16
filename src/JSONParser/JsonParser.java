/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSONParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author kchakrab
 */
public class JsonParser {

    public static void main(String[] args) {

        parseStringToJson(getJsonString());
    }

    public static String getJsonString() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("/home/kchakrab/workspace/files/jsonTestFile.txt"));
            String line = br.readLine();
            while (line != null) {
                System.out.println(line);
                return line;
            }
        } catch (IOException ex) {
            Logger.getLogger(JsonParser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(JsonParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return "";

    }

    public static void parseStringToJson(String Input) {
        JSONObject obj = new JSONObject(Input);

        System.out.println(obj);
    }

}
