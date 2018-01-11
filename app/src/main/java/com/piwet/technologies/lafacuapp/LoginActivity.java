package com.piwet.technologies.lafacuapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class LoginActivity extends AppCompatActivity {

    /* Views */
    ProgressDialog progDialog;
    EditText usernameTxt;
    EditText passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        // Hide ActionBar
        getSupportActionBar().hide();

        // Hide Status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // Init a ProgressDialog
        progDialog = new ProgressDialog(this);
        progDialog.setTitle(R.string.app_name);
        progDialog.setMessage("Conectando...");
        progDialog.setIndeterminate(false);

        // Init views
        usernameTxt = findViewById(R.id.usernameTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        usernameTxt.setTypeface(Configs.titSemibold);
        passwordTxt.setTypeface(Configs.titSemibold);

        TextView lTitleTxt = findViewById(R.id.loginTitleTxt);
        lTitleTxt.setTypeface(Configs.qsBold);


        // MARK: - LOGIN BUTTON ------------------------------------------------------------------
        Button loginButt = findViewById(R.id.loginButt);
        loginButt.setTypeface(Configs.titBlack);
        loginButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progDialog.show();

                ParseUser.logInInBackground(usernameTxt.getText().toString(), passwordTxt.getText().toString(),
                        new LogInCallback() {
                            public void done(ParseUser user, ParseException error) {
                                if (user != null) {
                                    progDialog.dismiss();
                                    startActivity(new Intent(LoginActivity.this, HOME.class));
                                } else {
                                    progDialog.dismiss();
                                    Configs.simpleAlert(error.getMessage(), LoginActivity.this);
                                }
                            }
                        });
            }
        });


        // MARK: - SIGN UP BUTTON ------------------------------------------------------------------
        Button signupButt = findViewById(R.id.signUpButt);
        signupButt.setTypeface(Configs.titSemibold);
        signupButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });


        // MARK: - FORGOT PASSWORD BUTTON ------------------------------------------------------------------
        Button fpButt = findViewById(R.id.forgotPassButt);
        fpButt.setTypeface(Configs.titSemibold);
        fpButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                alert.setTitle(R.string.app_name);
                alert.setMessage("Ingresá tu email a continuación.");

                // Add an EditTxt
                final EditText editTxt = new EditText(LoginActivity.this);
                editTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                alert.setView(editTxt)
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                // Reset password
                                ParseUser.requestPasswordResetInBackground(editTxt.getText().toString(), new RequestPasswordResetCallback() {
                                    public void done(ParseException error) {
                                        if (error == null) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                            builder.setMessage("Si tu email está asociado a una cuenta de Uni recibirás un email con un link para restaurar tu contraseña. Comprueba la carpeta de spam o correo basura si no ves el email en tu bandeja de entrada.")
                                                    .setTitle(R.string.app_name)
                                                    .setPositiveButton("OK", null);
                                            AlertDialog dialog = builder.create();
                                            dialog.setIcon(R.drawable.logo);
                                            dialog.show();

                                        } else {
                                            Configs.simpleAlert(error.getMessage(), LoginActivity.this);
                                        }
                                    }
                                });

                            }
                        });
                alert.show();



            }
        });


        // MARK: - DISMISS BUTTON ------------------------------------
        Button dismButt = findViewById(R.id.loginDismissButt);
        dismButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }// end onCreate()


}//@end

