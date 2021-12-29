package com.fifa.restaurant.modele;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fifa.restaurant.controleur.Order;
import com.fifa.restaurant.R;
import com.fifa.restaurant.vue.CustomAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class NextActivity extends AppCompatActivity {

    private static final String TAG = NextActivity.class.getSimpleName();

    private TextView tv;
    private TextView txtTotal;

    private Button btnSave;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private int total;

    private String mOrder, mPrice;
    private CharSequence orderId;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        // Affiche icône barre d'outils
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        txtTotal = (TextView) findViewById(R.id.cartTotal);

        btnSave = (Button) findViewById(R.id.btn_save);

        tv = (TextView) findViewById(R.id.tv);

        for (int i = 0; i < CustomAdapter.modelArrayList.size(); i++){
            if(CustomAdapter.modelArrayList.get(i).getSelected()) {
                tv.setText(tv.getText() + " \r\n" + CustomAdapter.modelArrayList.get(i).getMeal());
            }
        }

        // Save / MAJ order
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = tv.getText().toString();
                Double price = Double.valueOf(Integer.valueOf(mPrice.getBytes().toString()));

                // Vérifie si orderId existe déjà
                if (TextUtils.isEmpty(orderId)) {
                    createOrder(name, price);
                } else {
                    updateOrder(name, price);
                }


            }
        });

        toggleButton();
    }

    private void updateOrder(String name, Double price) {
    }

    // Change bouton text
    private void toggleButton() {
        if (TextUtils.isEmpty(orderId)) {
            btnSave.setText("Save");
        } else {
            btnSave.setText("Update");
        }
    }

    private void createOrder(String name, Double price) {
        // TODO
        // In real apps this orderId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(orderId)) {
            orderId = mFirebaseDatabase.push().getKey();
        }

       // Order order = new Order(name, price);
        // creation objet Order
        //Order order = new Order(true, meal, price);

        //met user dans la collection 'users' en utilisant orderId
       // mFirebaseDatabase.child((String) orderId).setValue(order);

        addOrderChangeListener();

    }

    /**
     * Order data change listener
     */
    private void addOrderChangeListener() {
        // Order data change listener
        mFirebaseDatabase.child((String) orderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Order order = dataSnapshot.getValue(Order.class);

                // Check for null
                if (order == null) {
                    Log.e(TAG, "Order data is null!");
                    return;
                }

                Log.e(TAG, "Order data is changed!" + order.products + ", " + order.price);

                // Affiche MAJ produits et prix
                tv.setText(order.products + ", " + order.price);


                // efface edit text
                tv.setText("");
                // mPrice.setText("");

                toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Echec lecture valeur
                Log.e(TAG, "Failed to read order", error.toException());
            }
        });
    }

    private void updateOrder(String meal, String price) {
        // MAJ order via noeuds enfants
        if (!TextUtils.isEmpty(meal))
            mFirebaseDatabase.child((String) orderId).child("name").setValue(meal);

        if (!TextUtils.isEmpty(price))
            mFirebaseDatabase.child(String.valueOf(orderId)).child("price").setValue(price);

    }



}


