package com.quartetofantastico.emoesc.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.quartetofantastico.emoesc.logicAndMechanic.Constants;

import static com.badlogic.gdx.graphics.g2d.ParticleEmitter.SpawnShape.line;

public class WallStructs{
    public static Array<Rectangle> loadFromFile(String path, float scale){
        Array<Rectangle> walls = new Array<>();
        Constants CONST = new Constants();
        String[] lines = Gdx.files.internal(path).readString().split("\n");
        for (int row = 0; row < lines.length; row++){
            String line = lines[row];
            for (int col = 0; col < line.length(); col++){
                if(line.charAt(col) == '#'){
                    float X = col*scale;
                    float Y = (lines.length - 1 - row)*scale;
                    walls.add(new Rectangle(X, Y, scale, scale));
                }
            }
        }
        return walls;
    }
    public static Array<Rectangle> loadFromFile(String path){
        Constants CONST = new Constants();
        return loadFromFile(path, CONST.SCALE);
    }
}
