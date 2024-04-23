package com.codewitharyan.chatbot;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    EditText lname;
    public static final String EXTRA_NAME= "com.codewitharyan.chatbot.extra.LNAME";
    EditText lpassword;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lname = findViewById(R.id.lname);

        lpassword = findViewById(R.id.lpassword);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (!checkLCredentials() ){

               }  else
               {
                   checkUser();

               }
            }
        });



        }



    private boolean checkLCredentials() {

        String Name=lname.getText().toString();
        String Password=lpassword.getText().toString();
        if( Name.isEmpty() || !Name.contains("@")){
            showError(lname,"Fill Email details correctly");
        }
        else if(Password.isEmpty() || Password.length()<7)
        {
            showError(lpassword,"Password does not met credentials");
        }
        else
        {
            Toast.makeText(this, "Login Successful !", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,MainActivity2.class);
            startActivity(intent);
            String nameText= lname.getText().toString();
            intent.putExtra(EXTRA_NAME,nameText);
            startActivity(intent);
        }

        return false;
    }

    public void checkUser(){
        String username = lname.getText().toString().trim();
        String userPassword = lpassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(username);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {
                    lname.setError(null);
                    String passwordFromDB = snapshot.child(username).child("password").getValue(String.class);

                    if (!Objects.equals(passwordFromDB,userPassword)){
                        lname.setError(null);
                        Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                        String lnameText=lname.getText().toString();
                        intent.putExtra(EXTRA_NAME,lnameText);
                        startActivity(intent);
                    }
                    else {
                        lpassword.setError("Invalid Credentials");
                        lpassword.requestFocus();
                    }
                }
                else {
                    lname.setError("User does not exist");
                    lname.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    public void openActivity(View v){
        checkLCredentials();

    }
    public void signupPage (View v){
        Toast.makeText(MainActivity.this, "register here", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this, SignupPage.class);
        startActivity(intent);

    }

}