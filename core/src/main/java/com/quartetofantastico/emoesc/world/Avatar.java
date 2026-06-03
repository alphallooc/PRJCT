package com.quartetofantastico.emoesc.world;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.quartetofantastico.emoesc.logicAndMechanic.Constants;
import com.quartetofantastico.emoesc.logicAndMechanic.Entity;

public class Avatar extends Entity {
    private float invencibilidade = 0f;
    Constants CONST = new Constants();
    private String name;
    private int id, jump, health;
    float tamanho; // ← sem valor aqui
    float olhoTamanho;
    float speed = 200.0f;
    int dirX = 0, dirY = 0;
    public Array entities = new Array<>();

    public Avatar(String _name, Vector2 _position) {
        super(_position, 0.8f * Constants.SCALE, 0.8f * Constants.SCALE);
        tamanho = 0.8f * Constants.SCALE; // ← calcula aqui dentro
        olhoTamanho = 0.1f * Constants.SCALE; // ← aqui também
        this.name = _name;
        this.health = 100;
        bounds = new Rectangle(
            _position.x,
            _position.y,
            tamanho,
            tamanho
        );
    }
    public Array<Rectangle> walls = new Array<>();
    public void draw(ShapeRenderer _draw) {

        float baseOlhoX = position.x + (tamanho / 2);
        float baseOlhoY = position.y + (tamanho * 0.7f); // Olhos um pouco mais acima

        float olharX = baseOlhoX + (dirX * 0.2f * CONST.SCALE);
        float olharY = baseOlhoY + (dirY * 0.2f * CONST.SCALE);

        // corpo
        _draw.setColor(CONST.GREEN_COLOR);
        _draw.rect(position.x, position.y, tamanho, tamanho);

        // Olhos
        _draw.setColor(CONST.WHITE_COLOR);
        _draw.circle(olharX - (0.2f * CONST.SCALE),
            olharY,
            olhoTamanho); // Olho esquerdo
        _draw.circle(olharX + (0.2f * CONST.SCALE),
            olharY,
            olhoTamanho); // Olho direito
    }
    public void update(float delta) {
        if (invencibilidade > 0) invencibilidade -= delta;
        dirX = 0;
        dirY = 0;
        if (CONST.UP_KEY()) dirY = 1;
        if (CONST.DOWN_KEY()) dirY = -1;
        if (CONST.LEFT_KEY()) dirX = -1;
        if (CONST.RIGHT_KEY()) dirX = 1;
        move(dirX * speed * delta, dirY * speed * delta, entities);
    }
    public void move(float _nextX, float _nextY, Array<Entity> entities) {
        float nextX = position.x + _nextX;
        float nextY = position.y + _nextY;

        // Colisão com paredes do labirinto
        Rectangle nextBoundsX = new Rectangle(nextX, position.y, tamanho, tamanho);
        Rectangle nextBoundsY = new Rectangle(position.x, nextY, tamanho, tamanho);

        boolean colideX = false;
        boolean colideY = false;

        for (Rectangle wall : walls) {
            if (nextBoundsX.overlaps(wall)) colideX = true;
            if (nextBoundsY.overlaps(wall)) colideY = true;
        }

        // Colisão com entidades
        if (!colideX && !collides(nextX, position.y, entities)) {
            position.x = MathUtils.clamp(nextX, 0, CONST.W_WORLD_SIZE - tamanho);
        }
        if (!colideY && !collides(position.x, nextY, entities)) {
            position.y = MathUtils.clamp(nextY, 0, CONST.H_WORLD_SIZE - tamanho);
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
        health -= damage;
        if (health < CONST.MIN_HEALTH) health = CONST.MIN_HEALTH;
    }

    public void increaseHealth(int help) {
        health += help;
        if (health > CONST.MAX_HEALTH) health = CONST.MAX_HEALTH;
    }

    public void increaseSpeed(int _speed) { this.speed += _speed; }
    public void decreaseSpeed(int _speed) { this.speed -= _speed; }
    public void increaseJump() { this.jump = CONST.HIGH_JUMP; }
    public void decreaseJump() { this.jump = CONST.NORMAL_JUMP; }

    public boolean podeTomarDano() {
        return invencibilidade <= 0;
    }

    public void ativarInvencibilidade() {
        invencibilidade = 1.0f;
    }
}
