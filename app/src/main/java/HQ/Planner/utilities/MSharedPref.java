package HQ.Planner.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class MSharedPref {

    static Context context;

    static final String REMIND_TIME = "remind_time";
    static final String THRESHOLD = "threshold";
    static final String REMIND_PERIOD = "remind_period";
    static final String FILE_NAME = "settings";

    public MSharedPref(Context context) {
    this.context = context;
    }

    public static void save(int remindTime, int threshold, int remindPeriod) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(REMIND_TIME, remindTime);
        editor.putInt(THRESHOLD, threshold);
        editor.putInt(REMIND_PERIOD, remindPeriod);
        editor.commit();
    }

    public static int read_Remind_time() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        int anInt = sharedPreferences.getInt(REMIND_TIME, 1);
        return anInt;

    }
    public static int read_threshold() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sharedPreferences.getInt(THRESHOLD, 60);

    } public static int read_Remind_period() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sharedPreferences.getInt(REMIND_PERIOD, 1);
    }
}
