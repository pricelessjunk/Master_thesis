/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Process_File;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kaustuv
 */
public class ProcessText {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int totalLines = 0;
        int linesRemoved = 0;
        int linesAfterRemoval = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/kaustuv/work/files/copy.txt"));
            PrintWriter out = new PrintWriter("/home/kaustuv/work/files/copy2.txt");

            try {
                String line = br.readLine();

                while (line != null) {
                    String str = line;
                    //String str = line.substring(line.indexOf(">") + 1, line.indexOf("</title>"));
                    System.out.println(str);
                    totalLines++;
                    if (/*!str.contains("User:") && !str.contains("Talk:") && !str.contains("Image:")*/ 
                            !str.contains("Wikipedia:") && !str.contains("talk:")) {
                        out.println(str);
                        linesAfterRemoval++;
                    } else {
                        //System.out.println("Line Remove : " + str);
                        linesRemoved++;
                    }

                    line = br.readLine();
                }

            } finally {
                br.close();
                out.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(ProcessText.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Total Lines: " + totalLines);
        System.out.println("Lines Removed: " + linesRemoved);
        System.out.println("Lines after Removal: " + linesAfterRemoval);

    }

}
