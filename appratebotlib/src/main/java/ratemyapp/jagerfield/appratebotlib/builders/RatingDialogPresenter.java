package ratemyapp.jagerfield.appratebotlib.builders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ratemyapp.jagerfield.appratebotlib.R;
import ratemyapp.jagerfield.appratebotlib.Utils.CLib;
import ratemyapp.jagerfield.appratebotlib.Utils.PreferenceUtil;

public class RatingDialogPresenter
{
    private final Context context;
    private AppCompatDialog appCompatDialog;
    private String title;
    private String description;
    private int icon;

    private RatingDialogPresenter(AppCompatDialog appCompatDialog, String title, String description, int icon)
    {
        this.appCompatDialog = appCompatDialog;
        this.context = appCompatDialog.getContext();
        this.title = title;
        this.description = description;
        this.icon = icon;
        initialize();
    }

    public static RatingDialogPresenter getNewInstace(AppCompatDialog appCompatDialog, String title, String description, int icon)
    {
        return new RatingDialogPresenter(appCompatDialog, title, description, icon);
    }

    private void initialize()
    {
        /**
         * Initialize UI elements
         */
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

                int state = RatingStatusEnum.fromEnumToInt(RatingStatusEnum.NEVER);
                PreferenceUtil.setInt(appCompatDialog.getContext(), CLib.IKEYS.KEY_RATINGS_STATE, state);
                appCompatDialog.dismiss();
            }
        });

        TextView laterRatingBt = (TextView) appCompatDialog.findViewById(R.id.laterRatingBt);
        laterRatingBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                int state = RatingStatusEnum.fromEnumToInt(RatingStatusEnum.LATER);
                PreferenceUtil.setInt(appCompatDialog.getContext(), CLib.IKEYS.KEY_RATINGS_STATE, state);

                long currentDate = System.currentTimeMillis();
                PreferenceUtil.setLong(appCompatDialog.getContext(), CLib.IKEYS.KEY_ASK_AGAIN_DATE, currentDate);

                appCompatDialog.dismiss();
            }
        });
    }
}



















