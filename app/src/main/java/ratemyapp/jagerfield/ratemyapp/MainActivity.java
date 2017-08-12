package ratemyapp.jagerfield.ratemyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;

import ratemyapp.jagerfield.appratebotlib.Func.Funcs;
import ratemyapp.jagerfield.appratebotlib.Utils.C;
import ratemyapp.jagerfield.appratebotlib.builders.builder.RatingStatusEnum;
import ratemyapp.jagerfield.appratebotlib.dialog.RatingDialog;

public class MainActivity extends AppCompatActivity
{
    private TextView currentTimeTv;
    private TextView appInstallationDateTv;
    private TextView usageCountTv;
    private TextView lastUsageDateTv;
    private TextView ratingStatusTv;
    private TextView askMeLaterDateTv;
    private TextView activationTimeTv;
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
        currentTimeTv = (TextView) findViewById(R.id.currentTimeTv);
        appInstallationDateTv = (TextView) findViewById(R.id.appInstallationDateTv);
        usageCountTv = (TextView) findViewById(R.id.usageCountTv);
        lastUsageDateTv = (TextView) findViewById(R.id.lastUsageDateTv);
        ratingStatusTv = (TextView) findViewById(R.id.ratingStatusTv);
        askMeLaterDateTv = (TextView) findViewById(R.id.askMeLaterDateTv);
        activationTimeTv = (TextView) findViewById(R.id.activationTimeTv);
        activationUsageTv = (TextView) findViewById(R.id.activationUsageTv);
        usageCountPeriodSeparationTv = (TextView) findViewById(R.id.usageCountPeriodSeparationTv);

        try
        {
            currentTimeTv.setText(util.getFormatedCurrentDateString());

            appInstallationDateTv.setText(util.getAppInstallationDateString(getApplicationContext()));

            String usageCount = String.valueOf(util.getUsageCount(getApplicationContext()));
            usageCountTv.setText(usageCount);

            String lastusageDate = util.getFormatedLastUsageDateString(getApplicationContext());
            lastUsageDateTv.setText(lastusageDate);

            RatingStatusEnum ratingStatus = util.getRatingStatus(getApplicationContext());
            ratingStatusTv.setText(ratingStatus.toString());

            String askMeLaterDate = util.getAskMeLaterDateString(getApplicationContext());
            askMeLaterDateTv.setText(askMeLaterDate);

            String activationTime = util.claculateNextActivationDate(getApplicationContext());
            activationTimeTv.setText(activationTime);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}













