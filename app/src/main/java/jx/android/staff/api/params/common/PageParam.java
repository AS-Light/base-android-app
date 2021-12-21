package jx.android.staff.api.params.common;

import java.io.Serializable;

public class PageParam implements Serializable {
    public final static int DEF_START_PAGE = 1;
    public final static int DEF_PAGE_SIZE = 10;

    public int pageNum = DEF_START_PAGE;
    public int pageSize = DEF_PAGE_SIZE;
}
