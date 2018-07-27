package sjsu.bhub.cityrun.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

public class SnackBarUtil {

    public static void showSnackbar(View v, String snackbarText) {
        if (v == null || snackbarText == null) {
            return;
        }
        Snackbar.make(v, snackbarText, Snackbar.LENGTH_LONG).show();
    }
}
