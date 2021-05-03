package com.espeedboat.admin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.espeedboat.admin.R;
import com.espeedboat.admin.model.Dropdown;

import java.util.List;

public class DropdownAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<Dropdown> lists;

    public DropdownAdapter(Context context, List<Dropdown> lists) {
        super(context, R.layout.item_dropdown);
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return lists.get(position).getText();
    }

    @Override
    public long getItemId(int position) {
        return lists.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.item_dropdown, parent, false);

        TextView tvDropdown = (TextView) convertView.findViewById(R.id.text_dropdown);
        tvDropdown.setText(lists.get(position).getText());

        return convertView;
    }
}
