package com.codewitharyan.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignupPage extends AppCompatActivity {

    EditText name, email, password, confirmpassword;
    FirebaseDatabase database;
    DatabaseReference reference;



    private static int RC_SIGN_IN = 100;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuppage);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);
        MaterialButton signupbutton = (MaterialButton) findViewById(R.id.signupbutton);
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                checkCredentials();


            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
    private void checkCredentials() {


        String Name = name.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String ConfirmPassword = confirmpassword.getText().toString();



        if (Name.isEmpty() || Name.length() < 7) {
            showError(name, "Username is not valid");
        } else if (Email.isEmpty() || !Email.contains("@")) {
            showError(email, "Email is not valid");
        } else if (Password.isEmpty()) {
            password.setError("Field can not be empty");
        } else if (!PASSWORD_PATTERN.matcher(Password).matches()) {
            password.setError("Password is too weak");
        } else if (ConfirmPassword.isEmpty() || !ConfirmPassword.equals(Password)) {
            showError(confirmpassword, "Password does not match !");
        } else {
            database = FirebaseDatabase.getInstance();
            reference = database.getReference("users");

            String Dusername = name.getText().toString();
            String Demail = email.getText().toString();
            String Dpassword = password.getText().toString();
            String DconfirmPassword = confirmpassword.getText().toString();


            HelperClass helperClass = new HelperClass(Dusername, Demail, DconfirmPassword);
            reference.child(Dusername).setValue(helperClass);
            Toast.makeText(SignupPage.this, "You have signup succesfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignupPage.this, Log.class);



        }

    }


    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    void register() {

        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}