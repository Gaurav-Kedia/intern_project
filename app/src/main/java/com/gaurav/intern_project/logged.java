package com.gaurav.intern_project;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class logged extends AppCompatActivity {

    CardView webv, androidv, ppv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loggedin);

        webv = findViewById(R.id.web);
        androidv = findViewById(R.id.android);
        ppv = findViewById(R.id.pp);

        boolean web = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("web", false);
        boolean android = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("android", false);
        boolean pp = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("pp", false);

        if (web) {
            webv.setVisibility(View.VISIBLE);
        }
        if (android) {
            androidv.setVisibility(View.VISIBLE);
        }
        if (pp) {
            ppv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    public void logout(View view) {
        FirebaseAuth mAuth = null;
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        finish();
    }
}
