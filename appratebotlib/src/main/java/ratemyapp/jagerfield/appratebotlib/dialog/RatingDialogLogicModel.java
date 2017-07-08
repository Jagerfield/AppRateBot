package ratemyapp.jagerfield.appratebotlib.dialog;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import ratemyapp.jagerfield.appratebotlib.R;
import ratemyapp.jagerfield.appratebotlib.builders.time_only.TimeOnlyBuilder;

public class RatingDialogLogicModel
{
    private AppCompatDialog appCompatDialog;
    private final String title;
    private final String description;
    private final int icon;


    private RatingDialogLogicModel(AppCompatDialog appCompatDialog, String title, String description, int icon)
    {
        this.appCompatDialog = appCompatDialog;
        this.title = title;
        this.description = description;
        this.icon = icon;
        initialize();
    }

    public static RatingDialogLogicModel getNewInstace(AppCompatDialog appCompatDialog, String title, String description, int icon)
    {
        return new RatingDialogLogicModel(appCompatDialog, title, description, icon);
    }

    private void initialize()
    {
        TextView titleRatingDlg = (TextView) appCompatDialog.findViewById(R.id.titleRatingDlg);
        titleRatingDlg.setText(title);

        TextView descriptionRatingDlg = (TextView) appCompatDialog.findViewById(R.id.descriptionRatingDlg);
        descriptionRatingDlg.setText(description);

        ImageView iconRatingDlg = (ImageView) appCompatDialog.findViewById(R.id.iconRatingDlg);
        iconRatingDlg.setImageDrawable(ContextCompat.getDrawable(appCompatDialog.getContext(), icon));

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



















