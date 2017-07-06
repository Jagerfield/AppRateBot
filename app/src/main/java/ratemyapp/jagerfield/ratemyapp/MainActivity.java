package ratemyapp.jagerfield.ratemyapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import ratemyapp.jagerfield.appratebotlib.builders.Ratings;
import ratemyapp.jagerfield.appratebotlib.builders.time_only.TimeOnly;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Ratings.TimeOnlyBuilder(this)
                .setTitle("Title")
                .setDescription("Description")
                .setIcon(1)
                .setTimeUnitAndAmount(TimeUnit.SECONDS, 60)
                .execute(new TimeOnly.ICallback()
                {
                    @Override
                    public void showRatingMessage()
                    {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run()
                            {
                                Toast.makeText(MainActivity.this, "Rate My App", Toast.LENGTH_LONG).show();
                            }
                        }, 5000);
                    }
                });
    }
}
