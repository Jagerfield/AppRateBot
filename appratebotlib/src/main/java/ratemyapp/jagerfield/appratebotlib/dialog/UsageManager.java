package ratemyapp.jagerfield.appratebotlib.dialog;

import android.content.Context;

import java.util.Calendar;
import java.util.TimeZone;
import ratemyapp.jagerfield.appratebotlib.Utils.RateLibUtil;
import ratemyapp.jagerfield.appratebotlib.Utils.PreferenceUtil;
import ratemyapp.jagerfield.appratebotlib.builders.builder.RatingStatusEnum;

/**
 * Two main functions
 * 1- getSavedLastUsageDate(context): Check that there is a valid lastUsageDate stored in the Preferences,
 *    if missing or first time running then reset usage usageCounter and last usage values.
 *
 * 2- updateAppUsageCount(): Compare the current date to the saved last usage date. If the difference is a day or more,
 *    then update usage usageCounter and last usage date values in Preferences.
 */
public class UsageManager
{
    private Context context;
    private static UsageManager instance;

    private UsageManager(Context context)
    {
        this.context = context;
        getSavedLastUsageDate();
    }

    public static UsageManager newInstance(Context context)
    {
        return new UsageManager(context);
    }

    public void updateAppUsageCount(long mSecondsInBetween)
    {
        Calendar cal = getCurrentCal();
        Calendar lastUsageCal = getLastUsageCal(context, mSecondsInBetween);

        inspectCalendarsValues(cal, lastUsageCal);

        int i = cal.compareTo(lastUsageCal);

        if (i>0)
        {
            int usageCounter = PreferenceUtil.getInt(context, RateLibUtil.IKEYS.KEY_USAGE_COUNT, 0);
            //Update usage usageCounter
            PreferenceUtil.setInt(context, RateLibUtil.IKEYS.KEY_USAGE_COUNT, usageCounter + 1);
            //Update last usage date
            PreferenceUtil.setLong(context, RateLibUtil.IKEYS.KEY_LAST_USAGE_DATE, getCurrentCal().getTimeInMillis());
        }
    }

    public Calendar getLastUsageCal(Context context, long mSecondsInBetween)
    {
        Calendar lastCal = getCurrentCal();
        long lastSavedDate = PreferenceUtil.getLong(context, RateLibUtil.IKEYS.KEY_LAST_USAGE_DATE, 0l);
        lastCal.setTimeInMillis(lastSavedDate + mSecondsInBetween);
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

    public void resetUsageAndDate()
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

        PreferenceUtil.setLong(context, RateLibUtil.IKEYS.KEY_LAST_USAGE_DATE, lastUsageDate);
        PreferenceUtil.setInt(context, RateLibUtil.IKEYS.KEY_USAGE_COUNT, 0);
        PreferenceUtil.setInt(context, RateLibUtil.IKEYS.KEY_RATINGS_STATE, RatingStatusEnum.fromEnumToInt(RatingStatusEnum.NOT_ASKED));
    }

    public void getSavedLastUsageDate()
    {
        long lastSavedDate = PreferenceUtil.getLong(context, RateLibUtil.IKEYS.KEY_LAST_USAGE_DATE, 0l);
        if (lastSavedDate==0) { resetUsageAndDate(); }
    }

    private void inspectCalendarsValues(Calendar cal, Calendar lastUsageCal) {
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

    public CalendarElements getCurrentDate()
    {
        Calendar cal = Calendar.getInstance();
        cal = customizeCal(cal);
        return new CalendarElements(cal);
    }

    public CalendarElements getUsageDate(Context context)
    {
        Calendar lastCal = getCurrentCal();
        long lastSavedDate = PreferenceUtil.getLong(context, RateLibUtil.IKEYS.KEY_LAST_USAGE_DATE, 0l);
        lastCal.setTimeInMillis(lastSavedDate);
        lastCal = customizeCal(lastCal);

        return new CalendarElements(lastCal);
    }

    public class CalendarElements
    {
        private int year;
        private int month;
        private int week;
        private int dayOfMonth;
        private int dayOfWeek;
        private int hours;
        private int minutes;
        private int seconds;
        private int milliSec;

        public CalendarElements(Calendar cal)
        {
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            week = cal.get(Calendar.WEEK_OF_YEAR);
            dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
            dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            hours = cal.get(Calendar.HOUR_OF_DAY);
            minutes = cal.get(Calendar.MINUTE);
            seconds = cal.get(Calendar.SECOND);
            milliSec = cal.get(Calendar.MILLISECOND);
        }

        public int getYear() {
            return year;
        }

        public int getMonth() {
            return month;
        }

        public int getWeek() {
            return week;
        }

        public int getDayOfMonth() {
            return dayOfMonth;
        }

        public int getDayOfWeek() {
            return dayOfWeek;
        }

        public int getHours() {
            return hours;
        }

        public int getMinutes() {
            return minutes;
        }

        public int getSeconds() {
            return seconds;
        }

        public int getMilliSec() {
            return milliSec;
        }
    }

//    public void updateAppUsageCount(Calendar calA, Calendar calB)
//    {
//        int i = calA.compareTo(calB);
//
//        if (i>0)
//        {
//            int count = PreferenceUtil.getInt(context, RateLibUtil.IKEYS.KEY_USAGE_COUNT, 0);
//            //Update usage usageCounter
//            PreferenceUtil.setInt(context, RateLibUtil.IKEYS.KEY_USAGE_COUNT, count + 1);
//            //Update last usage date
//            PreferenceUtil.setLong(context, RateLibUtil.IKEYS.KEY_LAST_USAGE_DATE, getCurrentCal().getTimeInMillis());
//        }
//    }

//    private String getDate(long time)
//    {
//        Date date = new Date(time);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        sdf.setTimeZone(TimeZone.getDefault());
////        sdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));
//
//        return sdf.format(date);
//    }
}









