package ratemyapp.jagerfield.appratebotlib.builders.builder;

import android.app.Activity;
import android.content.Context;
import java.util.concurrent.TimeUnit;
import ratemyapp.jagerfield.appratebotlib.Utils.CLib;
import ratemyapp.jagerfield.appratebotlib.Utils.PreferenceUtil;
import ratemyapp.jagerfield.appratebotlib.dialog.RatingDialog;

public class RatingsBuilder implements IBuilderFunctions
{
    private int usageMaxCount;
    private final Context context;
    private final Activity activity;
    private String title;
    private String description;
    private int icon = 0;
    private TimeUnit timeUnit;
    private int timePeriod = 0;
    private RatingsBuilderHelper ratingsBuilderHelper;
    private static RatingsBuilder instance;
    private BuilderTypeEnum builderType;

    public RatingsBuilder(Activity activity)
    {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        ratingsBuilderHelper = RatingsBuilderHelper.getNewInstance(context);
    }

    public static RatingsBuilder getNewInstance(Activity activity)
    {
        return new RatingsBuilder(activity);
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
    public IBuilderFunctions setActivationTime(TimeUnit timeUnit, int timeAmount)
    {
        this.builderType = BuilderTypeEnum.TIME_ONLY;
        this.timePeriod = timeAmount;
        this.timeUnit = timeUnit;
        return this;
    }

    @Override
    public IBuilderFunctions setActivationTimeAndUsageCount(TimeUnit timeUnit, int timeAmount, int usageMaxCount)
    {
        this.builderType = BuilderTypeEnum.TIME_AND_USAGE_COUNT;
        this.usageMaxCount = usageMaxCount;
        this.timePeriod = timeAmount;
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
            RatingStatusEnum ratingStatus = ratingsBuilderHelper.getRatingStatus();

            if (builderType == BuilderTypeEnum.TIME_AND_USAGE_COUNT)
            {
                int usageCount = PreferenceUtil.getInt(context, CLib.IKEYS.KEY_USAGE_COUNT, 0);
                if (usageCount <= usageMaxCount) { throw new IllegalStateException("Usage count is not enough"); }
            }

            switch (ratingStatus)
            {
                case NOT_ASKED:
                    ratingsBuilderHelper.isItOkToAskForFirstTime(activity, timeUnit, timePeriod, new RatingsBuilder.ICallback() {
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
                    ratingsBuilderHelper.isItTimeToAskAgain(activity, timeUnit, timePeriod, new RatingsBuilder.ICallback() {
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
