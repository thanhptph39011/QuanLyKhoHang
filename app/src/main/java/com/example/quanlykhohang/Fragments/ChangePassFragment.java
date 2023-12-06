package com.example.quanlykhohang.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quanlykhohang.Dao.UserDao;
import com.example.quanlykhohang.Model.NguoiDung;
import com.example.quanlykhohang.R;
import com.google.android.material.textfield.TextInputEditText;


public class ChangePassFragment extends Fragment {

    TextInputEditText edPassOld, edPassChange, edRePassChange;
    ImageView btnSaveUserChange, btnCancleUserChange;
    UserDao userDao;

    public ChangePassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_change_pass, container, false);
        userDao = new UserDao(getActivity());
        edPassOld = v.findViewById(R.id.edtOldPass);
        edPassChange = v.findViewById(R.id.edtNewPass);
        edRePassChange = v.findViewById(R.id.edtNhapLaiPass);
        btnSaveUserChange = v.findViewById(R.id.btnSave_ChangePass);
        btnCancleUserChange = v.findViewById(R.id.btnHuy_ChangePass);
        btnCancleUserChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edPassOld.setText("");
                edPassChange.setText("");
                edRePassChange.setText("");
            }
        });
        btnSaveUserChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String user = pref.getString("userName", "");
                if (validate() > 0) {
                    NguoiDung nd = userDao.getID(user);
                    nd.setPassWord(edPassChange.getText().toString());
                    if (userDao.updatePass(nd) > 0) {
                        Toast.makeText(getActivity(), "ChangePass Succ", Toast.LENGTH_SHORT).show();
                        edPassOld.setText("");
                        edPassChange.setText("");
                        edRePassChange.setText("");
                    } else {
                        Toast.makeText(getActivity(), "ChangePass Fail", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return v;
    }

    public int validate() {
        int check = 1;
        if (edPassOld.getText().length() == 0 || edPassChange.getText().length() == 0 || edRePassChange.getText().length() == 0) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String passOld = pref.getString("passWord", "");
            String pass = edPassChange.getText().toString();
            String rePass = edRePassChange.getText().toString();
            if (!passOld.equals(edPassOld.getText().toString())) {
                Toast.makeText(getContext(), "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!pass.equals(rePass)) {
                Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}