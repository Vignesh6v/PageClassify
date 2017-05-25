package com.brightedge.classify.bean;

import java.util.List;

/**
 * Created by Vignesh on 5/24/17.
 */
public class ParseData {

    private  final List<String> title;
    private  final List<String> desc;
    private  final List<String> body;
    private  final List<String> urlWords;
    private  final List<String> anchorText;


    /**
     * Construtor to initalize the corresponding instance varibles to the respective document element.
     */

    public ParseData(List<String> title,List<String> anchorText,List<String> body,
                     List<String> desc,List<String> urlWords){

        this.title = title;
        this.anchorText = anchorText;
        this.body = body;
        this.desc = desc;
        this.urlWords = urlWords;

    }

    public List<String> getTitle() {
        return title;
    }

    public List<String> getAnchorText() {
        return anchorText;
    }

    public List<String> getBody() {
        return body;
    }

    public List<String> getDesc() {
        return desc;
    }

    public List<String> getUrlWords() {
        return urlWords;
    }

}


