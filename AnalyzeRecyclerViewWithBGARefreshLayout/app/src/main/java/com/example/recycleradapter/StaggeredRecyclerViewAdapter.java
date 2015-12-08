package com.example.recycleradapter;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/16 0016.
 */
public class StaggeredRecyclerViewAdapter extends RecyclerViewAdapter{
    private List<Integer> mHeights = new ArrayList<Integer>();
    public StaggeredRecyclerViewAdapter(Context context, List<String> datas, List<Integer> mHeight){
        super(context,datas);
        this.mHeights = mHeight;
    }

    //绑定ViewHolder
    @Override
    public void onBindViewHolder(StaggeredRecyclerViewAdapter.ViewHolder holder, int position) {
        LayoutParams lp = holder.itemView.getLayoutParams();
        lp.height = mHeights.get(position);
        holder.itemView.setLayoutParams(lp);

        holder.mTextView.setText(mListData.get(position));
        setOnListtener(holder);
    }
}

