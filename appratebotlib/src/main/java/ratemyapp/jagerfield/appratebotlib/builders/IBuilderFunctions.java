package ratemyapp.jagerfield.appratebotlib.builders;

import java.util.concurrent.TimeUnit;

public interface IBuilderFunctions
{
    IBuilderFunctions setTitle(String title);
    IBuilderFunctions setDescription(String description);
    IBuilderFunctions setIcon(int icon);
    IBuilderFunctions setTimeUnitAndAmount(TimeUnit timeUnit, int timeAmount);
    void build();
}

