package com.example.mukhter.qrpay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {
    EditText edit_fullname, edit_email, edit_password;
    String sfullname, semail, spassword;
    Button register_btn;
    ProgressDialog mprogressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        //initialize views
        edit_fullname = (EditText) findViewById(R.id.fullnamelog);
        edit_email = (EditText) findViewById(R.id.emaillog);
        edit_password = (EditText) findViewById(R.id.passwordlog);
        register_btn = (Button) findViewById(R.id.next_btn);




        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sfullname = edit_fullname.getText().toString();
                semail = edit_email.getText().toString();
                spassword = edit_password.getText().toString();

                Intent regintent = new Intent(Register.this, SecretCode.class);
                regintent.putExtra("name", sfullname);
                regintent.putExtra("email", semail);
                regintent.putExtra("passintent", spassword);

                startActivity(regintent);
            }
        });

    }
}
