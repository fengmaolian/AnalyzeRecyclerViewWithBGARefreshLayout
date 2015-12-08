package com.example.recycleradapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.analyzerecyclerviewwithbgarefreshlayout.R;
import java.util.List;
/**
 * Created by fml on 2015/12/3 0003.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private LayoutInflater mLayoutInflater;
    protected List<String> mListData;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    //定义接口
    public interface OnItemClickListener{
        void onItemClick(View v,int position);
        void onItemLongClick(View v,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener ;
    }
    public RecyclerViewAdapter(Context context,List<String> datas){
        this.mListData = datas;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }
    //创建ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("TAG", "Hellow");
        View v = mLayoutInflater.inflate(R.layout.item,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    //绑定ViewHolder
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTextView.setText(mListData.get(position));
        setOnListtener(holder);
    }
    //触发
    protected void setOnListtener(final RecyclerView.ViewHolder holder){
        if(mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPosition = holder.getPosition();
                    mOnItemClickListener.onItemClick(holder.itemView,layoutPosition);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int layoutPosition = holder.getPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView,layoutPosition);
                    return true;
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return mListData.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_text);
        }
    }
}
