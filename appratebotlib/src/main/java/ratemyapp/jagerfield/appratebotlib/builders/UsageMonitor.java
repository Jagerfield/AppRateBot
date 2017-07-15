package ratemyapp.jagerfield.appratebotlib.builders;

import android.content.Context;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import ratemyapp.jagerfield.appratebotlib.Utils.CLib;
import ratemyapp.jagerfield.appratebotlib.Utils.PreferenceUtil;

public class UsageMonitor
{
    private Context context;

    private static UsageMonitor instance;

    private UsageMonitor(Context context)
    {
        this.context = context;
    }

    public static UsageMonitor newInstance(Context context)
    {
        return new UsageMonitor(context);
    }

    public void updateUsageInformation()
    {
        int year1 = getCurrentCal().get(Calendar.YEAR);
        int month1 = getCurrentCal().get(Calendar.MONTH);
        int day1 = getCurrentCal().get(Calendar.DAY_OF_WEEK);
        int week1 = getCurrentCal().get(Calendar.WEEK_OF_YEAR);
        int hours1 = getCurrentCal().get(Calendar.HOUR_OF_DAY);
        int minutes1 = getCurrentCal().get(Calendar.MINUTE);
        int mseconds1 = getCurrentCal().get(Calendar.SECOND);

        int year2 = getLastUsageCal(context).get(Calendar.YEAR);
        int month2 = getLastUsageCal(context).get(Calendar.MONTH);
        int week2 = getCurrentCal().get(Calendar.WEEK_OF_YEAR);
        int day2 = getLastUsageCal(context).get(Calendar.DAY_OF_WEEK);
        int hours2 = getCurrentCal().get(Calendar.HOUR_OF_DAY);
        int minutes2 = getCurrentCal().get(Calendar.MINUTE);
        int mseconds2 = getCurrentCal().get(Calendar.SECOND);

        int i = getCurrentCal().compareTo(getLastUsageCal(context));

        if (i>0)
        {
            int count = PreferenceUtil.getInt(context, CLib.IKEYS.KEY_USAGE_COUNT, 0);
            //Update usage count
            PreferenceUtil.setInt(context, CLib.IKEYS.KEY_USAGE_COUNT, count + 1);
            //Update last usage date
            PreferenceUtil.setLong(context, CLib.IKEYS.KEY_LAST_USAGE_DATE, Calendar.getInstance().getTimeInMillis());
        }

        String days[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    }

    public void updateUsageInformation(Calendar calA, Calendar calB)
    {
        int i = calA.compareTo(calB);

        if (i>0)
        {
            int count = PreferenceUtil.getInt(context, CLib.IKEYS.KEY_USAGE_COUNT, 0);
            //Update usage count
            PreferenceUtil.setInt(context, CLib.IKEYS.KEY_USAGE_COUNT, count + 1);
            //Update last usage date
            PreferenceUtil.setLong(context, CLib.IKEYS.KEY_LAST_USAGE_DATE, Calendar.getInstance().getTimeInMillis());
        }
        else
        {
            String str = "";
        }

        String days[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    }

    public Calendar getLastUsageCal(Context context)
    {
        Calendar cal = Calendar.getInstance();
        Calendar latestUsage = getSavedLastUsageDate(context);
        cal.set(latestUsage.get(Calendar.YEAR), latestUsage.get(Calendar.MONTH), latestUsage.get(Calendar.DAY_OF_WEEK), 0,0,0);
        return latestUsage;
    }


    public Calendar getSavedLastUsageDate(Context context)
    {
        Calendar lastCal = Calendar.getInstance();

        long lastSavedDate = PreferenceUtil.getLong(context, CLib.IKEYS.KEY_LAST_USAGE_DATE, 0l);

        if (lastSavedDate==0)
        {
            resetUsageAndDate(context);
        }

        lastCal.setTimeInMillis(lastSavedDate);

        return lastCal;
    }

    public Calendar getCurrentCal()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_WEEK), 0,0,0);
        return cal;
    }

    public static void resetUsageAndDate(Context context)
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1970);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.WEEK_OF_YEAR, 1);
        cal.set(Calendar.DAY_OF_WEEK, 1);
        cal.set(Calendar.HOUR_OF_DAY, 1);
        cal.set(Calendar.MINUTE, 1);
        cal.set(Calendar.SECOND, 1);
        cal.set(Calendar.MILLISECOND, 1);

        long lastSavedDate= cal.getTimeInMillis();

        PreferenceUtil.setLong(context, CLib.IKEYS.KEY_LAST_USAGE_DATE, lastSavedDate);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_WEEK);

        PreferenceUtil.setInt(context, CLib.IKEYS.KEY_USAGE_COUNT, 0);
    }

}









