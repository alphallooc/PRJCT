package com.quartetofantastico.emoesc.world;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.quartetofantastico.emoesc.logicAndMechanic.Entity;
import com.quartetofantastico.emoesc.logicAndMechanic.Constants;
import java.util.Random;


public abstract class Animals extends Entity {
    public float velocidade = 50f;
    public long timer = 0;
    Constants CONST = new Constants();

    private Random random = new Random();
    public int direcaodaVelocidade = 0;

    Animals(Vector2 _position, float _xSize, float _ySize) {
        super(_position, _xSize * Constants.SCALE, _ySize * Constants.SCALE);
        this.position = _position;
        this.xSize = _xSize;  // ← sem SCALE, o draw já aplica
        this.ySize = _ySize;  // ← sem SCALE, o draw já aplica
        this.bounds = new Rectangle(
            _position.x,
            _position.y,
            _xSize * Constants.SCALE,
            _ySize * Constants.SCALE
        );
        this.timer = System.currentTimeMillis();
        this.direcaodaVelocidade = random.nextInt(4);
    }

    @Override
    public void updateBounds() {
        bounds.setPosition(position.x, position.y);
    }

    @Override
    public void update(float _deltaTime) {
        long tempoAtual = System.currentTimeMillis();

        if ((tempoAtual - timer) > 2000) {
            direcaodaVelocidade = random.nextInt(4);
            timer = tempoAtual;
        }

        float movimento = velocidade * _deltaTime;

        switch(direcaodaVelocidade) {
            case 0: position.y += movimento; break;
            case 1: position.y -= movimento; break;
            case 2: position.x -= movimento; break;
            case 3: position.x += movimento; break;
        }

        updateBounds();
    }

    public static abstract class Hostil extends Animals {
        Hostil(Vector2 _position, float _xSize, float _ySize) {
            super(_position, _xSize, _ySize);
        }

        public static class Tiger extends Hostil {
            public Tiger(Vector2 _position) {
                super(_position, 1.2f, 0.5f);
            }
            public int getDano() { return 30; }

            @Override
            public void draw(ShapeRenderer _renderer) {
                _renderer.setColor(CONST.ORANGE_COLOR);
                _renderer.rect(position.x,
                    position.y,
                    1.2f * CONST.SCALE,
                    0.5f * CONST.SCALE);

                _renderer.circle(position.x + 1.3f * CONST.SCALE,
                    position.y + 0.35f * CONST.SCALE,
                    0.25f * CONST.SCALE);

                _renderer.setColor(CONST.BLACK_COLOR);
                _renderer.rect(position.x + 0.2f * CONST.SCALE,
                    position.y,
                    0.05f * CONST.SCALE,
                    0.5f * CONST.SCALE);
                _renderer.rect(position.x + 0.5f * CONST.SCALE,
                    position.y,
                    0.05f * CONST.SCALE,
                    0.5f * CONST.SCALE);

                _renderer.setColor(CONST.WHITE_COLOR);
                _renderer.circle(position.x + 1.35f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.05f * CONST.SCALE);
            }
        }

        public static class Crocodile extends Hostil {
            public Crocodile(Vector2 _position) {
                super(_position, 1.2f, 0.5f);
            }
            public int getDano() { return 25; }

            @Override
            public void draw(ShapeRenderer _renderer) {
                _renderer.set(ShapeRenderer.ShapeType.Filled);
                _renderer.setColor(CONST.GREEN_COLOR);

                _renderer.rect(position.x + 7,
                    position.y + 5, 1 * CONST.SCALE,
                    0.2f * CONST.SCALE);

                _renderer.circle(position.x + (1.3f * CONST.SCALE),
                    position.y + (0.2f * CONST.SCALE),
                    0.2f * CONST.SCALE);

                _renderer.setColor(CONST.WHITE_COLOR);
                _renderer.circle(position.x + (1.4f * CONST.SCALE),
                    position.y + (0.3f * CONST.SCALE),
                    0.05f * CONST.SCALE);
            }
        }

        public static class Leopard extends Hostil {
            public Leopard(Vector2 _position) {
                super(_position, 1.2f, 0.5f);
            }

            public int getDano() { return 20; }

            @Override
            public void draw(ShapeRenderer _renderer) {
                _renderer.setColor(CONST.YELLOW_COLOR);
                _renderer.rect(position.x,
                    position.y + 0.25f * CONST.SCALE,
                    1.35f * CONST.SCALE,
                    0.5f * CONST.SCALE);

                _renderer.circle(position.x + 1.5f * CONST.SCALE,
                    position.y + 0.6f * CONST.SCALE,
                    0.24f * CONST.SCALE);

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

    public static abstract class Friendly extends Animals {
        public Friendly(Vector2 _position, float _xSize, float _ySize) {
            super(_position, _xSize, _ySize);
        }

        public static class Dog extends Friendly {
           public Dog(Vector2 _position) {
                super(_position, 1.2f, 0.5f);
            }

            @Override
            public void draw(ShapeRenderer _renderer) {
                _renderer.setColor(CONST.GRAY_COLOR);
                _renderer.rect(position.x,
                    position.y,
                    xSize * CONST.SCALE,
                    ySize * CONST.SCALE);

                _renderer.circle(position.x + 1.3f * CONST.SCALE,
                    position.y, +0.35f * CONST.SCALE);

                _renderer.setColor(CONST.DARK_GRAY_COLOR);
                _renderer.triangle(position.x + 1.4f * CONST.SCALE,
                    position.y + 0.6f * CONST.SCALE,
                    position.x + 1.5f * CONST.SCALE,
                    position.y + 0.9f * CONST.SCALE,
                    position.x + 1.6f * CONST.SCALE,
                    position.y + 0.6f * CONST.SCALE);

                _renderer.setColor(CONST.WHITE_COLOR);
                _renderer.circle(position.x + 1.35f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.05f * CONST.SCALE);

                _renderer.setColor(CONST.BLACK_COLOR);
                _renderer.circle(position.x + 1.37f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.02f * CONST.SCALE);

                _renderer.circle(position.x + 1.55f * CONST.SCALE,
                    position.y + 0.3f * CONST.SCALE,
                    0.04f * CONST.SCALE);

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

                _renderer.triangle(
                    position.x - 0.1f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    position.x - 0.3f * CONST.SCALE,
                    position.y + 0.6f * CONST.SCALE,
                    position.x,
                    position.y + 0.5f * CONST.SCALE
                );
            }
        }

        public static class Cat extends Friendly {
            public Cat(Vector2 _position) {
                super(_position, 1.2f, 0.5f);
            }

            @Override
            public void draw(ShapeRenderer _renderer) {
                _renderer.setColor(CONST.GRAY_COLOR);
                _renderer.rect(position.x,
                    position.y,
                    xSize * CONST.SCALE,
                    ySize * CONST.SCALE);

                _renderer.circle(position.x + 1.3f * CONST.SCALE,
                    position.y, +0.35f * CONST.SCALE);

                _renderer.setColor(CONST.DARK_GRAY_COLOR);
                _renderer.triangle(position.x + 1.4f * CONST.SCALE,
                    position.y + 0.6f * CONST.SCALE,
                    position.x + 1.5f * CONST.SCALE,
                    position.y + 0.9f * CONST.SCALE,
                    position.x + 1.6f * CONST.SCALE,
                    position.y + 0.6f * CONST.SCALE);

                _renderer.setColor(CONST.WHITE_COLOR);
                _renderer.circle(position.x + 1.35f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.05f * CONST.SCALE);

                _renderer.setColor(CONST.BLACK_COLOR);
                _renderer.circle(position.x + 1.37f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.02f * CONST.SCALE);

                _renderer.circle(position.x + 1.55f * CONST.SCALE,
                    position.y + 0.3f * CONST.SCALE,
                    0.04f * CONST.SCALE);

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

                _renderer.triangle(
                    position.x - 0.1f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    position.x - 0.3f * CONST.SCALE,
                    position.y + 0.6f * CONST.SCALE,
                    position.x,
                    position.y + 0.5f * CONST.SCALE
                );
            }
        }

        public static class Horse extends Friendly {
            public Horse(Vector2 _position) {
                super(_position, 1.2f, 0.5f);
            }

            @Override
            public void draw(ShapeRenderer _renderer) {
                _renderer.setColor(CONST.GRAY_COLOR);
                _renderer.rect(position.x,
                    position.y,
                    xSize * CONST.SCALE,
                    ySize * CONST.SCALE);

                _renderer.circle(position.x + 1.3f * CONST.SCALE,
                    position.y, +0.35f * CONST.SCALE);

                _renderer.setColor(CONST.DARK_GRAY_COLOR);
                _renderer.triangle(position.x + 1.4f * CONST.SCALE,
                    position.y + 0.6f * CONST.SCALE,
                    position.x + 1.5f * CONST.SCALE,
                    position.y + 0.9f * CONST.SCALE,
                    position.x + 1.6f * CONST.SCALE,
                    position.y + 0.6f * CONST.SCALE);

                _renderer.setColor(CONST.WHITE_COLOR);
                _renderer.circle(position.x + 1.35f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.05f * CONST.SCALE);

                _renderer.setColor(CONST.BLACK_COLOR);
                _renderer.circle(position.x + 1.37f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.02f * CONST.SCALE);

                _renderer.circle(position.x + 1.55f * CONST.SCALE,
                    position.y + 0.3f * CONST.SCALE,
                    0.04f * CONST.SCALE);

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

    public static abstract class Restricted extends Animals {
        public Restricted(Vector2 _position, float _xSize, float _ySize) {
            super(_position, _xSize, _ySize);
        }

        public static class Gorilla extends Restricted {
            public Gorilla(Vector2 _position) {
                super(_position, 1.2f, 0.5f);
            }

            @Override
            public void draw(ShapeRenderer _renderer) {
                _renderer.setColor(CONST.GRAY_COLOR);
                _renderer.rect(position.x,
                    position.y,
                    xSize * CONST.SCALE,
                    ySize * CONST.SCALE);

                _renderer.circle(position.x + 1.3f * CONST.SCALE,
                    position.y, +0.35f * CONST.SCALE);

                _renderer.setColor(CONST.DARK_GRAY_COLOR);
                _renderer.triangle(position.x + 1.4f * CONST.SCALE,
                    position.y + 0.6f * CONST.SCALE,
                    position.x + 1.5f * CONST.SCALE,
                    position.y + 0.9f * CONST.SCALE,
                    position.x + 1.6f * CONST.SCALE,
                    position.y + 0.6f * CONST.SCALE);

                _renderer.setColor(CONST.WHITE_COLOR);
                _renderer.circle(position.x + 1.35f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.05f * CONST.SCALE);

                _renderer.setColor(CONST.BLACK_COLOR);
                _renderer.circle(position.x + 1.37f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.02f * CONST.SCALE);

                _renderer.circle(position.x + 1.55f * CONST.SCALE,
                    position.y + 0.3f * CONST.SCALE,
                    0.04f * CONST.SCALE);

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
            public Elephant(Vector2 _position) {
                super(_position, 1.2f, 0.5f);
            }

            @Override
            public void draw(ShapeRenderer _renderer) {
                _renderer.setColor(CONST.GRAY_COLOR);
                _renderer.rect(position.x,
                    position.y,
                    xSize * CONST.SCALE,
                    ySize * CONST.SCALE);

                _renderer.circle(position.x + 1.3f * CONST.SCALE,
                    position.y, +0.35f * CONST.SCALE);

                _renderer.setColor(CONST.DARK_GRAY_COLOR);
                _renderer.triangle(position.x + 1.4f * CONST.SCALE,
                    position.y + 0.6f * CONST.SCALE,
                    position.x + 1.5f * CONST.SCALE,
                    position.y + 0.9f * CONST.SCALE,
                    position.x + 1.6f * CONST.SCALE,
                    position.y + 0.6f * CONST.SCALE);

                _renderer.setColor(CONST.WHITE_COLOR);
                _renderer.circle(position.x + 1.35f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.05f * CONST.SCALE);

                _renderer.setColor(CONST.BLACK_COLOR);
                _renderer.circle(position.x + 1.37f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.02f * CONST.SCALE);

                _renderer.circle(position.x + 1.55f * CONST.SCALE,
                    position.y + 0.3f * CONST.SCALE,
                    0.04f * CONST.SCALE);

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
            public Giraffe(Vector2 _position) {
                super(_position, 1.2f, 0.5f);
            }

            @Override
            public void draw(ShapeRenderer _renderer) {
                _renderer.setColor(CONST.GRAY_COLOR);
                _renderer.rect(position.x,
                    position.y,
                    xSize * CONST.SCALE,
                    ySize * CONST.SCALE);

                _renderer.circle(position.x + 1.3f * CONST.SCALE,
                    position.y, +0.35f * CONST.SCALE);

                _renderer.setColor(CONST.DARK_GRAY_COLOR);
                _renderer.triangle(position.x + 1.4f * CONST.SCALE,
                    position.y + 0.6f * CONST.SCALE,
                    position.x + 1.5f * CONST.SCALE,
                    position.y + 0.9f * CONST.SCALE,
                    position.x + 1.6f * CONST.SCALE,
                    position.y + 0.6f * CONST.SCALE);

                _renderer.setColor(CONST.WHITE_COLOR);
                _renderer.circle(position.x + 1.35f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.05f * CONST.SCALE);

                _renderer.setColor(CONST.BLACK_COLOR);
                _renderer.circle(position.x + 1.37f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.02f * CONST.SCALE);

                _renderer.circle(position.x + 1.55f * CONST.SCALE,
                    position.y + 0.3f * CONST.SCALE,
                    0.04f * CONST.SCALE);

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

    public static abstract class Neutral extends Animals {
        Neutral(Vector2 _position, float _xSize, float _ySize) {
            super(_position, _xSize, _ySize);
        }

        public static class Rabbit extends Neutral {
           public Rabbit(Vector2 _position) {
                super(_position, 1.2f, 0.5f);
            }

            @Override
            public void draw(ShapeRenderer _renderer) {
                _renderer.setColor(CONST.GRAY_COLOR);
                _renderer.rect(position.x,
                    position.y,
                    xSize * CONST.SCALE,
                    ySize * CONST.SCALE);

                _renderer.circle(position.x + 1.3f * CONST.SCALE,
                    position.y, +0.35f * CONST.SCALE);

                _renderer.setColor(CONST.DARK_GRAY_COLOR);
                _renderer.triangle(position.x + 1.4f * CONST.SCALE,
                    position.y + 0.6f * CONST.SCALE,
                    position.x + 1.5f * CONST.SCALE,
                    position.y + 0.9f * CONST.SCALE,
                    position.x + 1.6f * CONST.SCALE,
                    position.y + 0.6f * CONST.SCALE);

                _renderer.setColor(CONST.WHITE_COLOR);
                _renderer.circle(position.x + 1.35f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.05f * CONST.SCALE);

                _renderer.setColor(CONST.BLACK_COLOR);
                _renderer.circle(position.x + 1.37f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.02f * CONST.SCALE);

                _renderer.circle(position.x + 1.55f * CONST.SCALE,
                    position.y + 0.3f * CONST.SCALE,
                    0.04f * CONST.SCALE);

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

        public static class Turtle extends Neutral {
           public Turtle(Vector2 _position) {
                super(_position, 1.2f, 0.5f);
            }

            @Override
            public void draw(ShapeRenderer _renderer) {
                _renderer.setColor(CONST.GREEN_COLOR);
                _renderer.rect(position.x,
                    position.y,
                    xSize * CONST.SCALE,
                    ySize * CONST.SCALE);

                _renderer.circle(position.x + 1.3f * CONST.SCALE,
                    position.y, +0.35f * CONST.SCALE);

                _renderer.setColor(CONST.WHITE_COLOR);
                _renderer.circle(position.x + 1.35f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.05f * CONST.SCALE);

                _renderer.setColor(CONST.BLACK_COLOR);
                _renderer.circle(position.x + 1.37f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.02f * CONST.SCALE);

                _renderer.circle(position.x + 1.55f * CONST.SCALE,
                    position.y + 0.3f * CONST.SCALE,
                    0.04f * CONST.SCALE);

                _renderer.setColor(CONST.GREEN_COLOR);
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

        public static class Bird extends Neutral {
           public Bird(Vector2 _position) {
                super(_position, 1.2f, 0.5f);
            }

            @Override
            public void draw(ShapeRenderer _renderer) {
                _renderer.setColor(CONST.YELLOW_COLOR);
                _renderer.rect(position.x,
                    position.y,
                    xSize * CONST.SCALE,
                    ySize * CONST.SCALE);

                _renderer.circle(position.x + 1.3f * CONST.SCALE,
                    position.y, +0.35f * CONST.SCALE);

                _renderer.setColor(CONST.WHITE_COLOR);
                _renderer.circle(position.x + 1.35f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.05f * CONST.SCALE);

                _renderer.setColor(CONST.BLACK_COLOR);
                _renderer.circle(position.x + 1.37f * CONST.SCALE,
                    position.y + 0.4f * CONST.SCALE,
                    0.02f * CONST.SCALE);

                _renderer.circle(position.x + 1.55f * CONST.SCALE,
                    position.y + 0.3f * CONST.SCALE,
                    0.04f * CONST.SCALE);

                _renderer.setColor(CONST.YELLOW_COLOR);
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
