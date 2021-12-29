package com.fifa.restaurant.vue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fifa.restaurant.R;

import java.util.ArrayList;

/**
 *
 */
public class CustomAdapter extends BaseAdapter {

    private Context context;
    public static ArrayList<Model> modelArrayList;


    public CustomAdapter(Context context, ArrayList<Model> modelArrayList) {

        this.context = context;
        this.modelArrayList = modelArrayList;

    }

    public CustomAdapter(ArrayList<Model> models, Context applicationContext) {
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return modelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return modelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_item, null, true);

            holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb);
            holder.tvMeal = (TextView) convertView.findViewById(R.id.meal);


            convertView.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder) convertView.getTag();
        }


        holder.checkBox.setText("Checkbox " + position);

        holder.tvMeal.setText(modelArrayList.get(position).getMeal());

        holder.checkBox.setChecked(modelArrayList.get(position).getSelected());

        holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.checkBox.setTag(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                View tempview = (View) holder.checkBox.getTag(R.integer.btnplusview);
                Integer pos = (Integer) holder.checkBox.getTag();

                //Toast.makeText(context, "Elément ajouté à la commande!", Toast.LENGTH_SHORT).show();
                 //Toast.makeText(context, "Checkbox " + pos + " clicked!", Toast.LENGTH_SHORT).show();

                if (modelArrayList.get(pos).getSelected()) {
                    modelArrayList.get(pos).setPrice(false);
                } else {
                    modelArrayList.get(pos).setSelected(true);
                }

            }
        });

        return convertView;
    }

    private class ViewHolder {

        protected CheckBox checkBox;
        private TextView tvMeal;

    }

}
