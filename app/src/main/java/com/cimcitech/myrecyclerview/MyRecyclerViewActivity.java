package com.cimcitech.myrecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cimcitech.myrecyclerview.adapter.MyRecyclerViewAdapter;
import com.cimcitech.myrecyclerview.base.BaseRvAdapter;
import com.cimcitech.myrecyclerview.model.RecyclerViewVo;
import com.cimcitech.myrecyclerview.widget.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyRecyclerViewActivity extends AppCompatActivity {

    @Bind(R.id.recyclerView)
    MyRecyclerView recyclerView;

    private MyRecyclerViewAdapter adapter;
    private List<RecyclerViewVo> data = new ArrayList<>();
    private int pageNum = 1; //分页加载的起始页码数、每上拉一次自增1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recycler_view);
        ButterKnife.bind(this);
        initXmlViewData();
    }

    private void initXmlViewData() {
        //头部View
        View headView = LayoutInflater.from(MyRecyclerViewActivity.this).inflate(R.layout.recycler_view_head_layout, null);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        headView.setLayoutParams(lp);

        //recyclerView 设置
        recyclerView.setVLinerLayoutManager();
        recyclerView.addHeaderView(headView);

        //数据填充
        adapter = new MyRecyclerViewAdapter(MyRecyclerViewActivity.this, data);
        adapter.setOnRecyclerViewItemClickListener(new BaseRvAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //条目点击事件
            }
        });
        recyclerView.setVLinerLayoutManager();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLoadingListener(new MyRecyclerView.LoadingListener() {
            @Override
            public void onLoadMore() {
                pageNum++;

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(3000);//休眠3秒
                            initData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
        initData();
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            RecyclerViewVo recyclerViewVo = new RecyclerViewVo();
            recyclerViewVo.setString((i + 1) + "");
            data.add(recyclerViewVo);
        }

        if (data.size() < 50) {
            if (data != null && data.size() > 0) {
                adapter.addData(data);
                recyclerView.loadMoreComplete();
            } else {
                recyclerView.noMoreLoading();
            }
        } else {
            recyclerView.noMoreLoading();
        }

    }
}
