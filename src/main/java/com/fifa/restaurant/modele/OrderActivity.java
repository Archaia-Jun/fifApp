package com.fifa.restaurant.modele;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fifa.restaurant.BuildConfig;
import com.fifa.restaurant.R;
import com.fifa.restaurant.controleur.Order;
import com.fifa.restaurant.controleur.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class OrderActivity extends AppCompatActivity {

    // Pour passer une commande

    private static final String TAG = OrderActivity.class.getSimpleName();

    AlertDialog.Builder builder;
    private TextView txtDetails, inputNameItem, inputPriceItem, inputOrder;
    private EditText inputPhone;
    private Button btnSave;
    private MyCustomAdapter dataAdapter = null;
    private Double orderTotal = 0.00;
    static ArrayList<com.fifa.restaurant.controleur.Product> productList;
    private ArrayList<Order> orderFinalList;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private String orderId;
    private boolean json;
    private JsonElement products;
    private Object Product;
    private String jsonArrayString;
    private Object AlertDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //  name = String.valueOf((TextView) findViewById(R.id.productName));


        txtDetails = (TextView) findViewById(R.id.txt_order);
        btnSave = (Button) findViewById(R.id.save);
        builder = new AlertDialog.Builder(this);
        inputNameItem = (TextView) findViewById(R.id.txt_order);
        inputPriceItem = (TextView) findViewById(R.id.cartTotal);
        inputPhone = (EditText) findViewById(R.id.phone);
        inputOrder = (TextView) findViewById(R.id.order);
        // input = (EditText) findViewById(R.id.activity_booking_date_editText);
        final ArrayList<Product> productList = new ArrayList<Product>();

        mFirebaseInstance = FirebaseDatabase.getInstance();

        // obtenir référence au noeud 'orders'
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        // stock titre de l'app title sur noeud 'app_title'
        //mFirebaseInstance.getReference("app_title").setValue("Votre commande");

        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(BuildConfig.DEBUG) {


                    Log.e(TAG, "App title updated");
                }
                String appTitle = dataSnapshot.getValue(String.class);

                // MAJ titre barre d'outils
                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                if (BuildConfig.DEBUG) {


                    // Echec lecture valeur
                    Log.e(TAG, "Failed to read app title value.", error.toException());
                }
            }
        });

        // Save / MAJ user
        btnSave.setOnClickListener(new View.OnClickListener() {
            private ArrayList<Order> orderList;

            @Override
            public void onClick(View view) {

                //Décommentez le code ci-dessous pour définir le message et le titre du fichier strings.xml
                builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                //Paramétrer le message manuellement et effectuer une action au clic du bouton
                String allDescription ="";
                String orderCheckout = "";
                for(int i =  0; i< OrderActivity.productList.size() ; i++) {


                    Product p =  OrderActivity.productList.get(i);

                    if (p.getQuantity() >= 1) {
                        orderCheckout +=(p.getQuantity() + " " +  p.getProductName() + " " + p.getPrice() + " € \n");

                        String[] orderList = new String[] {orderCheckout};
                        Arrays.toString(orderList);
                        // allDescription = p.getProductName();
                    } else {
                        System.out.println(allDescription);
                    }


                    allDescription = orderCheckout; //+=( p.getDescription() + "\n");


                }

                builder.setMessage("Vous avez choisis: " + " \n\n" + orderCheckout + " \n Total commande: " + orderTotal + " €")
                        .setCancelable(false)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                Toast.makeText(getApplicationContext(),"Commande envoyée",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action pour bouton 'NON'
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),"Sélectionnez d'autres éléments:",
                                        Toast.LENGTH_SHORT).show();
                            }

                        });
                //Création dialog box
                AlertDialog alert = builder.create();
                //Paramétrer titre manuellement
                alert.setTitle("Confirmez la commande ?");
                alert.show();


        //inputNameItem = (TextView) inputNameItem.getText(); //TextView
                String products = inputNameItem.getText().toString();
                allDescription = inputNameItem.getText().toString();
                String price = inputPriceItem.getText().toString(); //EditText
                String phone = inputPhone.getText().toString();
                String orderFinalList = inputOrder.getText().toString();

                // Vérifie si orderId existe déja
                if (TextUtils.isEmpty(orderId)) {
                    createOrder(products, price, phone, orderFinalList);
                } else {
                    updateOrder(products, price, phone, orderFinalList);
                }
            }
        });

        toggleButton();

/*
        private void createOrder(String name, Double price) {
            // recherche de orderId par firebase auth
            if (TextUtils.isEmpty(orderId)) {
                orderId = mFirebaseDatabase.push().getKey();
            }

            // creation objet order
            Order order = new Order(name, price);

            //met user dans le noeud 'users' en utilisant userId
            mFirebaseDatabase.child(orderId).setValue(order);

            addOrderChangeListener();
        }*/
        //Generate list View from ArrayList
        displayListView();

      /*  btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, NextActivity.class);
                startActivity(intent);
            }
        });*/

    }


    private void toggleButton() {
        if (TextUtils.isEmpty(orderId)) {
            btnSave.setText("Save");
        } else {
            btnSave.setText("Update");
        }
    }

    /**
     * Création noeud new order sous 'orders'
     */
    private void createOrder(String products, String price, String phone, String orderFinalList) {
        // TODO
        // recherche de orderId par firebase auth
        if (TextUtils.isEmpty(orderId)) {
            orderId = mFirebaseDatabase.push().getKey();
        }


        Gson gson = new Gson();


        String json = gson.toJson(productList);
        String jsonOrder = gson.toJson(orderFinalList);

        // creation objet order
        Order order = new Order(json, price, phone, jsonOrder);


        Product[] arrayOfProducts = gson.fromJson(jsonArrayString, Product[].class);
        Order[] arrayOfOrder = gson.fromJson(jsonArrayString, Order[].class);



        //met order dans le noeud 'orders' en utilisant orderId
        mFirebaseDatabase.child(orderId).setValue(order);

        addOrderChangeListener();
    }

    /**
     * Order data change listener
     */
    private void addOrderChangeListener() {
        // Order data change listener
        mFirebaseDatabase.child(orderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Order order = dataSnapshot.getValue(Order.class);

                // Check for null
                if (order == null) {
                    if (BuildConfig.DEBUG) {


                        Log.e(TAG, "Order data is null!");
                        return;
                    }
                }

                if(BuildConfig.DEBUG) {


                    Log.e(TAG, "Order data is changed!" + order.products + ", " + order.price + ", " + order.phone + ", " + order.orderFinalList);

                }
                // affiche nouvele infos MAJ
                txtDetails.setText(order.products + ", " + order.price + ", " + order.phone + ", " + order.orderFinalList);



                // efface edit text
                inputNameItem.setText("");
                inputPriceItem.setText("");
                inputPhone.setText("");
                inputOrder.setText("");


                toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                if(BuildConfig.DEBUG) {

                    // échec lecture valeur
                    Log.e(TAG, "Failed to read order", error.toException());
                }
            }
        });
    }

    private void updateOrder(String products, String price, String phone, String orderFinalList) {
        // mise à jour order via les nœuds enfants
        if (!TextUtils.isEmpty(products))
            mFirebaseDatabase.child(orderId).child("products").setValue(products);

        if (!TextUtils.isEmpty(price))
            mFirebaseDatabase.child(orderId).child("price").setValue(price);

        if (!TextUtils.isEmpty(phone))
            mFirebaseDatabase.child(orderId).child("phone").setValue(phone);

        if (!TextUtils.isEmpty(orderFinalList))
            mFirebaseDatabase.child(orderId).child("orderFinalList").setValue(orderFinalList);
    }

    private void displayListView() {


        //Array list des produits
        this.productList = new ArrayList<Product>();
        Product product = new Product("Accras", "Beignets de morue", 06.00);
        productList.add(product);
        product = new Product("Patate crevettes", "", 06.00);
        productList.add(product);
        product = new Product("Avocat crevettes", "", 06.00);
        productList.add(product);
        product = new Product("Boudin Créole", "", 06.00);
        productList.add(product);
        product = new Product("Adjotin", "2 tiges Brochettes d'agneau ou veau", 06.00);
        productList.add(product);
        product = new Product("Salade verte", "", 05.00);
        productList.add(product);
        product = new Product("Salade composée", "", 12.00);
        productList.add(product);
        product = new Product("Coeur de veau", "", 06.00);
        productList.add(product);
        product = new Product("Arrachides vapeur", "", 01.50);
        productList.add(product);
        product = new Product("Poulet braisé", "", 14.00);
        productList.add(product);
        product = new Product("Pintade braisé", "", 16.00);
        productList.add(product);
        product = new Product("Brochettes garnies", "", 14.00);
        productList.add(product);
        product = new Product("Mafé Riz", "Sauce dakatine, boeuf", 14.00);
        productList.add(product);
        product = new Product("Saka-saka boeuf", "Sauce de la feuille de manioc", 15.00);
        productList.add(product);
        product = new Product("N'dolé", "Epinard Camerounais, boeuf", 15.00);
        productList.add(product);
        product = new Product("Gboman", "Epinard, boeuf", 16.00);
        productList.add(product);
        product = new Product("Gboman Spécial", "Man Tindjan", 22.00);
        productList.add(product);
        product = new Product("Gombo", "boeuf", 16.00);
        productList.add(product);
        product = new Product("Asro-Kouin", "", 18.00);
        productList.add(product);
        product = new Product("Yassa Poulet", "Sauce Oignons Citron", 16.00);
        productList.add(product);
        product = new Product("Amiwo au poulet", "Purée de farine de maïs", 18.00);
        productList.add(product);
        product = new Product("Ragoût de papates douces", "boeuf", 16.00);
        productList.add(product);
        product = new Product("Ragoût d'ignames", "boeuf", 16.00);
        productList.add(product);
        product = new Product("Poisson braisé", "Capitaine", 18.00);
        productList.add(product);
        product = new Product("Poisson braisé", "Daurade", 18.00);
        productList.add(product);
        product = new Product("Tilapia", "", 16.00);
        productList.add(product);
        product = new Product("Poisson frit", "", 20.00);
        productList.add(product);
        product = new Product("Tiep-djen", "riz au poisson Sénégalais", 18.00);
        productList.add(product);
        product = new Product("Gboman", "Poisson fumé", 20.00);
        productList.add(product);
        product = new Product("Crin-crin", "", 20.00);
        productList.add(product);
        product = new Product("Asro-Kouin", "Poisson fumé", 20.00);
        productList.add(product);
        product = new Product("Monyo", "Poisson braisé, salade de tomates fraîches", 20.00);
        productList.add(product);
        product = new Product("Yassa poisson", "Sauce Oignons Citron", 18.00);
        productList.add(product);
        product = new Product("Saka-saka poisson fumé", "Sauce de feuille de manioc", 18.00);
        productList.add(product);
        product = new Product("Gambas sautés", "", 18.00);
        productList.add(product);
        product = new Product("Plats composés", "", 25.00);
        productList.add(product);
        product = new Product("Menu enfant", "Poulet frites", 10.00);
        productList.add(product);
        product = new Product("Menu enfant", "Poulet riz", 10.00);
        productList.add(product);
        product = new Product("Menu enfant", "Brochettes frites", 10.00);
        productList.add(product);
        product = new Product("Menu enfant", "Brochettes riz", 10.00);
        productList.add(product);
        product = new Product("Télibo", "Farine de cossettes d'ignames", 5.00);
        productList.add(product);
        product = new Product("Akumé", "Purée de farine de maïs", 5.00);
        productList.add(product);
        product = new Product("Mankumé", "", 5.00);
        productList.add(product);
        product = new Product("Koliko", "Igname frit", 5.00);
        productList.add(product);
        product = new Product("Igname vapeur", "", 5.00);
        productList.add(product);
        product = new Product("Eba", "Purée de farine de manioc", 5.00);
        productList.add(product);
        product = new Product("Atièkè", "Couscous de manioc frais", 5.00);
        productList.add(product);
        product = new Product("Aloko", "Banane plantin frite", 5.00);
        productList.add(product);
        product = new Product("Kuanga", "", 5.00);
        productList.add(product);
        product = new Product("Miondo", "", 5.00);
        productList.add(product);
        product = new Product("Ablo", "Pain de maïs", 5.00);
        productList.add(product);
        product = new Product("Coupe de fruits tropicaux", "", 6.00);
        productList.add(product);
        product = new Product("Banane flambée", "", 6.00);
        productList.add(product);
        product = new Product("Glace", "Parfums exotiques", 70.00);
        productList.add(product);
        product = new Product("Mangue greffée", "", 6.00);
        productList.add(product);
        product = new Product("Sodabi", "Alcool du vin de palme", 4.00);
        productList.add(product);
        product = new Product("Lait de panthère", "Punch coco", 4.00);
        productList.add(product);
        product = new Product("Whisky", "", 4.00);
        productList.add(product);
        product = new Product("Johnie Walker", "", 6.00);
        productList.add(product);
        product = new Product("Chivas", "", 6.00);
        productList.add(product);
        product = new Product("Jack Daniels", "", 6.00);
        productList.add(product);
        product = new Product("Gin", "", 4.00);
        productList.add(product);
        product = new Product("Gin Tonic", "", 7.00);
        productList.add(product);
        product = new Product("Baileys", "", 4.00);
        productList.add(product);
        product = new Product("Martini Rouge", "", 4.00);
        productList.add(product);
        product = new Product("Martini Blanc", "", 4.00);
        productList.add(product);
        product = new Product("Porto", "", 4.00);
        productList.add(product);
        product = new Product("Ricard", "", 4.00);
        productList.add(product);
        product = new Product("Kir", "", 4.00);
        productList.add(product);
        product = new Product("Punch Citron", "", 5.00);
        productList.add(product);
        product = new Product("Punch Gingembre", "", 5.00);
        productList.add(product);
        product = new Product("Jus d'ananas", "", 3.00);
        productList.add(product);
        product = new Product("Jus d'orange", "", 3.00);
        productList.add(product);
        product = new Product("Coca-Cola", "", 3.00);
        productList.add(product);
        product = new Product("Jus de mangue", "", 4.00);
        productList.add(product);
        product = new Product("Jus de goyave", "", 5.00);
        productList.add(product);
        product = new Product("Malta Guinness", "Boisson non alcoolisée, malte d'orge", 5.00);
        productList.add(product);
        product = new Product("Jus de gingembre", "", 5.00);
        productList.add(product);
        product = new Product("Top 66cl", "Boisson gazeuse arômatisée", 7.00);
        productList.add(product);
        product = new Product("Moka 66cl", "Boisson gazeuse, arôme café", 9.00);
        productList.add(product);
        product = new Product("Fizzi 66cl", "Boisson gazeuse, arôme fruits tropicaux", 9.00);
        productList.add(product);
        product = new Product("Pom-Pom", "Boisson gazeuse, arôme pomme", 9.00);
        productList.add(product);
        product = new Product("Heineken 66cl", "Bière", 5.00);
        productList.add(product);
        product = new Product("Heineken 33cl", "Bière", 3.00);
        productList.add(product);
        product = new Product("1664 66cl", "Bière", 6.00);
        productList.add(product);
        product = new Product("1664 33cl", "Bière", 3.50);
        productList.add(product);
        product = new Product("Pelforth", "Bière brune", 7.00);
        productList.add(product);
        product = new Product("Desperado 66cl", "Bière", 7.00);
        productList.add(product);
        product = new Product("Desperado 33cl", "Bière", 5.00);
        productList.add(product);
        product = new Product("Leffe 66cl", "Bière", 7.00);
        productList.add(product);
        product = new Product("Leffe 33cl", "Bière", 5.00);
        productList.add(product);
        product = new Product("La Béninoise", "Bière", 9.00);
        productList.add(product);
        product = new Product("Flag", "Blière", 9.00);
        productList.add(product);
        product = new Product("Pils", "Bière", 9.00);
        productList.add(product);
        product = new Product("Lager", "Bière", 9.00);
        productList.add(product);
        product = new Product("Eku", "Bière", 9.00);
        productList.add(product);
        product = new Product("Awoyo", "Bière brune", 9.00);
        productList.add(product);
        product = new Product("Guinness", "Bière brune", 9.00);
        productList.add(product);
        product = new Product("33", "Bière", 9.00);
        productList.add(product);
        product = new Product("St-Emilion", "Vin rouge", 22.00);
        productList.add(product);
        product = new Product("St-Emilion", "1/2 btl", 12.00);
        productList.add(product);
        product = new Product("Mouton Cadet", "Vin rouge", 22.00);
        productList.add(product);
        product = new Product("Mouton Cadet", "1/2 btl", 12.00);
        productList.add(product);
        product = new Product("Bordeaux", "Vin rouge", 17.00);
        productList.add(product);
        product = new Product("Bordeaux", "1/2 btl", 9.00);
        productList.add(product);
        product = new Product("Gamay de Touraine", "Vin rouge", 17.00);
        productList.add(product);
        product = new Product("Gamay de Touraine", "1/2 btl", 9.00);
        productList.add(product);
        product = new Product("Côte du Rhône", "Vin rouge", 16.00);
        productList.add(product);
        product = new Product("Côte du Rhône", "1/2 btl", 8.80);
        productList.add(product);
        product = new Product("Beaujolais", "Vin rouge", 16.00);
        productList.add(product);
        product = new Product("Beaujolais", "1/2 btl", 8.80);
        productList.add(product);
        product = new Product("Tavel", "Vin rosé", 22.00);
        productList.add(product);
        product = new Product("Tavel", "1/2 btl", 12.00);
        productList.add(product);
        product = new Product("Rosé de Provence", "Vin rosé", 16.00);
        productList.add(product);
        product = new Product("Rosé de Provence", "1/2 btl", 9.00);
        productList.add(product);
        product = new Product("Cabernet d'anjou", "Vin rosé", 16.00);
        productList.add(product);
        product = new Product("Cabernet d'anjou", "1/2 btl", 9.00);
        productList.add(product);
        product = new Product("Bordeaux", "Vin blanc", 15.00);
        productList.add(product);
        product = new Product("Bordeaux", "1/2 btl", 8.00);
        productList.add(product);
        product = new Product("Sancerre", "Vin blanc", 22.00);
        productList.add(product);
        product = new Product("Sancerre", "1/2 btl", 12.00);
        productList.add(product);
        product = new Product("Muscadet", "Vin blanc", 15.00);
        productList.add(product);
        product = new Product("Muscadet", "1/2 btl", 8.00);
        productList.add(product);
        product = new Product("Laurent Perrier", "Champagne", 60.00);
        productList.add(product);
        product = new Product("Moët & Chandon", "Champagne", 60.00);
        productList.add(product);
        product = new Product("Autres Champagnes", "", 45.00);
        productList.add(product);
        product = new Product("Evian", "Eau minérale", 4.50);
        productList.add(product);
        product = new Product("Evian", "1/2 btl", 3.00);
        productList.add(product);
        product = new Product("Badoit", "Eau minérale gazeuse", 4.50);
        productList.add(product);
        product = new Product("Badoit", "1/2 btl", 3.00);
        productList.add(product);
        product = new Product("Perrier", "Eau minérale gazeuse", 4.50);
        productList.add(product);
        product = new Product("Perrier", "1/2 btl", 3.00);
        productList.add(product);
        product = new Product("San Pelegrino", "Eau minérale gazeuse", 4.50);
        productList.add(product);
        product = new Product("San Pelegrino", "1/2 btl", 3.00);
        productList.add(product);
        product = new Product("Cognac", "Digestif", 5.00);
        productList.add(product);
        product = new Product("Cointreau", "Digestif", 5.00);
        productList.add(product);
        product = new Product("Grand-Marnier", "Digestif", 5.00);
        productList.add(product);
        product = new Product("Marie-Brizard", "Digestif", 5.00);
        productList.add(product);
        product = new Product("Café", "", 2.00);
        productList.add(product);
        product = new Product("Thé", "", 2.00);
        productList.add(product);
        product = new Product("Infusion", "", 2.00);
        productList.add(product);

        //création d'un ArrayAdaptar à partir de l'Array String
        dataAdapter = new MyCustomAdapter(this, R.layout.order_row, productList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assigne l'Adapter à la ListView
        listView.setAdapter(dataAdapter);

        // instances Gson sont "Threads safe", possibilité de les réutiliser sur plusieurs threads.
        Gson gson = new Gson();

        // converion prrodctList en json
        String json = gson.toJson(productList);

        String jsonOrder = gson.toJson(orderFinalList);
        System.out.println(json);
        System.out.println(jsonOrder);



    }

    private class MyCustomAdapter extends ArrayAdapter<Product> {

        private ArrayList<Product> productList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Product> productList) {
            super(context, textViewResourceId, productList);
            this.productList = new ArrayList<Product>();
            this.productList.addAll(productList);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            DecimalFormat df = new DecimalFormat("0.00##");
            Product product = productList.get(position);

            if (view == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = vi.inflate(R.layout.order_row, null);
                EditText quantity = (EditText) view.findViewById(R.id.quantity);
                //attache l'écouteur TextWatcher à EditText
                quantity.addTextChangedListener(new MyTextWatcher(view));
                //  if (position % 2 == 0) {
                //    view.setBackgroundColor(Color.rgb(238, 233, 233));
                //}
            }

            EditText quantity = (EditText) view.findViewById(R.id.quantity);
            quantity.setTag(product);
            if (product.getQuantity() != 0) {
                quantity.setText(String.valueOf(product.getQuantity()));
            } else {
                quantity.setText("");
            }

            TextView productName = (TextView) view.findViewById(R.id.productName);
            productName.setText(product.getProductName());
            TextView description = (TextView) view.findViewById(R.id.description);
            description.setText(product.getDescription());
            TextView price = (TextView) view.findViewById(R.id.price);
            price.setText("€" + df.format(product.getPrice()));
            TextView ext = (TextView) view.findViewById(R.id.ext);
            if (product.getQuantity() != 0) {
                ext.setText("€" + df.format(product.getExt()));
            } else {
                ext.setText("");
            }

            return view;

        }

    }

    private class MyTextWatcher implements TextWatcher { // classe interne qui implemente methodes: beforeTextChanged(), onTextChanged(), afterTextChanged()

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // ne fait rien
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // ne fait rien
        }

        public void afterTextChanged(Editable s) {

            DecimalFormat df = new DecimalFormat("0.00##");
            String qtyString = s.toString().trim();
            int quantity = qtyString.equals("") ? 0 : Integer.valueOf(qtyString);

            EditText qtyView = (EditText) view.findViewById(R.id.quantity);
            Product product = (Product) qtyView.getTag();

            if (product.getQuantity() != quantity) {

                Double currPrice = product.getExt();
                Double extPrice = quantity * product.getPrice();
                Double priceDiff = Double.valueOf(df.format(extPrice - currPrice).replace(',', '.')); // pour éviter problèmes (',' et '.') à  la compilation

                product.setQuantity(quantity);
                product.setExt(extPrice);

                TextView ext = (TextView) view.findViewById(R.id.ext);
                if (product.getQuantity() != 0) {
                    ext.setText("€" + df.format(product.getExt()));
                } else {
                    ext.setText("");
                }

                if (product.getQuantity() != 0) {
                    qtyView.setText(String.valueOf(product.getQuantity()));
                } else {
                    qtyView.setText("");
                }

                orderTotal += priceDiff;
                TextView cartTotal = (TextView) findViewById(R.id.cartTotal);
                cartTotal.setText(df.format(orderTotal));

            }


        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_order, menu);
        return true;
    }

    private class orderList {
    }
}
