package com.quartetofantastico.emoesc.interfaceUser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.quartetofantastico.emoesc.logicAndMechanic.Desafio;

public class DesafioModal {

    public interface RespostaCallback {
        void onResposta(boolean acertou);
    }

    private Stage stage;
    private Skin skin;
    private Dialog dialog;
    private boolean visivel = false;

    public DesafioModal() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin();

        BitmapFont font = new BitmapFont();

        // Textura fundo escuro
        Pixmap pixmapFundo = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmapFundo.setColor(0.1f, 0.1f, 0.1f, 1f);
        pixmapFundo.fill();
        Texture texturaFundo = new Texture(pixmapFundo);
        pixmapFundo.dispose();

        // Textura botão verde
        Pixmap pixmapBotao = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmapBotao.setColor(0.0f, 0.5f, 0.0f, 1f);
        pixmapBotao.fill();
        Texture texturaBotao = new Texture(pixmapBotao);
        pixmapBotao.dispose();

        skin.add("default-font", font);
        skin.add("white", texturaBotao);

        // Window style
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = font;
        windowStyle.titleFontColor = Color.WHITE;
        windowStyle.background = new TextureRegionDrawable(texturaFundo);
        skin.add("default", windowStyle);

        // Label style
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;
        skin.add("default", labelStyle);

        // TextField style
        TextField.TextFieldStyle tfStyle = new TextField.TextFieldStyle();
        tfStyle.font = font;
        tfStyle.fontColor = Color.WHITE;
        Pixmap pixmapTf = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmapTf.setColor(0.2f, 0.2f, 0.2f, 1f);
        pixmapTf.fill();
        tfStyle.background = new TextureRegionDrawable(new Texture(pixmapTf));
        pixmapTf.dispose();
        skin.add("default", tfStyle);

        // TextButton style
        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
        btnStyle.font = font;
        btnStyle.fontColor = Color.WHITE;
        btnStyle.up = new TextureRegionDrawable(texturaBotao);
        skin.add("default", btnStyle);

        // ScrollPane style
        ScrollPane.ScrollPaneStyle scrollStyle = new ScrollPane.ScrollPaneStyle();
        skin.add("default", scrollStyle);
    }

    public void show(Desafio desafio, RespostaCallback callback) {
        visivel = true;
        Gdx.input.setInputProcessor(stage);

        dialog = new Dialog("Desafio!", skin) {
            @Override
            public float getPrefWidth() { return 500f; }
            @Override
            public float getPrefHeight() { return 300f; }
        };

        dialog.getTitleLabel().setAlignment(com.badlogic.gdx.utils.Align.center);

        // Pergunta
        Label perguntaLabel = new Label(desafio.getPergunta(), skin);
        perguntaLabel.setWrap(true);
        perguntaLabel.setAlignment(com.badlogic.gdx.utils.Align.center);

        // Campo de resposta
        TextField campoResposta = new TextField("", skin);
        campoResposta.setMessageText("Escreve a tua resposta...");

        // Botão confirmar
        TextButton btnConfirmar = new TextButton("Confirmar", skin);
        btnConfirmar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String respostaJogador = campoResposta.getText();
                boolean acertou = desafio.acertou(respostaJogador);
                dialog.hide();
                visivel = false;
                callback.onResposta(acertou);
            }
        });

        // Botão pular
        TextButton btnPular = new TextButton("Pular (-10 HP)", skin);
        btnPular.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
                visivel = false;
                callback.onResposta(false);
            }
        });

        dialog.getContentTable().pad(20);
        dialog.getContentTable().add(perguntaLabel).width(400).padBottom(20).row();
        dialog.getContentTable().add(campoResposta).width(400).height(40).padBottom(20).row();

        dialog.getButtonTable().pad(10);
        dialog.getButtonTable().add(btnConfirmar).width(150).height(50).padRight(20);
        dialog.getButtonTable().add(btnPular).width(150).height(50);

        dialog.show(stage);
        dialog.setSize(500, 350);
        dialog.setPosition(
            Gdx.graphics.getWidth() / 2f - dialog.getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f - dialog.getHeight() / 2f
        );
    }

    public void render() {
        if (visivel) {
            stage.act();
            stage.draw();
        }
    }

    public boolean isVisivel() {
        return visivel;
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
