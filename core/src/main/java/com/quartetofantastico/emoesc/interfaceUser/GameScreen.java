package com.quartetofantastico.emoesc.interfaceUser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.quartetofantastico.emoesc.logicAndMechanic.Constants;
import com.quartetofantastico.emoesc.world.Animals;
import com.quartetofantastico.emoesc.world.Avatar;

import com.badlogic.gdx.utils.Array;
import com.quartetofantastico.emoesc.world.Frutas;
import com.quartetofantastico.emoesc.world.WallStructs;


public class GameScreen implements Screen {
    Array<Rectangle> walls;
    BitmapFont font = new BitmapFont();
    OrthographicCamera hudCamera;
    Array<Frutas> frutas = new Array<>();
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

        avatar = new Avatar("Player", new Vector2(55, 900));
        tiger = new Animals.Hostil.Tiger(new Vector2(200, 950));
        crocodile = new Animals.Hostil.Crocodile(new Vector2(200, 400));
        leopard = new Animals.Hostil.Leopard(new Vector2(800, 950));
        dog = new Animals.Friendly.Dog(new Vector2(1000, 400));
        giraffe = new Animals.Restricted.Giraffe(new Vector2(600, 200));


        camera.setToOrtho(false, CONST.W_SCREEN, CONST.H_SCREEN);

        frutas.add(new Frutas(Frutas.FruitType.MAÇA,    new Vector2(400, 950)));
        frutas.add(new Frutas(Frutas.FruitType.BANANA,   new Vector2(600, 400)));
        frutas.add(new Frutas(Frutas.FruitType.LARANJA,  new Vector2(1000, 200)));
        frutas.add(new Frutas(Frutas.FruitType.UVA,      new Vector2(800, 600)));

        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, CONST.W_SCREEN, CONST.H_SCREEN);

        avatar.entities.add(tiger);
        avatar.entities.add(crocodile);
        avatar.entities.add(leopard);
        avatar.entities.add(dog);
        avatar.entities.add(giraffe);

        walls = WallStructs.loadFromFile("maze.txt");
        avatar.walls = walls;
        tiger.walls = walls;
        crocodile.walls = walls;
        leopard.walls = walls;
        dog.walls = walls;
        giraffe.walls = walls;
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

        for (Frutas fruta : frutas){
            if (!fruta.isCollected() && avatar .getBounds().overlaps(fruta.getBounds())){
                avatar.increaseHealth(fruta.getHealAmount());
                fruta.setCollected(true);
            }
        }

        if (avatar.podeTomarDano()) {
            if (avatar.getBounds().overlaps(tiger.getBounds())) {
                avatar.decreaseHealth(tiger.getDano());
                avatar.ativarInvencibilidade();
            }
            if (avatar.getBounds().overlaps(crocodile.getBounds())) {
                avatar.decreaseHealth(crocodile.getDano());
                avatar.ativarInvencibilidade();
            }
            if (avatar.getBounds().overlaps(leopard.getBounds())) {
                avatar.decreaseHealth(leopard.getDano());
                avatar.ativarInvencibilidade();
            }
        }

        render.begin(ShapeRenderer.ShapeType.Filled);
        crocodile.draw(render);
        tiger.draw(render);
        leopard.draw(render);
        dog.draw(render);
        giraffe.draw(render);
        for (Frutas fruta : frutas){
            if (!fruta.isCollected()){
                fruta.draw(render);
            }
        }
        render.setColor(CONST.GRAY_COLOR);        // ← adiciona aqui
        for (Rectangle wall : walls) {            // ← adiciona aqui
            render.rect(wall.x, wall.y, wall.width, wall.height);  // ← adiciona aqui
        }                                         // ← adiciona aqui
        avatar.draw(render);
        render.end();

        render.begin(ShapeRenderer.ShapeType.Line);
        render.setColor(CONST.RED_COLOR);
        render.rect(tiger.getBounds().x, tiger.getBounds().y, tiger.getBounds().width, tiger.getBounds().height);
        render.rect(crocodile.getBounds().x, crocodile.getBounds().y, crocodile.getBounds().width, crocodile.getBounds().height);
        render.rect(leopard.getBounds().x, leopard.getBounds().y, leopard.getBounds().width, leopard.getBounds().height);
        render.setColor(CONST.GREEN_COLOR);
        render.rect(avatar.getBounds().x, avatar.getBounds().y, avatar.getBounds().width, avatar.getBounds().height);
        render.setColor(CONST.GRAY_COLOR);
        render.end();

        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        font.draw(batch, "HP: " + avatar.getHealth(), 20, CONST.H_SCREEN - 20);
        batch.end();
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
