package com.howard.filter.window.sort.holder;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.howard.filter.R;
import com.howard.filter.bean.base.BaseFilter;
import com.howard.filter.bean.ValueInValueFilterGroup;
import com.howard.filter.utils.StringUtils;
import com.howard.filter.window.sort.holder.base.BaseFilterHolder;

public class ValueInValueHolder extends BaseFilterHolder<ValueInValueFilterGroup> {

    ValueInValueFilterGroup mData;

    EditText mEditorValue;
    EditText mEditorFullValue;

    protected ValueInValueHolder(Context context, boolean isLittle) {
        super(context, isLittle);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.filter_item_value_in_value_input;
    }

    @Override
    protected void initViews(View rootView) {
        mEditorValue = rootView.findViewById(R.id.editor_value);
        mEditorFullValue = rootView.findViewById(R.id.editor_full_value);

        mEditorValue.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(mData.valueFilter.value) && TextUtils.isEmpty(mData.fullValueFilter.value) && !TextUtils.isEmpty(charSequence.toString())) {
                    for (BaseFilter filter : mData.mutexFilters) {
                        filter.forceNull(true);
                        mRootHolder.notifyFilterChanged(filter);
                    }
                }
                mData.valueFilter.value = charSequence.toString();
            }
        });

        mEditorFullValue.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(mData.valueFilter.value) && TextUtils.isEmpty(mData.fullValueFilter.value) && !TextUtils.isEmpty(charSequence.toString())) {
                    for (BaseFilter filter : mData.mutexFilters) {
                        filter.forceNull(true);
                        mRootHolder.notifyFilterChanged(filter);
                    }
                }
                mData.fullValueFilter.value = charSequence.toString();
            }
        });
    }

    @Override
    protected void onSetData(ValueInValueFilterGroup filter) {
        // todo: 必须有两个数据
        mData = filter;
        // 如果value值大于0才显示
        if (StringUtils.str2Int(mData.valueFilter.value) > 0) {
            mEditorValue.setText(mData.valueFilter.value);
        }
        if (StringUtils.str2Int(mData.fullValueFilter.value) > 0) {
            mEditorFullValue.setText(mData.fullValueFilter.value);
        }
    }

    @Override
    public boolean containFilter(BaseFilter filter) {
        return mData.containFilter(filter);
    }

    @Override
    public void forceRefresh(BaseFilter filter) {
        mEditorValue.setText(mData.valueFilter.value);
        mEditorFullValue.setText(mData.fullValueFilter.value);
    }

    private abstract class SimpleTextWatcher implements TextWatcher {
        @Override
        public void afterTextChanged(Editable editable) {
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
    }
}