package com.piwet.technologies.lafacuapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class HOME extends AppCompatActivity {

    public Button tab_home, tab_publicar, tab_account;

    // ON START ------------------------------------------------------------
    @Override
    protected void onStart() {
        super.onStart();
        if (ParseUser.getCurrentUser().getUsername() != null) {

            // Register for Push Notifications
            ParseInstallation installation = ParseInstallation.getCurrentInstallation();
            // IMPORTANT: REPLACE "263579955805" WITH YOUR OWN GCM Sender ID
            installation.put("GCMSenderId", "47877815975");
            //--------------------------------------------------------
            installation.put("username", ParseUser.getCurrentUser().getUsername());
            installation.put("userID", ParseUser.getCurrentUser().getObjectId());
            installation.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    Log.i("log-", "REGISTERED FOR PUSH NOTIFICATIONS");
                }
            });
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tab_account = findViewById(R.id.tab_five);
        tab_publicar = findViewById(R.id.tab_three);

        tab_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ParseUser.getCurrentUser().getUsername() == null) {

                    startActivity(new Intent(HOME.this, LoginActivity.class));

                } else {


                    startActivity(new Intent(HOME.this, Account.class));

                }

            }
        });

        tab_publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ParseUser.getCurrentUser().getUsername() == null) {

                    startActivity(new Intent(HOME.this, LoginActivity.class));

                } else {
                    startActivity(new Intent(HOME.this, PublishActivity.class));

                }
            }
        });


    }
}
