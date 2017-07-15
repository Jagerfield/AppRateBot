package ratemyapp.jagerfield.appratebotlib.builders;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import ratemyapp.jagerfield.appratebotlib.Utils.CLib;
import ratemyapp.jagerfield.appratebotlib.Utils.PreferenceUtil;
import ratemyapp.jagerfield.appratebotlib.builders.time_only.TimeOnlyBuilder;


public class BuilderHelper
{
    private BuilderHelper(Context context)
    {
        this.context = context;
    }

    public static BuilderHelper getNewInstance(Context context)
    {
        return new BuilderHelper(context);
    }

    public Context context;

    public RatingStatusEnum getRatingStatus()  throws Exception
    {
        int result;

        if (CLib.sysIsBroken(context))
        {
            throw new IllegalArgumentException("Context is null");
        }

        result = PreferenceUtil.getInt(context, CLib.IKEYS.KEY_RATINGS_STATE, -9);

        if (result == -9 )
        {
            result = RatingStatusEnum.fromEnumToInt(RatingStatusEnum.NOT_ASKED);
            PreferenceUtil.setInt(context, CLib.IKEYS.KEY_RATINGS_STATE, result);
        }

        return RatingStatusEnum.fromIntToEnum(result);
    }

    public long getAppInstallationDate() throws Exception
    {
        PackageManager packageManager =  context.getPackageManager();
        long installTimeInMilliseconds;

        PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        installTimeInMilliseconds = packageInfo.firstInstallTime;

        return installTimeInMilliseconds;
    }

    public long getAskMeLaterDate() throws Exception
    {
        long askMeLaterDate = PreferenceUtil.getLong(context, CLib.IKEYS.KEY_ASK_AGAIN_DATE, 0l);

        if (askMeLaterDate == 0 )
        {
            PreferenceUtil.setInt(context, CLib.IKEYS.KEY_RATINGS_STATE, RatingStatusEnum.fromEnumToInt(RatingStatusEnum.NEVER));
            throw new IllegalStateException("Can't get askMeLaterDate");
        }

        return askMeLaterDate;
    }

    private long getTimePeriod(TimeUnit timeUnit, int timeAmount)
    {
        long period = 0l;
        switch (timeUnit)
        {
            case DAYS:
                period = TimeUnit.DAYS.toMillis(timeAmount);
                break;
            case HOURS:
                period = TimeUnit.HOURS.toMillis(timeAmount);
                break;
            case MINUTES:
                period = TimeUnit.MINUTES.toMillis(timeAmount);
                break;
            case SECONDS:
                period = TimeUnit.SECONDS.toMillis(timeAmount);
                break;
        }

        return period;
    }

    public boolean isItOkToAskForFirstTime(Activity activity, TimeUnit timeUnit, int timeAmount, TimeOnlyBuilder.ICallback client)
    {
        boolean result = false;
        long currentDate = 0l;
        long installationDate = 0l;
        long timeSiceInstallation = 0l;
        long periodCriteria = getTimePeriod(timeUnit, timeAmount);

        try
        {
            if (client == null)
            {
                throw new IllegalStateException("client is null");
            }

            currentDate = System.currentTimeMillis();
            installationDate = getAppInstallationDate();
            timeSiceInstallation = installationDate + periodCriteria;

            if (installationDate == 0)
            {
                throw new IllegalArgumentException("installationDate = 0");
            }

            if (currentDate > timeSiceInstallation)
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

    public boolean isItTimeToAskAgain(Activity activity, TimeUnit timeUnit, int timeAmount, TimeOnlyBuilder.ICallback client)
    {
        boolean result = false;
        long currentDate = 0l;
        long askMeAgainDate = 0l;
        long timeSinceInstallation = 0l;
        long periodCriteria = getTimePeriod(timeUnit, timeAmount);

        try
        {
            currentDate = SystemClock.uptimeMillis();
            askMeAgainDate = getAskMeLaterDate();


            if (client == null)
            {
                throw new IllegalStateException("client is null");
            }

            if (askMeAgainDate==0)
            {
                throw new IllegalStateException("askMeAgainDate is 0");
            }

            timeSinceInstallation = askMeAgainDate + periodCriteria;

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

    private String getDate(long time)
    {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getDefault());
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));

        return sdf.format(date);
    }

    public long getAppUsageCount() throws Exception
    {
        long usageCount = PreferenceUtil.getLong(context, CLib.IKEYS.KEY_USAGE_COUNT, -9l);

        if (usageCount == -9 )
        {
            usageCount = 1l;
            PreferenceUtil.setLong(context, CLib.IKEYS.KEY_USAGE_COUNT, usageCount);
        }

        return usageCount;
    }

}










