package com.tnaapp.tnalayout.ai;

import android.util.Log;

import com.google.gson.Gson;
import com.tnaapp.tnalayout.tien.RSS;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by YoungKaka on 10/10/2015.
 */
public class MyConverter {
    public static News jsonToRootNew(String json) {
        News news = new News();
        try {
            news = new Gson().fromJson(json, News.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return news;
    }

    public static RSS jsonToRSS(String json) {
        RSS rss = new RSS();
        try {
            rss = new Gson().fromJson(json, RSS.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rss;
    }

    public static RootVideo jsonToRootVideo(String json){
        RootVideo rootVideo = new RootVideo();
        try {
            rootVideo = new Gson().fromJson(json,RootVideo.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return rootVideo;
    }

    public static News readXML(String xml) {

        return null;
    }
}
