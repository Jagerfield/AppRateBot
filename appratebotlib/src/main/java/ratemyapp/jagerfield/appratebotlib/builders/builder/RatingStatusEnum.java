package ratemyapp.jagerfield.appratebotlib.builders.builder;

public enum RatingStatusEnum
{
    NEVER, LATER, NOT_ASKED;

    public static RatingStatusEnum fromIntToEnum(int value)
    {
        switch (value)
        {
            case 0:
                return NEVER;
            case 1:
                return LATER;
            case 2:
                return NOT_ASKED;
            default:
                return NEVER;
        }
    }

    public static int fromEnumToInt(RatingStatusEnum value)
    {
        switch (value)
        {
            case NEVER:
                return 0;
            case LATER:
                return 1;
            case NOT_ASKED:
                return 2;
            default:
                return 0;
        }
    }
}
