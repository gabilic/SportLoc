package hr.foi.air.sportloc.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import hr.foi.air.core.SportModel;

public class SportArrayAdapter extends ArrayAdapter<SportModel> {

    private final SportModel[] values;

    public SportArrayAdapter(Context context, int resourceId, SportModel[] values) {
        super(context, resourceId, values);
        this.values = values;
    }

    @Override
    public int getCount(){
        return values.length;
    }

    @Override
    public SportModel getItem(int position){
        return values[position];
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(values[position].getName());

        return label;
    }

}
