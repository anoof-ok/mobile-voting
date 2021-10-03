package com.example.ipset;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Ipset extends AppCompatActivity implements View.OnClickListener {
    EditText ip;
    Button save;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipset);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ip=(EditText)findViewById(R.id.editText1);
//        ip.setText("192.168.149.44");
        save=(Button)findViewById(R.id.button1);

        save.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String ip_addr=ip.getText().toString();
        String deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
//        Toast.makeText(getApplicationContext(), "success="+deviceId, Toast.LENGTH_LONG).show();

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor=sh.edit();
        editor.putString("ip",ip_addr);
        editor.putString("dev_id",deviceId);

        editor.commit();

        Intent ij=new Intent(getApplicationContext(),Login.class);
        startActivity(ij);

//

    }
}
