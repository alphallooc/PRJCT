package com.quartetofantastico.emoesc.world;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.quartetofantastico.emoesc.logicAndMechanic.Constants;
import com.quartetofantastico.emoesc.logicAndMechanic.Entity;

public class Avatar extends Entity {
    Constants CONST = new Constants();

    private String name;
    private int id, jump, health;
    float tamanho = 1.0f * CONST.SCALE;// Escala aplicada conforme solicitado
    float olhoTamanho = 0.15f * CONST.SCALE;
    float speed = 50.0f; // Velocidade aumentada devido à escala do mundo
    int dirX = 0, dirY = 0;

    Array entities = new Array<>();
    Rectangle bounds;

    public Avatar(String _name, Vector2 _position) {
        super( _position, 1.0f, 1.0f);

        this.name = _name;
        this.health = 100;
        this.position.x = _position.x;
        this.position.y = _position.y;


        bounds = new Rectangle(_position.x+CONST.ANIMATED_OBJECT_MARGIN,
            _position.y+CONST.ANIMATED_OBJECT_MARGIN,
            tamanho+CONST.ANIMATED_OBJECT_MARGIN,
            tamanho-CONST.ANIMATED_OBJECT_MARGIN);
    }
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
        dirX = 0;
        dirY = 0;

        if (CONST.UP_KEY()) dirY = 1;
        if (CONST.DOWN_KEY()) dirY = -1;
        if (CONST.LEFT_KEY()) dirX = -1;
        if (CONST.RIGHT_KEY()) dirX = 1;

        move(dirX * speed * delta, dirY * speed * delta, entities);
    }
    public void move(float _nextX, float _nextY, Array<Entity> entities) {
        float nextX = position.x+_nextX;
        float nextY = position.y+_nextY;

        position.x = MathUtils.clamp(nextX,
            0,
            CONST.W_WORLD_SIZE - (1.0f * 5.0f));
        position.y = MathUtils.clamp(nextY,
            0,
            CONST.H_WORLD_SIZE - (1.0f * 5.0f));

        if (!collides(nextX, position.y, entities)) position.x = nextX;
        if (!collides(position.x, nextY, entities)) position.y = nextY;
        updateBounds();
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
}
