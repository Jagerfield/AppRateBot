package ratemyapp.jagerfield.appratebotlib.builders.builder;

import java.util.concurrent.TimeUnit;

public interface IBuilder
{
    IBuilder setTitle(String title);
    IBuilder setDescription(String description);
    IBuilder setIcon(int icon);
    IBuilder setUsageCountPeriodSeparation(long mSeconds);
    IBuilder setActivationTimeAndUsageCount(TimeUnit timeUnit, long timeAmount, int usageCount);
    void build();
}

