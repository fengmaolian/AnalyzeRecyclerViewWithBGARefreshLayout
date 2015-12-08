package com.example.analyzerecyclerviewwithbgarefreshlayout;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.baseactivity.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private Button mDefineRefresh , mDefineLoad , mDefineRefreshWidthLoad , mOtherStyles , mSlidingDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView() {
        mDefineRefresh = (Button) findViewById(R.id.main_define_refresh);
        mDefineLoad = (Button) findViewById(R.id.main_define_load);
        mDefineRefreshWidthLoad = (Button) findViewById(R.id.main_define_refreshwidthload);
        mOtherStyles = (Button) findViewById(R.id.main_define_other_styles);
        mSlidingDelete = (Button) findViewById(R.id.main_define_sliding);
        mDefineRefresh.setOnClickListener(this);
        mDefineLoad.setOnClickListener(this);
        mDefineRefreshWidthLoad.setOnClickListener(this);
        mOtherStyles.setOnClickListener(this);
        mSlidingDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /** 自定义刷新 */
            case R.id.main_define_refresh:
                intent(DefineRefreshActivity.class);
            break;
            /** 自定义加载 */
            case R.id.main_define_load:
                intent(DefineLoadActivity.class);
                break;
            /** 自定义刷新和加载 */
            case R.id.main_define_refreshwidthload:
                intent(DefineLoadWithRefreshActivity.class);
                break;
            /** GridView和瀑布流 */
            case R.id.main_define_other_styles:
                intent(DefineOtherStylesActivity.class);
                break;
            /** 侧滑删除 */
            case R.id.main_define_sliding:
                intent(DefineSlidingDeleteActivity.class);
                break;
            default:
                break;
        }
    }
}
