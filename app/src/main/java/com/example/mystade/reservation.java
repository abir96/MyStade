package com.example.mystade;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mystade.Model.Reservation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class reservation extends AppCompatActivity implements  View.OnClickListener{

    private String TAG = reservation.class.getSimpleName();
    private ListView lv;
    Button confirmer ;

    // ArrayList<HashMap<String, String>> matchsList;
    ArrayList<Reservation> reservationsList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        reservationsList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);
        confirmer = findViewById(R.id.confirmer);
        confirmer.setOnClickListener(this);

        new GetUsers().execute();

        ListAdapterReservation myAdapter = new ListAdapterReservation(reservation.this , reservationsList) ;
        lv.setAdapter(myAdapter);



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                TextView id = (TextView) lv.getChildAt(position).findViewById(R.id.id);
                TextView plceId = (TextView) lv.getChildAt(position).findViewById(R.id.placeId);

                String idd = id.getText().toString();
                String placeId = plceId.getText().toString().split(" ")[3];

                Activity activity = (Activity)reservation.this;
                FragmentActivity myContext=(FragmentActivity) activity;
                ListDialog oli= new ListDialog();
                oli.setContext(reservation.this);
                Bundle args=new Bundle();

                 args.putString("id" , idd);
                args.putString("placeId" , placeId);

                 oli.setArguments(args);
                 oli.show(myContext.getSupportFragmentManager(),"hey");








            }
        });


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Réservations");

    }

    private class GetUsers extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(reservation.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
String login ="";
            if(!MainActivity.lgn.equals("") || MainActivity.lgn != null)
                login =  MainActivity.lgn ;
            else
                login = inscription.login;

System.out.println();
            String url = "http://192.168.43.151:8080/android/reservationUser.php?login="+login;
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    //  JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    // JSONArray users = jsonObj.getJSONArray("users");
                    JSONArray reservations = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < reservations.length(); i++) {

                        JSONObject reservation = reservations.getJSONObject(i);

                        String date = reservation.getString("date");
                        String description = reservation.getString("description");

                       String placeId = reservation.getString("placeId") ;
                         String id = reservation.getString("id");

                        Reservation m = new Reservation(placeId,description,date , id) ;
                        // tmp hash map for single contact
                        HashMap<String, String> u = new HashMap<>();

                        // adding each child node to HashMap key => value
                        u.put("description", description);
                        u.put("date", date);

                        u.put("placeId" , placeId) ;
                        u.put("id" , id) ;


                        // adding contact to contact list
                        reservationsList.add(m);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
          /*  ListAdapter adapter = new SimpleAdapter(Home.this, matchsList,
                    R.layout.list_item, new String[]{ "description","date","id"},
                    new int[]{R.id.description, R.id.date,R.id.id} );
            lv.setAdapter(adapter);*/

            ListAdapterReservation myAdapter = new ListAdapterReservation(reservation.this , reservationsList) ;



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

    @Override
    public void onClick(View view) {
        if  (view.getId() == R.id.confirmer) {
            String login ="";
            if(!MainActivity.lgn.equals("") || MainActivity.lgn != null)
                login =  MainActivity.lgn ;
            else
                login = inscription.login;



            String HH = "http://192.168.43.151:8080/android/updateReserv.php?login="+login;



            confirm (HH) ;
            startActivity(new Intent(reservation.this, Home.class));
            Toast.makeText(reservation.this,"Vos reservations ont été confirmées!",Toast.LENGTH_LONG).show();



        }
    }

    void confirm (String HH) {


        StringRequest stringRequest=new StringRequest(Request.Method.GET, HH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

        };
        RequestQueue requestQueue= Volley.newRequestQueue(reservation.this);
        requestQueue.add(stringRequest);
    }



}