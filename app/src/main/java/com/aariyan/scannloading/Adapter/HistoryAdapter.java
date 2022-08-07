package com.aariyan.scannloading.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aariyan.scannloading.Activity.HistoryRectifying;
import com.aariyan.scannloading.Interface.LinesRectifying;
import com.aariyan.scannloading.Model.LinesModel;
import com.aariyan.scannloading.R;
import com.aariyan.scannloading.utils.LinesHistoryImplemented;

import org.w3c.dom.Text;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context context;
    private List<LinesModel> list;

    public HistoryAdapter(Context context, List<LinesModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_hostory_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinesModel model = list.get(position);
        holder.itemQuantity.setText(String.valueOf(model.getQtyOrdered()));
        holder.itemName.setText(model.getPastelDescription());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //LinesRectifying linesRectifying = new HistoryRectifying();
                LinesRectifying linesRectifying = new LinesHistoryImplemented();
                linesRectifying.carry(model);
                context.startActivity(new Intent(context, HistoryRectifying.class));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView itemName, itemQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.iName);
            itemQuantity = itemView.findViewById(R.id.iQuantity);
        }
    }
}
