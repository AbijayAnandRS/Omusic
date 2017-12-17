package com.abijayana.user.omusic;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by user on 17-12-2017.
 */

public class Music extends AppCompatActivity {
    SharedPreferences sp1,sp2,sp3,sp4;
    String name,authors,imgurl,url;
    private boolean playPause;
    private MediaPlayer mediaPlayer;
    private ProgressDialog progressDialog;
    private boolean initialStage = true;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.playiing);

        sp1=this.getSharedPreferences("SSD", Context.MODE_PRIVATE);
        sp2=this.getSharedPreferences("DDS", Context.MODE_PRIVATE);
        sp3=this.getSharedPreferences("ASD",Context.MODE_PRIVATE);
        sp4=this.getSharedPreferences("ASB",Context.MODE_PRIVATE);
        SharedPreferences spf=this.getSharedPreferences("SSK", Context.MODE_PRIVATE);
        String df=spf.getString("HAI","HAI");
        Firebase fr=new Firebase("https://olaplay-6fa04.firebaseio.com/");
        long t=System.currentTimeMillis();




        name=sp1.getString("HAI","HAI");
        authors=sp2.getString("HAI","HAI");
        imgurl=sp3.getString("HAI","HAI");
        url=sp4.getString("HAI","HAI");
        HashMap<String,Object> hs=new HashMap<String,Object>();
        hs.put("song",name);
        hs.put("artists",authors);
        hs.put("cover_image",imgurl);
        hs.put("url",url);
        fr.child("history").child(df).child(String.valueOf(t)).updateChildren(hs, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {

            }
        });


        ImageView iv=(ImageView)findViewById(R.id.imageView2);
        TextView tv1=(TextView)findViewById(R.id.textView);
        TextView tv2=(TextView)findViewById(R.id.textView2);
        b1=(Button)findViewById(R.id.button);
        Picasso.with(this).load(imgurl).into(iv);
        tv1.setText(name);tv2.setText(authors);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        progressDialog = new ProgressDialog(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!playPause) {
                    b1.setBackgroundResource(R.mipmap.pause);

                    if (initialStage) {
                        new Player().execute(url);
                    } else {
                        if (!mediaPlayer.isPlaying())
                            mediaPlayer.start();
                    }

                    playPause = true;

                } else {
                    b1.setBackgroundResource(R.mipmap.play);

                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }

                    playPause = false;
                }

            }
        });




    }
    class Player extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared = false;

            try {
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        initialStage = true;
                        playPause = false;
                        b1.setBackgroundResource(R.mipmap.play);
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });

                mediaPlayer.prepare();
                prepared = true;

            } catch (Exception e) {

                prepared = false;
            }

            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }

            mediaPlayer.start();
            initialStage = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Wait...");
            progressDialog.show();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
