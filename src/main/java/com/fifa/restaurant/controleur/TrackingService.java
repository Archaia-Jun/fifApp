package com.fifa.restaurant.controleur;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.fifa.restaurant.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TrackingService extends Service {

    private static final String TAG = TrackingService.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buildNotification();
        loginToFirebase();
    }

//Créer persistante notification

    private void buildNotification() {
        String stop = "stop";
        registerReceiver(stopReceiver, new IntentFilter(stop));
        PendingIntent broadcastIntent = PendingIntent.getBroadcast(
                this, 0, new Intent(stop), PendingIntent.FLAG_UPDATE_CURRENT);

// Créer persistante notification
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.tracking_enabled_notif))

//Rendre cette notification en cours afin qu'elle ne puisse pas être rejetée par l'utilisateur

                .setOngoing(true)
                .setContentIntent(broadcastIntent)
                .setSmallIcon(R.drawable.tracking_enabled);
        startForeground(1, builder.build());
    }

    protected BroadcastReceiver stopReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

//Annuler l'enregistrement de BroadcastReceiver lorsque la notification est exploitée

            unregisterReceiver(stopReceiver);

//Stop le Service//

            stopSelf();
        }
    };

    private void loginToFirebase() {

//Authentification avec Firebase,

        String email = getString(R.string.test_email);
        String password = getString(R.string.test_password);

//appel OnCompleteListener si user se connecte

        FirebaseAuth.getInstance().signInWithEmailAndPassword(
                email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {

//si user est authentifié

                if (task.isSuccessful()) {

//on appelle requestLocationUpdates

                    requestLocationUpdates();
                } else {

//si connection échoue, error

                    Log.d(TAG, "Firebase authentication failed");
                }
            }
        });
    }

// Lancer la demande pour suivre l'emplacement du périphérique

    private void requestLocationUpdates() {
        LocationRequest request = new LocationRequest();

//Spécifie la fréquence à laquelle votre application doit demander l’emplacement du périphérique
        request.setInterval(10000);

//obtiens la location la plus proche

        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        final String path = getString(R.string.firebase_path);
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

//si app a acces à location permission

        if (permission == PackageManager.PERMISSION_GRANTED) {

// MAJ requete location

            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {

//obtiens reference à database, pour que l'app puisse lire et écrire

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
                    Location location = locationResult.getLastLocation();
                    if (location != null) {

//Save données location dans database//

                        ref.setValue(location);
                    }
                }
            }, null);
        }
    }
}
