package com.quartetofantastico.emoesc.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.quartetofantastico.emoesc.logicAndMechanic.Constants;

import java.util.Random;

public class WallStructs {

    public static Array<Rectangle> loadFromFile(String path, float scale) {

        Array<Rectangle> walls = new Array<>();

        String[] lines = Gdx.files.internal(path)
            .readString()
            .split("\\r?\\n");

        for (int row = 0; row < lines.length; row++) {

            String line = lines[row];

            for (int col = 0; col < line.length(); col++) {

                if (line.charAt(col) == '#') {

                    float x = col * scale;
                    float y = (lines.length - 1 - row) * scale;

                    walls.add(new Rectangle(x, y, scale, scale));
                }
            }
        }

        return walls;
    }

    public static Array<Rectangle> loadFromFile(String path) {
        return loadFromFile(path, Constants.SCALE);
    }

    /**
     * Procura a posição marcada com 'S' no mapa.
     */
    public static Vector2 getStartPosition(String path, float scale) {

        String[] lines = Gdx.files.internal(path)
            .readString()
            .split("\\r?\\n");

        for (int row = 0; row < lines.length; row++) {

            String line = lines[row];

            for (int col = 0; col < line.length(); col++) {

                if (line.charAt(col) == 'S') {

                    float x = col * scale;
                    float y = (lines.length - 1 - row) * scale;

                    return new Vector2(x, y);
                }
            }
        }

        return new Vector2(scale, scale);
    }

    public static Vector2 getStartPosition(String path) {
        return getStartPosition(path, Constants.SCALE);
    }

    /**
     * Procura a posição marcada com 'E' no mapa.
     */
    public static Vector2 getExitPosition(String path, float scale) {

        String[] lines = Gdx.files.internal(path)
            .readString()
            .split("\\r?\\n");

        for (int row = 0; row < lines.length; row++) {

            String line = lines[row];

            for (int col = 0; col < line.length(); col++) {

                if (line.charAt(col) == 'E') {

                    float x = col * scale;
                    float y = (lines.length - 1 - row) * scale;

                    return new Vector2(x, y);
                }
            }
        }

        return null;
    }

    public static Vector2 getExitPosition(String path) {
        return getExitPosition(path, Constants.SCALE);
    }

    /**
     * Encontra uma posição aleatória livre de paredes.
     */
    public static Vector2 encontrarPosicaoLivre(
        Array<Rectangle> walls,
        float scale) {

        Random random = new Random();

        for (int i = 0; i < 1000; i++) {

            float x = random.nextInt(20) * scale * 2 + scale;
            float y = random.nextInt(20) * scale * 2 + scale;

            Rectangle test = new Rectangle(x, y, scale, scale);

            boolean livre = true;

            for (Rectangle wall : walls) {

                if (test.overlaps(wall)) {
                    livre = false;
                    break;
                }
            }

            if (livre) {
                return new Vector2(x, y);
            }
        }

        return new Vector2(scale, scale);
    }
}
