package com.shiqkuangsan.mycustomviews.ui.custom.swipeback.app;

import com.shiqkuangsan.mycustomviews.ui.custom.swipeback.utils.SwipeBackLayout;

public interface SwipeBackActivityBase {
     SwipeBackLayout getSwipeBackLayout();

      void setSwipeBackEnable(boolean enable);

     void scrollToFinishActivity();

}
