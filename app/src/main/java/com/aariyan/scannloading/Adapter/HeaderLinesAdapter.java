package com.aariyan.scannloading.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aariyan.scannloading.Activity.HeaderNLineActivity;
import com.aariyan.scannloading.Model.HeadersModel;
import com.aariyan.scannloading.R;

import java.util.List;

public class HeaderLinesAdapter extends RecyclerView.Adapter<HeaderLinesAdapter.ViewHolder> {

    private Context context;
    private List<HeadersModel> list;

    public HeaderLinesAdapter(Context context, List<HeadersModel> list) {
        this.context = context;
        this.list = list;
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView storeName, orderId, orderNo,messageInv,delAddress;

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
