package com.example.welove.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class Location {
    /** ResultCallback 1.Permission, 2.LocationRequest */
    public void getLocationProcess(Context context, Fragment fragment, Activity activity, LocationCallback locationCallback) {
        /** 1.Permission Check */
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PermissionChecker.PERMISSION_GRANTED) {
                /** 2. GPS Chcek */
                if(isGpsEnabled(context)) {
                    getLocation(context, locationCallback);
                } else
                /** 3. GPS Off Request On */
                    requestLocationEnable(context, activity);
            } else {
                fragment.requestPermissions(new  String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
            }
        } else {
            /** 2. GPS Chcek */
            if(isGpsEnabled(context)) {
                getLocation(context, locationCallback);
            } else
            /** 3. GPS Off Request On */
                requestLocationEnable(context, activity);
        }
    }

    public void requestLocationEnable(Context context, final Activity activity) {
        SettingsClient settingsClient = LocationServices.getSettingsClient(context);
        LocationRequest locationRequest = LocationRequest.create()
                .setNumUpdates(5)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(2 * 2000);

        LocationSettingsRequest locationSettingsRequest =
                new LocationSettingsRequest.Builder()
                        .addLocationRequest(locationRequest)
                        .build();

        settingsClient.checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        ApiException exception = (ApiException)e;

                        if (exception.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                            ResolvableApiException resolvableApiException = (ResolvableApiException) exception;
                            try {
                                resolvableApiException.startResolutionForResult(activity, 3821);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });
        /*settingsClient
                .checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener { }
                .addOnFailureListener { exception ->
                val statusCode = (exception as ApiException).statusCode
            when (statusCode) {
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                    val resolvableApiException = (exception as ResolvableApiException)
                    resolvableApiException.startResolutionForResult(activity, REQUEST_CODE_GPS_ALERT)
                }
            }
        }*/
    }

    boolean isGpsEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    void getLocation(Context context, LocationCallback locationCallback) {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        LocationRequest locationRequest = LocationRequest.create()
                .setNumUpdates(1)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval((60 * 1000))
                .setFastestInterval(2 * 2000);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        } else {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        }
    }
}
