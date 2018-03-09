package com.shiqkuangsan.mycustomviews.utils.font;

import com.joanzapata.iconify.Icon;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/3/9
 * 内容：自定义字体图片库
 * 最后修改：
 */

public enum TestIcons implements Icon {
    icon_fenxiang('\ue624'),
    icon_bofang('\ue625');

    private char character;

    TestIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
