package jx.android.staff.acts.base.easyrecycler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import jx.android.staff.utils.recycler.ItemHideUtils;

public abstract class BaseEasyHeader<T extends ViewDataBinding> implements RecyclerArrayAdapter.ItemView {

    protected T binding;

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(ViewGroup parent) {
        Context context = parent.getContext();
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutId(), null, false);
        binding();
        return binding.getRoot();
    }

    protected abstract int getLayoutId();

    @Override
    public void onBindView(View headerView) {
    }

    public abstract void binding();

    public void show(boolean show) {
        if (binding != null)
            ItemHideUtils.show(binding.getRoot(), show);
    }

    public ViewDataBinding getDataBinding() {
        return binding;
    }
}
