package com.quartetofantastico.emoesc.world;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.quartetofantastico.emoesc.logicAndMechanic.Constants;
import com.quartetofantastico.emoesc.logicAndMechanic.Desafio;
import com.quartetofantastico.emoesc.logicAndMechanic.Entity;

public class Emoji extends Entity {
    private String simbolo;
    private Desafio desafio;
    private boolean coletado = false;
    private boolean emCooldown = false;
    private float cooldown = 0f;
    Constants CONST = new Constants();
    float tamanho = 32f;
    private BitmapFont font;

    public Emoji(String simbolo, Desafio desafio) {
        super(new Vector2(0, 0), 32f, 32f);
        this.simbolo = simbolo;
        this.desafio = desafio;
        this.font = new BitmapFont();
        this.font.setColor(1f, 1f, 0f, 1f);
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
        updateBounds();
    }

    @Override
    public void update(float delta) {
        if (emCooldown) {
            cooldown -= delta;
            if (cooldown <= 0f) {
                emCooldown = false;
                cooldown = 0f;
            }
        }
        updateBounds();
    }

    @Override
    public void draw(ShapeRenderer _renderer) {
        // vazio - usa drawSprite para desenhar
    }

    public void draw(SpriteBatch batch) {
        if (!coletado) {
            font.draw(batch, simbolo, position.x, position.y + tamanho);
        }
    }

    public void updateCooldown(float delta) {
        if (emCooldown) {
            cooldown -= delta;
            if (cooldown <= 0f) {
                emCooldown = false;
                cooldown = 0f;
            }
        }
    }

    public void startCooldown(float tempo) {
        emCooldown = true;
        cooldown = tempo;
    }

    public boolean isEmCooldown() { return emCooldown; }
    public boolean isColetado() { return coletado; }
    public void coletar() { coletado = true; }
    public Desafio getDesafio() { return desafio; }

    public void dispose() {
        font.dispose();
    }
}
