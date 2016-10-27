package io.mile.mileio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by DHZ_Bill on 10/20/16.
 */
public class AdapterFillScreen extends ArrayAdapter<FillOweAmount>{
    public AdapterFillScreen(Context context, ArrayList<FillOweAmount> items) {
        super(context, 0, items);}

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Holder holder = new Holder();
            // Get the data item for this position
            holder.fillOweAmount = getItem(position);
            System.out.println(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_name_owe_amount, parent, false);
            }
            // I didn't figure out how to implement ButterKnife here.
            // Because all elements here is a property of the holder class
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.name.setTag(holder.fillOweAmount);
            holder.amount = (TextView) convertView.findViewById(R.id.amount);

            // Return the completed view to render on screen
            return convertView;
        }
        // set the TextView
        private void setupItem(Holder holder){
            holder.name.setText(holder.fillOweAmount.getName());
            holder.amount.setText(holder.fillOweAmount.getAmount());
        }
        public static class Holder{
            TextView name;
            TextView amount;
            FillOweAmount fillOweAmount;
        }
    }

