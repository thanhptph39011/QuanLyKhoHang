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
import com.example.quanlykhohang.Fragments.XuatFragment;
import com.example.quanlykhohang.Model.ThongKe;
import com.example.quanlykhohang.R;

import java.util.ArrayList;

public class XuatAdapter extends ArrayAdapter<ThongKe> {
    private Context context;
    XuatFragment fragment;
    ArrayList<ThongKe> list;

    public XuatAdapter(@NonNull Context context, XuatFragment fragment, ArrayList<ThongKe> list) {
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
            holder.tvSoluongHd=v.findViewById(R.id.tvSoLuongHd_thongKe);
            holder.tvSoluong = v.findViewById(R.id.tvSoLuong_thongKe);
            holder.tvTongTien = v.findViewById(R.id.tvTongTien_ThongKe);


            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        ThongKe item = getItem(position);
        if (item != null) {
            holder.tvSoluongHd.setText(String.valueOf(item.getSoLuongHoaDon()));
            holder.tvSoluong.setText(String.valueOf(item.getSoLuong()));
            holder.tvTongTien.setText(String.valueOf(item.getTongTien()));
        }

        return v;
    }

    static class ViewHolder {
        TextView tvSoluong;
        TextView tvTongTien;
        TextView tvSoluongHd;
    }
}
