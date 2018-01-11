package com.piwet.technologies.lafacuapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    /* Views */
    ImageView avatarImg;
    EditText usernameTxt;
    EditText passwordTxt;
    EditText fullnameTxt;
    EditText emailTxt;
    ProgressDialog pd;

    /* Variables */
    MarshMallowPermission mmp = new MarshMallowPermission(this);
    // IMAGE HANDLING METHODS ------------------------------------------------------------------------
    int CAMERA = 0;
    int GALLERY = 1;
    Uri imageURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Hide ActionBar
        getSupportActionBar().hide();

        // Hide Status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // Init a ProgressDialog
        pd = new ProgressDialog(this);
        pd.setTitle(R.string.app_name);
        pd.setMessage("Registrando...");
        pd.setIndeterminate(false);

        // Init views
        avatarImg = findViewById(R.id.cadAvatarImg);
        usernameTxt = findViewById(R.id.usernameTxt2);
        passwordTxt = findViewById(R.id.passwordTxt2);
        fullnameTxt = findViewById(R.id.fullnameTxt);
        emailTxt = findViewById(R.id.emailTxt2);
        usernameTxt.setTypeface(Configs.titSemibold);
        passwordTxt.setTypeface(Configs.titSemibold);
        fullnameTxt.setTypeface(Configs.titSemibold);
        emailTxt.setTypeface(Configs.titSemibold);


        // MARK: - CAMERA BUTTON ----------------------------------------
        Button camButt = findViewById(R.id.signupCamButt);
        camButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mmp.checkPermissionForCamera()) {
                    mmp.requestPermissionForCamera();
                } else {
                    openCamera();
                }
            }
        });


        // MARK: - GALLERY BUTTON ----------------------------------------
        Button galButt = findViewById(R.id.signupGalleryButt);
        galButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mmp.checkPermissionForReadExternalStorage()) {
                    mmp.requestPermissionForReadExternalStorage();
                } else {
                    openGallery();
                }
            }
        });


        // SIGN UP BUTTON ------------------------------------------------------------------------
        Button signupButt = findViewById(R.id.signUpButt2);
        signupButt.setTypeface(Configs.titBlack);
        signupButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (usernameTxt.getText().toString().matches("") || passwordTxt.getText().toString().matches("") ||
                        emailTxt.getText().toString().matches("") || fullnameTxt.getText().toString().matches("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Debe llenar todos los campos para registrarse")
                            .setTitle(R.string.app_name)
                            .setPositiveButton("OK", null);
                    AlertDialog dialog = builder.create();
                    dialog.setIcon(R.drawable.logo);
                    dialog.show();


                } else {
                    if (isValidEmaillId(emailTxt.getText().toString().trim())) {

                        pd.show();
                        dismisskeyboard();

                        ParseUser user = new ParseUser();
                        user.setUsername(usernameTxt.getText().toString());
                        user.setPassword(passwordTxt.getText().toString());
                        user.setEmail(emailTxt.getText().toString());
                        user.put(Configs.USER_FULLNAME, fullnameTxt.getText().toString());
                        user.put(Configs.USER_IS_REPORTED, false);
                        user.put(Configs.USER_SALDO, 0);
                        List<String> hasBlocked = new ArrayList<String>();
                        user.put(Configs.USER_HAS_BLOCKED, hasBlocked);


                        // Saving block
                        user.signUpInBackground(new SignUpCallback() {
                            public void done(ParseException error) {
                                if (error == null) {

                                    // Now save avatar
                                    ParseUser user = ParseUser.getCurrentUser();
                                    Bitmap bitmap = ((BitmapDrawable) avatarImg.getDrawable()).getBitmap();
                                    Bitmap scaledBm = Configs.scaleBitmapToMaxSize(300, bitmap);
                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                    scaledBm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                                    byte[] byteArray = stream.toByteArray();
                                    ParseFile imageFile = new ParseFile("avatar.jpg", byteArray);
                                    user.put(Configs.USER_AVATAR, imageFile);
                                    user.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            pd.dismiss();
                                            startActivity(new Intent(RegisterActivity.this, Account.class));


                                        }
                                    });


                                } else {
                                    pd.dismiss();
                                    Configs.simpleAlert(error.getMessage(), RegisterActivity.this);
                                }
                            }
                        });


                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("La dirección de correo electrónico que introdujo no es válida.")
                                .setTitle(R.string.app_name)
                                .setPositiveButton("OK", null);
                        AlertDialog dialog = builder.create();
                        dialog.setIcon(R.drawable.logo);
                        dialog.show();
                    }


                }

            }
        });


        // MARK: - TERMS OF SERVICE BUTTON ----------------------------------------------------------
        Button tosButt = findViewById(R.id.touButt);
        tosButt.setTypeface(Configs.titSemibold);
        tosButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, TermsOfUse.class));
            }
        });


        // MARK: - DISMISS BUTTON ---------------------------------------------------------------
        Button dismissButt = findViewById(R.id.signupDismissButt);
        dismissButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }// end onCreate()

    // OPEN CAMERA
    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory(), "image.jpg");
        imageURI = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".provider", file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
        startActivityForResult(intent, CAMERA);
    }


    // OPEN GALLERY
    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen"), GALLERY);
    }


    // IMAGE PICKED DELEGATE -----------------------------------
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Bitmap bm = null;

            // Image from Camera
            if (requestCode == CAMERA) {
                try {
                    bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageURI);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                // Image from Gallery
            } else if (requestCode == GALLERY) {
                try {
                    bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Set image
            Bitmap scaledBm = Configs.scaleBitmapToMaxSize(300, bm);
            avatarImg.setImageBitmap(scaledBm);
        }
    }
    //---------------------------------------------------------------------------------------------

    private boolean isValidEmaillId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    // DISMISS KEYBOARD
    public void dismisskeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(usernameTxt.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(passwordTxt.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(fullnameTxt.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(emailTxt.getWindowToken(), 0);
    }




}//@end
