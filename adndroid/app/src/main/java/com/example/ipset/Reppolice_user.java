package com.example.ipset;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Reppolice_user extends Activity implements OnItemSelectedListener{
	


	public String encodedImage;
	ImageView ivup;
	EditText etup1;
	Button btup1,btup2;
	Spinner sp;
	String[] tp={"Accident","Jam","Block","Other"};
	String id="",ttp="";
	String url="";
	private static final int CAMERA_PIC_REQUEST = 0;
	
	
	TextView tv3;
	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reppolice_user);
		
		
		try{
        	if(Build.VERSION.SDK_INT>9){
        		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        		StrictMode.setThreadPolicy(policy);
        	}
        	
        }catch(Exception e){
        	System.out.print(e);
        }
		
		tv3=(TextView)findViewById(R.id.textView3);
		tv3.setVisibility(View.GONE);
		ivup=(ImageView)findViewById(R.id.art_img);
		

		btup1=(Button)findViewById(R.id.button8);

		
	ivup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				open();
			}
		});
		btup1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SharedPreferences sh3= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				String aa=sh3.getString("pat_id","");
				SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				String IP = sh.getString("ip", "");
				String url = "http://" + IP + ":5000/care_emotion_add";

				RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

				StringRequest postRequest = new StringRequest(Request.Method.POST, url,
						new Response.Listener<String>() {
							@Override
							public void onResponse(String response) {

//								    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();

								// response
								try {
									JSONObject jsonObj = new JSONObject(response);
									String sucs = jsonObj.getString("status");
									if(sucs.equalsIgnoreCase("ok")) {
										tv3.setText("Status: finished");
										tv3.setVisibility(View.VISIBLE);
										Intent ij=new Intent(getApplicationContext(),AndroidBarcodeQrExample.class);
										startActivity(ij);

									}
									else
									{
										tv3.setText("Status: Not matched");
										tv3.setVisibility(View.VISIBLE);
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

						params.put("photo22", sh.getString("pho", ""));
						params.put("pho", encodedImage);
						params.put("lid", sh.getString("lid",""));

						return params;
					}
				};


				requestQueue.add(postRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));




			}
		});

		}
	public void open(){ 
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 0);
	}

	public Uri getImageUri(Context inContext, Bitmap inImage) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		inImage.compress(CompressFormat.JPEG, 100, bytes);
		String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
		return Uri.parse(path);
	}

	public String getRealPathFromURI(Uri uri) {
		String path = "";
		if (getContentResolver() != null) {
			Cursor cursor = getContentResolver().query(uri, null, null, null, null);
			if (cursor != null) {
				cursor.moveToFirst();
				int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
				path = cursor.getString(idx);
				cursor.close();
			}
		}
		return path;
	}
	
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
		super.onActivityResult(requestCode, resultCode, data); 
		//Bitmap bp = (Bitmap) data.getExtras().get("data"); 
		//imgFavorite.setImageBitmap(bp); 
		byte[] bitmapdata;
		
		 
		if (resultCode == RESULT_OK) 
		
			{
	        		
			if(data==null)
			{
				Toast.makeText(getApplicationContext(), "null data ", Toast.LENGTH_SHORT).show();
			}
			
			if (requestCode == CAMERA_PIC_REQUEST)
			{
				if (resultCode == RESULT_OK) 
				{	 		 
		      		try
		      		{

						Bitmap image = (Bitmap) data.getExtras().get("data");

						// CALL THIS METHOD TO GET THE ACTUAL PATH
//						File finalFile = new File(getRealPathFromURI(tempUri));


		      			ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		            	image.compress(CompressFormat.JPEG, 100, baos); //bm is the bitmap object

		            	byte[] b = baos.toByteArray(); 
		            	encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
		            	//Toast.makeText(this,encodedImage, Toast.LENGTH_LONG).show();
			            
		            }
		            catch (Exception e)
		            {
		                Toast.makeText(this,"String :"+e.getMessage().toString(), Toast.LENGTH_LONG).show();
		            }
		            
		            byte[] bt= Base64.decode(encodedImage, Base64.DEFAULT);
		            
		            //byte bt[]=re.getBytes();
		           // Log.d("imag.....", bt.toString());
		            Bitmap bmp=BitmapFactory.decodeByteArray(bt, 0, bt.length);
		            if(bmp!=null)
		            {
		            ivup.setImageBitmap(bmp);
		            }	
		            
		
			}
		
	} 

	

}
	}
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		//ttp=tp[arg2];
		
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
