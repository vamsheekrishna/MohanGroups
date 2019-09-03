package com.example.mybusinesstracker.services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import androidx.appcompat.widget.AppCompatTextView;

import com.example.mybusinesstracker.R;

class ServicesListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private ServicePOJO servicePOJO;
    ServicesListAdapter(Context context) {
        mContext = context ;
        servicePOJO = new ServicePOJO();
    }
    @Override
    public int getGroupCount() {
        return servicePOJO.getType().length;
    }

    @Override
    public int getChildrenCount(int i) {
        return servicePOJO.getTypeDetails().length;
    }

    @Override
    public Object getGroup(int i) {
        return servicePOJO.getType()[i];
    }

    @Override
    public Object getChild(int i, int i1) {
        return i;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.services_group_item, null);
            AppCompatTextView groupName = convertView.findViewById(R.id.service_group_name);
            groupName.setText(servicePOJO.getType()[i]);
        }
        return convertView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.expandable_group_list_item, null);
            AppCompatTextView typeItemHeader = convertView.findViewById(R.id.type_header);
            typeItemHeader.setText(servicePOJO.getTypeDetails()[i1]);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
