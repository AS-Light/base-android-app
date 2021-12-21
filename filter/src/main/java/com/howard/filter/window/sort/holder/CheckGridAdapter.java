package com.howard.filter.window.sort.holder;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.howard.filter.R;
import com.howard.filter.bean.CheckFilter;
import com.howard.filter.bean.CheckInputFilter;
import com.howard.filter.listener.OnHolderClickedListener;

import java.util.List;

public class CheckGridAdapter extends RecyclerView.Adapter {

    private List<CheckFilter> mData;
    private OnHolderClickedListener mOnHolderClickListener;

    private boolean isLittle = false;

    CheckGridAdapter(boolean isLittle) {
        this.isLittle = isLittle;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View itemView = LayoutInflater
                    .from(parent.getContext())
                    .inflate(isLittle ? R.layout.filter_item_check_grid_l : R.layout.filter_item_check_grid, parent, false);
            return new CheckItemHolder(itemView, mOnHolderClickListener);
        } else {
            View itemView = LayoutInflater
                    .from(parent.getContext())
                    .inflate(isLittle ? R.layout.filter_item_input_l : R.layout.filter_item_input, parent, false);
            return new InputCheckItemHolder(itemView, mOnHolderClickListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == 1) {
            ((CheckItemHolder) holder).setData(mData.get(position));
        } else {
            ((InputCheckItemHolder) holder).setData((CheckInputFilter) mData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        try {
            if (mData.get(position) instanceof CheckInputFilter) {
                return 2;
            } else {
                return 1;
            }
        } catch (Exception e) {
            return 1;
        }
    }

    public void setData(List<CheckFilter> data, OnHolderClickedListener listener) {
        mData = data;
        mOnHolderClickListener = listener;
        notifyDataSetChanged();
    }

    public static class CheckItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnHolderClickedListener mOnHolderClickedListener;
        private CheckedTextView mCheckedText;
        private CheckFilter mData;

        public CheckItemHolder(View itemView, OnHolderClickedListener listener) {
            super(itemView);
            mCheckedText = (CheckedTextView) itemView;
            mOnHolderClickedListener = listener;
        }

        public void setData(CheckFilter data) {
            mData = data;
            mCheckedText.setText(mData.name);
            mCheckedText.setOnClickListener(this);
            mCheckedText.setChecked(mData.checked);
        }

        @Override
        public void onClick(View v) {
            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                mOnHolderClickedListener.onItemClick(getAdapterPosition());
            }
        }
    }

    public static class InputCheckItemHolder extends RecyclerView.ViewHolder {

        private OnHolderClickedListener mOnHolderClickedListener;
        private EditText mEditor;
        private CheckInputFilter mData;

        public InputCheckItemHolder(View itemView, OnHolderClickedListener listener) {
            super(itemView);
            mEditor = (EditText) itemView;
            mOnHolderClickedListener = listener;
        }

        public void setData(CheckInputFilter data) {
            try {
                mData = data;
                if (!mEditor.getText().toString().equals(mData.value)) {
                    mEditor.setText(mData.value);
                }
                mEditor.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!TextUtils.isEmpty(s) && !mData.checked && getAdapterPosition() != RecyclerView.NO_POSITION) {
                            mOnHolderClickedListener.onItemClick(getAdapterPosition());
                        }
                        if (!s.toString().equals(mData.value)) {
                            mData.value = s.toString();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            } catch (Exception e) {

            }
        }
    }
}
