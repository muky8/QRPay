package com.example.mukhter.qrpay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.flutterwave.raveandroid.RaveConstants;
import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RavePayManager;

public class FundAccount extends AppCompatActivity {
    EditText editText;
    Button button1;
    String amount;
    String username,tokenf;
Double value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_account);
        editText=(EditText)findViewById(R.id.amountnumber);
        button1=(Button)findViewById(R.id.fund_btn);
        Intent intent=getIntent();
        username=intent.getStringExtra("name");
        tokenf=intent.getStringExtra("tokenf");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount=editText.getText().toString();
                value=Double.parseDouble(amount);
                new RavePayManager(FundAccount.this).setAmount(value)
                        .setCountry("NG")
                        .setCurrency("NGN")
                        .setfName(username)
                        .setlName("")
                        .setNarration("")
                        .setPublicKey("FLWPUBK-5bfb65a9817463e6eddda52c1aad9a8e-X")
                        .setSecretKey("FLWSECK-430cb25a158d67e8720a371344c004a1-X")
                        .setTxRef("")
                        .acceptAccountPayments(true)
                        .acceptCardPayments(true)
                        .onStagingEnv(true)
                        .initialize();
            }
        });




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                Toast.makeText(this, "SUCCESS " + message, Toast.LENGTH_SHORT).show();


            }
            else if (resultCode == RavePayActivity.RESULT_ERROR) {
                Toast.makeText(this, "ERROR " + message, Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                Toast.makeText(this, "CANCELLED " + message, Toast.LENGTH_SHORT).show();

            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
