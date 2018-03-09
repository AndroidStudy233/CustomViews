package com.shiqkuangsan.mycustomviews.utils.font;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/3/9
 * 内容：
 * 最后修改：
 */

public class TestFontModule implements IconFontDescriptor {

    @Override
    public String ttfFileName() {
        return "testfont.ttf";
    }

    @Override
    public Icon[] characters() {
        return TestIcons.values();
    }
}
