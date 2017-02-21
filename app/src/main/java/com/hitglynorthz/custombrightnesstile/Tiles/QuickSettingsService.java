package com.hitglynorthz.custombrightnesstile.Tiles;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.provider.Settings;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;

import com.hitglynorthz.custombrightnesstile.R;

import java.util.Locale;

/**
 * Created by rogel on 20/02/2017.
 */

@SuppressLint("Override")
@TargetApi(Build.VERSION_CODES.N)
public class QuickSettingsService extends TileService {

    private static final String SERVICE_STATUS_FLAG = "serviceStatus";
    private static final String PREFERENCES_KEY = "com.google.android_quick_settings";

    SharedPreferences sharedPref;

    /**
     * Called when the tile is added to the Quick Settings.
     * @return TileService constant indicating tile state
     */

    @Override
    public void onTileAdded() {
        Log.d("QS", "Tile added");
    }

    /**
     * Called when this tile begins listening for events.
     */
    @Override
    public void onStartListening() {
        Log.d("QS", "Start listening");
    }

    /**
     * Called when the user taps the tile.
     */
    @Override
    public void onClick() {
        Log.d("QS", "Tile tapped");

        updateTile();
    }

    /**
     * Called when this tile moves out of the listening state.
     */
    @Override
    public void onStopListening() {
        Log.d("QS", "Stop Listening");
    }

    /**
     * Called when the user removes this tile from Quick Settings.
     */
    @Override
    public void onTileRemoved() {
        Log.d("QS", "Tile removed");
    }

    //
    private void updateTile() {

        Tile tile = this.getQsTile();
        boolean isActive = getServiceStatus();

        Icon newIcon;
        String newLabel;
        int newState;

        sharedPref = getSharedPreferences("prefBrightness", Context.MODE_PRIVATE);

        //
        if (isActive) {

            newLabel = String.format(Locale.US, "%s %s", getString(R.string.tile_label), getString(R.string.tile_service_active));

            newIcon = Icon.createWithResource(getApplicationContext(), R.drawable.ic_brightness_medium_white_24dp);
            newState = Tile.STATE_ACTIVE;
            Settings.System.putInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS, sharedPref.getInt("brightnessON", 0));

        } else {
            newLabel = String.format(Locale.US, "%s %s", getString(R.string.tile_label), getString(R.string.tile_service_inactive));
            newIcon = Icon.createWithResource(getApplicationContext(), R.drawable.ic_brightness_medium_white_24dp);
            newState = Tile.STATE_INACTIVE;
            Settings.System.putInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS, sharedPref.getInt("brightnessOFF", 0));

        }
        tile.setLabel(newLabel);
        tile.setIcon(newIcon);
        tile.setState(newState);
        tile.updateTile();
    }

    private boolean getServiceStatus() {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(PREFERENCES_KEY, MODE_PRIVATE);
        boolean isActive = prefs.getBoolean(SERVICE_STATUS_FLAG, false);
        isActive = !isActive;
        prefs.edit().putBoolean(SERVICE_STATUS_FLAG, isActive).apply();

        return isActive;
    }
}