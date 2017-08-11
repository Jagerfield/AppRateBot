package ratemyapp.jagerfield.appratebotlib.builders.builder;

import java.util.concurrent.TimeUnit;

public interface IBuilderFunctions
{
    IBuilderFunctions setTitle(String title);
    IBuilderFunctions setDescription(String description);
    IBuilderFunctions setIcon(int icon);
    IBuilderFunctions setUsageCountPeriodSeparation(long mSeconds);
    IBuilderFunctions setActivationTimeAndUsageCount(TimeUnit timeUnit, long timeAmount, int usageCount);
    void build();
}

