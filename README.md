README
=========

Given any page (URL), be able to classify the page, and return a list of relevant topics.

Files Includes
==========
* Assignment.java
* ParseUurl.java
* Stopwords.java
* WeightedCount.java
* AssignmentTest.java
* stopwordlist.txt

Requirements
=========

* Jsoup
* Junit
* Java JDK 1.8

Instruction
=========

Execute following command from the home directory of the project java -jar BrightEdge.jar
(e.g. java -jar BrightEdge.jar http://blog.rei.com/camp/how-to-introduce-your-indoorsy-friend-to-the-outdoors/)

Documentation
=========

* The given URL is parsed using Jsoup library. We clean the text by ignoring characters with digits alone, special characters, remove stopwords and punctuation marks.

* This returned text in a List which is used to create a HashMap of words(1-gram, 2-gram and 3-gram) and their frequency of occurrences with the corresponding weights.

* This TreeHashmap is used to sorted and getting top 20 frequency words.

* Finally we filter it by removing the substring of other strings and return the top 5 frequent words as the related topics.

Analysis
======

* Stopwords - The Stopwords class read the list of stopwords listed in the file named stopwordlist.txt and have
the whole document in a set.

* ParseUrl - The ParseUrl class takes in URL and connect via HTTP using the Jsoup library to fetch the contents of the page,
 and construct the whole document. This document contains the title, list of anchor tage, meta-tag description, and all the text in the body.
 Then we also clean each word in the documents like removing stopwords, length of words should be greater than 1.

* WeightedCount - The WeightedCount class constructor first initialize the weights for each type of information.
The weights are chosen after 100 manual tries in finding the topic and then they have assigned the corresponding
values.

    * Title : 29
    * Url : 11
    * link : 1
    * body : 2
    * Description : 7
    * 1-grams : 1
    * 2-grams : 2
    * 3-grams : 3
