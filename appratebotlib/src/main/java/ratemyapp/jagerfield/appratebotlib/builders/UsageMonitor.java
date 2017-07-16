package ratemyapp.jagerfield.appratebotlib.builders;

import android.content.Context;
import java.util.Calendar;
import java.util.TimeZone;
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
        Calendar cal = getCurrentCal();
        int yearCal = cal.get(Calendar.YEAR);
        int monthCal = cal.get(Calendar.MONTH);

        int weekCal = cal.get(Calendar.WEEK_OF_YEAR);
        int dayOfMonthCal = cal.get(Calendar.DAY_OF_MONTH);
        int dayOfWeekCal = cal.get(Calendar.DAY_OF_WEEK);

        int hoursCal = cal.get(Calendar.HOUR_OF_DAY);
        int minutesCal = cal.get(Calendar.MINUTE);
        int secondsCal = cal.get(Calendar.SECOND);
        int milliSecCal = cal.get(Calendar.MILLISECOND);


        Calendar lastUsageCal = getLastUsageCal(context);
        int yearLastUsageCal = lastUsageCal.get(Calendar.YEAR);
        int monthLastUsageCal = lastUsageCal.get(Calendar.MONTH);

        int weekLastUsageCal = lastUsageCal.get(Calendar.WEEK_OF_YEAR);
        int dayOfMonthLastUsageCal = lastUsageCal.get(Calendar.DAY_OF_MONTH);
        int dayOfWeekLastUsageCal = lastUsageCal.get(Calendar.DAY_OF_WEEK);

        int hoursLastUsageCal = lastUsageCal.get(Calendar.HOUR_OF_DAY);
        int minutesLastUsageCal = lastUsageCal.get(Calendar.MINUTE);
        int secondsLastUsageCal = lastUsageCal.get(Calendar.SECOND);
        int milliSecLastUsageCal = lastUsageCal.get(Calendar.MILLISECOND);

        int i = cal.compareTo(lastUsageCal);

        if (i>0)
        {
            int count = PreferenceUtil.getInt(context, CLib.IKEYS.KEY_USAGE_COUNT, 0);
            //Update usage count
            PreferenceUtil.setInt(context, CLib.IKEYS.KEY_USAGE_COUNT, count + 1);
            //Update last usage date
            PreferenceUtil.setLong(context, CLib.IKEYS.KEY_LAST_USAGE_DATE, getCurrentCal().getTimeInMillis());
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
            PreferenceUtil.setLong(context, CLib.IKEYS.KEY_LAST_USAGE_DATE, getCurrentCal().getTimeInMillis());
        }
        else
        {
            String str = "";
        }

        String days[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    }


    public Calendar getLastUsageCal(Context context)
    {
        Calendar lastCal = getCurrentCal();

        long lastSavedDate = PreferenceUtil.getLong(context, CLib.IKEYS.KEY_LAST_USAGE_DATE, 0l);

        if (lastSavedDate==0) { resetUsageAndDate(context); }

        lastCal.setTimeInMillis(lastSavedDate);
        lastCal = customizeCal(lastCal);

        return lastCal;
    }

    public static Calendar getCurrentCal()
    {
        Calendar cal = Calendar.getInstance();
        cal = customizeCal(cal);
        return cal;
    }

    public static Calendar customizeCal(Calendar cal)
    {
        cal.setTimeZone(TimeZone.getDefault());
        cal.set(Calendar.HOUR_OF_DAY, 0);cal.set(Calendar.MINUTE, 0);cal.set(Calendar.SECOND, 0);cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    public static void resetUsageAndDate(Context context)
    {
        Calendar cal = getCurrentCal();

//        cal.set(Calendar.YEAR, 1970);
//        cal.set(Calendar.MONTH, 1);
//        cal.set(Calendar.DAY_OF_WEEK, 1);
//        cal.set(Calendar.HOUR_OF_DAY, 0);
//        cal.set(Calendar.MINUTE, 0);
//        cal.set(Calendar.SECOND, 0);
//        cal.set(Calendar.MILLISECOND, 0);

        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH, 6);
        cal.set(Calendar.DAY_OF_MONTH, 15);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        long lastSavedDate= cal.getTimeInMillis();

        PreferenceUtil.setLong(context, CLib.IKEYS.KEY_LAST_USAGE_DATE, lastSavedDate);
        PreferenceUtil.setInt(context, CLib.IKEYS.KEY_USAGE_COUNT, 0);
    }
}









