package id.ac.binus.recruito.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import id.ac.binus.recruito.models.LocationItem;
import id.ac.binus.recruito.R;

public class LocationAdapter extends ArrayAdapter<LocationItem> {
    @NonNull
    @Override
    public View getView(int Position, @Nullable View ConvertView, @NonNull ViewGroup Parent) {
        return initView(Position, ConvertView, Parent);
    }

    @Override
    public View getDropDownView(int Position, @Nullable View ConvertView, @NonNull ViewGroup Parent) {
        return initView(Position, ConvertView, Parent);
    }

    public LocationAdapter(@NonNull Context context, ArrayList<LocationItem> locationItemArrayList) {
        super(context, 0, locationItemArrayList);
    }


    private View initView(int Position, View ConvertView, ViewGroup Parent){
        if(ConvertView == null){
            ConvertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner_location_row, Parent, false
            );
        }

        TextView LocationName = ConvertView.findViewById(R.id.text_view_location);

        LocationItem CurrentItem = getItem(Position);

        if(CurrentItem != null) {
            LocationName.setText(CurrentItem.getLocationName());
        }

        return ConvertView;

    }
}
