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
    private static int activationUsageCount;
    private static Context context;
    private static Activity activity;
    private static String title;
    private static String description;
    private static int icon = 0;
    private static TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    private static long activationTime = 0;
    private static BuilderTypeEnum builderType;
    private static long timeBetweenUsages = 0;
    private static RatingsBuilder instance;

    private RatingsBuilder(Activity activity)
    {
        this.activity = activity;
        this.context = activity.getApplicationContext();
    }

    public static RatingsBuilder getNewInstance(Activity activity)
    {
        if(instance == null)
        {
            instance = new RatingsBuilder(activity);
        }

        return instance;
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
        return this;
    }

    @Override
    public IBuilderFunctions setActivationTimeAndUsageCount(TimeUnit timeUnit_, long activationTime_, int usageCount_)
    {
        builderType = BuilderTypeEnum.TIME_AND_USAGE_COUNT;
        activationUsageCount = usageCount_;
        activationTime = activationTime_;
        timeUnit = timeUnit_;
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
            RatingStatusEnum ratingStatus = RateLibUtil.getInstance().getRatingStatus(context);

            if (builderType == BuilderTypeEnum.TIME_AND_USAGE_COUNT)
            {
                int usageCount = PreferenceUtil.getInt(context, RateLibUtil.IKEYS.KEY_USAGE_COUNT, 0);
                if (usageCount < activationUsageCount) { throw new IllegalStateException("Usage count is not enough"); }
            }

            switch (ratingStatus)
            {
                case NOT_ASKED:
                    isItOkToAskForFirstTime(timeUnit, activationTime, new RatingsBuilder.ICallback() {
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
                    isItTimeToAskAgain(timeUnit, activationTime, new RatingsBuilder.ICallback() {
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
     *  Functions :  isItOkToAskForFirstTime  -   isItTimeToAskAgain
     *
     * ********************************************************************************************/

    public boolean isItOkToAskForFirstTime(TimeUnit timeUnit, long timeAmount, RatingsBuilder.ICallback client) throws Exception
    {
        boolean result = false;
        long currentDate = 0l;
        long installationDate = 0l;
        long timeSiceInstallation = 0l;
        long periodCriteria = RateLibUtil.getInstance().getTimeActivationPeriod(timeUnit, timeAmount);

        currentDate = Calendar.getInstance().getTimeInMillis();
        installationDate = RateLibUtil.getInstance().getAppInstallationDate(context);
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

    public boolean isItTimeToAskAgain(TimeUnit timeUnit, long timeAmount, RatingsBuilder.ICallback client)
    {
        boolean result = false;
        long currentDate = 0l;
        long askMeAgainDate = 0l;
        long timeSinceInstallation = 0l;

        try
        {
            long periodCriteria = RateLibUtil.getInstance().getTimeActivationPeriod(timeUnit, timeAmount);
            currentDate = Calendar.getInstance().getTimeInMillis();
            askMeAgainDate = RateLibUtil.getInstance().getAskMeLaterDate(context);
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
     * Static Functions
     *
     * ********************************************************************************************/

    public static TimeUnit getTimeUnit()
    {
        return timeUnit;
    }

    public static long getActivationTime()
    {
        long l = activationTime;
        return activationTime;
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
