package com.piwet.technologies.lafacuapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by macbook on 9/01/18.
 */

public class MarshMallowPermission {

    Activity activity;

    public MarshMallowPermission(Activity activity) {
        this.activity = activity;
    }


    // CHECK PERMISSIONS -------------------------------------------------------------------------
    public boolean checkPermissionForLocation() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkPermissionForRecord() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkPermissionForReadExternalStorage() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkPermissionForWriteExternalStorage() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkPermissionForCamera() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }


    // REQUEST PERMISSIONS -------------------------------------------------------------------------
    public void requestPermissionForLocation() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Toast.makeText(activity, "Location permission is needed to search Ads nearby your location. Please enable Location in App Settings.", Toast.LENGTH_LONG).show();
            Configs.simpleAlert("El permiso de ubicación es necesario para buscar anuncios cercanos a su ubicación. Activa la ubicación en la configuración de la aplicación, de lo contrario solo recibirás anuncios de la ciudad de Buenos Aires.", activity);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    public void requestPermissionForRecord() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.RECORD_AUDIO)) {
            Toast.makeText(activity, "Se necesita permiso necesario para la grabación. Permitir en la configuración de la aplicación funciones adicionales.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, 2);
        }
    }


    public void requestPermissionForReadExternalStorage() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(activity, "Se necesita permiso de almacenamiento externo. Por favor, permite en la configuración de la aplicación funcionalidad adicional.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
        }
    }

    public void requestPermissionForWriteExternalStorage() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(activity, "Se necesita permiso de almacenamiento externo. Por favor, permite en la configuración de la aplicación funcionalidad adicional.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 4);
        }
    }

    public void requestPermissionForCamera() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
            Toast.makeText(activity, "Se necesita permiso de cámara. Por favor, permite en la configuración de la aplicación funcionalidad adicional.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 5);
        }
    }


}//@end
