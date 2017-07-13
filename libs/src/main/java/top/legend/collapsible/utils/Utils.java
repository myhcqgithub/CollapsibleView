package top.legend.collapsible.utils;

import android.content.Context;

/**
 * Created by hcqi on.
 * Des:
 * Date: 2017/7/13
 */

public class Utils {
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
