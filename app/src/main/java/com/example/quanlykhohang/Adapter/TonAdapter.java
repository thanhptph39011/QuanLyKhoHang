package com.example.quanlykhohang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlykhohang.Fragments.TonFragment;
import com.example.quanlykhohang.Fragments.XuatFragment;
import com.example.quanlykhohang.Model.HangTon;
import com.example.quanlykhohang.Model.ThongKe;
import com.example.quanlykhohang.R;

import java.util.ArrayList;

public class TonAdapter extends ArrayAdapter<HangTon> {
    private Context context;
    TonFragment fragment;
    ArrayList<HangTon> list;
    TextView tvMaSp, tvSoluong, tvTongTien;

    public TonAdapter(@NonNull Context context, TonFragment fragment, ArrayList<HangTon> list) {
        super(context, 0, list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v=convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_tonkho, null);
        }
        final HangTon item  = list.get(position);
        if(item!=null){
            tvMaSp=v.findViewById(R.id.tvTenSp_tonKho);
            tvSoluong=v.findViewById(R.id.tvSoLuong_tonKho);
            tvTongTien=v.findViewById(R.id.tvTongTien_TonKho);
            //
            tvMaSp.setText(item.getMaSp());
            tvSoluong.setText(item.getSoLuongTon()+"");
            tvTongTien.setText(item.getTienTonKho()+"");
        }
        return v;
    }
}
