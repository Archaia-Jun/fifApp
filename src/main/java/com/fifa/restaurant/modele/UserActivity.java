package com.fifa.restaurant.modele;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.fifa.restaurant.controleur.ActivityLogin;
import com.fifa.restaurant.R;
import com.google.firebase.auth.FirebaseAuth;

public class UserActivity extends AppCompatActivity {
   // Button mOrderButton;                   // permettre la prise de commandes ou réservations
    //Button mBookingButton;
    Button btnNotification;
    Button btnLogOut;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

       // mOrderButton = (Button) findViewById(R.id.activity_main_order_button);
       // mBookingButton = (Button) findViewById(R.id.activity_main_booking_button);
        btnNotification = (Button) findViewById(R.id.activity_user_notification_button);

    /*
        mOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View orderView) {

                //démarrer une nouvelle activité "orderActivity", lié au contexte "MainActivity", ouvre la nouvelle activité "OrderActivity"
                Intent orderActivityIntent = new Intent(UserActivity.this, OrderActivity.class);
                startActivity(orderActivityIntent); // la méthode récupère les informations de l'Intent et démarre l'activité
            }
        });

        mBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View bookingView) {

                Intent bookingActivityIntent = new Intent(UserActivity.this, BookingActivity.class);
                startActivity(bookingActivityIntent);
            }
        });
 */

        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Intent I = new Intent(UserActivity.this, ActivityLogin.class);
                startActivity(I);

            }
        });

        btnNotification = (Button) findViewById(R.id.activity_user_notification_button);
        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent I = new Intent(UserActivity.this, ActivityLogin.class);
                startActivity(I);
            }
        });

    }
}
