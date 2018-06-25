package com.example.mukhter.qrpay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {

    RequestQueue requestQueue;
    String token_main;
    String balance;
String name;
    TextView amounttxt;
    Button fundacc;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    FloatingActionButton fab1;
    CardView cardviewgenerate,cardviewscan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        amounttxt = (TextView) findViewById(R.id.amount);
        fundacc=(Button)findViewById(R.id.fundbtn);
        cardviewgenerate=(CardView)findViewById(R.id.card_view);
        cardviewscan=(CardView)findViewById(R.id.card_view4);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Intent intent = getIntent();
        token_main = intent.getStringExtra("token");
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("token_key", token_main);
        editor.commit();

        requestQueue = Volley.newRequestQueue(this);
        String url = "https://qr-pay.herokuapp.com/auth/user";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("RESPONSE", response.toString());

                        for (int i = 0; i < response.length(); i++)
                            try {

                                balance = response.getString("balance");
                                name=response.getString("name");
                                Log.i("BALANCIAGA", balance);

                                amounttxt.setText("₦" + balance);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
                headers.put("Authorization", "Bearer " + token_main);
                return headers;
            }
        };
        //add request to queue
        requestQueue.add(jsonObjectRequest);

        //populate views from above data


        fundacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, FundAccount.class);
                intent.putExtra("name",name);
                intent.putExtra("tokenf",token_main);
                startActivity(intent);
            }
        });

cardviewgenerate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Home.this, AmountActivty.class);
        intent.putExtra("tokenm",token_main);
        startActivity(intent);
    }
});

        cardviewscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Home.this, ScanActivity.class);
                intent2.putExtra("tokenscan",token_main);
                startActivity(intent2);
            }
        });
    }


}
