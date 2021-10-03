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
import android.widget.TextView;

public class custom_vote_fir extends BaseAdapter {

    private android.content.Context Context;
    String[] c;
    String[] d;




    public custom_vote_fir(android.content.Context applicationContext, String[] c, String[] d) {

        this.Context=applicationContext;
        this.c=c;
        this.d=d;


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
            gridView=inflator.inflate(R.layout.cust_vote_fir, null);



        }
        else
        {
            gridView=(View)convertview;

        }





        TextView tv1=(TextView)gridView.findViewById(R.id.textView15);














        //


        /////////////////////





        tv1.setTextColor(Color.BLACK);


        //tv6.setTextColor(Color.BLACK);
        //tv1.setText(c[position]);
        //tv2.setText(d[position]);

        tv1.setText(d[position]);

        Button b3=(Button) gridView.findViewById(R.id.button2);

        b3.setTag(c[position]);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kk=v.getTag().toString();

                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(Context);

                SharedPreferences.Editor ed=sh.edit();
                ed.putString("eid",kk);

                ed.commit();
//                Intent i=new Intent(Context,View_candidate.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                Context.startActivity(i);




            }
        });

        return gridView;
    }


}



