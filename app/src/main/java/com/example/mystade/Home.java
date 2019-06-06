package com.example.mystade;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystade.Model.Match;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class Home extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;
    static String matchId ;

   // ArrayList<HashMap<String, String>> matchsList;
    ArrayList<Match> matchsList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        matchsList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);

        new GetUsers().execute();

        MatchAdapter myAdapter = new MatchAdapter(Home.this , matchsList) ;
         lv.setAdapter(myAdapter);



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                TextView id = (TextView) lv.getChildAt(position).findViewById(R.id.id);
                matchId = id.getText().toString();
                Intent intent1 = new Intent(Home.this, Accueil.class );
                intent1.putExtra("IDMatch" , matchId) ;
                startActivity(intent1);

            }
        });


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Home");

     }

    private class GetUsers extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(Home.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "http://192.168.43.151:8080/android/matchs.php";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    //  JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    // JSONArray users = jsonObj.getJSONArray("users");
                    JSONArray matchs = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < matchs.length(); i++) {

                        JSONObject match = matchs.getJSONObject(i);

                        String description = match.getString("description");
                        String date = match.getString("date");
                        String id = match.getString("id") ;

                        Match m = new Match(description,date,id) ;
                        // tmp hash map for single contact
                        HashMap<String, String> u = new HashMap<>();

                        // adding each child node to HashMap key => value
                        u.put("description", description);
                        u.put("date", date);
                        u.put("id" , id) ;


                        // adding contact to contact list
                        matchsList.add(m);
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

            MatchAdapter myAdapter = new MatchAdapter(Home.this , matchsList) ;
            lv.setAdapter(myAdapter);


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