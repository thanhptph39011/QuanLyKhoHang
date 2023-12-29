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
import com.example.quanlykhohang.Adapter.XuatAdapter;
import com.example.quanlykhohang.Dao.ThongKeDao;
import com.example.quanlykhohang.Model.ThongKe;
import com.example.quanlykhohang.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class XuatFragment extends Fragment {
    Button btnNgay, btnTuan, btnThang,btnTuChon;
    EditText edtStartDate, edtEndDate;
    ListView listViewThongKe;
    ArrayList<ThongKe> list;
    XuatAdapter adapter;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    int mYear, mMonth, mDay;
    public XuatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          View v=inflater.inflate(R.layout.fragment_xuat, container, false);
        btnNgay = v.findViewById(R.id.btnNgayXuat);
        btnTuan = v.findViewById(R.id.btnTuanXuat);
        btnThang = v.findViewById(R.id.btnThangXuat);
        btnTuChon =v.findViewById(R.id.btnThongKeNgayTuChonXuat);
        edtStartDate = v.findViewById(R.id.edtStart);
        edtEndDate = v.findViewById(R.id.edtEnd);
        listViewThongKe = v.findViewById(R.id.listView_Xuat);
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
                XuatFragment.this.onClickBtnNgay();

            }
        });
        btnTuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.setFirstDayOfWeek(Calendar.MONDAY);

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                calendar.set(year, month, dayOfMonth);
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                Date startDate = calendar.getTime();

                calendar.add(Calendar.DATE, 6); // Thêm 6 ngày để có 1 tuần đầy đủ
                Date endDate = calendar.getTime();

                String start = sdf.format(startDate);
                String end = sdf.format(endDate);
                edtStartDate.setText(start);
                edtEndDate.setText(end);

                Toast.makeText(getActivity(), "Tuần", Toast.LENGTH_SHORT).show();
                XuatFragment.this.onClickBtnTuan();
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
                XuatFragment.this.onClickBtnThang();
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
                XuatFragment.this.onClickBtnTuChon();
            }
        });
          return v;
    }
    private void onClickBtnNgay() {
        ThongKeDao thongKeDao = new ThongKeDao(getActivity());
        list = (ArrayList<ThongKe>) thongKeDao.thongKeXuatTheoNgay();
        adapter = new XuatAdapter(getActivity(), this, list);
        listViewThongKe.setAdapter(adapter);
    }

    private void onClickBtnTuan() {
        ThongKeDao thongKeDao = new ThongKeDao(getActivity());
        list = (ArrayList<ThongKe>) thongKeDao.thongKeXuatTheoTuan();
        adapter = new XuatAdapter(getActivity(), this, list);
        listViewThongKe.setAdapter(adapter);
    }

    private void onClickBtnThang() {
        ThongKeDao thongKeDao = new ThongKeDao(getActivity());
        list = (ArrayList<ThongKe>) thongKeDao.thongKeXuatTheoThang();
        adapter = new XuatAdapter(getActivity(), this, list);
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
            list = (ArrayList<ThongKe>) thongKeDao.thongKeXuatTheoNgayTuChon(batdau,ketthuc);
            adapter = new XuatAdapter(getActivity(), this, list);
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