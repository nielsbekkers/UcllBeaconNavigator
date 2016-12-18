package com.NielsBekkersSkynetBe.UcllbeaconsH7X;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.Random;

/**
 * Created by r0579260 on 25-11-2016.
 */

public class WidgetProvider extends AppWidgetProvider {
    private BeaconDevice bd;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.beaconinfo_widget_layout);
            remoteViews.setTextViewText(R.id.textViewTitle, bd.getKeyLocationTitle());
            remoteViews.setTextViewText(R.id.textViewDescription, bd.getLocationDescription());

            Intent intent = new Intent(context, WidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    public void setBeacon(BeaconDevice beacon) {
        bd = beacon;
    }
}
