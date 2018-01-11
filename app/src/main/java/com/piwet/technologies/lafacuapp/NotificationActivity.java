package com.piwet.technologies.lafacuapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    /* Views */
    ProgressDialog pd;


    /* Variables */
    List<ParseObject> activityArray;
    String cambioMessage = "";
    String accept_chat = "";
    String like_comment = "";
    String comment = "";
    ParseObject adObj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Hide ActionBar
        getSupportActionBar().hide();


        // Init a ProgressDialog
        pd = new ProgressDialog(this);
        pd.setTitle(R.string.app_name);
        pd.setIndeterminate(false);
        pd.setIcon(R.drawable.logo);


        // Call query
        queryActivity();


    }// end onCreate()


    // MARK: - QUERY ACTIVITY ---------------------------------------------------------------
    void queryActivity() {
        pd.setMessage("Espere...");
        pd.show();

        ParseQuery query = new ParseQuery(Configs.ACTIVITY_CLASS_NAME);
        query.orderByDescending(Configs.ACTIVITY_CREATED_AT);
        query.whereEqualTo(Configs.ACTIVITY_CURRENT_USER, ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException error) {
                if (error == null) {
                    activityArray = objects;
                    pd.dismiss();

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
                                cell = inflater.inflate(R.layout.cell_activity, null);
                            }
                            final View finalCell = cell;

                            // Get Parse object
                            final ParseObject actObj = activityArray.get(position);


                            // Get userPointer
                            actObj.getParseObject(Configs.ACTIVITY_OTHER_USER).fetchIfNeededInBackground(new GetCallback<ParseObject>() {
                                public void done(ParseObject userPointer, ParseException e) {

                                    TextView actTxt = finalCell.findViewById(R.id.actTextTxt);
                                    actTxt.setTypeface(Configs.titRegular);
                                    actTxt.setText(actObj.getString(Configs.ACTIVITY_TEXT));

                                    // Get date
                                    TextView dateTxt = finalCell.findViewById(R.id.actDateTxt);
                                    dateTxt.setTypeface(Configs.titRegular);
                                    dateTxt.setText(Configs.timeAgoSinceDate(actObj.getCreatedAt()));

                                    // Get Avatar
                                    final ImageView avImage = finalCell.findViewById(R.id.actAvatarImg);
                                    ParseFile fileObject = (ParseFile) userPointer.get(Configs.USER_AVATAR);
                                    fileObject.getDataInBackground(new GetDataCallback() {
                                        public void done(byte[] data, ParseException error) {
                                            if (error == null) {

                                                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                                                if (bmp != null) {
                                                    avImage.setImageBitmap(bmp);
                                                }
                                            }
                                        }
                                    });


                                }
                            });// end userPointer


                            return cell;
                        }

                        @Override
                        public int getCount() {
                            return activityArray.size();
                        }

                        @Override
                        public Object getItem(int position) {
                            return activityArray.get(position);
                        }

                        @Override
                        public long getItemId(int position) {
                            return position;
                        }
                    }

                    // Init ListView and set its adapter
                    final ListView aList = findViewById(R.id.actListView);
                    aList.setAdapter(new ListAdapter(NotificationActivity.this, activityArray));


                    // MARK: - TAP A CELL TO SEE USER'S PROFILE -----------------------------------
                    aList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                            // Get Parse object
                            ParseObject actObj = activityArray.get(position);

                            String hold = actObj.getString(Configs.ACTIVITY_INTERCAMBIO);




                        }
                    });


                    // MARK: - LONG PRESS TO DELETE AN ACTIVITY ------------------------------------
                    aList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {

                            ParseObject actObj = activityArray.get(i);
                            actObj.deleteInBackground(new DeleteCallback() {
                                @Override
                                public void done(ParseException e) {
                                    activityArray.remove(i);
                                    aList.invalidateViews();
                                    aList.refreshDrawableState();
                                    Toast.makeText(NotificationActivity.this, "Eliminado", Toast.LENGTH_SHORT).show();
                                }
                            });

                            return false;
                        }
                    });

                    // Error in query
                } else {
                    Configs.simpleAlert(error.getMessage(), NotificationActivity.this);
                    pd.dismiss();
                }
            }
        });


    }



}//@end
