package com.quartetofantastico.emoesc.logicAndMechanic;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.quartetofantastico.emoesc.world.WorldMap;

public abstract class Entity{
    public Entity(Vector2 _position, float _xSize, float _ySize) {
        this.position = _position;
        this.xSize    = _xSize;
        this.ySize    = _ySize;
        bounds = new Rectangle(
            position.x + CONST.ANIMATED_OBJECT_MARGIN,
            position.y + CONST.ANIMATED_OBJECT_MARGIN,
            xSize - 2 * CONST.ANIMATED_OBJECT_MARGIN,
            ySize - 2 * CONST.ANIMATED_OBJECT_MARGIN
        );
    }
    Constants CONST = new Constants();

    protected Vector2 position;
    protected Rectangle bounds;

    protected float xSize;
    protected float ySize;
    protected float velocityY = 0f;
    protected boolean isGrounded = false;
    protected WorldMap worldMap;

    public void setWorldMap(WorldMap map) { this.worldMap = map; }

    public boolean collidesWithWalls(float nextX, float nextY) {
        if (worldMap == null) return false;
        Array<Rectangle> nearby = worldMap.getNeighbours(nextX, nextY, xSize, ySize);
        Rectangle next = new Rectangle(nextX, nextY, xSize, ySize);
        for (Rectangle wall : nearby) {
            if (next.overlaps(wall)) return true;
        }
        return false;
    }

    protected void applyGravity(float _delta){
        velocityY +=CONST.GRAVITY*_delta;
        float nextY = position.y + velocityY * _delta;

        if (collidesWithWalls(position.x, nextY)){
            if (velocityY < 0) {
                isGrounded = true;
                Array<Rectangle> nearby = worldMap.getNeighbours(position.x, nextY, xSize, ySize);
                for (Rectangle wall : nearby) {
                    Rectangle foot = new Rectangle(position.x, nextY, xSize, ySize);
                    if (foot.overlaps(wall)) {
                        position.y = wall.y + wall.height;
                        break;
                    }
                }
            }
            velocityY = 0;
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
