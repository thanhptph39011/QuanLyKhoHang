package com.example.quanlykhohang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlykhohang.Adapter.CtHoaDonAdapter;
import com.example.quanlykhohang.Adapter.SanPhamSpinnerAdapter;
import com.example.quanlykhohang.Dao.CtHoaDonDao;
import com.example.quanlykhohang.Dao.KhoDao;
import com.example.quanlykhohang.Dao.SanPhamDao;
import com.example.quanlykhohang.Model.CtHoaDon;
import com.example.quanlykhohang.Model.SanPham;

import java.util.ArrayList;

public class HoaDonCtActivity extends AppCompatActivity {
    ListView lvSp;
    EditText edtsoHd, edtSoLuong;
    Spinner spnSanPham;
    TextView tvTongTien;
    ImageView imgBack;
    Button btnSave;
    SanPhamSpinnerAdapter sanPhamSpinnerAdapter;
    ArrayList<SanPham> listSp;
    CtHoaDonDao ctHoaDonDao;
    ArrayList<CtHoaDon> list;
    CtHoaDonAdapter adapter;
    CtHoaDon hoaDonCt;
    SanPhamDao sanPhamDao;
    String tenSp;
    int maSp, giatien;
    int maHd;
    private int maLoaiHoaDon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_ct);
        edtsoHd = findViewById(R.id.edtSoHd_itemHdCt);
        lvSp = findViewById(R.id.lvHdCt);
        tvTongTien = findViewById(R.id.tvTongTien);
        edtSoLuong = findViewById(R.id.edtSoLuong_hdct);
        btnSave = findViewById(R.id.btnSave_hdct);
        spnSanPham = findViewById(R.id.spSp_itemHdCt);
        imgBack = findViewById(R.id.imgBack);
        //
        ctHoaDonDao = new CtHoaDonDao(this);
        //
        maHd = getIntent().getIntExtra("maHd", 0);
        maLoaiHoaDon = getIntent().getIntExtra("maLoaiHoaDon", 1);
        edtsoHd.setText(String.valueOf(maHd));
        capNhapLv();
        //Sp sanPham
        sanPhamDao = new SanPhamDao(this);
        listSp = new ArrayList<>();
        listSp = (ArrayList<SanPham>) sanPhamDao.getAll();
        sanPhamSpinnerAdapter = new SanPhamSpinnerAdapter(this, listSp);
        spnSanPham.setAdapter(sanPhamSpinnerAdapter);

        spnSanPham.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maSp = listSp.get(i).getMaSp();
                tenSp = listSp.get(i).getTenSp();
                giatien = listSp.get(i).getGia();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String soluong = edtSoLuong.getText().toString();
//                if (soluong.isEmpty()) {
//                    Toast.makeText(HoaDonCtActivity.this, "Nhập số lượng", Toast.LENGTH_SHORT).show();
//                    edtSoLuong.requestFocus();
//                    return;
//                }
//                else {
//                    hoaDonCt = new CtHoaDon();
//                    hoaDonCt.setSoLuong(Integer.parseInt(soluong));
//                    hoaDonCt.setMaHoaDon(maHd);
//                    hoaDonCt.setMaSp(maSp);
//                    hoaDonCt.setDonGia(giatien);
//                    if (ctHoaDonDao.insertHoaDonCt(hoaDonCt)) {
//                        sanPhamDao.updateSoLuong(maSp, hoaDonCt.getSoLuong());
//                        Toast.makeText(HoaDonCtActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
//                        edtSoLuong.setText("");
//                        // Cập nhật lại ListView
//                        list.clear();
//                        list.addAll(ctHoaDonDao.getAll(maHd));
//                        adapter.notifyDataSetChanged();
//                    } else {
//                        Toast.makeText(HoaDonCtActivity.this, "Thêm fail", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                capNhapLv();
//            }
//        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soluong = edtSoLuong.getText().toString();
                if (soluong.isEmpty()) {
                    Toast.makeText(HoaDonCtActivity.this, "Nhập số lượng", Toast.LENGTH_SHORT).show();
                    edtSoLuong.requestFocus();
                    return;
//                } else if (maLoaiHoaDon==1) {
//                    int availableQuantity = sanPhamDao.getSoLuongSanPham(maSp); // Get the available quantity from the warehouse
//                    int requestedQuantity = Integer.parseInt(soluong);
//
//                    if (requestedQuantity > availableQuantity) {
//                        Toast.makeText(HoaDonCtActivity.this, "Số lượng trong kho không đủ", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
                } else {
                    hoaDonCt = new CtHoaDon();
                    hoaDonCt.setSoLuong(Integer.parseInt(soluong));
                    hoaDonCt.setMaHoaDon(maHd);
                    hoaDonCt.setMaSp(maSp);
                    hoaDonCt.setDonGia(giatien);
                    hoaDonCt.setMaLoaiHoaDon(maLoaiHoaDon);
                    // Kiểm tra xem sản phẩm đã tồn tại trong hoá đơn chưa
                    boolean isProductExists = false; //sản phẩm tồn tại
                    int existingProductIndex = -1; //index sản phẩm hiện có
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getMaSp() == maSp) {
                            isProductExists = true;
                            existingProductIndex = i;
                            break;
                        }
                    }
                    if (isProductExists) {
                        // Cập nhật số lượng sản phẩm nếu sản phẩm đã tồn tại trong hoá đơn
                        int newQuantity = list.get(existingProductIndex).getSoLuong() + Integer.parseInt(soluong);
                        list.get(existingProductIndex).setSoLuong(newQuantity);
                    } else {
                        // Thêm sản phẩm mới vào danh sách chi tiết hoá đơn
                        if (ctHoaDonDao.insertHoaDonCt(hoaDonCt)) {
                            list.add(hoaDonCt);
                        } else {
                            Toast.makeText(HoaDonCtActivity.this, "Thêm fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                    // Cập nhật lại ListView
                    adapter.notifyDataSetChanged();
                    // Cập nhật lại tổng tiền
                    int tongTien = adapter.tinhTongTien();
                    tvTongTien.setText(tongTien + " $");
                    if (maLoaiHoaDon == 0) {
                        sanPhamDao.updateSoLuong(maSp, hoaDonCt.getSoLuong());
                    } else {
                        sanPhamDao.updateSoLuong(maSp, -hoaDonCt.getSoLuong());
                    }


                    Toast.makeText(HoaDonCtActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    edtSoLuong.setText("");
                }
            }
        });
    }


    public void capNhapLv() {
        list = (ArrayList<CtHoaDon>) ctHoaDonDao.getAll(maHd);
        adapter = new CtHoaDonAdapter(this, list);
        adapter.setOnDeleteSuccessListener(new CtHoaDonAdapter.OnDeleteSuccessListener() {
            @Override
            public void onDeleteSuccess() {
                int tongTien = adapter.tinhTongTien();
                tvTongTien.setText(tongTien + " $");
            }
        });
        lvSp.setAdapter(adapter);
        int tongTien = adapter.tinhTongTien();
        tvTongTien.setText(tongTien + " $");
    }


}