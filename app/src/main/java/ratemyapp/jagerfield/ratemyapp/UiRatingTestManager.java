package ratemyapp.jagerfield.ratemyapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import ratemyapp.jagerfield.appratebotlib.Func.Funcs;
import ratemyapp.jagerfield.appratebotlib.Utils.C;
import ratemyapp.jagerfield.appratebotlib.Utils.PreferenceUtil;
import ratemyapp.jagerfield.appratebotlib.builders.builder.RatingStatusEnum;

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
    private Funcs util;
    private Activity activity;

    private TextView clickedTextView;
    private String clickedKeyType;

    private DatePickerDialog.OnDateSetListener dateListener;
    private TimePickerDialog.OnTimeSetListener timeListener;

    private Context context;

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

        onCalendarChanged();

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

        try
        {
            setListeners();

            getCurrentDateTime();

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

            RatingStatusEnum ratingStatus = util.getRatingStatus(activity.getApplicationContext());
            ratingStatusTv.setText(ratingStatus.toString());

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void onCalendarChanged()
    {
        dateListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                Calendar cal = Calendar.getInstance();
                cal.setTimeZone(TimeZone.getDefault());
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                try
                {
                    updateClickedTextViewValue(cal);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        };
    }

    private void getCurrentDateTime() throws Exception
    {
        if (context==null){return;}

        try
        {

            String uiTestDateTime = util.getFormatedUiTestCurrentDateTimeString(context).trim();

            String currentDateTime = util.getFormatedCurrentDateString().trim();
            String currentDateTimeArr[] = currentDateTime.split(" ");


            if (uiTestDateTime==null || uiTestDateTime.isEmpty())
            {
                //Use current time and dateListener
                currentDateTv.setText(currentDateTimeArr[0]);
                currentTimeTv.setText(currentDateTimeArr[1]);
                PreferenceUtil.setLong(activity.getApplicationContext(), C.IKEYS.KEY_UI_TEST_CURRENT_DATE_TIME, Long.parseLong(currentDateTime));
            }
            else
            {
                String uiTestDateTimeArr[] = uiTestDateTime.split(" ");

                boolean a = Long.parseLong(currentDateTimeArr[0])> Long.parseLong(uiTestDateTimeArr[0]);//Compare dates
                boolean b = Long.parseLong(currentDateTimeArr[0]) == Long.parseLong(uiTestDateTimeArr[0]);//Compare dates
                boolean c = Long.parseLong(currentDateTimeArr[1])> Long.parseLong(uiTestDateTimeArr[1]);//Compare times

                if (a || (b && c))
                {
                    currentDateTv.setText(currentDateTimeArr[0]);
                    currentTimeTv.setText(currentDateTimeArr[1]);
                    PreferenceUtil.setLong(activity.getApplicationContext(), C.IKEYS.KEY_UI_TEST_CURRENT_DATE_TIME, Long.parseLong(currentDateTime));
                }
                else
                {
                    currentDateTv.setText(uiTestDateTimeArr[0]);
                    currentTimeTv.setText(uiTestDateTimeArr[1]);
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void setListeners()
    {
        currentDateTv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    clickedTextView = currentDateTv;
                    String sections[] = currentDateTv.getText().toString().trim().split("-");
                    int year = Integer.parseInt(sections[0]);
                    int month = Integer.parseInt(sections[1]);
                    int day = Integer.parseInt(sections[2]);
                    new DatePickerDialog(activity, dateListener, year, month, day).show();
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
                    String sections[] = currentTimeTv.getText().toString().trim().split("-");
                    int hour = Integer.parseInt(sections[0]);
                    int min = Integer.parseInt(sections[1]);
                    int sec = Integer.parseInt(sections[2]);
                    new TimePickerDialog(activity, timeListener, hour, min, true).show();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    private void updateClickedTextViewValue(Calendar cal) throws Exception
    {
        if (clickedTextView== null || clickedKeyType == null || clickedKeyType.isEmpty())
        {
            throw new IllegalArgumentException("clickedTextView or clickedKeyType has a wrong value");
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("");

        if (clickedKeyType.contains("_DATE"))
        {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        else if (clickedKeyType.contains("_TIME"))
        {
            simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        }

        clickedTextView.setText(simpleDateFormat.format(cal.getTime()));

        //Save value
        PreferenceUtil.setLong(activity.getApplicationContext(), clickedKeyType, cal.getTimeInMillis());
        clickedTextView = null;
        clickedKeyType = null;
    }


    Add a check button to enable editing the current time. Adjust the getCurrentTime to work accordingly. So the getUiTestTime should be built into the
        getCurrentTime.

}

