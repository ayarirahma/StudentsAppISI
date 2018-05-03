package com.example.geekhamza.isi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
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

import api.Process;

public class AllProcess extends AppCompatActivity {
    ListView listView;
    String[] name_array ;
    SwipeRefreshLayout swipeRefreshLayout;
    String login;
    String psw;
    Process[]t;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_process);
        listView=findViewById(R.id.allprocess_list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),AddDemande.class);
                intent.putExtra("key",t[i].getKey());
                startActivity(intent);
                AllProcess.this.finish();
            }
        });
        swipeRefreshLayout=findViewById(R.id.allprocess_activity_swiperefresh);
        download_tasks();
    }

    private void download_tasks() {
        SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);
        login = prefs.getString("login", "hamza");
        psw = prefs.getString("psw", "hamza");
        Toast.makeText(AllProcess.this, login, Toast.LENGTH_SHORT).show();

        String url = "http://bps.isiforge.tn:8080/engine-rest/process-definition?latest=true";

        JsonArrayRequest JsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Toast.makeText(AllProcess.this, response.toString(), Toast.LENGTH_SHORT).show();
                        if(response.toString().length()==0){
                            Toast.makeText(AllProcess.this, "An empty response recived , please try again ", Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);

                        }
                        else{
                            Gson gson = new GsonBuilder().serializeNulls().create();

                            try{
                                t=gson.fromJson(response.toString(),Process[].class);
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
                                Toast.makeText(AllProcess.this, "error parsing "
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
