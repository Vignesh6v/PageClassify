package com.brightedge.classify.main;

import java.io.InputStream;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.io.InputStreamReader;

/**
 * Created by Vignesh on 5/23/17.
 */
class Stopwords {

    private static final String FILENAME = "/stopwordlist.txt";
    private static final String elements[] = {"http","like","https","videos","www","stars","video"};
    private final HashSet<String> stopwordset;


    protected Stopwords(){
        this.stopwordset = fetchWords();
    }

    protected HashSet<String> getStopwordset() {
        return stopwordset;
    }



    /**
     * Function to read the list of English stopwords from the file and generate a Hashset of stopwords.
     * methods
     *
     * @return : the output contains set of stopwords.
     */


    private HashSet<String> fetchWords(){

        HashSet<String> tmp = new HashSet<>();
        HashSet<String> otherwords = new HashSet<String>(Arrays.asList(elements));

        try{

            InputStream in = getClass().getResourceAsStream(FILENAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                tmp.add(sCurrentLine.trim().toLowerCase());
            }
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }

        tmp.addAll(otherwords);
        return tmp;

    }
}
