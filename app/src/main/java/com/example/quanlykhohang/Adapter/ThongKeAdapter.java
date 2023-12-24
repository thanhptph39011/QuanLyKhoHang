package com.example.quanlykhohang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlykhohang.Fragments.NhapFragment;
import com.example.quanlykhohang.Model.ThongKe;
import com.example.quanlykhohang.R;

import java.util.ArrayList;

public class ThongKeAdapter extends ArrayAdapter<ThongKe> {
    private Context context;
    NhapFragment fragment;
    ArrayList<ThongKe> list;
    TextView tvSoluong, tvTongTien;

    public ThongKeAdapter(@NonNull Context context, NhapFragment fragment, ArrayList<ThongKe> list) {
        super(context, 0, list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

   @NonNull
@Override
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    View v = convertView;
    ViewHolder holder;

    if (v == null) {
        LayoutInflater inflater = LayoutInflater.from(context);
        v = inflater.inflate(R.layout.item_thongke, parent, false);

        holder = new ViewHolder();
        holder.tvSoluong = v.findViewById(R.id.tvSoLuong_thongKe);
        holder.tvTongTien = v.findViewById(R.id.tvTongTien_ThongKe);

        v.setTag(holder);
    } else {
        holder = (ViewHolder) v.getTag();
    }

    ThongKe item = getItem(position);
    if (item != null) {
        holder.tvSoluong.setText(String.valueOf(item.getSoLuong()));
        holder.tvTongTien.setText(String.valueOf(item.getTongTien()));
    }

    return v;
}

static class ViewHolder {
    TextView tvSoluong;
    TextView tvTongTien;
}
}
