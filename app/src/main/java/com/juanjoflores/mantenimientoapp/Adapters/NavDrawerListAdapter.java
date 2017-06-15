package com.juanjoflores.mantenimientoapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juanjoflores.mantenimientoapp.Items.NavDrawerItem;
import com.juanjoflores.mantenimientoapp.R;

import java.util.ArrayList;

/**
 * Created by Contabilidad 5 on 15/06/2017.
 */

public class NavDrawerListAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<NavDrawerItem> navDrawerItems;
	
	public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems){
		this.context = context;
		this.navDrawerItems = navDrawerItems;
	}

	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {		
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.item_drawer_list, null);
        }
         
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
		txtTitle.setTextColor(context.getResources().getColor(android.R.color.black));
        TextView txtCount = (TextView) convertView.findViewById(R.id.counter);
		RelativeLayout divider = (RelativeLayout) convertView .findViewById(R.id.divider);
         
        imgIcon.setImageResource(navDrawerItems.get(position).getIcon());        
        txtTitle.setText(navDrawerItems.get(position).getTitle());
        
        // displaying count
        // check whether it set visible or not
		if(position ==1 || position == 3){
			divider.setVisibility(View.VISIBLE);
		}
        if(navDrawerItems.get(position).getCounterVisibility()){
        	txtCount.setText(navDrawerItems.get(position).getCount());
        }else{
        	// hide the counter view
        	txtCount.setVisibility(View.GONE);
        }
        
        return convertView;
	}

}
