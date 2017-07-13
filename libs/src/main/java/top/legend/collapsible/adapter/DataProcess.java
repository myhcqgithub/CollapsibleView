package top.legend.collapsible.adapter;

import java.util.List;

/**
 * Created by hcqi on.
 * Des:DataProcess
 * Do not use
 * Date: 2017/7/12
 */

public interface DataProcess<T> {
    void setData(List<T> newData);

    void addData(int position, List<T> newData);

    void addData(int position, T newData);

    void addData(T newData);

    void setData(int position, T newData);

    List<T> getData();

    int dataSize();

}
