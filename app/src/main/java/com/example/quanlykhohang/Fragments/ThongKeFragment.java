package com.example.quanlykhohang.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlykhohang.Dao.ThongKeDao;
import com.example.quanlykhohang.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class ThongKeFragment extends Fragment {
    EditText edtStartDate, edtEndDate;
    Button btnXuat, btnNhap, btnTon;
    TextView tvThongKe;

    ThongKeDao thongKeDao;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    int mYear, mMonth, mDay;


    public ThongKeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_thong_ke, container, false);
        edtStartDate = v.findViewById(R.id.edtStartDate);
        edtEndDate = v.findViewById(R.id.edtEndDate);
        btnXuat = v.findViewById(R.id.btnXuat);
        btnNhap = v.findViewById(R.id.btnNhap);
        btnTon = v.findViewById(R.id.btnTon);
        tvThongKe = v.findViewById(R.id.tvThongKe);
        //
        edtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(), 0, mDateTuNgay, mYear, mMonth, mDay);
                d.show();
            }
        });
        edtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(), 0, mDateDenNgay, mYear, mMonth, mDay);
                d.show();
            }
        });
        btnXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
String batdau = edtStartDate.getText().toString();
String ketthuc = edtEndDate.getText().toString();
if(batdau.isEmpty()||ketthuc.isEmpty()){
    Toast.makeText(getActivity(), "Vui lòng nhập ngày", Toast.LENGTH_SHORT).show();
    return;
}
                try {
                    Date startDate = sdf.parse(edtStartDate.getText().toString());
                    Date endDate = sdf.parse(edtEndDate.getText().toString());
                    thongKeDao = new ThongKeDao(getActivity());
                    int soLuongXuat = thongKeDao.thongKeSanPhamXuat(startDate, endDate);

                    tvThongKe.setText("Số lượng sản phẩm xuất trong khoảng thời gian đã chọn: " + soLuongXuat+" sản phẩm");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
        btnNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String batdau = edtStartDate.getText().toString();
                String ketthuc = edtEndDate.getText().toString();
                if(batdau.isEmpty()||ketthuc.isEmpty()){
                    Toast.makeText(getActivity(), "Vui lòng nhập ngày", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    Date startDate = sdf.parse(edtStartDate.getText().toString());
                    Date endDate = sdf.parse(edtEndDate.getText().toString());

                    thongKeDao = new ThongKeDao(getActivity());
                    int soLuongNhap = thongKeDao.thongKeSanPhamNhap(startDate, endDate);

                    tvThongKe.setText("Số lượng sản phẩm nhập trong khoảng thời gian đã chọn: " + soLuongNhap+" sản phẩm");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        btnTon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Date endDate = sdf.parse(edtEndDate.getText().toString());

                    thongKeDao = new ThongKeDao(getActivity());
                    int soLuongTon = thongKeDao.thongKeSanPhamTon(endDate);

                    tvThongKe.setText("Số lượng sản phẩm tồn trong kho đến ngày đã chọn: " + soLuongTon+" sản phẩm");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        return v;
    }

    DatePickerDialog.OnDateSetListener mDateTuNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            edtStartDate.setText(sdf.format(c.getTime()));
        }
    };

    DatePickerDialog.OnDateSetListener mDateDenNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            edtEndDate.setText(sdf.format(c.getTime()));
        }
    };

}