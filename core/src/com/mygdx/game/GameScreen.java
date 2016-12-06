package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {
    private Texture coinImage;
    private int coinWidth;
    private int coinHeight;
    private TextureRegion[] coinFrames;
    private Texture asteroidImage;
    private int asteroidWidth;
    private int asteroidHeight;
    private TextureRegion[] asteroidFrames;
    private TextureRegion currentFrame;
    private Texture playerShipImage;
    //private Sound dropSound;
    //private Music rainMusic;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Rectangle playerShip;
    private Array<Animation> coins;
    private Array<Animation> asteroids;
    private Array<Rectangle> coinsR;
    private Array<Rectangle> asteroidsR;
    private long lastCoinTime;
    private long lastAsteroidTime;

    private double directionX = 0;

    private final int START_LIFE = 5;
    private final int START_SCORE = 0;
    private int life = START_LIFE;
    private int score = START_SCORE;
    CharSequence lifeString = String.valueOf(START_LIFE);
    CharSequence scoreString = String.valueOf(START_SCORE);
    BitmapFont font;

    private boolean pause = true;
    private boolean end = false;
    private float stateTime;

    public GameScreen (final MyGdxGame game) {
        int index;

        /*

                // mała moneta

        coinImage = new Texture(Gdx.files.internal("coin.png"));
        coinHeight = coinImage.getHeight();
        coinWidth = coinImage.getWidth() / 8;
        TextureRegion[][] tmp = TextureRegion.split(coinImage, coinWidth, coinHeight);
        coinFrames = new TextureRegion[8];
        index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 8; j++) {
                coinFrames[index++] = tmp[i][j];
            }
        }

        */

                // duża moneta

        coinImage = new Texture(Gdx.files.internal("coin1-1.gif"));
        Texture[] coinImages = new Texture[14];
        for (int i = 1; i < 14; i++)
            coinImages[i] = new Texture(Gdx.files.internal("coin1-" + i + ".gif"));
        coinHeight = coinImage.getHeight();
        coinWidth = coinImage.getWidth();
        TextureRegion[][] tmp = TextureRegion.split(coinImage, coinWidth, coinHeight);
        coinFrames = new TextureRegion[13];

        for (int j = 1; j < 14; j++) {
            coinFrames[j-1] = TextureRegion.split(coinImages[j],coinWidth,coinHeight)[0][0];
        }

        /*

            // mała asteroida

        asteroidImage = new Texture(Gdx.files.internal("asteroid.png"));
        asteroidHeight = asteroidImage.getHeight() / 4;
        asteroidWidth = asteroidImage.getWidth() / 5;
        tmp = TextureRegion.split(asteroidImage, asteroidWidth, asteroidHeight);
        asteroidFrames = new TextureRegion[19];
        index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (i < 3 || j < 4)
                    asteroidFrames[index++] = tmp[i][j];
            }
        }

        */

            // duża asteroida

        asteroidImage = new Texture(Gdx.files.internal("asteroid1-1.gif"));
        Texture[] asteroidImages = new Texture[26];
        for (int i = 1; i < 26; i++)
            asteroidImages[i] = new Texture(Gdx.files.internal("asteroid1-" + i + ".gif"));
        asteroidHeight = asteroidImage.getHeight();
        asteroidWidth = asteroidImage.getWidth();
        tmp = TextureRegion.split(asteroidImage, asteroidWidth, asteroidHeight);
        asteroidFrames = new TextureRegion[25];

        for (int j = 1; j < 26; j++) {
            asteroidFrames[j-1] = TextureRegion.split(asteroidImages[j],asteroidWidth,asteroidHeight)[0][0];
        }



       // playerShipImage = new Texture(Gdx.files.internal("playerShip.png"));

        playerShipImage = new Texture(Gdx.files.internal("spaceShip.png"));

        //dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        //rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        //rainMusic.setLooping(true);
        //rainMusic.play();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        playerShip = new Rectangle();
        playerShip.x = 800 / 2 - playerShipImage.getWidth() / 2;
        playerShip.y = 10;
        playerShip.width = playerShipImage.getWidth();
        playerShip.height = playerShipImage.getHeight();

        coins = new Array<Animation>();
        asteroids = new Array<Animation>();
        coinsR = new Array<Rectangle>();
        asteroidsR = new Array<Rectangle>();

        font = new BitmapFont();
        font.getData().setScale(2);

        stateTime = 0f;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        lifeString = String.valueOf(life);
        scoreString = String.valueOf(score);

        if (pause) {
            batch.begin();
            font.draw(batch, lifeString, 30, 460);
            font.draw(batch, scoreString, 770, 460);
            font.draw(batch, "head your face on screen center", 200, 240);
            batch.draw(playerShipImage, playerShip.x, playerShip.y);
            batch.end();
            if (Gdx.input.isTouched()) {
                restart();
            }
            return;
        }

        if (end) {
            batch.begin();
            font.draw(batch, "final score: " + scoreString, 350, 240);
            batch.end();
            if (Gdx.input.isTouched()) {
                restart();
            }
            return;
        }

        stateTime += Gdx.graphics.getDeltaTime();
        batch.begin();
        font.draw(batch, lifeString, 30, 460);
        font.draw(batch, scoreString, 770, 460);
        batch.draw(playerShipImage, playerShip.x, playerShip.y);
        int index = 0;
        for (Animation coinAnimation : coins) {
            currentFrame = coinAnimation.getKeyFrame(stateTime, true);
            batch.draw(currentFrame, coinsR.get(index).x, coinsR.get(index).y);
            index++;
        }
        index = 0;
        for (Animation asteroidAnimation : asteroids) {
            currentFrame = asteroidAnimation.getKeyFrame(stateTime, true);
            batch.draw(currentFrame, asteroidsR.get(index).x, asteroidsR.get(index).y);
            index++;
        }
        batch.end();

        if (Math.abs(directionX) > 18) {
            playerShip.x += directionX / 8;
        }

        if (playerShip.x < 0)
            playerShip.x = 0;
        if (playerShip.x > 800 - playerShip.getWidth())
            playerShip.x = 800 - playerShip.getWidth();
        if (playerShip.y < 0)
            playerShip.y = 0;
        if (playerShip.y > 480 - playerShip.getHeight())
            playerShip.y = 480 - playerShip.getHeight();

        if (TimeUtils.nanoTime() - lastCoinTime > 1000000000)
            spawnCoin();

        if (TimeUtils.nanoTime() - lastAsteroidTime > 1500000000)
            spawnAsteroid();

        Iterator<Rectangle> iter = coinsR.iterator();
        Iterator<Animation> iterA = coins.iterator();
        while (iter.hasNext()) {
            Rectangle coin = iter.next();
            iterA.next();
            coin.y -= 50 * Gdx.graphics.getDeltaTime();
            if (coin.y + coinHeight < 0) {
                iter.remove();
                iterA.remove();
            }
            if (coin.overlaps(playerShip)) {
                //dropSound.play();
                score++;
                iter.remove();
                iterA.remove();
            }
        }

        iter = asteroidsR.iterator();
        iterA = asteroids.iterator();
        while (iter.hasNext()) {
            Rectangle asteroid = iter.next();
            iterA.next();
            asteroid.y -= 120 * Gdx.graphics.getDeltaTime();
            if (asteroid.y + asteroidHeight < 0) {
                iter.remove();
                iterA.remove();
            }
            if (asteroid.overlaps(playerShip)) {
                //dropSound.play();
                life--;
                iter.remove();
                iterA.remove();
                if (life <= 0) {
                    life = 0;
                    end = true;
                }
            }
        }
    }

    private void restart() {
        life = START_LIFE;
        score = START_SCORE;
        lifeString = String.valueOf(START_LIFE);
        scoreString = String.valueOf(START_SCORE);
        end = false;
        pause = false;

        Iterator<Rectangle> iter = coinsR.iterator();
        while (iter.hasNext()) {
            iter.next();
            iter.remove();
        }
        Iterator<Animation> iterA = coins.iterator();
        while (iterA.hasNext()) {
            iterA.next();
            iterA.remove();
        }
        iter = asteroidsR.iterator();
        while (iter.hasNext()) {
            iter.next();
            iter.remove();
        }
        iterA = asteroids.iterator();
        while (iterA.hasNext()) {
            iterA.next();
            iterA.remove();
        }


        playerShip.x = 800 / 2 - playerShip.getWidth() / 2;
        playerShip.y = 10;
    }

    public void setDirectionX(double directionX) {
        this.directionX = directionX;
    }

    @Override
    public void show() {

    }

    @Override
    public void dispose() {
        coinImage.dispose();
        asteroidImage.dispose();
        playerShipImage.dispose();
        //dropSound.dispose();
        //rainMusic.dispose();
        batch.dispose();
    }

    private void spawnCoin() {
        Rectangle coin = new Rectangle();
        coin.x = MathUtils.random(0, 800 - coinWidth);
        coin.y = 480;
        coin.width = coinWidth;
        coin.height = coinHeight;
        coinsR.add(coin);
        coins.add(new Animation(0.025f, coinFrames));
        lastCoinTime = TimeUtils.nanoTime();
    }

    private void spawnAsteroid() {
        Rectangle asteroid = new Rectangle();
        asteroid.x = MathUtils.random(0, 800 - asteroidWidth);
        asteroid.y = 480;
        asteroid.width = asteroidWidth;
        asteroid.height = asteroidHeight;
        asteroidsR.add(asteroid);
        asteroids.add(new Animation(0.025f, asteroidFrames));
        lastAsteroidTime = TimeUtils.nanoTime();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }
}
