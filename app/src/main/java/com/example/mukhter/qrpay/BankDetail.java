package com.example.mukhter.qrpay;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BankDetail extends AppCompatActivity {
    String fname, femail, fpass, tezt;
    EditText accountnum;
    String accountnumber;
    RequestQueue requestQueue;
    Button next;

    Spinner spinner;
    String spinner_text;
    ArrayList<String> arrayList1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_detail);
        spinner = (Spinner) findViewById(R.id.spinner1);
        arrayList1 = new ArrayList<>();
//get values from intents
        Intent intent = getIntent();
        fname = intent.getStringExtra("name");
        femail = intent.getStringExtra("email");


        //initialize views
        accountnum = (EditText) findViewById(R.id.accountno);
        accountnumber = accountnum.getText().toString();
        next = (Button) findViewById(R.id.next_btn);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        requestQueue = Volley.newRequestQueue(this);

             /*Json Request*/
        String url = "https://qr-pay.herokuapp.com/banks";
        Log.i("RESPONSE", url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("RESPONSE", response.toString());

                        for (int i = 0; i < response.length(); i++)
                            try {

                                JSONObject obj = response.getJSONObject(i);

                                // Genre is json array
                                String bank_name = obj.getString("name");
                                String bank_code = obj.getString("code");
                                arrayList1.add(bank_name);

                                Log.i("Bank name", bank_name);
                                Log.i("Bank code", bank_code);

                                //Log.e("items", "item added !");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        //add request to queue
        requestQueue.add(jsonArrayRequest);
        adapter.notifyDataSetChanged();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spinner_text = spinner.getSelectedItem().toString();
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(BankDetail.this, spinner_text, Toast.LENGTH_LONG).show();
                Log.i("texxt", spinner_text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BankDetail.this, SecretCode.class);
              //  intent.putExtra("name", fname);
                //intent.putExtra("email", femail);
                //intent.putExtra("pass", fpass);
                //intent.putExtra("account_no", accountnumber);
              //  intent.putExtra("bank_code", spinner_text);
                startActivity(intent);
            }
        });


    }
}
