package HQ.Planner.utilities;

import android.content.Context;
import android.widget.Toast;


public class MToast {
    static Toast currentToast;

    Context context;

    public static void T(Context context,
                         String msg) {
        Toast.makeText(
                context,
                msg,
                Toast.LENGTH_SHORT)
                .show();
    }

    public static void say(
            Context context,
            String msg) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(context,
                msg,
                Toast.LENGTH_SHORT);
        currentToast.show();
    }

}
