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
    float speed = CONST.MAX_SPEED; // Velocidade aumentada devido à escala do mundo
    int dirX = 0, dirY = 0;
    private float velX = 0f;
    private float velY = 0f;
    private boolean jumpAction;
    private float jumpTime=0;
    Array <Entity> entities = new Array<>();

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
    @Override
    public void update(float delta) {
        // ler entrada
        int inputX = 0;
        if (CONST.UP_KEY()) {} // manter se precisar pulo por tecla UP
        if (CONST.LEFT_KEY()) inputX = -1;
        if (CONST.RIGHT_KEY()) inputX = 1;

        // aceleração horizontal
        if (inputX != 0) {
            velX += inputX * CONST.ACCELERATION * delta;
            // clamp da velocidade
            if (velX > CONST.MAX_SPEED) velX = CONST.MAX_SPEED;
            if (velX < -CONST.MAX_SPEED) velX = -CONST.MAX_SPEED;
        } else {
            // aplicar atrito para reduzir a velocidade até 0
            if (velX > 0f) {
                velX -= CONST.FRICTION * delta;
                if (velX < 0f) velX = 0f;
            } else if (velX < 0f) {
                velX += CONST.FRICTION * delta;
                if (velX > 0f) velX = 0f;
            }
        }

        // pulo (W ou UP ou SPACE)
        boolean wantJump = CONST.UP_KEY();

        if (wantJump && isGrounded) {
            // iniciar pulo
            velY = CONST.JUMP_VELOCITY; // note: gravidade negativa pode exigir sinal trocado; ajuste conforme seu sistema
            isGrounded = false;
            jumpAction = true;
            jumpTime = 0f;
        }

        // pulo variável: enquanto segurado e jumpTime < maxJumpTime, reduz efeitos da gravidade
        if (jumpAction) {
            jumpTime += delta;
            if (!wantJump || jumpTime > CONST.MAX_JUMP_TIME) {
                jumpAction = false;
            } else {
                // Optional: aplicar um pequeno incremento para manter o impulso
                // velY = Math.max(velY, CONST.JUMP_VELOCITY * 0.6f);
            }
        }

        // aplicar gravidade
        velY += CONST.GRAVITY * delta;

        // calcular deslocamento
        float dx = velX * delta;
        float dy = velY * delta;

        // movimentação com checagem de colisões por eixo (use seus arrays de walls)
        // X axis
        float nextX = position.x + dx;
        if (!collidesWithWalls(nextX, position.y, walls) && !collides(nextX, position.y, entities)) {
            position.x = MathUtils.clamp(nextX, 0f, CONST.W_WORLD_SIZE - xSize);
        } else {
            // colisão em X: zerar velocidade X e ajustar para encostar
            velX = 0f;
            // para ajuste fino, pode iterar walls e posicionar colocação exata
        }

        // Y axis
        float nextY = position.y + dy;
        if (!collidesWithWalls(position.x, nextY, walls) && !collides(position.x, nextY, entities)) {
            position.y = MathUtils.clamp(nextY, 0f, CONST.H_WORLD_SIZE - ySize);
            isGrounded = false;
        } else {
            // colisão vertical: vindo de cima -> pousou
            if (velY < 0f) { // se velY negativo representa descida (ajuste conforme sinal)
                isGrounded = true;
            }
            velY = 0f;
            // ajustar position.y para encostar na superfície:
            for (Rectangle wall : walls) {
                Rectangle nextBounds = new Rectangle(position.x + CONST.ANIMATED_OBJECT_MARGIN,
                    nextY + CONST.ANIMATED_OBJECT_MARGIN,
                    xSize - 2*CONST.ANIMATED_OBJECT_MARGIN,
                    ySize - 2*CONST.ANIMATED_OBJECT_MARGIN);
                if (nextBounds.overlaps(wall)) {
                    // se estava caindo, encostar em cima do wall
                    if (position.y >= wall.y + wall.height) {
                        position.y = wall.y + wall.height - CONST.ANIMATED_OBJECT_MARGIN;
                    } else {
                        // ajuste para baixo (quando bate a cabeça)
                        position.y = wall.y - ySize + CONST.ANIMATED_OBJECT_MARGIN;
                    }
                    break;
                }
            }
        }

        updateBounds();
    }
    public void move(float _dx, float _dy, Array<Entity> entities) {
        float nextX = position.x + _dx;
        float nextY = position.y + _dy;

        // Checar colisão com paredes e entidades no eixo X
        boolean collisionX = collidesWithWalls(nextX, position.y, walls) || collides(nextX, position.y, entities);
        if (!collisionX) {
            position.x = MathUtils.clamp(nextX, 0f, CONST.W_WORLD_SIZE - this.tamanho);
        } else {
            // opcional: ajustar posição para "encostar" na parede ao invés de pular pra dentro dela
        }

        // Checar colisão com paredes e entidades no eixo Y
        boolean collisionY = collidesWithWalls(position.x, nextY, walls) || collides(position.x, nextY, entities);
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
