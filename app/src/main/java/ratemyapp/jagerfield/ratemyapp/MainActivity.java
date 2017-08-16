package ratemyapp.jagerfield.ratemyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;
import ratemyapp.jagerfield.appratebotlib.Func.Funcs;
import ratemyapp.jagerfield.appratebotlib.builders.builder.RatingStatusEnum;
import ratemyapp.jagerfield.appratebotlib.dialog.RatingDialog;

public class MainActivity extends AppCompatActivity {
    private Funcs util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        util = Funcs.getInstance();

        RatingStatusEnum type = util.getRatingStatus(getApplicationContext());

        if (type != RatingStatusEnum.NEVER) {
            RatingDialog.startRateBuilder(this)
                    .setTitle("Thanks for using the app")
                    .setDescription("If it has been useful to you\nwould you kindly rate it on GooglePlay")
                    .setIcon(R.mipmap.ic_launcher)
                    .setUsageCountPeriodSeparation(0l)
                    .setActivationTimeAndUsageCount(TimeUnit.MILLISECONDS, 48 * 3600 * 1000, 8)
                    .build();
        }

        UiRatingTestManager uiRatingTestManager = new UiRatingTestManager(this);
    }
}











