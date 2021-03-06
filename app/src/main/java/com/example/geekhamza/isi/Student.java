package com.example.geekhamza.isi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import api.task;

public class Student extends AppCompatActivity  {
    FloatingActionButton fab;
    ListView listView;
    String login;
    String psw;
    SwipeRefreshLayout swipeRefreshLayout;
    // Array of strings...
    String[] name_array ;
    task[] t;
    Menu menu;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
       fab = findViewById(R.id.student_fab);
       listView=findViewById(R.id.student_list_view);
       swipeRefreshLayout=findViewById(R.id.student_activity_swiperefresh);
       download_tasks();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),AllProcess.class);
                startActivity(intent);


            }
        });
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        download_tasks();
                    }
                });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.logout:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT)
                        .show();
                break;
            // action with ID action_settings was selected
            case R.id.help:
                Toast.makeText(this, "Help", Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }

        return true;
    }


    private void download_tasks() {
        SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);
         login = prefs.getString("login", "hamza");
         psw = prefs.getString("psw", "hamza");
        Toast.makeText(Student.this, login, Toast.LENGTH_SHORT).show();

        String url = "http://bps.isiforge.tn:8080/engine-rest/task?processDefinitionKey=dmd_dbl_corr";

        JsonArrayRequest JsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {


                    @Override
                    public void onResponse(JSONArray response) {
                        Toast.makeText(Student.this, response.toString(), Toast.LENGTH_SHORT).show();
                        if(response.toString().length()==0){
                            Toast.makeText(Student.this, "An empty response recived , please try again ", Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);

                        }
                        else{
                            Gson gson = new GsonBuilder().serializeNulls().create();

                            try{
                                t=gson.fromJson(response.toString(),task[].class);
                                 name_array = new String[t.length];
                                   for(int i=0;i<t.length;i++) {
                                       name_array[i]=t[i].getName();
                                       //Toast.makeText(Student.this, t[i].getId(), Toast.LENGTH_SHORT).show();
                                }
                                if(name_array.length>0) {
                                    ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                                            R.layout.single_item_list_view, name_array);
                                    listView.setAdapter(adapter);
                                }


                            }catch (Exception e){
                                Toast.makeText(Student.this, "error parsing "
                                        , Toast.LENGTH_SHORT).show();

                            }


                            swipeRefreshLayout.setRefreshing(false);



                        }

                    }
                }, new Response.ErrorListener() {


                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // TODO: Handle error
                        Toast.makeText(getApplicationContext(),"error ! "+error.getMessage(),Toast.LENGTH_LONG).show();
                        swipeRefreshLayout.setRefreshing(false);


                    }
                })
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

        };

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(JsonArrayRequest);


    }



    }

