package com.example.quanlykhohang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlykhohang.Model.TheLoai;
import com.example.quanlykhohang.R;

import java.util.ArrayList;

public class TheLoaiSpinnerAdapter extends ArrayAdapter<TheLoai> {
    private Context context;
    private ArrayList<TheLoai> list;
    TextView tvMaTheLoai, tvTenTheLoai;
    public TheLoaiSpinnerAdapter(@NonNull Context context, ArrayList<TheLoai> list) {
        super(context,0,list);
        this.context = context;
        this.list =list;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_theloai_spinner, null);
        }
        final TheLoai item = list.get(position);
        if (item != null) {
            tvMaTheLoai = v.findViewById(R.id.tvMatheLoai_spiner);
            tvMaTheLoai.setText(item.getMaTheLoai() + ". ");
            tvTenTheLoai = v.findViewById(R.id.tvTenTheLoai_spiner);
            tvTenTheLoai.setText(item.getTenTheLoai());
        }
        return v;
    }
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_theloai_spinner, null);
        }
        TheLoai item = list.get(position);
        if (item != null) {
            tvMaTheLoai = v.findViewById(R.id.tvMatheLoai_spiner);
            tvMaTheLoai.setText(item.getMaTheLoai() + ". ");
            tvTenTheLoai = v.findViewById(R.id.tvTenTheLoai_spiner);
            tvTenTheLoai.setText(item.getTenTheLoai());
        }
        return v;
    }
}
