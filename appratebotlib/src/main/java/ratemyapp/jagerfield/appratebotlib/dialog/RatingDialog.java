package ratemyapp.jagerfield.appratebotlib.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.Window;
import ratemyapp.jagerfield.appratebotlib.R;
import ratemyapp.jagerfield.appratebotlib.builders.time_only.TimeOnlyBuilder;


public class RatingDialog extends AppCompatDialog
{
    private final Context context;
    private final TimeOnlyBuilder executor;

    public static TimeOnlyBuilder timeOnlyBuilder(Activity activity)
    {
        return TimeOnlyBuilder.getNewInstance(activity);
    }

    private RatingDialog(Context context, TimeOnlyBuilder executor)
    {
        super(context);
        this.context = context;
        this.executor = executor;
    }

    public static RatingDialog getNewInstance(Context context, TimeOnlyBuilder executor)
    {
        return new RatingDialog(context, executor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(false);
        setContentView(R.layout.ratings_dialog);

        RatingDialogModel.getNewInstace(this, executor);
    }
}

//ContextCompat.getColor(context, builder.titleTextColor)
