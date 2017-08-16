package ratemyapp.jagerfield.ratemyapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import ratemyapp.jagerfield.appratebotlib.Func.Funcs;
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
    private DatePickerDialog.OnDateSetListener date;

    public UiRatingTestManager(Activity activity)
    {
        this.activity = activity;
        util = Funcs.getInstance();
        declareCalendarListener();
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

        try
        {
            String currentDateTimeArr[] = util.getFormatedCurrentDateString().trim().split(" ");
            currentDateTv.setText(currentDateTimeArr[0]);
            currentTimeTv.setText(currentDateTimeArr[1]);

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
                        new DatePickerDialog(activity, date, year, month, day).show();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });

            setCurrentDateTime();

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

    private void declareCalendarListener()
    {
        date = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                Calendar cal = Calendar.getInstance();
                cal.setTimeZone(TimeZone.getDefault());
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateClickedTextView(cal);
            }

        };
    }

    private void updateClickedTextView(Calendar cal)
    {
        if (clickedTextView== null){return;}

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        clickedTextView.setText(format.format(cal.getTime()));
    }

    private void setCurrentDateTime() throws Exception
    {

        String appInstallationtDateTimeArr[] = util.getAppInstallationDateString(activity.getApplicationContext()).trim().split(" ");
        appInstallationDateTv.setText(appInstallationtDateTimeArr[0]);
        appInstallationTimeTv.setText(appInstallationtDateTimeArr[1]);
    }

}

