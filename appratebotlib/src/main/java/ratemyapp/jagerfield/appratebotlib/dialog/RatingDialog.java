package ratemyapp.jagerfield.appratebotlib.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.Window;
import ratemyapp.jagerfield.appratebotlib.R;
import ratemyapp.jagerfield.appratebotlib.builders.RatingDialogPresenter;
import ratemyapp.jagerfield.appratebotlib.builders.UsageMonitor;
import ratemyapp.jagerfield.appratebotlib.builders.usage_and_time.RatingsBuilder;

public class RatingDialog extends AppCompatDialog
{
    private final Context context;
//    private TimeOnlyBuilder timeBuilder;
    private RatingsBuilder ratingsBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(false);
        setContentView(R.layout.ratings_dialog);

//        if (timeBuilder != null)
//        {
//            RatingDialogPresenter.getNewInstace(this, timeBuilder.getTitle(), timeBuilder.getDescription(), timeBuilder.getIcon());
//        }
        if (ratingsBuilder != null)
        {
            RatingDialogPresenter.getNewInstace(this, ratingsBuilder.getTitle(), ratingsBuilder.getDescription(), ratingsBuilder.getIcon());
        }
    }

//    private RatingDialog(Context context, TimeOnlyBuilder timeBuilder)
//    {
//        super(context);
//        this.context = context;
//        this.timeBuilder = timeBuilder;
//    }

    private RatingDialog(Context context, RatingsBuilder ratingsBuilder)
    {
        super(context);
        this.context = context;
        this.ratingsBuilder = ratingsBuilder;
    }

//    public static RatingDialog getNewInstance(Context context, TimeOnlyBuilder builder)
//    {
//        return new RatingDialog(context, builder);
//    }

    public static RatingDialog getNewInstance(Context context, RatingsBuilder builder)
    {
        return new RatingDialog(context, builder);
    }

//    public static TimeOnlyBuilder timeOnlyBuilder(Activity activity)
//    {
//        usageCounter(activity);
//        return TimeOnlyBuilder.getNewInstance(activity);
//    }

    public static RatingsBuilder usageAndTimeBuilder(Activity activity)
    {
        usageCounter(activity);
        return RatingsBuilder.getNewInstance(activity);
    }

    private static void usageCounter(Context context)
    {
//        UsageMonitor.resetUsageAndDate(context);
        /**
         * Check if the usage count should be incremented
         */
        UsageMonitor.newInstance(context).updateUsageInformation();
    }


}

//ContextCompat.getColor(context, builder.titleTextColor)
