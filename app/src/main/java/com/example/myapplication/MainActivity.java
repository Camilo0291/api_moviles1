package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ArrayList<String> musicaList;
    private  static final String TAG = "mainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.recyclerView);
        musicaList = new  ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, musicaList);
        listView.setAdapter(adapter);


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://laspinasmoviles2.000webhostapp.com/api.php";


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,

                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject cancion = response.getJSONObject(i);
                            String titulo = cancion.getString("titulo");
                            String artista = cancion.getString("artista");
                            musicaList.add(titulo + " - " + artista);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        Log.e(TAG, "Error parsing JSON response", e);
                    }
                },
                error -> Log.e(TAG, "Error in Volley request", error));

        queue.add(jsonArrayRequest);


    };
    }
