package pp_ss2017.controllingapps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import pp_ss2017.controllingapps.helper.AppObject;
import pp_ss2017.controllingapps.R;

/**
 * Created by DucGiang on 28.04.2017.
 *
 * Dieser Adapter dient zu Erstellung der App-Liste.
 */

public class AppListAdapter extends ArrayAdapter<AppObject> {

    private static final String TAG = "AppListAdapter";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private Context context;
    private List<AppObject> appList;
    private String profileID;

    public AppListAdapter(Context context, int textViewResourceId, List<AppObject> appList, String profileID) {
        super(context, textViewResourceId, appList);
        this.context = context;
        this.appList = appList;
        this.profileID = profileID;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        final int clickPosition = position;
        final AppObject appObject = appList.get(position);

        if(listView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listView = inflater.inflate(R.layout.app_listlayout, null);
        }

        final ImageButton imageButton = (ImageButton) listView.findViewById(R.id.addBtn);
        imageButton.setTag(position);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String dbKey = "blacklist" + profileID;

                myRef.child(dbKey).child(appObject.getAppName()).setValue(appObject.getPackageName());
            }
        });

        if(appObject != null) {
            TextView appName = (TextView) listView.findViewById(R.id.app_name);
            ImageView iconView = (ImageView) listView.findViewById(R.id.app_icon);

            appName.setText(appObject.getAppName());
            Picasso.with(context)
                    .load(appObject.getIconPath())
                    .into(iconView);
        }

        return listView;
    }
}
