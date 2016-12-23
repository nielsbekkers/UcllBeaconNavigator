package com.NielsBekkersSkynetBe.UcllbeaconsH7X;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import java.util.Random;

/**
 * Created by r0579260 on 25-11-2016.
 */

public class WidgetProvider extends AppWidgetProvider {
    public static String UPDATE_ACTION = "ActionUpdateWidget";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Tell the AppWidgetManager to perform an update on the current app widget
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.beaconinfo_widget_layout);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Bundle extras = intent.getExtras();
        String title1 = extras.getString("title");//this value does not come through
        String desc = extras.getString("desc");//this value does not come through

        if (action != null && action.equals(UPDATE_ACTION)) {
            final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName name = new ComponentName(context, WidgetProvider.class);
            int[] appWidgetId = AppWidgetManager.getInstance(context).getAppWidgetIds(name);
            final int N = appWidgetId.length;
            if (N < 1)
            {
                return ;
            }
            else {
                int id = appWidgetId[N-1];
                updateWidget(context, appWidgetManager, id ,title1,desc);
            }
        }

        else {
            super.onReceive(context, intent);
        }
    }

    static void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, String title,String desc){
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.beaconinfo_widget_layout);
        views.setTextViewText(R.id.textViewTitle, title);
        views.setTextViewText(R.id.textViewDescription, desc);
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }
}
