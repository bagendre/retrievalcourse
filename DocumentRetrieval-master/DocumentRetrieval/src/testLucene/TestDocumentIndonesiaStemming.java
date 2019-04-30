/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testLucene;

import model.Document;

/**
 *
 * @author Andrew
 */
public class TestDocumentIndonesiaStemming {
    public static void main(String[] args) {
        // TODO code application logic here
        Document doc =new Document(1,"ibu sedang pergi ke pasar"+"Bapak sedang membaca koran");
        doc.IndonesiaStemming();
        System.out.println(doc);
    }
    
}
