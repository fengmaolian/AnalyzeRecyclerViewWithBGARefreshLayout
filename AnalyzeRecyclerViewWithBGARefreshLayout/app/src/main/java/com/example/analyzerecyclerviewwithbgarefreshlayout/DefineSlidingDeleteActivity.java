package com.example.analyzerecyclerviewwithbgarefreshlayout;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.baseactivity.BaseActivity;
import com.example.bgarefreshlayoutclass.DefineBGARefreshLayout;
import com.example.bgarefreshlayoutviewholder.DefineBAGRefreshWithLoadView;
import com.example.recycleradapter.SlidingRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by fml on 2015/12/4 0004.
 */
public class DefineSlidingDeleteActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate , SlidingRecyclerViewAdapter.IonSlidingViewClickListener {
    private BGARefreshLayout mBGARefreshLayout;
    private RecyclerView mRecyclerView;
    private Context mContext;
    /** title */
    private TextView mTitle;
    /** 数据 */
    private List<String> mListData = new ArrayList<String>();
    /** 一次加载数据的条数 */
    private int DATASIZE = 10;
    /** 数据填充adapter */
    private SlidingRecyclerViewAdapter mSlidingRecyclerViewAdapter = null;
    /** 设置一共请求多少次数据 */
    private int ALLSUM = 0;
    /** 设置刷新和加载 */
    private DefineBAGRefreshWithLoadView mDefineBAGRefreshWithLoadView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_sliding);
        mContext = this;
        initView();
        setBgaRefreshLayout();
        setRecyclerView();
    }

    /** 进入页面首次加载数据 */
    @Override
    protected void onStart() {
        super.onStart();
        mBGARefreshLayout.beginRefreshing();
        onBGARefreshLayoutBeginRefreshing(mBGARefreshLayout);
    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.fml_title);
        mTitle.setText("侧滑删除");
        mBGARefreshLayout = (BGARefreshLayout) findViewById(R.id.define_sliding_bga);
        mRecyclerView = (RecyclerView) findViewById(R.id.define_sliding_recycler);
        //设置刷新和加载监听
        mBGARefreshLayout.setDelegate(this);
    }
    /**
     * 设置 BGARefreshLayout刷新和加载
     * */
    private void setBgaRefreshLayout() {
        mDefineBAGRefreshWithLoadView = new DefineBAGRefreshWithLoadView(mContext , true , true);
        //设置刷新样式
        mBGARefreshLayout.setRefreshViewHolder(mDefineBAGRefreshWithLoadView);
        mDefineBAGRefreshWithLoadView.updateLoadingMoreText("自定义加载更多");
    }
    /** 设置RecyclerView的布局方式 */
    private void setRecyclerView(){
        //垂直listview显示方式
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
    }
    /** 模拟请求网络数据 */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mListData.clear();
                    setData();
                    mBGARefreshLayout.endRefreshing();
                    break;
                case 1:
                    setData();
                    mBGARefreshLayout.endLoadingMore();
                    break;
                case 2:
                    mBGARefreshLayout.endLoadingMore();
                    break;
                default:
                    break;

            }
        }
    };
    /**
     * 添加假数据
     * */
    private void setData() {
        for(int i = 0 ; i < DATASIZE ; i++){
            mListData.add("第" + (ALLSUM * 10 + i) +"条数据");
        }
        if(ALLSUM == 0){
            setRecyclerCommadapter();
        }else{
            mSlidingRecyclerViewAdapter.notifyDataSetChanged();
        }
    }
    /** 数据填充 */
    private void setRecyclerCommadapter() {
        mSlidingRecyclerViewAdapter = new SlidingRecyclerViewAdapter(mContext, mListData);
        mRecyclerView.setAdapter(mSlidingRecyclerViewAdapter);
        mSlidingRecyclerViewAdapter.setDeleteLister(this);
    }
    /** 刷新 */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mDefineBAGRefreshWithLoadView.updateLoadingMoreText("自定义加载更多");
        mDefineBAGRefreshWithLoadView.showLoadingMoreImg();
        ALLSUM = 0;
        handler.sendEmptyMessageDelayed(0 , 2000);
    }
    /** 加载 */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if(ALLSUM == 2){
            mDefineBAGRefreshWithLoadView.updateLoadingMoreText("没有更多数据");
            mDefineBAGRefreshWithLoadView.hideLoadingMoreImg();
            handler.sendEmptyMessageDelayed(2 , 2000);
            return true;
        }
        ALLSUM++;
        handler.sendEmptyMessageDelayed(1 , 2000);
        return true;
    }
    /** 点击事件 */
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(mContext,"点击了 "+position,Toast.LENGTH_SHORT).show();
    }
    /** 长点击事件 */
    @Override
    public void onLongItemClick(View view, int position) {
        Toast.makeText(mContext,"长点击了 "+position,Toast.LENGTH_SHORT).show();
    }
    /** 删除事件 */
    @Override
    public void onDeleteBtnCilck(View view, int position) {
        mListData.remove(position);
        mSlidingRecyclerViewAdapter.notifyDataSetChanged();
    }
}
