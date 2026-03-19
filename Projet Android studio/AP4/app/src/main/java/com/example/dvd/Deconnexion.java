package com.example.dvd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Deconnexion extends AppCompatActivity {



    public void deconnecterUtilisateur() {
        // Accéder à SharedPreferences pour supprimer la session
        SharedPreferences prefs = getSharedPreferences("session", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("Email");
        editor.apply();


        String url = "http://yourserver.com/logout.php";


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");

                    if ("success".equals(status)) {

                        Toast.makeText(Deconnexion.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();


                        Intent loginIntent = new Intent(Deconnexion.this, Login.class);
                        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Effacer l'historique des activités
                        startActivity(loginIntent);
                    } else {
                        Toast.makeText(Deconnexion.this, "Erreur lors de la déconnexion serveur", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Deconnexion.this, "Erreur de réponse", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Deconnexion.this, "Erreur réseau", Toast.LENGTH_SHORT).show();
            }
        });


        Volley.newRequestQueue(this).add(stringRequest);
    }
}
