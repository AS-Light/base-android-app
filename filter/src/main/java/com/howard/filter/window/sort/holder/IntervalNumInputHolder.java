package com.howard.filter.window.sort.holder;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.howard.filter.R;
import com.howard.filter.bean.IntervalNumFilterGroup;
import com.howard.filter.bean.base.BaseFilter;
import com.howard.filter.utils.StringUtils;
import com.howard.filter.window.sort.holder.base.BaseFilterHolder;

public class IntervalNumInputHolder extends BaseFilterHolder<IntervalNumFilterGroup> {

    IntervalNumFilterGroup mData;

    EditText mEditorStart;
    EditText mEditorEnd;

    protected IntervalNumInputHolder(Context context, boolean isLittle) {
        super(context, isLittle);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.filter_item_interval_input;
    }

    @Override
    protected void initViews(View rootView) {
        mEditorStart = rootView.findViewById(R.id.editor_start);
        mEditorEnd = rootView.findViewById(R.id.editor_end);

        mEditorStart.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

            }
        });

        mEditorStart.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(mData.startFilter.value) && TextUtils.isEmpty(mData.endFilter.value) && !TextUtils.isEmpty(charSequence.toString())) {
                    for (BaseFilter filter : mData.mutexFilters) {
                        filter.forceNull(true);
                        mRootHolder.notifyFilterChanged(filter);
                    }
                }
                mData.startFilter.value = charSequence.toString();
            }
        });

        mEditorEnd.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(mData.startFilter.value) && TextUtils.isEmpty(mData.endFilter.value) && !TextUtils.isEmpty(charSequence.toString())) {
                    for (BaseFilter filter : mData.mutexFilters) {
                        filter.forceNull(true);
                        mRootHolder.notifyFilterChanged(filter);
                    }
                }
                mData.endFilter.value = charSequence.toString();
            }
        });
    }

    @Override
    protected void onSetData(IntervalNumFilterGroup filter) {
        // todo: 必须有两个数据
        mData = filter;
        // 如果value值大于0才显示
        if (StringUtils.str2Int(mData.startFilter.value) > 0) {
            mEditorStart.setText(mData.startFilter.value);
        }
        if (StringUtils.str2Int(mData.endFilter.value) > 0) {
            mEditorEnd.setText(mData.endFilter.value);
        }
    }

    @Override
    public boolean containFilter(BaseFilter filter) {
        return mData.containFilter(filter);
    }

    @Override
    public void forceRefresh(BaseFilter filter) {
        mEditorStart.setText(mData.startFilter.value);
        mEditorEnd.setText(mData.endFilter.value);
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
