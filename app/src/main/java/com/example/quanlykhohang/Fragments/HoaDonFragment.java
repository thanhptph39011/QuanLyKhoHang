package com.example.quanlykhohang.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlykhohang.Adapter.HoaDonAdapter;
import com.example.quanlykhohang.Dao.HoaDonDao;
import com.example.quanlykhohang.HoaDonCtActivity;
import com.example.quanlykhohang.Model.HoaDon;
import com.example.quanlykhohang.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class HoaDonFragment extends Fragment {
    ListView lvHoaDon;
    ArrayList<HoaDon> list;
    ArrayList<HoaDon> templist; //khai báo mảng đệm
    static HoaDonDao hoaDonDao;
    SimpleDateFormat sdf;
    HoaDonAdapter adapter;
    HoaDon item;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaHd, edtTimKiem, edtSoHd;
    Spinner spKhachHang;
    TextView tvNgay, tvTk;

    RadioGroup radioGroup;
    RadioButton rdoNhap, rdoXuat;
    ImageView btnSave, btnHuy;
    String tenTk = "";
    SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");
    int maHd;

    public HoaDonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hoa_don, container, false);
        lvHoaDon = v.findViewById(R.id.lvHoaDon);
        fab = v.findViewById(R.id.fltHoaDon);
        hoaDonDao = new HoaDonDao(getActivity());
        list = (ArrayList<HoaDon>) hoaDonDao.getAll();
        templist = (ArrayList<HoaDon>) hoaDonDao.getAll();
        adapter = new HoaDonAdapter(getActivity(), this, list);
        lvHoaDon.setAdapter(adapter);
        edtTimKiem = v.findViewById(R.id.edtTimKiemHoaDon);
        edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.clear();
                for (HoaDon hd : templist) {
                    if (hd.getSoHoaDon().contains(charSequence.toString())) {
                        list.add(hd);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiaLog(getActivity(), 0);
            }
        });
//        lvHoaDon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                item = list.get(i);
//                openDiaLog(getActivity(), 1); //update
//                return false;
//            }
//        });

        lvHoaDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), HoaDonCtActivity.class);
                maHd = list.get(i).getMaHd();
                intent.putExtra("maHd",maHd);
                startActivity(intent);

            }
        });
        return v;
    }

    public void capNhapLv() {
        list = (ArrayList<HoaDon>) hoaDonDao.getAll();
        adapter = new HoaDonAdapter(getActivity(), this, list);
        lvHoaDon.setAdapter(adapter);
    }

    public void openDiaLog(Context context, int type) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.item_diaglog_hoadon);
        edMaHd = dialog.findViewById(R.id.edtMaHd_itemAddHd);
        edtSoHd = dialog.findViewById(R.id.edtSoHd_itemAddHd);
        tvNgay = dialog.findViewById(R.id.tvDate_itemHd);
        tvTk = dialog.findViewById(R.id.tvMaNv);
        radioGroup = dialog.findViewById(R.id.radioGroup);
        rdoNhap = dialog.findViewById(R.id.radioNhap);
        rdoXuat = dialog.findViewById(R.id.radioXuat);
        btnSave = dialog.findViewById(R.id.btnSave_itemAddHd);
        btnHuy = dialog.findViewById(R.id.btnHuy_itemAddHd);
        //
        tvNgay.setText("Ngày: " + sfd.format(new Date()));
        if (type != 0) {
            edMaHd.setText(String.valueOf(item.getMaHd()));
            edtSoHd.setText(item.getSoHoaDon());
            tvNgay.setText(sfd.format(item.getNgay()));
            if (item.getLoaiHoaDon() == 1) {
                rdoNhap.setChecked(true);
            } else {
                rdoXuat.setChecked(false);
            }
        }
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        SharedPreferences preferences = context.getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        tenTk = preferences.getString("userName", "");
        tvTk.setText(tenTk);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item = new HoaDon();
                item.setMaUser(tvTk.getText().toString());
                item.setNgay(new Date());
                item.setSoHoaDon(edtSoHd.getText().toString());
                if (rdoNhap.isChecked()) {
                    item.setLoaiHoaDon(0);
                } else {
                    item.setLoaiHoaDon(1);
                }
                if (type == 0) {
                    //type==0 insert
                    if (hoaDonDao.insertHoaDon(item)) {
                        Toast.makeText(context, "Add Succ", Toast.LENGTH_SHORT).show();
                        capNhapLv();
                    } else {
                        Toast.makeText(context, "Add Fail", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    item.setMaHd(Integer.parseInt(edMaHd.getText().toString()));
                    if (hoaDonDao.updateHoaDon(item)) {
                        Toast.makeText(context, "Update Succ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Update Fail", Toast.LENGTH_SHORT).show();
                    }
                }
                capNhapLv();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void xoa(final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Cảnh báo");
        builder.setIcon(R.drawable.baseline_warning_24);
        builder.setMessage("Bạn có chắc chắn muốn xóa không");
        builder.setCancelable(true);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                hoaDonDao.deleteHoaDon(Integer.parseInt(id));
                capNhapLv();
                dialog.cancel();
                Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}