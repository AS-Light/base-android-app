package jx.android.staff.acts.distribution;

import androidx.recyclerview.widget.LinearLayoutManager;

import jx.android.staff.R;
import jx.android.staff.acts.base.BaseActivity;
import jx.android.staff.api.modelobserver.DistributionShopOrdersListObserver;
import jx.android.staff.binding.handler.DistributionShopOrdersActivityHandler;
import jx.android.staff.binding.vm.DistributionShopOrdersActivityViewModel;
import jx.android.staff.databinding.ActivityDistributionShopOrdersBinding;

public class DistributionShopOrdersActivity extends BaseActivity<ActivityDistributionShopOrdersBinding> {

    DistributionShopOrdersActivityViewModel mViewModel;
    DistributionShopOrdersActivityHandler mHandler;

    DistributionShopOrdersAdapter mAdapter;
    DistributionShopOrdersListObserver mListObserver;

    Long mShopOrderId;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_distribution_shop_orders;
    }

    @Override
    public void initBinding() {
        mViewModel = new DistributionShopOrdersActivityViewModel();
        mHandler = new DistributionShopOrdersActivityHandler(this, mViewModel);

        mViewBinding.setViewModel(mViewModel);
        mViewBinding.setHandler(mHandler);
    }

    @Override
    public void initViews() {
        mAdapter = new DistributionShopOrdersAdapter(this);
        mAdapter.setHandler(mHandler);
        mViewBinding.recycler.setAdapter(mAdapter);
        mViewBinding.recycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setNoMore(R.layout.layout_recycler_nomore);
        mViewBinding.recycler.setRefreshListener(this::getData);
    }

    @Override
    public void initObserver() {
        mListObserver = new DistributionShopOrdersListObserver();
        mListObserver.distributionPersonList.observe(this, personOrderItems -> {
            mAdapter.removeAll();
            mAdapter.addAll(personOrderItems);
        });
    }

    @Override
    public void initData() {
        mShopOrderId = getIntent().getLongExtra("shop_order_id", 0);
        getData();
    }

    private void getData() {
        mListObserver.requestDistributionShopList(mShopOrderId);
    }

    @Override
    public String getActivityName() {
        return null;
    }
}
