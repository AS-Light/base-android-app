package jx.android.staff.acts.distribution;

import androidx.recyclerview.widget.LinearLayoutManager;

import jx.android.staff.R;
import jx.android.staff.acts.base.BaseActivity;
import jx.android.staff.api.modelobserver.DistributionPersonOrderDetailObserver;
import jx.android.staff.binding.handler.DistributionPersonOrderDetailActivityHandler;
import jx.android.staff.binding.vm.DistributionPersonOrderDetailActivityViewModel;
import jx.android.staff.databinding.ActivityDistributionPersonOrderDetailBinding;

public class DistributionPersonOrderDetailActivity extends BaseActivity<ActivityDistributionPersonOrderDetailBinding> {

    DistributionPersonOrderDetailActivityViewModel mViewModel;
    DistributionPersonOrderDetailActivityHandler mHandler;

    DistributionPersonOrderDetailAdapter mAdapter;
    DistributionPersonOrderDetailObserver mObserver;

    Long mPersonOrderId;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_distribution_person_order_detail;
    }

    @Override
    public void initBinding() {
        mViewModel = new DistributionPersonOrderDetailActivityViewModel();
        mHandler = new DistributionPersonOrderDetailActivityHandler(this, mViewModel);

        mViewBinding.setViewModel(mViewModel);
        mViewBinding.setHandler(mHandler);
    }

    @Override
    public void initViews() {
        mAdapter = new DistributionPersonOrderDetailAdapter(this);
        mViewBinding.recycler.setAdapter(mAdapter);
        mViewBinding.recycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setNoMore(R.layout.layout_recycler_nomore);
        mViewBinding.recycler.setRefreshListener(this::getData);
    }

    @Override
    public void initObserver() {
        mObserver = new DistributionPersonOrderDetailObserver();
    }

    @Override
    public void initData() {
        mPersonOrderId = getIntent().getLongExtra("person_order_id", 0);
    }

    private void getData() {
        mObserver.requestPersonOrderDetail(mPersonOrderId);
    }

    @Override
    public String getActivityName() {
        return null;
    }
}
