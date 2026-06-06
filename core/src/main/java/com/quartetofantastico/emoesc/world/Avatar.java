package com.quartetofantastico.emoesc.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.quartetofantastico.emoesc.logicAndMechanic.Constants;
import com.quartetofantastico.emoesc.logicAndMechanic.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Avatar extends Entity {

    private float invencibilidade = 0f;
    Constants CONST = new Constants();

    private String name;
    private int health;
    float tamanho;
    float olhoTamanho;
    private float speed = 200.0f;
    int dirX = 0, dirY = 0;
    public Array entities = new Array<>();

    private Animation<TextureRegion> animacaoCorrer;
    private SpriteBatch spriteBatch;
    private float tempoAnimacao = 0f;

    // Buffs temporários
    private float speedBuffTimer = 0;
    private boolean speedBuffAtivo = false;

    // Escudo
    private boolean temEscudo = false;

    // Preso (quando erra)
    private float presoBloqueioTimer = 0f;
    private boolean estaPreso = false;

    // Speed debuff (quando erra)
    private float speedDebuffTimer = 0f;
    private boolean speedDebuffAtivo = false;

    // Posição inicial para recuo
    private Vector2 posicaoInicial;

    public Avatar(String _name, Vector2 _position) {
        super(_position, 0.8f * Constants.SCALE, 0.8f * Constants.SCALE);
        tamanho = 0.8f * Constants.SCALE;
        olhoTamanho = 0.1f * Constants.SCALE;
        this.name = _name;
        this.health = 100;
        this.posicaoInicial = new Vector2(_position);
        bounds = new Rectangle(_position.x, _position.y, tamanho, tamanho);

        spriteBatch = new SpriteBatch();
        TextureRegion[] frames = new TextureRegion[6];
        for (int i = 0; i < 6; i++) {
            String path = String.format("sprites/police/run/3_police_Run_%03d.png", i);
            frames[i] = new TextureRegion(new Texture(path));
        }
        animacaoCorrer = new Animation<>(0.1f, frames);
    }

    public Array<Rectangle> walls = new Array<>();

    public void drawSprite(SpriteBatch batch) {
        TextureRegion frame = animacaoCorrer.getKeyFrame(tempoAnimacao, true);
        batch.draw(frame, position.x, position.y, tamanho, tamanho);
    }

    public void draw(ShapeRenderer _draw) {
        float baseOlhoX = position.x + (tamanho / 2);
        float baseOlhoY = position.y + (tamanho * 0.7f);
        float olharX = baseOlhoX + (dirX * 0.2f * CONST.SCALE);
        float olharY = baseOlhoY + (dirY * 0.2f * CONST.SCALE);
        _draw.setColor(CONST.GREEN_COLOR);
        _draw.rect(position.x, position.y, tamanho, tamanho);
        _draw.setColor(CONST.WHITE_COLOR);
        _draw.circle(olharX - (0.2f * CONST.SCALE), olharY, olhoTamanho);
        _draw.circle(olharX + (0.2f * CONST.SCALE), olharY, olhoTamanho);
    }

    public void update(float delta) {
        if (invencibilidade > 0) invencibilidade -= delta;

        // Atualiza timer de preso
        if (estaPreso) {
            presoBloqueioTimer -= delta;
            if (presoBloqueioTimer <= 0) {
                estaPreso = false;
                presoBloqueioTimer = 0;
            }
            return; // não move enquanto está preso
        }

        // Atualiza speed debuff
        if (speedDebuffAtivo) {
            speedDebuffTimer -= delta;
            if (speedDebuffTimer <= 0) {
                speedDebuffAtivo = false;
                speedDebuffTimer = 0;
            }
        }

        dirX = 0;
        dirY = 0;
        if (CONST.UP_KEY()) dirY = 1;
        if (CONST.DOWN_KEY()) dirY = -1;
        if (CONST.LEFT_KEY()) dirX = -1;
        if (CONST.RIGHT_KEY()) dirX = 1;

        move(dirX * getVelocidadeAtual() * delta, dirY * getVelocidadeAtual() * delta, entities);

        if (dirX != 0 || dirY != 0) tempoAnimacao += delta;

        // Atualiza speed buff
        if (speedBuffAtivo) {
            speedBuffTimer -= delta;
            if (speedBuffTimer <= 0) {
                speedBuffAtivo = false;
                speedBuffTimer = 0;
            }
        }
    }

    public void move(float _nextX, float _nextY, Array<Entity> entities) {
        Array<Entity> alvosProcura = (entities != null) ? entities : this.entities;

        if (_nextX != 0) {
            float nextX = MathUtils.clamp(position.x + _nextX, 0, Constants.W_WORLD_SIZE - tamanho);
            Rectangle nextBoundsX = new Rectangle(nextX, position.y, tamanho, tamanho);
            boolean colideX = false;
            for (Rectangle wall : walls) if (nextBoundsX.overlaps(wall)) { colideX = true; break; }
            if (!colideX && !collides(nextX, position.y, alvosProcura)) position.x = nextX;
        }

        if (_nextY != 0) {
            float nextY = MathUtils.clamp(position.y + _nextY, 0, Constants.H_WORLD_SIZE - tamanho);
            Rectangle nextBoundsY = new Rectangle(position.x, nextY, tamanho, tamanho);
            boolean colideY = false;
            for (Rectangle wall : walls) if (nextBoundsY.overlaps(wall)) { colideY = true; break; }
            if (!colideY && !collides(position.x, nextY, alvosProcura)) position.y = nextY;
        }

        updateBounds();
    }

    @Override
    public void updateBounds() {
        bounds.setPosition(position.x, position.y);
    }

    public String getName() { return name; }
    public int getHealth() { return health; }

    public void decreaseHealth(int damage) {
        // Se tem escudo, absorve 1 dano
        if (temEscudo) {
            temEscudo = false;
            System.out.println("Escudo absorveu o dano!");
            return;
        }
        health -= damage;
        if (health < CONST.MIN_HEALTH) health = CONST.MIN_HEALTH;
    }

    public void increaseHealth(int heal) {
        health += heal;
        if (health > CONST.MAX_HEALTH) health = CONST.MAX_HEALTH;
    }

    public boolean podeTomarDano() { return invencibilidade <= 0; }
    public void ativarInvencibilidade() { invencibilidade = 1.0f; }
    public boolean temEscudo() { return temEscudo; }

    public float getVelocidadeAtual() {
        if (speedBuffAtivo) return speed * 1.5f;
        if (speedDebuffAtivo) return speed * 0.5f;
        return speed;
    }

    public void increaseSpeed(float valor) { speed += valor; }
    public void decreaseSpeed(float valor) { speed -= valor; }

    // ======== ACERTOU ========
    public void ativarPoder() {
        increaseHealth(20);
        temEscudo = true;
        speedBuffAtivo = true;
        speedBuffTimer = 10f;
        System.out.println("Acertou! +20 HP, escudo ativado, velocidade aumentada!");
    }

    // ======== ERROU ========
    public void ativarPunicao() {
        decreaseHealth(10);
        // Recua para posição inicial
        position.set(posicaoInicial);
        updateBounds();
        // Fica preso por 10 segundos
        estaPreso = true;
        presoBloqueioTimer = 10f;
        // Velocidade reduzida por 5 segundos
        speedDebuffAtivo = true;
        speedDebuffTimer = 5f;
        System.out.println("Errou! -10 HP, preso por 10s, velocidade reduzida!");
    }

    public boolean estaPreso() { return estaPreso; }
    public float getPresoBloqueioTimer() { return presoBloqueioTimer; }
}
