package com.bigw.laybymobile.android.module.preference;

import com.bigw.laybymobile.android.module.preference.bean.LaybyPreferences;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by srravela on 10/16/2015.
 */
public class PreferenceXMLParser  extends DefaultHandler{

    boolean currentElement = false;
    String currentValue = "";
    LaybyPreferences laybyPreferences;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        this.currentElement=true;
        currentValue="";
        if(this.laybyPreferences==null){
            this.laybyPreferences=new LaybyPreferences();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
//        super.endElement(uri, localName, qName);
        currentElement=false;
        if(localName.equalsIgnoreCase("battery")){
            laybyPreferences.setBattery(Integer.parseInt(currentValue));
        }else if(localName.equalsIgnoreCase("idle")){
            laybyPreferences.setIdle(Integer.parseInt(currentValue));
        }else if(localName.equalsIgnoreCase("accessCode")){
            laybyPreferences.setAccessCode(currentValue);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
//        super.characters(ch, start, length);
        if(currentElement){
            currentValue=currentValue+new String(ch,start,length);
        }
    }

    /**
     * Static method used for getting parsed preferences.
     * @return LaybyPreferences
     */
    public LaybyPreferences getLayByPreferences(){
        return this.laybyPreferences;
    }

}
