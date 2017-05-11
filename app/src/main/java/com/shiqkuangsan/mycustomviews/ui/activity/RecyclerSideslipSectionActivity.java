package com.shiqkuangsan.mycustomviews.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.shiqkuangsan.mycustomviews.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by shiqkuangsan on 2017/5/11.
 * <p>
 * ClassName: RecyclerSideslipSectionActivity
 * Author: shiqkuangsan
 * Description: 使用类库实现Recycler的侧滑删除和分组黏性头部效果
 */
public class RecyclerSideslipSectionActivity extends AppCompatActivity {

    @ViewInject(R.id.recycler_sideslip)
    LRecyclerView recycler_sideslip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_sideslip_section);
        x.view().inject(this);

        initUI();
    }

    private void initUI() {
        // 进入直接刷新
        recycler_sideslip.setPullRefreshEnabled(true);
        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(3.0f)
                .setPadding(0)
                .setColor(0xff445566)
                .build();
        recycler_sideslip.addItemDecoration(divider);

        // item高度不变是时候设置这句可以提高性能
        recycler_sideslip.setHasFixedSize(true);
        recycler_sideslip.setLayoutManager(new LinearLayoutManager(this));
    }
}
