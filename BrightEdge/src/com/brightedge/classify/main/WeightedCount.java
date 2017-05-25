package com.brightedge.classify.main;

import com.brightedge.classify.bean.ParseData;

import java.util.*;

/**
 * Created by Vignesh on 5/23/17.
 */

public class WeightedCount {

    private final HashMap<String,Integer> weights;
    private HashMap<String, Integer> hm;
    private TreeMap<Integer,String> sortedmap;

    /**
     * Constructor with the initial weight for the corresponding types.
     * These weights are uptained after several tried, in order predict the relevant topics.
     */

    public WeightedCount(){
        hm = new HashMap<>();
        weights  = new HashMap<String, Integer>()
                    {{
                        put("title", 29);
                        put("url", 11);
                        put("link", 1);
                        put("body", 2);
                        put("desc", 7);
                        put("two", 2);
                        put("three",3);
                    }};
    }

    public HashMap<String, Integer> getHm() {
        return hm;
    }


    /**
     * Function to do the wordcount for different type of Parsedata.
     *
     * @param pdObject: ParseData Object
     * @return : void
     */
    public void setHm(ParseData pdObject) {

        analyseWord(pdObject.getTitle(), "title");
        analyseWord(pdObject.getDesc(), "desc");
        analyseWord(pdObject.getUrlWords(), "url");
        analyseWord(pdObject.getAnchorText(), "link");
        analyseWord(pdObject.getBody(), "body");

    }

    /**
     * Function to anlyse each word and generate n-grames(n=1,2,3) words and the corresponding freq count.
     *
     * @param words,type: List of words and the types of those words.
     * @return : void
     */

    private void analyseWord(List<String> words, String type){

        int multiplier = 1;
        if (weights.containsKey(type)){
            multiplier = weights.get(type);
        }

        for(int i=0;i<words.size();i++){

             // 1 words
             hm.put(words.get(i),hm.getOrDefault(words.get(i), 0)+(1 * multiplier));

             // 2 words
             if (i < words.size()-1){
                 String two_word = String.format("%s %s", words.get(i), words.get(i + 1));
                 two_word = two_word.trim();

                 if (! two_word.equals(words.get(i))){
                     hm.put(two_word,hm.getOrDefault(two_word, 0)+(weights.get("two")*multiplier));
                 }

             }

            // 3 words
            if (i < words.size()-2){
                String three_word = String.format("%s %s %s", words.get(i), words.get(i + 1), words.get(i + 2));
                three_word = three_word.trim();

                if (! three_word.equals(words.get(i))){
                    hm.put(three_word,hm.getOrDefault(three_word, 0)+(weights.get("three")*multiplier));
                }

            }

        }

    }

    /**
     * Function to check whether a string is a substring of other or not
     *
     * @param data,index,top20: data to be checked with top20 and its corresponding index in top20 is also passed.
     * @return : true if not a substring else false
     */

    private boolean notSubstring(String data, int index, List<String> top20){
        String item = data.replace(" ", "");
        for (int i=0;i<top20.size();i++){
            if ( i!= index && top20.get(i).replace(" ", "").contains(item)){
                return false;
            }
        }

        return true;
    }

    /**
     * Function to sort the hashmap and get the top20 freq words
     */

    public List<String> getTopFreq(){
        sortedmap = new TreeMap<Integer,String>();
        List<String> top20 = new ArrayList<>();
        List<String> result = new ArrayList<>();
        int count = 0;

        for (Map.Entry entry: hm.entrySet()){
            sortedmap.put(-(Integer)entry.getValue(), (String) entry.getKey());
        }

        for(Map.Entry entry : sortedmap.entrySet()) {
            if (count > 20) break;
            String key = (String) entry.getValue();
            Integer value = -(Integer) entry.getKey();
            top20.add(key);
            count++;
            //System.out.println(key + " => " + value);

        }

        for (int i=0;i<top20.size();i++){
            if (notSubstring(top20.get(i),i,top20)){
                result.add(top20.get(i));
            }
        }
        return result;

    }

}
