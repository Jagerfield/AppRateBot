package ratemyapp.jagerfield.ratemyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;

import ratemyapp.jagerfield.appratebotlib.Func.Funcs;
import ratemyapp.jagerfield.appratebotlib.builders.builder.RatingStatusEnum;
import ratemyapp.jagerfield.appratebotlib.dialog.RatingDialog;

public class MainActivity extends AppCompatActivity
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        util = Funcs.getInstance();

        RatingStatusEnum type = util.getRatingStatus(getApplicationContext());

        if (type != RatingStatusEnum.NEVER)
        {
            RatingDialog.startRateBuilder(this)
                    .setTitle("Thanks for using the app")
                    .setDescription("If it has been useful to you\nwould you kindly rate it on GooglePlay")
                    .setIcon(R.mipmap.ic_launcher)
                    .setUsageCountPeriodSeparation(0l)
                    .setActivationTimeAndUsageCount(TimeUnit.MILLISECONDS, 48 * 3600 * 1000, 8)
                    .build();
        }

        initializeUIComponents();
    }

    private void initializeUIComponents()
    {
        currentDateTv = (TextView) findViewById(R.id.currentDateTv);
        currentTimeTv = (TextView) findViewById(R.id.currentTimeTv);
        appInstallationDateTv = (TextView) findViewById(R.id.appInstallationDateTv);
        appInstallationTimeTv = (TextView) findViewById(R.id.appInstallationTimeTv);
        askMeLaterDateTv = (TextView) findViewById(R.id.askMeLaterDateTv);
        askMeLaterTimeTv = (TextView) findViewById(R.id.askMeLaterTimeTv);
        nextActivationDateTv = (TextView) findViewById(R.id.activationDateTv);
        nextActivationTimeTv = (TextView) findViewById(R.id.activationTimeTv);
        lastUsageDateTv = (TextView) findViewById(R.id.lastUsageDateTv);
        lastUsageTimeTv = (TextView) findViewById(R.id.lastUsageTimeTv);
        usageCountTv = (TextView) findViewById(R.id.usageCountTv);

        ratingStatusTv = (TextView) findViewById(R.id.ratingStatusTv);
        activationUsageTv = (TextView) findViewById(R.id.activationUsageTv);
        usageCountPeriodSeparationTv = (TextView) findViewById(R.id.usageCountPeriodSeparationTv);

        try
        {
            String currentDateTimeArr[] = util.getFormatedCurrentDateString().trim().split(" ");
            currentDateTv.setText(currentDateTimeArr[0]);
            currentTimeTv.setText(currentDateTimeArr[1]);

            String appInstallationtDateTimeArr[] = util.getAppInstallationDateString(getApplicationContext()).trim().split(" ");
            appInstallationDateTv.setText(appInstallationtDateTimeArr[0]);
            appInstallationTimeTv.setText(appInstallationtDateTimeArr[1]);

            String lastusageDateTimeArr[] = util.getFormatedLastUsageDateString(getApplicationContext()).trim().split(" ");
            lastUsageDateTv.setText(lastusageDateTimeArr[0]);
            lastUsageTimeTv.setText(lastusageDateTimeArr[1]);

            String usageCount = String.valueOf(util.getUsageCount(getApplicationContext()));
            usageCountTv.setText(usageCount);

            String askMeLaterDateTimeArr[] = util.getAskMeLaterDateString(getApplicationContext()).trim().split(" ");
            askMeLaterDateTv.setText(askMeLaterDateTimeArr[0]);
            askMeLaterTimeTv.setText(askMeLaterDateTimeArr[1]);

            String activationDateTimeArr[] = util.claculateNextActivationDate(getApplicationContext()).trim().split(" ");
            nextActivationDateTv.setText(activationDateTimeArr[0]);
            nextActivationTimeTv.setText(activationDateTimeArr[1]);

            RatingStatusEnum ratingStatus = util.getRatingStatus(getApplicationContext());
            ratingStatusTv.setText(ratingStatus.toString());


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}













