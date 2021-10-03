package com.example.ipset;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class custom_nomi2 extends BaseAdapter {

    private android.content.Context Context;
    String[] c;
    String[] d,e,f;




    public custom_nomi2(android.content.Context applicationContext, String[] c, String[] d,String[] e,String[] f) {

        this.Context=applicationContext;
        this.c=c;
        this.d=d;
        this.e=e;
        this.f=f;


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
            gridView=inflator.inflate(R.layout.cust_nomini2, null);



        }
        else
        {
            gridView=(View)convertview;

        }





        TextView tv1=(TextView)gridView.findViewById(R.id.textView15);

        TextView tv2=(TextView)gridView.findViewById(R.id.textView5);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView7);













        //


        /////////////////////





        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);



        tv1.setText(d[position]);
        tv2.setText(e[position]);
        tv3.setText(f[position]);



        return gridView;
    }


}




