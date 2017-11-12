package com.example.feroz.randomnews;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Random;

public class MainActivity extends Activity {

    JSONArray jar=null;

    JsonParser jp = new JsonParser();
    JSONObject jobject = null;
    QueryBuilder qb=null;

    String titleNoFormatting = "";
    String title = "";
    String imageurl = "";
    String unescapedUrl = "";
    String url = "";
    String publishedDate = "";
    String content = "";
    String publishertxt="";
    ImageView imageView2;
    TextView titletxt,contenttxt,datetxt,publisher;
    View rl=null;
    View rBtn=null;
    CheckInternet ch = new CheckInternet(MainActivity.this);
    Drawable myDrawable = null;
    Drawable loadingimage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        qb = new QueryBuilder();

       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        titletxt = (TextView) findViewById(R.id.titletxtview);
        contenttxt = (TextView) findViewById(R.id.contenttxtview);
        datetxt = (TextView) findViewById(R.id.datetxt);
        imageView2 = (ImageView) findViewById(R.id.imageView);
        publisher = (TextView) findViewById(R.id.publishertext);
        myDrawable = getResources().getDrawable(R.drawable.no_image);
        loadingimage = getResources().getDrawable(R.drawable.loadingimage);

        if(ch.isOK()){
            new Process().execute();
        }else{
            Toast.makeText(getApplicationContext(), "No Internet :(", Toast.LENGTH_LONG).show();
        }

        rl = findViewById(R.id.nextbutton);
        rBtn = findViewById(R.id.reloadButton);
        rl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if(ch.isOK()){
                    Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("weburl", unescapedUrl);
                    startActivity(intent);
                }else{

                    Toast.makeText(getApplicationContext(), "No Internet :(", Toast.LENGTH_LONG).show();
                }
            }

        });

        rBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Log.d("checkinternet",""+ch.isOK());
                if(ch.isOK()){
                    new Process().execute();
                }else{
                    Toast.makeText(getApplicationContext(), "No Internet :(", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            //Toast.makeText(getApplicationContext(), test , Toast.LENGTH_SHORT).show();
            this.finish();
            // Trigger Async Task (onPreExecute method)
            return true;
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        if(id == R.id.action_about){
            // set title
            alertDialogBuilder.setTitle("About the App");

            // set dialog message
            alertDialogBuilder
                    .setMessage("By Feroz Akbar, ferozfirru@gmail.com")
                    .setCancelable(false)
                    .setNegativeButton("Ok :)",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
            return true;
        }

       // return super.onOptionsItemSelected(item);
        return false;
    }


    class Process extends AsyncTask<String,String,JSONObject>{

        ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        @Override
        protected JSONObject doInBackground(String... params) {

            jobject =  jp.makeHttpRequest(qb.getQuery());

            return jobject;
        }

        @Override
        protected void onPreExecute(){
           dialog.setMessage("Loading...");
            dialog.show();
       super.onPreExecute();
        }

        @Override
        protected void onPostExecute(JSONObject jobj2) {

            try {

                jobject = jobj2.getJSONObject("responseData");
                jar = jobject.getJSONArray("results");
                jobject = jar.getJSONObject(new Random().nextInt(jar.length()));

                title = Html.fromHtml(jobject.getString("title")).toString();
                titleNoFormatting = jobject.getString("titleNoFormatting");
                try {
                    if(jobject.has("image")){
                        if(jobject.getJSONObject("image").has("url")){
                            imageurl = jobject.getJSONObject("image").getString("url");
                        }
                    }else{
                        imageurl = "";
                    }
                }catch(Resources.NotFoundException e){
                    Log.d("imageerror",imageurl);
                }
                unescapedUrl = jobject.getString("unescapedUrl");
                publishedDate = jobject.getString("publishedDate");
                publishertxt = jobject.getString("publisher");
                url = jobject.getString("url");
                content = Html.fromHtml(jobject.getString("content")).toString();
                imageView2.setImageDrawable(loadingimage);
                if(imageurl != "" && imageurl != null)
                {
                    new DownloadImageTask(imageView2).execute(imageurl);

                }else{
                    imageView2.setImageDrawable(myDrawable);
                }



                titletxt.setText(title);
                contenttxt.setText(content);
                datetxt.setText(publishedDate);
                publisher.setText(publishertxt);
                rl.setVisibility(View.VISIBLE);
                rBtn.setVisibility(View.VISIBLE);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                System.gc();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                in.close();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            System.gc();
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if(bmImage != null)
            {
                bmImage.setImageResource(0);
                bmImage.setImageDrawable(null);
            }
            try {
                bmImage.setImageBitmap(result);
            }catch (NullPointerException e){
                e.printStackTrace();
            }
            System.gc();
        }


    }

   private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }


}
