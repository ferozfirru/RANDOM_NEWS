package com.example.feroz.randomnews;

/**
 * Created by feroz on 10-Jan-16.
 */

    import android.util.Log;

    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.io.UnsupportedEncodingException;

    import org.apache.http.HttpEntity;
    import org.apache.http.HttpResponse;
    import org.apache.http.client.ClientProtocolException;
    import org.apache.http.client.methods.HttpGet;
    import org.apache.http.impl.client.DefaultHttpClient;
    import org.apache.http.params.BasicHttpParams;
    import org.apache.http.params.HttpConnectionParams;
    import org.apache.http.params.HttpParams;
    import org.json.JSONException;
    import org.json.JSONObject;

    public class JsonParser {

        static InputStream is = null;
        static JSONObject jobj = null;
        static String json = "";
        public JsonParser(){

        }
        public JSONObject makeHttpRequest(String url){

            HttpGet httpget = new HttpGet(url);
            HttpParams httpParameters = new BasicHttpParams();
            int timeoutConnection = 3000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            int timeoutSocket = 5000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

            DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);

            try {
                HttpResponse httpresponse = httpclient.execute(httpget);
                HttpEntity httpentity = httpresponse.getEntity();
                is = httpentity.getContent();

            } catch (ClientProtocolException e) {
                Log.d("Error0","Testing here");
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.d("Error","Testing here");
                e.printStackTrace();
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                try {
                    while((line = reader.readLine())!=null){
                        sb.append(line+"\n");

                    }
                    is.close();
                    json = sb.toString();
                    try {
                        jobj = new JSONObject(json);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                Log.d("Error4","Testing here");
                e.printStackTrace();
            }
            System.gc();
            return jobj;

        }

    }
