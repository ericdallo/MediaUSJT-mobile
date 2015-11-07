package com.mediausjt.Util;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.mediausjt.Application.MediaActivity;
import com.mediausjt.Fragment.AverageFragment;
import com.mediausjt.Fragment.NewGradeFragment;
import com.mediausjt.R;

public class MediaConfig {

    private static boolean isLogic = true;
    private static Typeface allerFont;
    private static Typeface onrampFont;
    private static SharedPreferences prefs;
    private static MediaActivity mediaActivity;

    public MediaConfig(MediaActivity  mediaActivity) {
        MediaConfig.mediaActivity = mediaActivity;
        MediaConfig.allerFont = Typeface.createFromAsset(mediaActivity.getAssets(), "fonts/aller.ttf");
        MediaConfig.onrampFont = Typeface.createFromAsset(mediaActivity.getAssets(), "fonts/onramp.ttf");
        MediaConfig.prefs = PreferenceManager.getDefaultSharedPreferences(mediaActivity);
    }

    public static void addFirstFontTo(TextView view) {
        view.setTypeface(allerFont);
    }

    public static void addSecondFontTo(TextView view) {
        view.setTypeface(onrampFont);
    }

    public static float getPreference(String key, float defaultValue) {
        return prefs.getFloat(key, defaultValue);
    }
    public static String getPreference(String key,String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

    public static int getPreference(String key, int defaultValue) {
        return prefs.getInt(key, defaultValue);
    }

    public static void resetWeight() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat("peso1",  0.4f);
        editor.putFloat("peso2", 0.6f);
        editor.putInt("media", 6);
        editor.apply();
    }

    public static void savePreference(String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void savePreference(String key, Float value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(key, value);
        editor.apply();
    }
    public static void savePreference(String key, int value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void savePreference(String key, boolean value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void removePreference(String idToUpdate) {
        prefs.edit().remove(idToUpdate).apply();
    }

    public static boolean notContainsPreference(String key) {
        return !prefs.contains(key);
    }

    public static MediaActivity getActivity() {
        return MediaConfig.mediaActivity;
    }

    public static void goToNewGrade() {
        Fragment fragment = new NewGradeFragment();
        MediaConfig.getActivity().getSupportFragmentManager()
                    .beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
    public static void goToAverage() {
        Fragment fragment = new AverageFragment();
        MediaConfig.getActivity().getSupportFragmentManager()
                .beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
}
