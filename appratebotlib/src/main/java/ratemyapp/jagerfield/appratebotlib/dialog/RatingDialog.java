package ratemyapp.jagerfield.appratebotlib.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.Window;
import ratemyapp.jagerfield.appratebotlib.R;
import ratemyapp.jagerfield.appratebotlib.builders.time_only.TimeOnlyBuilder;
import ratemyapp.jagerfield.appratebotlib.builders.usage_and_time.UsageAndTimeBuilder;


public class RatingDialog extends AppCompatDialog
{
    private final Context context;
    private TimeOnlyBuilder timeBuilder;
    private UsageAndTimeBuilder usageAndTimeBuilder;

    private RatingDialog(Context context, TimeOnlyBuilder timeBuilder)
    {
        super(context);
        this.context = context;
        this.timeBuilder = timeBuilder;
    }

    private RatingDialog(Context context, UsageAndTimeBuilder usageAndTimeBuilder)
    {
        super(context);
        this.context = context;
        this.usageAndTimeBuilder = usageAndTimeBuilder;
    }

    public static RatingDialog getNewInstance(Context context, TimeOnlyBuilder builder)
    {
        return new RatingDialog(context, builder);
    }

    public static RatingDialog getNewInstance(Context context, UsageAndTimeBuilder builder)
    {
        return new RatingDialog(context, builder);
    }

    public static TimeOnlyBuilder timeOnlyBuilder(Activity activity)
    {
        return TimeOnlyBuilder.getNewInstance(activity);
    }

    public static UsageAndTimeBuilder usageAndTimeBuilder(Activity activity, int usageMaxCount)
    {
        return UsageAndTimeBuilder.getNewInstance(activity, usageMaxCount);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(false);
        setContentView(R.layout.ratings_dialog);

        if (timeBuilder != null)
        {
            RatingDialogLogicModel.getNewInstace(this, timeBuilder.getTitle(), timeBuilder.getDescription(), timeBuilder.getIcon());
        }
        else if (usageAndTimeBuilder != null)
        {
            RatingDialogLogicModel.getNewInstace(this, usageAndTimeBuilder.getTitle(), usageAndTimeBuilder.getDescription(), usageAndTimeBuilder.getIcon());
        }

    }
}

//ContextCompat.getColor(context, builder.titleTextColor)
