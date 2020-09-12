package com.mambure.myapplication;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

/**
 * Hilt requires this to generate neccessary components
 */
@HiltAndroidApp
public class MyApp extends Application {
}
