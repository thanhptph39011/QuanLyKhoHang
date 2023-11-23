package com.example.quanlykhohang.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quanlykhohang.Adapter.TheLoaiAdapter;
import com.example.quanlykhohang.Dao.TheLoaiDao;
import com.example.quanlykhohang.Model.TheLoai;
import com.example.quanlykhohang.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class TheLoaiFragment extends Fragment {
    FloatingActionButton btnAdd;
    TheLoaiDao theLoaiDao;
    EditText edtTimKiemTheLoai;
    TheLoaiAdapter adapter;
    ArrayList<TheLoai> tempList; //khai báo mảng đệm
    RecyclerView rcLG;
    ArrayList<TheLoai> listTl = new ArrayList<TheLoai>();

    public TheLoaiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_the_loai, container, false);
        rcLG = v.findViewById(R.id.rycTheLoai);
        btnAdd = v.findViewById(R.id.fltAddTheLoai);
        edtTimKiemTheLoai = v.findViewById(R.id.edtTimKiemTheLoai);
        theLoaiDao = new TheLoaiDao(getActivity());
        listTl = (ArrayList<TheLoai>) theLoaiDao.getAll();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        tempList = (ArrayList<TheLoai>) theLoaiDao.getAll();
        rcLG.setLayoutManager(layoutManager);
        adapter = new TheLoaiAdapter(getActivity(), listTl);
        rcLG.setAdapter(adapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        edtTimKiemTheLoai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listTl.clear();
                for (TheLoai tl : tempList) {
                    if (tl.getTenTheLoai().contains(charSequence.toString())) {
                        listTl.add(tl);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return v;
    }

    public void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_theloai, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        EditText edtTenLoai = view.findViewById(R.id.edtTenTheLoai_itemAddTheLoai);
        ImageView btnSave = view.findViewById(R.id.add_theLoai);
        ImageView btnHuy = view.findViewById(R.id.huyAdd_theLoai);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenLoai = edtTenLoai.getText().toString();
                if (tenLoai.isEmpty()) {
                    Toast.makeText(getActivity(), "Nhập tên loại", Toast.LENGTH_SHORT).show();
                    edtTenLoai.requestFocus();
                    return;
                }
                TheLoai tl = new TheLoai(tenLoai);
                if (theLoaiDao.insertTheLoai(tl)) {
                    listTl.clear();
                    listTl.addAll(theLoaiDao.getAll());
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Add Succ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Add Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}