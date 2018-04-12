package pp_ss2017.controllingapps.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import pp_ss2017.controllingapps.helper.AppObject;
import pp_ss2017.controllingapps.R;
import pp_ss2017.controllingapps.adapters.AppListAdapter;

/**
 * Dieses Fragment präsentiert die App-Liste.
 */

public class AppListFragment extends ListFragment {

    private static final String TAG = "AppListFragment";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private PackageManager packageManager;
    private List<AppObject> appList = new ArrayList<AppObject>();
    private AppListAdapter appListAdapter;
    private IDReceiver idReceiver;

    private String profileID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        idReceiver = new IDReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("pp_ss2017.controllingapps.APPLIST_IDRECEIVER");
        getActivity().registerReceiver(idReceiver, filter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        packageManager = getActivity().getPackageManager();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(idReceiver);
    }

    class IDReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            profileID = intent.getStringExtra("id");

            myRef.child("app").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    String appName = "";
                    String packageName= "";
                    String iconPath= "";
                    String type = "";

                    AppObject appObject = null;

                    for(DataSnapshot child : dataSnapshot.getChildren()) {
                        if(child.getKey().equals("name")) {
                            appName = dataSnapshot.child(child.getKey()).getValue().toString();
                        }
                        if(child.getKey().equals("packageName")) {
                            packageName = dataSnapshot.child(child.getKey()).getValue().toString();
                        }
                        if(child.getKey().equals("icon")) {
                            iconPath = dataSnapshot.child(child.getKey()).getValue().toString();
                        }
                        if(child.getKey().equals("type")) {
                            type = dataSnapshot.child(child.getKey()).getValue().toString();
                        }

                        appObject = new AppObject(appName, packageName, iconPath, type);
                    }

                    appListAdapter.add(appObject);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    AppObject appObject = null;

                    for(int i=0; i<appListAdapter.getCount(); i++) {
                        appObject = appListAdapter.getItem(i);
                        if(appObject.getAppName().equals(dataSnapshot.child("name").getValue().toString())) {
                            appListAdapter.remove(appObject);
                        }
                    }
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            appListAdapter = new AppListAdapter(getActivity(), R.layout.app_listlayout, appList, profileID);
            setListAdapter(appListAdapter);
        }
    }
}
