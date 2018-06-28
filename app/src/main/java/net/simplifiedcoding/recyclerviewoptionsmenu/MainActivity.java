package net.simplifiedcoding.recyclerviewoptionsmenu;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.Observer;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import remoteservices.AuthResponse;
import remoteservices.RemoteServiceFacade;
import remoteservices.ServiceCallBack;

public class MainActivity extends AppCompatActivity implements ServiceCallBack<ItemsModel,Void> {
    //recyclerview objects
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    //model object for our list data
    private List<MyList> list;
   // private ItemBroadcastReciever broadcastReciever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing views
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        requestGCMRegistration();
        //loading list view item with this function
        loadRecyclerViewItem();
     //   broadcastReciever=new ItemBroadcastReciever(this);
        subscribe();

    }

    @Override
    protected void onResume() {
        super.onResume();
    //    registerReceiver(broadcastReciever,new IntentFilter(ItemBroadcastReciever.ACTION));
    }



    @Override
    protected void onPause() {
        super.onPause();
     //   unregisterReceiver(broadcastReciever);
    }

    private void loadRecyclerViewItem() {
        adapter = new CustomAdapter(list, this);
        recyclerView.setAdapter(adapter);
    }

    private void requestGCMRegistration() {
        GCMClientManager pushClientManager = new GCMClientManager(this, "");

        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                Log.d("GCMid", registrationId);
                //requestData(CommonUtility.URL, p_email, registrationId);
                RemoteServiceFacade.getInstance().notifydatachange("GCMDATA", registrationId, new ServiceCallBack<AuthResponse, String>() {
                    @Override
                    public void onSuccess(AuthResponse response) {
                        if(response.isAuth()) {
                            Toast.makeText(MainActivity.this, "Application is ready", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(MainActivity.this,error, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(String ex) {
                super.onFailure(ex);
                //Toast.makeText(SigninActivity.this, "fail", Toast.LENGTH_LONG).show();
                // If there is an error registering, don't just keep trying to register.
                // Require the user to click a button again, or perform
                // exponential back-off when retrying.
            }
        });

    }

    @Override
    public void onSuccess(ItemsModel response) {
        MyList myList1 = new MyList(response.getName(),response.getQuantity(),response.getTablename());
        list.add(myList1);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(Void error) {

    }

    public void subscribe()
    {
        Observer<Bundle> observer=new Observer<Bundle>() {
            @Override
            public void onChanged(@Nullable Bundle bundle) {
                MyList model=new MyList(bundle.getString("name"),bundle.getString("quantity"),bundle.getString("tablename"));
                list.add(model);
                adapter.notifyDataSetChanged();
            }
        };
        GcmMessageHandler.liveData.observe(this, observer);
    }

}
