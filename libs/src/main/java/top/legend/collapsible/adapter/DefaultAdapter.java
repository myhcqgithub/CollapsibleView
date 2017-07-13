package top.legend.collapsible.adapter;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import top.legend.collapsible.adapter.holder.DefaultHolder;


/**
 * Created by hcqi on.
 * Des:DefaultAdapter for external use
 * Some IAdapter methods are implemented
 * <p>
 * Date: 2017/7/12
 */

public abstract class DefaultAdapter<T> implements IAdapter<T, DefaultHolder> {

    DataSetObservable mDataSetObservable = new DataSetObservable();

    protected List<T> mData;
    protected int mDefaultLayout = -1;
    protected ViewGroup mViewGroup;

    public DefaultAdapter(List<T> data) {
        this.mData = data;
    }

    public DefaultAdapter(List<T> data, int defaultLayout) {
        this.mData = data;
        this.mDefaultLayout = defaultLayout;
    }

    @Override
    public T getItem(int position) {
        if (mData != null && !mData.isEmpty()) {
            return mData.get(position);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mData == null || mData.isEmpty() ? 0 : mData.size();
    }

    @Override
    public List<T> getData() {
        return mData;
    }

    @Override
    public DefaultHolder onCreateHolder(ViewGroup parent) {
        mViewGroup = parent;
        Context context = parent.getContext();
        if (mDefaultLayout != -1) {
            View view = LayoutInflater.from(context).inflate(mDefaultLayout, parent, false);
            return new DefaultHolder(view);
        }


        return null;
    }

    @Override
    public void onBindHolder(DefaultHolder holder, int position) {
        if (holder == null) return;
        bind(holder, position, mData.get(position));

    }


    public DefaultAdapter<T> setDefaultLayout(int defaultLayout) {
        mDefaultLayout = defaultLayout;
        return this;
    }

    @Override
    public void notifyDataSetChanged() {
        mDataSetObservable.notifyChanged();

    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    public abstract void bind(DefaultHolder defaultHolder, int position, T item);

}
