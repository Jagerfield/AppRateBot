package ratemyapp.jagerfield.appratebotlib.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;

import ratemyapp.jagerfield.appratebotlib.R;

public class RatingDialog extends AppCompatDialog
{
    public RatingDialog(Context context)
    {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ratings_dialog);

        RatingDialogModel.getNewInstace(this);


    }





}
