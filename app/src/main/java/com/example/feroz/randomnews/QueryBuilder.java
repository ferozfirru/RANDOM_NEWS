package com.example.feroz.randomnews;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

/**
 * Created by feroz on 15-Jan-16.
 */
public class QueryBuilder {
        private String[] country = {"india",""};
    private String[] topics = {
            "music",
            "song",
            "politics",
            "comedy",
            "movie",
            "movie",
            "movie",
            "movie",
            "movie",
            "masjid",
            "bible",
            "quran",
            "hindu",
            "belief",
            "death",
            "chaos",
            "internet",
            "facebook",
            "hindi",
            "tollywood",
            "bollywood",
            "kollywood",
            "tamilnadu",
            "andhra pradesh",
            "bangalore",
            "bihar",
            "delhi",
            "gujarat",
            "modi",
            "sex",
            "dance",
            "boy",
            "xbox",
            "windows",
            "linux",
            "ubuntu",
            "iphone",
            "mac",
            "soccer",
            "hockey",
            "girl",
            "court",
            "justice",
            "suicide",
            "bomb",
            "hotel",
            "prostitute",
            "telugu",
            "hindi",
            "english",
            "tamil",
            "kannada",
            "malayalam",
            "bhojpuri",
            "sea",
            "rain",
            "tsunami",
            "weather",
            "farmer",
            "soldier",
            "army",
            "police",
            "politician",
            "doctor",
            "controversy",
            "controversial",
            "boys",
            "girls",
            "mother",
            "father",
            "kids",
            "senior",
            "ghost",
            "sex",
            "cricket",
            "ball",
            "actor",
            "actress",
            "bus",
            "train",
            "good",
            "bad",
            "scandal",
            "baba",
            "education",
            "study","studies",
            "rupee","money",
            "muslim","hindu","sikh","christian",
            "worst",
            "technology",
            "won","win",
            "hockey","olympic"
    };
    String q1,q2,finalcombination;
    String queryurl;
    String userip = "175.101.144.11";
    public String getQuery(){
        q1 = topics[new Random().nextInt(topics.length)];
        q2 = topics[new Random().nextInt(topics.length)];
        q1 = q1.concat("+");
        q1 = q1.concat(q2);
        try {
            finalcombination = URLEncoder.encode(q1,"utf-8");
            String ctext = country[new Random().nextInt(country.length)];
                if(!ctext.isEmpty()){
                    finalcombination = finalcombination.concat("+");
                    finalcombination = finalcombination.concat(ctext);
                }
            queryurl = "https://ajax.googleapis.com/ajax/services/search/news?v=1.0&q="+finalcombination+"&ned=in&v=1.0&userip=";
            queryurl = queryurl.concat(getCurrentIP());
            //Log.d("Generated url",queryurl);
        }catch (UnsupportedEncodingException e){
            Log.d("Testing New class",e.toString());
            e.printStackTrace();
        }
        System.gc();
        return queryurl ;
    }

    public String getCurrentIP () {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet("http://ifcfg.me/ip");
            // HttpGet httpget = new HttpGet("http://ipecho.net/plain");
            HttpResponse response;

            response = httpclient.execute(httpget);

            //Log.i("externalip",response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                long len = entity.getContentLength();
                if (len != -1 && len < 1024) {
                    String str= EntityUtils.toString(entity);
                    //Log.i("externalip",str);
                    //return str;
                } else {


                }
                return URLEncoder.encode(EntityUtils.toString(entity).trim(), "utf-8");
            } else {

                Log.i("Null", response.getStatusLine().toString());
            }

        }
        catch (Exception e)
        {

        }
return "";
    }

}
