package com.brightedge.classify.main;
/**
 * Created by Vignesh on 5/23/17.
 */

import java.util.Set;
import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Collections;
import java.util.stream.Collectors;


import com.brightedge.classify.bean.ParseData;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public final class ParseUrl{

    private final Set<String> stopwords;

    public ParseUrl(Set<String>stopwords) {
        this.stopwords = stopwords;
    }

    /**
     * Function to read the document object and generate a ParseData Object
     * methods
     *
     * @param url,doc: the URL to find the topics and Document Object doc.
     * @return : the output parseData Object
     */

    public final ParseData getpdObject(Document doc, String url){

        List<String> title = cleanText(doc.title());
        List<String> desc = cleanText(doc.select("meta[name=description]").first().attr("content"));
        List<String> body = cleanText(doc.body().text());
        List<String> anchorText = cleanText(genAnchortext(doc.select("a[href]")));
        List<String> urlWords = cleanText(genUrlwords(url));


        ParseData  pdObject = new ParseData(
                title,desc,body,anchorText,urlWords
        );

        return pdObject;
    }

    /**
     * Function to read the anchor tag text and trim length by 35.
     *
     * @param links: document Element
     * @return : the output trimed anchor text of the link
     */

    public final String genAnchortext(Elements links){
        StringBuilder tmp = new StringBuilder();
        for (Element link : links) {
            tmp.append(trim(link.text(), 35).toLowerCase()).append(" ");
        }
        return tmp.toString();
    }

    /**
     * Function to generate list of words from the URL
     *
     * @param url: url to find the topics
     * @return : the output contains List of words from the URL.
     */

    public final String genUrlwords(String url){
        String reg = ";|,|-|/";
        String[] res = url.split(reg);
        return Arrays.toString(res);

    }

    /**
     * Function to trim the anchortagtext
     *
     * @param s,width: respective string and the width to trim
     * @return : trimed version of the string
     */

    private String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }

    /**
     * Function to clean the text
     *
     * @param oldata: string to be cleaned
     * @return : the output contains cleaned version with removal of all punctuations and empty spaces.
     */

    private String operation1(String oldata){

        oldata =  oldata.toLowerCase().replaceAll("[^\\s\\w\\d@#-%&']", "").replaceAll("'s","");

        return oldata;
    }


    /**
     * Function to check length of each words and not in English stopwords.
     *
     * @param olddata: string to be cleaned
     * @return : string if it passes the case else null is returned
     */

    private String operation2(String olddata){
        olddata = olddata.trim();

        if (olddata.length() > 1 && ! olddata.matches("[0-9]+")
                && ! stopwords.contains(olddata) && olddata.length() < 10) {
            return olddata;
        }

        return null;
    }

    /**
     * Function to Clean the whole list of words
     *
     * @param words: word list to be cleaned
     * @return : The output will contain the cleaned version of the word list.
     */

    public final List<String> cleanText(String words){
        List<String> wordlist = new LinkedList<>(Arrays.asList(words.split(" +")));

        List<String> result = wordlist.stream()
                .map(x->operation1(x))
                .collect(Collectors.toList());

        List<String> finalresult = result.stream()
                .map(x->operation2(x))
                .collect(Collectors.toList());

        finalresult.removeAll(Collections.singleton(null));
        return finalresult;
    }



}


