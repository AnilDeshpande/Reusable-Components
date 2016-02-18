package com.bigw.laybymobile.android.module.preference;

import android.content.Context;
import android.os.Environment;
import android.util.JsonReader;
import android.util.Log;
import android.util.Xml;

import com.bigw.laybymobile.android.R;
import com.bigw.laybymobile.android.module.preference.bean.LaybyPreferences;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by srravela on 10/14/2015.
 */
public class FileReaderAndWriter {
    private static final String TAG = FileReaderAndWriter.class.getSimpleName();
    private static File preferencesFile;
    private static String preferencesFileName;
    private static String preferencesFolderName;
    public static FileReaderAndWriter sharedFileReaderAndWriter = null;
    public static HashMap<String, String> preferencesMap = new HashMap<String, String>();
    public static XmlPullParser xmlParser;
    private LaybyPreferences laybyPreference;

    /**
     * Constructor used for initializing the FileReaderAndWriter.
     */
    private FileReaderAndWriter() {
        super();
    }

    /**
     * Static method used for instantiating the FileReaderAndWriter using constructor.
     * @param preferencesFileName
     * @return FileReaderAndWriter
     */
    public static FileReaderAndWriter getSharedFileReaderAndWriter(String preferencesFileName) {

        if(sharedFileReaderAndWriter == null) {
            sharedFileReaderAndWriter = new FileReaderAndWriter();
            initializePreferencesFile(preferencesFileName);
        }
        return (FileReaderAndWriter)sharedFileReaderAndWriter;
    }

    /**
     * Static method used for initializing the preferences file.
     * @param fileName
     */
    public static void initializePreferencesFile(String fileName) {
        preferencesFileName = fileName;
        preferencesFolderName = "layby_preferences";
        File sdCard = Environment.getExternalStorageDirectory();
        if(sdCard.exists() && sdCard.canRead()) {
            File preferencesFolder = new File(sdCard.getAbsolutePath() + File.separator + preferencesFolderName);
            if(preferencesFolder.exists()&& preferencesFolder.canRead()) {
                preferencesFile = new File(preferencesFolder.getAbsolutePath()+File.separator+preferencesFileName);
            }
        }
    }

    /**
     * Static method used for getting the preferences file path.
     * @return String
     */
    public static String getSharedPreferencesFilePath() {
        return preferencesFile.getAbsolutePath();
    }

    /**
     * Static method used for verifying if the the preferences file is readable or not.
     * @return boolean
     */
    public static boolean isPreferencesFileReadable() {
        return (preferencesFile.exists()&&preferencesFile.canRead());
    }

    /**
     * Static method used for reading the preferences from the preferences.xml file.
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @return LaybyPreferences
     */
    public LaybyPreferences getLaybyPreference() throws FileNotFoundException, IOException,ParserConfigurationException, SAXException{
        LaybyPreferences laybyPreferences = null;
        if (preferencesFile != null) {
            preferencesMap = new HashMap<String, String>();
            InputStream inputStream = null;
            inputStream = new FileInputStream(preferencesFile);
            laybyPreferences = parseXML(inputStream);

        }
        return laybyPreferences;
    }

    /**
     * Static method used for writing the preferences to the preferences.xml file.
     * @throws FileNotFoundException
     * @throws IOException
     * @throws Exception
     * @return LaybyPreferences
     */
    public void writePreferecenToXmlFile(LaybyPreferences newLaybyPreference) throws FileNotFoundException, IOException, Exception{
        String xml=null;
        StringWriter stringWriter=new StringWriter();

            FileOutputStream fileOutputStream=new FileOutputStream(preferencesFile);
            XmlSerializer xmlSerializer=Xml.newSerializer();
            xmlSerializer.setOutput(stringWriter);
            xmlSerializer.startTag("", "preferences");

            xmlSerializer.startTag("", "battery");
            xmlSerializer.text("" + newLaybyPreference.getBattery());
            xmlSerializer.endTag("", "battery");

            xmlSerializer.startTag("", "idle");
            xmlSerializer.text("" + newLaybyPreference.getIdle());
            xmlSerializer.endTag("", "idle");

            xmlSerializer.startTag("", "accessCode");
            xmlSerializer.text("" + newLaybyPreference.getAccessCode());
            xmlSerializer.endTag("", "accessCode");

            xmlSerializer.endTag("", "preferences");

            xmlSerializer.endDocument();
            xmlSerializer.flush();
            xml=stringWriter.toString();
            fileOutputStream.write(xml.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
    }

    /**
     * Static method used for parsing the preferences in the preferences.xml file.
     * @throws FileNotFoundException
     * @throws IOException
     * @throws Exception
     * @return LaybyPreferences
     */
    private  LaybyPreferences parseXML(InputStream inputStream) throws FileNotFoundException,IOException, ParserConfigurationException, SAXException{

        LaybyPreferences laybyPreference = null;
        FileInputStream fileInputStream=new FileInputStream(preferencesFile);
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(fileInputStream));
        String line="";
        StringBuilder stringBuilder=new StringBuilder();
        while ((line=bufferedReader.readLine())!=null ){
            stringBuilder.append(line);
        }

        String parsedData = "";
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();
        XMLReader xr = sp.getXMLReader();

        PreferenceXMLParser myXMLHandler = new PreferenceXMLParser();
        xr.setContentHandler(myXMLHandler);
        InputSource inputSource = new InputSource();
        inputSource.setCharacterStream(new StringReader(stringBuilder.toString()));
        xr.parse(inputSource);
        laybyPreference=myXMLHandler.getLayByPreferences();
        /*Log.i(TAG,laybyPreference.getAccessCode() );*/

        return laybyPreference;
    }

}
