package com.example.quanlykhohang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlykhohang.Dao.SanPhamDao;
import com.example.quanlykhohang.Dao.TheLoaiDao;
import com.example.quanlykhohang.Fragments.SanPhamFragment;
import com.example.quanlykhohang.Model.SanPham;
import com.example.quanlykhohang.Model.TheLoai;
import com.example.quanlykhohang.R;

import java.util.ArrayList;

public class SanPhamAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<SanPham> list;
    SanPhamFragment fragment;
    TextView tvMaSp, tvTenSp, tvGia, tvSoLuong, tvMoTa, tvMaLoai;
    ImageView btnDelete;

    public SanPhamAdapter(@NonNull Context context, SanPhamFragment fragment, ArrayList<SanPham> list) {
        super(context, 0, list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_sanpham, null);
        }
        SanPham item = list.get(position);
        if (item != null) {
            TheLoaiDao theLoaiDao = new TheLoaiDao(context);
            TheLoai theLoai = theLoaiDao.getID(String.valueOf(item.getMaLoai()));
            tvMaSp = v.findViewById(R.id.tvMaSp_itemSanPham);
            tvTenSp = v.findViewById(R.id.tvTenSp_itemSanPham);
            tvGia = v.findViewById(R.id.tvGia_itemSanPham);
            tvSoLuong = v.findViewById(R.id.tvSoLuong_itemSanPham);
            tvMaLoai = v.findViewById(R.id.tvMaTl_itemSanPham);
            tvMoTa =v.findViewById(R.id.tvMoTa_itemSanPham);
            btnDelete = v.findViewById(R.id.btnDelete_sanPham);
            //
            tvMaSp.setText(item.getMaSp() + "");
            tvTenSp.setText(item.getTenSp());
            tvGia.setText(item.getGia() + "");
            tvSoLuong.setText(item.getSoLuong()+"");
            tvMoTa.setText(item.getMoTa());
            if (theLoai != null) {
                tvMaLoai.setText(theLoai.getTenTheLoai());
            } else {
                tvMaLoai.setText("Không xác định");
            }
        }
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.xoa(item.getMaSp());
            }
        });
        return v;
    }
}
