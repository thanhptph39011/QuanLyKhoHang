package com.example.quanlykhohang.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.quanlykhohang.Adapter.TonAdapter;
import com.example.quanlykhohang.Dao.ThongKeDao;
import com.example.quanlykhohang.Model.HangTon;
import com.example.quanlykhohang.R;

import java.util.ArrayList;


public class TonFragment extends Fragment {
    ListView listView;
    ArrayList<HangTon> list;
    TonAdapter adapter;

    public TonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ton, container, false);
        listView = v.findViewById(R.id.listView_ton);
        ThongKeDao thongKeDao = new ThongKeDao(getActivity());
        list = (ArrayList<HangTon>) thongKeDao.thongKeSanPham();
        adapter = new TonAdapter(getActivity(), this, list);
        listView.setAdapter(adapter);
        return v;
    }
}