package com.quartetofantastico.emoesc.logicAndMechanic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class Constants{
    //Valor de Ampliação:
    public static final float SCALE=50f;

    //Mundo:
    public static final int[][] Stage = new int[3][5];
    public static final float W_WORLD_SIZE=1920;
    public static final float H_WORLD_SIZE=1080;

    //Cores:
    public static final Color WHITE_COLOR = Color.WHITE;
    public static final Color BLUE_COLOR = Color.BLUE;
    public static final Color GREEN_COLOR = Color.GREEN;
    public static final Color YELLOW_COLOR = Color.YELLOW;
    public static final Color ORANGE_COLOR = Color.ORANGE;
    public static final Color PURPLE_COLOR = Color.PURPLE;
    public static final Color PINK_COLOR = Color.PINK;
    public static final Color CYAN_COLOR = Color.CYAN;
    public static final Color RED_COLOR = Color.RED;
    public static final Color GRAY_COLOR = Color.GRAY;
    public static final Color LIGHT_GRAY_COLOR = Color.LIGHT_GRAY;
    public static final Color DARK_GRAY_COLOR = Color.DARK_GRAY;
    public static final Color MAGENTA_COLOR = Color.MAGENTA;
    public static final Color BLACK_COLOR = Color.BLACK;
    public static final Color TRANSPARENT_COLOR = Color.CLEAR;

    public static final float BLUE_COLOR_FLOAT = Color.BLUE.toFloatBits();
    public static final float GREEN_COLOR_FLOAT = Color.GREEN.toFloatBits();
    public static final float YELLOW_COLOR_FLOAT = Color.YELLOW.toFloatBits();
    public static final float ORANGE_COLOR_FLOAT = Color.ORANGE.toFloatBits();
    public static final float PURPLE_COLOR_FLOAT = Color.PURPLE.toFloatBits();
    public static final float PINK_COLOR_FLOAT = Color.PINK.toFloatBits();
    public static final float CYAN_COLOR_FLOAT = Color.CYAN.toFloatBits();
    public static final float RED_COLOR_FLOAT = Color.RED.toFloatBits();
    public static final float GRAY_COLOR_FLOAT = Color.GRAY.toFloatBits();
    public static final float LIGHT_GRAY_COLOR_FLOAT = Color.LIGHT_GRAY.toFloatBits();
    public static final float DARK_GRAY_COLOR_FLOAT = Color.DARK_GRAY.toFloatBits();
    public static final float MAGENTA_COLOR_FLOAT = Color.MAGENTA.toFloatBits();
    public static final float BLACK_COLOR_FLOAT = Color.BLACK.toFloatBits();
    public static final float WHITE_COLOR_FLOAT = Color.WHITE.toFloatBits();
    public static final float TRANSPARENT_COLOR_FLOAT = Color.CLEAR.toFloatBits();

    //Avatar e outros NPCs
    public static final float MIN_SPEED = 200f;
    public static final float  ACCELERATION = 7500f;
    public static final float MAX_SPEED = 300f;
    public static final float FRICTION = 5000f;
    public static final float JUMP_VELOCITY = 500f;
    public static final float MAX_JUMP_TIME = 0.50f;
    public static final int MAX_HEALTH = 100;
    public static final int MIN_HEALTH = 0;
    public static final int MAX_SCORE = 999999999;
    public static final int W_SCREEN=1280;
    public static final int H_SCREEN=720;
    public static final float ANIMATED_OBJECT_MARGIN=2f;;
    public static final float GRAVITY = -900f;

    public boolean UP_KEY(){
        boolean key=false;
        if((Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W)) || (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.UP))) key = true;
        return key;
    }
    public boolean DOWN_KEY(){
        boolean key=false;
        if((Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S)) || (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.DOWN))) key = true;
        return key;
    }
    public boolean LEFT_KEY(){
        boolean key=false;
        if((Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A)) || (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.LEFT))) key = true;
        return key;
    }
    public boolean RIGHT_KEY(){
        boolean key=false;
        if((Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D)) || (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT))) key = true;
        return key;
    }

    public static final String[][] KeyWord = {
        {"Everybody"},
        {"Singer"},
        {"Mage"},
        {"Artist"},
        {"Cook"},
        {"Guard"},
        {"Astronaut"},
        {"Detective"},
        {"Pilote"},
        {"Ninja"},
        {"Scientist"},
        {"Mechanic"},
        {"Firefighter"},
        {"Farmer"},
        {"Teacher"},
        {"Teachnologist"},
        {"Judge"},
        {"Student"},
    };
}
//Essa classe contém todos os valores constantes do projecto;
