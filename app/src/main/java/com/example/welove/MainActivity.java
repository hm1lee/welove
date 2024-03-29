package com.example.welove;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private FragHome fragHome;
    private FragMap fragMap;
    private Fragusers fragusers;
    private Fraglike fraglike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        setFrag(0);
                        break;
                    case R.id.map:
                        setFrag(1);
                        break;
                    case R.id.like:
                        setFrag(2);
                        break;
                    case R.id.users:
                        setFrag(3);
                        break;
                }
                return true;
            }
        });
        fragHome = new FragHome();
        fragMap = new FragMap();
        fraglike = new Fraglike();
        fragusers = new Fragusers();
        setFrag(0); //첫 프래그먼트 화면을 무엇으로 지정해줄 것인지 선택

    }

    //프레그먼트 교체가 일어나는 실행문이다.
    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction(); //실제적인 프레그먼트 교체에서 사용
        switch (n) {
            case 0:
                ft.replace(R.id.main_frame,fragHome);
                ft.commit(); //저장의미
                break;
            case 1:
                ft.replace(R.id.main_frame, fragMap);
                ft.commit(); //저장의미
                break;
            case 2:
                ft.replace(R.id.main_frame,fraglike);
                ft.commit(); //저장의미
                break;
            case 3:
                ft.replace(R.id.main_frame,fragusers);
                ft.commit(); //저장의미
                break;

        }
    }

}
