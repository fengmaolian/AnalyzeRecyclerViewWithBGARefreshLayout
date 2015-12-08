package com.example.recycleradapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.analyzerecyclerviewwithbgarefreshlayout.R;
import com.example.sliding.RecyclerUtils;
import com.example.sliding.SlidingButtonView;
import java.util.List;
/**
 * Created by fml on 2015/12/3 0003.
 */
public class SlidingRecyclerViewAdapter extends RecyclerView.Adapter<SlidingRecyclerViewAdapter.ViewHolder> implements SlidingButtonView.IonSlidingButtonListener{
    private LayoutInflater mLayoutInflater;
    protected List<String> mListData;
    private Context mContext;
    private SlidingButtonView mMenu = null;
    private IonSlidingViewClickListener mIonSlidingViewClickListener;
    //定义接口
    public interface IonSlidingViewClickListener {
        void onItemClick(View view, int position);
        void onLongItemClick(View view, int position);
        void onDeleteBtnCilck(View view, int position);
    }

    //设置监听
    public void setDeleteLister(IonSlidingViewClickListener iDeleteBtnClickListener){
        if(iDeleteBtnClickListener != null){
            mIonSlidingViewClickListener = iDeleteBtnClickListener;
        }
    }
    public SlidingRecyclerViewAdapter(Context context, List<String> datas){
        this.mListData = datas;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }
    //创建ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("TAG", "Hellow");
        View v = mLayoutInflater.inflate(R.layout.item_sliding_delelte,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        ((SlidingButtonView) v).setSlidingButtonListener(SlidingRecyclerViewAdapter.this);
        return viewHolder;
    }
    //绑定ViewHolder
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTextView.setText(mListData.get(position));
        //设置内容布局的宽为屏幕宽度
        holder.mViewGroup.getLayoutParams().width = RecyclerUtils.getScreenWidth(mContext);
        /** 触发点击事件 */
        holder.mViewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Log.i("TAG","点击内容");
                //判断是否有删除菜单打开
                if (menuIsOpen()) {
                    Log.i("TAG","菜单处于打开状态");
                    //关闭菜单
                    closeMenu();
                } else {
                    Log.i("TAG","菜单处于关闭状态");
                    int n = holder.getLayoutPosition();
                    mIonSlidingViewClickListener.onItemClick(v, n);
                }
            }
        });
        /** 触发长点击事件 */
        holder.mViewGroup.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
            Log.i("TAG", "点击内容");
            //判断是否有删除菜单打开
            if (menuIsOpen()) {
                Log.i("TAG", "菜单处于打开状态");
                //关闭菜单
                closeMenu();
            } else {
                Log.i("TAG", "菜单处于关闭状态");
                int n = holder.getLayoutPosition();
                mIonSlidingViewClickListener.onLongItemClick(v, n);
            }
            return true;
            }
        });
        /** 触发删除事件 */
        holder.mDeleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG", "点击");
                int n = holder.getLayoutPosition();
                mIonSlidingViewClickListener.onDeleteBtnCilck(v, n);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView , mDeleteText;
        ViewGroup mViewGroup;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_sliding_text);
            mViewGroup = (ViewGroup) itemView.findViewById(R.id.item_sliding_lay);
            mDeleteText = (TextView) itemView.findViewById(R.id.item_sliding_delete);
        }
    }



    @Override
    public void onMenuIsOpen(View view) {
        Log.i("TAG", "onMenuIsOpen   23");
        mMenu = (SlidingButtonView) view;
        Log.i("TAG","删除菜单打开信息接收");
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        Log.i("TAG","关闭菜单");
        mMenu.closeMenu();
        mMenu = null;

    }
    /**
     * 判断是否有菜单打开
     */
    public Boolean menuIsOpen() {
        if(mMenu != null){
            Log.i("asd", "删除菜单处于打开状态");
            return true;
        }
        Log.i("asd", "删除菜单处于关闭状态");
        return false;
    }
    @Override
    public void onDownOrMove(SlidingButtonView slidingButtonView) {
        Log.i("TAG" , "onDownOrMove    22");
        if(menuIsOpen()){
            if(mMenu != slidingButtonView){
                Log.i("TAG", "关闭删除菜单111");
                closeMenu();
            }
        }
    }
}
