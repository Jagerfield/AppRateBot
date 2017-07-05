package ratemyapp.jagerfield.appratebotlib;

public enum RatingStatusEnum
{
    DONT_ASK_AGAIN, REMIND_ME_LATER, NOT_ASKED_YET;

    static RatingStatusEnum fromIntToEnum(int value)
    {
        switch (value)
        {
            case 0:
                return DONT_ASK_AGAIN;
            case 1:
                return REMIND_ME_LATER;
            case 2:
                return NOT_ASKED_YET;
            default:
                return NOT_ASKED_YET;
        }
    }

    static int fromEnumToInt(RatingStatusEnum value)
    {
        switch (value)
        {
            case DONT_ASK_AGAIN:
                return 0;
            case REMIND_ME_LATER:
                return 1;
            case NOT_ASKED_YET:
                return 2;
            default:
                return 2;
        }
    }
}
