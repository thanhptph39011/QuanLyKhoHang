package com.example.quanlykhohang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.quanlykhohang.Fragments.AddUserFragment;
import com.example.quanlykhohang.Fragments.ChangeFragment;
import com.example.quanlykhohang.Fragments.CtHoaDonFragment;
import com.example.quanlykhohang.Fragments.HoaDonFragment;
import com.example.quanlykhohang.Fragments.SanPhamFragment;
import com.example.quanlykhohang.Fragments.TheLoaiFragment;
import com.example.quanlykhohang.Fragments.ThongKeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;


public class HomeActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView nav;
    BottomNavigationView bottomNavigationView;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //ánh xạ
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        nav = findViewById(R.id.nav);
        bottomNavigationView = findViewById(R.id.botomMenu);
// gán toolbar
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //sét mẫu icon về bản gốc
        nav.setItemIconTintList(null);
        view = nav.getHeaderView(0);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.thongKe) {
                    ThongKeFragment fragment = new ThongKeFragment();
                    replaceFrg(fragment);
                } else if (item.getItemId() == R.id.themNguoiDung) {
                    AddUserFragment fragment = new AddUserFragment();
                    replaceFrg(fragment);
                } else if (item.getItemId() == R.id.doiMk) {
                    ChangeFragment fragment = new ChangeFragment();
                    replaceFrg(fragment);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setTitle("Cảnh báo");
                    builder.setIcon(R.drawable.baseline_warning_24);
                    builder.setMessage("Bạn có muốn đăng xuất k?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(HomeActivity.this, "Đăng xuất Succ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
                getSupportActionBar().setTitle(item.getTitle()); //khi click vào item hiển thị tieu de lên toolbar
                drawerLayout.close(); //đóng nav
                return true;
            }


        });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.hoaDon) {
                    HoaDonFragment fragment = new HoaDonFragment();
                    replaceFrg(fragment);
                } else if (item.getItemId() == R.id.CtHoaDon) {
                    CtHoaDonFragment fragment = new CtHoaDonFragment();
                    replaceFrg(fragment);
                } else if (item.getItemId() == R.id.theLoai) {
                    TheLoaiFragment fragment = new TheLoaiFragment();
                    replaceFrg(fragment);
                } else {
                    SanPhamFragment fragment = new SanPhamFragment();
                    replaceFrg(fragment);
                }
                getSupportActionBar().setTitle(item.getTitle());
                return true;
            }
        });
    }

    public void replaceFrg(Fragment frg) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frmnav, frg).commit();
    }
}