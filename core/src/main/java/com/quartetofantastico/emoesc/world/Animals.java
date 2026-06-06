package com.quartetofantastico.emoesc.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.quartetofantastico.emoesc.logicAndMechanic.Constants;
import com.quartetofantastico.emoesc.logicAndMechanic.Entity;
import java.util.Random;
import com.badlogic.gdx.utils.Array;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Animals extends Entity {
    Constants CONST = new Constants();
    Rectangle bounds;

    Animals(Vector2 _position, float _xSize, float _ySize) {
        super(_position, _xSize, _ySize);
        this.position = _position;
        this.xSize = _xSize;
        this.ySize = _ySize;

        // CORREÇÃO: Definir uma hitbox quadrada baseada no menor tamanho padrão estável para os corredores
        float tamanhoHitbox = 0.5f * Constants.SCALE;
        this.bounds = new Rectangle(_position.x, _position.y, tamanhoHitbox, tamanhoHitbox);
        Random random = new Random();
        int dir = random.nextInt(4);
        mudarDirecao(dir);
    }

    public float velocidade = 80f;
    public float dirX = 1f, dirY = 0f;
    private Random random = new Random();
    public Array<Rectangle> walls = new Array<>();
    float tamanho;

    private void mudarDirecao(int dir) {
        switch (dir) {
            case 0: dirX = 1;  dirY = 0;  break; // Direita
            case 1: dirX = -1; dirY = 0;  break; // Esquerda
            case 2: dirX = 0;  dirY = 1;  break; // Cima
            case 3: dirX = 0;  dirY = -1; break; // Baixo
        }
    }

    @Override
    public void updateBounds() {
        bounds.setPosition(position.x, position.y);
    }

    @Override
    public void update(float delta) {
        tamanho = bounds.width;
        boolean colidiuX = false;
        boolean colidiuY = false;

        // 1. TENTATIVA DE MOVIMENTO NO EIXO X
        if (dirX != 0) {
            float nextX = position.x + dirX * velocidade * delta;
            Rectangle nextBoundsX = new Rectangle(nextX, position.y, tamanho, tamanho);
            Rectangle wallColidida = null;

            for (Rectangle wall : walls) {
                if (nextBoundsX.overlaps(wall)) {
                    wallColidida = wall;
                    break;
                }
            }

            if (wallColidida == null) {
                position.x = nextX;
            } else {
                colidiuX = true;
                // RECUO SEGURO: Afasta o animal da parede imediatamente para evitar colagem por píxel
                if (dirX > 0) {
                    position.x = wallColidida.x - tamanho - 0.2f;
                } else {
                    position.x = wallColidida.x + wallColidida.width + 0.2f;
                }
            }
        }

        // 2. TENTATIVA DE MOVIMENTO NO EIXO Y
        if (dirY != 0) {
            float nextY = position.y + dirY * velocidade * delta;
            Rectangle nextBoundsY = new Rectangle(position.x, nextY, tamanho, tamanho);
            Rectangle wallColidida = null;

            for (Rectangle wall : walls) {
                if (nextBoundsY.overlaps(wall)) {
                    wallColidida = wall;
                    break;
                }
            }

            if (wallColidida == null) {
                position.y = nextY;
            } else {
                colidiuY = true;
                // RECUO SEGURO
                if (dirY > 0) {
                    position.y = wallColidida.y - tamanho - 0.2f;
                } else {
                    position.y = wallColidida.y + wallColidida.height + 0.2f;
                }
            }
        }

        // 3. SE BATEU NUMA PAREDE, PROCURA UMA NOVA DIREÇÃO VÁLIDA
        if (colidiuX || colidiuY) {
            int tentativas = 0;
            boolean direcaoValida = false;

            while (!direcaoValida && tentativas < 15) {
                int novaDir = random.nextInt(4);
                mudarDirecao(novaDir);

                float checkX = position.x + dirX * velocidade * delta;
                float checkY = position.y + dirY * velocidade * delta;
                Rectangle checkBounds = new Rectangle(checkX, checkY, tamanho, tamanho);

                boolean colideNovaDirecao = false;
                for (Rectangle wall : walls) {
                    if (checkBounds.overlaps(wall)) {
                        colideNovaDirecao = true;
                        break;
                    }
                }

                if (!colideNovaDirecao) {
                    direcaoValida = true;
                }
                tentativas++;
            }
        }

        updateBounds();
    }


    public static abstract class Hostil extends Animals {
        Hostil(Vector2 _position, float _xSize, float _ySize){super(_position, _xSize, _ySize);}

        public abstract int getDano();

        public static class Tiger extends Hostil {
            private static Texture tigerTexture;
            private static boolean textureLoaded = false;

            public Tiger(Vector2 _position) {
                super(_position, 0.5f, 0.5f);

                if (!textureLoaded) {
                    tigerTexture = new Texture(Gdx.files.internal("ui/Images2.0/tigre-no-body.png"));
                    textureLoaded = true;
                }

                bounds.setSize(bounds.width * 2.6f, bounds.height * 2.6f);
            }

            @Override
            public int getDano() {
                return 30;
            }

            @Override
            public void draw(ShapeRenderer _renderer) {
                // Vazio - NÃO desenha mais o quadrado amarelo
            }

            public void drawSprite(SpriteBatch batch) {
                if (tigerTexture != null) {
                    batch.draw(tigerTexture,
                        position.x,
                        position.y,
                        bounds.width,
                        bounds.height);
                }
            }

            public static void disposeTexture() {
                if (tigerTexture != null) {
                    tigerTexture.dispose();
                    tigerTexture = null;
                    textureLoaded = false;
                }
            }
        }

        public static class Crocodile extends Hostil {

                private static Texture textura = new Texture("sprites/crocodile-no-bg.png");
                private SpriteBatch spriteBatch = new SpriteBatch();

            public Crocodile(Vector2 _position) {
                super(_position, 0.5f, 0.5f);

                bounds.setSize(
                    bounds.width * 3f,
                    bounds.height * 3f
                );
            }                public int getDano() { return 25; }

                @Override
                public void draw(ShapeRenderer _renderer) {
                    // deixa vazio
                }

                public void drawSprite(SpriteBatch batch) {
                    batch.draw(textura, position.x, position.y, tamanho, tamanho);
                }
            }

        public static class Leopard extends Hostil {
            public Leopard(Vector2 _position) {super(_position, 0.5f, 0.5f);}
            public int getDano() { return 20; }

            @Override public void draw(ShapeRenderer _renderer) {
                _renderer.setColor(CONST.YELLOW_COLOR); // Amarelo
                _renderer.rect(position.x, position.y, tamanho, tamanho);
            }
        }
    }

    public static abstract class Friendly extends Animals {
        public Friendly(Vector2 _position, float _xSize, float _ySize) { super(_position, _xSize, _ySize); }

        public static class Dog extends Friendly{
            public Dog(Vector2 _position){super(_position, 0.5f, 0.5f);}

            @Override public void draw(ShapeRenderer _renderer){
                _renderer.setColor(com.badlogic.gdx.graphics.Color.BROWN); // Castanho para o cão
                _renderer.rect(position.x, position.y, tamanho, tamanho);
            }
        }

        public static class Cat extends Friendly{
            public Cat(Vector2 _position){super(_position, 0.5f, 0.5f);}

            @Override public void draw(ShapeRenderer _renderer) {
                _renderer.setColor(CONST.GRAY_COLOR);
                _renderer.rect(position.x, position.y, 0.5f * CONST.SCALE, 0.5f * CONST.SCALE);

                _renderer.setColor(CONST.DARK_GRAY_COLOR);
                _renderer.triangle(position.x + 0.1f * CONST.SCALE, position.y + 0.5f * CONST.SCALE,
                    position.x + 0.2f * CONST.SCALE, position.y + 0.7f * CONST.SCALE,
                    position.x + 0.3f * CONST.SCALE, position.y + 0.5f * CONST.SCALE);

                _renderer.setColor(CONST.WHITE_COLOR);
                _renderer.circle(position.x + 0.35f * CONST.SCALE, position.y + 0.3f * CONST.SCALE, 0.05f * CONST.SCALE);
            }
        }

        public static class Horse extends Friendly {
            public Horse(Vector2 _position) {super(_position, 0.5f, 0.5f);}

            @Override public void draw(ShapeRenderer _renderer) {
                _renderer.setColor(CONST.GRAY_COLOR);
                _renderer.rect(position.x, position.y, 0.5f * CONST.SCALE, 0.5f * CONST.SCALE);

                _renderer.setColor(CONST.WHITE_COLOR);
                _renderer.circle(position.x + 0.35f * CONST.SCALE, position.y + 0.35f * CONST.SCALE, 0.05f * CONST.SCALE);
            }
        }
    }

    public static abstract class Restricted extends Animals {
        public Restricted(Vector2 _position, float _xSize, float _ySize) { super(_position, _xSize, _ySize); }

        public static class Gorilla extends Restricted {
            public Gorilla(Vector2 _position) {super(_position, 0.5f, 0.5f);}

            @Override public void draw(ShapeRenderer _renderer) {
                _renderer.setColor(CONST.GRAY_COLOR);
                _renderer.rect(position.x, position.y, 0.5f * CONST.SCALE, 0.5f * CONST.SCALE);
            }
        }

        public static class Elephant extends Restricted {
            public Elephant(Vector2 _position) {super(_position, 0.5f, 0.5f);}

            @Override public void draw(ShapeRenderer _renderer) {
                _renderer.setColor(CONST.GRAY_COLOR);
                _renderer.rect(position.x, position.y, 0.5f * CONST.SCALE, 0.5f * CONST.SCALE);
            }
        }

        public static class Giraffe extends Restricted {
            private static Texture giraffeTexture;
            private static boolean textureLoaded = false;

            public Giraffe(Vector2 _position) {
                super(_position, 0.5f, 0.5f);

                // Carrega a textura apenas uma vez
                if (!textureLoaded) {
                    giraffeTexture = new Texture(Gdx.files.internal("ui/Images2.0/girafa-no-bckg.png"));
                    textureLoaded = true;
                }

                // Aumenta um pouco o tamanho da hitbox para melhor visualização da girafa
                bounds.setSize(
                    bounds.width * 2.0f,   // largura
                    bounds.height * 2.3f   // altura (girafa é mais alta)
                );
            }

            @Override
            public void draw(ShapeRenderer _renderer) {
                // Deixamos vazio para não desenhar o quadrado cinza
            }

            public void drawSprite(SpriteBatch batch) {
                // Desenha a imagem da girafa
                batch.draw(giraffeTexture,
                    position.x,
                    position.y,
                    bounds.width,
                    bounds.height);
            }

            // Método dispose para limpar memória (boa prática)
            public static void disposeTexture() {
                if (giraffeTexture != null) {
                    giraffeTexture.dispose();
                }
            }
        }
    }

    public static abstract class Neutral extends Animals {
        Neutral (Vector2 _position, float _xSize, float _ySize) { super(_position, _xSize, _ySize); }

        public static class Rabbit extends Neutral {
            public Rabbit(Vector2 _position) {super(_position, 0.5f, 0.5f);}

            @Override public void draw(ShapeRenderer _renderer) {
                _renderer.setColor(CONST.GRAY_COLOR);
                _renderer.rect(position.x, position.y, 0.5f * CONST.SCALE, 0.5f * CONST.SCALE);
            }
        }

        public static class Turtle extends Neutral {
            public Turtle(Vector2 _position) {super(_position, 0.5f, 0.5f);}

            @Override public void draw(ShapeRenderer _renderer) {
                _renderer.setColor(CONST.GREEN_COLOR);
                _renderer.rect(position.x, position.y, 0.5f * CONST.SCALE, 0.5f * CONST.SCALE);
            }
        }

        public static class Bird extends Neutral {
            public Bird(Vector2 _position) {super(_position, 0.5f, 0.5f);}

            @Override public void draw(ShapeRenderer _renderer) {
                _renderer.setColor(CONST.YELLOW_COLOR);
                _renderer.rect(position.x, position.y, 0.5f * CONST.SCALE, 0.5f * CONST.SCALE);

                _renderer.setColor(CONST.WHITE_COLOR);
                _renderer.circle(position.x + 0.3f * CONST.SCALE, position.y + 0.3f * CONST.SCALE, 0.05f * CONST.SCALE);
            }
        }
    }
}
