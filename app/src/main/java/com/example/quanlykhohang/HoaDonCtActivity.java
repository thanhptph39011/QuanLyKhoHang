package com.example.quanlykhohang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.quanlykhohang.Dao.HoaDonDao;
import com.example.quanlykhohang.Dao.KhoDao;
import com.example.quanlykhohang.Dao.SanPhamDao;
import com.example.quanlykhohang.Fragments.HoaDonFragment;
import com.example.quanlykhohang.Model.CtHoaDon;
import com.example.quanlykhohang.Model.SanPham;

import java.util.ArrayList;

public class HoaDonCtActivity extends AppCompatActivity {
    ListView lvSp;
    EditText edtsoHd, edtSoLuong;
    Spinner spnSanPham;
    TextView tvTongTien, tvLuuHoaDon;
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
    private int trangThai;

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
        tvLuuHoaDon = findViewById(R.id.tvluuHoaDon);
        imgBack = findViewById(R.id.imgBack);
        //
        ctHoaDonDao = new CtHoaDonDao(this);
        //
        maHd = getIntent().getIntExtra("maHd", 0);
        maLoaiHoaDon = getIntent().getIntExtra("maLoaiHoaDon", 0);
        trangThai = getIntent().getIntExtra("trangThai", 0);
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
        tvLuuHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HoaDonCtActivity.this);
                builder.setTitle("Lưu hoá đơn");
                builder.setIcon(R.drawable.baseline_warning_24);
                builder.setMessage("Bạn có chắc chắn muốn lưu hoá đơn không");
                builder.setCancelable(true);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        btnSave.setVisibility(View.GONE);
                        edtSoLuong.setVisibility(View.GONE);
                        tvLuuHoaDon.setVisibility(View.GONE);
                        spnSanPham.setVisibility(View.GONE);
                        HoaDonDao hoaDonDao = new HoaDonDao(HoaDonCtActivity.this);
                        hoaDonDao.updateTrangThaiHD(maHd, 0);
                        Toast.makeText(HoaDonCtActivity.this, "Lưu Succ", Toast.LENGTH_SHORT).show();
                        //
//                        if (maLoaiHoaDon == 0) {
//                            sanPhamDao.updateSoLuong(maSp, hoaDonCt.getSoLuong());
//                        } else {
//                            sanPhamDao.updateSoLuong(maSp, -hoaDonCt.getSoLuong());
//                        }
                        // Khởi chạy HoaDonFragment
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        Fragment hoaDonFragment = new HoaDonFragment();
                        ft.replace(android.R.id.content, hoaDonFragment);
                        ft.commit();
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
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (trangThai == 0) {
            btnSave.setVisibility(View.GONE);
            edtSoLuong.setVisibility(View.GONE);
            tvLuuHoaDon.setVisibility(View.GONE);
            spnSanPham.setVisibility(View.GONE);
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soluong = edtSoLuong.getText().toString();
                if (soluong.isEmpty()) {
                    Toast.makeText(HoaDonCtActivity.this, "Nhập số lượng", Toast.LENGTH_SHORT).show();
                    edtSoLuong.requestFocus();
                    return;
                }
                if (maLoaiHoaDon == 1) {
                    KhoDao khoDao = new KhoDao(HoaDonCtActivity.this);
                    if (Integer.parseInt(soluong) > khoDao.getSLConLai(maSp)) {
                        Toast.makeText(HoaDonCtActivity.this, "Số lượng trong kho không đủ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }


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
        });
    }


    public void capNhapLv() {
        list = (ArrayList<CtHoaDon>) ctHoaDonDao.getAll(maHd);
        adapter = new CtHoaDonAdapter(this, list, trangThai,maLoaiHoaDon);
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