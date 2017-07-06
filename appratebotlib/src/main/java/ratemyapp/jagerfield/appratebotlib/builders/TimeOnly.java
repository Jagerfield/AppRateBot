package ratemyapp.jagerfield.appratebotlib.builders;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import java.util.concurrent.TimeUnit;
import ratemyapp.jagerfield.appratebotlib.ExecuteRatingsContract;
import ratemyapp.jagerfield.appratebotlib.RatingStatusEnum;
import static ratemyapp.jagerfield.appratebotlib.RatingStatusEnum.NOT_ASKED_YET;

public class TimeOnly implements ITimeOnly
{
    private static Activity activity;
    private static Context context;
    private String title;
    private String description;
    private int icon;
    private TimeUnit timeUnit;
    private int timePeriod;

    private static TimeOnly instance;

    public TimeOnly()
    { }

    public static TimeOnly getNewInstance(Activity activity)
    {
        TimeOnly.activity = activity;
        context = activity.getApplicationContext();
        return new TimeOnly();
    }

    public String getTitle() {
        return title;
    }

    @Override
    public ITimeOnly setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public ITimeOnly setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getIcon() {
        return icon;
    }

    @Override
    public ITimeOnly setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    @Override
    public ITimeOnly setTimeUnitAndAmount(TimeUnit timeUnit, int timeAmount)
    {
        this.timePeriod = timeAmount;
        this.timeUnit = timeUnit;
        return this;
    }

    @Override
    public void execute(ICallback client)
    {
        if (activity == null)
        {
            Log.e("TAG", "Activity is null");
            return;
        }

        try
        {
            ExecuteRatingsContract executor = ExecuteRatingsContract.getNewInstance(context);
            RatingStatusEnum ratingStatus = NOT_ASKED_YET;
            ratingStatus = executor.getRatingStatus();

            switch (ratingStatus)
            {
                case NOT_ASKED_YET:
                    executor.isItOkToAskForFirstTime(activity, timeUnit, timePeriod, client);
                    break;
                case DONT_ASK_AGAIN:
                    break;
                case REMIND_ME_LATER:
                    executor.isItTimeToAskAgain(activity, timeUnit, timePeriod, client);
                    break;
                default:
                    break;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface ICallback
    {
        void showRatingMessage();
    }

}












