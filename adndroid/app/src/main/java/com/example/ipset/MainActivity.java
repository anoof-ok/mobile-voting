package com.example.ipset;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText ip;
    Button save;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.activity_main);
        ip=(EditText)findViewById(R.id.editText1);
        ip.setText("192.168.233.44");
        save=(Button)findViewById(R.id.button1);

        save.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String ip_addr=ip.getText().toString();

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor=sh.edit();
        editor.putString("ip",ip_addr);
        editor.commit();

        Intent ij=new Intent(getApplicationContext(),Login.class);
        startActivity(ij);
    }
}
