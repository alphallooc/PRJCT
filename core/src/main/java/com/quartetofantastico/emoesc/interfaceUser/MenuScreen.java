package com.quartetofantastico.emoesc.interfaceUser;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MenuScreen implements Screen{
    Dialog dialogCreditos;
    Texture fundoMenu;
    SpriteBatch batch;
    BitmapFont font;
    OrthographicCamera camera;
    FitViewport viewport;
    Stage stage;
    TextButton btnJogar;
    TextButton btnCreditos;
    TextButton btnDefinicoes;
    TextButton btnSair;
    Table table;
    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        batch = new SpriteBatch();
        font = new BitmapFont();
        fundoMenu = new Texture(Gdx.files.internal("ui/fundo_menu.jpeg"));
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        Pixmap pixmap= new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 0, 0, 1);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(texture);
        style.font = font;
        btnJogar = new TextButton("Jogar", style);
        btnCreditos = new TextButton("Créditos", style);
        btnDefinicoes = new TextButton("Definições", style);
        btnSair = new TextButton("Sair", style);
        table = new Table();
        table.setFillParent(true);
        table.add(btnJogar).pad(10).width(200).height(50).row();
        table.add(btnCreditos).pad(10).width(200).height(50).row();
        table.add(btnDefinicoes).pad(10).width(200).height(50).row();
        table.add(btnSair).pad(10).width(200).height(50).row();
        stage.addActor(table);
        Skin skin = new Skin();
        skin.add("default-font", font);
        skin.add("white", texture);

        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = font;
        windowStyle.background = new TextureRegionDrawable(texture);
        skin.add("default", windowStyle);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        skin.add("default", labelStyle);

        TextButton.TextButtonStyle dialogBtnStyle = new TextButton.TextButtonStyle();
        dialogBtnStyle.font = font;
        skin.add("default", dialogBtnStyle);

        dialogCreditos = new Dialog("Créditos", skin);
        dialogCreditos.getContentTable().pad(15);
        dialogCreditos.text("Desenvolvido por: Swaee");
        dialogCreditos.getButtonTable().pad(10);
        dialogCreditos.button("  Fechar  ");

        btnCreditos.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y){
               dialogCreditos.show(stage);
               dialogCreditos.setSize(Gdx.graphics.getWidth() * 0.6f, Gdx.graphics.getHeight() * 0.6f);
               dialogCreditos.setPosition(
                   Gdx.graphics.getWidth() / 2f - dialogCreditos.getWidth() / 2f,
                   Gdx.graphics.getHeight() / 2f - dialogCreditos.getHeight() / 2f
               );
           }
        });

        btnJogar.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Hand);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Arrow);
            }
        });
        btnDefinicoes.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Hand);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Arrow);
            }
        });
        btnSair.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Hand);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Arrow);
            }
        });
        btnCreditos.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Hand);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Arrow);
            }
        });

        btnSair.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }

        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.setColor(1, 1, 1, 0.5f);
        batch.draw(fundoMenu, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setColor(1, 1, 1, 1);
        batch.end();
        stage.act(delta);
        stage.draw();
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
