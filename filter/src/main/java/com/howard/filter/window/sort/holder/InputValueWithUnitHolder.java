package com.howard.filter.window.sort.holder;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.howard.filter.R;
import com.howard.filter.bean.base.BaseFilter;
import com.howard.filter.bean.InputValueWithUnitFilter;
import com.howard.filter.utils.StringUtils;
import com.howard.filter.window.sort.holder.base.BaseFilterHolder;

public class InputValueWithUnitHolder extends BaseFilterHolder<InputValueWithUnitFilter> {

    InputValueWithUnitFilter mData;

    EditText mEditor;
    TextView mTextUnit;

    protected InputValueWithUnitHolder(Context context, boolean isLittle) {
        super(context, isLittle);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.filter_item_input_with_unit_end;
    }

    @Override
    protected void initViews(View rootView) {
        mEditor = rootView.findViewById(R.id.editor);
        mTextUnit = rootView.findViewById(R.id.text_unit);

        mEditor.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(mData.value) && TextUtils.isEmpty(mData.value) && !TextUtils.isEmpty(charSequence.toString())) {
                    for (BaseFilter filter : mData.mutexFilters) {
                        filter.forceNull(true);
                        mRootHolder.notifyFilterChanged(filter);
                    }
                }
                mData.value = charSequence.toString();
            }
        });
    }

    @Override
    protected void onSetData(InputValueWithUnitFilter filter) {
        // todo: 必须有两个数据
        mData = filter;
        // 如果value值大于0才显示
        if (StringUtils.str2Int(mData.value) > 0) {
            mEditor.setText(mData.value);
        }
    }

    @Override
    public boolean containFilter(BaseFilter filter) {
        return false;
    }

    @Override
    public void forceRefresh(BaseFilter filter) {
        mEditor.setText(mData.value);
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