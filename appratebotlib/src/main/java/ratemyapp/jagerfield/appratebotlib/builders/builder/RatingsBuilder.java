package ratemyapp.jagerfield.appratebotlib.builders.builder;

import android.app.Activity;
import android.content.Context;
import java.util.concurrent.TimeUnit;
import ratemyapp.jagerfield.appratebotlib.Func.Funcs;
import ratemyapp.jagerfield.appratebotlib.Utils.C;
import ratemyapp.jagerfield.appratebotlib.Utils.PreferenceUtil;
import ratemyapp.jagerfield.appratebotlib.dialog.RatingDialog;

public class RatingsBuilder implements IBuilder
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
     *  Build Funcs :  getTitle  -  setDescription  -  setIcon  -  setUsageCountPeriodSeparation
     *                     setActivationTimeAndUsageCount  -  build
     *
     * ********************************************************************************************/

    public String getTitle() {
        return title;
    }

    @Override
    public IBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public IBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public IBuilder setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    @Override
    public IBuilder setUsageCountPeriodSeparation(long mSeconds)
    {
        this.timeBetweenUsages = mSeconds;
        return this;
    }

    @Override
    public IBuilder setActivationTimeAndUsageCount(TimeUnit timeUnit_, long activationTime_, int usageCount_)
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
            RatingStatusEnum ratingStatus = Funcs.getInstance().getRatingStatus(context);

            if (builderType == BuilderTypeEnum.TIME_AND_USAGE_COUNT)
            {
                int usageCount = PreferenceUtil.getInt(context, C.IKEYS.KEY_USAGE_COUNT, 0);
                if (usageCount < activationUsageCount) { throw new IllegalStateException("Usage count is not enough"); }
            }

            switch (ratingStatus)
            {
                case NOT_ASKED:
                    Funcs.getInstance().isItOkToAskForFirstTime(context, timeUnit, activationTime, new Funcs.ICallback() {
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
                    Funcs.getInstance().isItTimeToAskAgain(context, timeUnit, activationTime, new Funcs.ICallback() {
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
     * Helper Funcs : getDescription  -  getIcon  -  checkParamerters
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
     * Static Funcs
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

}
