package ratemyapp.jagerfield.appratebotlib.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import ratemyapp.jagerfield.appratebotlib.builders.builder.RatingStatusEnum;

public class RateLibUtil
{
    private static RateLibUtil instance;
    public static RateLibUtil newInstance()
    {
        if (instance == null)
        {
            instance = new RateLibUtil();
        }

        return instance;
    }

    public RateLibUtil() {
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

    public long getAppInstallationDate(Context context) throws Exception
    {
        PackageManager packageManager =  context.getPackageManager();
        long installTimeInMilliseconds;

        PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        installTimeInMilliseconds = packageInfo.firstInstallTime;

        return installTimeInMilliseconds;
    }

    public long getAskMeLaterDate(Context context) throws Exception
    {
        long askMeLaterDate = PreferenceUtil.getLong(context, RateLibUtil.IKEYS.KEY_ASK_AGAIN_DATE, 0l);

        if (askMeLaterDate == 0)
        {
            PreferenceUtil.setInt(context, RateLibUtil.IKEYS.KEY_RATINGS_STATE, RatingStatusEnum.fromEnumToInt(RatingStatusEnum.NEVER));
            throw new IllegalStateException("Can't get askMeLaterDate");
        }

        return askMeLaterDate;
    }

    public RatingStatusEnum getRatingStatus(Context context)  throws Exception
    {
        int result;

        if (RateLibUtil.sysIsBroken(context)) { throw new IllegalArgumentException("Context is null"); }

        result = PreferenceUtil.getInt(context, RateLibUtil.IKEYS.KEY_RATINGS_STATE, -9);

        if (result == -9 )
        {
            result = RatingStatusEnum.fromEnumToInt(RatingStatusEnum.NOT_ASKED);
            PreferenceUtil.setInt(context, RateLibUtil.IKEYS.KEY_RATINGS_STATE, result);
        }

        return RatingStatusEnum.fromIntToEnum(result);
    }

    public int getUsageCount(Context context)
    {
        int count = PreferenceUtil.getInt(context, RateLibUtil.IKEYS.KEY_USAGE_COUNT, 0);
        return count;
    }

}
