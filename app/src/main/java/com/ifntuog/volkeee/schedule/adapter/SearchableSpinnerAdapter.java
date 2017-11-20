package com.ifntuog.volkeee.schedule.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.ifntuog.volkeee.schedule.R;
import com.ifntuog.volkeee.schedule.model.Group;

import java.util.ArrayList;

/**
 * Created by volkeee on 11/7/17.
 */

public class SearchableSpinnerAdapter extends ArrayAdapter<Group> implements SpinnerAdapter, Filterable {
    private Context mContext;

    private ArrayList<Group> originalData;
    private ArrayList<Group> filteredData;

    public SearchableSpinnerAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Group> objects) {
        super(context, resource, objects);
        this.originalData = objects;
        this.filteredData = objects;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_spinner_textview, null);

        TextView textView = view.findViewById(R.id.item_spinner_textview);
        textView.setText(getItem(position).getName());
        textView.setPadding(8,8,8,8);

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_spinner_textview, null);

        TextView textView = view.findViewById(R.id.item_spinner_textview);
        textView.setText(getItem(position).getName());

        return view;
    }

    @Override
    public int getCount() {
        return filteredData.size();
    }

    @Nullable
    @Override
    public Group getItem(int position) {
        return filteredData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();

                if (charSequence == null || charSequence.length() == 0) {
                    results.values = originalData;
                    results.count = originalData.size();
                } else {
                    ArrayList<Group> filterResultsData = new ArrayList<>();

                    for (Group group : originalData) {
                        if(group.getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            filterResultsData.add(group);
                        }
                    }
                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }
                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredData = (ArrayList<Group>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
