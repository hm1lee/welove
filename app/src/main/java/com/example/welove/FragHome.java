package com.example.welove;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class FragHome extends Fragment implements View.OnClickListener {
    private View view;
    ImageView imageView = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_home, container, false);
        ImageButton btnSearch = view.findViewById(R.id.search2);
        btnSearch.setOnClickListener(this);

        Button btnClick = view.findViewById(R.id.click);
        btnClick.setOnClickListener(this);

        ImageView imageView = view.findViewById(R.id.imageView);//카페 이미지뷰
        Button prebutton = view.findViewById(R.id.prebutton);//사진 뒤로 넘기기 버튼
        Button afterbutton = view.findViewById(R.id.afterbutton);//사진 앞으로 넘기기 버튼
        imageView.setOnClickListener(this);
        prebutton.setOnClickListener(this);
        afterbutton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search2 :
                Intent intent = new Intent(getContext(),Search.class);
                startActivity(intent);
                break;
            case R.id.click :
                Intent intent2 = new Intent(getContext(),Click.class);
                startActivity(intent2);
                break;
                /*
            case R.id.prebutton ://이전 버튼 눌렀을 때 이미지 전환
                imageView.setImageResource(R.drawable.main1);
                break;
            case R.id.afterbutton ://이후 버튼 눌렀을 때 이미지 전환
                imageView.setImageResource(R.drawable.main2);
*/
        }
    }



}
