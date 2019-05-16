package qwy.anenda.com.baselib.utils;
 /*
 -----------------------------------------------------------------
 * Author: yzw
 * Create: 2019/2/28
 * Descride:
 *
 *-----------------------------------------------------------------
 */

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class EditTextTools {
    public static void addclerListener(final EditText e1, final ImageView m1, final TextView tv, final View view, final Drawable left, final Drawable left2, final Drawable right) {

        e1.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (right != null) {
                    right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());
                }
                if (!TextUtils.isEmpty(s.toString())) {
                    if (left != null) {
                        left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());
                    }
                    view.setBackgroundColor(Color.parseColor("#FAC000"));
                    if (tv != null) {
                        tv.setCompoundDrawables(left, null, right, null);
                    } else {
                        e1.setCompoundDrawables(left, null, right, null);
                    }

                } else {
                    if (left2 != null) {
                        left2.setBounds(0, 0, left2.getMinimumWidth(), left2.getMinimumHeight());
                    }
                    view.setBackgroundColor(Color.parseColor("#D2D2D2"));
                    if (tv != null) {
                        tv.setCompoundDrawables(left2, null, right, null);
                    } else {
                        e1.setCompoundDrawables(left2, null, right, null);
                    }
                }

            }
        });

    }

}
