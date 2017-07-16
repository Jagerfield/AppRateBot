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
        int year1 = cal.get(Calendar.YEAR);
        int month1 = cal.get(Calendar.MONTH);

        int week1 = cal.get(Calendar.WEEK_OF_YEAR);
        int monthDay = cal.get(Calendar.DAY_OF_MONTH);
        int day1 = cal.get(Calendar.DAY_OF_WEEK);

        int hours1 = cal.get(Calendar.HOUR_OF_DAY);
        int minutes1 = cal.get(Calendar.MINUTE);
        int mseconds1 = cal.get(Calendar.SECOND);


        Calendar lastUsageCal = getLastUsageCal(context);
        int year2 = lastUsageCal.get(Calendar.YEAR);
        int month2 = lastUsageCal.get(Calendar.MONTH);

        int week2 = lastUsageCal.get(Calendar.WEEK_OF_YEAR);
        int lastMonthDay = lastUsageCal.get(Calendar.DAY_OF_MONTH);
        int day22 = lastUsageCal.get(Calendar.DAY_OF_WEEK);

        int hours2 = lastUsageCal.get(Calendar.HOUR_OF_DAY);
        int minutes2 = lastUsageCal.get(Calendar.MINUTE);
        int mseconds2 = lastUsageCal.get(Calendar.SECOND);

        int i = cal.compareTo(lastUsageCal);

        if (i>0)
        {
            int count = PreferenceUtil.getInt(context, CLib.IKEYS.KEY_USAGE_COUNT, 0);
            //Update usage count
            PreferenceUtil.setInt(context, CLib.IKEYS.KEY_USAGE_COUNT, count + 1);

            int year = getCurrentCal().get(Calendar.YEAR);
            int month = getCurrentCal().get(Calendar.MONTH);
            int week = getCurrentCal().get(Calendar.WEEK_OF_YEAR);
            int day_ = getCurrentCal().get(Calendar.DAY_OF_MONTH);
            int day = getCurrentCal().get(Calendar.DAY_OF_WEEK);
            int hours = getCurrentCal().get(Calendar.HOUR_OF_DAY);
            int minutes = getCurrentCal().get(Calendar.MINUTE);
            int mseconds = getCurrentCal().get(Calendar.SECOND);

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
        Calendar lastUsageCal = getSavedLastUsageDate(context);
        lastUsageCal.setTimeZone(TimeZone.getDefault());

        lastUsageCal.set(Calendar.HOUR, 0);lastUsageCal.set(Calendar.MINUTE, 0);lastUsageCal.set(Calendar.SECOND, 0);lastUsageCal.set(Calendar.MILLISECOND, 0);

        int year = lastUsageCal.get(Calendar.YEAR);
        int month = lastUsageCal.get(Calendar.MONTH);
        int week = lastUsageCal.get(Calendar.WEEK_OF_YEAR);
        int day = lastUsageCal.get(Calendar.DAY_OF_WEEK);
        int hours = lastUsageCal.get(Calendar.HOUR_OF_DAY);
        int minutes = lastUsageCal.get(Calendar.MINUTE);
        int mseconds = lastUsageCal.get(Calendar.SECOND);

        return lastUsageCal;
    }


    public Calendar getSavedLastUsageDate(Context context)
    {
        Calendar lastCal = getCurrentCal();

        long lastSavedDate = PreferenceUtil.getLong(context, CLib.IKEYS.KEY_LAST_USAGE_DATE, 0l);

        if (lastSavedDate==0)
        {
            resetUsageAndDate(context);
        }

        lastCal.setTimeInMillis(lastSavedDate);

        int year = lastCal.get(Calendar.YEAR);
        int month = lastCal.get(Calendar.MONTH);
        int week = lastCal.get(Calendar.WEEK_OF_YEAR);
        int day = lastCal.get(Calendar.DAY_OF_WEEK);

        return lastCal;
    }

    public static Calendar getCurrentCal()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getDefault());
        cal.set(Calendar.HOUR, 0);cal.set(Calendar.MINUTE, 0);cal.set(Calendar.SECOND, 0);cal.set(Calendar.MILLISECOND, 0);
//        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_WEEK), 0,0,0); This won't set the Milliseconds to zero and will cause issues
        return cal;
    }

    public static void resetUsageAndDate(Context context)
    {
        Calendar cal = getCurrentCal();

//        cal.set(Calendar.YEAR, 1970);
//        cal.set(Calendar.MONTH, 1);
////        cal.set(Calendar.WEEK_OF_YEAR, 1);
//        cal.set(Calendar.DAY_OF_WEEK, 1);
//        cal.set(Calendar.HOUR_OF_DAY, 0);
//        cal.set(Calendar.MINUTE, 0);
//        cal.set(Calendar.SECOND, 0);
//        cal.set(Calendar.MILLISECOND, 0);

        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH, 6);
        cal.set(Calendar.DAY_OF_MONTH, 2);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        long lastSavedDate= cal.getTimeInMillis();

        PreferenceUtil.setLong(context, CLib.IKEYS.KEY_LAST_USAGE_DATE, lastSavedDate);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_WEEK);

        PreferenceUtil.setInt(context, CLib.IKEYS.KEY_USAGE_COUNT, 0);
    }

}









