package ratemyapp.jagerfield.appratebotlib.dialog;

import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.TextView;
import ratemyapp.jagerfield.appratebotlib.R;

public class RatingDialogModel
{
    private AppCompatDialog appCompatDialog;

    private RatingDialogModel(AppCompatDialog appCompatDialog)
    {
        this.appCompatDialog = appCompatDialog;
        initialize();
    }

    public static RatingDialogModel getNewInstace(AppCompatDialog appCompatDialog)
    {
        return new RatingDialogModel(appCompatDialog);
    }

    private void initialize()
    {
        TextView okRatingBt = appCompatDialog.findViewById(R.id.okRatingBt);
        okRatingBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        TextView noRatingBt = appCompatDialog.findViewById(R.id.noRatingBt);
        noRatingBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        TextView laterRatingBt = appCompatDialog.findViewById(R.id.laterRatingBt);
        laterRatingBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




    }


}



















