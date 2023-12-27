package com.example.quanlykhohang.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quanlykhohang.Dao.UserDao;
import com.example.quanlykhohang.HomeActivity;
import com.example.quanlykhohang.Model.NguoiDung;
import com.example.quanlykhohang.R;


public class AddUserFragment extends Fragment {
    EditText userName, hoTen, matKhau, email;
    ImageView btnSave, btnHuy;
    UserDao userDao;


    public AddUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_user, container, false);
        userName = view.findViewById(R.id.edtUser_itemAddUser);
        hoTen = view.findViewById(R.id.edtFullName_itemAddUser);
        matKhau = view.findViewById(R.id.edtPass_itemAddUser);
        email = view.findViewById(R.id.edtEmail_itemAddUser);
        btnSave = view.findViewById(R.id.btnSave_itemAddUser);
        btnHuy = view.findViewById(R.id.btnHuy_itemAddUser);
        userDao = new UserDao(getActivity());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = userName.getText().toString();
                String ten = hoTen.getText().toString();
                String pass = matKhau.getText().toString();
                String mail = email.getText().toString();
                if (user.isEmpty() || ten.isEmpty() || pass.isEmpty() || mail.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                NguoiDung nd = new NguoiDung(user, pass,ten, mail);
                if (userDao.insertUser(nd)) {
                    Toast.makeText(getActivity(), "Add Succ", Toast.LENGTH_SHORT).show();
                    userName.setText("");
                    matKhau.setText("");
                    hoTen.setText("");
                    email.setText("");
                } else {
                    Toast.makeText(getActivity(), "Add Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Huỷ Add", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}