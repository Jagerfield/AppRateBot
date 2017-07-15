package ratemyapp.jagerfield.appratebotlib.builders.not_used;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.util.concurrent.TimeUnit;
import ratemyapp.jagerfield.appratebotlib.Utils.CLib;
import ratemyapp.jagerfield.appratebotlib.Utils.PreferenceUtil;
import ratemyapp.jagerfield.appratebotlib.builders.RatingStatusEnum;
import ratemyapp.jagerfield.appratebotlib.builders.time_only.TimeOnlyBuilder;

public class BuilderHelperParent
{
    public Context context;

    public BuilderHelperParent(Context context) {
        this.context = context;
    }

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


}
