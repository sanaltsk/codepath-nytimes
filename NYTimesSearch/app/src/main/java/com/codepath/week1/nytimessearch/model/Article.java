package com.codepath.week1.nytimessearch.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sanal on 9/21/17.
 */

public class Article implements Serializable {
    String link;
    String headLine;
    String thumbNail;

    @Override
    public String toString() {
        return "Article{" +
                "link='" + link + '\'' +
                ", headLine='" + headLine + '\'' +
                ", thumbNail='" + thumbNail + '\'' +
                '}';
    }

    public Article(JSONObject jsonObject) {
        try {
            this.link = jsonObject.getString("web_url");
            this.headLine = jsonObject.getJSONObject("headline").getString("main");
            JSONArray multimedia = jsonObject.getJSONArray("multimedia");
            if(multimedia.length()>0) {
                JSONObject multimediaJson = multimedia.getJSONObject(0);
                this.thumbNail = "http://www.nytimes.com/" + multimediaJson.getString("url");
            } else {
                this.thumbNail = "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Article> fromJSONArray(JSONArray array) {
        ArrayList<Article> results = new ArrayList<>();
        for(int i=0;i<array.length();i++){
            try {
                results.add(new Article(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    public String getLink() {
        return link;
    }

    public String getHeadLine() {
        return headLine;
    }

    public String getThumbNail() {
        return thumbNail;
    }


}
