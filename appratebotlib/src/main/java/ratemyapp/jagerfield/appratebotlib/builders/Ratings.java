package ratemyapp.jagerfield.appratebotlib.builders;


import android.app.Activity;

import ratemyapp.jagerfield.appratebotlib.builders.time_only.TimeOnly;

public class Ratings
{

    public static TimeOnly TimeOnlyBuilder(Activity activity)
    {
        return TimeOnly.getNewInstance(activity);
    }

}
