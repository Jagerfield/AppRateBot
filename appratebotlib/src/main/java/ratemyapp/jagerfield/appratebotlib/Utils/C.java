package ratemyapp.jagerfield.appratebotlib.Utils;

import android.content.Context;
import android.util.Log;

public class C
{
    public static boolean sysIsBroken(Context context)
    {
        boolean result = false;
        if (context==null) { Log.e("TAG", "Context is null"); result = true; }

        return result;
    }

    public interface IKEYS
    {
        String KEY_EDITED_CURRENT_DATE_TIME = "KEY_EDITED_CURRENT_DATE_TIME";
        String KEY_RATINGS_STATE = "KEY_RATINGS_STATE";
        String KEY_ASK_AGAIN_DATE = "KEY_ASK_AGAIN_DATE";
        String KEY_USAGE_COUNT = "KEY_USAGE_COUNT";
        String KEY_LAST_USAGE_DATE = "KEY_LAST_USAGE_DATE"; //Used to know when to update the usage counter
        String KEY_ALLOW_EDITING_CURRENT_DATE_TIME = "KEY_ALLOW_EDITING_CURRENT_DATE_TIME";
    }
}
