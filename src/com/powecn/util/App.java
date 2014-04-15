package com.powecn.util;

import android.app.Application;

public class App extends Application {

	public int screenWidth = 0;
	public int screenHeight = 0;

	public App() {

	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

}
