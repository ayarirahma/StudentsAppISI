package com.example.geekhamza.isi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText Username,Psw;
    TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Username=findViewById(R.id.login_username);
        Psw=findViewById(R.id.login_psw);


        // check if the user is already logged in via shared preferences

    }

    public void login(View view) {
        String username,psw;
        username=Username.getText().toString();
        psw=Psw.getText().toString();
        if(username.length()==0||psw.length()==0)
        {
            Toast.makeText(this,"Empty Field Not Allowed",Toast.LENGTH_LONG).show();
        }
        else
        {
            // this should be changed later to redirection student vs teacher
            // save to shared preferences
            Intent intent=new Intent(this,Student.class);
            startActivity(intent);

        }
    }
}
