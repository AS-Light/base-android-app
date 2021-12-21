package com.howard.filter.bean;

import com.howard.filter.bean.base.BaseFilter;

public class CheckFilterGroup extends FilterGroup<CheckFilter> {
    public int maxChoiceCount = -1;

    public CheckFilterGroup(int maxChoiceCount) {
        super();
        this.maxChoiceCount = maxChoiceCount;
    }

    @Override
    public void forceNull(boolean forceFalse) {
        if (forceFalse) {
            for (CheckFilter filter : children) {
                filter.forceNull(true);
            }
        }
    }

    @Override
    public boolean isChecked() {
        for (CheckFilter child : children) {
            if (child.isChecked()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containFilter(BaseFilter filter) {
        return children.contains(filter);
    }
}
