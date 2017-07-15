package ratemyapp.jagerfield.appratebotlib.Utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import java.util.List;

public class CLib
{
    public static boolean sysIsBroken(Activity activity)
    {
        boolean result = false;
        if (activity==null) { Log.e("TAG", "Activity is null"); result = true; }

        return result;
    }

    public static boolean sysIsBroken(Context context)
    {
        boolean result = false;
        if (context==null) { Log.e("TAG", "Context is null"); result = true; }

        return result;
    }

    public interface IKEYS
    {
        String KEY_RATINGS_STATE = "KEY_RATINGS_STATE";
        String KEY_ASK_AGAIN_DATE = "KEY_ASK_AGAIN_DATE";
        String KEY_USAGE_COUNT = "KEY_USAGE_COUNT";
        String KEY_LAST_USAGE_DATE = "KEY_LAST_USAGE_DATE"; //Used to know when to update the usage counter
    }
}
