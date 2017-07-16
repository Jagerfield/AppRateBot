package ratemyapp.jagerfield.appratebotlib.builders;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import ratemyapp.jagerfield.appratebotlib.Utils.CLib;
import ratemyapp.jagerfield.appratebotlib.Utils.PreferenceUtil;
import ratemyapp.jagerfield.appratebotlib.builders.time_only.TimeOnlyBuilder;

public class ShowRatingsDialogHelper
{
    private ShowRatingsDialogHelper(Context context)
    {
        this.context = context;
    }

    public static ShowRatingsDialogHelper getNewInstance(Context context)
    {
        return new ShowRatingsDialogHelper(context);
    }

    public Context context;

    public RatingStatusEnum getRatingStatus()  throws Exception
    {
        int result;

        if (CLib.sysIsBroken(context)) { throw new IllegalArgumentException("Context is null"); }

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

        if (askMeLaterDate == 0)
        {
            PreferenceUtil.setInt(context, CLib.IKEYS.KEY_RATINGS_STATE, RatingStatusEnum.fromEnumToInt(RatingStatusEnum.NEVER));
            throw new IllegalStateException("Can't get askMeLaterDate");
        }

        return askMeLaterDate;
    }

    private long getTimePeriod(TimeUnit timeUnit, int timeAmount)
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

    public boolean isItOkToAskForFirstTime(Activity activity, TimeUnit timeUnit, int timeAmount, TimeOnlyBuilder.ICallback client) throws Exception
    {
        boolean result = false;
        long currentDate = 0l;
        long installationDate = 0l;
        long timeSiceInstallation = 0l;
        long periodCriteria = getTimePeriod(timeUnit, timeAmount);

        currentDate = Calendar.getInstance().getTimeInMillis();
        installationDate = getAppInstallationDate();
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

    public boolean isItTimeToAskAgain(Activity activity, TimeUnit timeUnit, int timeAmount, TimeOnlyBuilder.ICallback client)
    {
        boolean result = false;
        long currentDate = 0l;
        long askMeAgainDate = 0l;
        long timeSinceInstallation = 0l;
        long periodCriteria = getTimePeriod(timeUnit, timeAmount);

        try
        {
            currentDate = Calendar.getInstance().getTimeInMillis();
            askMeAgainDate = getAskMeLaterDate();
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
}










