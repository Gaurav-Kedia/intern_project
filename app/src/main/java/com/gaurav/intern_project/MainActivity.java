package com.gaurav.intern_project;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurav.intern_project.data.Contract;
import com.gaurav.intern_project.data.DbHelper;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout Login_view;
    private TextView List_of_workshops, Signin;
    private Button Login;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    List_of_workshops.setVisibility(View.GONE);
                    Login_view.setVisibility(View.VISIBLE);

                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    Login_view.setVisibility(View.GONE);
                    List_of_workshops.setVisibility(View.VISIBLE);
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

        Login_view = (RelativeLayout) findViewById(R.id.login_view);
        List_of_workshops = (TextView) findViewById(R.id.workshops);
        Login = (Button) findViewById(R.id.Login_button);
        Signin = (TextView) findViewById(R.id.sigin_button);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUp.class));
            }
        });
    }

    private void show_list() {
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

        List_of_workshops.setText("Table contains " + cursor.getCount() + " workshops");

        int idColumnIndex = cursor.getColumnIndex(Contract.Entry._ID);
        int nameColumnIndex = cursor.getColumnIndex(Contract.Entry.WORKSHOP_NAME);

        while(cursor.moveToNext()){
            int currentID = cursor.getInt(idColumnIndex);
            String currentname = cursor.getString(nameColumnIndex);
            List_of_workshops.append(("\n" + currentID + " " +
                    currentname));
        }

    }
}
