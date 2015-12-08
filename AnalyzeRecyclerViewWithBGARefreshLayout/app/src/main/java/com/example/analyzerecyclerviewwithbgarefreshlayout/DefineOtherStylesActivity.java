package com.example.analyzerecyclerviewwithbgarefreshlayout;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.baseactivity.BaseActivity;
import com.example.bgarefreshlayoutclass.DefineBGARefreshLayout;
import com.example.bgarefreshlayoutviewholder.DefineOtherStylesBAGRefreshWithLoadView;
import com.example.recycleradapter.RecyclerViewAdapter;
import com.example.recycleradapter.StaggeredRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.List;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by fml on 2015/12/4 0004.
 */
public class DefineOtherStylesActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate{
    private BGARefreshLayout mOtherStylesRefreshLayout;
    private RecyclerView mRecyclerView;

    /** GridView 和 瀑布流*/
    private Button mGridViewBtn , mPuBuLiuBtn;
    /** title */
    private TextView mTitle;
    /** 数据 */
    private List<String> mListData = new ArrayList<String>();
    /** 一次加载数据的条数 */
    private int DATASIZE = 10;
    /** 数据填充adapter */
    private RecyclerViewAdapter mRecyclerViewAdapter = null;
    private StaggeredRecyclerViewAdapter mStaggeredRecyclerViewAdapter = null;
    /** 设置一共请求多少次数据 */
    private int ALLSUM = 0;
    /** 设置刷新和加载 */
    private DefineOtherStylesBAGRefreshWithLoadView mDefineOtherStylesBAGRefreshWithLoadView = null;
    private Context mContext;
    private List<Integer> mHeights = new ArrayList<Integer>();
    /** 标记是GridView还是瀑布流 */
    private int TYPE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_others_style);
        mContext = this;
        initView();
        setBgaRefreshLayout();
        setRecyclerView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mOtherStylesRefreshLayout.beginRefreshing();
        onBGARefreshLayoutBeginRefreshing(mOtherStylesRefreshLayout);
    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.fml_title);
        mTitle.setText("GridView和瀑布流样式");
        mGridViewBtn = (Button) findViewById(R.id.define_other_gridview);
        mPuBuLiuBtn = (Button) findViewById(R.id.define_other_pubuliu);
        mGridViewBtn.setOnClickListener(new click());
        mPuBuLiuBtn.setOnClickListener(new click());
        mOtherStylesRefreshLayout = (BGARefreshLayout) findViewById(R.id.define_other_bga);
        mRecyclerView = (RecyclerView) findViewById(R.id.define_other_recycler);
        //设置刷新和加载监听
        mOtherStylesRefreshLayout.setDelegate(this);
    }
    /**
     * 设置 BGARefreshLayout刷新和加载
     * */
    private void setBgaRefreshLayout() {
        mDefineOtherStylesBAGRefreshWithLoadView = new DefineOtherStylesBAGRefreshWithLoadView(mContext , true , true);
        //设置刷新样式
        mOtherStylesRefreshLayout.setRefreshViewHolder(mDefineOtherStylesBAGRefreshWithLoadView);
        mDefineOtherStylesBAGRefreshWithLoadView.updateLoadingMoreText("自定义加载更多");
    }
    /** 设置RecyclerView的布局方式 */
    private void setRecyclerView(){
        //垂直listview显示方式
//        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext , 3));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3 , StaggeredGridLayoutManager.VERTICAL));
    }
    class click implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                /** GridView */
                case R.id.define_other_gridview:
                    ALLSUM = 0;
                    TYPE = 1;
                    mListData.clear();
                    mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3 , StaggeredGridLayoutManager.VERTICAL));
                    mOtherStylesRefreshLayout.beginRefreshing();
                    onBGARefreshLayoutBeginRefreshing(mOtherStylesRefreshLayout);
                    break;
                /** 瀑布流 */
                case R.id.define_other_pubuliu:
                    ALLSUM = 0;
                    TYPE = 2;
                    mListData.clear();
                    mOtherStylesRefreshLayout.beginRefreshing();
                    onBGARefreshLayoutBeginRefreshing(mOtherStylesRefreshLayout);
                    break;
                default:
                    break;
            }
        }
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
                    mOtherStylesRefreshLayout.endRefreshing();
                    break;
                case 1:
                    setData();
                    mOtherStylesRefreshLayout.endLoadingMore();
                    break;
                case 2:
                    mOtherStylesRefreshLayout.endLoadingMore();
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
            mListData.add("第" + (ALLSUM * DATASIZE + i) +"条数据");
            mHeights.add((int) (100+Math.random()*300));
        }
        if(ALLSUM == 0){
            if(TYPE == 1){
                setRecyclerCommadapter();
            }else{
                setPuBuRecyclerCommadapter();
            }
        }else{
            mRecyclerViewAdapter.notifyDataSetChanged();
        }
    }
    /** GridView数据填充 */
    private void setRecyclerCommadapter() {
        mRecyclerViewAdapter = new RecyclerViewAdapter(mContext , mListData);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        //点击事件
        mRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(mContext, "onclick  " + position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onItemLongClick(View v, int position) {
                Toast.makeText(mContext, "onlongclick  " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
    /** 瀑布流数据填充 */
    private void setPuBuRecyclerCommadapter() {
        mStaggeredRecyclerViewAdapter = new StaggeredRecyclerViewAdapter(mContext , mListData , mHeights);
        mRecyclerView.setAdapter(mStaggeredRecyclerViewAdapter);
        //点击事件
        mStaggeredRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(mContext, "onclick  " + position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onItemLongClick(View v, int position) {
                Toast.makeText(mContext, "onlongclick  " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 刷新
     * */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mDefineOtherStylesBAGRefreshWithLoadView.updateLoadingMoreText("自定义加载更多");
        mDefineOtherStylesBAGRefreshWithLoadView.showLoadingMoreImg();
        ALLSUM = 0;
        handler.sendEmptyMessageDelayed(0 , 2000);
    }
    /**
     * 加载
     * */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if(ALLSUM == 2){
            mDefineOtherStylesBAGRefreshWithLoadView.updateLoadingMoreText("没有更多数据");
            mDefineOtherStylesBAGRefreshWithLoadView.hideLoadingMoreImg();
            handler.sendEmptyMessageDelayed(2 , 2000);
            return true;
        }
        ALLSUM++;
        handler.sendEmptyMessageDelayed(1 , 2000);
        return true;
    }
}
