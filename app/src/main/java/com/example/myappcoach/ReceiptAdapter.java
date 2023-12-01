package com.example.myappcoach;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Receipt> listReceipt;

    public ReceiptAdapter(Context context, ArrayList<Receipt> listReceipt) {
        this.context = context;
        this.listReceipt = listReceipt;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view   = LayoutInflater.from(context).inflate(R.layout.item_receipt, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Receipt receipt = listReceipt.get(position);
        if(receipt != null){
            holder.ngaylap.setText("Ngày " + receipt.getDay());
            holder.diemden.setText( "Điểm Đến " + receipt.getNoiden());
            holder.diemdi.setText( "Điểm Đi " + receipt.getNoidi());
            holder.soghe.setText( "Số Ghế " + receipt.getSoghe());
            holder.sotien.setText( "Tổng Tiền " + receipt.getTien()+"");
        }
        holder.btRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alerDialog = new AlertDialog.Builder(context);
                alerDialog.setTitle("Thông Báo!");
                alerDialog.setMessage("Bạn có muốn xóa hóa đơn này không?");
                alerDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listReceipt.remove(receipt);
                        notifyDataSetChanged();
                    }
                });
                alerDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alerDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listReceipt.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ngaylap, diemden, diemdi, sotien, soghe;
        private ImageButton btRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ngaylap = itemView.findViewById(R.id.textView_ngaylap);
            diemden = itemView.findViewById(R.id.textView_diemden);
            diemdi = itemView.findViewById(R.id.textView_diemdi);
            sotien = itemView.findViewById(R.id.textView_tien);
            soghe = itemView.findViewById(R.id.textView_soghe);
            btRemove = itemView.findViewById(R.id.imageButtonRemove);
        }
    }
}
