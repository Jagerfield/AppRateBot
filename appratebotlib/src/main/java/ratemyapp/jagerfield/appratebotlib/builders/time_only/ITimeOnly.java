package ratemyapp.jagerfield.appratebotlib.builders.time_only;

import java.util.concurrent.TimeUnit;

import ratemyapp.jagerfield.appratebotlib.dialog.RatingDialog;

public interface ITimeOnly
{
    ITimeOnly setTitle(String title);
    ITimeOnly setDescription(String description);
    ITimeOnly setIcon(int icon);
    ITimeOnly setTimeUnitAndAmount(TimeUnit timeUnit, int timeAmount);
    void build();
}

