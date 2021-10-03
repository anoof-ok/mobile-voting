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

public class custom_nomi_pending extends BaseAdapter {

    private android.content.Context Context;
    String[] c;
    String[] d,e,f;




    public custom_nomi_pending(android.content.Context applicationContext, String[] c, String[] d,String[] e,String[] f) {

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
            gridView=inflator.inflate(R.layout.cust_nomi_pending, null);



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


        //tv6.setTextColor(Color.BLACK);
        //tv1.setText(c[position]);
        //tv2.setText(d[position]);

        tv1.setText(d[position]);
        tv2.setText(e[position]);
        tv3.setText(f[position]);

        Button b3=(Button) gridView.findViewById(R.id.button3);

        b3.setTag(c[position]);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kk=v.getTag().toString();

                 SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(Context);

                SharedPreferences.Editor ed=sh.edit();
                ed.putString("rid",v.getTag().toString());
//                Intent i=new Intent(Context,Stf_view_review.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                Context.startActivity(i);

                final String uname =v.getTag().toString();


                String hu = sh.getString("ip", "");
                String url = "http://" + hu + ":8080/and_nomination_del";
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

                                        Toast.makeText(Context, "Deleted", Toast.LENGTH_LONG).show();
//                                        Intent i=new Intent(Context,Serv_home.class);
//                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        Context.startActivity(i);


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

                        params.put("nid", uname);


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



