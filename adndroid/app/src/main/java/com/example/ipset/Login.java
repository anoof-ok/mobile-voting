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

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText email,password;
    Button login,signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=(EditText)findViewById(R.id.ed_num);
        password=(EditText)findViewById(R.id.ed_pwd);
        login=(Button)findViewById(R.id.bt_lg);
        signup=(Button)findViewById(R.id.bt_sig);
//        email.setText("bb");
//        password.setText("a");

        login.setOnClickListener(this);
        signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==login){
//            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
            int f=0;
            if(email.getText().toString().equalsIgnoreCase(""))
            {
                email.setError("Enter valid Email");
                f++;
            }
            if(password.getText().toString().equalsIgnoreCase(""))
            {
                password.setError("Enter Password");
                f++;
            }
            if(f==0)
            {
                final String eml=email.getText().toString();
                final String pass22=password.getText().toString();

                /////
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String hu = sh.getString("ip", "");
                String url = "http://" + hu + ":8080/and_login";



                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                // response
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                        String lid= jsonObj.getString("lid");
                                        String ty= jsonObj.getString("type");
                                        String pho=jsonObj.getString("pho");
                                        String con_id=jsonObj.getString("con_id");
                                        final SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        SharedPreferences.Editor editor=sh.edit();
                                        editor.putString("lid",lid);
                                        editor.putString("em",eml);
                                        editor.putString("pho",pho);
                                        editor.putString("con_id",con_id);


                                        editor.putString("uid",jsonObj.getString("uid"));

                                        editor.commit();
                                        //Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                                        if(ty.equalsIgnoreCase("user"))
                                        {
                                            Intent ij=new Intent(getApplicationContext(),Home.class);
                                            startActivity(ij);
                                            startService(new Intent(Login.this, LocationService.class));

                                        }
                                        if(ty.equalsIgnoreCase("candi"))
                                        {
                                            Intent ij=new Intent(getApplicationContext(),Home2.class);
                                            startActivity(ij);

                                        }
                                    }


                                    // }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                                    }

                                }    catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        Map<String, String> params = new HashMap<String, String>();


                        params.put("uname",eml);
                        params.put("pwd",pass22);

                        return params;
                    }
                };

                int MY_SOCKET_TIMEOUT_MS=100000;

                postRequest.setRetryPolicy(new DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(postRequest);

                ////
//            }
            }}
        if(v==signup){
            Intent ij=new Intent(getApplicationContext(),New_reg.class);
            startActivity(ij);
        }
    }

}