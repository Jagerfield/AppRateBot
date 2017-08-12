package ratemyapp.jagerfield.appratebotlib.dialog;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ratemyapp.jagerfield.appratebotlib.R;
import ratemyapp.jagerfield.appratebotlib.Utils.C;
import ratemyapp.jagerfield.appratebotlib.Utils.PreferenceUtil;
import ratemyapp.jagerfield.appratebotlib.builders.builder.RatingStatusEnum;


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
        if (icon > 0) {
            iconRatingDlg.setImageDrawable(ContextCompat.getDrawable(appCompatDialog.getContext(), icon));
        }

        TextView okRatingBt = (TextView) appCompatDialog.findViewById(R.id.okRatingBt);
        okRatingBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchMarket();
                PreferenceUtil.setInt(appCompatDialog.getContext(), C.IKEYS.KEY_RATINGS_STATE, RatingStatusEnum.fromEnumToInt(RatingStatusEnum.NEVER));
                appCompatDialog.dismiss();
            }
        });

        TextView noRatingBt = (TextView) appCompatDialog.findViewById(R.id.noRatingBt);
        noRatingBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PreferenceUtil.setInt(appCompatDialog.getContext(), C.IKEYS.KEY_RATINGS_STATE, RatingStatusEnum.fromEnumToInt(RatingStatusEnum.NEVER));
                appCompatDialog.dismiss();
            }
        });

        TextView laterRatingBt = (TextView) appCompatDialog.findViewById(R.id.laterRatingBt);
        laterRatingBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                PreferenceUtil.setInt(appCompatDialog.getContext(), C.IKEYS.KEY_RATINGS_STATE, RatingStatusEnum.fromEnumToInt(RatingStatusEnum.LATER));
                PreferenceUtil.setInt(appCompatDialog.getContext(), C.IKEYS.KEY_USAGE_COUNT, 0);
                PreferenceUtil.setLong(appCompatDialog.getContext(), C.IKEYS.KEY_ASK_AGAIN_DATE, UsageManager.getCurrentCal().getTimeInMillis());
                appCompatDialog.dismiss();
            }
        });
    }

    private void launchMarket() {
        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=jagerfield.ContentResolverVsCursorLoader");
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        myAppLinkToMarket.addCategory(Intent.CATEGORY_BROWSABLE);

        try
        {
            context.startActivity(myAppLinkToMarket);
        }
        catch (ActivityNotFoundException e)
        {
            Toast.makeText(context, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }

}



















