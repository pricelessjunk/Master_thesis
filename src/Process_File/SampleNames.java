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
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kaustuv
 */
public class SampleNames {

    static int sampleCount = 10000;
    static int totalLinesOrig = 4624939;    //known before hand

    public static void main(String[] args) {

        List list = loadToList("../files/namesFinal.txt");

        List<String> sampledList = getSampledList(list);

        writeToFile(sampledList, "../files/random" + sampleCount + "Names.txt");

    }

    private static List<String> loadToList(String inputFile) {

        List<String> ranList = new ArrayList<>(totalLinesOrig);
        String line;

        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));

            try {
                while ((line = br.readLine()) != null) {
                    ranList.add(line);
                }
            } finally {
                br.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(ProcessText.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("List Created. Size: " + ranList.size());

        return ranList;
    }

    private static void writeToFile(List<String> list, String outputFile) {
        System.out.println("Writing sampled list to file");
        try {

            PrintWriter out = new PrintWriter(outputFile);
            try {

                for (String s : list) {
                    out.println(s);
                }
            } finally {
                out.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ProcessText.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Writing done");
    }

    private static List<String> getSampledList(List<String> origList) {
        System.out.println("Begining Sampling of list");
        List<String> ranList = new ArrayList<>(sampleCount);
        BitSet bs = new BitSet();
        int ind = 0;

        while (ind < sampleCount) {
            Random rand = new Random();
            int randomNum = rand.nextInt(totalLinesOrig);

            if (!bs.get(randomNum)) {
                bs.set(randomNum);
                ranList.add(origList.get(randomNum));
                ind++;
            }

        }

        System.out.println("Sample list generated.");
        return ranList;
    }

}
