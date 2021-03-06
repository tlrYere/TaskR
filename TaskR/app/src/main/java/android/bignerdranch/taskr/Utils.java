package android.bignerdranch.taskr;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.app.Activity;
import android.bignerdranch.taskr.R;

public class Utils {

    private static int sTheme;
    public final static int THEME_DEFAULT = 0;
    public final static int THEME_ECHO = 1;
    public final static int THEME_SNS = 2;
    public final static int THEME_QP = 3;
    public final static int THEME_WISHCRAFT = 4;
    public final static int THEME_LILAC = 5;
    public final static int THEME_MINT = 6;
    public final static int THEME_ICEBREAKER = 7;
    public final static int THEME_GO = 8;
    public final static int THEME_COFFEE = 9;

    // Set the theme of the Activity, and restart it by creating a new Activity of the same type.
    public static void changeToTheme(Activity activity, int theme)
    {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    // Set the theme of the activity, according to the configuration.
    public static void onActivityCreateSetTheme(Activity activity)
    {
        switch (sTheme)
        {
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.AppTheme);
                break;
            case THEME_ECHO:
                activity.setTheme(R.style.AppThemeEcho);
                break;
            case THEME_SNS:
                activity.setTheme(R.style.AppThemeSwordsNSorcery);
                break;
            case THEME_QP:
                activity.setTheme(R.style.AppThemeQP);
                break;
            case THEME_WISHCRAFT:
                activity.setTheme(R.style.AppThemeWishCraft);
                break;
            case THEME_LILAC:
                activity.setTheme(R.style.AppThemeLilac);
                break;
            case THEME_MINT:
                activity.setTheme(R.style.AppThemeMint);
                break;
            case THEME_ICEBREAKER:
                activity.setTheme(R.style.AppThemeIcebreaker);
                break;
            case THEME_GO:
                activity.setTheme(R.style.AppThemeGoodOpportunity);
                break;
            case THEME_COFFEE:
                activity.setTheme(R.style.AppThemeCoffee);
                break;
        }
    }
}

