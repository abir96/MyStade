package com.example.mystade;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Debug;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class tranche extends AppCompatActivity {

    RadioButton r1, r2, r3 ;
      int p1, p2, p3;
    String[] prix;
    int idZone;
    static String tranche="";
    Button suiv;
    private RadioGroup radioTrancheGroup;
    private RadioButton radioTrancheButton;
    static int trancheID;
    //RequestQueue requestQueue;
    //String Url = "http://192.168.43.77/JsonDocs/prixTranche.php";
    private String TAG = tranche.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tranche);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Réservation");

        radioTrancheGroup= (RadioGroup) findViewById(R.id.trancheRadio);

        suiv = (Button) findViewById(R.id.suivantTrancheID);


        r1= (RadioButton) findViewById(R.id.tranche1ID);
        r2= (RadioButton) findViewById(R.id.tranche2ID);
        r3= (RadioButton) findViewById(R.id.tranche3ID);

        getZone("http://192.168.43.151:8080/android/getZones.php");
        getTranche("http://192.168.43.151:8080/android/getTranches.php");

        suiv.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {

                // get selected radio button from radioGroup
                int selectedId = radioTrancheGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioTrancheButton = (RadioButton) findViewById(selectedId);

                tranche = radioTrancheButton.getText().toString();
                trancheID= radioTrancheButton.getId();

                Intent intent = new Intent(tranche.this, place.class );
                startActivity(intent);


            }
        });



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
                Log.e(TAG, "designation zone : " + obj.getString("designation") +" Accuei.zone : "+ Accueil.zone);
                idZone= Integer.parseInt(obj.getString("id"));
                Log.e(TAG, "idZone : "+ Integer.parseInt(obj.getString("id")));
            }

        }

       /* r1.setText("1ère tranche ("+prix[0]+" Dhs)");
        r2.setText("2ème tranche ("+prix[1]+" Dhs)");
        r3.setText("1ème tranche ("+prix[2]+" Dhs)");  */

    }


    private void getTranche(final String urlWebService) {

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
                    loadIntoListTranche(s);
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


    private void loadIntoListTranche(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
         prix = new String[jsonArray.length()];
        int c=0;
        int[] id = new  int[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            //prix[i] = obj.getString("prix");
                int zoneId = Integer.parseInt(obj.getString("zoneId"));
            if(zoneId==idZone) {
                //prix[i] = obj.getString("prix");
                c++;

                if (c==1){p1=Integer.parseInt(obj.getString("prix"));
                    Log.e(TAG, "prix : "+ p1 );}
                if (c==2){p2=Integer.parseInt(obj.getString("prix"));
                    Log.e(TAG, "prix : "+ p2 );}
                if (c==3){p3=Integer.parseInt(obj.getString("prix"));
                    Log.e(TAG, "prix : "+ p3 );}

                id[i]= obj.getInt("id");
            }

        }

         r1.setText("1ère tranche ("+p1+" Dhs)");
        Log.e(TAG, "prixxxxx : "+p1);
        r1.setId(id[0]);
        r2.setText("2ème tranche ("+p2+" Dhs)");
        r2.setId(id[1]);
        r3.setText("3ème tranche ("+p3+" Dhs)");
       r3.setId(id[2]);

    }


}
