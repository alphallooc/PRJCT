package com.quartetofantastico.emoesc.interfaceUser;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class DataScreen implements Screen {


    private Stage stage;
    private FitViewport viewport;
    private SpriteBatch batch;
    private Skin skin;
    private Game game;

    public DataScreen(Game _game){this.game = _game;}

    private TextButton[] btn = new TextButton[4];
    @Override public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        skin = new Skin (Gdx.files.internal("ui/uiskin.json"));
        btn[0] = new TextButton("SLOT 1 ",skin);
        btn[1] = new TextButton("SLOT 2 ",skin);
        btn[2] = new TextButton("SLOT 3 ",skin);
        btn[3] = new TextButton("Main Menu ",skin);
        btn[3].addListener(new ClickListener(){@Override public void clicked(InputEvent event, float x, float y){game.setScreen(new MenuScreen(game));}});

        Table table = new Table();
        table.setFillParent(true);
        for (int i = 0; i < btn.length; i++) {
            table.add(btn[i]).pad(10).width(320).height(50).row();
        }
    }

    @Override
    public void render(float delta) {

    }

    @Override public void resize(int width, int height) {viewport.update(width, height, true);}

    @Override public void pause() {}

    @Override public void resume() {}

    @Override public void hide() {}

    @Override public void dispose() {}
}
