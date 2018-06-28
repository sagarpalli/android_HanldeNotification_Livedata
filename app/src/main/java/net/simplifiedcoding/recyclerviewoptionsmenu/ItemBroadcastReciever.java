package net.simplifiedcoding.recyclerviewoptionsmenu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import remoteservices.ServiceCallBack;

/**
 * Created by Sekhar on 5/30/18.
 */

public class ItemBroadcastReciever extends BroadcastReceiver {

    private ServiceCallBack<ItemsModel,Void> listener;
    static final String ACTION="com.cline.ITEMADDED";
    public ItemBroadcastReciever()
    {

    }

    public ItemBroadcastReciever(ServiceCallBack<ItemsModel, Void> listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(listener!=null && intent.getAction().equals(ACTION))
        {
            ItemsModel model=new ItemsModel(intent.getStringExtra("name"),intent.getStringExtra("quantity"),intent.getStringExtra("tablename"));
            listener.onSuccess(model);
        }
    }

    
}
