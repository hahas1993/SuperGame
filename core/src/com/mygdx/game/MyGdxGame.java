package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game {

	SpriteBatch batch;
	BitmapFont font;
	GameScreen gameScreen;

	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		gameScreen = new GameScreen(this);
		this.setScreen(gameScreen);
	}

	public void render() {
		super.render();
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
