package com.example.ipset;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class custom_viw_noti extends BaseAdapter {

    private android.content.Context Context;
    String[] c;
    String[] d;
    String[] e;



    public custom_viw_noti(android.content.Context applicationContext, String[] c, String[] d,String[] e) {

        this.Context=applicationContext;
        this.c=c;
        this.d=d;
        this.e=e;

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
            gridView=inflator.inflate(R.layout.cust_noti, null);



        }
        else
        {
            gridView=(View)convertview;

        }





        TextView tv1=(TextView)gridView.findViewById(R.id.textView15);

        TextView tv2=(TextView)gridView.findViewById(R.id.textView17);

        TextView tv3=(TextView)gridView.findViewById(R.id.textView19);












        //


        /////////////////////





        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);

        tv3.setTextColor(Color.BLACK);


        //tv6.setTextColor(Color.BLACK);
        //tv1.setText(c[position]);
        //tv2.setText(d[position]);

        tv1.setText(c[position]);
        tv2.setText(d[position]);
        tv3.setText(e[position]);


        return gridView;
    }


}



