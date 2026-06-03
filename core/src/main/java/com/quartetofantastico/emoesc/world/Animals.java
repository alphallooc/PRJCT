package com.quartetofantastico.emoesc.world;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.quartetofantastico.emoesc.logicAndMechanic.Constants;
import com.quartetofantastico.emoesc.logicAndMechanic.Entity;

public abstract class Animals extends Entity {
    Constants CONST = new Constants();

    protected float dirX = 1f;
    protected float speed = CONST.MIN_SPEED;
    Animals(Vector2 _position, float _xSize, float _ySize ){
        super(_position, Constants.SCALE, Constants.SCALE);
        this.position = _position;
        this.xSize = _xSize;
        this.ySize = _ySize;
    }
    @Override public void update(float _delta){
        applyGravity(_delta);

        if (!isGrounded) return;

        float nextX = position.x + dirX * speed * _delta;

        if (collidesWithWalls(nextX, position.y)) {          // ← era (..., walls)
            dirX = -dirX;
            return;
        }

        float ledgeCheckX = (dirX > 0)
            ? position.x + xSize + 1
            : position.x - 1;

        if (!collidesWithWalls(ledgeCheckX, position.y - 1)) { // ← era (..., walls)
            dirX = -dirX;
            return;
        }
        position.x=nextX;
        updateBounds();
    }

    public static abstract class Hostil extends Animals {
        Hostil(Vector2 _position, float _xSize, float _ySize){super(_position, _xSize, _ySize);}
        public static class Tiger extends Hostil {
            public Tiger(Vector2 _position) { super(_position, 0.8f, 0f); }

            @Override public void draw(ShapeRenderer _renderer) {
                // Corpo
                _renderer.setColor(CONST.ORANGE_COLOR);
                _renderer.rect(position.x,
                    position.y,
                    1.2f * CONST.SCALE,
                    0.5f * CONST.SCALE);

                // Cabeça
                _renderer.circle(position.x + 1.3f * CONST.SCALE,
                    position.y + 0.35f * CONST.SCALE,
                    0.25f * CONST.SCALE);

                // Listras
                _renderer.setColor(CONST.BLACK_COLOR);
                _renderer.rect(position.x + 0.2f * CONST.SCALE,
                    position.y,
                    0.05f * CONST.SCALE,
                    0.5f * CONST.SCALE);
                _renderer.rect(position.x + 0.5f * CONST.SCALE,
                    position.y,
                    0.05f * CONST.SCALE,
                    0.5f * CONST.SCALE);

                // Olho
                _renderer.setColor(CONST.WHITE_COLOR);
                _renderer.circle(position.x + 1.35f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.05f * CONST.SCALE);
            }
        }

        public static class Crocodile extends Hostil {
            public Crocodile(Vector2 _position) { super(_position, 10, 2); }

            @Override public void draw(ShapeRenderer _renderer) {
                _renderer.set(ShapeRenderer.ShapeType.Filled);
                _renderer.setColor(CONST.GREEN_COLOR);

                // Corpo (Lógico: 1.2 unidades de largura, 0.4 de altura * escala)
                _renderer.rect(position.x+7,
                    position.y+5, 1*CONST.SCALE,
                    0.2f * CONST.SCALE);

                // Cabeça (Lógico: raio 0.2 * escala)
                _renderer.circle(position.x + (1.3f * CONST.SCALE),
                    position.y + (0.2f * CONST.SCALE),
                    0.2f * CONST.SCALE);

                // Olho
                _renderer.setColor(CONST.WHITE_COLOR);
                _renderer.circle(position.x + (1.4f * CONST.SCALE),
                    position.y + (0.3f * CONST.SCALE),
                    0.05f * CONST.SCALE);
            }
        }
        public static class Leopard extends Hostil {
            public Leopard(Vector2 _position) {super(_position, 1.2f, 0.5f);}

            @Override public void draw(ShapeRenderer _renderer) {
                // Tronco
                _renderer.setColor(CONST.YELLOW_COLOR);
                _renderer.rect(position.x,
                    position.y + 0.25f * CONST.SCALE,
                    1.35f * CONST.SCALE,
                    0.5f * CONST.SCALE);

                // Cabeça
                _renderer.circle(position.x + 1.5f * CONST.SCALE,
                    position.y + 0.6f * CONST.SCALE,
                    0.24f * CONST.SCALE);

                // Membros
                _renderer.rect(position.x + 0.15f * CONST.SCALE,
                    position.y,
                    0.1f * CONST.SCALE,
                    0.25f * CONST.SCALE);
                _renderer.rect(position.x + 0.45f * CONST.SCALE,
                    position.y,
                    0.1f * CONST.SCALE,
                    0.25f * CONST.SCALE);
                _renderer.rect(position.x + 0.9f * CONST.SCALE,
                    position.y,
                    0.1f * CONST.SCALE,
                    0.25f * CONST.SCALE);
                _renderer.rect(position.x + 1.15f * CONST.SCALE,
                    position.y,
                    0.1f * CONST.SCALE,
                    0.25f * CONST.SCALE);

                // Manchas
                _renderer.setColor(CONST.BLACK_COLOR);
                _renderer.circle(position.x + 0.35f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.05f * CONST.SCALE);
                _renderer.circle(position.x + 0.75f * CONST.SCALE,
                    position.y + 0.3f * CONST.SCALE,
                    0.05f * CONST.SCALE);
                _renderer.circle(position.x + 1.05f * CONST.SCALE,
                    position.y + 0.55f * CONST.SCALE,
                    0.05f * CONST.SCALE);
            }
        }
    }

    public static abstract class Restricted extends Animals {
        public Restricted(Vector2 _position, float _xSize, float _ySize) { super(_position, _xSize, _ySize); }
        public static class Gorilla extends Restricted {
            public Gorilla(Vector2 _position) {super(_position, 1.2f, 0.5f);}

            @Override public void draw(ShapeRenderer _renderer) {
                //corpo
                _renderer.setColor(CONST.GRAY_COLOR);
                _renderer.rect(position.x,
                    position.y,
                    xSize * CONST.SCALE,
                    ySize * CONST.SCALE);

                //cabeça
                _renderer.circle(position.x + 1.3f * CONST.SCALE,
                    position.y, +0.35f * CONST.SCALE);

                //orelhas
                _renderer.setColor(CONST.DARK_GRAY_COLOR);
                _renderer.triangle(position.x + 1.4f * CONST.SCALE,
                    position.y + 0.6f * CONST.SCALE,
                    position.x + 1.5f * CONST.SCALE,
                    position.y + 0.9f * CONST.SCALE,
                    position.x + 1.6f * CONST.SCALE,
                    position.y + 0.6f * CONST.SCALE);

                //olhos
                _renderer.setColor(CONST.WHITE_COLOR);
                _renderer.circle(position.x + 1.35f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.05f * CONST.SCALE);

                //pupila
                _renderer.setColor(CONST.BLACK_COLOR);
                _renderer.circle(position.x + 1.37f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.02f * CONST.SCALE);

                //nariz
                _renderer.circle(position.x + 1.55f * CONST.SCALE,
                    position.y + 0.3f * CONST.SCALE,
                    0.04f * CONST.SCALE);

                //pernas
                _renderer.setColor(CONST.GRAY_COLOR);
                _renderer.rect(position.x + 0.2f * CONST.SCALE,
                    position.y - 0.2f * CONST.SCALE,
                    0.1f * CONST.SCALE,
                    0.2f * CONST.SCALE);
                _renderer.rect(position.x + 0.5f * CONST.SCALE,
                    position.y - 0.2f * CONST.SCALE,
                    0.1f * CONST.SCALE,
                    0.2f * CONST.SCALE);
                _renderer.rect(position.x + 0.8f * CONST.SCALE,
                    position.y - 0.2f * CONST.SCALE,
                    0.1f * CONST.SCALE,
                    0.2f * CONST.SCALE);
            }
        }
        public static class Elephant extends Restricted {
            public Elephant(Vector2 _position) {super(_position, 1.2f, 0.5f);}

            @Override public void draw(ShapeRenderer _renderer) {
                //corpo
                _renderer.setColor(CONST.GRAY_COLOR);
                _renderer.rect(position.x,
                    position.y,
                    xSize * CONST.SCALE,
                    ySize * CONST.SCALE);

                //cabeça
                _renderer.circle(position.x + 1.3f * CONST.SCALE,
                    position.y, +0.35f * CONST.SCALE);

                //orelhas
                _renderer.setColor(CONST.DARK_GRAY_COLOR);
                _renderer.triangle(position.x + 1.4f * CONST.SCALE,
                    position.y + 0.6f * CONST.SCALE,
                    position.x + 1.5f * CONST.SCALE,
                    position.y + 0.9f * CONST.SCALE,
                    position.x + 1.6f * CONST.SCALE,
                    position.y + 0.6f * CONST.SCALE);

                //olhos
                _renderer.setColor(CONST.WHITE_COLOR);
                _renderer.circle(position.x + 1.35f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.05f * CONST.SCALE);

                //pupila
                _renderer.setColor(CONST.BLACK_COLOR);
                _renderer.circle(position.x + 1.37f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.02f * CONST.SCALE);

                //nariz
                _renderer.circle(position.x + 1.55f * CONST.SCALE,
                    position.y + 0.3f * CONST.SCALE,
                    0.04f * CONST.SCALE);

                //pernas
                _renderer.setColor(CONST.GRAY_COLOR);
                _renderer.rect(position.x + 0.2f * CONST.SCALE,
                    position.y - 0.2f * CONST.SCALE,
                    0.1f * CONST.SCALE,
                    0.2f * CONST.SCALE);
                _renderer.rect(position.x + 0.5f * CONST.SCALE,
                    position.y - 0.2f * CONST.SCALE,
                    0.1f * CONST.SCALE,
                    0.2f * CONST.SCALE);
                _renderer.rect(position.x + 0.8f * CONST.SCALE,
                    position.y - 0.2f * CONST.SCALE,
                    0.1f * CONST.SCALE,
                    0.2f * CONST.SCALE);
            }
        }
        public static class Giraffe extends Restricted {
            public Giraffe(Vector2 _position) {super(_position, 1.2f, 0.5f);}

            @Override public void draw(ShapeRenderer _renderer) {
                //corpo
                _renderer.setColor(CONST.GRAY_COLOR);
                _renderer.rect(position.x,
                    position.y,
                    xSize * CONST.SCALE,
                    ySize * CONST.SCALE);

                //cabeça
                _renderer.circle(position.x + 1.3f * CONST.SCALE,
                    position.y, +0.35f * CONST.SCALE);

                //orelhas
                _renderer.setColor(CONST.DARK_GRAY_COLOR);
                _renderer.triangle(position.x + 1.4f * CONST.SCALE,
                    position.y + 0.6f * CONST.SCALE,
                    position.x + 1.5f * CONST.SCALE,
                    position.y + 0.9f * CONST.SCALE,
                    position.x + 1.6f * CONST.SCALE,
                    position.y + 0.6f * CONST.SCALE);

                //olhos
                _renderer.setColor(CONST.WHITE_COLOR);
                _renderer.circle(position.x + 1.35f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.05f * CONST.SCALE);

                //pupila
                _renderer.setColor(CONST.BLACK_COLOR);
                _renderer.circle(position.x + 1.37f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.02f * CONST.SCALE);

                //nariz
                _renderer.circle(position.x + 1.55f * CONST.SCALE,
                    position.y + 0.3f * CONST.SCALE,
                    0.04f * CONST.SCALE);

                //pernas
                _renderer.setColor(CONST.GRAY_COLOR);
                _renderer.rect(position.x + 0.2f * CONST.SCALE,
                    position.y - 0.2f * CONST.SCALE,
                    0.1f * CONST.SCALE,
                    0.2f * CONST.SCALE);
                _renderer.rect(position.x + 0.5f * CONST.SCALE,
                    position.y - 0.2f * CONST.SCALE,
                    0.1f * CONST.SCALE,
                    0.2f * CONST.SCALE);
                _renderer.rect(position.x + 0.8f * CONST.SCALE,
                    position.y - 0.2f * CONST.SCALE,
                    0.1f * CONST.SCALE,
                    0.2f * CONST.SCALE);
            }
        }
    }
}
//essa classe é responsável pelos npcs movidos por IA;
