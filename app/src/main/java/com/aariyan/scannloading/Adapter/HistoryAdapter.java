package com.aariyan.scannloading.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aariyan.scannloading.Activity.HistoryRectifying;
import com.aariyan.scannloading.Constant.Constant;
import com.aariyan.scannloading.Database.DatabaseAdapter;
import com.aariyan.scannloading.Interface.LinesRectifying;
import com.aariyan.scannloading.Model.LinesModel;
import com.aariyan.scannloading.R;
import com.aariyan.scannloading.utils.LinesHistoryImplemented;

import org.w3c.dom.Text;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context context;
    private List<LinesModel> list;
    private DatabaseAdapter databaseAdapter;

    public HistoryAdapter(Context context, List<LinesModel> list) {
        this.context = context;
        this.list = list;
        databaseAdapter = new DatabaseAdapter(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_hostory_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinesModel model = list.get(position);
        //holder.itemQuantity.setText(String.valueOf(model.getQtyOrdered()));
        holder.itemQuantity.setText(String.valueOf(model.getTotalItem()));
        holder.itemName.setText(model.getPastelDescription());
        Log.d("TESTING_LOADED", "onBindViewHolder: "+model.getLoaded() + " : "+model.getPastelDescription());
        String name = model.getPastelDescription();

        boolean flag = false;
        List<LinesModel> linesList = databaseAdapter.getLinesByName(name);
        for (LinesModel lines : linesList) {
            if (lines.getLoaded() == 1) {
                flag = true;
            } else {
                flag = false;
                break;
            }
        }
        if (flag) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_green_dark));

        } else {
            holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_dark));
        }

//        if (model.getLoaded() == 0) {
//            holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_dark));
//        } else {
//            holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_green_dark));
//        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //LinesRectifying linesRectifying = new HistoryRectifying();
                LinesRectifying linesRectifying = new LinesHistoryImplemented();
                linesRectifying.carry(model);
                Constant.historyListPosition = position;
                context.startActivity(new Intent(context, HistoryRectifying.class).putExtra("id",model.getProductId())
                        .putExtra("name",model.getPastelDescription()));
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
