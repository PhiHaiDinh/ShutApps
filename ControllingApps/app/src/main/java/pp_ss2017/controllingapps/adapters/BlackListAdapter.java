package pp_ss2017.controllingapps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import pp_ss2017.controllingapps.R;

/**
 * Created by DucGiang on 23.05.2017.
 *
 * Dieser Adapter dient zu Erstellung der Blacklist.
 */

public class BlackListAdapter extends ArrayAdapter<String> {

    private static final String TAG = "BlackListAdapter";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private Context context;
    private List<String> appList;
    private String profileID;

    public BlackListAdapter(Context context, int textViewResourceId, List<String> appList, String profileID) {
        super(context, textViewResourceId, appList);
        this.context = context;
        this.appList = appList;
        this.profileID = profileID;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        final String appName = appList.get(position);

        if(listView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listView = inflater.inflate(R.layout.black_listlayout, null);
        }

        ImageButton imageButton = (ImageButton) listView.findViewById(R.id.removeBtn);
        imageButton.setTag(position);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String dbKey = "blacklist" + profileID;

                myRef.child(dbKey).child(appName).removeValue();

            }
        });

        if(appName != null) {
            TextView appString = (TextView) listView.findViewById(R.id.app_string);

            appString.setText(appName);
        }

        return listView;
    }
}
