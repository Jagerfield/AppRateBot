package ratemyapp.jagerfield.ratemyapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import ratemyapp.jagerfield.appratebotlib.Func.Funcs;
import ratemyapp.jagerfield.appratebotlib.Utils.C;
import ratemyapp.jagerfield.appratebotlib.Utils.PreferenceUtil;
import ratemyapp.jagerfield.appratebotlib.builders.builder.EnumRatingStatus;

public class UiRatingTestManager
{
    private TextView currentDateTv;
    private TextView currentTimeTv;
    private TextView appInstallationDateTv;
    private TextView appInstallationTimeTv;
    private TextView askMeLaterDateTv;
    private TextView askMeLaterTimeTv;
    private TextView nextActivationDateTv;
    private TextView nextActivationTimeTv;
    private TextView lastUsageDateTv;
    private TextView lastUsageTimeTv;
    private TextView usageCountTv;
    private TextView ratingStatusTv;
    private TextView activationUsageTv;
    private TextView usageCountPeriodSeparationTv;

    private CheckBox editCurrentTimeTitleCb;
    private Funcs util;
    private Activity activity;
    private TextView clickedTextView;
    private DatePickerDialog.OnDateSetListener datePicker;
    private TimePickerDialog.OnTimeSetListener timePicker;
    private Context context;
    private EnumCALValueType clickedTextViewType;

    public UiRatingTestManager(Activity activity)
    {
        this.activity = activity;
        context = activity.getApplicationContext();
        util = Funcs.getInstance();
        initializeUIComponents();
    }

    private void initializeUIComponents()
    {
        if (activity==null)
        {
            return;
        }

        currentDateTv = (TextView) activity.findViewById(R.id.currentDateTv);
        currentTimeTv = (TextView) activity.findViewById(R.id.currentTimeTv);
        appInstallationDateTv = (TextView) activity.findViewById(R.id.appInstallationDateTv);
        appInstallationTimeTv = (TextView) activity.findViewById(R.id.appInstallationTimeTv);
        askMeLaterDateTv = (TextView) activity.findViewById(R.id.askMeLaterDateTv);
        askMeLaterTimeTv = (TextView) activity.findViewById(R.id.askMeLaterTimeTv);
        nextActivationDateTv = (TextView) activity.findViewById(R.id.activationDateTv);
        nextActivationTimeTv = (TextView) activity.findViewById(R.id.activationTimeTv);
        lastUsageDateTv = (TextView) activity.findViewById(R.id.lastUsageDateTv);
        lastUsageTimeTv = (TextView) activity.findViewById(R.id.lastUsageTimeTv);
        usageCountTv = (TextView) activity.findViewById(R.id.usageCountTv);
        ratingStatusTv = (TextView) activity.findViewById(R.id.ratingStatusTv);
        activationUsageTv = (TextView) activity.findViewById(R.id.activationUsageTv);
        usageCountPeriodSeparationTv = (TextView) activity.findViewById(R.id.usageCountPeriodSeparationTv);
        editCurrentTimeTitleCb = (CheckBox) activity.findViewById(R.id.editCurrentTimeTitleCb);

        try
        {
            setListeners();

            initialCurrentTDateCheckbox();

            String appInstallationtDateTimeArr[] = util.getAppInstallationDateString(activity.getApplicationContext()).trim().split(" ");
            appInstallationDateTv.setText(appInstallationtDateTimeArr[0]);
            appInstallationTimeTv.setText(appInstallationtDateTimeArr[1]);

            String lastusageDateTimeArr[] = util.getFormatedLastUsageDateString(activity.getApplicationContext()).trim().split(" ");
            lastUsageDateTv.setText(lastusageDateTimeArr[0]);
            lastUsageTimeTv.setText(lastusageDateTimeArr[1]);

            String usageCount = String.valueOf(util.getUsageCount(activity.getApplicationContext()));
            usageCountTv.setText(usageCount);

            String askMeLaterDateTimeArr[] = util.getAskMeLaterDateString(activity.getApplicationContext()).trim().split(" ");
            askMeLaterDateTv.setText(askMeLaterDateTimeArr[0]);
            askMeLaterTimeTv.setText(askMeLaterDateTimeArr[1]);

            String activationDateTimeArr[] = util.claculateNextActivationDate(activity.getApplicationContext()).trim().split(" ");
            nextActivationDateTv.setText(activationDateTimeArr[0]);
            nextActivationTimeTv.setText(activationDateTimeArr[1]);

            EnumRatingStatus ratingStatus = util.getRatingStatus(activity.getApplicationContext());
            ratingStatusTv.setText(ratingStatus.toString());

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void initialCurrentTDateCheckbox()
    {
        settingCurrentTimeAndDateViews();
    }

    private void settingCurrentTimeAndDateViews() {
        /**
         * Set checkbox state and current time date colors
         */
        boolean b = PreferenceUtil.getBoolean(context, C.IKEYS.KEY_ALLOW_EDITING_CURRENT_DATE_TIME, false);

        if (b)
        {
            currentDateTv.setTextColor(ContextCompat.getColor(context, R.color.menublue));
            currentTimeTv.setTextColor(ContextCompat.getColor(context, R.color.green_dark));
            editCurrentTimeTitleCb.setChecked(true);
        }
        else
        {
            currentDateTv.setTextColor(ContextCompat.getColor(context, R.color.greytext));
            currentTimeTv.setTextColor(ContextCompat.getColor(context, R.color.greytext));
            editCurrentTimeTitleCb.setChecked(false);
        }

        /**
         * Fill in the values of current time date. They can be either the sys date and time or the saved value in the preferences
         * depending on the edit mode
         */

        String currentDateTime[];

        try
        {
            String value = Funcs.getInstance().getAppCurrentDateTimeString(context);
            if (value != null && !value.isEmpty())
            {
                currentDateTime = value.trim().split(" ");

                if (currentDateTime[0]!=null && !currentDateTime[0].isEmpty() && currentDateTime[1]!=null && !currentDateTime[1].isEmpty())
                {
                    //Use current time and datePicker
                    currentDateTv.setText(currentDateTime[0]);
                    currentTimeTv.setText(currentDateTime[1]);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    /**
     * Listeners   *********************************************************************************
     *
     *
     */

    private void setListeners()
    {
        editCurrentTimeTitleCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if (b)
                {
                    currentDateTv.setTextColor(ContextCompat.getColor(context, R.color.menublue));
                    currentTimeTv.setTextColor(ContextCompat.getColor(context, R.color.green_dark));
                    PreferenceUtil.setBoolean(context, C.IKEYS.KEY_ALLOW_EDITING_CURRENT_DATE_TIME, true);
                    PreferenceUtil.setString(context, C.IKEYS.KEY_EDITED_CURRENT_DATE_TIME, Funcs.getInstance().getSysDateTimeString());
                    PreferenceUtil.setLong(context, C.IKEYS.KEY_LAST_USAGE_DATE, getCurrentCal().getTimeInMillis());
                }

                else
                {
                    currentDateTv.setTextColor(ContextCompat.getColor(context, R.color.greytext));
                    currentTimeTv.setTextColor(ContextCompat.getColor(context, R.color.greytext));
                    resetKeys();
                }

                settingCurrentTimeAndDateViews();
            }
        });

        /**
         * Date and Time Picker listeners
         */

        datePicker = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                try
                {
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeZone(TimeZone.getDefault());

                    //Set date from the Cal
                    cal.set(Calendar.YEAR, year);
                    cal.set(Calendar.MONTH, monthOfYear);
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    //set time from the currentTimeTv
                    String time = currentTimeTv.getText().toString();
                    String timeArr[] = time.trim().split(":");

                    if (timeArr[0]!=null && timeArr[1]!=null && timeArr[2]!=null &&
                            !timeArr[0].isEmpty() && !timeArr[1].isEmpty() && !timeArr[2].isEmpty())
                    {
                        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArr[0]));
                        cal.set(Calendar.MINUTE, Integer.parseInt(timeArr[1]));
                        cal.set(Calendar.SECOND, Integer.parseInt(timeArr[2]));
                    }

                    updateTextViewDateValue(cal);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };


        timePicker = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute)
            {
                try
                {
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeZone(TimeZone.getDefault());

                    //set date from the currentDateTv
                    String date = currentDateTv.getText().toString();
                    String dateArr[] = date.trim().split("-");

                    if (dateArr[0]!=null && dateArr[1]!=null && dateArr[2]!=null &&
                            !dateArr[0].isEmpty() && !dateArr[1].isEmpty() && !dateArr[2].isEmpty())
                    {
                        cal.set(Calendar.YEAR, Integer.parseInt(dateArr[0]));
                        cal.set(Calendar.MONTH, Integer.parseInt(dateArr[1]));
                        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArr[2]));
                    }

                    //set time from the Cal
                    cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    cal.set(Calendar.MINUTE, minute);
                    cal.set(Calendar.SECOND, 0);

                    updateTextViewDateValue(cal);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };


        /******************************************************************************************/

        currentDateTv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    clickedTextView = currentDateTv;
                    clickedTextViewType = EnumCALValueType.DATE;
                    String sections[] = currentDateTv.getText().toString().trim().split("-");
                    int year = Integer.parseInt(sections[0]);
                    int month = Integer.parseInt(sections[1]);
                    int day = Integer.parseInt(sections[2]);
                    new DatePickerDialog(activity, datePicker, year, month, day).show();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        currentTimeTv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    clickedTextView = currentTimeTv;
                    clickedTextViewType = EnumCALValueType.TIME;
                    String sections[] = currentTimeTv.getText().toString().trim().split(":");
                    int hour = Integer.parseInt(sections[0]);
                    int min = Integer.parseInt(sections[1]);
                    int sec = Integer.parseInt(sections[2]);
                    new TimePickerDialog(activity, timePicker, hour, min, true).show();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    private void resetKeys()
    {
        PreferenceUtil.setBoolean(context, C.IKEYS.KEY_ALLOW_EDITING_CURRENT_DATE_TIME, false);
        PreferenceUtil.setString(context, C.IKEYS.KEY_EDITED_CURRENT_DATE_TIME, "");
        PreferenceUtil.setLong(context, C.IKEYS.KEY_LAST_USAGE_DATE, getCurrentCal().getTimeInMillis());
        PreferenceUtil.setInt(context, C.IKEYS.KEY_USAGE_COUNT, 1);
        PreferenceUtil.setLong(context, C.IKEYS.KEY_ASK_AGAIN_DATE, 0l);
        PreferenceUtil.setInt(context, C.IKEYS.KEY_RATINGS_STATE, EnumRatingStatus.fromEnumToInt(EnumRatingStatus.NOT_ASKED));
    }

    private void updateTextViewDateValue(Calendar cal)
    {
        try
        {
            if (clickedTextView== null || clickedTextViewType== null)
            {
                throw new IllegalArgumentException("clickedTextView or clickedKeyType has a wrong value");
            }

            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateTimeValue = dateTimeFormat.format(cal.getTime());

            if (dateTimeValue != null && !dateTimeValue.isEmpty())
            {
                String dateTime[]= dateTimeValue.trim().split(" ");

                switch (clickedTextViewType)
                {
                    case DATE:
                        if (dateTime[0]!=null && !dateTime[0].isEmpty())
                        {
                            clickedTextView.setText(dateTime[0]);

                            if (editCurrentTimeTitleCb.isChecked())
                            {
                                PreferenceUtil.setString(activity.getApplicationContext(), C.IKEYS.KEY_EDITED_CURRENT_DATE_TIME, dateTimeValue);
                            }
                        }

                        break;

                    case TIME:

                        if (dateTime[1]!=null && !dateTime[1].isEmpty())
                        {
                            clickedTextView.setText(dateTime[1]);

                            if (editCurrentTimeTitleCb.isChecked())
                            {
                                PreferenceUtil.setString(activity.getApplicationContext(), C.IKEYS.KEY_EDITED_CURRENT_DATE_TIME, dateTimeValue);
                            }
                        }

                        break;
                }
            }

            //Save value
            clickedTextView = null;
            clickedTextViewType = null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Calendar getCurrentCal()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getDefault());
        return cal;
    }

    private enum EnumCALValueType
    {
        DATE, TIME
    }

    /***
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */

//    Update the value of last usage as you get the current time
//    Add a check button to enable editing the current time. Adjust the getCurrentTime to work accordingly. So the getUiTestTime should be built into the
//        getCurrentTime.

}



//    private void updateTextViewDateValue(Calendar cal)
//    {
//        try
//        {
//            if (clickedTextView== null || clickedKeyType == null || clickedKeyType.isEmpty())
//            {
//                throw new IllegalArgumentException("clickedTextView or clickedKeyType has a wrong value");
//            }
//
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("");
//
//            if (clickedKeyType.contains("_DATE"))
//            {
//                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            }
//            else if (clickedKeyType.contains("_TIME"))
//            {
//                simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
//            }
//
//            clickedTextView.setText(simpleDateFormat.format(cal.getTime()));
//
//            //Save value
//            PreferenceUtil.setLong(activity.getApplicationContext(), clickedKeyType, cal.getTimeInMillis());
//            clickedTextView = null;
//            clickedKeyType = null;
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }







//    private void getAppCurrentDateTimeString() throws Exception
//    {
//        if (context==null){return;}
//
//        try
//        {
//
//            String uiTestDateTime = util.getFormatedUserEditedCurrentDateTimeString(context).trim();
//
//            String currentDateTime = util.getSysDateTimeString().trim();
//            String currentDateTimeArr[] = currentDateTime.split(" ");
//
//
//            if (uiTestDateTime==null || uiTestDateTime.isEmpty())
//            {
//                //Use current time and datePicker
//                currentDateTv.setText(currentDateTimeArr[0]);
//                currentTimeTv.setText(currentDateTimeArr[1]);
//                PreferenceUtil.setLong(activity.getApplicationContext(), C.IKEYS.KEY_EDITED_CURRENT_DATE_TIME, Long.parseLong(currentDateTime));
//            }
//            else
//            {
//                String uiTestDateTimeArr[] = uiTestDateTime.split(" ");
//
//                boolean a = Long.parseLong(currentDateTimeArr[0])> Long.parseLong(uiTestDateTimeArr[0]);//Compare dates
//                boolean b = Long.parseLong(currentDateTimeArr[0]) == Long.parseLong(uiTestDateTimeArr[0]);//Compare dates
//                boolean c = Long.parseLong(currentDateTimeArr[1])> Long.parseLong(uiTestDateTimeArr[1]);//Compare times
//
//                if (a || (b && c))
//                {
//                    currentDateTv.setText(currentDateTimeArr[0]);
//                    currentTimeTv.setText(currentDateTimeArr[1]);
//                    PreferenceUtil.setLong(activity.getApplicationContext(), C.IKEYS.KEY_EDITED_CURRENT_DATE_TIME, Long.parseLong(currentDateTime));
//                }
//                else
//                {
//                    currentDateTv.setText(uiTestDateTimeArr[0]);
//                    currentTimeTv.setText(uiTestDateTimeArr[1]);
//                }
//            }
//
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }

