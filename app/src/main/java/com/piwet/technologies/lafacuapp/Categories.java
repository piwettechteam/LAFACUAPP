package com.piwet.technologies.lafacuapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class Categories extends AppCompatActivity {
    /* Views */
    ProgressDialog pd;
    ListView catListView;


    /* Variables */
    List<String> categories;
    String selCateg = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Hide ActionBar
        getSupportActionBar().hide();


        // Init a ProgressDialog
        pd = new ProgressDialog(this);
        pd.setTitle(R.string.app_name);
        pd.setIndeterminate(false);
        pd.setIcon(R.drawable.logo);

        // add "All" to categories array
        categories = new ArrayList<String>();
        categories.add("Todos");


        // Init views
        catListView = findViewById(R.id.categListView);


        // Call query
        queryCategories();


        // MARK: - DONE BUTTON ------------------------------------
        Button doneButt = findViewById(R.id.categDoneButt);
        doneButt.setTypeface(Configs.titSemibold);
        doneButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!selCateg.matches("")) {
                    Configs.selectedCategory = selCateg;
                    categories.clear();
                    finish();
                } else {
                    Configs.simpleAlert("Seleccionar una categoria o apretar cancelar!", Categories.this);
                }
            }
        });


        // MARK: - CANCEL BUTTON ------------------------------------
        Button cancButt = findViewById(R.id.categCancelButt);
        cancButt.setTypeface(Configs.titSemibold);
        cancButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categories.clear();
                finish();

            }
        });


    }// end onCreate()


    // MARK: - QUERY CATEGORIES ---------------------------------------------------------------
    void queryCategories() {
        pd.setMessage("Espere...");
        pd.show();

        ParseQuery query = ParseQuery.getQuery(Configs.CATEGORIES_CLASS_NAME);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(final List<ParseObject> objects, ParseException error) {
                if (error == null) {
                    pd.dismiss();

                    for (int i = 0; i < objects.size(); i++) {
                        // Get Parse object
                        ParseObject cObj = objects.get(i);
                        categories.add(cObj.getString(Configs.CATEGORIES_CATEGORY));
                    }

                    // Init ListView and set its adapter
                    catListView.setAdapter(new ListAdapter(Categories.this, categories));
                    catListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                            selCateg = categories.get(position);


                            Log.i("log-", "SELECTED CATEGORY: " + selCateg);
                        }
                    });

                    // Error in query
                } else {
                    Configs.simpleAlert(error.getMessage(), Categories.this);
                    pd.dismiss();
                }
            }
        });

    }


    // CUSTOM LIST ADAPTER
    public class ListAdapter extends BaseAdapter {
        private Context context;

        public ListAdapter(Context context, List<String> objects) {
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

            TextView nameTxt = cell.findViewById(R.id.cCatSortTxt);
            nameTxt.setTypeface(Configs.titRegular);
            nameTxt.setText(categories.get(position));

            return cell;
        }

        @Override
        public int getCount() {
            return categories.size();
        }

        @Override
        public Object getItem(int position) {
            return categories.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }


}//@end
