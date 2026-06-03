package com.quartetofantastico.emoesc.interfaceUser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.quartetofantastico.emoesc.logicAndMechanic.Constants;
import com.quartetofantastico.emoesc.world.Animals;
import com.quartetofantastico.emoesc.world.Avatar;
import com.quartetofantastico.emoesc.world.WorldMap;

public class GameScreen implements Screen {
    Constants CONST = new Constants();
    Animals.Hostil.Tiger tiger;
    Animals.Hostil.Crocodile crocodile;
    Animals.Hostil.Leopard leopard;

    Animals.Restricted.Gorilla gorilla;
    Animals.Restricted.Elephant elephant;
    Animals.Restricted.Giraffe giraffe;

    private FitViewport viewport;
    private ShapeRenderer render;
    private SpriteBatch batch;
    private Avatar avatar;
    private WorldMap worldMap;


    private OrthographicCamera camera;

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, CONST.W_SCREEN, CONST.H_SCREEN);

        viewport = new FitViewport(CONST.W_SCREEN, CONST.H_SCREEN, camera);

        render = new ShapeRenderer();
        batch = new SpriteBatch();

        avatar = new Avatar("Player", new Vector2(100, 500));
        tiger = new Animals.Hostil.Tiger(new Vector2((CONST.W_WORLD_SIZE / 2)-100, (CONST.H_WORLD_SIZE / 3)-100));
        crocodile = new Animals.Hostil.Crocodile(new Vector2((CONST.W_WORLD_SIZE / 2)-150, (CONST.H_WORLD_SIZE /2)-150));
        leopard = new Animals.Hostil.Leopard(new Vector2((CONST.W_WORLD_SIZE/2)-200, (CONST.H_WORLD_SIZE/2)-200));
        giraffe = new Animals.Restricted.Giraffe(new Vector2((CONST.W_WORLD_SIZE/2)-200, (CONST.H_WORLD_SIZE/2)-200));

        // show():
        worldMap = WorldMap.load("Worlds/World0.txt", CONST.SCALE);
        avatar.setWorldMap(worldMap);
        tiger.setWorldMap(worldMap);
// etc.

// render() — draw do mundo antes das entidades:
        render.begin(ShapeRenderer.ShapeType.Filled);
        worldMap.draw(render);
        crocodile.draw(render);
        tiger.draw(render);
        avatar.draw(render);
        render.end();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(CONST.BLUE_COLOR_FLOAT, CONST.TRANSPARENT_COLOR_FLOAT, CONST.TRANSPARENT_COLOR_FLOAT, CONST.TRANSPARENT_COLOR_FLOAT);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        avatar.update(delta);

        // Câmera segue avatar com limites de mundo
        float cameraHalfWidth = camera.viewportWidth / 2f;
        float cameraHalfHeight = camera.viewportHeight / 2f;

        float targetX = avatar.getVector2PositionX()+avatar.getWidth();
        float targetY = avatar.getVector2PositionY()+avatar.getHeight();

        float clampedX = Math.max(cameraHalfWidth, Math.min(targetX, CONST.W_WORLD_SIZE - cameraHalfWidth));
        float clampedY = Math.max(cameraHalfHeight, Math.min(targetY, CONST.H_WORLD_SIZE - cameraHalfHeight));

        camera.position.set(clampedX, clampedY, 0);
        camera.update();

        render.setProjectionMatrix(camera.combined);

        render.begin(ShapeRenderer.ShapeType.Filled);
        // desenhar paredes aqui (se quiser vê-las)
        if (worldMap != null) {
            worldMap.draw(render);
        }

        crocodile.draw(render);
        tiger.draw(render);
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
