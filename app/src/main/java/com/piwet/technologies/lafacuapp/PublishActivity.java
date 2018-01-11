package com.piwet.technologies.lafacuapp;

import android.*;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PublishActivity extends AppCompatActivity {

    /* Views */
    ProgressDialog pd;
    Button categoriesButt, newButtArchivo, newButtImagen, newButtEnlace;

    EditText adTitleTxt, comentariotxt;
    ImageView image1;


    /* Variables */
    ParseObject adObj;
    int pictureTag = 0;

    MarshMallowPermission mmp = new MarshMallowPermission(this);
    String categoryName = "";
    String condition = "";
    List<ParseObject> categoriesArray;
    RelativeLayout categoriesLayout;
    // IMAGE/VIDEO HANDLING METHODS ------------------------------------------------------------------------
    int CAMERA = 0;
    int GALLERY = 1;

    Uri imageURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        // Hide ActionBar
        getSupportActionBar().hide();


        // Init a ProgressDialog
        pd = new ProgressDialog(this);
        pd.setTitle(R.string.app_name);
        pd.setIndeterminate(false);
        pd.setIcon(R.drawable.logo);


        // Init views
        categoriesButt = findViewById(R.id.sellCategoriesButt);
        categoriesButt.setTypeface(Configs.titSemibold);
        newButtArchivo = findViewById(R.id.buttonArchivo);
        newButtArchivo.setTypeface(Configs.titSemibold);
        newButtImagen = findViewById(R.id.buttonImagen);
        newButtImagen.setTypeface(Configs.titSemibold);
        newButtEnlace = findViewById(R.id.buttonEnlace);
        newButtEnlace.setTypeface(Configs.titSemibold);

        comentariotxt = findViewById(R.id.editTextCommentarios);
        comentariotxt.setTypeface(Configs.titRegular);
        adTitleTxt = findViewById(R.id.editTextTitulo);
        adTitleTxt.setTypeface(Configs.titRegular);

        image1 = findViewById(R.id.imageViewPreview);


        categoriesLayout = findViewById(R.id.sellCategoriesLayout);
        hideCategoriesLayout();


        // Call query
        queryCategories();


        // MARK: - BACK BUTTON ------------------------------------
        Button backButt = findViewById(R.id.btn_cancelar);
        backButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        // MARK: - CHOOSE CATEGORY BUTTON ------------------------------------
        categoriesButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCategoriesLayout();
            }
        });


        // MARK: - DONE CATEGORY BUTTON ------------------------------------
        Button catDoneButt = findViewById(R.id.sellCategDoneButt);
        catDoneButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideCategoriesLayout();
            }
        });


        // MARK: - NEW BUTTON ------------------------------------
        newButtArchivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                condition = "Archivo";
                newButtArchivo.setBackgroundColor(Color.parseColor("#777777"));
                newButtImagen.setBackgroundColor(Color.parseColor("#bababa"));
                newButtEnlace.setBackgroundColor(Color.parseColor("#bababa"));




            }
        });

        // MARK: - USED BUTTON ------------------------------------
        newButtImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                condition = "Imagen";
                newButtArchivo.setBackgroundColor(Color.parseColor("#bababa"));
                newButtImagen.setBackgroundColor(Color.parseColor("#777777"));
                newButtEnlace.setBackgroundColor(Color.parseColor("#bababa"));






            }
        });


        // MARK: - USED BUTTON ------------------------------------
        newButtEnlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                condition = "Enlace";
                newButtArchivo.setBackgroundColor(Color.parseColor("#bababa"));
                newButtImagen.setBackgroundColor(Color.parseColor("#bababa"));
                newButtEnlace.setBackgroundColor(Color.parseColor("#777777"));







            }
        });






        // MARK: - SUBMIT AD BUTTON -----------------------------------------------------------------
        Button submitButt = findViewById(R.id.btn_publish);
        submitButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                dismissKeyboard();



                if (image1.getDrawable() == null || adTitleTxt.getText().toString().matches("") || condition.matches("") || categoryName.matches("")) {
                    Configs.simpleAlert("Debe asegurarse de haber insertado los siguientes detalles:\n-Categoría\n-Título\n-Condición de la asignatura", PublishActivity.this);

                } else {
                    pd.setMessage("Enviando...");
                    pd.show();

                    adObj.put(Configs.ADS_SELLER_POINTER, currentUser);
                    adObj.put(Configs.ADS_TITLE, adTitleTxt.getText().toString());
                    adObj.put(Configs.ADS_CATEGORY, categoryName);
                    adObj.put(Configs.ADS_CONDITION, condition);
                    adObj.put(Configs.ADS_DESCRIPTION, comentariotxt.getText().toString());

                    // Add keywords
                    List<String> keywords = new ArrayList<String>();
                    String[] a = adTitleTxt.getText().toString().toLowerCase().split(" ");
                    String[] b = comentariotxt.getText().toString().toLowerCase().split(" ");
                    for (String keyw : a) {
                        keywords.add(keyw);
                    }
                    for (String keyw : b) {
                        keywords.add(keyw);
                    }
                    keywords.add(condition.toLowerCase());
                    keywords.add("@" + currentUser.getString(Configs.USER_USERNAME).toLowerCase());
                    adObj.put(Configs.ADS_KEYWORDS, keywords);

                    adObj.put(Configs.ADS_LIKES, 0);
                    adObj.put(Configs.ADS_COMMENTS, 0);
                    adObj.put(Configs.ADS_IS_REPORTED, false);


                    // Saving block
                    adObj.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {

                                // Save image1
                                Bitmap bitmap = ((BitmapDrawable) image1.getDrawable()).getBitmap();
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                                byte[] byteArray = stream.toByteArray();
                                ParseFile imageFile = new ParseFile("image1.jpg", byteArray);
                                adObj.put(Configs.ADS_IMAGE1, imageFile);



                                // Save images now
                                adObj.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {

                                                        pd.dismiss();

                                                        AlertDialog.Builder alert = new AlertDialog.Builder(PublishActivity.this);
                                                        alert.setMessage("Su asignatura ha sido enviada con exito! Lo revisaremos en las proximas 48 horas.")
                                                                .setTitle(R.string.app_name)
                                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                                        startActivity(new Intent(PublishActivity.this, Account.class));
                                                                    }
                                                                })
                                                                .setIcon(R.drawable.logo);
                                                        alert.create().show();



                                            // error
                                        } else {
                                            Configs.simpleAlert(e.getMessage(), PublishActivity.this);
                                            pd.dismiss();
                                        }
                                    }
                                });


                                // error on saving
                            } else {
                                Configs.simpleAlert(e.getMessage(), PublishActivity.this);
                                pd.dismiss();
                            }
                        }
                    });


                } // en IF

            }
        });




}



    // MARK: - SHOW ALERT FOR UPLOADING IMAGES -----------------------------------------------------
    void showAlertForImage() {
        Log.i("log-", "PICTURE TAG: " + pictureTag);
        AlertDialog.Builder alert = new AlertDialog.Builder(PublishActivity.this);
        alert.setMessage(R.string.seleccionar_string)
                .setTitle(R.string.app_name)
                .setPositiveButton("Tomar una foto", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!mmp.checkPermissionForCamera()) {
                            mmp.requestPermissionForCamera();
                        } else {
                            openCamera();
                        }
                    }
                })

                .setNegativeButton("Desde la libreria", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!mmp.checkPermissionForReadExternalStorage()) {
                            mmp.requestPermissionForReadExternalStorage();
                        } else {
                            openGallery();
                        }
                    }
                })

                .setNeutralButton("Cancelar", null)
                .setIcon(R.drawable.logo);
        alert.create().show();
    }

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


    // IMAGE/VIDEO PICKED DELEGATE ------------------------------
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


                // Video from Camera or Gallery
            }

            // Set images based on the pictureTag
            if (bm != null) {
                Bitmap scaledBm = Configs.scaleBitmapToMaxSize(600, bm);
                if (pictureTag == 1) {
                    image1.setImageBitmap(scaledBm);
                }
            }

        }
    }
    //---------------------------------------------------------------------------------------------



    // MARK: - QUERY CATEGORIES ------------------------------------------------------------
    void queryCategories() {
        ParseQuery query = ParseQuery.getQuery(Configs.CATEGORIES_CLASS_NAME);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException error) {
                if (error == null) {
                    categoriesArray = objects;

                    // CUSTOM LIST ADAPTER
                    class ListAdapter extends BaseAdapter {
                        private Context context;

                        public ListAdapter(Context context, List<ParseObject> objects) {
                            super();
                            this.context = context;
                        }

                        // CONFIGURE CELL
                        @Override
                        public View getView(int position, View cell, ViewGroup parent) {
                            if (cell == null) {
                                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                cell = inflater.inflate(R.layout.cell_category_sortby, null);
                            }

                            // Get Parse object
                            ParseObject cObj = categoriesArray.get(position);

                            TextView catTxt = cell.findViewById(R.id.cCatSortTxt);
                            catTxt.setTypeface(Configs.titRegular);
                            catTxt.setText(cObj.getString(Configs.CATEGORIES_CATEGORY));

                            return cell;
                        }

                        @Override
                        public int getCount() {
                            return categoriesArray.size();
                        }

                        @Override
                        public Object getItem(int position) {
                            return categoriesArray.get(position);
                        }

                        @Override
                        public long getItemId(int position) {
                            return position;
                        }
                    }

                    // Init ListView and set its adapter
                    ListView aList = findViewById(R.id.sellCategListView);
                    aList.setAdapter(new ListAdapter(PublishActivity.this, categoriesArray));
                    aList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                            ParseObject cObj = categoriesArray.get(position);
                            categoriesButt.setText(cObj.getString(Configs.CATEGORIES_CATEGORY));
                            categoryName = cObj.getString(Configs.CATEGORIES_CATEGORY);
                        }
                    });


                    // Error in query
                } else {
                    Configs.simpleAlert(error.getMessage(), PublishActivity.this);
                    pd.dismiss();
                }
            }
        });

    }


    // MARK: - SHOW/HIDE CATEGORIES LAYOUT
    void showCategoriesLayout() {
        ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(categoriesLayout.getLayoutParams());
        marginParams.setMargins(0, 0, 0, 0);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
        categoriesLayout.setLayoutParams(layoutParams);
    }


    void hideCategoriesLayout() {
        ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(categoriesLayout.getLayoutParams());
        marginParams.setMargins(0, 5000, 0, 0);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
        categoriesLayout.setLayoutParams(layoutParams);
    }




    // MARK: - DISMISS KEYBOARD
    public void dismissKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(adTitleTxt.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(comentariotxt.getWindowToken(), 0);
    }


}
