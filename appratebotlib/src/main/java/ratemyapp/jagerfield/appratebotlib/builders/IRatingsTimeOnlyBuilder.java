package ratemyapp.jagerfield.appratebotlib.builders;

import java.util.concurrent.TimeUnit;

public interface IRatingsTimeOnlyBuilder
{
    IRatingsTimeOnlyBuilder setTitle(String title);
    IRatingsTimeOnlyBuilder setDescription(String description);
    IRatingsTimeOnlyBuilder setIcon(int icon);
    IRatingsTimeOnlyBuilder setTimeUnitAndAmount(TimeUnit timeUnit, int timeAmount);
    void execute(RatingsTimeOnlyBuilder.ICallback client);
}

