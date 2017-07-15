package ratemyapp.jagerfield.appratebotlib.builders.usage_and_time;

import android.app.Activity;
import android.content.Context;
import java.util.concurrent.TimeUnit;
import ratemyapp.jagerfield.appratebotlib.Utils.CLib;
import ratemyapp.jagerfield.appratebotlib.Utils.PreferenceUtil;
import ratemyapp.jagerfield.appratebotlib.builders.RatingStatusEnum;
import ratemyapp.jagerfield.appratebotlib.builders.IBuilderFunctions;
import ratemyapp.jagerfield.appratebotlib.builders.BuilderHelper;
import ratemyapp.jagerfield.appratebotlib.builders.time_only.TimeOnlyBuilder;
import ratemyapp.jagerfield.appratebotlib.dialog.RatingDialog;

public class UsageAndTimeBuilder implements IBuilderFunctions
{
    private final int usageMaxCount;
    private final Context context;
    private final Activity activity;
    private String title;
    private String description;
    private int icon = -1000;
    private TimeUnit timeUnit;
    private int timePeriod = -1000;
    private BuilderHelper builderHelper;

    private static UsageAndTimeBuilder instance;

    public UsageAndTimeBuilder(Activity activity, int usageMaxCount)
    {
        this.usageMaxCount = usageMaxCount;
        this.activity = activity;
        this.context = activity.getApplicationContext();

        builderHelper = BuilderHelper.getNewInstance(context);
//        builderHelper.updateUsageCountAndDate();//Increase count if the app was last used a day ago
    }

    public static UsageAndTimeBuilder getNewInstance(Activity activity, int usageCount)
    {
        return new UsageAndTimeBuilder(activity, usageCount);
    }

    public String getTitle() {
        return title;
    }

    @Override
    public IBuilderFunctions setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public IBuilderFunctions setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getIcon() {
        return icon;
    }

    @Override
    public IBuilderFunctions setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    @Override
    public IBuilderFunctions setTimeUnitAndAmount(TimeUnit timeUnit, int timeAmount)
    {
        this.timePeriod = timeAmount;
        this.timeUnit = timeUnit;
        return this;
    }

    @Override
    public void build()
    {
        final RatingDialog[] obj = new RatingDialog[1];
        final UsageAndTimeBuilder builder = this;

        try
        {
            //Check usage count
            int usageCount = PreferenceUtil.getInt(context, CLib.IKEYS.KEY_USAGE_COUNT, -9);

            if (usageCount <= usageMaxCount)
            {
                throw new IllegalStateException("Usage not enough is null");
            }

            checkUp();
            RatingStatusEnum ratingStatus;
            ratingStatus = builderHelper.getRatingStatus();

            switch (ratingStatus)
            {
                case NOT_ASKED:
                    builderHelper.isItOkToAskForFirstTime(activity, timeUnit, timePeriod, new TimeOnlyBuilder.ICallback() {
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
                    builderHelper.isItTimeToAskAgain(activity, timeUnit, timePeriod, new TimeOnlyBuilder.ICallback() {
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

    private void checkUp() throws Exception
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
            throw new IllegalStateException("timeUnit is null");
        }

        if (icon <= 0)
        {
            throw new IllegalStateException("icon is incorrect");
        }

        if (timePeriod <= 0)
        {
            throw new IllegalStateException("icon is incorrect");
        }
    }

    public interface ICallback
    {
        void showRatingDialog();
    }
}
