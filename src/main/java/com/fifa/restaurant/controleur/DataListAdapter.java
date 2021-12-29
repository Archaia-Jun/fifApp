package com.fifa.restaurant.controleur;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fifa.restaurant.R;
import com.fifa.restaurant.modele.DataModel;

import java.util.List;

public class DataListAdapter extends ArrayAdapter <DataModel> {
    //la list des valeurs
    private List<DataModel> datas;

    // context de l'activité
    Context context;

    int resource;

    public DataListAdapter(@NonNull Context context, int resource, @NonNull List<DataModel> data) {
        super(context, resource, data);
        this.datas = data;
        this.context = context;
        this.resource = resource;
    }

    //retourne ListView Item en tant que View
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // nous devons avoir la vue du XML pour l'élément de liste
        // pour cela, il faut un layoutinflater
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //prend view
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(resource, null, false);

        //obtenir les éléments de la liste à partir de la vue
        TextView text_view_name = view.findViewById(R.id.order_name);
        TextView text_view_other_information = view.findViewById(R.id.other_information);

        DataModel value = datas.get(position);

        // ajoute les valeurs à la list item
        text_view_name.setText(value.toString());
        text_view_other_information.setText(value.getMeal() + " : " + value.getPrice());


        //retourne la view
        return view;
    }
}
