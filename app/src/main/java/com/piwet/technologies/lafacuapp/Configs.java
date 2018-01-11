package com.piwet.technologies.lafacuapp;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.location.Location;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by macbook on 9/01/18.
 */

public class Configs extends Application {


    // IMPORTANT: Replace the red strings below with your own App ID and Client Key of your app on back4app.com

    public static String PARSE_APP_ID = "w0oamFEcyaw2m5YzcCFBahUBUm7mxhpqy0lnJ7SN";
    public static String PARSE_CLIENT_KEY = "rRAz0utVLMhzZxWES5Cg3Ah2ttGgjufpRkMePwLR";

    public static Typeface qsBold, qsLight, qsRegular, titBlack, titLight, titRegular, titSemibold;

    public static String[] reportAdOptions = {
            "Producto Prohibido",
            "Falsificación",
            "Categoría incorrecta",
            "Desnudo / pornografía / contenido maduro",
            "Discurso de odio / chantaje",
    };
    // YOU CAN CHANGE THE USER REPORT OPTIONS BELOW AS YOU WISH
    public static String[] reportUserOptions = {
            "Intercambiando articulos prohibidos",
            "Artículos categorizados erróneamente",
            "Desnudo / pornografía / contenido maduro",
            "Discurso de odio / chantaje",
            "Sospechoso de fraude",
            "Retirado del trato",
            "Otros",
    };
    /***********  DO NOT EDIT THE CODE BELOW!! **********/

    public static String PATH_TO_PHP_FILE = "https://www.permutapp.com/report/";

    public static String USER_CLASS_NAME = "_User";
    public static String USER_USERNAME = "username";
    public static String USER_EMAIL = "email";
    public static String USER_EMAIL_VERIFIED = "emailVerified";
    public static String USER_FULLNAME = "fullName";
    public static String USER_AVATAR = "avatar";
    public static String USER_SALDO = "saldo";
    public static String USER_IS_REPORTED = "isReported";
    public static String USER_REPORT_MESSAGE = "reportMessage";
    public static String USER_HAS_BLOCKED = "hasBlocked";





    public static String CATEGORIES_CLASS_NAME = "Categories";
    public static String CATEGORIES_CATEGORY = "category";
    public static String CATEGORIES_IMAGE = "image";
    public static String ADS_CLASS_NAME = "Ads";
    public static String ADS_SELLER_POINTER = "sellerPointer";
    public static String ADS_LIKED_BY = "likedBy"; // Array
    public static String ADS_KEYWORDS = "keywords"; // Array
    public static String ADS_TITLE = "title";
    public static String ADS_PRICE = "price";
    public static String ADS_CURRENCY = "currency";
    public static String ADS_CATEGORY = "category";
    public static String ADS_LOCATION = "location";
    public static String ADS_IMAGE1 = "image1";
    public static String ADS_IMAGE2 = "image2";
    public static String ADS_IMAGE3 = "image3";
    public static String ADS_VIDEO = "video";
    public static String ADS_VIDEO_THUMBNAIL = "videoThumbnail";
    public static String ADS_DESCRIPTION = "description";
    public static String ADS_CONDITION = "condition";
    public static String ADS_LIKES = "likes";
    public static String ADS_COMMENTS = "comments";
    public static String ADS_IS_REPORTED = "isReported";
    public static String ADS_REPORT_MESSAGE = "reportMessage";
    public static String ADS_CREATED_AT = "createdAt";
    public static String ADS_CIUDAD = "ciudad";
    public static String ADS_PAIS = "pais";
    public static String LIKES_CLASS_NAME = "Likes";
    public static String LIKES_CURR_USER = "currUser";
    public static String LIKES_AD_LIKED = "adLiked";
    public static String LIKES_CREATED_AT = "createdAt";
    public static String COMMENTS_CLASS_NAME = "Comments";
    public static String COMMENTS_USER_POINTER = "userPointer";
    public static String COMMENTS_AD_POINTER = "adPointer";
    public static String COMMENTS_COMMENT = "comment";
    public static String COMMENTS_CREATED_AT = "createdAt";
    public static String ACTIVITY_CLASS_NAME = "Activity";
    public static String ACTIVITY_CURRENT_USER = "currUser";
    public static String ACTIVITY_OTHER_USER = "otherUser";
    public static String ACTIVITY_TEXT = "text";
    public static String ACTIVITY_CREATED_AT = "createdAt";
    public static String ACTIVITY_INTERCAMBIO = "Intercambio";
    public static String ACTIVITY_AD = "AdPointer";

    public static String INBOX_CLASS_NAME = "Inbox";
    public static String INBOX_AD_POINTER = "adPointer";
    public static String INBOX_SENDER = "sender";
    public static String INBOX_RECEIVER = "receiver";
    public static String INBOX_INBOX_ID = "inboxID";
    public static String INBOX_MESSAGE = "message";
    public static String INBOX_IMAGE = "image";
    public static String INBOX_CREATED_AT = "createdAt";
    public static String CHATS_CLASS_NAME = "Chats";
    public static String CHATS_LAST_MESSAGE = "lastMessage";
    public static String CHATS_USER_POINTER = "userPointer";
    public static String CHATS_OTHER_USER = "otherUser";
    public static String CHATS_ID = "chatID";
    public static String CHATS_AD_POINTER = "adPointer";
    public static String CHATS_CREATED_AT = "createdAt";
    public static String FEEDBACKS_CLASS_NAME = "Feedbacks";
    public static String FEEDBACKS_AD_TITLE = "adTitle";
    public static String FEEDBACKS_SELLER_POINTER = "sellerPointer";
    public static String FEEDBACKS_REVIEWER_POINTER = "reviewerPointer";
    public static String FEEDBACKS_STARS = "stars";
    public static String FEEDBACKS_TEXT = "text";
    public static String CHOOSE_ADS_CLASS = "ChooseAds";
    public static String CHOOSE_ADS1 = "OtherUserAd";
    public static String CHOOSE_ADS2 = "CurrentUserAd";
    public static String CHOOSE_USER = "OTHER_USER";
    public static String CHOOSE_CURRENT_USER = "CURRENT_USER";
    public static String CHOOSE_ADS_MESSAGE = "CHOOSE_ADS_MESSAGE";
    public static String CHOOSE_AD_NAME = "Current_AD_Name";
    public static String CHOOSE_AD_POINTER = "CHOOSE_AD_POINTER";
    public static String CHOOSE_CREATED_AT = "createdAt";
    public static String CHANGE_ADS_CLASS = "Change_ADS_CLASS";
    public static String CHANGE_USER = "OTHER_USER";
    public static String CHANGE_CURRENT_USER = "CURRENT_USER";
    public static String CHANGE_ADS_MESSAGE = "CHANGE_ADS_MESSAGE";
    public static String CHANGE_AD_NAME = "Current_AD_Name";

    public static String KEYWORD_CLASS = "REGISTRO_KEYWORD";
    public static String KEYWORD_BUSCADO = "KEYWORD";

    public static String REGISTRO_APP_CRASH_CLASS = "REGISTRO_APP_CRASH";
    public static String REGISTRO_MENSAJE = "mensaje";


    /* Global Variables */


    public static float distanceInMiles = (float) 20;
    public static String sortBy = "Reciente";
    public static String selectedCategory = "Todos";
    public static Location chosenLocation = null;
    private static Configs mInstance;
    boolean isParseInitialized = false;

    // MARK: - SCALE BITMAP TO MAX SIZE ---------------------------------------
    public static Bitmap scaleBitmapToMaxSize(int maxSize, Bitmap bm) {
        int outWidth;
        int outHeight;
        int inWidth = bm.getWidth();
        int inHeight = bm.getHeight();
        if (inWidth > inHeight) {
            outWidth = maxSize;
            outHeight = (inHeight * maxSize) / inWidth;
        } else {
            outHeight = maxSize;
            outWidth = (inWidth * maxSize) / inHeight;
        }
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bm, outWidth, outHeight, false);
        return resizedBitmap;
    }

    // MARK: - GET TIME AGO SINCE DATE ------------------------------------------------------------
    public static String timeAgoSinceDate(Date date) {
        String dateString = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss");
            String sentStr = (df.format(date));
            Date past = df.parse(sentStr);
            Date now = new Date();
            long seconds = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
            long minutes = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            long hours = TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            long days = TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());


            if (seconds < 60) {
                dateString = "Hace " + seconds + " segundos";
            } else if (minutes < 60) {
                dateString = "Hace " + minutes + " minutos";
            } else if (hours < 24) {
                dateString = "Hace " + hours + " horas";
            } else if (days < 28) {

                dateString = "Hace " + days + " dias";
            }else {

                long months = days / 30;

                int mont = (int) months;

                if (mont > 1 && mont < 12) {

                    dateString = "Hace " + mont + " meses";

                }else if(mont == 1){

                    dateString = "Hace " + mont + " mes";



                } else {

                    long year = mont / 12;
                    int yearr = (int) year;

                    if (yearr == 1 ){
                        dateString = "Hace " + yearr + " año";

                    } else if (yearr > 1) {
                        dateString = "Hace " + yearr + " años";
                    }

                }

            }
        } catch (Exception j) {
            j.printStackTrace();
        }
        return dateString;
    }

    // ROUND THOUSANDS NUMBERS ------------------------------------------
    public static String roundThousandsIntoK(Number number) {
        char[] suffix = {' ', 'k', 'M', 'B', 'T', 'P', 'E'};
        long numValue = number.longValue();
        int value = (int) Math.floor(Math.log10(numValue));
        int base = value / 3;
        if (value >= 3 && base < suffix.length) {
            return new DecimalFormat("#0.0").format(numValue / Math.pow(10, base * 3)) + suffix[base];
        } else {
            return new DecimalFormat("#,##0").format(numValue);
        }
    }

    // MARK: - SIMPLE ALERT ------------------------------
    public static void simpleAlert(String mess, Context activity) {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setMessage(mess)
                .setTitle(R.string.app_name)
                .setPositiveButton("OK", null)
                .setIcon(R.drawable.logo2);
        alert.create().show();
    }

    public static synchronized Configs getInstance() {
        return mInstance;
    }

    // MARK: - LOGIN ALERT ------------------------------
    public static void loginAlert(String mess, final Context activity) {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setMessage(mess)
                .setTitle(R.string.app_name)
                .setPositiveButton("Conectarse", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(activity, LoginManager.class);
                        activity.startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .setIcon(R.drawable.logo2);
        alert.create().show();
    }

    public void onCreate() {
        super.onCreate();

        mInstance = this;

        OneSignal.startInit(this)
                .autoPromptLocation(false) // default call promptLocation later
                .setNotificationReceivedHandler(new ExampleNotificationReceivedHandler())
                .setNotificationOpenedHandler(new ExampleNotificationOpenedHandler())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        OneSignal.promptLocation();

        if (!isParseInitialized) {
            Parse.initialize(new Parse.Configuration.Builder(this)
                    .applicationId(String.valueOf(PARSE_APP_ID))
                    .clientKey(String.valueOf(PARSE_CLIENT_KEY))
                    .server("https://parseapi.back4app.com")
                    .build()
            );
            Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);
            ParseUser.enableAutomaticUser();
            isParseInitialized = true;


        /*    // Init Facebook Utils
            ParseFacebookUtils.initialize(this);

            FacebookSdk.sdkInitialize(getApplicationContext());
            AppEventsLogger.activateApp(this);

            if (BuildConfig.DEBUG) {
                FacebookSdk.setIsDebugEnabled(true);
                FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
            }*/
        }

        // Init fonts
        qsBold = Typeface.createFromAsset(getAssets(), "font/Quicksand-Bold.ttf");
        qsLight = Typeface.createFromAsset(getAssets(), "font/Quicksand-Light.ttf");
        qsRegular = Typeface.createFromAsset(getAssets(), "font/Quicksand-Regular.ttf");
        titBlack = Typeface.createFromAsset(getAssets(), "font/Titillium-Black.otf");
        titLight = Typeface.createFromAsset(getAssets(), "font/Titillium-Light.otf");
        titRegular = Typeface.createFromAsset(getAssets(), "font/Titillium-Regular.otf");
        titSemibold = Typeface.createFromAsset(getAssets(), "font/Titillium-Semibold.otf");

    }// end onCreate()

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    private class ExampleNotificationReceivedHandler implements OneSignal.NotificationReceivedHandler {
        @Override
        public void notificationReceived(OSNotification notification) {
            JSONObject data = notification.payload.additionalData;
            String notificationID = notification.payload.notificationID;
            String title = notification.payload.title;

            String customKey;

            Log.i("OneSignalExample", "NotificationID received: " + notificationID);

            if (data != null) {
                customKey = data.optString("customkey", null);
                if (customKey != null)
                    Log.i("OneSignalExample", "customkey set with value: " + customKey);
            }
        }
    }

    private class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        // This fires when a notification is opened by tapping on it.
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotificationAction.ActionType actionType = result.action.type;
            JSONObject data = result.notification.payload.additionalData;
            String launchUrl = result.notification.payload.launchURL; // update docs launchUrl

            String customKey;
            String openURL = null;
            Object activityToLaunch = HOME.class;

            if (data != null) {
                customKey = data.optString("customkey", null);
                openURL = data.optString("openURL", null);

                if (customKey != null)
                    Log.i("OneSignalExample", "customkey set with value: " + customKey);

                if (openURL != null)
                    Log.i("OneSignalExample", "openURL to webview with URL value: " + openURL);
            }

            // The following can be used to open an Activity of your choice.
            // Replace - getApplicationContext() - with any Android Context.
            // Intent intent = new Intent(getApplicationContext(), YourActivity.class);
            Intent intent = new Intent(getApplicationContext(), HOME.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);

            // startActivity(intent);
            startActivity(intent);


        }
    }


}// @end
