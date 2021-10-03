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

public class auth_fir extends AppCompatActivity implements View.OnClickListener {
EditText ed1;
Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_fir);
        ed1=(EditText)findViewById(R.id.ed_num);
        b1=(Button)findViewById(R.id.bt_lg);
        b1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String a2=ed1.getText().toString();
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String a1 = sh.getString("rnd", "");
        if(a1.equalsIgnoreCase(a2))
        {
            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
            Intent ij=new Intent(getApplicationContext(),Reppolice_user.class);
            startActivity(ij);

        }
        else
        {
            Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();

        }
    }
}
