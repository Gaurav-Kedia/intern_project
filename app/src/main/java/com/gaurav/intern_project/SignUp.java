package com.gaurav.intern_project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    private EditText email_UI, pass_UI;
    private Button button_signin;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        mAuth = FirebaseAuth.getInstance();

        email_UI = findViewById(R.id.email_signin);
        pass_UI = findViewById(R.id.password_signin);
        button_signin = findViewById(R.id.signup);

        button_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup_user();
            }
        });


    }

    private void signup_user() {
        final String email, password;

        email = email_UI.getText().toString().trim();
        password = pass_UI.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            pass_UI.setError("Incorrect Password");
            focusView = pass_UI;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            email_UI.setError("This field is required");
            focusView = email_UI;
            cancel = true;
        } else if (!isEmailValid(email)) {
            email_UI.setError("This email address is invalid");
            focusView = email_UI;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            pass_UI.setError("This field is required");
            focusView = pass_UI;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();// There was an error; don't attempt login and focus the first
        } else {
            final boolean[] check = new boolean[1];
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(SignUp.this, "User created", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public void back_to_login_view(View view) {
        finish();
    }
}
