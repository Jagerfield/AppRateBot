package ratemyapp.jagerfield.appratebotlib.builders;


import android.app.Activity;

public class Ratings
{

    public static TimeOnly TimeOnlyBuilder(Activity activity)
    {
        return TimeOnly.getNewInstance(activity);
    }

}
