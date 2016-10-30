package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game {

	SpriteBatch batch;
	BitmapFont font;
	MenuScreen menuScreen;
	GameScreen gameScreen;

	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		this.setScreen(menuScreen);
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	public void setDirectionX(double directionX) {
		gameScreen.setDirectionX(directionX);
	}

	public boolean isPause() {
		return gameScreen.isPause();
	}

	public void setPause(boolean pause) {
		gameScreen.setPause(pause);
	}

}
