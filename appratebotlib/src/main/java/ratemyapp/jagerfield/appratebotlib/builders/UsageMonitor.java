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
            int seconds = getCurrentCal().get(Calendar.SECOND);
            int milliSec = getCurrentCal().get(Calendar.MILLISECOND);

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

        if (lastSavedDate==0)
        {
            resetUsageAndDate(context);
        }

        lastCal.setTimeInMillis(lastSavedDate);
        lastCal.setTimeZone(TimeZone.getDefault());
        lastCal.set(Calendar.HOUR_OF_DAY, 0);lastCal.set(Calendar.MINUTE, 0);lastCal.set(Calendar.SECOND, 0);lastCal.set(Calendar.MILLISECOND, 0);

        int year = lastCal.get(Calendar.YEAR);
        int month = lastCal.get(Calendar.MONTH);
        int week = lastCal.get(Calendar.WEEK_OF_YEAR);
        int day = lastCal.get(Calendar.DAY_OF_WEEK);
        int hours = lastCal.get(Calendar.HOUR_OF_DAY);
        int minutes = lastCal.get(Calendar.MINUTE);
        int mseconds = lastCal.get(Calendar.SECOND);

        return lastCal;
    }

    public static Calendar getCurrentCal()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getDefault());
        cal.set(Calendar.HOUR_OF_DAY, 0);cal.set(Calendar.MINUTE, 0);cal.set(Calendar.SECOND, 0);cal.set(Calendar.MILLISECOND, 0);
//        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_WEEK), 0,0,0); This won't set the Milliseconds to zero and will cause issues
        return cal;
    }

    public static void resetUsageAndDate(Context context)
    {
        Calendar cal = getCurrentCal();

        cal.set(Calendar.YEAR, 1970);
        cal.set(Calendar.MONTH, 1);
//        cal.set(Calendar.WEEK_OF_YEAR, 1);
        cal.set(Calendar.DAY_OF_WEEK, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

//        cal.set(Calendar.YEAR, 2017);
//        cal.set(Calendar.MONTH, 6);
//        cal.set(Calendar.DAY_OF_MONTH, 2);
//        cal.set(Calendar.HOUR_OF_DAY, 0);
//        cal.set(Calendar.MINUTE, 0);
//        cal.set(Calendar.SECOND, 0);
//        cal.set(Calendar.MILLISECOND, 0);

        long lastSavedDate= cal.getTimeInMillis();

        PreferenceUtil.setLong(context, CLib.IKEYS.KEY_LAST_USAGE_DATE, lastSavedDate);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int minutes = cal.get(Calendar.MINUTE);
        int mseconds = cal.get(Calendar.SECOND);

        PreferenceUtil.setInt(context, CLib.IKEYS.KEY_USAGE_COUNT, 0);
    }

    private class CalValues
    {
        private int year ;
        private int month;
        private int week;
        private int dayOfMonth;
        private int dayOfWeek;
        private int hourOfDay;
        private int minutes ;
        private int seconds ;
        private int milliSec ;
        private Calendar cal;

        public CalValues(Calendar cal) {
            this.cal = cal;
            getYear();
            getMonth();
            getWeek();
            getDayOfMonth();
            getDayOfWeek();
            getHourOfDay();
            getMinutes();
            getSeconds();
            getMilliSec();
        }

        public int getYear() {
            return cal.get(Calendar.YEAR);
        }

        public int getMonth() {
            return cal.get(Calendar.MONTH);
        }

        public int getWeek() {
            return cal.get(Calendar.WEEK_OF_YEAR);
        }

        public int getDayOfMonth() {
            return cal.get(Calendar.DAY_OF_MONTH);
        }

        public int getDayOfWeek() {
            return cal.get(Calendar.DAY_OF_WEEK);
        }

        public int getHourOfDay() {
            return  cal.get(Calendar.HOUR_OF_DAY);
        }

        public int getMinutes() {
            return cal.get(Calendar.MINUTE);
        }

        public int getSeconds() {
            return cal.get(Calendar.SECOND);
        }

        public int getMilliSec() {
            return cal.get(Calendar.MILLISECOND);
        }
    }
}









