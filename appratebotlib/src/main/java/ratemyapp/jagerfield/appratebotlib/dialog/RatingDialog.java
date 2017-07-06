package ratemyapp.jagerfield.appratebotlib.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.Window;
import ratemyapp.jagerfield.appratebotlib.R;
import ratemyapp.jagerfield.appratebotlib.builders.time_only.TimeOnly;

public class RatingDialog extends AppCompatDialog
{
    public static TimeOnly TimeOnlyBuilder(Activity activity)
    {
        return TimeOnly.getNewInstance(activity);

    }

    private RatingDialog(Context context)
    {
        super(context);
    }

    public static RatingDialog getNewInstance(Context context)
    {
        return new RatingDialog(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(false);
        setContentView(R.layout.ratings_dialog);
        RatingDialogModel.getNewInstace(this);
    }
//
//    @Override
//    public void show()
//    {
//        super.show();
//    }
//
//    @Override
//    public void dismiss()
//    {
//        super.dismiss();
//    }
}

//ContextCompat.getColor(context, builder.titleTextColor)
