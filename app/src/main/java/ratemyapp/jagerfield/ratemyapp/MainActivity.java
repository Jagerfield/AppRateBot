package ratemyapp.jagerfield.ratemyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.concurrent.TimeUnit;
import ratemyapp.jagerfield.appratebotlib.dialog.RatingDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RatingDialog.timeOnlyBuilder(this)
                .setTitle("Thanks for using the app")
                .setDescription("If it has been useful to you\nwould you kindly rate it on GooglePlay")
                .setIcon(R.mipmap.ic_launcher)
                .setTimeUnitAndAmount(TimeUnit.SECONDS, 60)
                .build();
    }
}
