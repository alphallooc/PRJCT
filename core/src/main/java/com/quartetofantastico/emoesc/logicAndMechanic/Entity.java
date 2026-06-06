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
        bounds = new Rectangle(position.x, position.y, xSize, ySize);
    }

    public float getVector2PositionX(){
        return position.x;
    }

    public float getVector2PositionY(){
        return position.y;
    }

    public void update(float delta) {
    }

    public abstract void draw (ShapeRenderer _renderer);

    public void updateBounds(){bounds.setPosition(position.x+CONST.ANIMATED_OBJECT_MARGIN, position.y+CONST.ANIMATED_OBJECT_MARGIN);}

    public boolean collides(float nextX, float nextY, Array<Entity> entities){
        Rectangle nextBounds = new Rectangle(
            nextX,
            nextY,
            xSize,
            ySize
        );
        for (Entity other : entities){
            if (other == this) continue;
            if (nextBounds.overlaps(other.bounds)) return true;
        }
        return false;
    }
    public Rectangle getBounds() { return bounds; }
}
