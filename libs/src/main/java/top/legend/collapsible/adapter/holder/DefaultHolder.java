package top.legend.collapsible.adapter.holder;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by hcqi on.
 * Des:concrete Item  instance
 * For external use
 * <p>
 * Date: 2017/7/12
 */

public class DefaultHolder extends BaseHolder {
    protected SparseArray<View> mViews;

    public DefaultHolder(View rootView) {
        super(rootView);
        mViews = new SparseArray<>();
    }

    protected <T extends View> T findView(@IdRes int id) {
        if (mItemView == null) return null;
        View view = mViews.get(id);
        if (view == null) {
            view = mItemView.findViewById(id);
            mViews.put(id, view);
        }
        return (T) view;
    }

    public DefaultHolder setText(@IdRes int id, String str) {
        TextView view = findView(id);
        if (view != null) {
            view.setText(str);
        }
        return this;
    }

    public DefaultHolder setTextColor(@IdRes int id, int color) {
        TextView view = findView(id);
        if (view != null) {
            view.setTextColor(color);
        }
        return this;
    }

    public DefaultHolder setImageResource(@IdRes int id, int resourceId) {
        ImageView view = findView(id);
        if (view != null) {
            view.setImageResource(resourceId);
        }
        return this;
    }

    public DefaultHolder setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
        View view = findView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public DefaultHolder setBackgroundRes(@IdRes int viewId, @DrawableRes int backgroundRes) {
        View view = findView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }


    public DefaultHolder setImageDrawable(@IdRes int viewId, Drawable drawable) {
        ImageView view = findView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public DefaultHolder setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        ImageView view = findView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public DefaultHolder setAlpha(@IdRes int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            findView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            findView(viewId).startAnimation(alpha);
        }
        return this;
    }


    public DefaultHolder setVisible(@IdRes int viewId, boolean visible) {
        View view = findView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }


    public DefaultHolder linkify(@IdRes int viewId) {
        TextView view = findView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public DefaultHolder setTypeface(@IdRes int viewId, Typeface typeface) {
        TextView view = findView(viewId);
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }


    public DefaultHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = findView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }


    public DefaultHolder setProgress(@IdRes int viewId, int progress) {
        ProgressBar view = findView(viewId);
        view.setProgress(progress);
        return this;
    }

    public DefaultHolder setProgress(@IdRes int viewId, int progress, int max) {
        ProgressBar view = findView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }


    public DefaultHolder setMax(@IdRes int viewId, int max) {
        ProgressBar view = findView(viewId);
        view.setMax(max);
        return this;
    }


    public DefaultHolder setRating(@IdRes int viewId, float rating) {
        RatingBar view = findView(viewId);
        view.setRating(rating);
        return this;
    }


    public DefaultHolder setRating(@IdRes int viewId, float rating, int max) {
        RatingBar view = findView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

}
