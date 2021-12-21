package jx.android.staff.acts.base.easyrecycler;

import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

public abstract class BaseEasyHolder<T, B extends ViewDataBinding> extends BaseViewHolder<T> {

    private B mDataBinding;

    public BaseEasyHolder(View itemView) {
        super(itemView);
    }

    protected abstract int getVariableId();

    public void setData(T data) {
        bindData(data);
        parseData(data);
    }

    public abstract void parseData(T data);

    private void bindData(T data) {
        mDataBinding.setVariable(getVariableId(), data);
        mDataBinding.executePendingBindings();
    }

    public B getDataBinding() {
        return mDataBinding;
    }
}
