package com.beta.tacademy.hellomoneycustomer.listView.mainNavi;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beta.tacademy.hellomoneycustomer.R;

import java.util.ArrayList;

/**
 * Created by user on 2017. 12. 2..
 */
public class MainNaviAdapter extends BaseAdapter {
    private ArrayList<MainNaviItem> listViewItemList = new ArrayList<>() ;

    public MainNaviAdapter() {}

    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.main_navi_item, parent, false);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.content) ;

        MainNaviItem listViewItem = listViewItemList.get(position);

        titleTextView.setText(listViewItem.getContent());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    public void addItem(String content) {
        MainNaviItem item = new MainNaviItem(content);

        listViewItemList.add(item);
    }
}