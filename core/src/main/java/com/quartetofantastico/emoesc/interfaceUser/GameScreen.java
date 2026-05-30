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
import com.quartetofantastico.emoesc.world.WallStructs;

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
    private Array<Rectangle> walls;


    private OrthographicCamera camera;

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, CONST.W_SCREEN, CONST.H_SCREEN);

        viewport = new FitViewport(CONST.W_SCREEN, CONST.H_SCREEN, camera);

        render = new ShapeRenderer();
        batch = new SpriteBatch();

        avatar = new Avatar("Player", new Vector2(CONST.W_WORLD_SIZE, CONST.H_WORLD_SIZE));
        tiger = new Animals.Hostil.Tiger(new Vector2((CONST.W_WORLD_SIZE / 2)-100, (CONST.H_WORLD_SIZE / 3)-100));
        crocodile = new Animals.Hostil.Crocodile(new Vector2((CONST.W_WORLD_SIZE / 2)-150, (CONST.H_WORLD_SIZE /2)-150));
        leopard = new Animals.Hostil.Leopard(new Vector2((CONST.W_WORLD_SIZE/2)-200, (CONST.H_WORLD_SIZE/2)-200));
        dog = new Animals.Friendly.Dog(new Vector2((CONST.W_WORLD_SIZE/2)-200, (CONST.H_WORLD_SIZE/2)-200));
        giraffe = new Animals.Restricted.Giraffe(new Vector2((CONST.W_WORLD_SIZE/2)-200, (CONST.H_WORLD_SIZE/2)-200));

        walls = WallStructs.loadFromFile("Worlds/World0.txt", CONST.SCALE);
        avatar.setWalls(walls);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(CONST.BLUE_COLOR_FLOAT, CONST.TRANSPARENT_COLOR_FLOAT, CONST.TRANSPARENT_COLOR_FLOAT, CONST.TRANSPARENT_COLOR_FLOAT);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        avatar.update(delta);

        // Câmera segue avatar com limites de mundo
        float cameraHalfWidth = camera.viewportWidth / 2f;
        float cameraHalfHeight = camera.viewportHeight / 2f;

        float targetX = avatar.getVector2PositionX();
        float targetY = avatar.getVector2PositionY();

        float clampedX = Math.max(cameraHalfWidth, Math.min(targetX, CONST.W_WORLD_SIZE - cameraHalfWidth));
        float clampedY = Math.max(cameraHalfHeight, Math.min(targetY, CONST.H_WORLD_SIZE - cameraHalfHeight));

        camera.position.set(clampedX, clampedY, 0);
        camera.update();

        render.setProjectionMatrix(camera.combined);

        render.begin(ShapeRenderer.ShapeType.Filled);
        // desenhar paredes aqui (se quiser vê-las)
        if (walls != null) {
            render.setColor(1f, 1f, 1f, 1f);
            for (Rectangle w : walls) {
                render.rect(w.x, w.y, w.width, w.height);
            }
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
