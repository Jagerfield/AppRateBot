package ratemyapp.jagerfield.appratebotlib.builders.not_used;

import android.content.Context;

public class UsageAndTimeHelper
{
    public UsageAndTimeHelper(Context context)
    {
        this.context = context;
    }

//    public static UsageAndTimeHelper getNewInstance(Context context)
//    {
//        return new UsageAndTimeHelper(context);
//    }

    public Context context;
//
//    public RatingStatusEnum getRatingStatus()  throws Exception
//    {
//        int result;
//
//        if (CLib.sysIsBroken(context))
//        {
//            throw new IllegalArgumentException("Context is null");
//        }
//
//        result = PreferenceUtil.getInt(context, CLib.IKEYS.KEY_RATINGS_STATE, -9);
//
//        if (result == -9 )
//        {
//            result = RatingStatusEnum.fromEnumToInt(RatingStatusEnum.NOT_ASKED);
//            PreferenceUtil.setInt(context, CLib.IKEYS.KEY_RATINGS_STATE, result);
//        }
//
//        return RatingStatusEnum.fromIntToEnum(result);
//    }
//
//    public long getAppInstallationDate() throws Exception
//    {
//        PackageManager packageManager =  context.getPackageManager();
//        long installTimeInMilliseconds;
//
//        PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
//        installTimeInMilliseconds = packageInfo.firstInstallTime;
//
//        return installTimeInMilliseconds;
//    }
//
//    public int getAppUsageCount() throws Exception
//    {
//        int usageCount = PreferenceUtil.getInt(context, CLib.IKEYS.KEY_USAGE_COUNT, -9);
//
//        return usageCount;
//    }
//
//    public long getAskMeLaterDate() throws Exception
//    {
//        long askMeLaterDate = PreferenceUtil.getLong(context, CLib.IKEYS.KEY_ASK_AGAIN_DATE, 0l);
//
//        if (askMeLaterDate == 0 )
//        {
//            PreferenceUtil.setInt(context, CLib.IKEYS.KEY_RATINGS_STATE, RatingStatusEnum.fromEnumToInt(RatingStatusEnum.NEVER));
//            throw new IllegalStateException("Can't get askMeLaterDate");
//        }
//
//        return askMeLaterDate;
//    }
//
//    private long getTimeActivationPeriod(TimeUnit timeUnit, int timeAmount)
//    {
//        long period = 0l;
//        switch (timeUnit)
//        {
//            case DAYS:
//                period = TimeUnit.DAYS.toMillis(timeAmount);
//                break;
//            case HOURS:
//                period = TimeUnit.HOURS.toMillis(timeAmount);
//                break;
//            case MINUTES:
//                period = TimeUnit.MINUTES.toMillis(timeAmount);
//                break;
//            case SECONDS:
//                period = TimeUnit.SECONDS.toMillis(timeAmount);
//                break;
//        }
//
//        return period;
//    }
//
//    public boolean isItOkToAskForFirstTime(Activity activity, TimeUnit timeUnit, int timeAmount, TimeOnlyBuilder.ICallback client)
//    {
//        boolean result = false;
//        long getCurrentCal = 0l;
//        long installationDate = 0l;
//        long thresholdPeriod = 0l;
//        long activationPeriod = getTimeActivationPeriod(timeUnit, timeAmount);
//
//        try
//        {
//            if (client == null)
//            {
//                throw new IllegalStateException("client is null");
//            }
//
//            getCurrentCal = System.currentTimeMillis();
//            installationDate = getAppInstallationDate();
//            thresholdPeriod = installationDate + activationPeriod;
//
////            String time_currentDate = getDate(getCurrentCal);
////            String time_installationDate = getDate(installationDate);
////            String time_timeSiceInstallation = getDate(timeSiceInstallation);
//
//            if (installationDate == 0)
//            {
//                throw new IllegalArgumentException("installationDate = 0");
//            }
//
//            if (getCurrentCal > thresholdPeriod)
//            {
//                result = true;
//
//                /**
//                 *  Show message on Activity
//                 *
//                 *
//                 *
//                 *
//                 */
//                client.showRatingDialog();
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            result = false;
//        }
//
//        return result;
//    }
//
//    public boolean isItTimeToAskAgain(Activity activity, TimeUnit timeUnit, int timeAmount, TimeOnlyBuilder.ICallback client)
//    {
//        boolean result = false;
//        long getCurrentCal = 0l;
//        long askMeAgainDate = 0l;
//        long thresholdPeriod = 0l;
//        long activationPeriod = getTimeActivationPeriod(timeUnit, timeAmount);
//
//        try
//        {
//            if (client == null)
//            {
//                throw new IllegalStateException("client is null");
//            }
//
//            getCurrentCal = System.currentTimeMillis();
//
//            askMeAgainDate = getAskMeLaterDate();
//
//            if (askMeAgainDate==0)
//            {
//                throw new IllegalStateException("askMeAgainDate is 0");
//            }
//
//            thresholdPeriod = askMeAgainDate + activationPeriod;
//
//            if (getCurrentCal > thresholdPeriod)
//            {
//                result = true;
//
//                /**
//                 *  Show message on Activity
//                 *
//                 *
//                 *
//                 *
//                 */
//                client.showRatingDialog();
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            result = false;
//        }
//
//        return result;
//    }

//    public String getDate(long time)
//    {
//        Date date = new Date(time);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        sdf.setTimeZone(TimeZone.getDefault());
////        sdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));
//
//        return sdf.format(date);
//    }

//    public void updateUsageCountAndDate()
//    {
//        int count = PreferenceUtil.getInt(context, CLib.IKEYS.KEY_USAGE_COUNT, -9);
//        long lastUsageDate = PreferenceUtil.getLong(context, CLib.IKEYS.KEY_LAST_USAGE_DATE, -9l);
//
//        Calendar lastDate = Calendar.getInstance();
//
//        lastDate.setWeekDate(2017, 21, Calendar.FRIDAY);//requires SDK24
//
////        lastDate.setTimeInMillis(lastUsageDate);
//
//        int lastDayOfWeek = lastDate.get(Calendar.DAY_OF_WEEK);
//        int lastYear = lastDate.get(Calendar.YEAR);
//        int lastMonth = lastDate.get(Calendar.MONTH);
//
//        Calendar getCurrentCal = Calendar.getInstance();
//        int currentDayOfWeek = getCurrentCal.get(Calendar.DAY_OF_WEEK);
//        int currentYear = getCurrentCal.get(Calendar.YEAR);
//        int currentMonth = getCurrentCal.get(Calendar.MONTH);
//
//        //Set usage count and date for the first time
//        if (count==-9)
//        {
//            PreferenceUtil.setInt(context, CLib.IKEYS.KEY_USAGE_COUNT, 1);
//            PreferenceUtil.setLong(context, CLib.IKEYS.KEY_LAST_USAGE_DATE, getCurrentCal.getTimeInMillis());
//            return;
//        }
//
//        if (currentDayOfWeek>lastDayOfWeek || currentYear>lastYear || currentMonth>lastMonth)
//        {
//            PreferenceUtil.setInt(context, CLib.IKEYS.KEY_USAGE_COUNT, count + 1);
//            PreferenceUtil.setLong(context, CLib.IKEYS.KEY_LAST_USAGE_DATE, getCurrentCal.getTimeInMillis());
//        }
//        else
//        {
//
//        }
//
//        String days[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
//    }
}
