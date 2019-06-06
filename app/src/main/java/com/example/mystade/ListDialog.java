package com.example.mystade;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

public class ListDialog extends DialogFragment implements View.OnClickListener {

    Button delete ;
    Button cancel ;
String idP  , placeId;
Context context ;


    public void setContext(Context context) {
        this.context = context;
    }

    public ListDialog newInstance(Context context) {
        this.context=context;
        ListDialog f = new ListDialog();
        return f;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idP = getArguments().getString("id") ;
        placeId = getArguments().getString("placeId") ;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_info, container, false);


        delete=(Button) v.findViewById(R.id.delete);
        delete.setOnClickListener(this);
        cancel=(Button) v.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        return v;
    }



    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.delete) {






            String HH = "http://192.168.43.151:8080/android/deleteReserv.php?id="+idP+"&placeId="+placeId;



            del (HH) ;

          // new Delete().execute() ;
            startActivity(new Intent(getContext(), reservation.class));

        }
        else
            dismiss();



    }




    void del (String HH) {


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
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }






/*
    private class Delete extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response



            System.out.println("ggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg"+idP);

            String url = "http://192.168.1.108:8080/android/deleteReserv.php?id="+idP;

             sh.makeServiceCall(url);


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);




       }
    }
*/



}
