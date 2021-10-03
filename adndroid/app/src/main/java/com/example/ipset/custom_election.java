package com.example.ipset;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class custom_election extends BaseAdapter {

    private android.content.Context Context;
    String[] c;
    String[] d;
    String[] e,f,g;




    public custom_election(android.content.Context applicationContext, String[] c, String[] d,String[] e,String[] f,String[] g) {

        this.Context=applicationContext;
        this.c=c;
        this.d=d;
        this.e=e;
        this.f=f;
        this.g=g;


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
            gridView=inflator.inflate(R.layout.cust_elect, null);



        }
        else
        {
            gridView=(View)convertview;

        }





        TextView tv1=(TextView)gridView.findViewById(R.id.textView15);

        TextView tv2=(TextView)gridView.findViewById(R.id.textView17);

        TextView tv3=(TextView)gridView.findViewById(R.id.textView19);


        TextView tv4=(TextView)gridView.findViewById(R.id.textView21);


        ImageView im=(ImageView)gridView.findViewById(R.id.imageView3);










        //


        /////////////////////





        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);

        tv3.setTextColor(Color.BLACK);

        tv4.setTextColor(Color.BLACK);


        //tv6.setTextColor(Color.BLACK);
        //tv1.setText(c[position]);
        //tv2.setText(d[position]);

        tv1.setText(d[position]);
        tv2.setText(e[position]);
        tv3.setText(f[position]);
        tv4.setText(g[position]);

//        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(Context);
//        String ss=sh.getString("ip", "");
//        String url = "http://" + ss + ":5000"+i[position];
//        //Toast.makeText(Context, "tstid ass="+url, Toast.LENGTH_LONG).show();
//
//        //Picasso.with(Context).load(url).into(im);
//        Picasso.with(Context).load(url).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).transform(new CircleTransform()).into(im);


        Button b4=(Button) gridView.findViewById(R.id.button);

        b4.setTag(c[position]);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(Context);



                final String eid =v.getTag().toString();

                SharedPreferences.Editor ed=sh.edit();
                ed.putString("eid",eid);


                ed.commit();
                Intent i=new Intent(Context,User_view_candi.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Context.startActivity(i);







            }
        });






        return gridView;
    }


}




