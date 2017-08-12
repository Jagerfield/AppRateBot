package ratemyapp.jagerfield.appratebotlib.Func;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import ratemyapp.jagerfield.appratebotlib.Utils.C;
import ratemyapp.jagerfield.appratebotlib.Utils.PreferenceUtil;
import ratemyapp.jagerfield.appratebotlib.builders.builder.RatingStatusEnum;
import ratemyapp.jagerfield.appratebotlib.builders.builder.RatingsBuilder;

public class Funcs
{
    private static Funcs instance;
    public static Funcs getInstance()
    {
        if (instance == null)
        {
            instance = new Funcs();
        }

        return instance;
    }

    private Funcs() {
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
        long askMeLaterDate = PreferenceUtil.getLong(context, C.IKEYS.KEY_ASK_AGAIN_DATE, 0l);

//        if (askMeLaterDate == 0)
//        {
//            PreferenceUtil.setInt(context, C.IKEYS.KEY_RATINGS_STATE, RatingStatusEnum.fromEnumToInt(RatingStatusEnum.NEVER));
//            throw new IllegalStateException("Can't get askMeLaterDate");
//        }

        return askMeLaterDate;
    }

    public RatingStatusEnum getRatingStatus(Context context)
    {
        int result;

        result = PreferenceUtil.getInt(context, C.IKEYS.KEY_RATINGS_STATE, -9);

        if (result == -9 )
        {
            result = RatingStatusEnum.fromEnumToInt(RatingStatusEnum.NOT_ASKED);
            PreferenceUtil.setInt(context, C.IKEYS.KEY_RATINGS_STATE, result);
        }

        return RatingStatusEnum.fromIntToEnum(result);
    }

    public int getUsageCount(Context context) throws Exception
    {
        if (C.sysIsBroken(context)) { throw new IllegalArgumentException("Context is null"); }
        int count = PreferenceUtil.getInt(context, C.IKEYS.KEY_USAGE_COUNT, 0);
        return count;
    }

    public String getFormatedCurrentDateString()  throws Exception
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getDefault());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return format.format(cal.getTime());
    }

    public String getFormatedLastUsageDateString(Context context) throws Exception
    {
        Calendar lastCal = Calendar.getInstance();
        long lastSavedDate = PreferenceUtil.getLong(context, C.IKEYS.KEY_LAST_USAGE_DATE, 0l);
        lastCal.setTimeInMillis(lastSavedDate);

        lastCal.setTimeZone(TimeZone.getDefault());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        return format.format(lastCal.getTime());
    }

    public String getAppInstallationDateString(Context context)  throws Exception
    {
        Calendar cal = Calendar.getInstance();
        PackageManager packageManager =  context.getPackageManager();
        long installTimeInMilliseconds;
        PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        installTimeInMilliseconds = packageInfo.firstInstallTime;

        cal.setTimeInMillis(installTimeInMilliseconds);
        cal.setTimeZone(TimeZone.getDefault());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String date = format.format(cal.getTime());

        return date;
    }

    public String getAskMeLaterDateString(Context context)  throws Exception
    {
        Calendar cal = Calendar.getInstance();
        long askMeLaterDate = PreferenceUtil.getLong(context, C.IKEYS.KEY_ASK_AGAIN_DATE, 0l);
        cal.setTimeInMillis(askMeLaterDate);

        cal.setTimeZone(TimeZone.getDefault());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        String date = format.format(cal.getTime());
        return date;
    }

    public long getTimeActivationPeriod(TimeUnit timeUnit, long timeAmount)  throws Exception
    {
        if (timeUnit==null || timeAmount <=0){ return 0; }

        long period = 0l;
        switch (timeUnit)
        {
            case HOURS:
                period = TimeUnit.HOURS.toMillis(timeAmount);
                break;
            case DAYS:
                period = TimeUnit.DAYS.toMillis(timeAmount);
                break;
            case MINUTES:
                period = TimeUnit.MINUTES.toMillis(timeAmount);
                break;
            case SECONDS:
                period = TimeUnit.SECONDS.toMillis(timeAmount);
                break;
            case MILLISECONDS:
                period = timeAmount;
                break;
            default:
                period = 0;
                break;
        }

        return period;
    }


    /**
     * Activation time period is set by the user. If Rating status is "Not asked", then the UI will show
     * time till next activation = Activation time period + date of installation.
     * Else,
     * If Rating status is "Ask me later" then the UI will show
     * time till next activation = Activation time period + date of ask me later.
     */
    public String claculateNextActivationDate(Context context) throws Exception
    {
        long result = 0;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        try
        {
            TimeUnit timeUnit = RatingsBuilder.getTimeUnit();
            long activationTime = RatingsBuilder.getActivationTime();
            long waitingPeriod = getTimeActivationPeriod(timeUnit, activationTime);

            RatingStatusEnum ratingStatus = getRatingStatus(context);

            switch (ratingStatus)
            {
                case NOT_ASKED:
                    long appInstallationDate = getAppInstallationDate(context);
                    result = waitingPeriod + appInstallationDate;
                    break;
                case NEVER:
                    break;
                case LATER:
                    long askMeLaterDate = getAskMeLaterDate(context);
                    result = waitingPeriod + askMeLaterDate;
                    break;
                default:
                    throw new Exception("Not an acceptable argument");
            }

            cal.setTimeInMillis(result);
            cal.setTimeZone(TimeZone.getDefault());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new Exception("Function claculateNextActivationDate failed");
        }

        return format.format(cal.getTime());
    }


    /**
     *  Funcs :  isItOkToAskForFirstTime  -   isItTimeToAskAgain
     *
     * ********************************************************************************************/

    public boolean isItOkToAskForFirstTime(Context context, TimeUnit timeUnit, long timeAmount, ICallback client) throws Exception
    {
        boolean result = false;
        long currentDate = 0l;
        long installationDate = 0l;
        long timeSiceInstallation = 0l;
        long periodCriteria = Funcs.getInstance().getTimeActivationPeriod(timeUnit, timeAmount);

        currentDate = Calendar.getInstance().getTimeInMillis();
        installationDate = Funcs.getInstance().getAppInstallationDate(context);
        timeSiceInstallation = installationDate + periodCriteria;

        if (periodCriteria == 0) {  throw new IllegalStateException("periodCriteria is 0");  }
        if (client == null) { throw new IllegalStateException("client is null");  }
        if (installationDate == 0) { throw new IllegalArgumentException("installationDate = 0"); }

        if (currentDate > timeSiceInstallation)
        {
            result = true;
            client.showRatingDialog(); //Call back
        }

        return result;
    }

    public boolean isItTimeToAskAgain(Context context, TimeUnit timeUnit, long timeAmount, ICallback client)
    {
        boolean result = false;
        long currentDate = 0l;
        long askMeAgainDate = 0l;
        long timeSinceInstallation = 0l;

        try
        {
            long periodCriteria = Funcs.getInstance().getTimeActivationPeriod(timeUnit, timeAmount);
            currentDate = Calendar.getInstance().getTimeInMillis();
            askMeAgainDate = Funcs.getInstance().getAskMeLaterDate(context);
            timeSinceInstallation = askMeAgainDate + periodCriteria;

            if (client == null) { throw new IllegalStateException("client is null"); }
            if (askMeAgainDate==0) { throw new IllegalStateException("askMeAgainDate is 0"); }
            if (timeSinceInstallation == 0) { throw new IllegalArgumentException("installationDate = 0"); }

            if (currentDate > timeSinceInstallation)
            {
                result = true;

                /**
                 *  Show message on Activity
                 *
                 *
                 *
                 *
                 */
                client.showRatingDialog();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result = false;
        }

        return result;
    }


    /**
     * Data Structres :  ICallback   -   BuilderTypeEnum
     *
     * ********************************************************************************************/

    public interface ICallback
    {
        void showRatingDialog();
    }
}
