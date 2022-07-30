package com.aariyan.scannloading.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.aariyan.scannloading.Activity.HeaderNLineActivity;
import com.aariyan.scannloading.Constant.Constant;
import com.aariyan.scannloading.Interface.QuantityUpdater;
import com.aariyan.scannloading.Interface.SingleClickUpdate;
import com.aariyan.scannloading.Model.LinesModel;
import com.aariyan.scannloading.R;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class HeaderNLineAdapter extends RecyclerView.Adapter<HeaderNLineAdapter.ViewHolder> {

    private Context context;
    private List<LinesModel> list;
    private QuantityUpdater quantityUpdater;
    private SingleClickUpdate singleClickUpdate;

    public HeaderNLineAdapter(Context context, List<LinesModel> list, QuantityUpdater updater, SingleClickUpdate singleClickUpdate) {
        this.context = context;
        this.list = list;
        this.quantityUpdater = updater;
        this.singleClickUpdate = singleClickUpdate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_recycler_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinesModel model = list.get(position);
        holder.storeName.setText(model.getPastelDescription());
        holder.itemQuantity.setText("" + model.getQty());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                quantityUpdater.onClick(model.getOrderId(), model.getOrderDetailId(), Constant.userId,
                        model.getLoaded(), model.getQty(), Constant.getDate(), Constant.types[0], model.getPrice(), model.getPastelDescription());
                return false;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClickUpdate.onSingleClick(model.getOrderId(), model.getOrderDetailId(), Constant.userId,
                        model.getLoaded(), model.getQty(), Constant.getDate(), Constant.types[0], model.getPrice(), model.getPastelDescription());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView storeName, itemQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            storeName = itemView.findViewById(R.id.itemName);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
        }
    }
}
