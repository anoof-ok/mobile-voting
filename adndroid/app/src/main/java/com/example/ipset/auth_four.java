package com.example.ipset;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class auth_four extends AppCompatActivity implements View.OnClickListener {

    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_four);
        b1=(Button)findViewById(R.id.bt_lg);
        b1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        SharedPreferences sh22= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ip=sh22.getString("ip", "");
        final String sid1=sh22.getString("lid", "");


        String url = "http://" + ip + ":8080/wallet_new22";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        //   Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();

                        // response
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String sucs=   jsonObj.getString("status");
                            if(sucs.equalsIgnoreCase("ok"))
                            {
                                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                                Intent ins = new Intent(getApplicationContext(), Home.class);
                                startActivity(ins);







                            }



                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),"Error"+e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(),"eeeee"+error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {


                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String>  params = new HashMap<String, String>();

                params.put("lid",sh.getString("lid",""));
                params.put("nid",sh.getString("nid",""));

                params.put("lati",LocationService.lati);
                params.put("longi",LocationService.logi);

                return params;
            }
        };

        requestQueue.add(postRequest.setRetryPolicy(new DefaultRetryPolicy(60000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));


    }
}
