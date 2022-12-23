package com.example.mobilecw;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TripAdapter extends BaseAdapter {

    private MainActivity context;
    private int layout;
    private List<Trip> tripList;

    public TripAdapter(MainActivity context, int layout, List<Trip> tripList) {
        this.context = context;
        this.layout = layout;
        this.tripList = tripList;
    }

    @Override
    public int getCount() {
        return tripList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class viewHolder{
        TextView txtName, txtDestination, txtDate;
        ImageView imgEdit, imgBin;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder holder;
        if(view == null){
            holder = new viewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);

            holder.txtName = view.findViewById(R.id.txtTripName);
            holder.txtDestination = view.findViewById(R.id.txtDestination);
            holder.txtDate = view.findViewById(R.id.txtDate);
            holder.imgEdit = view.findViewById(R.id.imgEdit);
            holder.imgBin = view.findViewById(R.id.imgDelete);
            view.setTag(holder);
        }else {
            holder = (viewHolder) view.getTag();
        }

        Trip trip = tripList.get(i);
        holder.txtName.setText(trip.getTripName());
        holder.txtDate.setText(trip.getDate());
        holder.txtDestination.setText(trip.getDestination());

        // bat su kien xoa va sua

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent EditPage = new Intent(context, Edit_Activity.class );
                EditPage.putExtra("tripName",trip.getTripName());
                EditPage.putExtra("tripId",trip.getId());
                EditPage.putExtra("tripDestination",trip.getDestination());
                EditPage.putExtra("tripDate",trip.getDate());
                EditPage.putExtra("tripRisk",trip.getRisk());
                EditPage.putExtra("tripDuration",trip.getDuration());
                EditPage.putExtra("tripDescription",trip.getDescription());
                EditPage.putExtra("tripVehicle",trip.getVehicle());
                context.startActivity(EditPage);
            }
        });

        holder.imgBin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogDeleteTrip(trip.getTripName(),trip.getId());
            }
        });
        return view;
    }
}
