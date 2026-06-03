package com.quartetofantastico.emoesc.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.quartetofantastico.emoesc.logicAndMechanic.Constants;

public class WorldMap {

    private final int[][] tiles;   // 0 = vazio, 1 = sólido
    private final int cols, rows;
    public  final float tileSize;

    private WorldMap(int[][] tiles, int cols, int rows, float tileSize) {
        this.tiles    = tiles;
        this.cols     = cols;
        this.rows     = rows;
        this.tileSize = tileSize;
    }

    public static WorldMap load(String path, float tileSize) {
        String[] lines = Gdx.files.internal(path).readString().split("\\b?\\n");
        int rows = lines.length;
        int cols = 0;
        for (String l : lines) cols = Math.max(cols, l.length());

        int[][] tiles = new int[cols][rows];
        for (int row = 0; row < rows; row++) {
            String line = lines[row];
            for (int col = 0; col < line.length(); col++) {
                // Y invertido: linha 0 do ficheiro = topo do mundo
                tiles[col][rows - 1 - row] = (line.charAt(col) == '#') ? 1 : 0;
            }
        }
        return new WorldMap(tiles, cols, rows, tileSize);
    }

    public boolean isSolid(int col, int row) {
        if (col < 0 || col >= cols || row < 0 || row >= rows) return true; // borda = sólido
        return tiles[col][row] == 1;
    }

    public int toCol(float worldX) { return (int)(worldX / tileSize); }
    public int toRow(float worldY) { return (int)(worldY / tileSize); }

    public Rectangle getTileRect(int col, int row) {
        return new Rectangle(col * tileSize, row * tileSize, tileSize, tileSize);
    }

    public Array<Rectangle> getNeighbours(float x, float y, float w, float h) {
        Array<Rectangle> result = new Array<>();
        int minCol = toCol(x)     - 1;
        int maxCol = toCol(x + w) + 1;
        int minRow = toRow(y)     - 1;
        int maxRow = toRow(y + h) + 1;

        for (int a = minCol; a <= maxCol; a++) {
            for (int b = minRow; b <= maxRow; b++) {
                if (isSolid(a, b)) result.add(getTileRect(a, b));
            }
        }
        return result;
    }

    public void draw(ShapeRenderer renderer) {
        Constants CONST = new Constants();
        renderer.setColor(CONST.WHITE_COLOR);
        for (int a = 0; a < cols; a++) {
            for (int b = 0; b < rows; b++) {
                if (tiles[a][b] == 1) {
                    renderer.rect(a * tileSize, b * tileSize, tileSize, tileSize);
                }
            }
        }
    }
}
