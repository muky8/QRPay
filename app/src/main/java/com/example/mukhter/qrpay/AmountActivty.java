package com.example.mukhter.qrpay;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AmountActivty extends AppCompatActivity {

    EditText edittext1;
    Button button1;
    String amountedit;
    RequestQueue requestQueue3;
    String logtoken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount_activty);
        edittext1=(EditText)findViewById(R.id.amountno);
        button1=(Button)findViewById(R.id.generate_btn);
        requestQueue3 = Volley.newRequestQueue(this);
        Intent intent= getIntent();
        logtoken=intent.getStringExtra("tokenm");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountedit=edittext1.getText().toString();
                JSONObject json = new JSONObject();
                try {

                    json.put("email", amountedit); //Add the data you'd like to send to the server.

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String url = "https://qr-pay.herokuapp.com/encode-qr";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("RESPONSE", response.toString());
                                Intent intent = new Intent(AmountActivty.this, BarcodeActivity.class);
                                intent.putExtra("encoded",response.toString());
                                startActivity(intent);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }

                        }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Authorization", "Bearer " + logtoken );
                        return headers;
                    }
                };
                //add request to queue
                requestQueue3.add(jsonObjectRequest);

            }
        });

    }
}
