package ratemyapp.jagerfield.appratebotlib.builders.builder;

import android.app.Activity;
import android.content.Context;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import ratemyapp.jagerfield.appratebotlib.Utils.RateLibUtil;
import ratemyapp.jagerfield.appratebotlib.Utils.PreferenceUtil;
import ratemyapp.jagerfield.appratebotlib.dialog.RatingDialog;

public class RatingsBuilder implements IBuilderFunctions
{
    private int activationUsageCount;
    private final Context context;
    private final Activity activity;
    private String title;
    private String description;
    private int icon = 0;
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    long activationTime = 0;
    private BuilderTypeEnum builderType;
    private long timeBetweenUsages = 0;
    private static RatingsBuilder instance;

    public RatingsBuilder(Activity activity)
    {
        this.activity = activity;
        this.context = activity.getApplicationContext();
    }

    public static RatingsBuilder getNewInstance(Activity activity)
    {
        return new RatingsBuilder(activity);
    }


    /**
     *  Build Functions :  getTitle  -  setDescription  -  setIcon  -  setUsageCountPeriodSeparation
     *                     setActivationTimeAndUsageCount  -  build
     *
     * ********************************************************************************************/

    public String getTitle() {
        return title;
    }

    @Override
    public IBuilderFunctions setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public IBuilderFunctions setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public IBuilderFunctions setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    @Override
    public IBuilderFunctions setUsageCountPeriodSeparation(long mSeconds)
    {
        this.timeBetweenUsages = mSeconds;
        return null;
    }

    @Override
    public IBuilderFunctions setActivationTimeAndUsageCount(TimeUnit timeUnit, long activationTime, int activationUsageCount)
    {
        this.builderType = BuilderTypeEnum.TIME_AND_USAGE_COUNT;
        this.activationUsageCount = activationUsageCount;
        this.activationTime = activationTime;
        this.timeUnit = timeUnit;
        return this;
    }

    @Override
    public void build()
    {
        final RatingDialog[] obj = new RatingDialog[1];
        final RatingsBuilder builder = this;

        try
        {
            checkParamerters();
            RatingStatusEnum ratingStatus = RateLibUtil.newInstance().getRatingStatus(context);

            if (builderType == BuilderTypeEnum.TIME_AND_USAGE_COUNT)
            {
                int usageCount = PreferenceUtil.getInt(context, RateLibUtil.IKEYS.KEY_USAGE_COUNT, 0);
                if (usageCount < activationUsageCount) { throw new IllegalStateException("Usage count is not enough"); }
            }

            switch (ratingStatus)
            {
                case NOT_ASKED:
                    isItOkToAskForFirstTime(activity, timeUnit, activationTime, new RatingsBuilder.ICallback() {
                        @Override
                        public void showRatingDialog()
                        {
                            RatingDialog.getNewInstance(activity, builder).show();
                        }
                    });
                    break;
                case NEVER:
                    break;
                case LATER:
                    isItTimeToAskAgain(activity, timeUnit, activationTime, new RatingsBuilder.ICallback() {
                        @Override
                        public void showRatingDialog()
                        {
                            RatingDialog.getNewInstance(activity, builder).show();
                        }
                    });
                    break;
                default:
                    break;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper Functions : getDescription  -  getIcon  -  checkParamerters
     *
     * ********************************************************************************************/

    public String getDescription() {
        return description;
    }

    public int getIcon() {
        return icon;
    }

    private void checkParamerters() throws Exception
    {
        if (activity == null)
        {
            throw new IllegalStateException("Activity is null");
        }

        if (title == null || title.isEmpty())
        {
            throw new IllegalStateException("title is null");
        }

        if (description == null || description.isEmpty())
        {
            throw new IllegalStateException("description is null");
        }

        if (timeUnit == null)
        {
            timeUnit = TimeUnit.MILLISECONDS;
        }

        if (icon <= 0)
        {
            throw new IllegalStateException("icon is incorrect");
        }

        if (activationTime <= 0)
        {
            activationTime = 0l;
        }

        if (timeBetweenUsages <= 0)
        {
            timeBetweenUsages = 0l;
        }
    }


    /**
     *  Functions :  getTimeActivationPeriod  -  isItOkToAskForFirstTime  -   isItTimeToAskAgain
     *
     * ********************************************************************************************/

    private long getTimeActivationPeriod(TimeUnit timeUnit, long timeAmount)
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

    public boolean isItOkToAskForFirstTime(Activity activity, TimeUnit timeUnit, long timeAmount, RatingsBuilder.ICallback client) throws Exception
    {
        boolean result = false;
        long currentDate = 0l;
        long installationDate = 0l;
        long timeSiceInstallation = 0l;
        long periodCriteria = getTimeActivationPeriod(timeUnit, timeAmount);

        currentDate = Calendar.getInstance().getTimeInMillis();
        installationDate = RateLibUtil.newInstance().getAppInstallationDate(context);
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

    public boolean isItTimeToAskAgain(Activity activity, TimeUnit timeUnit, long timeAmount, RatingsBuilder.ICallback client)
    {
        boolean result = false;
        long currentDate = 0l;
        long askMeAgainDate = 0l;
        long timeSinceInstallation = 0l;
        long periodCriteria = getTimeActivationPeriod(timeUnit, timeAmount);

        try
        {
            currentDate = Calendar.getInstance().getTimeInMillis();
            askMeAgainDate = RateLibUtil.newInstance().getAskMeLaterDate(context);
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

    public enum BuilderTypeEnum
    {
        TIME_ONLY, TIME_AND_USAGE_COUNT;

        public static BuilderTypeEnum fromIntToEnum(int value)
        {
            switch (value)
            {
                case 0:
                    return TIME_ONLY;
                case 1:
                    return TIME_AND_USAGE_COUNT;
                default:
                    return TIME_ONLY;
            }
        }

        public static int fromEnumToInt(BuilderTypeEnum value)
        {
            switch (value)
            {
                case TIME_ONLY:
                    return 0;
                case TIME_AND_USAGE_COUNT:
                    return 1;
                default:
                    return 0;
            }
        }
    }
}
