package com.example.geekhamza.isi;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import api.Process;
import api.form;
import api.variable_struct;

public class AddDemande extends AppCompatActivity {
    EditText nom;
    EditText prenom;
    EditText commentaire;
    EditText matiere;
    EditText group;
    String niveau_val,process_key;
    Spinner niveau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_demande);
        nom=findViewById(R.id.add_demande_name);
        prenom=findViewById(R.id.add_demande_last_name);
        commentaire=findViewById(R.id.add_demande_comment);
        matiere=findViewById(R.id.add_demande_module);
        niveau=findViewById(R.id.add_demande_niveau);
        group=findViewById(R.id.add_demande_group);
        niveau_val="l1sil";
        process_key=getIntent().getExtras().getString("key");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.niveau_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        niveau.setAdapter(adapter);
        niveau.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                niveau_val=niveau.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                niveau_val=niveau.getItemAtPosition(0).toString();


            }


        });

    }

    public void SendForm(View view) {

        final String  nom_val,prenom_val,commentaire_val,matiere_val,group_val,login,psw;
        nom_val=nom.getText().toString();
        prenom_val=prenom.getText().toString();
        commentaire_val=commentaire.getText().toString();
        matiere_val=matiere.getText().toString();
        group_val=group.getText().toString();


        SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);
        login = prefs.getString("login", "hamza");
        psw = prefs.getString("psw", "hamza");
       // Toast.makeText(AddDemande.this, login, Toast.LENGTH_SHORT).show();

        final ProgressDialog progressDialog = new ProgressDialog(AddDemande.this,
                R.style.Theme_AppCompat);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        progressDialog.show();


        if(nom_val.length()==0||prenom_val.length()==0||commentaire_val.length()==0||matiere_val.length()==0||group_val.length()==0||niveau_val.length()==0)
        {

            Toast.makeText(this,"empty fields ! ", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();

         }


        // if everything is ok fill the form and go
        final  form f =new form();
        f.nom.value=nom_val;f.prenom.value=prenom_val;f.niveau.value=niveau_val;
        f.commentaire.value=commentaire_val;f.matierre.value=matiere_val;f.groupe.value=group_val;
        String url = "http://bps.isiforge.tn:8080/engine-rest/process-definition/key/"+process_key+"/submit-form";
        //String url = "http://34.242.37.99:8080";
        final String body=new Gson().toJson(f);
         //Log.e(" body   " ,body.toString());
        //Log.e(" url   " ,url.toString());
       final HashMap<String,form> mp=new HashMap<String,form>();
        mp.put("variables",f);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e(" res  " ,response.toString());
                        Toast.makeText(AddDemande.this,"Success ", Toast.LENGTH_LONG).show();
                        AddDemande.this.finish();



                    }
                }, new Response.ErrorListener() {


                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        // TODO: Handle error
                        Log.e(" res  " ,error.toString());

                        Toast.makeText(getApplicationContext(),"error ! "+error.getMessage(),Toast.LENGTH_LONG).show();


                    }
                }
            )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String credentials = login+":"+psw;
                String auth = "Basic "
                          + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", auth);
                return headers;

            }



            @Override
            public String getBodyContentType() {
                return "application/json";
            }

        /*    // this is the relevant method
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("variables", body.toString());
                return params;
            }
            */
        @Override
        public byte[] getBody() throws AuthFailureError {
            return new Gson().toJson(mp).getBytes();
        }





        };

//Log.d("boody",jsonObjectRequest.toString())
// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }

    }

