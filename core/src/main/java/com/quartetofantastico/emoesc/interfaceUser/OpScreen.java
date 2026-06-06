package com.quartetofantastico.emoesc.interfaceUser;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.quartetofantastico.emoesc.Home;
import com.quartetofantastico.emoesc.logicAndMechanic.Constants;

public class OpScreen implements Screen {
    public static final String TAG = OpScreen.class.getName();
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    Texture mainImage;
    OrthographicCamera camera;
    FitViewport viewport;
    float spinnerAngle;
    float spinnerRadius;
    float elapsedTime;
    Constants CONST;
    boolean imageLoaded;
    BitmapFont font;
    Home game;

    @Override
    public void show() {
        CONST = new Constants();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        // Tente isto só para testar se ele encontra algum ficheiro na raiz da pasta ui
        mainImage = new Texture(Gdx.files.internal("ui/intro.png"));
        spinnerAngle = 0;
        spinnerRadius = 50;
        elapsedTime = 0;
        imageLoaded = true;
        font = new BitmapFont();
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(mainImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font.draw(batch, "Carregando...", Gdx.graphics.getWidth() /2f - 50, Gdx.graphics.getHeight() / 6f);
        batch.end();

        elapsedTime += delta;
        if (elapsedTime > 3){
            game.setScreen(new MenuScreen(game));
        }
    }
    public OpScreen(Home game){
        this.game = game;
    }
    @Override
    public void resize(int width, int height) {

    }
    @Override
    public void pause() {

    }
    @Override
    public void resume() {

    }
    @Override
    public void hide() {

    }
    @Override
    public void dispose() {

    }
}
//Essa classe é responsável pela primeira tela do joggo;
