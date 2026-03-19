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
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ap4.Ap4Adapter;
import ap4.Ap4Model;

public class Humour extends AppCompatActivity {

    private ListView listHumour;
    private List<Ap4Model> listeHumour;
    private Ap4Adapter adapterHumour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humour);

        listHumour = findViewById(R.id.humour_listview);
        Button btnRetour = findViewById(R.id.btnRetourHumour);

        listeHumour = new ArrayList<>();
        adapterHumour = new Ap4Adapter(this, R.layout.ligne, listeHumour);
        listHumour.setAdapter(adapterHumour);

        chargerEvenementsHumour();

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Humour.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void chargerEvenementsHumour() {
        String url = URL.EVENEMENTS_BY_TYPE + "?type=humour";

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

                            listeHumour.add(ap4);
                        }

                        Collections.sort(listeHumour, (ap1, ap2) -> {
                            if (ap1.getDateConcert() == null || ap2.getDateConcert() == null) return 0;
                            return ap1.getDateConcert().compareTo(ap2.getDateConcert());
                        });

                        adapterHumour.notifyDataSetChanged();

                        listHumour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                                Ap4Model ap4 = listeHumour.get(position);

                                Intent intent = new Intent(Humour.this, Reservation.class);

                                intent.putExtra("nomArtiste", ap4.getNomArtiste());
                                intent.putExtra("tarif", ap4.getPrixPlace());
                                intent.putExtra("dateConcert", ap4.getDateConcert().getTime());
                                intent.putExtra("imgResID", ap4.getImgArtiste());

                                startActivity(intent);
                            }
                        });

                    } catch (Exception e) {
                        Log.e("Humour", "Erreur parsing JSON", e);
                    }
                },
                error -> {
                    Log.e("Humour", "Erreur réseau", error);
                    Toast.makeText(this, "Erreur réseau", Toast.LENGTH_SHORT).show();
                });

        queue.add(request);
    }
}
