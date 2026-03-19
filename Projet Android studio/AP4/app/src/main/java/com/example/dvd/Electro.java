package com.example.dvd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ap4.Ap4Adapter;
import ap4.Ap4Model;

public class Electro extends AppCompatActivity {

    private ListView list_electro;
    private List<Ap4Model> listeElectro;
    private Ap4Adapter adapterElectro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electro);

        list_electro = findViewById(R.id.electro_listview);
        Button btnRetour = findViewById(R.id.btnRetourElectro);

        listeElectro = new ArrayList<>();
        adapterElectro = new Ap4Adapter(this, R.layout.ligne, listeElectro);
        list_electro.setAdapter(adapterElectro);

        chargerEvenementsElectro();

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Electro.this, MainActivity.class);
                startActivity(intent);
            }
        });

        list_electro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Ap4Model ap4 = listeElectro.get(position);


                Intent intent = new Intent(Electro.this, Reservation.class);

                intent.putExtra("nomArtiste", ap4.getNomArtiste());
                intent.putExtra("tarif", ap4.getPrixPlace());
                intent.putExtra("dateConcert", ap4.getDateConcert().getTime()); // Date en millisecondes
                intent.putExtra("imgResID", ap4.getImgArtiste());

                // Démarrer l'activité Reservation
                startActivity(intent);
            }
        });

        Log.d("Electro", "onCreate appelé, activité Electro lancée");
    }

    // Charger les événements Electro à partir de l'API
    private void chargerEvenementsElectro() {
        String url = URL.EVENEMENTS_BY_TYPE + "?type=electro";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray evenements = response.getJSONArray("evenements");

                        for (int i = 0; i < evenements.length(); i++) {
                            JSONObject item = evenements.getJSONObject(i);

                            Ap4Model ap4 = new Ap4Model();

                            ap4.setNomArtiste(item.getString("nomArtiste"));
                            ap4.setPrixPlace(item.getDouble("tarif"));

                            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
                            Date concertDate = inputDateFormat.parse(item.getString("DateEvenement"));
                            ap4.setDateConcert(concertDate);

                            String imgName = item.getString("imgArtiste");
                            int resID = getResources().getIdentifier(imgName, "drawable", getPackageName());
                            ap4.setImgArtiste(resID);

                            listeElectro.add(ap4);
                        }

                        Collections.sort(listeElectro, new Comparator<Ap4Model>() {
                            @Override
                            public int compare(Ap4Model compar1, Ap4Model compar2) {
                                return compar1.getDateConcert().compareTo(compar2.getDateConcert());
                            }
                        });

                        adapterElectro.notifyDataSetChanged();
                        Log.d("Electro", "Chargement API terminé, nombre d'éléments : " + listeElectro.size());
                    } catch (Exception e) {
                        Log.e("Electro", "Erreur parsing JSON", e);
                    }
                },
                error -> {
                    Log.e("Electro", "Erreur lors de la requête API", error);
                    Toast.makeText(this, "Erreur réseau", Toast.LENGTH_SHORT).show();
                });

        queue.add(request);
    }
}
