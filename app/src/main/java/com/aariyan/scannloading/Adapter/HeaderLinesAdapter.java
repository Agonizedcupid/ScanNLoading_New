package com.aariyan.scannloading.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aariyan.scannloading.Activity.HeaderNLineActivity;
import com.aariyan.scannloading.Activity.Home;
import com.aariyan.scannloading.Database.DatabaseAdapter;
import com.aariyan.scannloading.Model.HeadersModel;
import com.aariyan.scannloading.Model.LinesModel;
import com.aariyan.scannloading.R;

import java.util.List;

public class HeaderLinesAdapter extends RecyclerView.Adapter<HeaderLinesAdapter.ViewHolder> {

    private Context context;
    private List<HeadersModel> list;
    DatabaseAdapter databaseAdapter;
    int count0, count1, count2;

    public HeaderLinesAdapter(Context context, List<HeadersModel> list) {
        this.context = context;
        this.list = list;
        databaseAdapter = new DatabaseAdapter(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_header_lines_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HeadersModel model = list.get(position);
        holder.storeName.setText(model.getStoreName());
        holder.orderId.setText(String.format(" # %s", model.getOrderId()));

        holder.delAddress.setText(model.getDeladdress());
        holder.messageInv.setText(model.getMESSAGESINV());
        holder.orderNo.setText(model.getOrderNo());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Home.position = position;
                Intent intent = new Intent(context, HeaderNLineActivity.class);
                intent.putExtra("orderId", model.getOrderId());
                intent.putExtra("storeName", model.getStoreName());
                intent.putExtra("orderNumber", model.getOrderNo());
                intent.putExtra("createdBy", "");
                intent.putExtra("orderDate", model.getOrderDate());
                intent.putExtra("invoiceNo", model.getInvoiceNo());
                intent.putExtra("address", model.getDeladdress());
                intent.putExtra("value", model.getValue());
                intent.putExtra("position", position);
                context.startActivity(intent);
                return true;
            }
        });
        //Black
        int countZero = databaseAdapter.countZero(model.getOrderId());
        //Green
        int countOne = databaseAdapter.countOne(model.getOrderId());
        //Red
        int countTwo = databaseAdapter.countTwo(model.getOrderId());

        if (countOne == list.size()) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_green_dark));
        }
        if (countTwo > 0) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_dark));
        } else if (countOne == 0) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.white));
        } else if (countZero > 0 && countOne > 0) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.yellow));
        }



//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//                List<LinesModel> list = databaseAdapter.getLinesByDateRouteNameOrderTypes(model.getOrderId());
//                int count = databaseAdapter.countZero(model.getOrderId());
//                Log.d("COUNT_VALUE", "run: " + count);
//                for (int i = 0; i < list.size(); i++) {
//                    Log.d("FLAG_TEST", "run: " + list.get(i).getFlag());
//                }
//                for (int i = 0; i < list.size(); i++) {
//                    //LinesModel linesModel = list.get(position);
//                    //Black
//                    if (list.get(i).getFlag() == 0) {
//                        count0++;
//                    }
//                    //Green
//                    if (list.get(i).getFlag() == 1) {
//                        count1++;
//                    }
//                    //RED
//                    if (list.get(i).getFlag() == 2) {
//                        count2++;
//                    }
//                }
//                Log.d("LIST_SIZE", "run: " + list.size() + ", " + count0 + "," + count1 + "," + count2);
//                if (count1 == list.size()) {
//                    //Log.d("LIST_SIZE", "run: " + list.size() + ", " + count0 + "," + count1 + "," + count2);
//                    holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_green_dark));
//                }
//                if (count1 == 0) {
//                    holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.white));
//                }
//                if (count0 > 0 && count1 > 0) {
//                    holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.yellow));
//                }
//                if (count2 > 0) {
//                    holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_dark));
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView storeName, orderId, orderNo, messageInv, delAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            storeName = itemView.findViewById(R.id.storeName);
            orderId = itemView.findViewById(R.id.orderId);
            orderNo = itemView.findViewById(R.id.orderNo);
            messageInv = itemView.findViewById(R.id.messageINV);
            delAddress = itemView.findViewById(R.id.deladdress);
        }
    }
}
