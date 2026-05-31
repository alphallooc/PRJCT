package com.quartetofantastico.emoesc.world;

import com.quartetofantastico.emoesc.logicAndMechanic.Constants;

import com.quartetofantastico.emoesc.logicAndMechanic.Entity;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Frutas extends Entity {
    public enum FruitType{
        MAÇA, BANANA, LARANJA, UVA
    }
    private FruitType type;
    private int healAmount;
    private boolean collected = false;

    Constants CONST = new Constants();

    float tamanho = 20f;

    public Frutas(FruitType _type, Vector2 _position){
        super(_position, 20f, 20f);
        this.type = _type;

        switch (_type) {
            case MAÇA -> this.healAmount = 20;
            case BANANA -> this.healAmount = 15;
            case LARANJA -> this.healAmount = 25;
            case UVA -> this.healAmount = 10;
        }
    }
    @Override
    public void update(float delta) {
        // fruta não se move, deixa vazio
    }

    @Override
    public void draw(ShapeRenderer _renderer) {
        switch(type) {
            case MAÇA -> {
                _renderer.setColor(CONST.RED_COLOR);
                _renderer.circle(position.x + tamanho/2, position.y + tamanho/2, tamanho/2);
            }
            case BANANA -> {
                _renderer.setColor(CONST.YELLOW_COLOR);
                _renderer.rectLine(position.x, position.y, position.x + tamanho, position.y + tamanho/2, tamanho/3);
            }
            case LARANJA -> {
                _renderer.setColor(CONST.ORANGE_COLOR);
                _renderer.circle(position.x + tamanho/2, position.y + tamanho/2, tamanho/2);
            }
            case UVA -> {
                _renderer.setColor(CONST.PURPLE_COLOR);
                _renderer.circle(position.x,             position.y + tamanho/2, tamanho/4);
                _renderer.circle(position.x + tamanho/2, position.y + tamanho/2, tamanho/4);
                _renderer.circle(position.x + tamanho/4, position.y,             tamanho/4);
            }
        }
    }
    public boolean isCollected() { return collected; }
    public void setCollected(boolean c) { this.collected = c; }
    public int getHealAmount() { return healAmount; }
}
