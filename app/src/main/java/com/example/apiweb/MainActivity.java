package com.example.apiweb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter arrayAdapter;
    ArrayList<String> datos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.ListViewdatos);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, datos);
        listView.setAdapter(arrayAdapter);
        obtenerDatos();

    }

    //Peticion al Web cervice de tipo get
    private void obtenerDatos() {
        String url="https://parallelum.com.br/fipe/api/v1/caminhoes/marcas";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override

            public void onResponse(JSONArray response) {
                //Recibir informacion
                //pasar Json  bicicletas
                pasarJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Manejar error
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(this).add(jsonArrayRequest);

    }

    private void pasarJson(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            Modelos b1 = new Modelos();
            JSONObject json ;

            try {
                json = array.getJSONObject(i);
                b1.setNome(json.getString("nome"));
                b1.setCodigo(json.getInt("codigo"));

                //Clase Instaciada y seteada con datos de web service la anterior
                //Agrega los datos al Array List enlazado con la lista
                datos.add(b1.getNome());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        arrayAdapter.notifyDataSetChanged();
    }

}