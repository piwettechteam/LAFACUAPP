package com.piwet.technologies.lafacuapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

public class Account extends AppCompatActivity {

    /* Views */
    ProgressDialog pd;
    Button likeButt;
    TextView likesTxt;
    /* Variables */
    List<ParseObject> myAdsArray;
    public Button tab_home, tab_publicar, tab_account, btnnotification;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Hide ActionBar
        getSupportActionBar().hide();


        // getUserDetails();
        // Init a ProgressDialog
        pd = new ProgressDialog(this);
        pd.setTitle(R.string.app_name);
        pd.setIndeterminate(false);
        pd.setIcon(R.drawable.logo);

        btnnotification = findViewById(R.id.btn_notificacion);

        tab_home = findViewById(R.id.tab_one);
        tab_publicar = findViewById(R.id.tab_three);

        btnnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ParseUser.getCurrentUser().getUsername() == null) {

                    startActivity(new Intent(Account.this, LoginActivity.class));

                } else {

                    startActivity(new Intent(Account.this, NotificationActivity.class));
                }

            }
        });

        tab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ParseUser.getCurrentUser().getUsername() == null) {

                    startActivity(new Intent(Account.this, LoginActivity.class));

                } else {

                    startActivity(new Intent(Account.this, HOME.class));
                }
            }
        });

        tab_publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ParseUser.getCurrentUser().getUsername() == null) {

                    startActivity(new Intent(Account.this, LoginActivity.class));

                } else {

                    startActivity(new Intent(Account.this, PublishActivity.class));

                }
            }
        });



        getUserDetails();

    }


    // MARK: - GET USER'S DETAILS ----------------------------------------------------------------
    void getUserDetails() {
        ParseUser currUser = ParseUser.getCurrentUser();

        // Get username
        TextView usernTxt = findViewById(R.id.accUsernameTxt);
        usernTxt.setTypeface(Configs.titSemibold);
        usernTxt.setText("@" + currUser.getString(Configs.USER_USERNAME));

        // Get fullname
        TextView fnTxt = findViewById(R.id.textviewFullName);
        fnTxt.setTypeface(Configs.titSemibold);
        fnTxt.setText(currUser.getString(Configs.USER_FULLNAME));

        // Get saldo
        TextView saldotxt = findViewById(R.id.textViewSaldo);
        saldotxt.setTypeface(Configs.titSemibold);
        saldotxt.setText(" $ " + currUser.getInt(Configs.USER_SALDO) + "  USD ");


        // Get avatar
        // Get Image
        final ImageView avImg = findViewById(R.id.accAvatarImg);
        ParseFile fileObject = (ParseFile) currUser.get(Configs.USER_AVATAR);
        if (fileObject != null) {
            fileObject.getDataInBackground(new GetDataCallback() {
                public void done(byte[] data, ParseException error) {
                    if (error == null) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                        if (bmp != null) {
                            avImg.setImageBitmap(bmp);
                        }
                    }
                }
            });
        }


        // Call query
        //queryMyAds();
    }
}
