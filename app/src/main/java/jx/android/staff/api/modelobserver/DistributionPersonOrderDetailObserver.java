package jx.android.staff.api.modelobserver;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import jx.android.staff.api.Api;
import jx.android.staff.api.callback.OnHttpCallbackListener;
import jx.android.staff.api.callback.OnHttpCallbackSub;
import jx.android.staff.api.model.base.BaseListResult;
import jx.android.staff.api.model.items.DistributionShopItem;
import jx.android.staff.app.AppContext;

public class DistributionPersonOrderDetailObserver extends ViewModel {

    public MutableLiveData<ArrayList<DistributionShopItem>> distributionShopList = new MutableLiveData<>();

    public void requestPersonOrderDetail(Long personOrderId) {
        Api.getInstance().getOrderDetail(personOrderId, new OnHttpCallbackSub(new OnHttpCallbackListener() {
            @Override
            public void onSuccess(String result) {
                BaseListResult<DistributionShopItem> listResult = new Gson().fromJson(result, new TypeToken<BaseListResult<DistributionShopItem>>() {
                }.getType());
                if (listResult.getList() == null) {
                    listResult.setList(new ArrayList<>());
                }
                distributionShopList.postValue(listResult.getList());
            }

            @Override
            public void onFault(String errorMsg) {
                AppContext.getInstance().showToast(errorMsg);
                distributionShopList.postValue(new ArrayList<>());
            }
        }));
    }
}
