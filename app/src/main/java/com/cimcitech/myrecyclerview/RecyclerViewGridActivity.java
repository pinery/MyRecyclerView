package com.cimcitech.myrecyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cimcitech.myrecyclerview.adapter.RecyclerViewAdapter;
import com.cimcitech.myrecyclerview.adapter.RecyclerViewGridAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecyclerViewGridActivity extends AppCompatActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private Handler handler = new Handler();
    private boolean isLoading;
    private List<String> data = new ArrayList<>();
    private int pageNum = 1; //分页加载的起始页码数、每上拉一次自增1
    private RecyclerViewGridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_grid);
        ButterKnife.bind(this);
        initViewData(2);
    }

    @OnClick({R.id.button2, R.id.button4, R.id.button6, R.id.button8})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.button2:
                data.clear();
                initViewData(2);
                break;
            case R.id.button4:
                data.clear();
                initViewData(4);
                break;
            case R.id.button6:
                data.clear();
                initViewData(6);
                break;
            case R.id.button8:
                data.clear();
                initViewData(8);
                break;
        }
    }

    /***上下拉动刷新***/
    public void initViewData(int horizontalNum) {
        adapter = new RecyclerViewGridAdapter(RecyclerViewGridActivity.this, data);
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
        final LinearLayoutManager layoutManager = new GridLayoutManager(RecyclerViewGridActivity.this, horizontalNum);
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
                                //判断是否有分页:true 有;false 无;一般是由后台接口给的返回来判断
                                //上拉加载
                                if (true) {
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
        adapter.setOnItemClickListener(new RecyclerViewGridAdapter.OnItemClickListener() {
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
            //判断是否有分页、或者这里直接给true让他继续加载
            //这里是和//上拉加载处的条件是一样的，一般是由后台接口给的返回来判断
            if (!adapter.isNotMoreData()) { //判断是否有分页、或者这里直接给true让他继续加载
                adapter.setNotMoreData(false); //设置是否有分页显示set(true)加载中或者set(false)已经加载全部
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
