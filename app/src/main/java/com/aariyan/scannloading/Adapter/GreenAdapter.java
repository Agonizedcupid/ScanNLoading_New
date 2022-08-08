package com.aariyan.scannloading.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aariyan.scannloading.Interface.UpdateLines;
import com.aariyan.scannloading.Model.LinesModel;
import com.aariyan.scannloading.R;

import java.util.List;

public class GreenAdapter extends RecyclerView.Adapter<GreenAdapter.ViewHolder> {

    private Context context;
    private List<LinesModel> list;
    private UpdateLines updateLines;

    public GreenAdapter(Context context, List<LinesModel> list, UpdateLines updateLines) {
        this.context = context;
        this.list = list;
        this.updateLines = updateLines;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinesModel model = list.get(position);
        holder.itemName.setText(model.getPastelDescription());
        holder.itemQuantity.setText(String.valueOf(model.getQtyOrdered()));
        holder.comments.setText(model.getComment());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                updateLines.clickForUpdate(model, position, "green", holder.shower);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView itemName,itemQuantity;
        private TextView comments;
        private View shower;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
            comments = itemView.findViewById(R.id.comments);
            shower = itemView.findViewById(R.id.shower);
        }
    }
}
