package pp_ss2017.controllingapps.services;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DucGiang on 06.06.2017.
 *
 * Der Service zum Blockieren der Apps.
 */

public class BlockAccessibilityService extends AccessibilityService {

    private static final String TAG = "BlockService";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private Context appContext;

    private Handler handler;

    private BlockServiceReceiver blockServiceReceiver;

    private List<String> blockedApps = new ArrayList<String>();

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();

        appContext = getBaseContext();

        AccessibilityServiceInfo config = new AccessibilityServiceInfo();
        config.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        config.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;

        config.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;

        setServiceInfo(config);

        handler = new Handler(Looper.getMainLooper());
        blockServiceReceiver = new BlockServiceReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("pp_ss2017.controllingapps.BLOCK_ACCESSIBILITY_SERVICE");
        registerReceiver(blockServiceReceiver, intentFilter);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if(accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            if(accessibilityEvent.getPackageName() != null) {
                String currentApp = accessibilityEvent.getPackageName().toString();
                if(blockedApps.contains(currentApp)) {
                    shortToast(currentApp);
                    Intent startHomescreen = new Intent(Intent.ACTION_MAIN);
                    startHomescreen.addCategory(Intent.CATEGORY_HOME);
                    startHomescreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startHomescreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(startHomescreen);
                }
            }
        }
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(blockServiceReceiver);
    }

    public void shortToast(String toast) {
        final String tempString = toast;

        if(appContext!=null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(appContext, tempString, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    class BlockServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getStringExtra("command").equals("block")) {
                Log.d(TAG, "Apps werden zur Blacklist hinzugefügt.");

                String dbKey = "blacklist" + intent.getStringExtra("id");

                myRef.child(dbKey).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        blockedApps.add(dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        blockedApps.remove(dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            else if(intent.getStringExtra("command").equals("unblock")){
                String dbKey = "blacklist" + intent.getStringExtra("id");

                ChildEventListener listListener = myRef.child(dbKey).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        blockedApps.remove(dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                myRef.removeEventListener(listListener);
            }
            else if(intent.getStringExtra("command").equals("totalunblock")) {
                blockedApps.clear();
            }
        }
    }
}
