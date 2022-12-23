package com.example.mobilecw;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ExpensiveAdapter extends BaseAdapter {

    private ItemDetail context;
    private int layout;
    private List<Expensive> ExpensiveList;

    public ExpensiveAdapter(ItemDetail context, int layout, List<Expensive> expensiveList) {
        this.context = context;
        this.layout = layout;
        this.ExpensiveList = expensiveList;
    }

    @Override
    public int getCount() {
        return ExpensiveList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class viewHolderExpensive{
        TextView txtName, txtAmount, txtDate, txtComment;
        ImageView imgBin;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        viewHolderExpensive holder;
        if(view == null){
            holder = new viewHolderExpensive();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);

            holder.txtName = view.findViewById(R.id.txtExpensiveName);
            holder.txtAmount = view.findViewById(R.id.txtExpensiveAmount);
            holder.txtDate = view.findViewById(R.id.txtExpensiveDate);
            holder.txtComment = view.findViewById(R.id.txtExpensiveComment);
            holder.imgBin = view.findViewById(R.id.imgDeleteExpensive);
            view.setTag(holder);
        }else {
            holder = (viewHolderExpensive) view.getTag();
        }

        Expensive expensive = ExpensiveList.get(i);
        holder.txtName.setText("Name: "+ expensive.getExpensiveName());
        holder.txtAmount.setText("Amount: "+ expensive.getAmount());
        holder.txtDate.setText("Date: "+ expensive.getDate());
        holder.txtComment.setText("Comment: "+ expensive.getComment());


        // bat su kien xoa

        holder.imgBin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogDeleteExpensive(expensive.getExpensiveName(),expensive.getId());
            }
        });

        return view;
    }
}
