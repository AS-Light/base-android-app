package jx.android.staff.acts.base.easyrecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

public abstract class BaseEasyAdapter<T, B extends ViewDataBinding> extends RecyclerArrayAdapter<T> {

    public BaseEasyAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseEasyHolder<T, B> OnCreateViewHolder(ViewGroup parent, int viewType) {
        B dataBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                getItemLayoutId(viewType),
                parent,
                false);

        return buildHolder(dataBinding);
    }

    @Override
    public void OnBindViewHolder(BaseViewHolder holder, int position) {
        super.OnBindViewHolder(holder, position);
    }

    public abstract int getItemLayoutId(int viewType);

    public abstract BaseEasyHolder<T, B> buildHolder(B dataBinding);
}
