package com.brightedge.classify.main;

import com.brightedge.classify.bean.ParseData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import java.io.IOException;

public class Assignment {

    private static final int NUMBER_OF_VALID_ARGUMENTS = 1;
    public static final String PARSE_FAILURE = "failure";
    private static final int STATUS_FAILURE = 1;


    public static void main(String[] args) {

        if (args.length == NUMBER_OF_VALID_ARGUMENTS) {

            Assignment assignmentObject = new Assignment();
            List<String> result = assignmentObject.findTopics(args[0]);
            if (result.size() == 0){
                System.out.println("Error");
            }
            else {
                System.out.println("Relevant Topics are listed below: ");
                System.out.println(result);
            }

        }
        else{

            System.out.println("Error: Check the Number of arguments passed.");

        }

    }

    /**
     * Function to read the URL, find relevant topics  and prepare formatted output through other helper
     * methods
     *
     * @param url: the URL to find the topics
     * @return : the output contains relevant topics for the URL
     */

    public List<String> findTopics(String url){
        List<String> result;
        HashSet<String> stopwords;
        try {
            Stopwords s = new Stopwords();
            stopwords =  s.getStopwordset();
            if (stopwords.size() == 0){
                return null;
            }

            Document doc = connect(url);

            if (doc.equals("")){
                return null;
            }
            else {

                ParseUrl item = new ParseUrl(stopwords);
                ParseData pdObject = item.getpdObject(doc,url);
                WeightedCount wc = new WeightedCount();

                wc.setHm(pdObject);

                result = wc.getTopFreq();
                if (result.size() == 0){
                    return null;
                }
                List<String> answer = new LinkedList<String>();
                for (int i=0; i< 6;i++) answer.add(result.get(i));
                return answer;
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * Function to connect to the URL. In case of heavy traffic or unable to load for the first time. it wil retry
     * for three times.
     *
     * @param url: the URL to find the topics
     * @return : the output document object of the URL
     */

    private static Document connect(String url){
        // Note: Oftentimes a page can be very busy. Setting Timeout to 10 s.
        StringBuilder sb = new StringBuilder();
        Document doc = null;
        int i = 0;
        boolean success = false;

        while( i < 3){
            try {
                doc = Jsoup.connect(url).get();
                success = true;
                break;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            i++;
        }
        if (success){
            return doc;
        }else {
            return null;
        }

    }


}
