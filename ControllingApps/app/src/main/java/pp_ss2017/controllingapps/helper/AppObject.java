package pp_ss2017.controllingapps.helper;

/**
 * Created by DucGiang on 03.06.2017.
 *
 * Klasse für ein selbsterstelltes Objekt für eine App
 * Objekt besteht aus App-Name, Package-Name, Pfad im Firebase-Storage für das App-Icon und die Kategorie der App
 *
 */

public class AppObject extends Object {

    private String appName;
    private String packageName;
    private String iconPath;
    private String type;

    public AppObject(String appName, String packageName, String iconPath, String type) {
        this.appName = appName;
        this.packageName = packageName;
        this.iconPath = iconPath;
        this.type = type;
    }

    public String getAppName() {
        return appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getIconPath() {
        return iconPath;
    }

    public String getType() {
        return type;
    }
}
