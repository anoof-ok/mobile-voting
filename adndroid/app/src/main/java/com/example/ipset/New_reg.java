package com.example.ipset;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class New_reg extends AppCompatActivity implements View.OnClickListener {
EditText voter,name,dob,phone,email,hn,hs,plc,post,pin,dist,state,pwd;
RadioButton r1,r2;
Spinner sp1;
ImageView im;
Button b1;

    String path, atype, fname, attach, attatch1;
    byte[] byteArray = null;
    String[]constituent_id,constituency_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reg);
        voter=(EditText)findViewById(R.id.ed_voter);
        name=(EditText)findViewById(R.id.ed_name);
        dob=(EditText)findViewById(R.id.ed_dob);
        phone=(EditText)findViewById(R.id.ed_phone);
        email=(EditText)findViewById(R.id.ed_email);
        hs=(EditText)findViewById(R.id.ed_hs);
        hn=(EditText)findViewById(R.id.ed_hn);
        plc=(EditText)findViewById(R.id.ed_place);
        post=(EditText)findViewById(R.id.ed_post);
        pin=(EditText)findViewById(R.id.ed_pin);
        dist=(EditText)findViewById(R.id.ed_district);
        state=(EditText)findViewById(R.id.ed_state);
        pwd=(EditText)findViewById(R.id.ed_password);

        r1=(RadioButton)findViewById(R.id.male);
        r2=(RadioButton)findViewById(R.id.female);
        im=(ImageView)findViewById(R.id.imageView8);
        sp1=(Spinner)findViewById(R.id.spinner2);
        b1=(Button)findViewById(R.id.button53);
        b1.setOnClickListener(this);
        im.setOnClickListener(this);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":8080/and_view_consi";


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        // response
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                //constituent_id,constituency_name
                                JSONArray js = jsonObj.getJSONArray("data");
                                constituent_id = new String[js.length()];
                                constituency_name = new String[js.length()];


//
//                                JSONArray js1= jsonObj.getJSONArray("rating");
//                                rating=new String[js1.length()];

                                for (int i = 0; i < js.length(); i++) {
                                    JSONObject u = js.getJSONObject(i);
                                    constituent_id[i] = u.getString("constituent_id");
                                    constituency_name[i] = u.getString("constituency_name");






                                }
//

                                ArrayAdapter<String> ad=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,constituency_name);
                                sp1.setAdapter(ad);

                            }



                            else {
                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
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
                //String id=sh.getString("lid","");
                //   params.put("lid",id);
                //params.put("lid",id);

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
    void showfilechooser(int string) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //getting all types of files

        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), string);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getApplicationContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();

        }
    }


    private boolean validateEmailAddress(String emailAddress) {
        String expression = "^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = emailAddress;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                ////
                Uri uri = data.getData();

                try {
                    path = FileUtils.getPath(this, uri);

                    File fil = new File(path);
                    float fln = (float) (fil.length() / 1024);
                    atype = path.substring(path.lastIndexOf(".") + 1);


                    fname = path.substring(path.lastIndexOf("/") + 1);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                try {

                    File imgFile = new File(path);

                    if (imgFile.exists()) {

                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        im.setImageBitmap(myBitmap);

                    }


                    File file = new File(path);
                    byte[] b = new byte[8192];
                    Log.d("bytes read", "bytes read");

                    InputStream inputStream = new FileInputStream(file);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();

                    int bytesRead = 0;

                    while ((bytesRead = inputStream.read(b)) != -1) {
                        bos.write(b, 0, bytesRead);
                    }
                    byteArray = bos.toByteArray();

                    String str = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                    attach = str;


                } catch (Exception e) {
                    Toast.makeText(this, "String :" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }


            }
        }
    }

    String gen = "", donr = "";
    Boolean tt;
    Integer name_len = 0, pwd_len = 0, cpwd_len = 0, phh;


    @Override
    public void onClick(View view) {
        if (view == im) {
            showfilechooser(1);
        }
        if (view == b1) {

            int f = 0;

            if (name.getText().toString().equalsIgnoreCase("")) {
                name.setError("");
                f++;

            }
            if (dob.getText().toString().equalsIgnoreCase("")) {
                dob.setError("");
                f++;

            }
            if (plc.getText().toString().equalsIgnoreCase("")) {
                plc.setError("");
                f++;

            }
            if (pin.getText().toString().equalsIgnoreCase("")) {
                pin.setError("");
                f++;

            }

            if (post.getText().toString().equalsIgnoreCase("")) {
                post.setError("");
                f++;

            }

            if (phone.getText().toString().equalsIgnoreCase("")) {
                phone.setError("");
                f++;

            }
            if (hn.getText().toString().equalsIgnoreCase("")) {
                hn.setError("");
                f++;

            }
            if (hs.getText().toString().equalsIgnoreCase("")) {
                hs.setError("");
                f++;

            }
            if (voter.getText().toString().equalsIgnoreCase("")) {
                voter.setError("");
                f++;

            }
            if (dist.getText().toString().equalsIgnoreCase("")) {
                dist.setError("");
                f++;

            }
            if (state.getText().toString().equalsIgnoreCase("")) {
                state.setError("");
                f++;

            }

            if (email.getText().toString().equalsIgnoreCase("")) {
                email.setError("");
                f++;

            }
            if (pwd.getText().toString().equalsIgnoreCase("")) {
                pwd.setError("");
                f++;

            }
                        ///datecheck
//            Date strDate = null;
//
//            try {
//                String valid_until = ed_dob.getText().toString();
//                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//
//                strDate = sdf.parse(valid_until);
//            } catch (ParseException e) {
//
//            }
//            if (new Date().before(strDate)) {
//                //catalog_outdated = 1;
//                Toast.makeText(getApplicationContext(),"date check",Toast.LENGTH_LONG).show();
//                f++;
//            }
//
//
//            ///over
//
            name_len = name.getText().toString().length();
            pwd_len = pwd.getText().toString().length();

            phh = phone.getText().toString().length();
            if (phh != 10) {
                Toast.makeText(getApplicationContext(), "please enter valid phone number", Toast.LENGTH_LONG).show();
                f++;
            }


            if (name_len < 2) {
                Toast.makeText(getApplicationContext(), "name must be Min 2 character", Toast.LENGTH_LONG).show();
                f++;
            }

//            if(cpwd_len<4)
//            {
//                Toast.makeText(getApplicationContext(),"password Min 4 chartctor",Toast.LENGTH_LONG).show();
//                f++;
//            }
            if (pwd_len < 4) {
                Toast.makeText(getApplicationContext(), "password Min 4 chartcter", Toast.LENGTH_LONG).show();
                f++;
            }

//
            ///pin lenth chrk
            try {
                int val = Integer.parseInt(pin.getText().toString());


                if (val < 111111 || val > 999999) {
                    Toast.makeText(getApplicationContext(), "check pin code length", Toast.LENGTH_LONG).show();
                    f++;
                }


            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "check pin", Toast.LENGTH_LONG).show();
            }
            //ovr


            tt = validateEmailAddress(email.getText().toString());
            if (tt.equals(Boolean.FALSE)) {
                Toast.makeText(getApplicationContext(), "CHECK MAIL ADDRESS", Toast.LENGTH_LONG).show();
                f++;
            }

            if (attach.equalsIgnoreCase("")) {
                Toast.makeText(getApplicationContext(), "please choose image", Toast.LENGTH_LONG).show();
                f++;
            }


//


            if (f == 0) {


                final String name1 = name.getText().toString();
                final String dob1 = dob.getText().toString();
                final String place1 = plc.getText().toString();
                final String pin1 = pin.getText().toString();
                final String post1 = post.getText().toString();
                final String district1 = dist.getText().toString();
                final String phone1 = phone.getText().toString();
                final String hn1 = hn.getText().toString();
                final String hs1 = hs.getText().toString();
                final String voter1 = voter.getText().toString();
                final String state1 = state.getText().toString();


                final String email1 = email.getText().toString();
                final String password1 = pwd.getText().toString();

                if (r1.isChecked() == true) {
                    gen = "male";
                } else if (r2.isChecked() == true) {
                    gen = "female";
                }



                    SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                    final String hu = sh.getString("ip", "");
                    String url = "http://" + hu + ":8080/usr_reg";


                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                    // response
                                    try {
                                        JSONObject jsonObj = new JSONObject(response);
                                        if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
//
                                            Intent j = new Intent(getApplicationContext(), Login.class);
                                            startActivity(j);

                                        }


                                        // }
                                        else {
                                            Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                                        }

                                    } catch (Exception e) {
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

//                String id=sh.getString("uid","");
                            params.put("name", name1);
                            params.put("gen", gen);
                            params.put("dob", dob1);
                            params.put("phone", phone1);
                            params.put("email", email1);


                            params.put("hs", hs1);
                            params.put("hn", hn1);

                            params.put("place", place1);


                            params.put("post",post1);

                            params.put("pin", pin1);
                            params.put("dist", district1);
                            params.put("state", state1);
                            params.put("voter_id",voter1);



                            params.put("pho", attach);
                            params.put("con_id",constituent_id[sp1.getSelectedItemPosition()]);

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


                } else {

                    Toast.makeText(this, "Password doesn't match ,Try again!!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
