package com.example.geekhamza.isi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;

import static android.provider.Telephony.Carriers.PASSWORD;

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

        final String username,psw;

        username=Username.getText().toString();
        psw=Psw.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(Login.this,
                R.style.Theme_AppCompat);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        if(username.length()==0||psw.length()==0)
        {
            final Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.login_layout), "Empty Field not allowed !", Snackbar.LENGTH_LONG);
            snackbar.setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    });
            progressDialog.dismiss();

            snackbar.show();
        }
        else
        {
            // this should be changed later to redirection student vs teacher
            // save to shared preferences
            String url = "http://bps.isiforge.tn:8080/engine-rest/user";

            JsonArrayRequest JsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {


                        @Override
                        public void onResponse(JSONArray response) {
                            progressDialog.dismiss();
                            if(response.length()==0){
                                Toast.makeText(Login.this, "An empty response recived , please try again ", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Gson gson = new Gson();
                                try {
                                    user s=gson.fromJson(response.get(0).toString(),user.class);
                                    SharedPreferences sharedPref = getSharedPreferences("pref", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("id", s.getId());
                                    editor.putString("firstName", s.getFirstName());
                                    editor.putString("lastName", s.getLastName());
                                    editor.putString("email", s.getEmail());
                                    editor.putString("login", Username.getText().toString());
                                    editor.putString("psw", Psw.getText().toString());
                                    editor.commit();

                                    Intent intent=new Intent(getApplicationContext(),Student.class);
                                    startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(Login.this, "Json format Unexpacted please try again ", Toast.LENGTH_SHORT).show();
                                }

                            }

                        }
                    }, new Response.ErrorListener() {


                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();

                            // TODO: Handle error
                            Toast.makeText(getApplicationContext(),"hh"+error.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    String credentials = username+":"+psw;
                    String auth = "Basic "
                            + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", auth);
                    return headers;

                }

                };

// Access the RequestQueue through your singleton class.
                MySingleton.getInstance(this).addToRequestQueue(JsonArrayRequest);


        }
    }
}
