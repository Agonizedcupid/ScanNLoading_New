package com.aariyan.scannloading.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aariyan.scannloading.Constant.Constant;
import com.aariyan.scannloading.Interface.RedListChanger;
import com.aariyan.scannloading.Interface.UpdateLines;
import com.aariyan.scannloading.Model.HeadersModel;
import com.aariyan.scannloading.Model.LinesModel;
import com.aariyan.scannloading.R;

import java.util.List;

public class RedAdapter extends RecyclerView.Adapter<RedAdapter.ViewHolder> {

    private Context context;
    private List<HeadersModel> list;
    private RedListChanger redListChanger;
    int quantity;
    String comment;

    public RedAdapter(Context context, List<HeadersModel> list, RedListChanger redListChanger, int quantity, String comment) {
        this.context = context;
        this.list = list;
        this.redListChanger = redListChanger;
        this.quantity = quantity;
        this.comment = comment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_item, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HeadersModel model = list.get(position);
        holder.itemName.setText(model.getStoreName());
        //holder.itemQuantity.setText(String.valueOf(quantity));
//        for (int i=0; i<Constant.map.size(); i++) {
//            Log.d("VIEW_HOLDER", "onBindViewHolder: "+Constant.map.get(model.getOrderId()));
//        }

        try {
            holder.itemQuantity.setText(""+Constant.map.get(model.getOrderId()));
        } catch (Exception e) {
            Log.d("EXCEPTION_TESTING", "onBindViewHolder: " + e.getMessage());
        }

        holder.comments.setText(comment);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                redListChanger.clickForRedUpdate(model, position, "red");
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
