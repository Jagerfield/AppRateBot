package ratemyapp.jagerfield.appratebotlib.dialog;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import ratemyapp.jagerfield.appratebotlib.R;
import ratemyapp.jagerfield.appratebotlib.builders.time_only.TimeOnlyBuilder;

public class RatingDialogModel
{
    private AppCompatDialog appCompatDialog;
    private TimeOnlyBuilder builder;

    private RatingDialogModel(AppCompatDialog appCompatDialog, TimeOnlyBuilder builder)
    {
        this.appCompatDialog = appCompatDialog;
        this.builder = builder;
        initialize();
    }

    public static RatingDialogModel getNewInstace(AppCompatDialog appCompatDialog, TimeOnlyBuilder builder)
    {
        return new RatingDialogModel(appCompatDialog, builder);
    }

    private void initialize()
    {
        TextView titleRatingDlg = (TextView) appCompatDialog.findViewById(R.id.titleRatingDlg);
        titleRatingDlg.setText(builder.getTitle());

        TextView descriptionRatingDlg = (TextView) appCompatDialog.findViewById(R.id.descriptionRatingDlg);
        descriptionRatingDlg.setText(builder.getDescription());

        ImageView iconRatingDlg = (ImageView) appCompatDialog.findViewById(R.id.iconRatingDlg);
        iconRatingDlg.setImageDrawable(ContextCompat.getDrawable(appCompatDialog.getContext(), builder.getIcon()));

        TextView okRatingBt = (TextView) appCompatDialog.findViewById(R.id.okRatingBt);
        okRatingBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        TextView noRatingBt = (TextView) appCompatDialog.findViewById(R.id.noRatingBt);
        noRatingBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        TextView laterRatingBt = (TextView) appCompatDialog.findViewById(R.id.laterRatingBt);
        laterRatingBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}



















