package top.legend.collapsible;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import top.legend.collapsible.adapter.IAdapter;
import top.legend.collapsible.adapter.holder.BaseHolder;
import top.legend.collapsible.listener.OnItemClickListener;
import top.legend.collapsible.listener.OnItemLongClickListener;
import top.legend.collapsible.listener.ToggleListener;


/**
 * Created by hcqi on.
 * Des:Retractable, expandable layout, such as ListView
 * <p>
 * Date: 2017/7/12
 */

public class CollapsibleLayout extends LinearLayout implements View.OnClickListener {
    private static final String TAG = "ExpandLayout";
    //init params
    private Drawable mRightIconDrawable;
    private String mExpandText, mCollapseText;
    private int mTextColor, mTextSize;
    private boolean mIconAnimation, hasExpandView;


    //Is the click and hold status
    private boolean hasClickCollapseState;

    //views...
    private TextView mTvTips;
    private View mBottomView;
    private ImageView mImageArrow;

    //instance
    private IAdapter mAdapter;
    private List<Object> mOldData = new ArrayList<>();

    private DataSetObserverImpl mObserver;
    //animation
    RotateAnimation animation;


    //listener
    private ToggleListener mToggleListener;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    //
    private int mDefaultShowCount;
    private int mDefaultItemViewHeight;
    private int mItemSumViewHeight;
    private ValueAnimator mValueAnimator;
    private LinearLayout mListLayout;


    public CollapsibleLayout(Context context) {
        this(context, null);
    }

    public CollapsibleLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CollapsibleLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //Get  attributes
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.expand_layout);
        mRightIconDrawable = ta.getDrawable(R.styleable.expand_layout_right_icon);
        mExpandText = ta.getString(R.styleable.expand_layout_expand_text);
        mCollapseText = ta.getString(R.styleable.expand_layout_collapse_text);
        mTextColor = ta.getColor(R.styleable.expand_layout_text_color,
                getResources().getColor(R.color.colorBlack90));
        mTextSize = ta.getDimensionPixelSize(R.styleable.expand_layout_text_size, 16);
        mIconAnimation = ta.getBoolean(R.styleable.expand_layout_icon_animation, true);
        hasExpandView = ta.getBoolean(R.styleable.expand_layout_has_expand_view, true);
        mDefaultShowCount = ta.getInteger(R.styleable.expand_layout_default_show_count, 2);
        ta.recycle();
        initView();
        initParams();

    }

    private void initView() {
        mBottomView = LayoutInflater.from(getContext()).inflate(R.layout.item_tips_layout, this, false);
        mTvTips = (TextView) mBottomView.findViewById(R.id.tv_tips);
        mImageArrow = (ImageView) mBottomView.findViewById(R.id.image_arrow);
        mBottomView.setOnClickListener(this);

        mListLayout = new LinearLayout(getContext());
        mListLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mListLayout.setOrientation(VERTICAL);

        //Gets some of the configuration of the parent view and uses it
        int showDividers = getShowDividers();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            Drawable dividerDrawable = getDividerDrawable();
            mListLayout.setDividerDrawable(dividerDrawable);
        }
        mListLayout.setShowDividers(showDividers);
    }

    //init params
    private void initParams() {
        mCollapseText = TextUtils.isEmpty(mCollapseText) ? getResources().getString(R.string.string_collapse) : mCollapseText;
        mExpandText = TextUtils.isEmpty(mExpandText) ? getResources().getString(R.string.string_expand) : mExpandText;
        mTvTips.setText(mExpandText);
        mTvTips.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        mTvTips.setTextColor(mTextColor);

        //setup image drawable
        if (mImageArrow != null) {
            mImageArrow.setImageDrawable(mRightIconDrawable);
        }

    }

//    @Override
//    protected Parcelable onSaveInstanceState() {
//        log("onSaveInstanceState");
//        super.onSaveInstanceState();
//        Bundle bundle = new Bundle();
//        bundle.putString("mExpandText", mExpandText);
//        bundle.putString("mCollapseText", mCollapseText);
//        bundle.putInt("mTextColor", mTextColor);
//        bundle.putInt("mTextSize", mTextSize);
//        bundle.putBoolean("mIconAnimation", mIconAnimation);
//        bundle.putBoolean("hasExpandView", hasExpandView);
//        return bundle;
//
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Parcelable state) {
//        log("onRestoreInstanceState");
//        if (state instanceof Bundle) {
//            Bundle bundle = (Bundle) state;
//            mExpandText = bundle.getString("mExpandText");
//            mCollapseText = bundle.getString("mCollapseText");
//            mTextColor = bundle.getInt("mTextColor");
//            mTextSize = bundle.getInt("mTextSize");
//            mIconAnimation = bundle.getBoolean("mIconAnimation");
//            hasExpandView = bundle.getBoolean("hasExpandView");
//            super.onRestoreInstanceState(bundle);
//        }
//    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mImageArrow != null) {

            mImageArrow.clearAnimation();
        }
        if (animation != null) {
            animation.cancel();
            animation = null;
        }
        if (mListLayout != null) {
            mListLayout.clearAnimation();
        }
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
            mValueAnimator = null;
        }
        if (mAdapter != null) {
            mAdapter.unregisterDataSetObserver(mObserver);
        }

    }

    @Override
    public void onClick(View v) {
        toggle();

    }

    private void toggle() {
        if (hasClickCollapseState) {
            collapse();
            hasClickCollapseState = false;
        } else {
            hasClickCollapseState = true;
            expand();
        }
        startArrowAnimation(hasClickCollapseState, mIconAnimation);
        if (mToggleListener != null) {
            mToggleListener.toggle(this, hasClickCollapseState);
        }
        log("toggle");
    }

    //触发的展开状态
    public void expand() {
        if (getChildCount() > 1) {
            startPanelAnimation(true);
            mTvTips.setText(mCollapseText);
        }
        log("expand");


    }

    //触发的收起状态
    public void collapse() {
        if (getChildCount() > 1) {
            mTvTips.setText(mExpandText);
            startPanelAnimation(false);
        }
        log("collapse");


    }

    //start view animation...
    public void startPanelAnimation(boolean expand) {
        if (expand) {
            mValueAnimator = ValueAnimator.ofInt(mDefaultItemViewHeight, mItemSumViewHeight);
        } else {
            mValueAnimator = ValueAnimator.ofInt(mItemSumViewHeight, mDefaultItemViewHeight);
        }

        mValueAnimator.setDuration(500);
        mValueAnimator.start();
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer animatedValue = (Integer) animation.getAnimatedValue();
                log("animatedValue:  " + animatedValue);
                mListLayout.setLayoutParams(new LayoutParams(
                        mListLayout.getWidth(), animatedValue));
            }
        });
    }

    //animation
    public void startArrowAnimation(boolean has, boolean animationToggle) {

        if (has) {
            animation = new RotateAnimation(0, 180, mImageArrow.getHeight() / 2, mImageArrow.getHeight() / 2);
        } else {
            animation = new RotateAnimation(180, 360, mImageArrow.getHeight() / 2, mImageArrow.getHeight() / 2);
        }
        animation.setDuration(animationToggle ? 500 : 0);
        animation.setFillAfter(true);
        mImageArrow.startAnimation(animation);
    }

    //If ListView uses
    public CollapsibleLayout setAdapter(IAdapter adapter) {
        if (adapter == null) {
            throw new NullPointerException("adapter is null");
        }
        mAdapter = adapter;
        if (!mOldData.isEmpty()) {
            mOldData.clear();
        }
        mOldData.addAll(mAdapter.getData());
        //register observer
        mObserver = new DataSetObserverImpl();
        mAdapter.registerDataSetObserver(mObserver);
        //reset child views...
        if (mListLayout.getChildCount() > 0) mListLayout.removeAllViews();
        if (getChildCount() > 0) removeAllViews();
        //refresh ui...
        refreshView(true);
        return this;
    }

    private boolean hasShowDefault(int index) {
        if (mDefaultShowCount > index) {
            return true;
        }
        return false;
    }

    private void refreshView(boolean init) {
        //reset
        mItemSumViewHeight = 0;
        mDefaultItemViewHeight = 0;
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            BaseHolder holder = mAdapter.onCreateHolder(this);
            mAdapter.onBindHolder(holder, i);
            View itemView = holder.mItemView;
            itemView.measure(0, 0);
            mItemSumViewHeight += itemView.getMeasuredHeight();
            if (hasShowDefault(i)) {
                mDefaultItemViewHeight += itemView.getMeasuredHeight();
            }
            //copy
            final int finalI = i;
            if (init) {
                //add adapter view...
                mListLayout.addView(itemView);

                //call click
                callItemOnClick(itemView, finalI);
                callItemOnLongClick(itemView, finalI);
            } else {
                //Prevent repeated addition of view

                Object item = mAdapter.getItem(i);
                if (mOldData.indexOf(item) < 0) {
                    mListLayout.addView(itemView, i);
                    //call click
                    callItemOnClick(itemView, finalI);
                    callItemOnLongClick(itemView, finalI);
                    mOldData.add(item);
                }
            }

        }
        //These view are initialized at the first time...
        if (init) {
            if (hasExpandView) {
                mListLayout.setLayoutParams(new LayoutParams(
                        LayoutParams.MATCH_PARENT, mDefaultItemViewHeight));
            }
            addView(mListLayout);
            if (hasExpandView) {
                mListLayout.setLayoutParams(new LayoutParams(
                        LayoutParams.MATCH_PARENT, mDefaultItemViewHeight));
                addView(mBottomView);
            }
        }

    }

    //call listener
    private void callItemOnClick(View itemView, final int finalI) {
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callItemClickListener(finalI);
            }
        });
    }

    private void callItemOnLongClick(View itemView, final int finalI) {
        itemView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                boolean b = callItemLongClickListener(finalI);
                return b;
            }
        });
    }

    private void callItemClickListener(int index) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(CollapsibleLayout.this, index, mAdapter.getItem(index));
        }
    }

    private boolean callItemLongClickListener(int index) {
        if (mOnItemLongClickListener != null) {
            return mOnItemLongClickListener.onItemLongClick(CollapsibleLayout.this, index, mAdapter.getItem(index));
        }
        return false;
    }


    //listener...
    public void setToggleListener(ToggleListener toggleListener) {
        mToggleListener = toggleListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    //log...
    private void log(String log) {
        Log.e(TAG, String.format("log: %s", log));
    }

    //observer impl instance
    class DataSetObserverImpl extends DataSetObserver {

        @Override
        public void onChanged() {
            super.onChanged();
            refreshView(false);
        }
    }
}
