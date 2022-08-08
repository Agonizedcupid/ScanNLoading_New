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
import com.aariyan.scannloading.Model.HeadersModel;
import com.aariyan.scannloading.Model.LinesModel;
import com.aariyan.scannloading.R;

import java.util.List;

public class GreenAdapter extends RecyclerView.Adapter<GreenAdapter.ViewHolder> {

    private Context context;
    private List<HeadersModel> list;
    private UpdateLines updateLines;
    int quantity;
    String comment;

    public GreenAdapter(Context context, List<HeadersModel> list, UpdateLines updateLines, int quantity, String comment) {
        this.context = context;
        this.list = list;
        this.updateLines = updateLines;
        this.quantity = quantity;
        this.comment = comment;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HeadersModel model = list.get(position);
        holder.itemName.setText(model.getStoreName());
        holder.itemQuantity.setText(String.valueOf(quantity));
        holder.comments.setText(comment);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                updateLines.clickForUpdate(model, position, "green");
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
