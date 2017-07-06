package ratemyapp.jagerfield.appratebotlib.builders;

import java.util.concurrent.TimeUnit;

public interface ITimeOnly
{
    ITimeOnly setTitle(String title);
    ITimeOnly setDescription(String description);
    ITimeOnly setIcon(int icon);
    ITimeOnly setTimeUnitAndAmount(TimeUnit timeUnit, int timeAmount);
    void execute(TimeOnly.ICallback client);
}

