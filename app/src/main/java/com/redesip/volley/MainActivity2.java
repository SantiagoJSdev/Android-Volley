package com.redesip.volley;

import static com.redesip.volley.R.id.textNew;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity2 extends AppCompatActivity {

    TextView textview;
    RequestQueue requestQueue;
    ImageView imageView;
    private static final String url1 = "https://my-json-server.typicode.com/typicode/demo/comments";
    private static final String url2 = "https://my-json-server.typicode.com/typicode/demo/posts";
    private static final String url3 = "https://my-json-server.typicode.com/typicode/demo/db";
    private static final String url4 = "https://lumiere-a.akamaihd.net/v1/images/de_mickeymouseclubhouse-mitmachmorgen_hero_r_82c8aca0.jpeg?region=293,1,1483,834";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        requestQueue = Volley.newRequestQueue(this);
        init2();
         stringRequest();

       // jsonRequest();

       // jsonObjectRequest();

        // imageRequest();
    }

    private void init2() {
        textview = findViewById(R.id.textNew);
        imageView = findViewById(R.id.imageView);
    }

    private void stringRequest(){

        StringRequest resquest = new StringRequest(
                Request.Method.GET,
                url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    textview.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof ServerError) {
                            Log.i("TAG", "Server Error");
                        }
                        if (error instanceof NoConnectionError) {
                            Log.i("TAG", "No Connection Internet Error");
                        }

                        if (error instanceof NetworkError) {
                            Log.i("TAG", "No Connection Internet Error");
                        }
                    }
                }

        );
        requestQueue.add(resquest);
    }

    private void jsonRequest(){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url2,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int size = response.length();
                        for (int i = 0; i < size; i++) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.get(i).toString());
                                String title = jsonObject.getString("title");
                                textview.append("title: " + title + "\n");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        );
        requestQueue.add(jsonArrayRequest);
    }

    private void jsonObjectRequest(){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url3,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("comments");
                            int size = jsonArray.length();
                            for (int i = 0; i < size; i++) {
                                JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                                String title = jsonObject.getString("body");
                                textview.append("title: " + title + "\n");
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        );
        requestQueue.add(jsonObjectRequest);
    }

    private void imageRequest() {
        ImageRequest imageRequest = new ImageRequest(
                url4,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                    imageView.setImageBitmap(response);
                    }
                },
                0,
                0,
                null,
                null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        );
        requestQueue.add(imageRequest);
    }
}