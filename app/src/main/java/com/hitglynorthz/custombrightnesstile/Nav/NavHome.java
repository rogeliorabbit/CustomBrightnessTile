package com.hitglynorthz.custombrightnesstile.Nav;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hitglynorthz.custombrightnesstile.R;

/**
 * Created by rogel on 20/02/2017.
 */

public class NavHome extends Fragment {
    View view;
    Context context;

    RelativeLayout permissions_false;
    LinearLayout permissions_true, ll_customBrightnessOFF;
    Button btn_permissions, btn_saveTile, btn_resetTile;

    SeekBar seekbarDefaultBrightness, seekbarCustomBrightnessOFF, seekbarCustomBrightnessON;
    TextView  tv_defaultBrightnessLevel, tv_customBrightnessLevelOFF, tv_customBrightnessLevelON;
    SwitchCompat switch_custom_brightness_OFF;

    int BrightnessDefault;
    int brightnessON, brightnessOFF;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    int prefBrightnessOFF, prefBrightnessON;

    public static NavHome newInstance() {
        NavHome fragment = new NavHome();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_nav_home, container, false);
        context = getContext();

        initViews();
        settingPermission();

        sharedPref = getActivity().getSharedPreferences("prefBrightness", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        defaultData();
        customDataOFF();
        customDataON();
        saveTile();
        resetTile();

        return view;
    }

    private void initViews() {
        permissions_false = (RelativeLayout) view.findViewById(R.id.permissions_false);
        permissions_true = (LinearLayout) view.findViewById(R.id.permissions_true);
        btn_permissions = (Button) view.findViewById(R.id.btn_permissions);
        //
        seekbarDefaultBrightness = (SeekBar) view.findViewById(R.id.seekbarDefaultBrightness);
        seekbarCustomBrightnessOFF = (SeekBar) view.findViewById(R.id.seekbarCustomBrightnessOFF);
        seekbarCustomBrightnessON = (SeekBar) view.findViewById(R.id.seekbarCustomBrightnessON);
        tv_defaultBrightnessLevel = (TextView) view.findViewById(R.id.tv_defaultBrightnessLevel);
        tv_customBrightnessLevelOFF = (TextView) view.findViewById(R.id.tv_customBrightnessLevelOFF);
        tv_customBrightnessLevelON = (TextView) view.findViewById(R.id.tv_customBrightnessLevelON);
        switch_custom_brightness_OFF = (SwitchCompat) view.findViewById(R.id.switch_custom_brightness_OFF);
        ll_customBrightnessOFF = (LinearLayout) view.findViewById(R.id.ll_customBrightnessOFF);
        btn_saveTile = (Button) view.findViewById(R.id.btn_saveTile);
        btn_resetTile = (Button) view.findViewById(R.id.btn_resetTile);
    }

    // Default
    private void defaultData() {
        BrightnessDefault = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 0);
        brightnessOFF = BrightnessDefault;
        brightnessON = BrightnessDefault;
        seekbarDefaultBrightness.setProgress(BrightnessDefault);
        tv_defaultBrightnessLevel.setText(BrightnessDefault + "%");
        seekbarDefaultBrightness.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    // Custom OFF
    private void customDataOFF() {
        switch_custom_brightness_OFF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    ll_customBrightnessOFF.setVisibility(View.VISIBLE);
                } else {
                    ll_customBrightnessOFF.setVisibility(View.GONE);
                    brightnessOFF = BrightnessDefault;
                }
            }
        });
        seekbarCustomBrightnessOFF.setProgress(sharedPref.getInt("brightnessOFF", BrightnessDefault));
        tv_customBrightnessLevelOFF.setText(sharedPref.getInt("brightnessOFF", BrightnessDefault) + "%");
        seekbarCustomBrightnessOFF.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int valueOFF = sharedPref.getInt("brightnessOFF", BrightnessDefault);
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                valueOFF = progress;
                tv_customBrightnessLevelOFF.setText(progress + "%");
                //Settings.System.putInt(context.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tv_customBrightnessLevelOFF.setText(valueOFF + "%");
                //Settings.System.putInt(context.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS, progressChangedValue);
                brightnessOFF = valueOFF;
            }
        });
    }

    // Custom ON
    private void customDataON() {
        seekbarCustomBrightnessON.setProgress(sharedPref.getInt("brightnessON", BrightnessDefault));
        tv_customBrightnessLevelON.setText(sharedPref.getInt("brightnessON", BrightnessDefault) + "%");
        seekbarCustomBrightnessON.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int valueON = sharedPref.getInt("brightnessON", BrightnessDefault);
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                valueON = progress;
                tv_customBrightnessLevelON.setText(progress + "%");
                //Settings.System.putInt(context.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tv_customBrightnessLevelON.setText(valueON + "%");
                //Settings.System.putInt(context.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS, progressChangedValue);
                brightnessON = valueON;
            }
        });
    }

    // Permissions
    public void settingPermission() {
        if (!Settings.System.canWrite(getActivity().getApplicationContext())) {
            permissions_false.setVisibility(View.VISIBLE);
            permissions_true.setVisibility(View.GONE);
            btn_permissions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + getActivity().getPackageName()));
                    startActivityForResult(intent, 200);
                }
            });
        } else {
            permissions_false.setVisibility(View.GONE);
            permissions_true.setVisibility(View.VISIBLE);
        }
    }

    // Save
    private void saveTile() {
        btn_saveTile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("brightnessOFF", brightnessOFF).commit();
                editor.putInt("brightnessON", brightnessON).commit();

                prefBrightnessOFF = sharedPref.getInt("brightnessOFF", BrightnessDefault);
                prefBrightnessON = sharedPref.getInt("brightnessON", BrightnessDefault);
                Snackbar.make(view, prefBrightnessOFF + " " + prefBrightnessON, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    // Reset
    private void resetTile() {
        btn_resetTile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekbarCustomBrightnessOFF.setProgress(BrightnessDefault);
                tv_customBrightnessLevelOFF.setText(BrightnessDefault + "%");
                seekbarCustomBrightnessON.setProgress(BrightnessDefault);
                tv_customBrightnessLevelON.setText(BrightnessDefault + "%");
                //editor.putInt("brightnessON", BrightnessDefault).commit();
                //editor.putInt("brightnessON", BrightnessDefault).commit();

                Snackbar.make(view, "Reset: " + sharedPref.getInt("brightnessOFF", BrightnessDefault) + " " + sharedPref.getInt("brightnessON", BrightnessDefault), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        settingPermission();
    }
}
