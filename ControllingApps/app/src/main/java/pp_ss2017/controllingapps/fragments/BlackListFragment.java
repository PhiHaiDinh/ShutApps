package pp_ss2017.controllingapps.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import pp_ss2017.controllingapps.R;
import pp_ss2017.controllingapps.adapters.BlackListAdapter;

/**
 * Dieses Fragment präsentiert die Blacklist.
 */

public class BlackListFragment extends ListFragment {

    private static final String TAG = "BlackListFragment";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private List<String> appList = new ArrayList<String>();
    private BlackListAdapter blackListAdapter;
    private String profileID;
    private IDReceiver idReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        idReceiver = new BlackListFragment.IDReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("pp_ss2017.controllingapps.BLACKLIST_IDRECEIVER");
        getActivity().registerReceiver(idReceiver, filter);
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

            String dbKey = "blacklist" + profileID;

            myRef.child(dbKey).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    blackListAdapter.add(dataSnapshot.getKey());
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    blackListAdapter.remove(dataSnapshot.getKey());
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            blackListAdapter = new BlackListAdapter(getActivity(), R.layout.black_listlayout, appList, profileID);
            setListAdapter(blackListAdapter);
        }
    }
}
