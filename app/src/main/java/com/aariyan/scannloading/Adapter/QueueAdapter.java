package com.aariyan.scannloading.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aariyan.scannloading.Model.QueueModel;
import com.aariyan.scannloading.R;

import java.util.List;

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.ViewHolder> {

    private Context context;
    private List<QueueModel> list;
    public QueueAdapter(Context context, List<QueueModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_queue_item,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QueueModel model = list.get(position);
        holder.primaryKey.setText(String.valueOf(model.getIds()));
        holder.itemName.setText(model.getInstrunctions());
        holder.itemQuantity.setText(model.getTypes());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView primaryKey,itemName,itemQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            primaryKey = itemView.findViewById(R.id.primaryKey);
            itemName = itemView.findViewById(R.id.item);
            itemQuantity = itemView.findViewById(R.id.quantity);
        }
    }
}
