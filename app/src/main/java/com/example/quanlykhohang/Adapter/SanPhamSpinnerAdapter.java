package com.example.quanlykhohang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlykhohang.Model.SanPham;
import com.example.quanlykhohang.R;

import java.util.ArrayList;

public class SanPhamSpinnerAdapter extends ArrayAdapter<SanPham> {
    private Context context;
    private ArrayList<SanPham> list;
    TextView tvMaSp, tvTenSp;
    public SanPhamSpinnerAdapter(@NonNull Context context, ArrayList<SanPham> list) {
        super(context, 0,list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_spinner_sanpham, null);
        }
        final SanPham item = list.get(position);
        if (item != null) {
            tvMaSp = v.findViewById(R.id.tvMaSp_spinner);
            tvMaSp.setText(item.getMaSp() + ". ");
            tvTenSp = v.findViewById(R.id.tvTenSp_spinner);
            tvTenSp.setText(item.getTenSp());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_spinner_sanpham, null);
        }
        SanPham item = list.get(position);
        if (item != null) {
            tvMaSp = v.findViewById(R.id.tvMaSp_spinner);
            tvMaSp.setText(item.getMaSp() + ". ");
            tvTenSp = v.findViewById(R.id.tvTenSp_spinner);
            tvTenSp.setText(item.getTenSp());
        }
        return v;
    }
}
