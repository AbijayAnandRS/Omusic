package com.abijayana.user.omusic;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
   static View rootview1a,rootview2a;
    TabLayout tabLayout;
    public SectionsPagerAdapter mSectionsPagerAdapter;
    public ViewPager mViewPager;
    ArrayList<songs> songlist;
    ArrayList<songs> imglist;
    ArrayList<songs> lik;

    imgadpter imgadp,adp2,sdp;
    RecyclerView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences spd=this.getSharedPreferences("SSL", Context.MODE_PRIVATE);
        SharedPreferences spf=this.getSharedPreferences("SSK", Context.MODE_PRIVATE);

        int v=spd.getInt("HAI",0);
        if(v==0) {
            Random r = new Random();
            int i1 = (r.nextInt(80) + 65);

            SharedPreferences.Editor ed=spd.edit();
            ed.putInt("HAI",1);
            ed.commit();
            SharedPreferences.Editor edf=spf.edit();
            edf.putString("HAI",String.valueOf(i1));
            edf.commit();




        }
        lik=new ArrayList<songs>();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager)findViewById(R.id.container12);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = (TabLayout)findViewById(R.id.tabs12);
        tabLayout.setupWithViewPager(mViewPager);
        LayoutInflater lf1=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE) ;
        LayoutInflater lf2=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE) ;
        rootview1a= lf1.inflate(R.layout.home,null);
        rootview2a=lf2.inflate(R.layout.history,null);
        RecyclerView rv=(RecyclerView)rootview1a.findViewById(R.id.recyclerview1);
        imglist=new ArrayList<songs>();
        imgadp=new imgadpter(imglist,R.layout.simpleitempic,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(imgadp);


        RecyclerView rv1=(RecyclerView) rootview1a.findViewById(R.id.recyclerview2);
        gv=(RecyclerView) rootview2a.findViewById(R.id.recyclerview3);
        songlist=new ArrayList<songs>();
        sdp=new imgadpter(songlist,R.layout.grid_item,this);
        adp2=new imgadpter(lik,R.layout.grid_item,this);

        gv.setLayoutManager(new GridLayoutManager(this, 2));
        gv.setItemAnimator(new DefaultItemAnimator());
        gv.setAdapter(adp2);

        rv1.setLayoutManager(new GridLayoutManager(this, 2));
        rv1.setItemAnimator(new DefaultItemAnimator());
        rv1.setAdapter(sdp);







        // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //.setAction("Action", null).show();


        Firebase fr=new Firebase("https://olaplay-6fa04.firebaseio.com/");
        Query iv=fr.child("songs").orderByKey();
        Query iv2=fr.child("images").orderByKey();
        String yu=spf.getString("HAI","HAI");
        Query ui=fr.child("history").child(yu).orderByKey();
        ui.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lik.clear();

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    songs s=new songs();
                    s.setImgUrl(String.valueOf(dataSnapshot1.child("cover_image").getValue()));

                    s.setNme(String.valueOf(dataSnapshot1.child("song").getValue()));
                    s.setAuthors(String.valueOf(dataSnapshot1.child("artists").getValue()));
                    s.setUrl(String.valueOf(dataSnapshot1.child("url").getValue()));

                    lik.add(s);

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        ui.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                adp2.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                adp2.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                adp2.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                adp2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        iv2.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                imglist.clear();

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    songs s=new songs();
                    s.setImgUrl(String.valueOf(dataSnapshot1.child("url").getValue()));
                    s.setNme(String.valueOf(dataSnapshot1.child("title").getValue()));
                    s.setAuthors(String.valueOf(dataSnapshot1.child("text").getValue()));

                    imglist.add(s);

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        iv2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                imgadp.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                imgadp.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                imgadp.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                imgadp.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });





        iv.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                songlist.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    songs s=new songs();
                    s.setImgUrl(String.valueOf(dataSnapshot1.child("cover_image").getValue()));

                    s.setNme(String.valueOf(dataSnapshot1.child("song").getValue()));
                    s.setAuthors(String.valueOf(dataSnapshot1.child("artists").getValue()));
                    s.setUrl(String.valueOf(dataSnapshot1.child("url").getValue()));
                    songlist.add(s);

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        iv.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
              sdp.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                sdp.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                sdp.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                sdp.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public  static class abi1a extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater1, ViewGroup container,
                                 Bundle savedInstanceState) {



            return rootview1a;
        }

    }
    public  static class abi2a extends Fragment{

        @Override
        public View onCreateView(LayoutInflater inflater2, ViewGroup container,
                                 Bundle savedInstanceState) {


            return rootview2a;
        }

    }




    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:

                    return new abi1a();


                case 1:

                    return new abi2a();

                default:
                    //this page does not exists
                    return null;
            }
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

        }

        @Override
        public int getCount() {

            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "HOME";
                case 1:
                    return "HISTORY";



            }
            return null;
        }
    }
}
