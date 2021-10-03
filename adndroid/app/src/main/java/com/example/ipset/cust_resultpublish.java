package com.example.ipset;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by riss on 6/10/2018.
 */
public class cust_resultpublish extends BaseAdapter {

    private android.content.Context Context;
    String[] c;
    String[] d;
    String[] e;
    String[] f;
    String[] p;



    public cust_resultpublish(android.content.Context applicationContext, String[] c, String[] d,String[] e,String[] f,String[] p) {

        this.Context=applicationContext;
        this.c=c;
        this.d=d;
            this.e=e;
        this.f=f;
        this.p=p;

    }

    @Override
    public int getCount() {

        return d.length;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {


        LayoutInflater inflator=(LayoutInflater)Context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(convertview==null)
        {
            gridView=new View(Context);
            gridView=inflator.inflate(R.layout.cust_resu, null);



        }
        else
        {
            gridView=(View)convertview;

        }





        TextView tv1=(TextView)gridView.findViewById(R.id.te2);

        TextView tv2=(TextView)gridView.findViewById(R.id.te4);
        TextView tv3=(TextView)gridView.findViewById(R.id.te6);
        TextView tv4=(TextView)gridView.findViewById(R.id.te69);


        ImageView im=(ImageView)gridView.findViewById(R.id.imageView1);










        //


        /////////////////////





        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);

        //tv6.setTextColor(Color.BLACK);
        //tv1.setText(c[position]);
        //tv2.setText(d[position]);

        tv1.setText(c[position]);
        tv2.setText(d[position]);
        tv3.setText(e[position]);
        tv4.setText(p[position]);


        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(Context);
        String ss=sh.getString("ip", "");
        String url = "http://" + ss + ":5000/"+f[position];
        //Toast.makeText(Context, "tstid ass="+url, Toast.LENGTH_LONG).show();

       //Picasso.with(Context).load(url).into(im);
        Picasso.with(Context).load(url).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).transform(new CircleTransform()).into(im);


        return gridView;
    }


}

