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

        RatingDialog.TimeOnlyBuilder(this)
                .setTitle("Title")
                .setDescription("Description")
                .setIcon(R.drawable.ic_launcher_background)
                .setTimeUnitAndAmount(TimeUnit.SECONDS, 60)
                .build();
    }
}
