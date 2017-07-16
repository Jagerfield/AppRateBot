package ratemyapp.jagerfield.appratebotlib.builders.time_only;

import android.app.Activity;
import android.content.Context;

import java.util.concurrent.TimeUnit;

import ratemyapp.jagerfield.appratebotlib.builders.ShowRatingsDialogHelper;
import ratemyapp.jagerfield.appratebotlib.builders.IBuilderFunctions;
import ratemyapp.jagerfield.appratebotlib.builders.RatingStatusEnum;
import ratemyapp.jagerfield.appratebotlib.dialog.RatingDialog;

public class TimeOnlyBuilder implements IBuilderFunctions
{
    private static Activity activity;
    private static Context context;
    private String title;
    private String description;
    private int icon = -1000;
    private TimeUnit timeUnit;
    private int timePeriod = -1000;

    private static TimeOnlyBuilder instance;

    public TimeOnlyBuilder()
    { }

    public static TimeOnlyBuilder getNewInstance(Activity activity)
    {
        TimeOnlyBuilder.activity = activity;
        context = activity.getApplicationContext();
        return new TimeOnlyBuilder();
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
        final TimeOnlyBuilder builder = this;

        try
        {
            checkParameters();

            final ShowRatingsDialogHelper RatingsHelper = ShowRatingsDialogHelper.getNewInstance(context);
            RatingStatusEnum ratingStatus;
            ratingStatus = RatingsHelper.getRatingStatus();

            if (ratingStatus==null) {  throw new IllegalStateException("ratingStatus is null");  }

            switch (ratingStatus)
            {
                case NOT_ASKED:
                    RatingsHelper.isItOkToAskForFirstTime(activity, timeUnit, timePeriod, new ICallback()
                    {
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
                    RatingsHelper.isItTimeToAskAgain(activity, timeUnit, timePeriod, new ICallback() {
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

    public void checkParameters() throws Exception
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












