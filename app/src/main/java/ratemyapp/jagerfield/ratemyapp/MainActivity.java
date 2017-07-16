package ratemyapp.jagerfield.ratemyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import ratemyapp.jagerfield.appratebotlib.dialog.UsageMonitor;
import ratemyapp.jagerfield.appratebotlib.dialog.RatingDialog;

public class MainActivity extends AppCompatActivity
{
    private TextView usageCount;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usageCount = (TextView)findViewById(R.id.usageCount);

        RatingDialog.usageAndTimeBuilder(this)
                .setTitle("Thanks for using the app")
                .setDescription("If it has been useful to you\nwould you kindly rate it on GooglePlay")
                .setIcon(R.mipmap.ic_launcher)
                .setActivationTimeAndUsageCount(TimeUnit.SECONDS, 10, 3)
                .build();

        int i = UsageMonitor.getUsageCount(this);

        usageCount.setText(String.valueOf(i));

    }



}
