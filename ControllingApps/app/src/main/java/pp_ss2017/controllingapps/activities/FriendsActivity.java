package pp_ss2017.controllingapps.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import pp_ss2017.controllingapps.R;

/**
 *  Die FriendsActivity dient dazu, die wichtigsten Daten zu den Freunden darstellen.
 *  Außerdem wird hier sichtbar, welche Apps ein Freund blockiert.
 *
 */

public class FriendsActivity extends AppCompatActivity {

    //Datenbank-Referenz
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        //Das Profilbild wird über Facebook geladen
        ImageView userPicture = (ImageView) findViewById(R.id.user_image);
        Picasso.with(this)
                .load("https://graph.facebook.com/" + getIntent().getStringExtra("profileID") + "/picture?type=large")
                .into(userPicture);

        //Der Nutzer-Name wird dargestellt
        TextView userName = (TextView) findViewById(R.id.user_name);
        userName.setText(getIntent().getStringExtra("userName"));

        final TextView appList = (TextView) findViewById(R.id.app_list);

        //Datenbankzugriff auf die Blacklist mit der profileID
        myRef.child("blacklist" + getIntent().getStringExtra("profileID")).addChildEventListener(new ChildEventListener() {
            String newString = "";

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Die Apps, die von einem Freund blockiert werden, werden ausgelesen und angezeigt
                String tempString = dataSnapshot.getKey() + " \n";
                newString += tempString;
                appList.setText(newString);
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
    }
}
