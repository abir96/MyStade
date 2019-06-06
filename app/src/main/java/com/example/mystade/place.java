package com.example.mystade;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class place extends AppCompatActivity {


    int matchID = Integer.parseInt(Home.matchId);
    int zoneID, trancheID;
    String zone, tranche;
    int cmp=0;
    int[] placeID;
    int[] matchsID;
    int nbPlaceDispo;
    int r ;

    RequestQueue requestQueue;
    String insertUrl = "http://192.168.43.151:8080/android/reservationUpdate.php";


    NumberPicker npicker;
    static int nbPlace;
    Button suiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        //action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Réservation");


        requestQueue = Volley.newRequestQueue(getApplicationContext());

        trancheID= com.example.mystade.tranche.trancheID;

        npicker=(NumberPicker) findViewById(R.id.quantity);
        npicker.setMaxValue(10);
        npicker.setMinValue(0);

        suiv= (Button) findViewById(R.id.suivantPlaceID);

        getZone("http://192.168.43.151:8080/android/getZones.php");
        getAllInfo("http://192.168.43.151:8080/android/getAllInfo.php");
        // getMatchs("http://192.168.43.77/JsonDocs/getMatchs.php");
        getReservation("http://192.168.43.151:8080/android/getReservation.php");



        suiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nbPlace = npicker.getValue();
                if(nbPlaceDispo<nbPlace){
                    Toast.makeText(place.this, "nombre de places disponibles est insuffisant pour votre demande", Toast.LENGTH_LONG).show();
                }
                else {

                    for (r=0; r < nbPlace; r++) {

                        StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                System.out.println(response.toString());
                            }
                        }, new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> parameters = new HashMap<String, String>();
                                parameters.put("placeID",  "3");
                                parameters.put("matchID", Integer.toString(matchID));
                                if(!MainActivity.lgn.equals("") || MainActivity.lgn != null)
                                    parameters.put("userLogin", MainActivity.lgn);
                                else
                                    parameters.put("userLogin", inscription.login);

                                return parameters;
                            }
                        };

                        requestQueue.add(request);


                    }

                    Toast.makeText(place.this, "Réservation effectuée avec succès", Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(place.this, Home.class);
                    startActivity(intent1);
                }
            }
        });


    }


    private void getZone(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListZones(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }


    private void loadIntoListZones(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        //String[] prix = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            //prix[i] = obj.getString("prix");

            if(obj.getString("designation").equals(Accueil.zone)) {
                zoneID= Integer.parseInt(obj.getString("id"));
            }

        }



    }


    private void getAllInfo(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListInfo(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }


    private void loadIntoListInfo(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        //String[] prix = new String[jsonArray.length()];
        placeID = new int[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            //prix[i] = obj.getString("prix");



            if(obj.getString("etat").equals('L') && Integer.parseInt(obj.getString("trancheId")) == trancheID && Integer.parseInt(obj.getString("zoneId")) == zoneID  ){

                cmp++;
                placeID[i]= Integer.parseInt(obj.getString("idPlace"));

            }

        }

    }




    private void getMatchs(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListMatchs(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }


    private void loadIntoListMatchs(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        //String[] prix = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            matchsID[i] = Integer.parseInt(obj.getString("id"));

        }
    }


    private void getReservation(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListReserv(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }


    private void loadIntoListReserv(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        nbPlaceDispo = placeID.length;
        //String[] prix = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            //prix[i] = obj.getString("prix");

            if(Integer.parseInt(obj.getString("matchId"))== matchID) {
                for(int j=0; j<nbPlaceDispo; j++){
                    if(Integer.parseInt(obj.getString("placeId"))== placeID[j]){
                        for(int k=j ;j<nbPlaceDispo-1; k++)
                            placeID[k]=placeID[k+1];
                        nbPlaceDispo--;
                        cmp--;
                    }
                }
            }

        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {

        case R.id.accueil:
            Intent intent = new Intent(this, Home.class );
            startActivity(intent);
            return(true);
        case R.id.mesReservations:
            Intent intent2 = new Intent(this, reservation.class );
            startActivity(intent2);
            return(true);
        case R.id.deconnexion:
            Intent intent1 = new Intent(this, MainActivity.class );
            startActivity(intent1);
            return(true);

    }
        return(super.onOptionsItemSelected(item));
    }


}
