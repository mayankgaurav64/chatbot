package com.codewitharyan.chatbot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity2 extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    Button pdfbutton;
    Button voicebutton;
    TextView signname;
    Button signoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        signname=findViewById(R.id.signinname);
        Intent intent=getIntent();
        String lname=intent.getStringExtra(MainActivity.EXTRA_NAME);
        signname.setText(lname);
        voicebutton=findViewById(R.id.vbutton);
        voicebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //gotoURL("tushar path");
                Intent i=new Intent(getApplicationContext(),mainactivity3.class);
                MainActivity2.this.startActivity(i);


            }
        });
        pdfbutton=findViewById(R.id.pbutton);

          pdfbutton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent i=new Intent(getApplicationContext(),mainactivity4.class);
                  MainActivity2.this.startActivity(i);
              }
          });

      signoutBtn=findViewById(R.id.signout);



        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });



    }
    void signOut(){
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete( Task<Void> task) {
      finish();
      startActivity(new Intent(MainActivity2.this,MainActivity.class));

            }
        });
    }
    void gotoURL(String s){
        try {
            Uri uri=Uri.parse(s);


        }catch(Exception e){
            Toast.makeText(getApplicationContext(),"something went wrong !",Toast.LENGTH_SHORT).show();

        }
    }
}