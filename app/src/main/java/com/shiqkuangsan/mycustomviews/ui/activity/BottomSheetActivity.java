package com.shiqkuangsan.mycustomviews.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.utils.MyLogUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import static android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED;
import static android.support.design.widget.BottomSheetBehavior.STATE_DRAGGING;
import static android.support.design.widget.BottomSheetBehavior.STATE_EXPANDED;
import static android.support.design.widget.BottomSheetBehavior.STATE_HIDDEN;
import static android.support.design.widget.BottomSheetBehavior.STATE_SETTLING;

/**
 * Created by shiqkuangsan on 2017/01/03.
 * <p>
 * author: shiqkuangsan
 * description: 底部上拉菜单,Api23之后系统封装了(另外还有BottomSheetDialog,BottomSheetDialogFragment)
 */
@ContentView(R.layout.activity_bottom_sheet)
public class BottomSheetActivity extends AppCompatActivity {

    @ViewInject(R.id.ll_bottom_sheet)
    LinearLayout ll_bottom_sheet;
    @ViewInject(R.id.rl_bottomsheet_head)
    RelativeLayout rl_head;
    @ViewInject(R.id.iv_bottom_content_arrow)
    ImageView iv_arrow;

    private BottomSheetBehavior<LinearLayout> behavior;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_bottomsheet_act);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_bottomsheet_act);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });

        initBottomSheet();
    }

    private int CURRENT_STATE = -1;

    private void initBottomSheet() {
        behavior = BottomSheetBehavior.from(ll_bottom_sheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                CURRENT_STATE = newState;
                switch (newState) {
                    // 这是中间状态，此时用户直接上下拖动 bottom sheet。
                    case STATE_DRAGGING:
                        MyLogUtil.d("BottomSheet状态: STATE_DRAGGING");
                        showToast("BottomSheet状态: STATE_DRAGGING");
                        break;

                    // 视图被释放之后到达最终位置之间的瞬间。
                    case STATE_SETTLING:
                        MyLogUtil.d("BottomSheet状态: STATE_SETTLING");
                        showToast("BottomSheet状态: STATE_SETTLING");
                        break;

                    // bottom sheet完全展开的状态。 整个bottom sheet都是可见的（如果它的高度小于包含它的CoordinatorLayout）或者整个CoordinatorLayout都是填满的。
                    case STATE_EXPANDED:
                        iv_arrow.setImageResource(R.drawable.img_arrow_down);
                        MyLogUtil.d("BottomSheet状态: STATE_EXPANDED");
                        showToast("BottomSheet状态: STATE_EXPANDED");
                        break;

                    // 这是折叠状态 ，也是默认的状态。只是在底部边沿显示布局的一部分。其高度可以使用 app:behavior_peekHeight 属性来控制（默认是0）。
                    case STATE_COLLAPSED:
                        iv_arrow.setImageResource(R.drawable.img_arrow_up);
                        MyLogUtil.d("BottomSheet状态: STATE_COLLAPSED");
                        showToast("BottomSheet状态: STATE_COLLAPSED");
                        break;

                    // 默认禁用是的(使用app:behavior_hideable属性来启用 ), 如果这个启用，用户可以在 bottom sheet中下滑以完全隐藏bottom sheet
                    case STATE_HIDDEN:
                        MyLogUtil.d("BottomSheet状态: STATE_HIDDEN");
                        showToast("BottomSheet状态: STATE_HIDDEN");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                MyLogUtil.d("BottomSheet滑动中...");
            }
        });
    }


    @Event(value = {R.id.rl_bottomsheet_head, R.id.tv_bottom_hello})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_bottomsheet_head:
                if(CURRENT_STATE == -1 || CURRENT_STATE == STATE_COLLAPSED) {
                    behavior.setState(STATE_EXPANDED);
                    CURRENT_STATE = STATE_EXPANDED;
                    return;
                }
                if (CURRENT_STATE == STATE_EXPANDED) {
                    behavior.setState(STATE_COLLAPSED);
                    CURRENT_STATE = STATE_COLLAPSED;
                }
                break;

            case R.id.tv_bottom_hello:
                MyLogUtil.d("点击HelloWorld");
                if (CURRENT_STATE != STATE_COLLAPSED ) {
                    behavior.setState(STATE_COLLAPSED);
                    CURRENT_STATE = STATE_COLLAPSED;
                }
                break;
        }

    }


    protected Toast toast;

    protected void showToast(String msg) {
        if (toast != null) {
            toast.setText(msg + "");
        } else
            toast = Toast.makeText(this, msg + "", Toast.LENGTH_SHORT);
        toast.show();
    }

}
