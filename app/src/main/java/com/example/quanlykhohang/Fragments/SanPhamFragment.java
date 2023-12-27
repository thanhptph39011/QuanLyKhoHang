package com.example.quanlykhohang.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quanlykhohang.Adapter.SanPhamAdapter;
import com.example.quanlykhohang.Adapter.TheLoaiSpinnerAdapter;
import com.example.quanlykhohang.Dao.SanPhamDao;
import com.example.quanlykhohang.Dao.TheLoaiDao;
import com.example.quanlykhohang.Model.SanPham;
import com.example.quanlykhohang.Model.TheLoai;
import com.example.quanlykhohang.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class SanPhamFragment extends Fragment {
    ListView lvSp;
    ArrayList<SanPham> list;
    ArrayList<SanPham> templist;
    Dialog dialog;
    FloatingActionButton fab;
    EditText edtMaSp, edtTenSp, edtGia, edtSL, edtMoTa, edtTmKiemSp;
    Spinner spinner;
    ImageView btnSave, btnHuy;
    static SanPhamDao sanPhamDao;
    SanPhamAdapter adapter;
    SanPham item;
    TheLoaiSpinnerAdapter spinerAdapter;
    ArrayList<TheLoai> listTl;
    TheLoaiDao theLoaiDao;
    int maTl, position;

    public SanPhamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_san_pham, container, false);
        //ánh xạ
        lvSp = v.findViewById(R.id.lvSanPham);
        fab = v.findViewById(R.id.fltSanPham);
        edtTmKiemSp = v.findViewById(R.id.edtTimKiemSanPham);
        //
        sanPhamDao = new SanPhamDao(getActivity());
        list = (ArrayList<SanPham>) sanPhamDao.getAll();
        templist = (ArrayList<SanPham>) sanPhamDao.getAll();
        adapter = new SanPhamAdapter(getActivity(), this, list);
        lvSp.setAdapter(adapter);
        //Tìm kiếm
        edtTmKiemSp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //code
                list.clear();
                for (SanPham sp : templist
                ) {
                    if (sp.getTenSp().contains(charSequence.toString())) {
                        list.add(sp);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(getActivity(), 0);
            }
        });
        lvSp.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = list.get(i);
                openDialog(getActivity(), 1); //update
                return false;
            }
        });
        return v;
    }

    public void capNhapLv() {
        list = (ArrayList<SanPham>) sanPhamDao.getAll();
        adapter = new SanPhamAdapter(getActivity(), this, list);
        lvSp.setAdapter(adapter);
    }

    public void openDialog(Context context, int type) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.item_dialog_sanpham); //lấy dialog
        //ánh xạ
        edtMaSp = dialog.findViewById(R.id.edtMaSp_itemSp);
        edtTenSp = dialog.findViewById(R.id.edtTenSp_itemSp);
        edtGia = dialog.findViewById(R.id.edtGia_itemSp);
        edtSL = dialog.findViewById(R.id.edtSl_itemSp);
        edtMoTa = dialog.findViewById(R.id.edtMoTa_itemSp);
        spinner = dialog.findViewById(R.id.spTheLoai_itemSp);
        btnSave = dialog.findViewById(R.id.btnAdd_Sp);
        btnHuy = dialog.findViewById(R.id.btnHuyAdd_Sp);
        //
        listTl = new ArrayList<TheLoai>();
        theLoaiDao = new TheLoaiDao(context);
        listTl = (ArrayList<TheLoai>) theLoaiDao.getAll();
        spinerAdapter = new TheLoaiSpinnerAdapter(context, listTl);
        spinner.setAdapter(spinerAdapter);
        //check nếu listLoaiGiay Null
        if (listTl.isEmpty()) {
            Toast.makeText(context, "Vui lòng thêm thể loại trước", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;
        }
        //Lấy mã loại giày
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maTl = listTl.get(i).getMaTheLoai();
                Toast.makeText(context, "Chọn " + listTl.get(i).getTenTheLoai(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//kiểm tra type insert 0 hay update 1
        edtMaSp.setEnabled(false);
        if (type != 0) {
            edtMaSp.setText(String.valueOf(item.getMaSp()));
            edtTenSp.setText(item.getTenSp());
            edtGia.setText(String.valueOf(item.getGia()));
            edtSL.setText(String.valueOf(item.getSoLuong()));
            edtMoTa.setText(item.getMoTa());
            for (int i = 0; i < listTl.size(); i++) {
                if (item.getMaLoai() == (listTl.get(i).getMaTheLoai())) {
                    position = i;
                }
                Log.i("zzzzzzzzzzzzz", "posSanPham" + position);
                spinner.setSelection(position);
            }
        }
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtTenSp.equals("") || edtGia.equals("") || edtSL.equals("") || edtMoTa.equals("")) {
                    Toast.makeText(context, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                try{
                    int gia = Integer.parseInt(edtGia.getText().toString());
                    if(gia<0){
                        Toast.makeText(context, "Giá tiền >0", Toast.LENGTH_SHORT).show();
                        edtGia.requestFocus();
                        return;
                    }
                }catch (Exception e){
                    Toast.makeText(context, "Giá là sô", Toast.LENGTH_SHORT).show();
                    edtGia.requestFocus();
                    return;
                }
                try{
                    int soluong = Integer.parseInt(edtSL.getText().toString());
                    if(soluong<0){
                        Toast.makeText(context, "Số lượng >0", Toast.LENGTH_SHORT).show();
                        edtSL.requestFocus();
                        return;
                    }
                }catch (Exception e){
                    Toast.makeText(context, "Số lượng là số", Toast.LENGTH_SHORT).show();
                    edtSL.requestFocus();
                    return;
                }

                item = new SanPham();
                item.setTenSp(edtTenSp.getText().toString());
                item.setGia(Integer.parseInt(edtGia.getText().toString()));
                item.setSoLuong(Integer.parseInt(edtSL.getText().toString()));
                item.setMoTa(edtMoTa.getText().toString());
                item.setMaLoai(maTl);
                if (validate() > 0) {
                    if (type == 0) {
//insert
                        if (sanPhamDao.insertSanPham(item)) {
                            Toast.makeText(context, "Add Succ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Add Fail", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        item.setMaSp(Integer.parseInt(edtMaSp.getText().toString()));
                        if (sanPhamDao.updateSanPham(item)) {
                            Toast.makeText(context, "Update Succ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Update Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                    capNhapLv();
                    dialog.dismiss();


                }


            }
        });
        dialog.show();
    }


    public void xoa(int Id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Cảnh báo");
        builder.setIcon(R.drawable.baseline_warning_24);
        builder.setMessage("Bạn có chắc chắn muốn xóa không");
        builder.setCancelable(true);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sanPhamDao.deleteTheLoai(Id);
                capNhapLv();
                dialog.cancel();
                Toast.makeText(getContext(), "Delete Succ", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        builder.show();
    }

    public int validate() {
        int check = 1;
        if (edtTenSp.length() == 0 || edtGia.length() == 0 || edtMoTa.length() == 0 || edtSL.length() == 0) {
            Toast.makeText(getActivity(), "Vui lòng nhập dầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            try {
                int gia = Integer.parseInt(edtGia.getText().toString());
                if (gia < 0) {
                    Toast.makeText(getActivity(), "Nhập giá >0", Toast.LENGTH_SHORT).show();
                    edtGia.requestFocus();
                    check = -1;
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Giá là số", Toast.LENGTH_SHORT).show();
                edtGia.requestFocus();
                check = -1;
            }
            try {
                int sl = Integer.parseInt(edtSL.getText().toString());
                if (sl < 0) {
                    Toast.makeText(getActivity(), "Số lượng >0", Toast.LENGTH_SHORT).show();
                    edtSL.requestFocus();
                    check = -1;
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Số lượng sp là số", Toast.LENGTH_SHORT).show();
                edtSL.requestFocus();
                check = -1;
            }
        }

        return check;
    }
}