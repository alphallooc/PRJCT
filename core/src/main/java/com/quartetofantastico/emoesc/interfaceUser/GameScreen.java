package com.quartetofantastico.emoesc.interfaceUser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.quartetofantastico.emoesc.logicAndMechanic.Constants;
import com.quartetofantastico.emoesc.world.Animals;
import com.quartetofantastico.emoesc.world.Avatar;

public class GameScreen implements Screen {
    Constants CONST = new Constants();
    Animals.Hostil.Tiger tiger;
    Animals.Hostil.Crocodile crocodile;
    Animals.Hostil.Leopard leopard;

    Animals.Friendly.Cat cat;
    Animals.Friendly.Horse horse;
    Animals.Friendly.Dog dog;

    Animals.Neutral.Bird bird;
    Animals.Neutral.Rabbit rabbit;
    Animals.Neutral.Turtle turtle;

    Animals.Restricted.Gorilla gorilla;
    Animals.Restricted.Elephant elephant;
    Animals.Restricted.Giraffe giraffe;

    private FitViewport viewport;
    private ShapeRenderer render;
    private SpriteBatch batch;
    private Avatar avatar;


    private OrthographicCamera camera;

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(CONST.W_SCREEN, CONST.H_SCREEN, camera);

        render = new ShapeRenderer();
        //render.setAutoShapeType(true);
        batch = new SpriteBatch();

        avatar = new Avatar("Player", new Vector2(CONST.W_WORLD_SIZE / 2, CONST.H_WORLD_SIZE / 2));
        tiger = new Animals.Hostil.Tiger(new Vector2((CONST.W_WORLD_SIZE / 2)-100, (CONST.H_WORLD_SIZE / 3)-100));
        crocodile = new Animals.Hostil.Crocodile(new Vector2((CONST.W_WORLD_SIZE / 2)-150, (CONST.H_WORLD_SIZE /2)-150));
        leopard = new Animals.Hostil.Leopard(new Vector2((CONST.W_WORLD_SIZE/2)-200, (CONST.H_WORLD_SIZE/2)-200));
        dog = new Animals.Friendly.Dog(new Vector2((CONST.W_WORLD_SIZE/2)-200, (CONST.H_WORLD_SIZE/2)-200));

        giraffe = new Animals.Restricted.Giraffe (new Vector2((CONST.W_WORLD_SIZE/2)-200, (CONST.H_WORLD_SIZE/2)-200));
        camera.setToOrtho(false, CONST.W_SCREEN, CONST.H_SCREEN);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(CONST.BLUE_COLOR_FLOAT, CONST.TRANSPARENT_COLOR_FLOAT, CONST.TRANSPARENT_COLOR_FLOAT, CONST.TRANSPARENT_COLOR_FLOAT);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        avatar.update(delta);

        tiger.update(delta);
        crocodile.update(delta);
        leopard.update(delta);
        dog.update(delta);
        giraffe.update(delta);

        camera.position.set(avatar.getVector2PositionX(), avatar.getVector2PositionY(), 0);
        camera.update();

        render.setProjectionMatrix(camera.combined);

        render.begin(ShapeRenderer.ShapeType.Filled);
        crocodile.draw(render);
        tiger.draw(render);
        leopard.draw(render);
        dog.draw(render);
        giraffe.draw(render);
        avatar.draw(render);
        render.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        render.dispose();
    }
}
