package com.geek.libbase.plugin;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IPlugin {
    void attach(@NotNull Activity Activity);

    void onCreate(@Nullable Bundle saveInstance);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onNewIntent(@Nullable Intent newIntent);

    void onRestart();

    void onDestroy();

    void finish();

    void onBackPressed();

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);

    void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults);

    void onConfigurationChanged(@NotNull Configuration newConfig);
}
