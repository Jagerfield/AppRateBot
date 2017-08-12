package ratemyapp.jagerfield.appratebotlib.builders.builder;

/**
 * Created by Sense on 2017-08-12.
 */

public enum BuilderTypeEnum
{
    TIME_ONLY, TIME_AND_USAGE_COUNT;

    public static BuilderTypeEnum fromIntToEnum(int value)
    {
        switch (value)
        {
            case 0:
                return TIME_ONLY;
            case 1:
                return TIME_AND_USAGE_COUNT;
            default:
                return TIME_ONLY;
        }
    }

    public static int fromEnumToInt(BuilderTypeEnum value)
    {
        switch (value)
        {
            case TIME_ONLY:
                return 0;
            case TIME_AND_USAGE_COUNT:
                return 1;
            default:
                return 0;
        }
    }
}
