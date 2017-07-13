package top.legend.collapsible.adapter;

import android.database.DataSetObserver;
import android.view.ViewGroup;

import java.util.List;

import top.legend.collapsible.adapter.holder.BaseHolder;


/**
 * Created by hcqi on.
 * Des:This interface is used for data coordination and uses it as if it were using ListView,
 * which can be customized if required
 * <p>
 * Date: 2017/7/12
 */

public interface IAdapter<T, V extends BaseHolder> {


    int getItemCount();

    T getItem(int position);

    List<T> getData();

    V onCreateHolder(ViewGroup parent);

    void onBindHolder(V holder, int position);

    void notifyDataSetChanged();

    void registerDataSetObserver(DataSetObserver observer);

    void unregisterDataSetObserver(DataSetObserver observer);


}
