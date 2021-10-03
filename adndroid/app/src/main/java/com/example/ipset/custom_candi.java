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

public class custom_candi extends BaseAdapter {

    private android.content.Context Context;
    String[] c;
    String[] d;
    String[] e,f,g;




    public custom_candi(android.content.Context applicationContext, String[] c, String[] d,String[] e,String[] f,String[] g) {

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
            gridView=inflator.inflate(R.layout.cust_candi, null);



        }
        else
        {
            gridView=(View)convertview;

        }





        TextView tv1=(TextView)gridView.findViewById(R.id.textView15);

        TextView tv2=(TextView)gridView.findViewById(R.id.textView17);





        ImageView im=(ImageView)gridView.findViewById(R.id.imageView3);










        //


        /////////////////////





        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);






        //tv6.setTextColor(Color.BLACK);
        //tv1.setText(c[position]);
        //tv2.setText(d[position]);

        tv1.setText(e[position]);
        tv2.setText(g[position]);



        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(Context);
        String ss=sh.getString("ip", "");
        String url = "http://" + ss + ":8080"+f[position];
        //Toast.makeText(Context, "tstid ass="+url, Toast.LENGTH_LONG).show();

        //Picasso.with(Context).load(url).into(im);
        Picasso.with(Context).load(url).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).transform(new CircleTransform()).into(im);


        Button b4=(Button) gridView.findViewById(R.id.button);

        b4.setTag(c[position]+"#"+d[position]);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(Context);



                final String cid =v.getTag().toString();
                String ar[];
                ar=cid.split("#");
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("nid",ar[0]);
                ed.putString("cid",ar[1]);
                ed.commit();
                //rnd............
//                Intent i=new Intent(Context,Staff_recent_work_update.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                Context.st
                SharedPreferences sh22= PreferenceManager.getDefaultSharedPreferences(Context);
                String ss=sh22.getString("ip", "");
                String url = "http://" + ss + ":8080/email_veri";
                //  Toast.makeText(getApplicationContext(),"tt="+url,Toast.LENGTH_LONG).show();

                RequestQueue requestQueue = Volley.newRequestQueue(Context);
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                // response
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                       String kk=jsonObj.getString("rnd");
                                        SharedPreferences sh22= PreferenceManager.getDefaultSharedPreferences(Context);
                                        SharedPreferences.Editor ed=sh22.edit();
                                        ed.putString("rnd",kk);

                                        ed.commit();

                                        Intent i=new Intent(Context,auth_fir.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        Context.startActivity(i);


                                    }



                                    // }
                                    else {
                                        Toast.makeText(Context, "Not found", Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(Context, "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(Context, "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(Context);
                        Map<String, String> params = new HashMap<String, String>();

//                String id=sh.getString("uid","");

                        params.put("em", sh.getString("em",""));


//                params.put("mac",maclis);

                        return params;
                    }
                };

                int MY_SOCKET_TIMEOUT_MS = 100000;

                postRequest.setRetryPolicy(new DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(postRequest);








            }
        });






        return gridView;
    }


}





