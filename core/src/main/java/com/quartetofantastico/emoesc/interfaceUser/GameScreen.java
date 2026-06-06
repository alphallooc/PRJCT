package com.quartetofantastico.emoesc.interfaceUser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.quartetofantastico.emoesc.logicAndMechanic.Constants;
import com.quartetofantastico.emoesc.logicAndMechanic.Desafio;
import com.quartetofantastico.emoesc.logicAndMechanic.DesafioManager;
import com.quartetofantastico.emoesc.interfaceUser.DesafioModal;
import com.quartetofantastico.emoesc.world.Emoji;
import com.quartetofantastico.emoesc.world.Animals;
import com.quartetofantastico.emoesc.world.Avatar;

import com.badlogic.gdx.utils.Array;
import com.quartetofantastico.emoesc.world.Frutas;
import com.quartetofantastico.emoesc.world.WallStructs;



public class GameScreen implements Screen {
    private Array<Animals> todosOsAnimais = new Array<>();
    private boolean gameOver = false;
    private Rectangle botaoContinuar;
    private Rectangle botaoMenu;
    private Rectangle botaoDefinicoes;
    private Rectangle botaoSair;

    private java.util.ArrayList<Emoji> emojis = new java.util.ArrayList<>();
    private DesafioManager desafioManager;
    private DesafioModal modal;

    private Emoji emojiAtual = null;
    private boolean esperandoResposta = false;
    private float cooldownErro = 0;
    private boolean bloqueado = false;

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
    private final com.badlogic.gdx.Game game;
    public GameScreen(com.badlogic.gdx.Game game){
        this.game = game;
    }

    private OrthographicCamera camera;

    private Rectangle botaoPausar;
    private boolean jogoPausado = false;

    @Override
    public void show() {
        walls = WallStructs.loadFromFile("maze.txt");

        float maxLabirintoX = 0;
        float maxLabirintoY = 0;

        for (Rectangle wall : walls) {
            if (wall.x + wall.width > maxLabirintoX) {
                maxLabirintoX = wall.x + wall.width;
            }
            if (wall.y + wall.height > maxLabirintoY) {
                maxLabirintoY = wall.y + wall.height;
            }
        }

        Constants.W_WORLD_SIZE = maxLabirintoX;
        Constants.H_WORLD_SIZE = maxLabirintoY;

        camera = new OrthographicCamera();
        viewport = new FitViewport(CONST.W_SCREEN, CONST.H_SCREEN, camera);
        camera.position.set(55, 900, 0);
        camera.update();

        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, CONST.W_SCREEN, CONST.H_SCREEN);

        render = new ShapeRenderer();
        batch = new SpriteBatch();

        avatar = new Avatar("Player", new Vector2(55, 900));
        tiger = new Animals.Hostil.Tiger(new Vector2(1200, 950));
        crocodile = new Animals.Hostil.Crocodile(new Vector2(600, 400));
        leopard = new Animals.Hostil.Leopard(new Vector2(2500, 800));
        dog = new Animals.Friendly.Dog(new Vector2(1800, 300));
        giraffe = new Animals.Restricted.Giraffe(new Vector2(1500, 600));

        todosOsAnimais.add(tiger);
        todosOsAnimais.add(crocodile);
        todosOsAnimais.add(leopard);
        todosOsAnimais.add(dog);
        todosOsAnimais.add(giraffe);

        avatar.walls = walls;
        tiger.walls = walls;
        crocodile.walls = walls;
        leopard.walls = walls;
        dog.walls = walls;
        giraffe.walls = walls;

        desafioManager = new DesafioManager();
        modal = new DesafioModal();



        Emoji emoji1 = new Emoji("😀", desafioManager.sortear());
        emoji1.setPosition(200, 300);

        Emoji emoji2 = new Emoji("🔥", desafioManager.sortear());
        emoji2.setPosition(500, 300);

        emojis.add(emoji1);
        emojis.add(emoji2);

        /*avatar.entities.add(tiger);
        avatar.entities.add(crocodile);
        avatar.entities.add(leopard);
        avatar.entities.add(dog);
        avatar.entities.add(giraffe);*/

        frutas.clear();
        Array<Vector2> posicoesLivres = new Array<>();

        float larguraMapa = Constants.W_WORLD_SIZE;
        float alturaMapa = Constants.H_WORLD_SIZE;
        float passo = 60f;

        for (float x = passo; x < larguraMapa - passo; x += passo) {
            for (float y = passo; y < alturaMapa - passo; y += passo) {
                boolean colisaoComParede = false;
                Rectangle areaTeste = new Rectangle(x, y, 20f, 20f);

                for (Rectangle wall : walls) {
                    if (wall.overlaps(areaTeste)) {
                        colisaoComParede = true;
                        break;
                    }
                }

                if (!colisaoComParede) {
                    float distanciaAoAvatar = Vector2.dst(x, y, avatar.getVector2PositionX(), avatar.getVector2PositionY());
                    if (distanciaAoAvatar > 150) {
                        posicoesLivres.add(new Vector2(x + 20, y + 20));
                    }
                }
            }
        }

        if (posicoesLivres.size >= 4) {
            posicoesLivres.shuffle();
            frutas.add(new Frutas(Frutas.FruitType.MACA,    posicoesLivres.get(0)));
            frutas.add(new Frutas(Frutas.FruitType.BANANA,   posicoesLivres.get(1)));
            frutas.add(new Frutas(Frutas.FruitType.LARANJA,  posicoesLivres.get(2)));
            frutas.add(new Frutas(Frutas.FruitType.UVA,      posicoesLivres.get(3)));
        } else {
            frutas.add(new Frutas(Frutas.FruitType.MACA,    new Vector2(avatar.getVector2PositionX() + 100, avatar.getVector2PositionY())));
            frutas.add(new Frutas(Frutas.FruitType.BANANA,   new Vector2(avatar.getVector2PositionX() + 150, avatar.getVector2PositionY())));
            frutas.add(new Frutas(Frutas.FruitType.LARANJA,  new Vector2(avatar.getVector2PositionX() + 200, avatar.getVector2PositionY())));
            frutas.add(new Frutas(Frutas.FruitType.UVA,      new Vector2(avatar.getVector2PositionX() + 250, avatar.getVector2PositionY())));
        }

        botaoPausar = new Rectangle(20, CONST.H_SCREEN - 60, 120, 40);

        botaoContinuar = new Rectangle(CONST.W_SCREEN/2f - 100, CONST.H_SCREEN/2f + 20, 200, 50);
        botaoMenu = new Rectangle(CONST.W_SCREEN/2f - 100, CONST.H_SCREEN/2f - 40, 200, 50);
        botaoSair = new Rectangle(CONST.W_SCREEN/2f - 100, CONST.H_SCREEN/2f - 100, 200, 50);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.2f, 1f);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.justTouched()) {
            Vector3 toquedomouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            hudCamera.unproject(toquedomouse);

            if (gameOver) {

                if (botaoContinuar.contains(toquedomouse.x, toquedomouse.y)) {
                    reiniciarJogo();
                    return;
                }
                if (botaoMenu.contains(toquedomouse.x, toquedomouse.y)) {
                    game.setScreen(new MenuScreen(game));
                }
                if (botaoSair.contains(toquedomouse.x, toquedomouse.y)) {
                    Gdx.app.exit();
                }
            } else {
                if (botaoPausar.contains(toquedomouse.x, toquedomouse.y)) {
                    jogoPausado = !jogoPausado;
                }
            }
        }

        if (!jogoPausado && !gameOver && !modal.isVisivel()) {
            avatar.update(delta);

            for (Animals animal : todosOsAnimais) {
                animal.update(delta);
            }

            for (Frutas fruta : frutas) {
                fruta.update(delta);
            }

            for (Emoji emoji : emojis) {

                if (!emoji.isColetado()
                    && avatar.getBounds().overlaps(emoji.getBounds())
                    && !emoji.isEmCooldown()
                    && !esperandoResposta) {

                    esperandoResposta = true;
                    emojiAtual = emoji;

                    modal.show(emoji.getDesafio(), new DesafioModal.RespostaCallback() {
                        @Override
                        public void onResposta(boolean acertou) {

                            esperandoResposta = false;

                            if (acertou) {
                                emojiAtual.coletar();
                                avatar.ativarPoder();
                            } else {
                                avatar.ativarPunicao();
                                emojiAtual.startCooldown(30f);
                            }

                            emojiAtual = null;
                        }
                    });
                }

                emoji.updateCooldown(delta);
            }
        }


        camera.position.set(avatar.getVector2PositionX(), avatar.getVector2PositionY(), 0);
        camera.update();

        render.setProjectionMatrix(camera.combined);

        float camX = MathUtils.clamp(avatar.getVector2PositionX(), CONST.W_SCREEN / 2f, Constants.W_WORLD_SIZE - (CONST.W_SCREEN / 2f));
        float camY = MathUtils.clamp(avatar.getVector2PositionY(), CONST.H_SCREEN / 2f, Constants.H_WORLD_SIZE - (CONST.H_SCREEN / 2f));

        camera.position.set(camX, camY, 0);
        camera.update();

        render.setProjectionMatrix(camera.combined);

        for (Frutas fruta : frutas){
            if (!fruta.isCollected() && avatar.getBounds().overlaps(fruta.getBounds())){
                avatar.increaseHealth(fruta.getHealAmount());
                fruta.setCollected(true);
            }
        }

        float avatarX = avatar.getVector2PositionX();
        float avatarY = avatar.getVector2PositionY();
        Rectangle hitboxAvatarReal = new Rectangle(avatarX, avatarY, 32f, 32f);

        for (Animals animal : todosOsAnimais) {
            if (animal instanceof Animals.Hostil) {
                float distancia = Vector2.dst(
                    avatar.getVector2PositionX(),
                    avatar.getVector2PositionY(),
                    animal.getVector2PositionX(),
                    animal.getVector2PositionY()
                );
                if (distancia < 40f)  {
                    if (avatar.podeTomarDano()) {
                        avatar.decreaseHealth(((Animals.Hostil) animal).getDano());
                        avatar.ativarInvencibilidade();
                        System.out.println("Dano! HP: " + avatar.getHealth());
                    }
                    break;
                }
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

        render.setColor(CONST.GRAY_COLOR);
        for (Rectangle wall : walls) {
            render.rect(wall.x, wall.y, wall.width, wall.height);
        }
        render.end();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        avatar.drawSprite(batch);
        tiger.drawSprite(batch);
        crocodile.drawSprite(batch);
        giraffe.drawSprite(batch);
        for (Emoji emoji : emojis) {
            emoji.draw(batch);
        }
        batch.end();



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

        render.setProjectionMatrix(hudCamera.combined);
        batch.setProjectionMatrix(hudCamera.combined);

        if (avatar.getHealth() <= 0) {
            gameOver = true;
        }

        if (gameOver) {
            jogoPausado = true;
        }

// Botão pausa
        render.begin(ShapeRenderer.ShapeType.Filled);
        render.setColor(CONST.GRAY_COLOR);
        render.rect(botaoPausar.x, botaoPausar.y,
            botaoPausar.width, botaoPausar.height);
        render.end();

// HUD
        batch.begin();

        font.setColor(0f, 1f, 0f, 1f);
        font.draw(batch, "PAUSA",
            botaoPausar.x + 35,
            botaoPausar.y + 25);

        font.setColor(1f, 1f, 1f, 1f); // reset

        String hpText = "HP: " + avatar.getHealth();
        font.draw(batch,
            hpText,
            CONST.W_SCREEN - 100,
            CONST.H_SCREEN - 10);

        modal.render();
        batch.end();

// Tela Game Over
        if (gameOver) {
            render.setProjectionMatrix(hudCamera.combined);

            render.begin(ShapeRenderer.ShapeType.Filled);

            // Fundo escuro
            render.setColor(0, 0, 0, 0.7f);
            render.rect(
                CONST.W_SCREEN / 2f - 150,
                CONST.H_SCREEN / 2f - 130,
                300,
                220
            );

            // Botão Continuar
            render.setColor(CONST.RED_COLOR);
            render.rect(
                botaoContinuar.x,
                botaoContinuar.y,
                botaoContinuar.width,
                botaoContinuar.height
            );

            // Botão Menu
            render.setColor(CONST.GRAY_COLOR);
            render.rect(
                botaoMenu.x,
                botaoMenu.y,
                botaoMenu.width,
                botaoMenu.height
            );

            // Botão Sair
            render.setColor(CONST.DARK_GRAY_COLOR);
            render.rect(
                botaoSair.x,
                botaoSair.y,
                botaoSair.width,
                botaoSair.height
            );

            render.end();

            // IMPORTANTE: mesma câmera da HUD
            batch.setProjectionMatrix(hudCamera.combined);

            batch.begin();

            font.draw(
                batch,
                "GAME OVER!",
                CONST.W_SCREEN / 2f - 50,
                CONST.H_SCREEN / 2f + 100
            );

            font.draw(
                batch,
                "Continuar",
                botaoContinuar.x + 50,
                botaoContinuar.y + 30
            );

            font.draw(
                batch,
                "Menu",
                botaoMenu.x + 70,
                botaoMenu.y + 30
            );

            font.draw(
                batch,
                "Sair",
                botaoSair.x + 75,
                botaoSair.y + 30
            );

            batch.end();
        }

    }

    private void reiniciarJogo() {

        gameOver = false;
        jogoPausado = false;

        avatar = new Avatar("Player", new Vector2(55, 900));

        tiger = new Animals.Hostil.Tiger(new Vector2(1200, 950));
        crocodile = new Animals.Hostil.Crocodile(new Vector2(600, 400));
        leopard = new Animals.Hostil.Leopard(new Vector2(2500, 800));
        dog = new Animals.Friendly.Dog(new Vector2(1800, 300));
        giraffe = new Animals.Restricted.Giraffe(new Vector2(1500, 600));

        avatar.walls = walls;
        tiger.walls = walls;
        crocodile.walls = walls;
        leopard.walls = walls;
        dog.walls = walls;
        giraffe.walls = walls;

        todosOsAnimais.clear();

        todosOsAnimais.add(tiger);
        todosOsAnimais.add(crocodile);
        todosOsAnimais.add(leopard);
        todosOsAnimais.add(dog);
        todosOsAnimais.add(giraffe);

        frutas.clear();

        camera.position.set(55, 900, 0);
        camera.update();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
        modal.resize(width, height);
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
        batch.dispose();
        font.dispose();
        modal.dispose();
    }
}
