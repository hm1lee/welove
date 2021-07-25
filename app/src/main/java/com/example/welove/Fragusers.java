package com.example.welove;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragusers extends Fragment implements View.OnClickListener {
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_users, container, false);
        Button loginButton = (Button) view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginButton :
                Intent intent = new Intent(getContext(),Login.class);
                startActivity(intent);
                break;
        }
    }
}