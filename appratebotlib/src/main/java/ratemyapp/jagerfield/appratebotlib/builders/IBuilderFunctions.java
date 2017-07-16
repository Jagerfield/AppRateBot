package ratemyapp.jagerfield.appratebotlib.builders;

import java.util.concurrent.TimeUnit;

public interface IBuilderFunctions
{
    IBuilderFunctions setTitle(String title);
    IBuilderFunctions setDescription(String description);
    IBuilderFunctions setIcon(int icon);
    IBuilderFunctions setActivationTime(TimeUnit timeUnit, int timeAmount);
    IBuilderFunctions setActivationTimeAndUsageCount(TimeUnit timeUnit, int timeAmount, int usagewCount);
    void build();
}

