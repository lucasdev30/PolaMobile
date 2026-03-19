package com.example.dvd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
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

public class Rap extends AppCompatActivity {

    private List<Ap4Model> listeRap;
    private Ap4Adapter adapterRap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rap);

        final Button btnRetour = findViewById(R.id.btnRetourRap);
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Rap.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Récupérer l'identifiant de la catégorie (idCat) passé par l'intent
        int idCat = this.getIntent().getIntExtra("idCat", 0);

        // Initialiser la liste des concerts rap et l'adaptateur
        listeRap = new ArrayList<>();
        adapterRap = new Ap4Adapter(this, R.layout.ligne, listeRap);
        ListView listRap = findViewById(R.id.rap_listview);
        listRap.setAdapter(adapterRap);

        listRap.setOnItemClickListener((parent, view, position, id) -> {
            Ap4Model artiste = listeRap.get(position);

            Intent intent = new Intent(Rap.this, Reservation.class);
            intent.putExtra("nomArtiste", artiste.getNomArtiste());
            intent.putExtra("tarif", artiste.getPrixPlace());
            intent.putExtra("dateConcert", artiste.getDateConcert().getTime()); // en millis
            intent.putExtra("imgResID", artiste.getImgArtiste());

            startActivity(intent);
        });

        chargerXmlRap(idCat);
    }

    private void chargerXmlRap(int idCat) {

        String url = URL.EVENEMENTS_BY_TYPE + "?type=rap";



        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        // Récupérer les événements dans le tableau "evenements"
                        JSONArray evenements = response.getJSONArray("evenements");

                        for (int i = 0; i < evenements.length(); i++) {
                            JSONObject item = evenements.getJSONObject(i);

                            Ap4Model ap4 = new Ap4Model();

                            // Récupérer les détails de chaque événement
                            ap4.setNomArtiste(item.getString("nomArtiste"));
                            ap4.setPrixPlace(item.getDouble("tarif"));

                            // Convertir la date au format "yyyy-MM-dd"
                            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
                            Date concertDate = inputDateFormat.parse(item.getString("DateEvenement"));
                            ap4.setDateConcert(concertDate);

                            String imgName = item.getString("imgArtiste");
                            int resID = getResources().getIdentifier(imgName, "drawable", getPackageName());
                            ap4.setImgArtiste(resID);

                            // Ajouter l'objet à la liste
                            listeRap.add(ap4);
                        }

                        // Trier la liste par date croissante des événements
                        Collections.sort(listeRap, new Comparator<Ap4Model>() {
                            @Override
                            public int compare(Ap4Model comparaison1, Ap4Model comparaison2) {
                                return comparaison1.getDateConcert().compareTo(comparaison2.getDateConcert());
                            }
                        });

                        // Notifier l'adaptateur que les données ont été modifiées
                        adapterRap.notifyDataSetChanged();

                    } catch (Exception e) {
                        // En cas d'erreur lors du parsing JSON
                        Log.e("API", "Erreur parsing JSON", e);
                    }
                },
                error -> {
                    // En cas d'erreur réseau
                    Log.e("API", "Erreur lors de la requête API", error);
                    Toast.makeText(this, "Erreur réseau", Toast.LENGTH_SHORT).show();
                });

        // Ajouter la requête à la file d'attente de Volley
        queue.add(request);
    }
}
