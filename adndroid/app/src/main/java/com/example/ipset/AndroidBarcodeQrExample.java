package com.example.ipset;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.GridView;
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
import java.util.Locale;
import java.util.Map;

public class AndroidBarcodeQrExample extends Activity {
	/** Called when the activity is first created. */

	static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
	TextToSpeech ttobj;
	GridView gv;
	Handler hnd;
	TextView tv;

	TextView tvkey;

	String[]login_id,name,photo;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	
		tv= (TextView) findViewById(R.id.textView1);
		tv.setText("");
		ttobj=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
			
			@Override
			public void onInit(int status) {
				// TODO Auto-generated method stub
				if(status!=TextToSpeech.ERROR) {
					ttobj.setLanguage(Locale.UK);
					//ttobj.setPitch(2);
				}
			}
		});	
			
	}

	public void scanBar(View v) {
		try {
			Intent intent = new Intent(ACTION_SCAN);
			intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
			startActivityForResult(intent, 0);
		} catch (ActivityNotFoundException anfe) {
			showDialog(AndroidBarcodeQrExample.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
		}
	}

	public void scanQR(View v) {
	try {
			
			Intent intent = new Intent(ACTION_SCAN);
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, 0);
			
		} catch (ActivityNotFoundException anfe) {
			showDialog(AndroidBarcodeQrExample.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
		}
	}
	private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
		AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
		downloadDialog.setTitle(title);
		downloadDialog.setMessage(message);
		downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int i) {
				Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				try {
					act.startActivity(intent);
				} catch (ActivityNotFoundException anfe) {

				}
			}
		});
		downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int i) {
			}
		});
		return downloadDialog.show();
	}
    Runnable rn= new Runnable() {
	@Override
	public void run() {
	}
    };
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				final String contents = intent.getStringExtra("SCAN_RESULT");
//				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
//				
//				tv.setText(contents);
//				
//				ttobj.speak(contents, TextToSpeech.QUEUE_FLUSH, null);
				
    	        
				SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				Editor ed=sh.edit();
				ed.putString("oid",contents);
				ed.commit();



				
				//Intent i=new Intent(getApplicationContext(), Viw_candi.class);
				//startActivity(i);


				String ip=sh.getString("ip", "");
				String url = "http://" + ip + ":8080/view_qr";


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

										//Toast.makeText(getApplicationContext(), "success qr", Toast.LENGTH_LONG).show();

//										Intent ij=new Intent(getApplicationContext(),Reppolice_user.class);
//										startActivity(ij);
										Intent ij=new Intent(getApplicationContext(),Auth_three.class);
										startActivity(ij);



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



						params.put("oid", sh.getString("oid",""));

						params.put("lid", sh.getString("lid",""));

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


		}
		}
	}
