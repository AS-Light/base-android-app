package jx.android.staff.acts.distribution;

import androidx.recyclerview.widget.LinearLayoutManager;

import jx.android.staff.R;
import jx.android.staff.acts.base.BaseActivity;
import jx.android.staff.binding.handler.DistributionGoodsOrdersActivityHandler;
import jx.android.staff.binding.vm.DistributionGoodsOrdersActivityViewModel;
import jx.android.staff.databinding.ActivityDistributionGoodsOrdersBinding;

public class DistributionGoodsOrdersActivity extends BaseActivity<ActivityDistributionGoodsOrdersBinding> {

    DistributionGoodsOrdersActivityViewModel mViewModel;
    DistributionGoodsOrdersActivityHandler mHandler;

    DistributionGoodsOrdersAdapter mAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_distribution_goods_orders;
    }

    @Override
    public void initBinding() {
        mViewModel = new DistributionGoodsOrdersActivityViewModel();
        mHandler = new DistributionGoodsOrdersActivityHandler(this, mViewModel);

        mViewBinding.setViewModel(mViewModel);
        mViewBinding.setHandler(mHandler);
    }

    @Override
    public void initViews() {
        mAdapter = new DistributionGoodsOrdersAdapter(this);
        mViewBinding.recycler.setAdapter(mAdapter);
        mViewBinding.recycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setNoMore(R.layout.layout_recycler_nomore);
        mViewBinding.recycler.setRefreshListener(this::getData);
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initData() {

    }

    private void getData() {

    }

    @Override
    public String getActivityName() {
        return null;
    }
}
