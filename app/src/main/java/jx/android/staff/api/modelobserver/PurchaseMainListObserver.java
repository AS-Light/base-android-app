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
import jx.android.staff.api.model.items.PurchaseItem;
import jx.android.staff.app.AppContext;

public class PurchaseMainListObserver extends ViewModel {

    public MutableLiveData<ArrayList<PurchaseItem>> purchaseList = new MutableLiveData<>();

    public void requestPurchaseList(String shipTimeStr) {
        Api.getInstance().getPurchaseList(shipTimeStr, new OnHttpCallbackSub(new OnHttpCallbackListener() {
            @Override
            public void onSuccess(String result) {
                BaseListResult<PurchaseItem> listResult = new Gson().fromJson(result, new TypeToken<BaseListResult<PurchaseItem>>() {
                }.getType());
                if (listResult.getList() == null) {
                    listResult.setList(new ArrayList<>());
                }
                purchaseList.postValue(listResult.getList());
            }

            @Override
            public void onFault(String errorMsg) {
                AppContext.getInstance().showToast(errorMsg);
                purchaseList.postValue(new ArrayList<>());
            }
        }));
    }
}
