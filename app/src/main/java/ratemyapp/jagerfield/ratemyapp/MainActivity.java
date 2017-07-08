package ratemyapp.jagerfield.ratemyapp;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import ratemyapp.jagerfield.appratebotlib.Utils.C;
import ratemyapp.jagerfield.appratebotlib.Utils.PreferenceUtil;
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



//    public long getAppInstallationDate()
//    {
//        PackageManager packageManager =  getPackageManager();
//        long installTimeInMilliseconds;
//
//        PackageInfo packageInfo = null;
//        try {
//            packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        installTimeInMilliseconds = packageInfo.firstInstallTime;
//
//        return installTimeInMilliseconds;
//    }
}
