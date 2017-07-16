package ratemyapp.jagerfield.appratebotlib.builders;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import ratemyapp.jagerfield.appratebotlib.Utils.CLib;
import ratemyapp.jagerfield.appratebotlib.Utils.PreferenceUtil;

/**
 * Two main functions
 * 1- checkPreferencesLastUsageDate(context): Check that there is a valid lastUsageDate stored in the Preferences,
 *    if missing or first time running then reset usage count and last usage values.
 *
 * 2- updateUsageInformation(): Compare the current date to the saved last usage date. If the difference is a day or more,
 *    then update usage count and last usage date values in Preferences.
 */
public class UsageMonitor
{
    private Context context;
    private static UsageMonitor instance;
    private UsageMonitor(Context context)
    {
        this.context = context;
        checkPreferencesLastUsageDate(context);
    }

    public static UsageMonitor newInstance(Context context)
    {
        return new UsageMonitor(context);
    }

    public void updateUsageInformation()
    {
        Calendar cal = getCurrentCal();
        Calendar lastUsageCal = getLastUsageCal(context);

        showCalendarsValues(cal, lastUsageCal);

        int i = cal.compareTo(lastUsageCal);

        if (i>0)
        {
            int count = PreferenceUtil.getInt(context, CLib.IKEYS.KEY_USAGE_COUNT, 0);
            //Update usage count
            PreferenceUtil.setInt(context, CLib.IKEYS.KEY_USAGE_COUNT, count + 1);
            //Update last usage date
            PreferenceUtil.setLong(context, CLib.IKEYS.KEY_LAST_USAGE_DATE, getCurrentCal().getTimeInMillis());
        }
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
    }

    public static Calendar getLastUsageCal(Context context)
    {
        Calendar lastCal = getCurrentCal();
        long lastSavedDate = PreferenceUtil.getLong(context, CLib.IKEYS.KEY_LAST_USAGE_DATE, 0l);
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
//        cal.set(Calendar.HOUR_OF_DAY, 0);cal.set(Calendar.MINUTE, 0);cal.set(Calendar.SECOND, 0);cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    public void resetUsageAndDate(Context context)
    {
        Calendar cal = getCurrentCal();

        cal.set(Calendar.YEAR, 1970);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_WEEK, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        long lastUsageDate= cal.getTimeInMillis();

        PreferenceUtil.setLong(context, CLib.IKEYS.KEY_LAST_USAGE_DATE, lastUsageDate);
        PreferenceUtil.setInt(context, CLib.IKEYS.KEY_USAGE_COUNT, 0);
    }

    public void checkPreferencesLastUsageDate(Context context)
    {
        long lastSavedDate = PreferenceUtil.getLong(context, CLib.IKEYS.KEY_LAST_USAGE_DATE, 0l);
        if (lastSavedDate==0) { resetUsageAndDate(context); }
    }

    private void showCalendarsValues(Calendar cal, Calendar lastUsageCal) {
        int yearCal = cal.get(Calendar.YEAR);
        int monthCal = cal.get(Calendar.MONTH);

        int weekCal = cal.get(Calendar.WEEK_OF_YEAR);
        int dayOfMonthCal = cal.get(Calendar.DAY_OF_MONTH);
        int dayOfWeekCal = cal.get(Calendar.DAY_OF_WEEK);

        int hoursCal = cal.get(Calendar.HOUR_OF_DAY);
        int minutesCal = cal.get(Calendar.MINUTE);
        int secondsCal = cal.get(Calendar.SECOND);
        int milliSecCal = cal.get(Calendar.MILLISECOND);


        int yearLastUsageCal = lastUsageCal.get(Calendar.YEAR);
        int monthLastUsageCal = lastUsageCal.get(Calendar.MONTH);

        int weekLastUsageCal = lastUsageCal.get(Calendar.WEEK_OF_YEAR);
        int dayOfMonthLastUsageCal = lastUsageCal.get(Calendar.DAY_OF_MONTH);
        int dayOfWeekLastUsageCal = lastUsageCal.get(Calendar.DAY_OF_WEEK);

        int hoursLastUsageCal = lastUsageCal.get(Calendar.HOUR_OF_DAY);
        int minutesLastUsageCal = lastUsageCal.get(Calendar.MINUTE);
        int secondsLastUsageCal = lastUsageCal.get(Calendar.SECOND);
        int milliSecLastUsageCal = lastUsageCal.get(Calendar.MILLISECOND);
    }

    private String getDate(long time)
    {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getDefault());
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));

        return sdf.format(date);
    }

    public static int getUsageCount(Context context)
    {
       int count = PreferenceUtil.getInt(context, CLib.IKEYS.KEY_USAGE_COUNT, 0);
        return count;
    }
}









