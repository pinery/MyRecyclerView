package com.cimcitech.myrecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cimcitech.myrecyclerview.adapter.RecyclerViewAdapter;
import com.cimcitech.myrecyclerview.demo_recyclerview.HomeActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.buttonPanel1)
    Button buttonPanel1;
    @Bind(R.id.buttonPanel2)
    Button buttonPanel2;
    @Bind(R.id.buttonPanel3)
    Button buttonPanel3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.buttonPanel1, R.id.buttonPanel2, R.id.buttonPanel3, R.id.buttonPanel4})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.buttonPanel1:
                startActivity(new Intent(MainActivity.this, MyRecyclerViewActivity.class));
                break;
            case R.id.buttonPanel2:
                startActivity(new Intent(MainActivity.this, RecyclerViewActivity.class));
                break;
            case R.id.buttonPanel3:
                startActivity(new Intent(MainActivity.this, RecyclerViewGridActivity.class));
                break;
            case R.id.buttonPanel4:
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                break;
        }
    }
}
