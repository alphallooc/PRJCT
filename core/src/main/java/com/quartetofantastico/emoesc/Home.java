package com.quartetofantastico.emoesc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.quartetofantastico.emoesc.interfaceUser.GameScreen;
import com.quartetofantastico.emoesc.interfaceUser.OpScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Home extends Game{

    private SpriteBatch batch;
    private Texture image;
    private BitmapFont font;

    @Override
    public void create() {
       //batch = new SpriteBatch();
        //font=new BitmapFont();
        //image = new Texture("libgdx.png");
        setScreen(new OpScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        /*batch.dispose();
        font.dispose();
        image.dispose();*/
    }
}
