package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class MyGdxGame extends ApplicationAdapter {

	private Texture dropImage;
	private Texture bucketImage;
	private Sound dropSound;
	private Music rainMusic;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Rectangle bucket;
	private Array<Rectangle> raindrops;
	private long lastDropTime;

	private double directionX = 0;
	private double directionY = 0;

	private int life = 5;
	private int score = 0;
	CharSequence lifeString = "5";
	CharSequence scoreString = "0";
	BitmapFont font;

	boolean pause = true;
	boolean end = false;

	@Override
	public void create () {
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));

		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		rainMusic.setLooping(true);
		rainMusic.play();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

		bucket = new Rectangle();
		bucket.x = 800/ 2 - 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;

		raindrops = new Array<Rectangle>();

		font = new BitmapFont();
		font.getData().setScale(2);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);

		lifeString = String.valueOf(life);
		scoreString = String.valueOf(score);

		if(pause) {
			batch.begin();
			font.draw(batch, lifeString, 30, 460);
			font.draw(batch, scoreString, 770, 460);
			font.draw(batch, "head your face on screen center", 200, 240);
			batch.draw(bucketImage, bucket.x, bucket.y);
			batch.end();
			if(Gdx.input.isTouched()) {
				restart();
			}
			return;
		}

		if(end){
			batch.begin();
			font.draw(batch, "final score: " + scoreString, 350, 240);
			batch.end();
			if(Gdx.input.isTouched()) {
				restart();
			}
			return;
		}

		batch.begin();
		font.draw(batch, lifeString, 30, 460);
		font.draw(batch, scoreString, 770, 460);
		batch.draw(bucketImage, bucket.x, bucket.y);
		for(Rectangle raindrop : raindrops) {
			batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		batch.end();

		if(Math.abs(directionX) > 15) {
			bucket.x += directionX / 5;
		}
		if(Math.abs(directionY) > 15) {
			bucket.y += directionY / 5;
		}

		if(bucket.x < 0)
			bucket.x = 0;
		if(bucket.x > 800 - 64)
			bucket.x = 800 - 64;
		if(bucket.y < 0)
			bucket.y = 0;
		if(bucket.y > 480 - 64)
			bucket.y = 480 - 64;

		if(TimeUtils.nanoTime() - lastDropTime > 1500000000)
			spawnRaindrop();

		Iterator<Rectangle> iter = raindrops.iterator();
		while(iter.hasNext()) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 150 * Gdx.graphics.getDeltaTime();
			if (raindrop.y + 64 < 0) {
				life --;
				iter.remove();
				if( life <= 0){
					life = 0;
					end = true;
				}
			}
			if(raindrop.overlaps(bucket)) {
				dropSound.play();
				score ++;
				iter.remove();
			}
		}
	}

	private void restart(){
		life = 5;
		score = 0;
		lifeString = "5";
		scoreString = "0";
		end = false;
		pause = false;

		Iterator<Rectangle> iter = raindrops.iterator();
		while(iter.hasNext()) {
			iter.next();
			iter.remove();
		}

		bucket.x = 800/ 2 - 64 / 2;
		bucket.y = 20;
	}

	public void setDirectionX(double directionX){
		this.directionX = directionX;
	}
	public void setDirectionY(double directionY) { this.directionY = directionY; }

	@Override
	public void dispose() {
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
	}

	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800 - 64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}
}
