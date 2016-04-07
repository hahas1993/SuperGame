package com.mygdx.game;

import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		initialize(new MyGdxGame(), config);

		if (!OpenCVLoader.initDebug()) {
			Log.e(this.getClass().getSimpleName(), "  OpenCVLoader.initDebug(), not working.");
		} else {
			Log.d(this.getClass().getSimpleName(), "  OpenCVLoader.initDebug(), working.");
		}
	}
}
