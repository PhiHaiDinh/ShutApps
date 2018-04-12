package pp_ss2017.controllingapps.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Der NotificationService dient zum Unterbinden der Notifications der blockierten Anwendungen.
 * Dabei wird der Service durch das NotificationListenerService erweitert.
 */

public class NotificationService extends NotificationListenerService {

    public static final String TAG = "NotificationService";

    //Datenbank-Referenz
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private NotificationListenerServiceReceiver notificationListenerServiceReceiver;

    private List<String> blockedNotifications = new ArrayList<String>();
    private ArrayList<StatusBarNotification> savedNotifications = new ArrayList<StatusBarNotification>();

    @Override
    public void onCreate() {
        super.onCreate();
        //Der Broadcast-Receiver wird im Service registriert
        notificationListenerServiceReceiver = new NotificationListenerServiceReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("pp_ss2017.controllingapps.NOTIFICATION_LISTENER");
        registerReceiver(notificationListenerServiceReceiver, intentFilter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    //Diese Methode wird jedes Mal aufgerufen, sobald eine neue Notification am Smartphone ankommt.
    @Override
    public void onNotificationPosted(StatusBarNotification statusBarNotification){
        String packageName = statusBarNotification.getPackageName();

        for(Object pkg : blockedNotifications) {
            if(packageName.equals(pkg)) {
                savedNotifications.add(statusBarNotification);
                cancelNotification(statusBarNotification.getKey());
            }
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification statusBarNotification){

    }

    class NotificationListenerServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getStringExtra("command").equals("block")) {

                String dbKey = "blacklist" + intent.getStringExtra("id");

                myRef.child(dbKey).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        blockedNotifications.add(dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        blockedNotifications.remove(dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            else if(intent.getStringExtra("command").equals("totalunblock")) {
                blockedNotifications.clear();

                //verpasste Notifications sind in savedNotifications gespeichert
                //man könnte die Notifications anzeigen lassen, bevor man die Liste leert
                savedNotifications.clear();
            }
        }
    }
}
