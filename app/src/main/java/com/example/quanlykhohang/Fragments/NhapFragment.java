package com.example.quanlykhohang.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quanlykhohang.Adapter.ThongKeAdapter;
import com.example.quanlykhohang.Dao.ThongKeDao;
import com.example.quanlykhohang.DataBase.DbHelper;
import com.example.quanlykhohang.Model.ThongKe;
import com.example.quanlykhohang.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class NhapFragment extends Fragment {
    Button btnNgay, btnTuan, btnThang,btnTuChon;
    EditText edtStartDate, edtEndDate;
    ListView listViewThongKe;
    ArrayList<ThongKe> list;
    ThongKeAdapter adapter;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    int mYear, mMonth, mDay;

    public NhapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_nhap, container, false);
        btnNgay = v.findViewById(R.id.btnNgay);
        btnTuan = v.findViewById(R.id.btnTuan);
        btnThang = v.findViewById(R.id.btnThang);
        btnTuChon =v.findViewById(R.id.btnThongKeNgayTuChon);
        edtStartDate = v.findViewById(R.id.edtStartDat);
        edtEndDate = v.findViewById(R.id.edtEndDat);
        listViewThongKe = v.findViewById(R.id.listView_Nhap);
        btnNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // Đặt giá trị ngày hiện tại vào các trường văn bản
                edtStartDate.setText(String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth));
                edtEndDate.setText(String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth));
                Toast.makeText(getActivity(), "Ngày", Toast.LENGTH_SHORT).show();
                NhapFragment.this.onClickBtnNgay();

            }
        });
        btnTuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.setFirstDayOfWeek(Calendar.MONDAY);
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                calendar.set(year, month, dayOfMonth);
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                Date startDate = calendar.getTime();

                calendar.set(year, month, dayOfMonth);
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
                Date endDate = calendar.getTime();

                String start = sdf.format(startDate);
                String end = sdf.format(endDate);
                edtStartDate.setText(start);
                edtEndDate.setText(end);
                Toast.makeText(getActivity(), "Tuần", Toast.LENGTH_SHORT).show();
                NhapFragment.this.onClickBtnTuan();
            }
        });
        btnThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);

                calendar.set(year, month, 1);
                Date startDate = calendar.getTime();

                calendar.set(year, month, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                Date endDate = calendar.getTime();

                String start = sdf.format(startDate);
                String end = sdf.format(endDate);
                edtStartDate.setText(start);
                edtEndDate.setText(end);
                Toast.makeText(getActivity(), "Tháng", Toast.LENGTH_SHORT).show();
                NhapFragment.this.onClickBtnThang();
            }
        });
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
        btnTuChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
NhapFragment.this.onClickBtnTuChon();
            }
        });
        return v;
    }

    private void onClickBtnNgay() {
        ThongKeDao thongKeDao = new ThongKeDao(getActivity());
        list = (ArrayList<ThongKe>) thongKeDao.thongKeNhapTheoNgay();
        adapter = new ThongKeAdapter(getActivity(), this, list);
        listViewThongKe.setAdapter(adapter);
    }

    private void onClickBtnTuan() {
        ThongKeDao thongKeDao = new ThongKeDao(getActivity());
        list = (ArrayList<ThongKe>) thongKeDao.thongKeNhapTheoTuan();
        adapter = new ThongKeAdapter(getActivity(), this, list);
        listViewThongKe.setAdapter(adapter);
    }

    private void onClickBtnThang() {
        ThongKeDao thongKeDao = new ThongKeDao(getActivity());
        list = (ArrayList<ThongKe>) thongKeDao.thongKeNhapTheoThang();
        adapter = new ThongKeAdapter(getActivity(), this, list);
        listViewThongKe.setAdapter(adapter);
    }
    private void
    onClickBtnTuChon(){
        String batdau = edtStartDate.getText().toString();
        String ketthuc = edtEndDate.getText().toString();
        if (batdau.isEmpty() || ketthuc.isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng nhập ngày", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            ThongKeDao thongKeDao = new ThongKeDao(getActivity());
            list = (ArrayList<ThongKe>) thongKeDao.thongKeNhapTheoNgayTuChon(batdau,ketthuc);
            adapter = new ThongKeAdapter(getActivity(), this, list);
            listViewThongKe.setAdapter(adapter);
        }catch (Exception e) {
                    e.printStackTrace();
                }

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