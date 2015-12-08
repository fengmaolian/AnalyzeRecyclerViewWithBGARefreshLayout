package com.example.bgarefreshlayoutviewholder;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.analyzerecyclerviewwithbgarefreshlayout.R;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
/**
 * Created by fml on 2015/12/3 0003.
 */
public class DefineBAGRefreshView extends BGARefreshViewHolder {
    private TextView mHeaderStatusTv;
    private ImageView mHeaderArrowIv;
    private ImageView mHeaderChrysanthemumIv;
    private String mPullDownRefreshText = "下拉刷新!!";
    private String mReleaseRefreshText = "释放更新!!";
    private String mRefreshingText = "加载中!!...";
    private boolean isRefreshEnabled = true;
    /**
     * 参数说明：
     * context：上下文
     * isLoadingMoreEnabled：加载更多是否可用
     * isRefreshEnabled：刷新是否可用
     * */
    public DefineBAGRefreshView(Context context, boolean isLoadingMoreEnabled,boolean isRefreshEnabled) {
        super(context, isLoadingMoreEnabled);
        this.isRefreshEnabled = isRefreshEnabled;
    }


    /** 设置下拉显示文字 */
    public void setPullDownRefreshText(String pullDownRefreshText) {
        this.mPullDownRefreshText = pullDownRefreshText;
    }
    /** 设置释放显示文字 */
    public void setReleaseRefreshText(String releaseRefreshText) {
        this.mReleaseRefreshText = releaseRefreshText;
    }
    /** 设置正在刷新显示文字 */
    public void setRefreshingText(String refreshingText) {
        this.mRefreshingText = refreshingText;
    }
    /** 设置刷新布局 */
    public View getRefreshHeaderView() {
        if(this.mRefreshHeaderView == null) {
            this.mRefreshHeaderView = View.inflate(this.mContext, R.layout.header_bga_dodo, (ViewGroup)null);
            this.mRefreshHeaderView.setBackgroundColor(0);
            if(this.mRefreshViewBackgroundColorRes != -1) {
                this.mRefreshHeaderView.setBackgroundResource(this.mRefreshViewBackgroundColorRes);
            }
            if(this.mRefreshViewBackgroundDrawableRes != -1) {
                this.mRefreshHeaderView.setBackgroundResource(this.mRefreshViewBackgroundDrawableRes);
            }
            this.mHeaderStatusTv = (TextView)this.mRefreshHeaderView.findViewById(R.id.tv_normal_refresh_header_status);
            this.mHeaderArrowIv = (ImageView)this.mRefreshHeaderView.findViewById(R.id.iv_normal_refresh_header_arrow);
            this.mHeaderChrysanthemumIv = (ImageView)this.mRefreshHeaderView.findViewById(R.id.iv_normal_refresh_header_chrysanthemum);
            this.mHeaderStatusTv.setText(this.mPullDownRefreshText);
        }
        //刷新不可用
        if(!isRefreshEnabled){
            return null;
        }
        return this.mRefreshHeaderView;
    }
    public void handleScale(float scale, int moveYDistance) {
    }
    public void changeToIdle() {
    }
    //开始下拉
    public void changeToPullDown() {
        this.mHeaderStatusTv.setText(this.mPullDownRefreshText);
        this.mHeaderChrysanthemumIv.setVisibility(View.GONE);
        this.mHeaderArrowIv.setVisibility(View.VISIBLE);
    }

    //下拉到一定程度，可以刷新
    public void changeToReleaseRefresh() {
        this.mHeaderStatusTv.setText(this.mReleaseRefreshText);
        this.mHeaderChrysanthemumIv.setVisibility(View.GONE);
        this.mHeaderArrowIv.setVisibility(View.VISIBLE);
    }

    //已经开始刷新
    public void changeToRefreshing() {
        this.mHeaderStatusTv.setText(this.mRefreshingText);
        this.mHeaderArrowIv.clearAnimation();
        this.mHeaderArrowIv.setVisibility(View.GONE);
        this.mHeaderChrysanthemumIv.setVisibility(View.VISIBLE);
    }
    public void onEndRefreshing() {
        this.mHeaderStatusTv.setText(this.mPullDownRefreshText);
        this.mHeaderChrysanthemumIv.setVisibility(View.GONE);
        this.mHeaderArrowIv.setVisibility(View.VISIBLE);
    }


}
