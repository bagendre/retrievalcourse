/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.id.IndonesianAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.snowball.*;
import org.apache.lucene.analysis.id.*;

/**
 *
 * @author Lenovo
 */
public class Document implements Comparable<Document>{

    private int id;
    private String content;

    public Document() {
    }

    public Document(String content) {
        this.content = content;
    }

    public Document(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getListofTerm() {
        String value = this.getContent();
        value = value.replaceAll("[.,?!]", "");
        return value.split(" ");
    }

    public ArrayList<Posting> getListofPosting() {
        String[] tempString = getListofTerm();
        ArrayList<Posting> list = new ArrayList<>();
        for (int i = 0; i < tempString.length; i++) {
            if (i == 0) {
                Posting tempPosting = new Posting(tempString[0], this);
                list.add(tempPosting);
            } else {
                Collections.sort(list);
                Posting tempPosting = new Posting(tempString[i], this);
                int indexCari = Collections.binarySearch(list, tempPosting);
                if (indexCari < 0) {
                    list.add(tempPosting);
                } else {
                    int tempNumber = list.get(indexCari).getNumberOfTerm() + 1;
                    list.get(indexCari).setNumberOfTerm(tempNumber);
                }
            }
        }
        Collections.sort(list);
        return list;
    }

    @Override
    public int compareTo(Document o) {
        return Integer.compare(this.id, o.id);
    }
    public void IndonesiaStemming(){
        String text = content;
        Version matchVersion = Version.LUCENE_7_7_0;
        StringReader tReader = new StringReader(text);
        IndonesianAnalyzer analyzer = new IndonesianAnalyzer();
        TokenStream tStream = analyzer.tokenStream("contents",tReader);
        //buat token
        TokenStream tokenStream = analyzer.tokenStream(
                "myField",
                new StringReader(text.trim()));
        //stemming
        tokenStream = new PorterStemFilter(tokenStream);
        //buat string baru
        StringBuilder sb = new StringBuilder();
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        try{
            tokenStream.reset();
            while(tokenStream.incrementToken()){
                String term = charTermAttribute.toString();
                sb.append(term + " ");
            }
        } catch (IOException ex) {
            System.out.println("Exception : " + ex);
        }
        content = sb.toString();
    }
}
