package com.quartetofantastico.emoesc.logicAndMechanic;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public abstract class Entity{
    Constants CONST = new Constants();

    protected Vector2 position;
    protected Rectangle bounds;

    protected float xSize;
    protected float ySize;

    public Entity (Vector2 _position, float _xSize, float _ySize){
        this.position = _position;
        this.xSize = _xSize;
        this.ySize = _ySize;

        bounds = new Rectangle(
            position.x + CONST.ANIMATED_OBJECT_MARGIN,
            position.y + CONST.ANIMATED_OBJECT_MARGIN,
            xSize - 2 * CONST.ANIMATED_OBJECT_MARGIN,
            ySize - 2 * CONST.ANIMATED_OBJECT_MARGIN
        );
    }

    public float getVector2PositionX(){
        return position.x;
    }

    public float getVector2PositionY(){
        return position.y;
    }
    public abstract void update(float delta);
    public abstract void draw (ShapeRenderer _renderer);

    public void updateBounds(){bounds.setPosition(position.x+CONST.ANIMATED_OBJECT_MARGIN, position.y+CONST.ANIMATED_OBJECT_MARGIN);}

    public boolean collides(float nextX, float nextY, Array<Entity> entities){
        Rectangle nextBounds = new Rectangle(nextX+CONST.ANIMATED_OBJECT_MARGIN,
            nextY+CONST.ANIMATED_OBJECT_MARGIN,
            xSize-CONST.ANIMATED_OBJECT_MARGIN,
            ySize-CONST.ANIMATED_OBJECT_MARGIN);

        for (Entity other : entities){
            if (other == this)  continue;
            if (nextBounds.overlaps(other.bounds)) return true;
        }
        return false;
    };
}
