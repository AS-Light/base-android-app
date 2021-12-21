package com.howard.filter.window;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import androidx.recyclerview.widget.RecyclerView;

import com.howard.filter.bean.CheckFilter;

import java.util.List;

public abstract class SimpleListFilterAdapter<T extends CheckFilter> extends RecyclerView.Adapter {

    private OnFilterClickListener mOnFilterClickListener;

    private List<T> mData;

    public SimpleListFilterAdapter(List<T> data, OnFilterClickListener listener) {
        this.mData = data;
        mOnFilterClickListener = listener;
    }

    public void setData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutResId(), parent, false);
        return new FilterViewHolder(view, mOnFilterClickListener);
    }

    protected abstract int getLayoutResId();

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mData != null && position < mData.size()) {
            ((FilterViewHolder) holder).setData(mData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    public static class FilterViewHolder<T extends CheckFilter> extends RecyclerView.ViewHolder {

        CheckedTextView mTextFilter;
        OnFilterClickListener mOnFilterClickListener;

        T mData;

        public FilterViewHolder(View itemView, OnFilterClickListener listener) {
            super(itemView);
            mTextFilter = (CheckedTextView) itemView;
            mOnFilterClickListener = listener;
        }

        public void setData(T data) {
            mData = data;
            if (mData != null) {
                mTextFilter.setText(mData.name);
                mTextFilter.setChecked(mData.checked);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnFilterClickListener.onFilterClick(mData);
                    }
                });
            }
        }
    }
}