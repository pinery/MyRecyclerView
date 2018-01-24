package com.cimcitech.myrecyclerview.adapter;

import android.content.Context;
import com.cimcitech.myrecyclerview.R;
import com.cimcitech.myrecyclerview.base.BaseRvAdapter;
import com.cimcitech.myrecyclerview.base.BaseViewHolder;
import com.cimcitech.myrecyclerview.model.RecyclerViewVo;
import com.cimcitech.myrecyclerview.utils.Config;

import java.util.List;

/**
 * Created by dapineapple on 2018/1/24.
 */

public class MyRecyclerViewAdapter extends BaseRvAdapter<RecyclerViewVo> {

    private Context context;

    public MyRecyclerViewAdapter(Context context, List<RecyclerViewVo> data) {
        super(Config.CONTEXT, R.layout.recycler_view_item_layout, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, RecyclerViewVo item) {
        helper.setText(R.id.text_tv, item.getString());
        //context.startActivity(new Intent(context, LoginActivity.class));
    }
}
