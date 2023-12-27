package com.example.quanlykhohang.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlykhohang.Dao.TheLoaiDao;
import com.example.quanlykhohang.Model.TheLoai;
import com.example.quanlykhohang.R;

import java.util.ArrayList;


public class TheLoaiAdapter extends RecyclerView.Adapter<TheLoaiAdapter.viewholer> {
    Context context;
    private final ArrayList<TheLoai> listtl;
    TheLoaiDao theLoaiDao;

    public TheLoaiAdapter(Context context, ArrayList<TheLoai> listtl) {
        this.listtl = listtl;
        this.context = context;
        theLoaiDao = new TheLoaiDao(context);
    }

    @NonNull
    @Override
    public viewholer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_theloai, null);
        return new viewholer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholer holder, int position) {
        holder.tvMaTl.setText(String.valueOf(listtl.get(position).getMaTheLoai()));
        holder.tvTenTl.setText(listtl.get(position).getTenTheLoai());
        TheLoai tl = listtl.get(position);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo");//set tiêu đề
                builder.setIcon(R.drawable.baseline_warning_24);//set icon
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (theLoaiDao.deleteTheLoai(tl.getMaTheLoai())) {
                            listtl.clear();
                            listtl.addAll(theLoaiDao.getAll());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Delete Succ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Delete Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Huỷ xoá", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogUpdate(tl);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listtl.size();
    }

    public class viewholer extends RecyclerView.ViewHolder {
        TextView tvMaTl, tvTenTl;
        ImageView btnUpdate, btnDelete;

        public viewholer(@NonNull View itemView) {
            super(itemView);
            tvMaTl = itemView.findViewById(R.id.tvMaTl_itemTheLoai);
            tvTenTl = itemView.findViewById(R.id.tvTenTl_itemTheLoai);
            btnUpdate = itemView.findViewById(R.id.btnUpdate_theLoai);
            btnDelete = itemView.findViewById(R.id.btnDelete_theLoai);
        }
    }

    public void openDialogUpdate(TheLoai tl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_update_theloai, null);
        builder.setView(view);//gán view vào hôp thoại
        Dialog dialog = builder.create();//tạo hộp thoại
        dialog.show();
        EditText maLoai = view.findViewById(R.id.edtMaLoai_itemUpTheLoai);
        EditText tenLoai = view.findViewById(R.id.edtTenTheLoai_itemUpTheLoai);
        ImageView btnSave = view.findViewById(R.id.update_theLoai);
        ImageView btnHuy = view.findViewById(R.id.huyUpdate_theLoai);
//gán dl
        maLoai.setText(String.valueOf(tl.getMaTheLoai()));
        tenLoai.setText(tl.getTenTheLoai());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tenLoai.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập tên loại", Toast.LENGTH_SHORT).show();
                    tenLoai.requestFocus();
                    return;
                }
                tl.setTenTheLoai(tenLoai.getText().toString());
                if (theLoaiDao.updateTheLoai(tl)) {
                    listtl.clear();
                    listtl.addAll(theLoaiDao.getAll());
                    notifyDataSetChanged();
                    dialog.dismiss();
                    Toast.makeText(context, "Update Succ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Update Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Huỷ Update", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}
