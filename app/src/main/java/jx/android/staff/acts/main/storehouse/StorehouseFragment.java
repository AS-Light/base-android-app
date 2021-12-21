package jx.android.staff.acts.main.storehouse;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import jx.android.staff.R;
import jx.android.staff.acts.base.BaseFragment;
import jx.android.staff.binding.handler.StorehouseFragmentHandler;
import jx.android.staff.binding.handler.delegate.StorehouseFragmentDelegate;
import jx.android.staff.binding.vm.StorehouseFragmentViewModel;
import jx.android.staff.databinding.FragmentMainDeliveryBinding;

public class StorehouseFragment extends BaseFragment<FragmentMainDeliveryBinding> implements StorehouseFragmentDelegate {

    StorehouseFragmentViewModel mViewModel;
    StorehouseFragmentHandler mHandler;

    StorehouseAdapter mAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_main_delivery;
    }

    @Override
    public void initBinding() {
        mViewModel = new StorehouseFragmentViewModel();
        mHandler = new StorehouseFragmentHandler(getActivity(), mViewModel, this);

        mViewBinding.setViewModel(mViewModel);
        mViewBinding.setHandler(mHandler);
    }

    @Override
    public void initViews() {
        mAdapter = new StorehouseAdapter(getActivity());
        mViewBinding.recycler.setAdapter(mAdapter);
        mViewBinding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter.setMore(R.layout.layout_loadmore_default_footer, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                getData();
            }

            @Override
            public void onMoreClick() {

            }
        });
        mAdapter.setNoMore(R.layout.layout_recycler_nomore);
        mViewBinding.recycler.setRefreshListener(this::getData);
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initData() {
        getData();
    }

    private void getData() {

    }

    @Override
    public String getFragmentName() {
        return null;
    }

    @Override
    public void onDateChange() {
        getData();
    }
}
