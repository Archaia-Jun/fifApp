package com.fifa.restaurant.modele;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fifa.restaurant.BuildConfig;
import com.fifa.restaurant.R;
import com.fifa.restaurant.controleur.Booking;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookingActivity extends AppCompatActivity {

    private static final String TAG = BookingActivity.class.getSimpleName();
    private TextView txtDetails;
    private EditText inputSeat, inputDate, inputHour, inputNote, inputPhone;
    private Button btnSave;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private String bookingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Pour les réservations
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Displaying toolbar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        txtDetails = (TextView) findViewById(R.id.txt_booking);
        inputSeat = (EditText) findViewById(R.id.activity_booking_seat_editText);
        inputDate = (EditText) findViewById(R.id.activity_booking_date_editText);
        inputHour = (EditText) findViewById(R.id.activity_booking_hour_editText);
        inputNote = (EditText) findViewById(R.id.activity_booking_note_editText);
        inputPhone = (EditText) findViewById(R.id.phone);

        btnSave = (Button) findViewById(R.id.activity_booking_validateBooking_button);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        // obtenir référence au noeud 'users'
        mFirebaseDatabase = mFirebaseInstance.getReference("users").child("bookings");


        // stock title sur noeud 'app_title'
        mFirebaseInstance.getReference("app_title").setValue("Votre réservation");

        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(BuildConfig.DEBUG) {
                    //Si on se trouve en version debug, alors on affiche des messages dans le Logcat

                    Log.e(TAG, "App title updated");
                }

                String appTitle = dataSnapshot.getValue(String.class);

                // update toolbar title
                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                if(BuildConfig.DEBUG) {
                    // Failed to read value
                    Log.e(TAG, "Failed to read app title value.", error.toException());
                }
            }
        });

        // Save / MAJ user
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String seat = inputSeat.getText().toString();
                String date = inputDate.getText().toString();
                String hour = inputHour.getText().toString();
                String note = inputNote.getText().toString();
                String phone = inputPhone.getText().toString();

                // Vérifie si bookingId existe
                if (TextUtils.isEmpty(bookingId)) {
                    createBooking(seat, date, hour, note, phone);
                } else {
                    updateBooking(seat, date, hour, note, phone);
                }
            }
        });

        toggleButton();
    }

    // change bouton text
    private void toggleButton() {
        if (TextUtils.isEmpty(bookingId)) {
            btnSave.setText("Save");
        } else {
            btnSave.setText("Update");
        }
    }

    /**
     * Création noeud new user sous 'users'
     */
    private void createBooking(String seat, String date, String hour, String note, String phone) {
        // TODO
        // recherche de bookingId par firebase auth
        if (TextUtils.isEmpty(bookingId)) {
            bookingId = mFirebaseDatabase.push().getKey();
        }

        // creation objet user
        Booking booking = new Booking(seat, date, hour, note, phone);

        //met user dans le noeud 'users' en utilisant bookingId
        mFirebaseDatabase.child(bookingId).setValue(booking);

        addBookingChangeListener();
    }


    private void addBookingChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(bookingId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Booking booking = dataSnapshot.getValue(Booking.class);

                // Check for null
                if (booking == null) {
                    if(BuildConfig.DEBUG) {
                        Log.e(TAG, "Booking data is null!");
                    }
                    return;
                }

                if(BuildConfig.DEBUG) {
                    Log.e(TAG, "Booking data is changed!" + booking.seat + ", " + booking.date + ", " + booking.hour + ", " + booking.note + ", " + booking.phone);

                }
                // affiche nom et date MAJ
                txtDetails.setText(booking.seat + "places, " + booking.date + ", " + booking.hour + ", " + booking.note + ", " + booking.phone);

                // effacement edit text
                inputSeat.setText("");
                inputDate.setText("");
                inputHour.setText("");
                inputNote.setText("");
                inputPhone.setText("");

                toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                if(BuildConfig.DEBUG) {
                    // Echec lecture valeur
                    Log.e(TAG, "Failed to read booking", error.toException());
                }
            }
        });
    }

    private void updateBooking(String seat, String date, String hour, String note, String phone) {
        // MAJ user via noeuds enfants
        if (!TextUtils.isEmpty(seat))
            mFirebaseDatabase.child(bookingId).child("seat").setValue(seat);

        if (!TextUtils.isEmpty(date))
            mFirebaseDatabase.child(bookingId).child("date").setValue(date);

        if (!TextUtils.isEmpty(hour))
            mFirebaseDatabase.child(bookingId).child("hour").setValue(hour);

        if (!TextUtils.isEmpty(note))
            mFirebaseDatabase.child(bookingId).child("note").setValue(note);

        if (!TextUtils.isEmpty(phone))
            mFirebaseDatabase.child(bookingId).child("phone").setValue(phone);
    }
}
