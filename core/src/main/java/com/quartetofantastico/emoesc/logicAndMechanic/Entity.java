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
    protected float velocityY = 0f;
    protected boolean isGrounded = false;
    protected Array<Rectangle> walls = new Array<>();

    public void setWalls(Array<Rectangle> _walls){
        this.walls = _walls;
    }
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

    protected void applyGravity(float _delta){
        velocityY +=CONST.MAX_SPEED*_delta;
        float nextY = getVector2PositionY() + velocityY * _delta;

        if (collidesWithWalls(position.x, nextY, walls)){
            if (velocityY<0 )   isGrounded = true;
            for (Rectangle wall : walls){
                Rectangle foot = new Rectangle(
                    position.x+CONST.ANIMATED_OBJECT_MARGIN,
                    nextY+CONST.ANIMATED_OBJECT_MARGIN,
                    xSize - 2*CONST.ANIMATED_OBJECT_MARGIN,
                    ySize - 2*CONST.ANIMATED_OBJECT_MARGIN
                );
                if (foot.overlaps(wall)){
                    position.y = wall.y + wall.height - CONST.ANIMATED_OBJECT_MARGIN;
                    break;
                }
            }
            velocityY = 0f;
        }else{
            position.y = nextY;
            isGrounded = false;
        }
        updateBounds();
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
    }
    public boolean collidesWithWalls(float nextX, float nextY, Array<Rectangle> walls){
        Rectangle next = new Rectangle(nextX+CONST.ANIMATED_OBJECT_MARGIN,
            nextY+CONST.ANIMATED_OBJECT_MARGIN,
            xSize - 2*CONST.ANIMATED_OBJECT_MARGIN,
            ySize - 3*CONST.ANIMATED_OBJECT_MARGIN);
        for (Rectangle wall : walls){
            if (next.overlaps(wall)) return true;
        }
        return false;
    }
}
