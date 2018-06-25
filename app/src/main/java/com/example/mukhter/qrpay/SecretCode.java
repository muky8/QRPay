package com.example.mukhter.qrpay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.irvingryan.VerifyCodeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SecretCode extends AppCompatActivity {
    RequestQueue requestQueue2;
    Button register;
    String sname, semail, spass, account, bank_code, code;
    VerifyCodeView verifcode;
    ProgressDialog mprogressbar;
    RequestQueue requestQueue;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_code);
        verifcode = (VerifyCodeView) findViewById(R.id.verifcode);

        //get values from intents


        Intent intent = getIntent();
        sname = intent.getStringExtra("name");
        semail = intent.getStringExtra("email");
        spass = intent.getStringExtra("passintent");


        requestQueue = Volley.newRequestQueue(this);
        mprogressbar = new ProgressDialog(this);

        register = (Button) findViewById(R.id.reg_btn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mprogressbar.setMessage("Loading...");
                mprogressbar.setCancelable(false);
                mprogressbar.show();
                code = verifcode.getText();


                JSONObject json = new JSONObject();
                try {

                    json.put("email", semail); //Add the data you'd like to send to the server.
                    json.put("name", sname);
                    json.put("code", code);
                    json.put("password", spass);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String urlpost = "https://qr-pay.herokuapp.com/auth/signup";
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
                                        token = response.getString("token");


                                        Log.i("Token", token);


                                        //Log.e("items", "item added !");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                Intent intent = new Intent(SecretCode.this, Home.class);
                                intent.putExtra("token",token);
                                startActivity(intent);
                                Log.i("Response", response.toString());
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mprogressbar.dismiss();
                        Toast.makeText(SecretCode.this, "Error, try again", Toast.LENGTH_SHORT).show();
                        Log.i("Error", error.toString());
                    }
                });

                requestQueue.add(jsonObjectRequest);
            }

        });

    }
}
