package com.quartetofantastico.emoesc.world;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.quartetofantastico.emoesc.logicAndMechanic.Constants;
import com.quartetofantastico.emoesc.logicAndMechanic.Entity;

public class Avatar extends Entity {

    private String name;
    private int health;
    float tamanho = Constants.SCALE;// Escala
    float olhoTamanho = 0.15f * Constants.SCALE;
    int dirX = 0, dirY = 0;
    private float velX = 0f;
    private float velY = 0f;
    private boolean jumpAction;
    private float jumpTime=0;
    Array <Entity> entities = new Array<>();

    Constants CONST = new Constants();

    public Avatar(String _name, Vector2 _position) {
        super(_position, Constants.SCALE, Constants.SCALE);

        this.tamanho = Constants.SCALE;
        this.name = _name;
        this.health = 100;
        this.position.x = _position.x;
        this.position.y = _position.y;
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
    @Override public void update(float delta) {
        // ler entrada
        int inputX = 0;
        if (CONST.UP_KEY()) {} // manter se precisar pulo por tecla UP
        if (CONST.LEFT_KEY()) inputX = -1;
        if (CONST.RIGHT_KEY()) inputX = 1;

        // aceleração horizontal
        if (inputX != 0) {
            velX += inputX * CONST.ACCELERATION * delta;
            if (velX > CONST.MAX_SPEED) velX = CONST.MAX_SPEED;
            if (velX < -CONST.MAX_SPEED) velX = -CONST.MAX_SPEED;
        } else {
            if (velX > 0f) {
                velX -= CONST.FRICTION * delta;
                if (velX < 0f) velX = 0f;
            } else if (velX < 0f) {
                velX += CONST.FRICTION * delta;
                if (velX > 0f) velX = 0f;
            }
        }

        // pulo (W ou UP)
        boolean wantJump = CONST.UP_KEY();

        if (wantJump && isGrounded) {
            velocityY = CONST.JUMP_VELOCITY;  // ← MUDOU: velY → velocityY
            isGrounded = false;
            jumpAction = true;
            jumpTime = 0f;
        }

        // pulo variável
        if (jumpAction) {
            jumpTime += delta;
            if (!wantJump || jumpTime > CONST.MAX_JUMP_TIME) {
                jumpAction = false;
            }
        }

        // aplicar gravidade
        velocityY += CONST.GRAVITY * delta;

        // calcular deslocamento
        float dx = velX * delta;
        float dy = velocityY * delta;

        // X axis (movimento horizontal)
        float nextX = position.x + dx;
        if (!collidesWithWalls(nextX, position.y) && !collides(nextX, position.y, entities)) {
            position.x = MathUtils.clamp(nextX, 0f, CONST.W_WORLD_SIZE - xSize);
        } else {
            velX = 0f;
        }

        // Y axis (movimento vertical com gravidade)
        float nextY = position.y + dy;
        if (!collidesWithWalls(position.x, nextY) && !collides(position.x, nextY, entities)) {
            position.y = MathUtils.clamp(nextY, 0f, CONST.H_WORLD_SIZE - ySize);
            isGrounded = false;
        } else {
            // Colidiu com algo
            if (velocityY < 0f) {  // ← MUDOU: velY → velocityY
                isGrounded = true;
            }
            velocityY = 0f;

            // Ajustar para encostar na parede
            if (worldMap != null) {  // ← PROTEÇÃO CONTRA NULL
                Array<Rectangle> nearby = worldMap.getNeighbours(position.x, nextY, xSize, ySize);
                for (Rectangle wall : nearby) {
                    Rectangle nextBounds = new Rectangle(
                        position.x,
                        nextY,
                        xSize,
                        ySize
                    );
                    if (nextBounds.overlaps(wall)) {
                        if (position.y >= wall.y + wall.height) {
                            position.y = wall.y + wall.height;
                        } else {
                            position.y = wall.y - ySize;
                        }
                        break;
                    }
                }
            }
        }
        updateBounds();
    }
    public void move(float _dx, float _dy, Array<Entity> entities) {
        float nextX = position.x + _dx;
        float nextY = position.y + _dy;

        // Checar colisão com paredes e entidades no eixo X
        boolean collisionX = collidesWithWalls(nextX, position.y) || collides(nextX, position.y, entities);
        if (!collisionX) {
            position.x = MathUtils.clamp(nextX, 0f, CONST.W_WORLD_SIZE - this.tamanho);
        } else {
            // opcional: ajustar posição para "encostar" na parede ao invés de pular pra dentro dela
        }

        // Checar colisão com paredes e entidades no eixo Y
        boolean collisionY = collidesWithWalls(position.x, nextY) || collides(position.x, nextY, entities);
        if (!collisionY) {
            position.y = MathUtils.clamp(nextY, 0f, CONST.H_WORLD_SIZE - this.tamanho);
        } else {
            // opcional: mesma lógica de "encostar" na parede
        }

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

    public float getWidth() { return this.tamanho; }
    public float getHeight() { return this.tamanho; }
}
