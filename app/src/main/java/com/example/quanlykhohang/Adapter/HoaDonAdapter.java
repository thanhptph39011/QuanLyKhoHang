package com.example.quanlykhohang.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlykhohang.Fragments.HoaDonFragment;
import com.example.quanlykhohang.Model.HoaDon;
import com.example.quanlykhohang.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HoaDonAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<HoaDon> list;
    HoaDonFragment fragment;
    TextView tvMaHd, tvSoHD, tvMaThuKho, tvloaiHd, tvNgay,tvTrangThai;
    ImageView btnDelete;
    SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");

    public HoaDonAdapter(@NonNull Context context, HoaDonFragment fragment, ArrayList<HoaDon> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_hoadon, null);
        }
        final HoaDon item = list.get(position);
        if (item != null) {
            tvMaHd = v.findViewById(R.id.tvMaHd_itemHoaDon);
            tvSoHD = v.findViewById(R.id.tvSoHd_itemHoaDon);
            tvMaThuKho = v.findViewById(R.id.tvTenThuKho_itemHoaDon);
            tvloaiHd = v.findViewById(R.id.tvLoaiHd_itemHoaDon);
            tvNgay = v.findViewById(R.id.tvNgay_itemHoaDon);
            tvTrangThai=v.findViewById(R.id.tvTrangThai_itemHoaDon);
            btnDelete = v.findViewById(R.id.btnDelete_hoaDon);
//
            tvMaHd.setText(item.getMaHd() + "");
            tvSoHD.setText(item.getSoHoaDon());
            tvMaThuKho.setText(item.getMaUser());
            try {
                tvNgay.setText(sfd.format(item.getNgay()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(item.getXacNhanHd()==0){
                tvTrangThai.setText(" Đã hoàn thành");
                tvTrangThai.setTextColor(Color.BLUE);
            }else{
                tvTrangThai.setText("Chưa hoàn thành");
                tvTrangThai.setTextColor(Color.RED);
            }
            if (item.getLoaiHoaDon() == 0) {
                tvloaiHd.setText("Nhập");
            } else {
                tvloaiHd.setText("Xuất");
            }
        }
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.xoa(String.valueOf(item.getMaHd()));
            }
        });
        return v;
    }
}
