package com.gaurav.intern_project;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.sax.StartElementListener;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurav.intern_project.data.Contract;
import com.gaurav.intern_project.data.DbHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout Login_view;
    private TextView List_of_workshops, Signin;
    private Button Login;
    EditText email_UI, pass_UI;

    private FirebaseAuth mAuth;

    public ArrayList<String> list = new ArrayList<String>();
    private RecyclerView recyclerView;
    private Adapter mAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    //List_of_workshops.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    Login_view.setVisibility(View.VISIBLE);

                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    Login_view.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    //List_of_workshops.setVisibility(View.VISIBLE);
                    show_list();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        Login_view = (RelativeLayout) findViewById(R.id.login_view);
        //List_of_workshops = (TextView) findViewById(R.id.workshops);
        Login = (Button) findViewById(R.id.button_login);
        Signin = (TextView) findViewById(R.id.sigin_button);
        email_UI = (EditText) findViewById(R.id.email_login);
        pass_UI = (EditText) findViewById(R.id.password_login);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_workshop);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUp.class));
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_user();
            }
        });
    }

    private void login_user() {
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
            mAuth.fetchProvidersForEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                            check[0] = !task.getResult().getProviders().isEmpty();
                            //Toast.makeText(HomeActivity.this, "check :" + check[0], Toast.LENGTH_SHORT).show();
                            sigininORcreate(check[0], email, password);
                        }
                    });

        }
    }

    private void show_list() {
        list = new ArrayList<String>();
        DbHelper mDbHelper = new DbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projections = {
                Contract.Entry._ID,
                Contract.Entry.WORKSHOP_NAME};
        Cursor cursor = db.query(
                Contract.Entry.TABLE_NAME,
                projections,
                null,
                null,
                null,
                null,
                null);

        //List_of_workshops.setText("Table contains " + cursor.getCount() + " workshops");

        int idColumnIndex = cursor.getColumnIndex(Contract.Entry._ID);
        int nameColumnIndex = cursor.getColumnIndex(Contract.Entry.WORKSHOP_NAME);

        while(cursor.moveToNext()){
            int currentID = cursor.getInt(idColumnIndex);
            String currentname = cursor.getString(nameColumnIndex);
            list.add(currentname);

           // List_of_workshops.append(("\n" + currentID + " " +
           //         currentname));
        }

        mAdapter = new Adapter(list, this);
        recyclerView.setAdapter(mAdapter);

    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private void sigininORcreate(boolean check, String email, String password){
        if (check) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Toast.makeText(MainActivity.this, "Welcome to workshop registration", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, logged.class));
                            }
                            else{
                                pass_UI.setError("Invalid Credentials");
                                View focusView = pass_UI;
                                focusView.requestFocus();
                            }
                        }
                    });

        } else {
            /*mAuth.createUserWithEmailAndPassword(email, password);*/
            Toast.makeText(MainActivity.this, "Please signin to register for workshop", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, SignUp.class));
        }

    }
}
