package jx.android.staff.api.modelobserver;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import jx.android.staff.api.Api;
import jx.android.staff.api.callback.OnHttpCallbackListener;
import jx.android.staff.api.callback.OnHttpCallbackSub;
import jx.android.staff.api.model.PersonOrderList;
import jx.android.staff.api.model.base.BaseListResult;
import jx.android.staff.api.model.items.PersonOrderItem;
import jx.android.staff.app.AppContext;

public class DistributionShopOrdersListObserver extends ViewModel {

    public MutableLiveData<ArrayList<PersonOrderItem>> distributionPersonList = new MutableLiveData<>();

    public void requestDistributionShopList(Long id) {
        Api.getInstance().getDistributionPersonList(id, new OnHttpCallbackSub(new OnHttpCallbackListener() {
            @Override
            public void onSuccess(String result) {
                PersonOrderList listResult = new Gson().fromJson(result, PersonOrderList.class);
                if (listResult.getOrderList() == null) {
                    listResult.setOrderList(new ArrayList<>());
                }
                distributionPersonList.postValue(listResult.getOrderList());
            }

            @Override
            public void onFault(String errorMsg) {
                AppContext.getInstance().showToast(errorMsg);
                distributionPersonList.postValue(new ArrayList<>());
            }
        }));
    }
}
