package com.cimcitech.myrecyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cimcitech.myrecyclerview.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecyclerViewActivity extends AppCompatActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private Handler handler = new Handler();
    private boolean isLoading;
    private List<String> data = new ArrayList<>();
    private int pageNum = 1; //分页加载的起始页码数、每上拉一次自增1
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);
        initViewData();
    }

    /***上下拉动刷新***/
    public void initViewData() {
        adapter = new RecyclerViewAdapter(RecyclerViewActivity.this, data);
        swipeRefreshLayout.setColorSchemeResources(R.color.blueStatus);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //下拉刷新
                        data.clear(); //清除数据
                        pageNum = 1;
                        isLoading = false;
                        initData();
                    }
                }, 1000);
            }
        });
        //final LinearLayoutManager layoutManager = new GridLayoutManager(RecyclerViewActivity.this, 2);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(RecyclerViewActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0)
                        ? 0 : recyclerView.getChildAt(0).getTop();
                if (topRowVerticalPosition > 0) {
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //上拉加载
                                if (true) { //判断是否有分页:true 有;false 无
                                    pageNum++;
                                    initData();
                                }
                                isLoading = false;
                            }
                        }, 1000);
                    }
                }
            }
        });

        //给List添加点击事件
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onItemLongClickListener(View view, int position) {
            }
        });

        initData();
    }

    private void initData() {
        if (true) {// 判断网络请求到的数据是否为空
            //请求到的数据不为空添加到data中
            for (int i = 0; i < 10; i++)
                data.add((i + 1) + "");
            if (!adapter.isNotMoreData()) { //判断是否有分页、或者这里直接给true让他继续加载
                adapter.setNotMoreData(false); //设置是否有分页显示加载中或者已经加载全部
            } else
                adapter.setNotMoreData(true);
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
            adapter.notifyItemRemoved(adapter.getItemCount());
        } else {
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);

        }
    }
}
