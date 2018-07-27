package sjsu.bhub.cityrun.utils;

import android.Manifest;
import android.content.Context;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class PermissionUtil {

    public static void getPermission(Context context){
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            }
        };

        if(TedPermission.isDenied(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                || TedPermission.isDenied(context, Manifest.permission.ACCESS_FINE_LOCATION)){

            TedPermission.with(context)
                    .setPermissionListener(permissionListener)
                    .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                    .check();
        }
    }
}
