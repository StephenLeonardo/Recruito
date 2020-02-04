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

import id.ac.binus.recruito.R;
import id.ac.binus.recruito.models.StatusItem;

public class StatusAdapter extends ArrayAdapter<StatusItem>{
    public View getView(int Position, @Nullable View ConvertView, @NonNull ViewGroup Parent) {
        return initView(Position, ConvertView, Parent);
    }

    @Override
    public View getDropDownView(int Position, @Nullable View ConvertView, @NonNull ViewGroup Parent) {
        return initView(Position, ConvertView, Parent);
    }

    public StatusAdapter(@NonNull Context context, ArrayList<StatusItem> StatusList) {
        super(context, 0, StatusList);
    }

    private View initView(int Position, View ConvertView, ViewGroup Parent){
        if(ConvertView == null){
            ConvertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner_status_row, Parent, false
            );
        }

        TextView CategoryName = ConvertView.findViewById(R.id.text_view_status);

        StatusItem CurrentItem = getItem(Position);

        if(CurrentItem != null) {
            CategoryName.setText(CurrentItem.getStatusName());
        }

        return ConvertView;

    }
}
