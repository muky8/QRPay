package com.example.mukhter.qrpay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText emailadd, password1;
    String semaillog, spassword;
    ProgressDialog mprogressbar;
    RequestQueue requestQueue;
    Button logbtn;
    String logintoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailadd = (EditText) findViewById(R.id.emaillogg);
        password1 = (EditText) findViewById(R.id.passwordlogg);

        logbtn=(Button)findViewById(R.id.log_btn);
        mprogressbar = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);
        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mprogressbar.setMessage("Loading...");
                mprogressbar.setCancelable(false);
                mprogressbar.show();
                semaillog = emailadd.getText().toString();
                spassword = password1.getText().toString();
                JSONObject json = new JSONObject();
                try {

                    json.put("email", semaillog); //Add the data you'd like to send to the server.
                    json.put("password", spassword);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String urlpost = "https://qr-pay.herokuapp.com/auth/login";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlpost, json,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                mprogressbar.dismiss();

                                for (int i = 0; i < response.length(); i++)
                                    try {


                                        // Genre is json array
                                        String email = response.getString("email");
                                        String name = response.getString("name");
                                        logintoken = response.getString("token");


                                        Log.i("Tokenlog", logintoken);


                                        //Log.e("items", "item added !");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                Intent intent = new Intent(Login.this, Home.class);
                                intent.putExtra("token",logintoken);
                                startActivity(intent);
                                Log.i("Response", response.toString());
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mprogressbar.dismiss();
                        Toast.makeText(Login.this, "Error, try again", Toast.LENGTH_SHORT).show();
                        Log.i("Error", error.toString());
                    }
                });

                requestQueue.add(jsonObjectRequest);
            }

        });
    }
}
